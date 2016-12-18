package UAV.comm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.Time;

public class DateAndTime 
{
	public static java.util.Date GetsqlDateFromutilDate(Date utildate)
	{
		if(utildate!=null)
		{
			return new java.util.Date(utildate.getTime());
		}
		else
		{
			return null;
		}
		
	}	
	public static java.sql.Date getSqlDatefromUtilDate(java.util.Date utilDate)
	{
		if(utilDate!=null)
		{
			return new java.sql.Date(utilDate.getTime());
		}
		else
		{
			return null;
		}
		
	}
	public static java.sql.Time GetsqlTime (java.util.Date date)
	{
		
		if(date!=null)
		{
			java.sql.Time time;
			time=Time.valueOf(new SimpleDateFormat("HH:mm:ss").format(date));
			return time;
		}
		else
		{
			return null;
		}
		
	}
}
