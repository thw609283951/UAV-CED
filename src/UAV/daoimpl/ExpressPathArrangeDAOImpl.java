package UAV.daoimpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import UAV.comm.DBAccess;
import UAV.dao.ExpressPathArrangeDAO;
import UAV.entity.Point;
import UAV.entity.Record;

public class ExpressPathArrangeDAOImpl implements ExpressPathArrangeDAO {
	public List<Point> getTestCzPoints() {
		try {
			Connection conn=DBAccess.getInstance();
			StringBuffer sql=new StringBuffer("select * from dock_point;");			
			Statement stmt=conn.createStatement();
			System.out.println(sql.toString());
			ResultSet rs=stmt.executeQuery(sql.toString());
			//List<Record> list=new ArrayList<Record>();
			List<Point> ps = new ArrayList<Point>();
			while(rs.next())
			{
				//Record(rs.getString("ipaddress"), rs.getInt("port"),rs.getDate("indate"),rs.getTime("intime"),rs.getDate("outdate"),rs.getTime("outtime"))
				ps.add(new Point(rs.getInt("id"), rs.getDouble("longitude"), rs.getDouble("latitude")));
			}
			
			return ps;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
