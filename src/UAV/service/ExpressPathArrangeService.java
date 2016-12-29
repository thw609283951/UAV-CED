package UAV.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


import UAV.entity.Car;

import UAV.ACO.ACO;

import UAV.comm.MapDistance;
import UAV.comm.kdtree.KDTree;
import UAV.comm.kdtree.KeyDuplicateException;
import UAV.comm.kdtree.KeySizeException;
import UAV.dao.ExpressPathArrangeDAO;
import UAV.comm.TSPUtils;
import UAV.entity.ChildZone;
import UAV.entity.DockPoint;
import UAV.entity.NeedPoint;
import UAV.entity.Point;
import UAV.entity.WarePoint;

import UAV.factory.ExpressPathArrangeDAOFactory;

import UAV.entity.UavForExpress;

/*
 * entity中咱们能用到的类（也即是数据库中咱们能用到的表）:
 * ChildZone, DockPoint, NeedPoint, Point(无数据表对应), UavForExpress, UavVersionInfo, WarePoint
 * (如新增可添加在此~)
 *
 * 由于学长们没设置外键，设计的时候我也没设计（现在倒是觉得设计了好了。。其实还是因为不涉及逆向过来方便。。偷懒了）。。说明一下数据表之间的关系
 * NeedPoint:
 * 		其中有个dockid的字段，表示负责这个NeedPoint的DockPoint是哪个，dockDis表示到这个DockPoint的距离
 * DockPoint:
 * 		其中selected表示其是否被选择，group无视之，czid是对应于ChildZone中的id的,表示这个DockPoint属于这个ChildZone
 * ChildZone:
 * 		其中有一个wrid,是对应于WarePoint中的id的，表示这个子区域包含哪个仓库点，因为仓库点对子区域是1toN的关系，所以这么做
 *
 * 所以，子区域的数据结构表示为：
 * 		通过ChildZone的id在DockPoint中找是这个czid的就能找到这个子区域的所有DockPoint,然后并上ChildZone中的wrid对应的仓库点即是子区域包含的所有点
 * 		当然，可以在内存中用个Set或者List存一下，更快更方便，然而依照我能水则水的设计思想（。。。）先从数据库来吧，之后再改感觉也不难
 *
 * 还有一个要注意的关系是我们用到的无人机如果要得到速度，最大载货量等参数，需要用Version字段访问UavVersionInfo,这个是出于实际情况考虑的。
 *
 *
 *
 */
public class ExpressPathArrangeService {

	
	private List<DockPoint> allDockPoints;//所有停靠点
	
	private List<NeedPoint> allNeedPoints;//所有需求点
	private List<DockPoint> selectedDockPoints;//选择的停靠点
	private List<WarePoint> warePoints;//仓库店
	public List<DockPoint> getAllDockPoints() {
		return allDockPoints;
	}


	public void setAllDockPoints(List<DockPoint> allDockPoints) {
		this.allDockPoints = allDockPoints;
	}


	public List<NeedPoint> getAllNeedPoints() {
		return allNeedPoints;
	}


	public void setAllNeedPoints(List<NeedPoint> allNeedPoints) {
		this.allNeedPoints = allNeedPoints;
	}

	

