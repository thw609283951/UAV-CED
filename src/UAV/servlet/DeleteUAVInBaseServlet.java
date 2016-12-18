package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.UAVInBase;
import UAV.service.UAVInBaseInformationService;

public class DeleteUAVInBaseServlet extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("***************DeleteUAVInBasePointServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();		
		String num=request.getParameter("num");
		int number=Integer.parseInt(num);
		
		UAVInBaseInformationService uis=new UAVInBaseInformationService();	
		
		List<UAVInBase> uol=new ArrayList<UAVInBase>();
		uol=uis.searchAllUAVInBase();
		
		boolean b=uis.deleteUAVInBase(uol.get(number).getIpaddress(), uol.get(number).getPort());
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
