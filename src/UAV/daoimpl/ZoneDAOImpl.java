package UAV.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import UAV.comm.DBAccess;
import UAV.dao.ZoneDAO;
import UAV.entity.Page;
import UAV.entity.ZonePoint;

public class ZoneDAOImpl implements ZoneDAO {

	@Override
	public boolean addZonePoint(ZonePoint zonePoint) {
		// TODO Auto-generated method stub
		try {
			Connection conn = DBAccess.getInstance();
			String sql ;
			PreparedStatement pstmt;
			sql = "insert into zone(longitude,latitude)values(?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1,zonePoint.getLongitude());
			pstmt.setDouble(2, zonePoint.getLatitude());
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<ZonePoint> searchAllZonePoint() 
	{
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from zone order by inde;");			
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<ZonePoint> list=new ArrayList<ZonePoint>();
			while(rs.next()){
				list.add(new ZonePoint(rs.getDouble("longitude"), rs.getDouble("latitude")));
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

	@Override
	public List<ZonePoint> searchZonePointLike(Page page) {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from zone order by inde ");				
			sql.append("limit "+page.getBeginIndex()+","+page.getEveryPage());
			sql.append(";");
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<ZonePoint> list=new ArrayList<ZonePoint>();
			while(rs.next())
			{
				list.add(new ZonePoint(rs.getDouble("longitude"), rs.getDouble("latitude")));
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

	@Override
	public int searchZonePointTotal() 
	{
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select  count(*) from zone;");
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());			
			if(rs.next())
			{
				return rs.getInt(1);
			}
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	@Override
	public boolean deleteZonePoint(double longitude, double latitude) {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			String sql="delete from zone where longitude=? and latitude=?;";
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
