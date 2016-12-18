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
import UAV.entity.Record;
import UAV.service.RecordInformationService;

public class ShowRecordServlet extends HttpServlet {

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

		System.out.println("ShowRecordServlet");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		RecordInformationService ris=new RecordInformationService();
		List<Record> list=new ArrayList<Record>();
		//获取当前页
		int currentPage=0;
		if(request.getParameter("currentPage")!=null&&!(request.getParameter("currentPage")).isEmpty()){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));			
			System.out.println(request.getParameter("currentPage"));
		}else{
			currentPage=1;
		}
		Page page=PageUtil.createPage(12, ris.searchRecordTotal(), currentPage);
		
		
		
		list=ris.searchAllRecord(page);
		StringBuffer xml=new StringBuffer("<RecordList>");
		if(list!=null){			
			for(int i=0;i<list.size();i++){
				xml.append("<Record>");
				xml.append("<Ipaddress>"+list.get(i).getIpaddress()+"</Ipaddress>");
				xml.append("<Port>"+list.get(i).getPort()+"</Port>");
				xml.append("<Indate>"+list.get(i).getIndate()+"</Indate>");
				xml.append("<Intime>"+list.get(i).getIntime()+"</Intime>");
				xml.append("<Outdate>"+list.get(i).getOutdate()+"</Outdate>");
				xml.append("<Outtime>"+list.get(i).getOuttime()+"</Outtime>");				
				xml.append("</Record>");
			}
			
		}
		xml.append("<page>");
		xml.append("<hasPrePage>"+page.isHasPrePage()+"</hasPrePage>");
		xml.append("<hasNextPage>"+page.isHasNexPage()+"</hasNextPage>");
		xml.append("<currentPage>"+currentPage+"</currentPage>");
		xml.append("<totalPage>"+page.getTotalPage()+"</totalPage>");
		xml.append("</page>");
		xml.append("</RecordList>");
		System.out.println(xml.toString());
		out.print(xml);
		out.flush();
		out.close();
	}

}


