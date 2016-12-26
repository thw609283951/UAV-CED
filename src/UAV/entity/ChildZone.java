package UAV.entity;
// default package

import UAV.entity.DockPoint;
import UAV.entity.Car;
/**
 * ChildZone entity. @author MyEclipse Persistence Tools
 */

public class ChildZone implements java.io.Serializable {

	// Fields

    private Integer wrid;//子区域所包含的仓库点的id，因为仓库点对子区域是一对多的关系，所以在ChildZone中添加此字段
	private DockPoint DockPoint_arr[];
	private Car car;

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



}