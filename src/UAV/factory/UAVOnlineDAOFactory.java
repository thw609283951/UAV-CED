package UAV.factory;

import UAV.dao.UAVOnlineDAO;
import UAV.daoimpl.UAVOnlineDAOImpl;

public class UAVOnlineDAOFactory {

	public UAVOnlineDAOFactory() 
	{
		
	}

	public static UAVOnlineDAO getInstance()
	{
		return new UAVOnlineDAOImpl();
	}

}
