package UAV.service;



import java.util.ArrayList;
import java.util.List;

import UAV.comm.MapDistance;
import UAV.entity.ChildZone;
import UAV.entity.DockPoint;
import UAV.entity.NeedPoint;
import UAV.entity.Point;
import UAV.entity.WarePoint;
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

	
	private List<DockPoint> allDockPoints;
	
	private List<NeedPoint> allNeedPoints;
	private List<DockPoint> selectedDockPoints;
	private List<WarePoint> warePoints;
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
		
		pointPreProcess();
		List<ChildZone> childZones = childZonePatition();
		for (ChildZone childZone : childZones) {
			//TODO:czPs是子区域的所有停靠点和对应的仓库点，需要使用childZone从数据库获得
			List<Point> czPs = new ArrayList<Point>();
			
			double[][] pDis = getPointDisByRoad(czPs);
			List<Point> carPath = carPathArrange(czPs, pDis);
			UAVArrange(carPath);
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
	private void pointPreProcess() {
		
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
	 * 
	 * 解决旅行商问题的模块,返回形式为最优解的行走序列，第一个点为仓库点，之后顺序的为停靠点
	 * @param childZone
	 */
	private List<Point> carPathArrange(List<Point> czPs, double[][] pDis) {
		
		return new ArrayList<Point>();
	}
	
	/**
	 * 妥
	 * 无人机调度模块
	 * @param carPath 已经经过计算的旅行商问题最优解的序列，车要按着这个序列走
	 */
	private void UAVArrange(List<Point> carPath) {
		//TODO:会需要从停靠点得到停靠点所负责的所有需求点的集合
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
