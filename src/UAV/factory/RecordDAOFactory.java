package UAV.factory;

import UAV.dao.RecordDAO;
import UAV.daoimpl.RecordDAOImpl;

public class RecordDAOFactory 
{
	public RecordDAOFactory() 
	{
		// TODO Auto-generated constructor stub
	}
	public static RecordDAO getInstance()
	{
		return new RecordDAOImpl();
	}

}
