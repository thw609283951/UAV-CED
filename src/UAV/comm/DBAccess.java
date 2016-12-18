package UAV.comm;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBAccess {

	private static String password="123456789";
	private static String username="root";
	private static String url="jdbc:mysql://localhost:3306/uavweb";
	
	public static Connection getInstance(){
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn=null;
		try 
		{
			conn=DriverManager.getConnection(url,username,password);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.out.println(url);
			e.printStackTrace();
		}
		
		return conn;
	}
}
