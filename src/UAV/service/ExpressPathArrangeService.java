package UAV.service;



import java.util.ArrayList;
import java.util.List;

import UAV.entity.CarPath;
import UAV.entity.ChildZone;

public class ExpressPathArrangeService {

	/**
	 * 路径规划总算法
	 */
	public void pathArrange() {
		pointPreProcess();
		List<ChildZone> childZones = childZonePatition();
		for (ChildZone childZone : childZones) {
			CarPath carPath = carPathArrange(childZone);
			UAVArrange(carPath, childZone);
		}
	}
	
	
	/**
	 * 路径显示
	 * 如果本功能放到pathArrange里面更简单，那就放进去。。
	 */
	public void pathDisplay() {
		
	}
	
	/**
	 * 此子函数为距离计算模块和停靠点选取模块的综合
	 */
	private void pointPreProcess() {
		
	}
	
	/**
	 * 子区域划分模块
	 */
	private List<ChildZone> childZonePatition() {
		
		
		return new ArrayList<ChildZone>();
	}
	
	/**
	 * 解决旅行商问题的模块,返回形式需要再思考一下
	 * @param childZone
	 */
	private CarPath carPathArrange(ChildZone childZone) {
		
		return new CarPath();
	}
	
	/**
	 * 无人机调度模块
	 */
	private void UAVArrange(CarPath carPath, ChildZone childZone) {
		
	}
}
