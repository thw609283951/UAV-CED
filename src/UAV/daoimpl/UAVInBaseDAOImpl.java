package UAV.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import UAV.comm.DBAccess;
import UAV.dao.UAVInBaseDAO;
import UAV.entity.UAVInBase;

public class UAVInBaseDAOImpl implements UAVInBaseDAO{

	public UAVInBaseDAOImpl() {
	}

	@Override
	public boolean addUAVInBase(UAVInBase uavinbase) {
		try {
			Connection conn = DBAccess.getInstance();
			String sql ;
			PreparedStatement pstmt;
			sql = "insert into uavinbase(ipaddress,port,longitude,latitude,elevation,height,velocity,powerconsumption,sumpower,remainingpower,version)values(?,?,?,?,?,?,?,?,?,?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,uavinbase.getIpaddress());
			pstmt.setInt(2, uavinbase.getPort());
			pstmt.setDouble(3, uavinbase.getLongitude());
			pstmt.setDouble(4, uavinbase.getLatitude());
			pstmt.setDouble(5, uavinbase.getElevation());
			pstmt.setDouble(6, uavinbase.getHeight());
			pstmt.setDouble(7, uavinbase.getVelocity());
			pstmt.setDouble(8, uavinbase.getPowerconsumption());
			pstmt.setDouble(9, uavinbase.getSumpower());
			pstmt.setDouble(10, uavinbase.getRemainingpower());
			pstmt.setString(11, uavinbase.getVersion());
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
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean modifyUAVInBase(UAVInBase uavinbase) 
	{
		try {
			Connection conn=DBAccess.getInstance();
			String sql="update uavinbase set longitude=?,latitude=?,elevation =?,height=?,velocity=?  where ipaddress=? and port=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setDouble(1, uavinbase.getLongitude());
			pstmt.setDouble(2, uavinbase.getLatitude());
			pstmt.setDouble(3, uavinbase.getElevation());
			pstmt.setDouble(4, uavinbase.getHeight());
			pstmt.setDouble(5, uavinbase.getVelocity());
			pstmt.setString(6, uavinbase.getIpaddress());
			pstmt.setInt(7, uavinbase.getPort());
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
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<UAVInBase> searchAllUAVInBase() {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from uavinbase order by id;");			
			Statement stmt=conn.createStatement();
			//System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<UAVInBase> list=new ArrayList<UAVInBase>();
			while(rs.next())
			{
				list.add(new UAVInBase(rs.getString("ipaddress"), rs.getInt("port"), rs.getDouble("longitude"), rs.getDouble("latitude"), rs.getDouble("elevation"), rs.getDouble("height"), rs.getDouble("velocity"), rs.getDouble("powerconsumption"), rs.getDouble("sumpower"), rs.getInt("remainingpower"), rs.getString("version")));
			}
			return list;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
    public UAVInBase searchUAVInBase(UAVInBase uavinbase)
    {
    	try {
			Connection conn=DBAccess.getInstance();
			String sql="select * from uavinbase  where ipaddress=? and port=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, uavinbase.getIpaddress());
			pstmt.setInt(2, uavinbase.getPort());
			
			ResultSet rs=pstmt.executeQuery();
			//System.out.println(uavinbase.getIpaddress());
			//System.out.println(uavinbase.getPort());
			//System.out.println(sql);
			while(rs.next())
			{
			return	new UAVInBase(rs.getString("ipaddress"), rs.getInt("port"), rs.getDouble("longitude"), rs.getDouble("latitude"), rs.getDouble("elevation"), rs.getDouble("height"), rs.getDouble("velocity"), rs.getDouble("powerconsumption"), rs.getDouble("sumpower"),rs.getInt("remainingpower"), rs.getString("version"));
			}				
		} 
    	catch (SQLException e) 
    	{
			e.printStackTrace();
			return null;
		}
    	return null;
    }
	@Override
	public int searchUAVInBaseTotal() {
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select  count(*) from uavinbase;");
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
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	@Override
	public boolean deleteUAVInBase(String ipaddress, int port) {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			String sql="delete from uavinbase where ipaddress=? and port=?;";
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
	public boolean modifyUAVInBase(String ipaddress, int port,double longitude,double latitude) {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			String sql="update uavinbase set longitude=?,latitude=? where ipaddress=? and port=?;";
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
