package UAV.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import UAV.comm.kmeans.Cluster;
import UAV.comm.kmeans.KMeans;


import UAV.entity.Car;

import UAV.ACO.ACO;

import UAV.comm.MapDistance;
import UAV.comm.RoadDistance;
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
import UAV.service.Utility;
import UAV.service.DBscan;

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
	private static List<DockPoint> selectedDockPoints;//选择的停靠点
	private static List<WarePoint> warePoints;//仓库店
	private List<ChildZone> childZones;
	
	private List<ArrayList<DockPoint>> resultList=new ArrayList<ArrayList<DockPoint>>();//
	public List<DockPoint> getAllDockPoints() {
		return allDockPoints;
	}
	/**马
	 * 获取所有仓库点，double数组形式返回
	 * @return
	 */
	public ArrayList<ArrayList<Double>> getWpCoords() {
		ArrayList<ArrayList<Double>> coordList = new ArrayList<ArrayList<Double>>();
		for (WarePoint wp : warePoints) {
			coordList.add(wp.toCoordArray());
		}
		return coordList;
	}
	/**马
	 * 获取所有停靠点，double数组形式返回
	 * @return
	 */
	public ArrayList<ArrayList<Double>> getSdpCoords() {
		ArrayList<ArrayList<Double>> coordList = new ArrayList<ArrayList<Double>>();
		for (DockPoint sdp : selectedDockPoints) {
			coordList.add(sdp.toCoordArray());
		}
		return coordList;
	}
	/**马
	 * 获取所有需求点，double数组形式返回
	 * @return
	 */
	public ArrayList<ArrayList<Double>> getNpCoords() {
		ArrayList<ArrayList<Double>> coordList = new ArrayList<ArrayList<Double>>();
		for (NeedPoint np : allNeedPoints) {
			coordList.add(np.toCoordArray());
		}
		return coordList;
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
	 * @throws Exception
	 */
	public List<ChildZone> pathArrange() throws Exception {
		
		//TODO: 利用DAOimpl初始化allDockPoints,allNeedPoints,warePoints;
		pointPreProcess();
		this.childZones = childZonePatition();
		createCarAndUavs(childZones);//根据子区域，产生Car以及uav对象
		for (ChildZone childZone : childZones) {
			List<Point> czPoints = childZone.getCzPoints();
			double[][] pDis = getPointDisByRoad(czPoints);
			childZone.setCzPoints((ArrayList<Point>) carPathArrange(czPoints, pDis));//TODO: 不确定这句话对不对
			UAVArrange(childZone);
		}
		return childZones;
	}
public void multiThreadPathArrange() {
		//TODO: 利用DAOimpl初始化allDockPoints,allNeedPoints,warePoints;
		pointPreProcess();
		List<ChildZone> childZones = childZonePatition();
		createCarAndUavs(childZones);//根据子区域，产生Car以及uav对象
		int id=0;
		for (ChildZone childZone : childZones) {
			MultiThreadForExpress m = new MultiThreadForExpress(childZone, id);
			m.start();
			id++;
		}
	}
	
	/**
	 * 妥
	 * new出car和uav对象，并将Car绑定到子区域上，将uav绑定到car上
	 * @param childZones
	 */
	private void createCarAndUavs(List<ChildZone> childZones) {
		// TODO Auto-generated method stub
		for (ChildZone childZone : childZones){
			Car car= new Car();
			ArrayList<UavForExpress> uavs = new ArrayList<UavForExpress>();
			for (int i = 0; i < Car.uavsInEveryCar ; i++){
				UavForExpress uav = new UavForExpress();
				uavs.add(uav);
			}
			car.setUavs(uavs);
			childZone.setCar(car);
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
	 * 负责填写needPoint表中的dockid和dockdis
	 * 在allNeedPoints中修改并将修改添加到数据库
	 * 并将allDockPoints中选出selectedDockPoints
	 * 并释放allDockPoints
	 */
	private void pointPreProcess() {
		ExpressPathArrangeDAO epaDao = ExpressPathArrangeDAOFactory.getInstance();
		allDockPoints = epaDao.getAllDockPoints();
		allNeedPoints = epaDao.getAllNeedPoints();
		warePoints = epaDao.getAllWarePoints();
		KDTree<Integer> wareKdTree = new KDTree<Integer>(2);
		KDTree<Integer> dockKdTree = new KDTree<Integer>(2);
		for (WarePoint warePoint : warePoints) {
			double[] coord = {warePoint.getLongitude().doubleValue(),
					warePoint.getLatitude().doubleValue()};
			try {
				wareKdTree.insert(coord, warePoint.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("dockpoint\t\twarepoint");
		
		Integer wrId = null;
		for (DockPoint dockPoint : allDockPoints) {
			double[] coord = {dockPoint.getLongitude().doubleValue(),
					dockPoint.getLatitude().doubleValue()};
			try {
				wrId = wareKdTree.nearest(coord);
				dockPoint.setWrid(wrId);
				System.out.println(dockPoint.getId() + "\t\t\t" + dockPoint.getWrid() );
				dockKdTree.insert(coord, dockPoint.getId());
			} catch (KeySizeException e) {
				e.printStackTrace();
			} catch (KeyDuplicateException e) {
				e.printStackTrace();
			}
		}
		for (NeedPoint needPoint : allNeedPoints) {
			double[] coord = {needPoint.getLongitude().doubleValue(),
					needPoint.getLatitude().doubleValue()};
			Integer dockId = null;
			try {
				dockId = dockKdTree.nearest(coord);
				for (DockPoint d : allDockPoints) {
					if (d.getId().equals(dockId)) {
						d.getneedPoints().add(needPoint);
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
				e.printStackTrace();
			}
			
		}

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
	 * 负责填写dockPoint表中的czid
	 * 在selectedDockPoints中修改并将修改添加到数据库
	 * 生成ChildZone序列，并填写其中的wrid
	 */
	private List<ChildZone> childZonePatition() {
		int index = 1;
		ArrayList<WarePoint> UAVWarePoint = (ArrayList<WarePoint>) warePoints;
		ArrayList<Car> UAVCar = new ArrayList<Car>();//存放一个仓库点所有的车
		ArrayList<ChildZone> UAVChildZone = new ArrayList<ChildZone>();//存放所有子区域 不同仓库点的子区域都在
		ChildZone Zone = new ChildZone();//单个子区域
		Car car = new Car();//单辆车 应该直接从UAVCar中获得，这里暂时有自己创建
		String[] args = null;
		int wareid=0;
		List<ArrayList<Point>> AllChildZone = new ArrayList<ArrayList<Point>>();
		ArrayList<Point> onechildzone = new ArrayList<Point>();
		Point mypoint = new Point();
//		Utility.display(resultList);
		for (WarePoint warePoint:UAVWarePoint){
			//根据仓库点的id从数据库获得子区域划分结果
			wareid=warePoint.getId();
			resultList=DBscan.resultList(wareid,selectedDockPoints);//获得一个仓库点的子区域集合
			for(Iterator<ArrayList<DockPoint>> it=resultList.iterator();it.hasNext();){
				ArrayList<DockPoint> lst=it.next();//子区域
				if(lst.isEmpty()){
					continue;
				}
				onechildzone=new ArrayList<Point>();//清楚子区域伪装数组
				//为每个子区域配子区域id 负责子区域的仓库点 子区域的停靠点集合 负责子区域的车 多个仓库点的话需要再加一层循环
				// 这里只考虑了一个仓库点
				Zone = new ChildZone();
				Zone.setId(index);//设置子区域id
				Zone.setWrid(wareid);//设置负责子区域的仓库点
				Zone.setCar(car);//设置负责子区域的车
				Zone.getCzPoints().add(warePoint);
				for(DockPoint dockPoint: lst){
					Zone.getCzPoints().add(dockPoint);
				}
				UAVChildZone.add(Zone);

		    }
		}
		return UAVChildZone;
	}

	/**
	 * 冯
	 * 返回0;6;11;13;2;10;1;5;4;14;3;12;9;8;7，其中0代表仓库点
	 * 解决旅行商问题的模块,返回形式为最优解的行走序列，第一个点为仓库点，之后顺序的为停靠点
	 * @param 
	 */
	public static List<Point> carPathArrange(List<Point> czPs, double[][] pDis) {
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
	public void UAVArrange(ChildZone childZone) {
		//TODO:会需要从停靠点得到停靠点所负责的所有需求点的集合
		ArrayList<NeedPoint> S = new ArrayList<NeedPoint>();//保存child_zone的所有需求点
		ArrayList<ArrayList<NeedPoint>> div = new ArrayList<ArrayList<NeedPoint>>();//指定停靠点所属需求点的划分
		Double maxL;//保存最长的区域路径长度
		Double lLength = new Double(0);//l长度
		Double maxWaitTime;//指定停靠点的最大等待时间，等待是为了充电
		Double time = new Double(0);//相对时间，从0起
		Double dist;//保存距离计算结果输出
		Point warePoint = childZone.getCzPoints().get(0);//保存仓库点
		Double tmpTime;
		Car car = childZone.getCar();//获取子区域负责车辆
	
		ArrayList<DockPoint> dps = new ArrayList<DockPoint>();//将czpoint中的对象从第2分开始往后转化为dockPoint
		ArrayList<Point> czps = childZone.getCzPoints();
		for (int i = 1; i < czps.size(); i++) {
			dps.add((DockPoint) czps.get(i));
		}
		car.addP(time,warePoint);//向仓库点添加时序序列表示其由仓库点开始，走向了第一个停靠点
		dist = 0.001 * getDistanceByAir(warePoint,dps.get(0));//仓库点到第一个停靠点的距离
		tmpTime = dist/Car.carV;//到下一个停靠点的时间
		time+=tmpTime;
		
		for(DockPoint dock : dps){ //遍历停靠点
			car.addP(time,dock);//车行驶到停靠点
			S = dock.getneedPoints();//所有需求点
			Point nextDock = childZone.getNextDock(dock);//获取下一个停靠点;
			maxL = new Double(0);
			maxWaitTime = new Double(0);
			double tMaxFly = 0;
			
			if (S.size() != 0){ //停靠点包含至少一个需求点
				div = DividNeed(S, dock, car.getUavCount());//划分所有需求点
				List<Point> l;//保存每个划分的无人机路径序列，第一个为停靠点，后面为需求点
				UavForExpress uav = null;
				for(List<NeedPoint> part : div){ //遍历所有需求划分区域
					l = TSP(part, dock);         //该区域的路线，借助旅行商算法
					uav = car.sendUav();         //从car中派出一辆无人机
					uav.addP(l,time,UavForExpress.velocity);      //通过路径，向uav中添加时序路径序列
					lLength = getLLength(l);  //得到l长度，为从停靠点出发，再返回停靠点的长度
					if (maxL < lLength){       //得到最长路径，计算充电时间
						maxL = lLength;
					}
				}
				tMaxFly = maxL/UavForExpress.velocity;//无人机最长飞行时间
				maxWaitTime = getMaxWaitTime(maxL,UavForExpress.velocity,Car.carV,tmpTime,dock,childZone);//获取car在dock的最大等待时间
			}
			if (nextDock != null){//未到最后一个停靠点
				dist = 0.001 * getDistanceByAir(dock,nextDock);
				tmpTime = dist/Car.carV;//到下一个停靠点的时间
				tmpTime += maxWaitTime + tMaxFly;
				if (maxWaitTime>0){
					car.addP(maxWaitTime+time+tMaxFly,dock);//需要原地等待
				}
				time += tmpTime;//过了tmpTime时间，车行驶到下一个停靠点
			}
			else{//添加到仓库点的时序路径
				dist = 0.001 * getDistanceByAir(dock,warePoint);
				time += dist/Car.carV;
				car.addP(time, warePoint);
			}
			car.freeUavs();//设置所有无人机状态为“闲”,表示所有无人机已返回快递车
		}
	}

	/**
	 * 妥，计算路径序列总长度
	 * @param l路径序列长度
	 * @return
	 */
	private Double getLLength(List<Point> l) {
		// TODO Auto-generated method stub
		Double lLength = new Double(0);
		int i;
		for (i=0; i<l.size()-1;i++){
			lLength += 0.001 * getDistanceByAir(l.get(i),l.get(i+1));
		}
		lLength += 0.001 *getDistanceByAir(l.get(i), l.get(0));//加上到最后一个需求点到停靠点的距离
		return lLength;
	}


	/**
	 * 妥
	 * 划分需求点并指派给无人机,S为需求点集合，varUav为无人机数量，也就是需要划分成的块数
	 * @param S 需求点集合
	 * @param dock 停靠点
	 * @param varUav无人及数量
	 * @return 划分结果
	 */
	private ArrayList<ArrayList<NeedPoint>> DividNeed(List<NeedPoint> S,DockPoint dock, int varUav){
		int m = S.size();//需求点个数
		ArrayList<ArrayList<NeedPoint>> div = new ArrayList<ArrayList<NeedPoint>>();
		if(varUav>=m){
			for(NeedPoint p : S){
				ArrayList<NeedPoint> tmp = new ArrayList<NeedPoint>();
				tmp.add(p);
				div.add(tmp);
			}
		}
		else{
			return kMeansWithPoint(S,varUav);
		}

		return div;
	}
	/**
	 * 妥
	 * k均值算法划分需求点为varUav个部分
	 * @param s 需求点集合
	 * @param varUav 无人及数量，也是划分子区域数量
	 * @return
	 */
	private ArrayList<ArrayList<NeedPoint>> kMeansWithPoint(
			List<NeedPoint> s, int varUav) {
		// TODO Auto-generated method stub
		List<Cluster> clusters= new ArrayList<Cluster>();
		ArrayList<ArrayList<NeedPoint>> divPoints = new ArrayList<ArrayList<NeedPoint>>();
		KMeans k = new KMeans();
		k.setNUM_CLUSTERS(varUav);
		k.init(s);
		k.calculate();
		clusters = k.getClusters();
		for (Cluster cluster : clusters){
			ArrayList<NeedPoint> tmpPoints = new ArrayList<NeedPoint>();
			tmpPoints = cluster.getNeedPoints();
			divPoints.add(tmpPoints);
		}
		return divPoints;
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
		
		double[][] pPist = getPointDisByAir(points);//记录停靠点和所有需求点彼此路径长度
		l =  carPathArrange(points, pPist);//这里完全可以使用掌印的TSP代码
		
		return l;
	}

	/**
	 * 妥
	 * 根据无人机执行情况推算所需的最大充电时间，以得到car在停靠点dock的最大等待时间
	 * maxL：所有无人机最长飞行距离
	 * uavV,carV:无人机、车速
	 */
	private double getMaxWaitTime(double maxL,double uavV,double carV,double toNextDockTime, DockPoint dock, ChildZone childZone){
		double tMaxCharge = getMaxChargeTime(maxL);//先设一个常数，后面换成数学模型

		if (toNextDockTime > tMaxCharge){// 在路上即可完成充电
			return 0;
		}
		else{//需要等待充一会电再出发
			return tMaxCharge - toNextDockTime;
		}
	}
	/**
	 * 妥
	 * 最长充电时间
	 * 充电时间=总充电时间*（已行驶航程/总航程）     <不考虑负责需求点数>
	 * @param maxL：最长航程
	 * @return
	 */
	private double getMaxChargeTime(double maxL) {
		// TODO Auto-generated method stub
		return UavForExpress.totalChargeTime*(maxL/UavForExpress.maxTrade);
	}

	
	/**
	 * 妥
	 * 打印路径
	 */
	public void printPath(){
		double tmpTime,totalTime = 0;
		System.out.println("路径规划输出：");
		for (ChildZone childZone: childZones){
    		Car car = childZone.getCar();
    		tmpTime = car.getP().get(car.getP().size()-1).get(0);//最终返回仓库点的时间
    		if (tmpTime>totalTime){
    			totalTime=tmpTime;
    		}
    		System.out.println("Car"+car.getP());
    		for (UavForExpress uav : car.getUavs()){
    			System.out.println("Uav"+uav.getP());
    		}
    	}
		System.out.println("任务总时间估计："+totalTime+"小时");
		
	}
	/**
	 * 妥
	 * 测试代码
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
    	ExpressPathArrangeService ep = new ExpressPathArrangeService();
    	long start = System.currentTimeMillis();
    	List<ChildZone> childZones = ep.pathArrange();
    	ep.printPath();
    }

	public ArrayList<ArrayList<ArrayList<Double>>> getDemoCarPath(){
		ArrayList<ArrayList<ArrayList<Double>>> pss = new ArrayList<ArrayList<ArrayList<Double>>>() ;
		for (ChildZone childZone: childZones){
    		Car car = childZone.getCar();
    		ArrayList<ArrayList<Double>> ps = new ArrayList<ArrayList<Double>>();
    		for (ArrayList<Double> p : car.getP()){
    			ArrayList<Double> item = new ArrayList<Double>();
    			item.add(p.get(1));
    			item.add(p.get(2));
    			ps.add(item);
    		}
    		pss.add(ps);
    	}
		return pss;
	}

	public ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> getDemoUavPath() {
		ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> pss = new ArrayList<ArrayList<ArrayList<ArrayList<Double>>>>();
		for (ChildZone childZone: childZones){
    		Car car = childZone.getCar();
    		ArrayList<UavForExpress>uavs = car.getUavs();
    		for (UavForExpress uav : uavs){
    			pss.add(uav.getDemoP());
    		}
    	}
		return pss;
	}

	/**
	 * 马
	 * 计算points数组中各个点之间的距离,此距离为路上距离，而不是直线距离。以二维数组形式返回
	 * @param points
	 * @return
	 * @throws Exception
	 */
	public static double[][] getPointDisByRoad(List<Point> points) throws Exception {
		double[][] dis = new double[points.size()][points.size()];
		for (int i = 0; i < points.size(); i++) {
			for (int j = 0; j < points.size(); j++) {
				if (j == i) {
					dis[i][j] = 0;
				} else if (j > i){
					dis[i][j] = dis[j][i] = RoadDistance.getDistanceByRoad(
							points.get(i).getLongitude(),
							points.get(i).getLatitude(),
							points.get(j).getLongitude(),
							points.get(j).getLatitude());
				}
			}
		}


		return dis;
	}
	
	public static double getDistanceByAir(Point a, Point b) {
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
}
