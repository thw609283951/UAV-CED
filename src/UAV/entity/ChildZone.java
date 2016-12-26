package UAV.entity;
// default package

import UAV.entity.DockPoint;
import UAV.entity.Car;
/**
 * ChildZone entity. @author MyEclipse Persistence Tools
 */

public class ChildZone implements java.io.Serializable {

	// Fields

	private Integer id;
	private DockPoint DockPoint_arr[];
	private Car car;

	// Constructors

	/** default constructor */
	public ChildZone() {
	}

	// Property accessors
	
	
	/*
	 * 获取下一个停靠点
	 */
	public DockPoint get_next_dock(DockPoint p){
		return null;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DockPoint[] getDockPoint_arr() {
		return DockPoint_arr;
	}

	public void setDockPoint_arr(DockPoint dockPoint_arr[]) {
		DockPoint_arr = dockPoint_arr;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

}