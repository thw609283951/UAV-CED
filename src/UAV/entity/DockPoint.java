package UAV.entity;
// default package

import UAV.entity.NeedPoint;
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
	private NeedPoint NeedPoint_arr[];
	
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
	public NeedPoint[] getNeedPoint_arr() {
		return NeedPoint_arr;
	}
	public void setNeedPoint_arr(NeedPoint needPoint_arr[]) {
		NeedPoint_arr = needPoint_arr;
	}

	

}