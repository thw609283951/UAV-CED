package UAV.factory;

import UAV.dao.UAVInBaseDAO;
import UAV.daoimpl.UAVInBaseDAOImpl;

public class UAVInBaseDAOFactory {
	public UAVInBaseDAOFactory() 
	{
		
	}

	public static UAVInBaseDAO getInstance()
	{
		return new UAVInBaseDAOImpl();
	}

}
