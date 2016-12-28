package UAV.entity;

public class Point {
	private Integer id;
	private Double longitude;//经度
	private Double latitude;//纬度
//	private boolean isKey;//判断是否是核心点
//	private boolean isClassed;//判断是否已经分类
//	private String name;
	
	
	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Point(Integer id, Double longitude, Double latitude) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	@Override
	public String toString() {
		return "Point [id=" + id + ", longitude=" + longitude + ", latitude="
				+ latitude + "]";
	}

}
