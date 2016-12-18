package UAV.service;

import UAV.dao.UserDAO;
import UAV.entity.User;
import UAV.factory.UserDAOFactory;

public class LoginService {

	public User login(String name,String password){
		UserDAO ud=UserDAOFactory.getInstance();
		User user=ud.searchUserForLogin(name, password);
		if(user!=null)
		{
			return user;
		}
		return null;
	}	
}
