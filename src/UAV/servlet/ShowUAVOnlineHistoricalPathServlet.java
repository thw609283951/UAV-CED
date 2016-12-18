package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.HistoricalPath;
import UAV.entity.UAVOnline;
import UAV.service.HistoricalPathInformationService;
import UAV.service.UAVOnlineInformationService;

public class ShowUAVOnlineHistoricalPathServlet extends HttpServlet {

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
		System.out.println("***************ShowUAVOnlineHistoricalPathServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		PrintWriter out = response.getWriter();		
		String ip=request.getParameter("ipaddress");
		String po=request.getParameter("port");
		int portint=Integer.parseInt(po);
		
		HistoricalPathInformationService hps=new HistoricalPathInformationService();
		List<HistoricalPath> hl=new ArrayList<HistoricalPath>();
		hl=hps.search(ip, portint);	
		StringBuffer xml=new StringBuffer("<HistoricalPathList>");		
		DecimalFormat  df2   = new DecimalFormat("#0.000000");
		DecimalFormat  df1   = new DecimalFormat("#0.00");
		if (hl != null) {
			for (int i = 0; i < hl.size(); i++) {
				xml.append("<HistoricalPath>");
				xml.append("<Longitude>" + df2.format(hl.get(i).getLongitude()) + "</Longitude>");
				xml.append("<Latitude>" + df2.format(hl.get(i).getLatitude())+ "</Latitude>");
				xml.append("</HistoricalPath>");
			}
		}
		UAVOnlineInformationService uois=new UAVOnlineInformationService();
		UAVOnline uavonline=new UAVOnline();
		uavonline.setIpaddress(ip);
		uavonline.setPort(portint);
		uavonline=uois.searchUAVOnline(uavonline);
		xml.append("<HistoricalPath>");
		xml.append("<Longitude>" + df2.format(uavonline.getLongitude()) + "</Longitude>");
		xml.append("<Latitude>" + df2.format(uavonline.getLatitude())+ "</Latitude>");
		xml.append("<Velocity>"+df1.format(uavonline.getVelocity())+"</Velocity>");
		xml.append("</HistoricalPath>");
		xml.append("</HistoricalPathList>");		
		System.out.println(xml.toString());
		out.print(xml);
		out.flush();
		out.close();
	}	
}
