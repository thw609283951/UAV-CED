package UAV.entity;

public class TaskPoint {

	 private double  longitude;
	    private double  latitude;
	    
	    public TaskPoint()
	    {
	   	 super();
	    }
	    
	    public TaskPoint(double longitude,double latitude)
	    {
	   	 super();
	   	 this.longitude=longitude;
	   	 this.latitude=latitude;
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

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
}
