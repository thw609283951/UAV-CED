package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.BanArea;
import UAV.service.BanAreaInformationService;

public class ModifyBanAreaServlet extends HttpServlet {

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
		System.out.println("***************ModifyBanAreaServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String longitude=request.getParameter("longitude");
		String latitude=request.getParameter("latitude");
		String newadius=request.getParameter("newradius");
		
		String northeastlng=request.getParameter("northeastlng");
		String northeastlat=request.getParameter("northeastlat");
		String southwestlng=request.getParameter("southwestlng");
		String southwestlat=request.getParameter("southwestlat");
		
	    double longitudedouble=Double.parseDouble(longitude);
	    double latitudedouble=Double.parseDouble(latitude);
	    double newadiusdouble=Double.parseDouble(newadius);
	    double northeastlngdouble=Double.parseDouble(northeastlng);
	    double northeastlatdouble=Double.parseDouble(northeastlat);
	    double southwestlngdouble=Double.parseDouble(southwestlng);
	    double southwestlatdouble=Double.parseDouble(southwestlat);
				
	    BanAreaInformationService bis=new BanAreaInformationService();
	    BanArea banarea=new BanArea();
		banarea.setLongitude(longitudedouble);
		banarea.setLatitude(latitudedouble);
		banarea.setRadius(newadiusdouble);
		banarea.setNortheastlongitude(northeastlngdouble);
		banarea.setNortheastlatitude(northeastlatdouble);
		banarea.setSouthwestlongitude(southwestlngdouble);
		banarea.setSouthwestlatitude(southwestlatdouble);
		boolean b=bis.modifyBanArea(banarea);
		StringBuffer xml=new StringBuffer();
		if (b)
		{
			
			 xml.append("SUCCESSED");			
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

