package UAV.factory;

import UAV.dao.RestrictionDAO;
import UAV.daoimpl.RestrictionDAOImpl;

public class RestrictionDAOFactory 
{

	public RestrictionDAOFactory()
	{
		// TODO Auto-generated constructor stub
	}
	public static RestrictionDAO getInstance()
	{
		return new RestrictionDAOImpl();
	}
}
