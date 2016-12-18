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

import UAV.comm.PageUtil;
import UAV.entity.Page;
import UAV.entity.ZonePoint;
import UAV.service.ZoneInformationService;

public class SearchAllZoneServlet extends HttpServlet {

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

		System.out.println("SearchAllZoneServlet");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		ZoneInformationService zis=new ZoneInformationService();
		List<ZonePoint> list=new ArrayList<ZonePoint>();
		//获取当前页
		int currentPage=0;
		if(request.getParameter("currentPage")!=null&&!(request.getParameter("currentPage")).isEmpty()){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));			
			System.out.println(request.getParameter("currentPage"));
		}else{
			currentPage=1;
		}
		Page page=PageUtil.createPage(8, zis.searchZoneTotal(), currentPage);
		
		DecimalFormat  df1   = new DecimalFormat("#0.000000");
		
		list=zis.searchAllZone(page);
		StringBuffer xml=new StringBuffer("<ZoneList>");
		if(list!=null){			
			for(int i=0;i<list.size();i++){
				xml.append("<ZonePoint>");
				xml.append("<Longitude>"+df1.format(list.get(i).getLongitude())+"</Longitude>");
				xml.append("<Latitude>"+df1.format(list.get(i).getLatitude())+"</Latitude>");
				xml.append("</ZonePoint>");
			}
			
		}
		xml.append("<page>");
		xml.append("<hasPrePage>"+page.isHasPrePage()+"</hasPrePage>");
		xml.append("<hasNextPage>"+page.isHasNexPage()+"</hasNextPage>");
		xml.append("<currentPage>"+currentPage+"</currentPage>");
		xml.append("<totalPage>"+page.getTotalPage()+"</totalPage>");
		xml.append("</page>");
		xml.append("</ZoneList>");
		System.out.println(xml.toString());
		out.print(xml);
		out.flush();
		out.close();
	}

}


