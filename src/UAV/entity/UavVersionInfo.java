package UAV.entity;
// default package

/**
 * UavVersionInfo entity. @author MyEclipse Persistence Tools
 * 同型号无人机的信息
 */

public class UavVersionInfo implements java.io.Serializable {

	// Fields

	private String version;//型号
	private Double sumpower;//总电量
	private Integer maxexpramount;//该型号无人机最大的载货件数
	private Double velocity;//速度
	private Double powerconsumption;//消耗电量的速度
	private Integer powerfulltime;//充电充满的时间
	/*	下面是学长给出的数据库字段的设计
	sumpower	Double[(6,2)]	Not null	总电量，单位是kj
	velocity	Double[(6,2)]	Not null	无人机的速度，单位为km/h
	powerconsumption	Double[(6,2)]	Not null	无人机的功率，即耗电量，单位为kj/km。小数点后保留两位小数。
	remainingpower	Int	Not null	无人机电量剩余，单位为%.
*/

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