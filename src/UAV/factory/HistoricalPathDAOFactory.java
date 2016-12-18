package UAV.factory;

import UAV.dao.HistoricalPathDAO;
import UAV.daoimpl.HistoricalPathDAOImpl;

public class HistoricalPathDAOFactory 
{
	public HistoricalPathDAOFactory() 
	{
		// TODO Auto-generated constructor stub
	}
	public static HistoricalPathDAO getInstance()
	{
		return new HistoricalPathDAOImpl();
	}
}
