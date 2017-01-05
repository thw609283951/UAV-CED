package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.ChildZone;
import UAV.entity.Point;
import UAV.entity.UavForExpress;
import UAV.service.ExpressPathArrangeService;
import UAV.test.preProcessTest;

public class ShowTravelPointServlet extends HttpServlet {

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("***************ShowTravelPointServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		ExpressPathArrangeService expressPathArrangeService = new ExpressPathArrangeService();
		List<ChildZone> childZones = new ArrayList<ChildZone>();
		childZones = expressPathArrangeService.pathArrange();//mubiao
		
		request.setAttribute("childZones", childZones); 
		RequestDispatcher de=request.getRequestDispatcher("/jsp/TraveTest.jsp"); 
		de.forward(request, response); 
		out.flush();
		out.close();
		
	}
}






















