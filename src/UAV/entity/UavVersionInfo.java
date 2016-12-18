package UAV.entity;
// default package

/**
 * UavVersionInfo entity. @author MyEclipse Persistence Tools
 */

public class UavVersionInfo implements java.io.Serializable {

	// Fields

	private String version;
	private Double sumpower;
	private Integer maxexpramount;
	private Double velocity;
	private Double powerconsumption;
	private Integer powerfulltime;

	// Constructors

	/** default constructor */
	public UavVersionInfo() {
	}

	/** full constructor */
	public UavVersionInfo(String version, Double sumpower,
			Integer maxexpramount, Double velocity, Double powerconsumption,
			Integer powerfulltime) {
		this.version = version;
		this.sumpower = sumpower;
		this.maxexpramount = maxexpramount;
		this.velocity = velocity;
		this.powerconsumption = powerconsumption;
		this.powerfulltime = powerfulltime;
	}

	// Property accessors

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Double getSumpower() {
		return this.sumpower;
	}

	public void setSumpower(Double sumpower) {
		this.sumpower = sumpower;
	}

	public Integer getMaxexpramount() {
		return this.maxexpramount;
	}

	public void setMaxexpramount(Integer maxexpramount) {
		this.maxexpramount = maxexpramount;
	}

	public Double getVelocity() {
		return this.velocity;
	}

	public void setVelocity(Double velocity) {
		this.velocity = velocity;
	}

	public Double getPowerconsumption() {
		return this.powerconsumption;
	}

	public void setPowerconsumption(Double powerconsumption) {
		this.powerconsumption = powerconsumption;
	}

	public Integer getPowerfulltime() {
		return this.powerfulltime;
	}

	public void setPowerfulltime(Integer powerfulltime) {
		this.powerfulltime = powerfulltime;
	}

}