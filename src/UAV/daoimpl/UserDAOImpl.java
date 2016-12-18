package UAV.daoimpl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import UAV.comm.DBAccess;
import UAV.dao.UserDAO;

import UAV.entity.User;

public class UserDAOImpl implements UserDAO{

	public User searchUserForLogin(String account,String password) {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from user where name='"+account+"' ");
			sql.append(password!=null&&!password.isEmpty()?"and password='"+password+"' ":"");
			sql.append(";");
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			User user=null;
			if(rs.next())
			{
				user=new User(rs.getString("Name"), rs.getString("password"));
				return user;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
