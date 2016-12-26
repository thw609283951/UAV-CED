package UAV.service;



import java.util.ArrayList;
import java.util.List;
import UAV.entity.Car;
import UAV.comm.MapDistance;
import UAV.entity.ChildZone;
import UAV.entity.DockPoint;
import UAV.entity.NeedPoint;
import UAV.entity.Point;
import UAV.entity.WarePoint;
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
	private void UAVArrange(ChildZone childZone) {
		//TODO:会需要从停靠点得到停靠点所负责的所有需求点的集合
		NeedPoint S[];//保存child_zone的所有需求点
		NeedPoint div[][];//指定停靠点所属需求点的划分
		NeedPoint l_all[][];//指定停靠点的无人机路径序列
		double max_wait_time;//指定停靠点的最大等待时间，等待是为了充电
		double time = 0;//相对时间，从0起
		double tmp_time;
		Car car = childZone.getCar();//获取子区域负责车辆
		for(DockPoint dock : childZone.getDockPoint_arr()){ //遍历停靠点
			S=dock.getNeedPoint_arr();//所有需求点
			DockPoint next_dock = childZone.get_next_dock(dock);//获取下一个停靠点;
			div = Divid_need(S, dock, car.getUavCount());//划分所有需求点，分派给无人机
			NeedPoint l[];//保存每个划分的无人机路径序列，不含路径头和尾，因为这两个点都应该是停靠点dock，数据类型不同保存不方便
			UavForExpress uav;
			for(NeedPoint[] part : div){ //遍历所有需求划分区域
				l = TPS(part, dock); //该区域的路线
				uav = car.sendUav();//从car中派出一辆无人机
				uav.add_P(l,time);//通过路径，向uav中添加时序路径序列
			max_wait_time = get_max_wait_time(l_all,uav.getVelocity(),car.getV(),dock,childZone);//获取car在dock的最大等待时间
			tmp_time = get_dist(dock, next_dock)/car.getV();//到下一个停靠点的时间
			tmp_time += max_wait_time;
			time += tmp_time;//过了tmp_time时间，车行驶到下一个停靠点
			car.add_P(time,dock,next_dock);//为车添加时序路径
			}
		}
	}
	/*
	 * 划分需求点并指派给无人机
	 */
	private NeedPoint[][] Divid_need(NeedPoint[] S,DockPoint dock, int var_uav){
		int m = S.length;//需求点个数
		NeedPoint div[][];
		
		if(var_uav>=m){
			return S;
		}
		else{
			return k_means(S,n);
		}

		return div;
	}

	/*
	 * 旅行商问题求解从dock点出发遍历part中点，并返回dock的最短路径的路径序列
	 */
	private NeedPoint[] TPS(NeedPoint[] part, DockPoint dock){
		NeedPoint l[];
		return l;
	}

	/*
	 * 根据无人机执行情况推算所需的最大充电时间，以得到car在停靠点dock的最大等待时间
	 */
	private double get_max_wait_time(NeedPoint[][] l_all,double uav_v,double car_v,DockPoint dock, ChildZone childZone){
		return 0;
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
