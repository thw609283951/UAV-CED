package UAV.entity;

public class HistoricalPath 
{

	private String ipaddress;
	private int port;
	private double longitude;
	private double latitude;
	public HistoricalPath() 
	{
		// TODO Auto-generated constructor stub
	super();
	}
    public HistoricalPath(String ipaddress,int port,double longitude,double latitude)
    {
    	this.ipaddress=ipaddress;
    	this.port=port;
    	this.longitude=longitude;
    	this.latitude=latitude;
    }
	public String getIpaddress() 
	{
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) 
	{
		this.ipaddress = ipaddress;
	}
	public int getPort() 
	{
		return port;
	}
	public void setPort(int port) 
	{
		this.port = port;
	}
	public double getLongitude() 
	{
		return longitude;
	}
	public void setLongitude(double longitude) 
	{
		this.longitude = longitude;
	}
	public double getLatitude() 
	{
		return latitude;
	}
	public void setLatitude(double latitude) 
	{
		this.latitude = latitude;
	}
}
