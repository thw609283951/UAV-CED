package UAV.entity;
import java.util.ArrayList;
import java.util.List;
import UAV.service.ExpressPathArrangeService;
import UAV.comm.MapDistance;
// default package
import UAV.entity.NeedPoint;
/**
 * UavForExpress entity. @author MyEclipse Persistence Tools
 * 无人机信息
 */

public class UavForExpress implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer carId;
	private String version;//无人机的型号，注意一些同型号无人机的属性被保存到了使用这个字段链接的UavVersionInfo表中
	private Double longitude;//经度
	private Double latitude;//纬度
	private Integer remainingpower;//剩余点亮，按照学长给的文档，单位为%，学长给出的数据库字段说明如下
	private ArrayList<ArrayList<Double>> P; //时许序列
	
/*	
	sumpower	Double[(6,2)]	Not null	总电量，单位是kj
	velocity	Double[(6,2)]	Not null	无人机的速度，单位为km/h
	powerconsumption	Double[(6,2)]	Not null	无人机的功率，即耗电量，单位为kj/km。小数点后保留两位小数。
	remainingpower	Int	Not null	无人机电量剩余，单位为%.
*/
	
	private double velocity;//无人机速度
	private Integer expressamount;//无人机当前载有货物的数量（不是最大能载货数量哦）
	private Boolean is_sended = false;//记录无人机是否已派出

	// Constructors

	/** default constructor */
	public UavForExpress() {
		this.P=new ArrayList<ArrayList<Double>>();
	}

	/** full constructor */
//	public UavForExpress(String version, Double longitude, Double latitude,
//			Integer remainingpower, Integer expressamount) {
//		this.version = version;
//		this.longitude = longitude;
//		this.latitude = latitude;
//		this.remainingpower = remainingpower;
//		this.expressamount = expressamount;
//		this.P=new ArrayList<ArrayList<Double>>();
//	}

	public UavForExpress(Integer id, Integer carId, String version,
			Double longitude, Double latitude, Integer remainingpower,
			ArrayList<ArrayList<Double>> p, double velocity,
			Integer expressamount) {
		super();
		this.id = id;
		this.carId = carId;
		this.version = version;
		this.longitude = longitude;
		this.latitude = latitude;
		this.remainingpower = remainingpower;
		P = p;
		this.velocity = velocity;
		this.expressamount = expressamount;
	}

	// 根据路径l向无人机添加时序路径
	public void add_P(List<Point> l,double time){
		ArrayList<Double> item;
		double dist = 0;//得到dock和第一个需求点的距离，待完成。
		for (int i=0;i<l.size();i++){
			if (i == l.size()-1){//已经到最后一个需求点，计算需求点到dock的距离
				dist = getDistanceByAir(l.get(i),l.get(0));
			}
			else{//需求点到下一需求点的距离
				dist = getDistanceByAir(l.get(i),l.get(i+1));
			}
			time+=dist / this.velocity;
			item = new ArrayList<Double>();
			item.add(time);//时间
			item.add(l.get(i).getLongitude());//经度
			item.add(l.get(i).getLatitude());//纬度
			this.P.add(item);//添加到路径序列
		}
	}
	// 根据停靠点向无人机添加时序路径
	public void add_P(Double time, Point dock) {
		// TODO Auto-generated method stub
		ArrayList<Double> item = new ArrayList<Double>();
		item.add(time);//时间
		item.add(dock.getLongitude());//经度
		item.add(dock.getLatitude());//纬度
		this.P.add(item);//添加到路径序列
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
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

	public Boolean getIs_sended() {
		return is_sended;
	}

	public void setIs_sended(Boolean is_sended) {
		this.is_sended = is_sended;
	}
	
	private double getDistanceByAir(Point a, Point b) {
		return MapDistance.GetDistance(a.getLongitude(), 
										a.getLatitude(),
										b.getLongitude(),
										b.getLatitude());
	}

}