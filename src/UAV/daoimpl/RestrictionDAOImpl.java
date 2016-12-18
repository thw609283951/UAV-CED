package UAV.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import UAV.comm.DBAccess;
import UAV.dao.RestrictionDAO;
import UAV.entity.Page;
import UAV.entity.Restriction;

public class RestrictionDAOImpl implements RestrictionDAO{

	public boolean addRestriction(Restriction restriction)
	{
		try {
			Connection conn = DBAccess.getInstance();
			String sql ;
			PreparedStatement pstmt;
			sql = "insert into res(version,restrictheight,restrictremainingpower)values(?,?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,restriction.getVersion());
			pstmt.setDouble(2, restriction.getHeight());
			pstmt.setInt(3, restriction.getRemainingpower());
			int count = pstmt.executeUpdate();
			if (count > 0) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public boolean modifyRestriction(Restriction restriction)
	{
		try {
			Connection conn=DBAccess.getInstance();
			String sql="update res set restrictheight=?,restrictremainingpower=? where version=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);
					
			pstmt.setDouble(1, restriction.getHeight());		
			pstmt.setInt(2, restriction.getRemainingpower());
			pstmt.setString(3, restriction.getVersion());
			int count=pstmt.executeUpdate();
			if(count>0){
				return true;
			}else{
				return false;
			}						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public List<Restriction> searchAllRestriction()
	{
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from res;");			
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<Restriction> list=new ArrayList<Restriction>();
			while(rs.next()){
				list.add(new Restriction(rs.getString("version"), rs.getDouble("restrictheight"), rs.getInt("restrictremainingpower")));
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public Restriction searchRestriction(String version)
	{
		try {
			Connection conn=DBAccess.getInstance();
			String sql="select * from res  where version=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);			
			pstmt.setString(1, version);					
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()){
			return	new Restriction(rs.getString("version"), rs.getDouble("restrictheight"), rs.getInt("restrictremainingpower"));
			}				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    	return null;
	}
	public int searchRestrictionTotal()
	{
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select  count(*) from res;");
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());			
			if(rs.next()){
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
	public boolean deleteRestriction(String version)
	{
		// TODO Auto-generated method stub
				try {
					Connection conn=DBAccess.getInstance();
					String sql="delete from res where version=?;";
					PreparedStatement pstmt=conn.prepareStatement(sql);								
					pstmt.setString(1,  version);									
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
	public List<Restriction> searchRestrictionLike(Page page)
	{
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from res ");				
			sql.append("limit "+page.getBeginIndex()+","+page.getEveryPage());
			sql.append(";");
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<Restriction> list=new ArrayList<Restriction>();
			while(rs.next())
			{
				list.add(new Restriction(rs.getString("version"), rs.getDouble("restrictheight"), rs.getInt("restrictremainingpower")));
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
}
