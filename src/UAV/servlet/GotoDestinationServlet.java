package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.UAVOnline;
import UAV.service.UAVOnlineInformationService;

public class GotoDestinationServlet extends HttpServlet {

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
		System.out.println("***************GotoDestinationServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String ip=request.getParameter("ipaddress");
		String po=request.getParameter("port");
		System.out.println("aaaaaaaaaaaaaa" + po);
		String lo=request.getParameter("newlongitude");
		String la=request.getParameter("newlatitude");
				
		UAVOnlineInformationService uis=new UAVOnlineInformationService();
		UAVOnline uavonline=new UAVOnline();	
		int intport=Integer.parseInt(po);		
		double newlatitude=Double.parseDouble(la);
		double newlongitude=Double.parseDouble(lo);		
		uavonline.setIpaddress(ip);
		uavonline.setPort(intport);
		boolean b=uis.modifyUAVOnline(ip,intport,newlongitude,newlatitude);
		uavonline=uis.searchUAVOnline(uavonline);
		double ve=uavonline.getVelocity();
		StringBuffer xml=new StringBuffer();
		if (b)
		{
			xml.append("<ResultAndVelocity>");
			xml.append("<Result>");
			xml.append("SUCCESSED");
			xml.append("</Result>");
			xml.append("<Velocity>");
			xml.append(ve);
			xml.append("</Velocity>");
			xml.append("</ResultAndVelocity>");
		}
		else
		{
			xml.append("<ResultAndVelocity>");
			xml.append("<Result>");
			xml.append("FAILED");
			xml.append("</Result>");
			xml.append("<Velocity>");
			xml.append(ve);
			xml.append("</Velocity>");
			xml.append("</ResultAndVelocity>");
		}		
		System.out.println(xml.toString());
		out.print(xml);
		out.flush();
		out.close();
	}	
}

