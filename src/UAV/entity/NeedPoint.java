package UAV.entity;
// default package

import java.sql.Timestamp;

/**
 * NeedPoint entity. @author MyEclipse Persistence Tools
 * 需求点
 */

public class NeedPoint extends Point implements java.io.Serializable {

	// Fields

	
	private Integer group;//没用了
	private Integer amount;//需求点需要送快递的数量
	private Timestamp deadline;//需求点的deadline，不考虑了，没时间就不做了
	private Integer dockid;
	private Double dockdis;
	
	public NeedPoint() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	public NeedPoint(Integer id, Integer group, Double longitude, Double latitude, Integer amount, Timestamp deadline, Integer dockid, Double dockdis) {
		super(id, longitude, latitude);
		this.group = group;
		this.amount = amount;
		this.deadline = deadline;
		this.dockid = dockid;
		this.dockdis = dockdis;
	}





	public Integer getGroup() {
		return group;
	}
	public void setGroup(Integer group) {
		this.group = group;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public Timestamp getDeadline() {
		return deadline;
	}
	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}





	public Integer getDockid() {
		return dockid;
	}





	public void setDockid(Integer dockid) {
		this.dockid = dockid;
	}





	public Double getDockdis() {
		return dockdis;
	}





	public void setDockdis(Double dockdis) {
		this.dockdis = dockdis;
	}

	

}