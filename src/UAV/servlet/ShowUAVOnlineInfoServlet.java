package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.Restriction;
import UAV.entity.UAVOnline;
import UAV.service.RestrictionInformationService;
import UAV.service.UAVOnlineInformationService;

public class ShowUAVOnlineInfoServlet  extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub		
		 doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("***************ShowUAVOnlineInfoServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();		
		String ip=request.getParameter("ipaddress");
		String po=request.getParameter("port");
		
		UAVOnlineInformationService uis=new UAVOnlineInformationService();
		RestrictionInformationService ris=new RestrictionInformationService();
		UAVOnline uo=new UAVOnline();        
		uo.setIpaddress(ip);				
     	uo.setPort(Integer.parseInt(po));
		uo=uis.searchUAVOnline(uo);
		
		StringBuffer xml=new StringBuffer();
			
		if (uo!=null)
		{
			DecimalFormat  df1   = new DecimalFormat("#0.00");
			DecimalFormat  df2   = new DecimalFormat("#0.000000");
			String Ipaddress=uo.getIpaddress();
			int Port=uo.getPort();
			String Longitude=df2.format(uo.getLongitude());
			String  Latitude=df2.format(uo.getLatitude());
			String Elevation=df1.format(uo.getElevation());
			double Height=uo.getHeight();						
			String Velocity=df1.format(uo.getVelocity());
			String Powerconsumption=df1.format(uo.getPowerconsumption());		
			int Remainingpower=uo.getRemainingpower();
			String Version=uo.getVersion();
			Restriction re=ris.searchRestriction(Version);
			double HeightRestriction=re.getHeight();
			int RemainingpowerRestriction=re.getRemainingpower();
			boolean heightbool=true;
			boolean remainingpowerbool=true;
			if(Height>HeightRestriction)
			{
				heightbool=false;
			}
			if(Remainingpower<RemainingpowerRestriction)
			{
				remainingpowerbool=false;
			}
			
			xml.append("<UAVOnline>");
			xml.append("<Ipaddress>" + Ipaddress+ "</Ipaddress>");
			xml.append("<Port>" +Port+ "</Port>");
			xml.append("<Longitude>" + Longitude + "</Longitude>");
			xml.append("<Latitude>" +Latitude+ "</Latitude>");
			xml.append("<Elevation>" + Elevation+ "</Elevation>");
			xml.append("<Height>" + df1.format(Height) + "</Height>");		
			xml.append("<HeightJudge>" +heightbool+ "</HeightJudge>");				
			xml.append("<Velocity>" +Velocity+ "</Velocity>");
			xml.append("<Powerconsumption>"+Powerconsumption+ "</Powerconsumption>");
			xml.append("<Remainingpower>" + Remainingpower+ "</Remainingpower>");		
			xml.append("<RemainingpowerJudge>" +remainingpowerbool+ "</RemainingpowerJudge>");		
			xml.append("<Version>" + Version+ "</Version>");
			xml.append("</UAVOnline>");			
		}
		else
		{
			xml.append("FAILED");
		}		
		System.out.println(xml.toString());
		out.print(xml);
		out.flush();
		out.close();
	}	
}
