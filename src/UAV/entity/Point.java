package UAV.entity;

public abstract class Point {
	private Integer id;
	private Double longitude;//经度
	private Double latitude;//纬度
//	private boolean isKey;//判断是否是核心点
//	private boolean isClassed;//判断是否已经分类
//	private String name;
	
	
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
//	public Point(double longitude,double latitude){
//		this.longitude=longitude;
//		this.latitude=latitude;
//	}
//	public Point(String str){//打印分类结果
//		String[] p=str.split(",");
//		this.longitude=Double.parseDouble(p[0]);
//		this.latitude=Double.parseDouble(p[1]);
//		this.name=p[2];
//	}
//	public String print(){
//		return "<"+this.longitude+","+this.latitude+"> 地点:"+this.name;
//	}
	
}
