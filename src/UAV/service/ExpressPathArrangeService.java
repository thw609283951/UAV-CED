package UAV.service;



import java.util.ArrayList;
import java.util.List;

import UAV.entity.CarPath;
import UAV.entity.ChildZone;
import UAV.entity.DockPoint;
import UAV.entity.NeedPoint;
import UAV.entity.Point;
import UAV.entity.WarePoint;

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
			
			double[][] pDis = getPointDis(czPs);
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
	 * 计算points数组中各个点之间的距离。以二维数组形式返回
	 * @param points
	 * @return
	 */
	private double[][] getPointDis(List<Point> points) {
		
		double[][] a = null;
		return a;
	}
	
	
}
