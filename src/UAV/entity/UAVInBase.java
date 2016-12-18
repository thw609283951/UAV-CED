package UAV.entity;

public class UAVInBase {

	private String ipaddress;
	private int port;
	private double longitude;
	private double latitude;
	private double elevation;
	private double height;
	private double velocity;
	private double powerconsumption;
	private double sumpower;
	private int remainingpower;
	private String version;
	//可以走的距离,以米做单位
	private double gotodistance;
	private double movetime;
	private boolean canmove;
	private boolean inbase;
	public double getMovetime() {
		return movetime;
	}
	public void setMovetime(double movetime) {
		this.movetime = this.movetime+movetime;
	}
	public UAVInBase() 
	{
	super();
	}
    public UAVInBase(String ipaddress,int port,double longitude,double latitude,
    		double elevation,double height,double velocity,double powerconsumption,
    		double sumpower,int remainingpower,String version)
    {
    	this.ipaddress=ipaddress;
    	this.port=port;
    	this.longitude=longitude;
    	this.latitude=latitude;
    	this.elevation=elevation;
    	this.height=height;
    	this.velocity=velocity;
    	this.powerconsumption=powerconsumption;
    	this.sumpower=sumpower;
    	this.remainingpower=remainingpower;
    	this.version=version;
    	
    	this.gotodistance=(sumpower/powerconsumption)*1000;
    	this.movetime=0;
    	this.canmove=true;
    	this.inbase=true;
    }
    
	public boolean isInbase() {
		return inbase;
	}
	public void setInbase(boolean inbase) {
		this.inbase = inbase;
	}
	public boolean isCanmove() {
		return canmove;
	}
	public void setCanmove(boolean canmove) {
		this.canmove = canmove;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getElevation() {
		return elevation;
	}
	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getVelocity() {
		return velocity;
	}
	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}
	public double getPowerconsumption() {
		return powerconsumption;
	}
	public void setPowerconsumption(double powerconsumption) {
		this.powerconsumption = powerconsumption;
	}
	public double getSumpower() {
		return sumpower;
	}
	public void setSumpower(double sumpower) {
		this.sumpower = sumpower;
	}
	public int getRemainingpower() {
		return remainingpower;
	}
	public void setRemainingpower(int remainingpower) {
		this.remainingpower = remainingpower;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public double getGotodistance() {
		return gotodistance;
	}
	public void setGotodistance(double gotodistance) {
		this.gotodistance = this.gotodistance-gotodistance;
	}
	public void resetGotodistance(){
		this.gotodistance=(this.sumpower/this.powerconsumption)*1000;
	}

}
