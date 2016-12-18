package UAV.factory;

import UAV.dao.BanAreaDAO;
import UAV.daoimpl.BanAreaDAOImpl;

public class BanAreaDAOFactory 
{

	public BanAreaDAOFactory() 
	{
		// TODO Auto-generated constructor stub
	}
	public static BanAreaDAO getInstance()
	{
		return new BanAreaDAOImpl();
	}
}
