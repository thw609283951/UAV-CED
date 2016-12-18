package UAV.entity;
// default package

import java.sql.Timestamp;

/**
 * NeedPoint entity. @author MyEclipse Persistence Tools
 */

public class NeedPoint extends Point implements java.io.Serializable {

	// Fields

	
	private Integer group;

	private Integer amount;
	private Timestamp deadline;
	
	
	
	
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