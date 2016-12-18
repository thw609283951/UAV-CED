package UAV.entity;
// default package

/**
 * UavForExpress entity. @author MyEclipse Persistence Tools
 */

public class UavForExpress implements java.io.Serializable {

	// Fields

	private Integer id;
	private String version;
	private Double longitude;
	private Double latitude;
	private Integer remainingpower;
	private Integer expressamount;

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

}