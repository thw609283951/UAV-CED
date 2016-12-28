package UAV.entity;
// default package

import java.util.ArrayList;
import java.util.List;

import UAV.entity.DockPoint;
import UAV.entity.Car;
/**
 * ChildZone entity. @author MyEclipse Persistence Tools
 */

public class ChildZone implements java.io.Serializable {

	// Fields
	private Integer id;//子区域的id，逻辑主键
    private Integer wrid;//子区域所包含的仓库点的id，因为仓库点对子区域是一对多的关系，所以在ChildZone中添加此字段
	private ArrayList<DockPoint> DockPoint_arr;//保存所有的停靠点
	private Car car;//保存负责车辆

	// Constructors

	/** default constructor */
	public ChildZone() {
	}


	public ChildZone(Integer id, Integer wrid) {
		super();
		this.id = id;
		this.wrid = wrid;
	}


	// Property accessors


	/*
	 * 获取下一个停靠点
	 */
	public DockPoint get_next_dock(DockPoint p){
		for(int i=0;i<this.DockPoint_arr.size();i++)
		{
			if (this.DockPoint_arr.get(i) == p && i!= DockPoint_arr.size()-1){
				return DockPoint_arr.get(i+1);
			}
			else if (i == DockPoint_arr.size()-1){
				return null;
			}
		}
		return null;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getWrid() {
		return wrid;
	}

	public void setWrid(Integer wrid) {
		this.wrid = wrid;
	}


	public Car getCar() {
		return car;
	}


	public void setCar(Car car) {
		this.car = car;
	}


	public ArrayList<DockPoint> getDockPoint_arr() {
		return DockPoint_arr;
	}


	public void setDockPoint_arr(ArrayList<DockPoint> dockPoint_arr) {
		DockPoint_arr = dockPoint_arr;
	}



}