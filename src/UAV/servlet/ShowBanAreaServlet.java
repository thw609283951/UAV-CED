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

import UAV.entity.BanArea;
import UAV.service.BanAreaInformationService;

public class ShowBanAreaServlet extends HttpServlet {

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
		System.out.println("***************ShowBanAreaServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		BanAreaInformationService bis=new BanAreaInformationService();
		List<BanArea> bl=new ArrayList<BanArea>();
		bl=bis.searchAllBanArea();
		StringBuffer xml=new StringBuffer("<BanAreaList>");
		
		DecimalFormat  df1   = new DecimalFormat("#0.00");
		DecimalFormat  df2   = new DecimalFormat("#0.000000");
		if (bl != null) {
			for (int i = 0; i < bl.size(); i++) {
				xml.append("<BanArea>");
				xml.append("<Longitude>" + df2.format(bl.get(i).getLongitude()) + "</Longitude>");
				xml.append("<Latitude>" + df2.format(bl.get(i).getLatitude())+ "</Latitude>");
				xml.append("<Radius>" + df1.format(bl.get(i).getRadius())+ "</Radius>");
				xml.append("</BanArea>");
			}
		}
		xml.append("</BanAreaList>");		
		System.out.println(xml.toString());
		out.print(xml);
		out.flush();
		out.close();
	}	
}

