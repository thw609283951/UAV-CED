package UAV.daoimpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import UAV.comm.DBAccess;
import UAV.dao.RecordDAO;
import UAV.entity.Page;
import UAV.entity.Record;

public class RecordDAOImpl implements RecordDAO 
{
	@Override
	public boolean addRecord(Record record) 
	{
		// TODO Auto-generated method stub
		try {
			Connection conn = DBAccess.getInstance();
			String sql ;
			PreparedStatement pstmt;
			sql = "insert into  record(ipaddress,port,indate,intime)values(?,?,?,?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,record.getIpaddress());
			pstmt.setInt(2, record.getPort());
			pstmt.setDate(3, record.getIndate());
			pstmt.setTime(4, record.getIntime());
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
	public boolean modifyRecord(Record record) 
	{
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			String sql="update record set outdate=?,outtime=? where ipaddress=? and port=?;";
			PreparedStatement pstmt=conn.prepareStatement(sql);	
			pstmt.setDate(1, record.getOutdate());
			pstmt.setTime(2, record.getOuttime());
			pstmt.setString(3, record.getIpaddress());
			pstmt.setInt(4, record.getPort());
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
	public List<Record> searchAllRecord() 
	{
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from record;");			
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<Record> list=new ArrayList<Record>();
			while(rs.next())
			{
				list.add(new Record(rs.getString("ipaddress"), rs.getInt("port"),rs.getDate("indate"),rs.getTime("intime"),rs.getDate("outdate"),rs.getTime("outtime")));
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
	public List<Record> searchRecordLike(Page page) {
		// TODO Auto-generated method stub
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from record ");				
			sql.append("limit "+page.getBeginIndex()+","+page.getEveryPage());
			sql.append(";");
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			List<Record> list=new ArrayList<Record>();
			while(rs.next())
			{
				list.add(new Record(rs.getString("ipaddress"), rs.getInt("port"),rs.getDate("indate"),rs.getTime("intime"),rs.getDate("outdate"),rs.getTime("outtime")));
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
	public int searchRecordTotal() 
	{
		// TODO Auto-generated method stub
				try {
					Connection conn=DBAccess.getInstance();
					StringBuffer sql=new StringBuffer("select  count(*) from record;");
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
}
