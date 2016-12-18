package UAV.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import UAV.entity.User;

public class MyFilter implements Filter{

	public void destroy() {

		System.out.println("destoryFilter");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		System.out.println("doFilter");

		HttpServletRequest req=(HttpServletRequest)request;
		HttpSession session=req.getSession();
		User user=(User)session.getAttribute("Identity");

		if(req.getServletPath().equals("/jsp/LoginServlet.action"))
		{
			chain.doFilter(request, response);
		}
		else if(req.getServletPath().equals("/jsp/UAV-OnlineInfomationInDetail.jsp"))
		{
			HttpServletResponse res=(HttpServletResponse)response;
			res.sendRedirect("/jsp/UAV-Online.jsp");
		}
		else
		{
			if(user!=null&&!user.getName().isEmpty()&&!user.getPassword().isEmpty()){
				chain.doFilter(request, response);
			}
			else
			{
				HttpServletResponse res=(HttpServletResponse)response;
				res.sendRedirect("../login.jsp");
			}			
		}
	}
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("initFilter");
	}
}