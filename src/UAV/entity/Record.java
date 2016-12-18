package UAV.entity;

import java.sql.Date;
import java.sql.Time;

public class Record 
{
	private String ipaddress;
	private int port;
	private  Date indate;
	private Time intime;
	private  Date outdate;
	private Time outtime;
	public Record()
	{
		super();
	}
	public Record(String ip,int po,Date id,Time it,Date od,Time ot)
	{
		this.ipaddress=ip;
		this.port=po;
		this.indate=id;
		this.intime=it;
		this.outdate=od;
		this.outtime=ot;
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
	public Date getIndate() 
	{
		return indate;
	}
	public void setIndate(Date indate) 
	{
		this.indate = indate;
	}
	public Time getIntime() 
	{
		return intime;
	}
	public void setIntime(Time intime) 
	{
		this.intime = intime;
	}
	public Date getOutdate() 
	{
		return outdate;
	}
	public void setOutdate(Date outdate) 
	{
		this.outdate = outdate;
	}
	public Time getOuttime() 
	{
		return outtime;
	}
	public void setOuttime(Time outtime) 
	{
		this.outtime = outtime;
	}
}
