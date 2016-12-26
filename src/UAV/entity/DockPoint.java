package UAV.entity;
// default package

/**
 * DockPoint entity. @author MyEclipse Persistence Tools
 * 停靠点
 */

public class DockPoint extends Point implements java.io.Serializable {

	// Fields
	private Integer czid;
	//private Integer id;
	private Integer group;//没用了。。。。
	//private Double longitude;
	//private Double latitude;
	private Boolean selected;//是否是被选择了的停靠点
	public DockPoint() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DockPoint(Integer id, Integer czid, Integer group, Double longitude, Double latitude, Boolean selected) {
		super(id, longitude, latitude);
		this.czid = czid;
		this.group = group;
		this.selected = selected;
	}
	
	
	public Integer getCzid() {
		return czid;
	}
	public void setCzid(Integer czid) {
		this.czid = czid;
	}
	public Integer getGroup() {
		return group;
	}
	public void setGroup(Integer group) {
		this.group = group;
	}
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	

}