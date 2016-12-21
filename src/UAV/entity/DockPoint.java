package UAV.entity;
// default package

/**
 * DockPoint entity. @author MyEclipse Persistence Tools
 */

public class DockPoint extends Point implements java.io.Serializable {

	// Fields
	private static Integer group;//代表停靠点的子区域编号
	private Boolean selected;//判断停靠点是否需要停靠
	private boolean isKey;//判断是否是核心点
	private boolean isClassed;//判断是否已经分类
	private String name;//显示点的名称
	
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
	public DockPoint(String str){//打印分类结果
		String[] p=str.split(",");
		this.setLongitude(Double.parseDouble(p[0]));
		this.setLatitude(Double.parseDouble(p[1]));
		this.name=p[2];
	}
	public String print(){
		return "<"+this.getLongitude()+","+this.getLatitude()+"> 地点:"+this.name;
	}

}