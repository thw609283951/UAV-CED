package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.TaskPoint;
import UAV.service.TaskInformationService;


public class AddTaskServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		System.out.println("-----------AddTaskServlet----------");
		TaskInformationService bis=new TaskInformationService();
		TaskPoint z=new TaskPoint();
        String longitude = request.getParameter("newlongitude");
        String latitude=request.getParameter("newlatitude");        
        double longitudedouble=Double.parseDouble(longitude );
        double latitudedouble=Double.parseDouble(latitude);
        z.setLongitude(longitudedouble);
        z.setLatitude(latitudedouble);
		boolean bool=bis.addTask(z);
		if(bool){
			out.print("SUCCESSED");
		}else{
			out.print("FAILED");
		}
		out.flush();
		out.close();				
	}
}

