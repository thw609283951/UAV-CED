package UAV.servlet;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.comm.PolygonPoint;
import UAV.entity.UAVOnline;
import UAV.entity.UAVOnlinePath;
import UAV.entity.ZonePoint;
import UAV.service.UAVOnlineInformationService;
import UAV.service.ZoneInformationService;

public class AreaCoverageServlet extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{

		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		System.out.println("**************AreaCoverageServlet****************");
		
		ZoneInformationService zis=new ZoneInformationService();
		List<ZonePoint> zpl=new ArrayList<ZonePoint>();
		zpl=zis.searchAllZone();
		StringBuffer xml=new StringBuffer("<ZonePathList>");
		DecimalFormat  df2   = new DecimalFormat("#0.000000");
		if(zpl!=null)
		{
			double maxlongitude=zpl.get(0).getLongitude();
			double minlongitude=zpl.get(0).getLongitude();
			double maxlatitude=zpl.get(0).getLatitude();
			double minlatitude=zpl.get(0).getLatitude();	
			for (int i = 1; i < zpl.size(); i++) 
			{
				double i_longitude=zpl.get(i).getLongitude();
				double i_latitude=zpl.get(i).getLatitude();
				if(i_longitude>maxlongitude)
				{
					maxlongitude=i_longitude;
				}
				if(i_longitude<minlongitude)
				{
					minlongitude=i_longitude;
				}
				if(i_latitude>maxlongitude)
				{
					maxlatitude=i_latitude;
				}
				if(i_latitude<minlatitude)
				{
					minlatitude=i_latitude;
				}
			}
			int longtitudesum=10;
			int latitudesum=10;
			double longtitudegap=(maxlongitude-minlongitude)/longtitudesum;
			double latitudegap=(maxlatitude-minlatitude)/latitudesum;
			List<ZonePoint> allzpls=new ArrayList<ZonePoint>();
			Polygon polygon=PolygonPoint.CreatePolygon(zpl);
			for(int i=0;i<=longtitudesum;i++)
			{
				for(int j=0;j<=latitudesum;j++)
				{
					double i_j_longitude=Double.parseDouble(df2.format(minlongitude+longtitudegap*i));
					double i_j_latitude=Double.parseDouble(df2.format(minlatitude+latitudegap*j));
					if((PolygonPoint.checkWithJdkPolygon(new Point2D.Double(i_j_longitude, i_j_latitude),polygon)))
					{
						allzpls.add(new ZonePoint(i_j_longitude,i_j_latitude));
					}
				}			
			}
			System.out.println("点的总数是："+allzpls.size());
			UAVOnlineInformationService uis=new UAVOnlineInformationService();
			List<UAVOnlinePath> uavonlinepath=new ArrayList<UAVOnlinePath>();
			List<UAVOnline> uavonline=uis.searchAllUAVOnline();
			if(uavonline!=null&&uavonline.size()>0&&allzpls.size()>0)
			{
				for(int i=0;i<uavonline.size();i++)
				{
					List<ZonePoint> zonepointlist=new ArrayList<ZonePoint>();
					zonepointlist.add(new ZonePoint(uavonline.get(i).getLongitude(), uavonline.get(i).getLatitude()));
					uavonlinepath.add(new UAVOnlinePath(uavonline.get(i),zonepointlist));				
				}
				uis.getUVAOnlineCoverPath(uavonlinepath, uavonline, allzpls);
				for(int p=0;p<uavonline.size();p++)
				{
					xml.append("<UAVOnlinePath>");
					for(int q=0;q<uavonlinepath.get(p).getZonepointlist().size();q++)
					{
						xml.append("<LonAndLat>");	
						xml.append("<Longitude>");
						xml.append(uavonlinepath.get(p).getZonepointlist().get(q).getLongitude());
						xml.append("</Longitude>");
						xml.append("<Latitude>");
						xml.append(uavonlinepath.get(p).getZonepointlist().get(q).getLatitude());
						xml.append("</Latitude>");
						xml.append("</LonAndLat>");	
					}
					xml.append("</UAVOnlinePath>");
				}
			}
			
		}
		xml.append("</ZonePathList>");
		System.out.println(xml.toString());
		
		out.print(xml);
		out.flush();
		out.close();				
	}
	

}
