package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.service.ZoneInformationService;

public class DeleteZonePointServlet extends HttpServlet{

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		 doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("***************DeleteZonePointServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();		
		String longitude=request.getParameter("longitude");
		String latitude=request.getParameter("latitude");
		double longitudedouble=Double.parseDouble(longitude);
		double latitudedouble=Double.parseDouble(latitude);
		ZoneInformationService zis=new ZoneInformationService();		
		boolean b=zis.deleteZone(longitudedouble, latitudedouble);
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
