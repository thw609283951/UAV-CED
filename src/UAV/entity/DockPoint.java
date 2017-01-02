package UAV.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// default package

/**
 * DockPoint entity. @author MyEclipse Persistence Tools
 */

public class DockPoint extends Point implements java.io.Serializable {

	// Fields
	private Integer czid;//子区域
	//private Integer id;
	private Integer group;//没用了。。。。
	private Integer wrid;//仓库点
	private Boolean selected;//是否是被选择了的停靠点
	private ArrayList<NeedPoint> NeedPoint_arr = new ArrayList<NeedPoint>();//保存这个停靠点所有的需求点

	private boolean isKey;//判断是否是核心点
	private boolean isClassed;//判断是否已经分类
	private String name;//显示点的名称
//	private Integer id;//点的ID
	private int idnumber=0;
	


	public int getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(int idnumber) {
		this.idnumber = idnumber;
	}
	public Integer getWrid() {
		return wrid;
	}
	public void setWrid(Integer wrid) {
		this.wrid = wrid;
	}
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
	public boolean isKey() {
		return isKey;
	}
	public void setKey(boolean isKey) {
		this.isKey = isKey;
	}
	public boolean isClassed() {
		return isClassed;
	}
	public void setClassed(boolean isClassed) {
		this.isClassed = isClassed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


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
	public DockPoint(String str,int id){//打印分类结果
		String[] p=str.split(",");
		this.setLongitude(Double.parseDouble(p[0]));
		this.setLatitude(Double.parseDouble(p[1]));
		this.name=p[2];
//		this.id=id;
	}
	public String print(){
		return "<"+this.getLongitude()+","+this.getLatitude()+"> 地点:"+this.getName()+" id:"+this.getId();
	}
	public ArrayList<NeedPoint> getNeedPoint_arr() {
		return NeedPoint_arr;
	}
	public void setNeedPoint_arr(ArrayList<NeedPoint> needPoint_arr) {
		NeedPoint_arr = needPoint_arr;
	}

	@Override
	public String toString() {
		return super.toString() + "DockPoint [czid=" + czid + ", selected=" + selected
				+ ", NeedPoint_arr=" + NeedPoint_arr + "]";
	}

//    @Override
//    public String toString() {
//        return super.toString() + "DockPoint [czid=" + czid + ", selected=" + selected
//                + ", NeedPoint_arr=" + Arrays.toString(NeedPoint_arr) + "]";
//    }
	


}