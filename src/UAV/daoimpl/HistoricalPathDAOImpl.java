package UAV.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import UAV.comm.DBAccess;
import UAV.dao.HistoricalPathDAO;
import UAV.entity.HistoricalPath;

public class HistoricalPathDAOImpl implements HistoricalPathDAO
{

	@Override
	public List<HistoricalPath> seach(String ipaddress, int port) 
	{
		// TODO Auto-generated method stub
		try 
		{
			Connection conn=DBAccess.getInstance();
			String sql="select * from historicalpath where ipaddress=? and port=? order by inde;";			
			PreparedStatement pstmt=conn.prepareStatement(sql);			
			pstmt.setString(1,ipaddress);
			pstmt.setInt(2,port);
			ResultSet rs=pstmt.executeQuery();
			List<HistoricalPath> lohp=new ArrayList<HistoricalPath>();
			while(rs.next())
			{
				lohp.add(new HistoricalPath(rs.getString("ipaddress"),rs.getInt("port"),rs.getDouble("longitude"),rs.getDouble("latitude")));
			}
			return lohp;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean addHistoricalPath(HistoricalPath historicalpath)
	{
		try 
		{
			Connection conn = DBAccess.getInstance();
			String sql ;
			PreparedStatement pstmt;
			sql = "insert into historicalpath(ipaddress,port,longitude,latitude )values(?,?,?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,historicalpath.getIpaddress());
			pstmt.setInt(2, historicalpath.getPort());
			pstmt.setDouble(3, historicalpath.getLongitude());
			pstmt.setDouble(4, historicalpath.getLatitude());
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

}
