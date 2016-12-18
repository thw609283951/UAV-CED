package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.BanArea;
import UAV.service.BanAreaInformationService;


public class AddBanAreaServlet  extends HttpServlet {

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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		System.out.println("AddRestrictservlet");
		BanAreaInformationService bis=new BanAreaInformationService();
		BanArea ba=new BanArea();
        String longitude = request.getParameter("longitude");
        String latitude=request.getParameter("latitude");
        String radius=request.getParameter("radius");
        
        String northeastlng=request.getParameter("northeastlng");
		String northeastlat=request.getParameter("northeastlat");
		String southwestlng=request.getParameter("southwestlng");
		String southwestlat=request.getParameter("southwestlat");
        
        double longitudedouble=Double.parseDouble(longitude );
        double latitudedouble=Double.parseDouble(latitude);
        double radiusdouble =Double.parseDouble(radius);
        double northeastlngdouble=Double.parseDouble(northeastlng);
	    double northeastlatdouble=Double.parseDouble(northeastlat);
	    double southwestlngdouble=Double.parseDouble(southwestlng);
	    double southwestlatdouble=Double.parseDouble(southwestlat);
	    
        ba.setLongitude(longitudedouble);
        ba.setLatitude(latitudedouble);
        ba.setRadius(radiusdouble);
        ba.setNortheastlongitude(northeastlngdouble);
		ba.setNortheastlatitude(northeastlatdouble);
		ba.setSouthwestlongitude(southwestlngdouble);
		ba.setSouthwestlatitude(southwestlatdouble);
		boolean bool=bis.addBanArea(ba);
		if(bool){
			out.print("SUCCESSED");
		}else{
			out.print("FAILED");
		}
		out.flush();
		out.close();				
	}
}
