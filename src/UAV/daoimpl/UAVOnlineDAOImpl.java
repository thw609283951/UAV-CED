package UAV.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import UAV.comm.DBAccess;
import UAV.dao.UAVOnlineDAO;
import UAV.entity.UAVOnline;

public class UAVOnlineDAOImpl implements UAVOnlineDAO {

	public UAVOnlineDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean addUAVOnline(UAVOnline uavonline) {
		// TODO Auto-generated method stub
		try {
			Connection conn = DBAccess.getInstance();
			String sql ;
			PreparedStatement pstmt;
			sql = "insert into uavonline(ipaddress,port,longitude,latitude,elevation,height,velocity,powerconsumption,remainingpower,version)values(?,?,?,?,?,?,?,?,?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,uavonline.getIpaddress());
			pstmt.setInt(2, uavonline.getPort());
			pstmt.setDouble(3, uavonline.getLongitude());
			pstmt.setDouble(4, uavonline.getLatitude());
			pstmt.setDouble(5, uavonline.getElevation());
			pstmt.setDouble(6, uavonline.getHeight());
			pstmt.setDouble(7, uavonline.getVelocity());
			pstmt.setDouble(8, uavonline.getPowerconsumption());
			pstmt.setDouble(9, uavonline.getRemainingpower());
			pstmt.setString(10, uavonline.getVersion());
			int count = pstmt.executeUpdate();
			if (count > 0) 
			{
				return true;
			} 
			else 
			{
				return false;
			}

		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean modifyUAVOnline(UAVOnline uavonline) 
	{
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			String sql="update uavonline set longitude=?,latitude=?,elevation =?,height=?,velocity=?  where ipaddress=? and port=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setDouble(1, uavonline.getLongitude());
			pstmt.setDouble(2, uavonline.getLatitude());
			pstmt.setDouble(3, uavonline.getElevation());
			pstmt.setDouble(4, uavonline.getHeight());
			pstmt.setDouble(5, uavonline.getVelocity());
			pstmt.setString(6, uavonline.getIpaddress());
			pstmt.setInt(7, uavonline.getPort());
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

	@Override
	public List<UAVOnline> searchAllUAVOnline() {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from uavonline;");			
			Statement stmt=conn.createStatement();
			//System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<UAVOnline> list=new ArrayList<UAVOnline>();
			while(rs.next())
			{
				list.add(new UAVOnline(rs.getString("ipaddress"), rs.getInt("port"), rs.getDouble("longitude"), rs.getDouble("latitude"), rs.getDouble("elevation"), rs.getDouble("height"), rs.getDouble("velocity"), rs.getDouble("powerconsumption"), rs.getInt("remainingpower"), rs.getString("version")));
			}
			return list;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
    public UAVOnline searchUAVOnline(UAVOnline uavonline)
    {
    	try {
			Connection conn=DBAccess.getInstance();
			String sql="select * from uavonline  where ipaddress=? and port=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, uavonline.getIpaddress());
			pstmt.setInt(2, uavonline.getPort());
			
			ResultSet rs=pstmt.executeQuery();
			//System.out.println(uavonline.getIpaddress());
			//System.out.println(uavonline.getPort());
			//System.out.println(sql);
			while(rs.next())
			{
			return	new UAVOnline(rs.getString("ipaddress"), rs.getInt("port"), rs.getDouble("longitude"), rs.getDouble("latitude"), rs.getDouble("elevation"), rs.getDouble("height"), rs.getDouble("velocity"), rs.getDouble("powerconsumption"), rs.getInt("remainingpower"), rs.getString("version"));
			}				
		} 
    	catch (SQLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	return null;
    }
	@Override
	public int searchUAVOnlineTotal() {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select  count(*) from uavonline;");
			Statement stmt=conn.createStatement();
			//System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());			
			if(rs.next())
			{
				return rs.getInt(1);
			}
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	@Override
	public boolean deleteUAVOnline(String ipaddress, int port) {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			String sql="delete from uavonline where ipaddress=? and port=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);			
			
			pstmt.setString(1, ipaddress);
			pstmt.setInt(2, port);
			
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

	@Override
	public boolean modifyUAVOnline(String ipaddress, int port,double longitude,double latitude) {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			String sql="update uavonline set longitude=?,latitude=? where ipaddress=? and port=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setDouble(1,longitude);
			pstmt.setDouble(2,latitude);
			pstmt.setString(3, ipaddress);
			pstmt.setInt(4,port);
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
