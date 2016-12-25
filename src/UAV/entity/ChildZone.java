package UAV.entity;
// default package

/**
 * ChildZone entity. @author MyEclipse Persistence Tools
 * 子区域
 */

public class ChildZone implements java.io.Serializable {

	// Fields

	private Integer id;//子区域的id，逻辑主键
	private Integer wrid;//子区域所包含的仓库点的id，因为仓库点对子区域是一对多的关系，所以在ChildZone中添加此字段
	
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