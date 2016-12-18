package UAV.dao;

import UAV.entity.User;

public interface UserDAO 
{
	public User searchUserForLogin(String account,String password);
}
