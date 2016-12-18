package UAV.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import UAV.comm.DBAccess;
import UAV.dao.BanAreaDAO;
import UAV.entity.BanArea;
import UAV.entity.Page;
public class BanAreaDAOImpl implements BanAreaDAO
{

	@Override
	public boolean addBanArea(BanArea banarea) 
	{
		// TODO Auto-generated method stub
		try {
			Connection conn = DBAccess.getInstance();
			String sql ;
			PreparedStatement pstmt;
			sql = "insert into banarea(longitude,latitude,radius,northeastlongitude,northeastlatitude,southwestlongitude,southwestlatitude)values(?,?,?,?,?,?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1,banarea.getLongitude());
			pstmt.setDouble(2,banarea.getLatitude() );
			pstmt.setDouble(3,banarea.getRadius());
			pstmt.setDouble(4,banarea.getNortheastlongitude());
			pstmt.setDouble(5,banarea.getNortheastlatitude());
			pstmt.setDouble(6,banarea.getSouthwestlongitude());
			pstmt.setDouble(7,banarea.getSouthwestlatitude());
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
	public boolean modifyBanArea(BanArea banarea) 
	{
		// TODO Auto-generated method stub
		try 
		{
			Connection conn=DBAccess.getInstance();
			String sql="update banarea set radius=?,northeastlongitude=?, northeastlatitude=?,southwestlongitude=?,southwestlatitude=? where longitude=? and latitude=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);					
			pstmt.setDouble(1,banarea.getRadius());		
			pstmt.setDouble(2,banarea.getNortheastlongitude());
			pstmt.setDouble(3,banarea.getNortheastlatitude());
			pstmt.setDouble(4,banarea.getSouthwestlongitude());
			pstmt.setDouble(5,banarea.getSouthwestlatitude());
			pstmt.setDouble(6,banarea.getLongitude());
			pstmt.setDouble(7,banarea.getLatitude());
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
	public List<BanArea> searchAllBanArea() 
	{
		// TODO Auto-generated method stub
		try 
		{
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from banarea;");			
			Statement stmt=conn.createStatement();
			//System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<BanArea> list=new ArrayList<BanArea>();
			while(rs.next())
			{
				list.add(new BanArea(rs.getDouble("longitude"),rs.getDouble("latitude"),rs.getDouble("radius"),rs.getDouble("northeastlongitude"),
						rs.getDouble("northeastlatitude"),rs.getDouble("southwestlongitude"),rs.getDouble("southwestlatitude")));
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
	public BanArea searchBanArea(double longitude, double latitude,
			double radius) 
	{
		// TODO Auto-generated method stub
		try 
		{
			Connection conn=DBAccess.getInstance();
			String sql="select * from banarea  where longitude=? and latitude=? and radius=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);			
			pstmt.setDouble(1,longitude);
			pstmt.setDouble(2,latitude);
			pstmt.setDouble(3,radius);
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next())
			{
				return	new BanArea(rs.getDouble("longitude"),rs.getDouble("latitude"),rs.getDouble("radius"),rs.getDouble("northeastlongitude"),
						rs.getDouble("northeastlatitude"),rs.getDouble("southwestlongitude"),rs.getDouble("southwestlatitude"));
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
	public List<BanArea> searchBanAreaLike(Page page) 
	{
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from banarea ");				
			sql.append("limit "+page.getBeginIndex()+","+page.getEveryPage());
			sql.append(";");
			Statement stmt=conn.createStatement();
			//System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<BanArea> list=new ArrayList<BanArea>();
			while(rs.next())
			{
				list.add(new BanArea(rs.getDouble("longitude"),rs.getDouble("latitude"),rs.getDouble("radius"),rs.getDouble("northeastlongitude"),
						rs.getDouble("northeastlatitude"),rs.getDouble("southwestlongitude"),rs.getDouble("southwestlatitude")));
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
	public int searchBanAreaTotal() 
	{
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select  count(*) from banarea;");
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
	public boolean deleteBanArea(double longitude, double latitude) 
	{
		try {
			Connection conn=DBAccess.getInstance();
			String sql="delete from banarea where  longitude=? and latitude=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);								
			pstmt.setDouble(1, longitude);
			pstmt.setDouble(2, latitude);
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
