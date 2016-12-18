package UAV.entity;
// default package

/**
 * DockPoint entity. @author MyEclipse Persistence Tools
 */

public class DockPoint extends Point implements java.io.Serializable {

	// Fields

	//private Integer id;
	private Integer group;
	//private Double longitude;
	//private Double latitude;
	private Boolean selected;
	public DockPoint() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DockPoint(Integer group, Boolean selected) {
		super();
		this.group = group;
		this.selected = selected;
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