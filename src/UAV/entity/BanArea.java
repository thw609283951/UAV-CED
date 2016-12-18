package UAV.entity;

public class BanArea 
{
	private double  longitude;
    private double  latitude;
    private double  radius;
    private double northeastlongitude;
    private double northeastlatitude;
    private double southwestlongitude;
    private double southwestlatitude;
    
    public BanArea()
    {
   	 super();
    }
    
    public BanArea(double  longitude,double  latitude,double  radius,double northeastlongitude, double northeastlatitude, double southwestlongitude,double southwestlatitude)
    {
   	 super();
   	 this.longitude=longitude;
   	 this.latitude=latitude;
   	 this.radius=radius;
   	 this.northeastlongitude=northeastlongitude;
   	 this.northeastlatitude=northeastlatitude;
   	 this.southwestlongitude=southwestlongitude;
   	 this.southwestlatitude=southwestlatitude;
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

	public double getRadius() 
	{
		return radius;
	}

	public void setRadius(double radius) 
	{
		this.radius = radius;
	}

	public double getNortheastlongitude() {
		return northeastlongitude;
	}

	public void setNortheastlongitude(double northeastlongitude) {
		this.northeastlongitude = northeastlongitude;
	}

	public double getNortheastlatitude() {
		return northeastlatitude;
	}

	public void setNortheastlatitude(double northeastlatitude) {
		this.northeastlatitude = northeastlatitude;
	}

	public double getSouthwestlongitude() {
		return southwestlongitude;
	}

	public void setSouthwestlongitude(double southwestlongitude) {
		this.southwestlongitude = southwestlongitude;
	}

	public double getSouthwestlatitude() {
		return southwestlatitude;
	}

	public void setSouthwestlatitude(double southwestlatitude) {
		this.southwestlatitude = southwestlatitude;
	}
	
}
