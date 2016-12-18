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

import UAV.entity.ZonePoint;
import UAV.service.ZoneInformationService;

public class ShowZoneServlet extends HttpServlet {

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
		System.out.println("***************ShowZoneServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		ZoneInformationService zis=new ZoneInformationService();
		List<ZonePoint> zl=new ArrayList<ZonePoint>();
		zl=zis.searchAllZone();
		StringBuffer xml=new StringBuffer("<ZoneList>");
		DecimalFormat  df2   = new DecimalFormat("#0.000000");
		if (zl != null) {
			for (int i = 0; i < zl.size(); i++) {
				xml.append("<ZonePoint>");
				xml.append("<Longitude>" + df2.format(zl.get(i).getLongitude()) + "</Longitude>");
				xml.append("<Latitude>" + df2.format(zl.get(i).getLatitude())+ "</Latitude>");
				xml.append("</ZonePoint>");
			}
		}
		xml.append("</ZoneList>");		
		System.out.println(xml.toString());
		out.print(xml);
		out.flush();
		out.close();
	}	
}

