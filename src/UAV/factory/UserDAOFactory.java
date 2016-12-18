package UAV.factory;

import UAV.dao.UserDAO;
import UAV.daoimpl.UserDAOImpl;

public class UserDAOFactory 
{

	public UserDAOFactory() 
	{
		// TODO Auto-generated constructor stub
	}
	public static UserDAO getInstance()
	{
		return new UserDAOImpl();
	}
}
