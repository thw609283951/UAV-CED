package UAV.entity;
// default package
import UAV.entity.NeedPoint;
/**
 * UavForExpress entity. @author MyEclipse Persistence Tools
 * 无人机信息
 */

public class UavForExpress implements java.io.Serializable {

	// Fields

	private Integer id;
	private String version;//无人机的型号，注意一些同型号无人机的属性被保存到了使用这个字段链接的UavVersionInfo表中
	private Double longitude;//经度
	private Double latitude;//纬度
	private Integer remainingpower;//剩余点亮，按照学长给的文档，单位为%，学长给出的数据库字段说明如下
/*	
	sumpower	Double[(6,2)]	Not null	总电量，单位是kj
	velocity	Double[(6,2)]	Not null	无人机的速度，单位为km/h
	powerconsumption	Double[(6,2)]	Not null	无人机的功率，即耗电量，单位为kj/km。小数点后保留两位小数。
	remainingpower	Int	Not null	无人机电量剩余，单位为%.
*/
	
	private double velocity;//无人机速度
	private Integer expressamount;//无人机当前载有货物的数量（不是最大能载货数量哦）

	// Constructors

	/** default constructor */
	public UavForExpress() {
	}

	/** full constructor */
	public UavForExpress(String version, Double longitude, Double latitude,
			Integer remainingpower, Integer expressamount) {
		this.version = version;
		this.longitude = longitude;
		this.latitude = latitude;
		this.remainingpower = remainingpower;
		this.expressamount = expressamount;
	}

	// Property accessors
	public void add_P(NeedPoint[] l,double time){
		return null;
	}
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getRemainingpower() {
		return this.remainingpower;
	}

	public void setRemainingpower(Integer remainingpower) {
		this.remainingpower = remainingpower;
	}

	public Integer getExpressamount() {
		return this.expressamount;
	}

	public void setExpressamount(Integer expressamount) {
		this.expressamount = expressamount;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

}