package UAV.entity;

public class Restriction 
{
	private String  version;
    private double  height;
    private int  remainingpower;
    
    public Restriction()
    {
   	 super();
    }
    
    public Restriction(String version,double  height,int  remainingpower)
    {
   	 super();
   	 this.version=version;
   	 this.height=height;
   	 this.remainingpower=remainingpower;
    }

	public String getVersion() 
	{
		return version;
	}

	public void setVersion(String version) 
	{
		this.version = version;
	}

	public double getHeight() 
	{
		return height;
	}

	public void setHeight(double height) 
	{
		this.height = height;
	}

	public int getRemainingpower() 
	{
		return remainingpower;
	}

	public void setRemainingpower(int remainingpower) 
	{
		this.remainingpower = remainingpower;
	}
}
