package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.Restriction;
import UAV.service.RestrictionInformationService;

public class AddRestrictServlet extends HttpServlet {

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
		RestrictionInformationService ars=new RestrictionInformationService();
        Restriction r=new Restriction();
        String version = request.getParameter("version");
        String maxheight=request.getParameter("maxheight");
        String minrp=request.getParameter("minrp");
        
        double maxheightdouble=Double.parseDouble(maxheight);
        int minrpint=Integer.parseInt(minrp);
		r.setVersion(version);
		r.setHeight(maxheightdouble);
		r.setRemainingpower(minrpint);
		boolean bool=ars.addRestriction(r);

		if(bool){
			out.print("SUCCESSED");
		}else{
			out.print("FAILED");
		}
		out.flush();
		out.close();				
	}
}