	/**
	 * 路径规划总算法
	 */
	public void pathArrange() {
		
		//TODO: 利用DAOimpl初始化allDockPoints,allNeedPoints,warePoints;
//		ExpressPathArrangeDAO epaDao = ExpressPathArrangeDAOFactory.getInstance();
//		allDockPoints = epaDao.getAllDockPoints();
//		allNeedPoints = epaDao.getAllNeedPoints();
		pointPreProcess();
		List<ChildZone> childZones = childZonePatition();
		for (ChildZone childZone : childZones) {
			List<Point> czPoints = childZone.getCzPoints();
			double[][] pDis = getPointDisByRoad(czPoints);
//			List<Point> carPath = carPathArrange(czPoints, pDis);
			childZone.setCzPoints((ArrayList<Point>) carPathArrange(czPoints, pDis));//TODO: 不确定这句话对不对
			UAVArrange(childZone);
		}
	}
	
	
	/**
	 * 路径显示
	 * 如果本功能放到pathArrange里面更简单，那就放进去。。
	 */
	public void pathDisplay() {
		
	}
	
	
	/**
	 * 马
	 * 
	 * 此子函数为距离计算模块和停靠点选取模块的综合
	 * 负责填写need_point表中的dockid和dockdis
	 * 在allNeedPoints中修改并将修改添加到数据库
	 * 并将allDockPoints中选出selectedDockPoints
	 * 并释放allDockPoints
	 */
	public void pointPreProcess() {
		ExpressPathArrangeDAO epaDao = ExpressPathArrangeDAOFactory.getInstance();
		allDockPoints = epaDao.getAllDockPoints();
		allNeedPoints = epaDao.getAllNeedPoints();
		KDTree<Integer> kdTree = new KDTree<Integer>(2);
		//Map<Integer, ArrayList<NeedPoint>> needPointMap = new TreeMap<Integer, ArrayList<NeedPoint>>();
		for (DockPoint dockPoint : allDockPoints) {
			//needPointMap.put(dockPoint.getId(), new ArrayList<NeedPoint>());
			double[] coord = {dockPoint.getLongitude().doubleValue(),
					dockPoint.getLatitude().doubleValue()};
			try {
				//System.out.println("kkkkk"+dockPoint.getId());
				kdTree.insert(coord, dockPoint.getId());
			} catch (KeySizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyDuplicateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (NeedPoint needPoint : allNeedPoints) {
			double[] coord = {needPoint.getLongitude().doubleValue(),
					needPoint.getLatitude().doubleValue()};
			Integer id = null;
			try {
				id = kdTree.nearest(coord);
				//System.out.println(id + "\t\t" + needPoint.getId());
				for (DockPoint d : allDockPoints) {
					if (d.getId().equals(id)) {
						//needPointMap.get(d.getId()).add(needPoint);
						d.getNeedPoint_arr().add(needPoint);
						needPoint.setDockid(d.getId());
						needPoint.setDockdis(MapDistance.GetDistance(
								needPoint.getLongitude(), 
								needPoint.getLatitude(),
								d.getLongitude(),
								d.getLatitude()));
						break;
					}
				}
				
			} catch (KeySizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
//		for (DockPoint dockPoint : allDockPoints) {
//			dockPoint.setSelected(true);
//			dockPoint.setCzid(-1);
//			needPointMap.get(dockPoint.getId()).toArray();
//			dockPoint.setNeedPoint_arr((NeedPoint[]) needPointMap.get(dockPoint.getId()).toArray(new NeedPoint[0]));
//		}
		
		selectedDockPoints = allDockPoints;
		
		for (NeedPoint needPoint : allNeedPoints) {
			System.out.println(needPoint);
		}
		for (DockPoint dockPoint : allDockPoints) {
			System.out.println(dockPoint);
		}
	}
	
	
	
	/**
	 * 黄
	 * 
	 * 子区域划分模块
	 * 负责填写dock_point表中的czid
	 * 在selectedDockPoints中修改并将修改添加到数据库
	 * 生成ChildZone序列，并填写其中的wrid
	 */
	private List<ChildZone> childZonePatition() {
		
		
		return new ArrayList<ChildZone>();
	}
	
	/**
	 * 冯
	 * 返回0;6;11;13;2;10;1;5;4;14;3;12;9;8;7，其中0代表仓库点
	 * 解决旅行商问题的模块,返回形式为最优解的行走序列，第一个点为仓库点，之后顺序的为停靠点
	 * @param 
	 */
	public List<Point> carPathArrange(List<Point> czPs, double[][] pDis) {
		 ACO aco = new ACO();
		 aco.Init_Distance(czPs.size(), pDis);
	     aco.iterator();
	     List<Point> poList = new ArrayList<Point>();
	     poList = TSPUtils.getPointSequence(czPs, aco.getBestTour());
		 return poList;
	}
	
	/**
	 * 妥
	 * 无人机调度模块
	 * @param carPath 已经经过计算的旅行商问题最优解的序列，车要按着这个序列走
	 */
	private void UAVArrange(ChildZone childZone) {
		//TODO:会需要从停靠点得到停靠点所负责的所有需求点的集合
		ArrayList<NeedPoint> S = new ArrayList<NeedPoint>();//保存child_zone的所有需求点
		ArrayList<ArrayList<NeedPoint>> div = new ArrayList<ArrayList<NeedPoint>>();//指定停靠点所属需求点的划分
//		ArrayList<Double> l_all = new ArrayList<Double>();//指定停靠点的无人机路径序列
		double max_l = 0;//保存最长的区域路径长度
		double l_length = 0;//l长度
		double max_wait_time;//指定停靠点的最大等待时间，等待是为了充电
		double time = 0;//相对时间，从0起
		double dist;//保存距离计算结果输出
		ArrayList<Point> point_list = new ArrayList<Point>();//记录点集合，计算距离使用
		double tmp_time;
		Car car = childZone.getCar();//获取子区域负责车辆
		for(DockPoint dock : childZone.getDockPoint_arr()){ //遍历停靠点
			S = dock.getNeedPoint_arr();//所有需求点
			DockPoint next_dock = childZone.get_next_dock(dock);//获取下一个停靠点;
			div = Divid_need(S, dock, car.getUavCount());//划分所有需求点，分派给无人机
			List<Point> l;//保存每个划分的无人机路径序列，不含路径头和尾，因为这两个点都应该是停靠点dock，数据类型不同保存不方便
			UavForExpress uav = null;
			for(List<NeedPoint> part : div){ //遍历所有需求划分区域
				l = TSP(part, dock);         //该区域的路线
				uav = car.sendUav();         //从car中派出一辆无人机
				uav.add_P(dock,l,time);     //通过路径，向uav中添加时序路径序列
				l_length = get_l_length(l); //得到l长度
				if (max_l < l_length){
					max_l = l_length;
				}
			}
			if (next_dock != null){//未到最后一个停靠点
				dist = getDistanceByAir(dock,next_dock);
				tmp_time = dist/car.getV();//到下一个停靠点的时间
				max_wait_time = get_max_wait_time(max_l,uav.getVelocity(),car.getV(),tmp_time,dock,childZone);//获取car在dock的最大等待时间
				tmp_time += max_wait_time;
				time += tmp_time;//过了tmp_time时间，车行驶到下一个停靠点
				car.add_P(time,dock,next_dock);//为车添加时序路径
			}
		}
	}
	/**
	 * 妥，计算路径序列总长度
	 * @param l路径序列
	 * @return
	 */
	private double get_l_length(List<Point> l) {
		// TODO Auto-generated method stub
		double l_length = 0;
		for (int i=0; i<l.size()-1;i++){
			l_length += getDistanceByAir(l.get(i),l.get(i+1));
		}
		return l_length;
	}


	/**
	 * 妥
	 * 划分需求点并指派给无人机,S为需求点集合，var_uav为无人机数量，也就是需要划分成的块数
	 * @param S 需求点集合
	 * @param dock 停靠点
	 * @param var_uav无人及数量
	 * @return 划分结果
	 */
	private ArrayList<ArrayList<NeedPoint>> Divid_need(List<NeedPoint> S,DockPoint dock, int var_uav){
		int m = S.size();//需求点个数
		ArrayList<ArrayList<NeedPoint>> div = new ArrayList<ArrayList<NeedPoint>>();
		if(var_uav>=m){
			for(NeedPoint p : S){
				ArrayList<NeedPoint> tmp = new ArrayList<NeedPoint>();
				tmp.add(p);
				div.add(tmp);
			}
		}
		else{
			return k_means_with_Point(S,var_uav);
		}

		return div;
	}
	/**
	 * 妥
	 * k均值算法划分需求点为var_uav个部分
	 * @param s 需求点集合
	 * @param var_uav 无人及数量，也是划分子区域数量
	 * @return
	 */
	private ArrayList<ArrayList<NeedPoint>> k_means_with_Point(
			List<NeedPoint> s, int var_uav) {
		// TODO Auto-generated method stub
		
		return null;
	}


	/**
	 * 妥
	 * 旅行商问题求解从dock点出发遍历part中点，并返回dock的最短路径的路径序列
	 */
	private List<Point> TSP(List<NeedPoint> part, DockPoint dock){
		List<Point> l = new ArrayList<Point>();
		List<Point> points = new ArrayList<Point>();
		points.add(dock);
		points.addAll(part);
		
		double[][] p_dist = getPointDisByAir(points);//记录停靠点和所有需求点彼此路径长度
		l =  carPathArrange(points, p_dist);//这里完全可以使用掌印的TSP代码
		
		return l;
	}

	/**
	 * 妥
	 * 根据无人机执行情况推算所需的最大充电时间，以得到car在停靠点dock的最大等待时间
	 * max_l：所有无人机最长飞行距离
	 * uavZ_v,car_v:无人机、车速
	 */
	private double get_max_wait_time(double max_l,double uav_v,double car_v,double to_next_dock_time, DockPoint dock, ChildZone childZone){
		double t_max_charge = 10;//先设一个常数，后面换成数学模型
		double t_max_fly = 0;//无人机最长飞行时间
		
		t_max_fly = max_l/uav_v;
		if (to_next_dock_time > t_max_charge){// 在路上即可完成充电
			return t_max_fly;
		}
		else{//需要等待充一会电再出发
			return to_next_dock_time - t_max_charge + t_max_fly;
		}
	}
	
	/**
	 * 马
	 * 计算points数组中各个点之间的距离,此距离为路上距离，而不是直线距离。以二维数组形式返回
	 * @param points
	 * @return
	 */
	public static double[][] getPointDisByRoad(List<Point> points) {
		double[][] dis = new double[points.size()][points.size()];
		for (int i = 0; i < points.size(); i++) {
			for (int j = 0; j < points.size(); j++) {
				if (j == i) {
					dis[i][j] = 0;
				} else if (j > i){
					dis[i][j] = dis[j][i] = MapDistance.GetDistance(
							points.get(i).getLongitude(),
							points.get(i).getLatitude(),
							points.get(j).getLongitude(),
							points.get(j).getLatitude());
				}
			}
		}


		return dis;
	}
	
	public double getDistanceByAir(Point a, Point b) {
		return MapDistance.GetDistance(a.getLongitude(), 
										a.getLatitude(),
										b.getLongitude(),
										b.getLatitude());
	}
	
	
	/**
	 * 马
	 * 计算points数组中各个点之间的距离,此距离为空中距离，即是直线距离。以二维数组形式返回
	 * @param points
	 * @return
	 */
	public static double[][] getPointDisByAir(List<Point> points) {
		double[][] dis = new double[points.size()][points.size()];
		for (int i = 0; i < points.size(); i++) {
			for (int j = 0; j < points.size(); j++) {
				if (j == i) {
					dis[i][j] = 0;
				} else if (j > i){
					dis[i][j] = dis[j][i] = MapDistance.GetDistance(
							points.get(i).getLongitude(),
							points.get(i).getLatitude(), 
							points.get(j).getLongitude(),
							points.get(j).getLatitude());
				}
			}
		}
		return dis;
	}
		

//	public List<Point> getTestCzPoints() {
//		
//	}
	
	
	
}
