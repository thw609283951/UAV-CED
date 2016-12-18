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

import UAV.entity.TaskPoint;
import UAV.service.TaskInformationService;

public class ShowTaskServlet extends HttpServlet{

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("***************ShowTaskServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		TaskInformationService zis=new TaskInformationService();
		List<TaskPoint> zl=new ArrayList<TaskPoint>();
		zl=zis.searchAllTask();
		StringBuffer xml=new StringBuffer("<TaskList>");
		DecimalFormat  df2   = new DecimalFormat("#0.000000");
		if (zl != null) {
			for (int i = 0; i < zl.size(); i++) {
				xml.append("<TaskPoint>");
				xml.append("<Longitude>" + df2.format(zl.get(i).getLongitude()) + "</Longitude>");
				xml.append("<Latitude>" + df2.format(zl.get(i).getLatitude())+ "</Latitude>");
				xml.append("</TaskPoint>");
			}
		}
		xml.append("</TaskList>");		
		System.out.println(xml.toString());
		out.print(xml);
		out.flush();
		out.close();
	}	
}
