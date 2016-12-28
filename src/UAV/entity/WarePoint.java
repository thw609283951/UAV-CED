package UAV.entity;
// default package

/**
 * WarePoint entity. @author MyEclipse Persistence Tools
 * 仓库点
 */

public class WarePoint extends Point implements java.io.Serializable {

	public WarePoint() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WarePoint(Integer id, Double longitude, Double latitude){
		super(id, longitude, latitude);
	}
	
	

}