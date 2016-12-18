package UAV.factory;

import UAV.dao.ZoneDAO;
import UAV.daoimpl.ZoneDAOImpl;

public class ZoneDAOFactory 
{

	public ZoneDAOFactory() 
	{
		
	}
	public static ZoneDAO getInstance()
	{
		return new ZoneDAOImpl();
	}
}
