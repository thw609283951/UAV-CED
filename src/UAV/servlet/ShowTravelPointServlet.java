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
		
		ExpressPathArrangeService ep = new ExpressPathArrangeService();//创建快递规划对象
		ep.pathArrange();//执行主函数
		
		request.setAttribute("warePoints", ep.getWpCoords());
		request.setAttribute("dockPoints", ep.getSdpCoords());
		request.setAttribute("needPoints", ep.getNpCoords());
		
		request.setAttribute("carsPath", ep.getDemoCarPath());
		request.setAttribute("uavsPath", ep.getDemoUavPath());
	
		RequestDispatcher de=request.getRequestDispatcher("/jsp/TraveTest.jsp"); 
		de.forward(request, response); 
		
	}
}






















