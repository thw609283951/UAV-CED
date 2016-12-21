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
	
	
	
	
	public NeedPoint() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	public NeedPoint(Integer group, Integer amount, Timestamp deadline) {
		super();
		this.group = group;
		this.amount = amount;
		this.deadline = deadline;
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

	

}