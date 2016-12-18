package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.Restriction;
import UAV.service.RestrictionInformationService;


public class ModifyRestrictionServlet extends HttpServlet {

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
		System.out.println("***************ModifyRestrictionServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String version=request.getParameter("version");
		String newmaxheight=request.getParameter("newmaxheight");
		String newminrp=request.getParameter("newminrp");
	    double newmaxheightdouble=Double.parseDouble(newmaxheight);
	    int newminrpint=Integer.parseInt(newminrp);
				
		RestrictionInformationService ris=new RestrictionInformationService();
		Restriction restriction=new Restriction();
		restriction.setVersion(version);
		restriction.setHeight(newmaxheightdouble);
		restriction.setRemainingpower(newminrpint);
		boolean b=ris.modifyRestriction(restriction);
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
