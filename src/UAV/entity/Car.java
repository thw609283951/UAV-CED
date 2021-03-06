package UAV.entity;
import java.util.ArrayList;

import UAV.entity.UavForExpress;
import UAV.entity.DockPoint;
import UAV.test.carPathArrangeTest;


public class Car {
	private Integer Id;
	private ArrayList<UavForExpress> Uavs;
    private ArrayList<ArrayList<Double>> P = new ArrayList<ArrayList<Double>>();//时序路径
    public static int uavsInEveryCar = 4;     //每辆车上的无人机数量
    public static double carV = 10;            //快递车速，km/h
	
	/*
	 * 派出一架无人机，从数组中选择一个无人机派出，设置无人机状态为“忙”
	 */
	public UavForExpress sendUav(){
		for (UavForExpress uav : Uavs){
			if (!uav.getisSended()){
				uav.setisSended(true);
				return uav;
			}
		}
		return null;
	}
	
	/*
	 * 向车辆添加一个时序路径，就是走到当前dock。
	 */
	public void addP(Double time,Point dock){
		ArrayList<Double> tmp_arr = new ArrayList<Double>();
		tmp_arr.add(time);
		tmp_arr.add(dock.getLongitude());
		tmp_arr.add(dock.getLatitude());
		getP().add(tmp_arr);
		for(UavForExpress uav : Uavs){
			uav.addP(time,dock);
		}
	}
	
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	public int getUavCount() {
		return Uavs.size();
	}


	public ArrayList<UavForExpress> getUavs() {
		return Uavs;
	}

	public void setUavs(ArrayList<UavForExpress> uavs) {
		Uavs = uavs;
	}
	public void freeUavs() {
		// TODO Auto-generated method stub
		for(UavForExpress uav : Uavs){
			uav.setisSended(false);
		}
	}
	public ArrayList<ArrayList<Double>> getP() {
		return P;
	}
	public void setP(ArrayList<ArrayList<Double>> p) {
		P = p;
	}
	public int getUavsInEveryCar() {
		return uavsInEveryCar;
	}
	public void setUavsInEveryCar(int uavsInEveryCar) {
		this.uavsInEveryCar = uavsInEveryCar;
	}

}
