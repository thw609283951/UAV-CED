package UAV.entity;
import java.util.ArrayList;

import UAV.entity.UavForExpress;
import UAV.entity.DockPoint;


public class Car {
	private float v;//速度
	private int uavCount;//无人机数目
	private ArrayList<UavForExpress> Uavs;
	
	/*
	 * 派出一架无人机，从数组中选择一个无人机派出，设置无人机状态为“忙”
	 */
	public UavForExpress sendUav(){
		//后续实现
		return this.getUavs().get(0);
	}
	/*
	 * 向车辆添加一个时序路径，就是从当前dock走到下一个dock。
	 */
	public void add_P(double time,DockPoint dock,DockPoint next_dock){
		
	}
	
	public float getV() {
		return v;
	}
	public void setV(float v) {
		this.v = v;
	}
	public int getUavCount() {
		return uavCount;
	}
	public void setUavCount(int uavCount) {
		this.uavCount = uavCount;
	}

	public ArrayList<UavForExpress> getUavs() {
		return Uavs;
	}

	public void setUavs(ArrayList<UavForExpress> uavs) {
		Uavs = uavs;
	}

	
	
	

}
