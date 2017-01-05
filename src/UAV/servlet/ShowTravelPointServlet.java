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
	
		ArrayList<Point> points = new ArrayList<Point>();
		ArrayList<ArrayList<Point>> ps = new ArrayList<ArrayList<Point>>();
		ArrayList<ArrayList<ArrayList<Point>>> pss = new ArrayList<ArrayList<ArrayList<Point>>>();
		for(int i=0;i<3;i++) {
			Point point = new Point();
			point.setId(1);
			point.setLatitude(121231.3);
			points.add(point);
		}
		ps.add(points);
		ps.add(points);
		
		pss.add(ps);
		pss.add(ps);
		
		request.setAttribute("pss", pss); 
		RequestDispatcher de=request.getRequestDispatcher("/TraveTest.jsp"); 
		de.forward(request, response); 
		
	}
}






















