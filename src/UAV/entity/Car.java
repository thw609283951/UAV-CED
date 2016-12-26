package UAV.entity;
import java.util.ArrayList;

import UAV.entity.UAVInBase;


public class Car {
	private float v;//速度
	private int uavCount;//无人机数目
	private ArrayList<UAVInBase> Uavs;
	
	/*
	 * 派出一架无人机，从数组中选择一个无人机派出，设置无人机状态为“忙”
	 */
	public UAVInBase sendUav(){
		//后续实现
		return this.getUavs().get(0);
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

	public ArrayList<UAVInBase> getUavs() {
		return Uavs;
	}

	public void setUavs(ArrayList<UAVInBase> uavs) {
		Uavs = uavs;
	}

	
	
	

}
