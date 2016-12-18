package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.comm.MapDistance;
import UAV.entity.BanArea;
import UAV.entity.UAVOnline;
import UAV.service.BanAreaInformationService;
import UAV.service.UAVOnlineInformationService;

public class ShortestPathServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		 doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		class Node
		{
			public Node(double longitude,double latitude)
			{
				this.longitude=longitude;
				this.latitude=latitude;
			}
			private double longitude;
			private double latitude;
			public double getLongitude() {
				return longitude;
			}
			public void setLongitude(double longitude) {
				this.longitude = longitude;
			}
			public double getLatitude() {
				return latitude;
			}
			public void setLatitude(double latitude) {
				this.latitude = latitude;
			}
			
		}
		System.out.println("***************ShortestPathServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String ip=request.getParameter("ipaddress");
		String po=request.getParameter("port");
		String lo=request.getParameter("newlongitude");
		String la=request.getParameter("newlatitude");

		UAVOnlineInformationService uis=new UAVOnlineInformationService();
		UAVOnline uavonline=new UAVOnline();
		UAVOnline uav;
		int intport=Integer.parseInt(po);
		
		double newlatitude=Double.parseDouble(la);
		double newlongitude=Double.parseDouble(lo);		
		uavonline.setIpaddress(ip);
		uavonline.setPort(intport);
		uav=uis.searchUAVOnline(uavonline);
		
		BanAreaInformationService bis=new BanAreaInformationService();
		List<BanArea> banarealist=new ArrayList<BanArea>();
		List<Node> nodelist=new ArrayList<Node>();
		banarealist=bis.searchAllBanArea();
		
		nodelist.add(new Node(uav.getLongitude(),uav.getLatitude()));
		
		int nodesum=banarealist.size()*8+2;
		double [][]C=new double[nodesum][nodesum];
	
		if(banarealist!=null)
		{
			for(int i=0;i<banarealist.size();i++)
			{
				nodelist.add(new Node(banarealist.get(i).getSouthwestlongitude(),banarealist.get(i).getSouthwestlatitude()));
				nodelist.add(new Node(banarealist.get(i).getSouthwestlongitude(),banarealist.get(i).getLatitude()));
				nodelist.add(new Node(banarealist.get(i).getSouthwestlongitude(),banarealist.get(i).getNortheastlatitude()));
				
				nodelist.add(new Node(banarealist.get(i).getLongitude(),banarealist.get(i).getSouthwestlatitude()));
				nodelist.add(new Node(banarealist.get(i).getLongitude(),banarealist.get(i).getNortheastlatitude()));
				
				nodelist.add(new Node(banarealist.get(i).getNortheastlongitude(),banarealist.get(i).getSouthwestlatitude()));
				nodelist.add(new Node(banarealist.get(i).getNortheastlongitude(),banarealist.get(i).getLatitude()));
				nodelist.add(new Node(banarealist.get(i).getNortheastlongitude(),banarealist.get(i).getNortheastlatitude()));
			}
		}
		
		nodelist.add(new Node(newlongitude,newlatitude));

				
		for(int i=0;i<nodesum;i++)
		{
			double i_longitude=nodelist.get(i).getLongitude();
			double i_latitude=nodelist.get(i).getLatitude();
			for(int j=i;j<nodesum;j++)
			{
				if(i==j)
				{
					C[i][j]=C[j][i]=0;
				}
				else if(C[i][j]==MapDistance.MAX_Distance||C[j][i]==MapDistance.MAX_Distance)
				{
					;
				}
				else
				{
					double j_longitude=nodelist.get(j).getLongitude();
					//System.out.println(j_longitude);
					double j_latitude=nodelist.get(j).getLatitude();
					//System.out.println(j_latitude);
					for(int k=0;k<banarealist.size();k++)
					{
						double circlelongitude=banarealist.get(k).getLongitude();				
						double circlelatitude=banarealist.get(k).getLatitude();
						double circleradius=banarealist.get(k).getRadius();
						boolean bool=MapDistance.ispasscircle(i_longitude, i_latitude, j_longitude, j_latitude,circlelongitude, circlelatitude, circleradius);
					
						if(bool)
						{					
							C[i][j]=C[j][i]=MapDistance.MAX_Distance;							
							break;
						}
						else
						{
							C[i][j]=C[j][i]=MapDistance.GetDistance(i_longitude, i_latitude, j_longitude, j_latitude);
						}
					}
				}				
			}			
		}
		
		double [][]A=new double[nodesum][nodesum];
		
		int [][]P=new int[nodesum][nodesum];
		MapDistance.Flod(A, C, nodesum,P);

	   
		ArrayList<Integer> intlist=new ArrayList<Integer>();
		MapDistance.Path(intlist,0, nodelist.size()-1, P);
		double ve=uav.getVelocity();
		StringBuffer xml=new StringBuffer();
		xml.append("<LonAndLatList>");
		xml.append("<Velocity>");
		xml.append(ve);
		
		xml.append("</Velocity>");
		xml.append("<LonAndLat>");	
		xml.append("<Longitude>");
		xml.append(nodelist.get(0).getLongitude());
		xml.append("</Longitude>");
		xml.append("<Latitude>");
		xml.append(nodelist.get(0).getLatitude());
		xml.append("</Latitude>");
		xml.append("</LonAndLat>");	
		
		
		
		boolean bool=true;
		for(int i=0;i<intlist.size();i++)
		{
			System.out.println("intlistdedaxiaosho"+intlist.size());
			double m_longitude=nodelist.get(intlist.get(i)).getLongitude();
			double m_latitude=nodelist.get(intlist.get(i)).getLatitude();
			bool=uis.modifyUAVOnline(ip,intport,m_longitude,m_latitude);
			if(bool)
			{
				xml.append("<LonAndLat>");	
				xml.append("<Longitude>");
				xml.append(m_longitude);
				xml.append("</Longitude>");
				xml.append("<Latitude>");
				xml.append(m_latitude);
				xml.append("</Latitude>");
				xml.append("</LonAndLat>");	
			}
			else
			{
				break;
			}					
		}
		boolean boolend=uis.modifyUAVOnline(ip,intport,newlongitude,newlatitude);
		
	
		xml.append("<LonAndLat>");	
		xml.append("<Longitude>");
		xml.append(newlongitude);
		xml.append("</Longitude>");
		xml.append("<Latitude>");
		xml.append(newlatitude);
		xml.append("</Latitude>");
		xml.append("</LonAndLat>");			
		xml.append("<Result>");
		
		if(bool&&boolend)
		{
			xml.append("SUCCESSED");
		}
		
		else
		{		
			xml.append("FAILED");			
		}
		xml.append("</Result>");
		xml.append("</LonAndLatList>");
		System.out.println(xml.toString());
		out.print(xml);
		out.flush();
		out.close();
	}	
}