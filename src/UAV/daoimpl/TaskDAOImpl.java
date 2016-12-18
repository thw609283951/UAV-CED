package UAV.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import UAV.comm.DBAccess;
import UAV.dao.TaskDAO;
import UAV.entity.Page;
import UAV.entity.TaskPoint;

public class TaskDAOImpl implements TaskDAO{

	@Override
	public boolean addTaskPoint(TaskPoint taskPoint) {
		try {
			Connection conn = DBAccess.getInstance();
			String sql ;
			PreparedStatement pstmt;
			sql = "insert into task(longitude,latitude)values(?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1,taskPoint.getLongitude());
			pstmt.setDouble(2, taskPoint.getLatitude());
			int count = pstmt.executeUpdate();
			if (count > 0) 
			{
				return true;
			} 
			else 
			{
				return false;
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<TaskPoint> searchAllTaskPoint() 
	{
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from task order by longitude;");			
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<TaskPoint> list=new ArrayList<TaskPoint>();
			while(rs.next()){
				list.add(new TaskPoint(rs.getDouble("longitude"), rs.getDouble("latitude")));
			}
			return list;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public int searchTaskPointTotal() 
	{
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select  count(*) from task;");
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());			
			if(rs.next())
			{
				return rs.getInt(1);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	@Override
	public boolean deleteTaskPoint(double longitude, double latitude) {
		try {
			Connection conn=DBAccess.getInstance();
			String sql="delete from task where longitude=? and latitude=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);								
			pstmt.setDouble(1,longitude);
			pstmt.setDouble(2,latitude);
			int count=pstmt.executeUpdate();
			if(count>0)
			{
				return true;
			}
			else
			{
				return false;
			}					
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
