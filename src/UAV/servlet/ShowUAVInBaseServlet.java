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

import UAV.comm.Ipserver;
import UAV.entity.UAVInBase;
import UAV.service.UAVInBaseInformationService;

public class ShowUAVInBaseServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    //System.out.println("***************ShowUAVInBaseServlet****************");
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		UAVInBaseInformationService uis=new UAVInBaseInformationService();
		List<UAVInBase> uol=new ArrayList<UAVInBase>();
		uol=uis.searchAllUAVInBase();
		UAVInBase uo=new UAVInBase();
		StringBuffer xml=new StringBuffer("<UAVInBaseList>");
		String ipstr=Ipserver.GetAddressIP();
		xml.append("<localhost>"+ipstr+"</localhost>");
		DecimalFormat  df1   = new DecimalFormat("#0.00");
		DecimalFormat  df2   = new DecimalFormat("#0.000000");
		if (uol != null) {
			for (int i = 0; i < uol.size(); i++) {
				xml.append("<UAVInBase>");
				xml.append("<Ipaddress>" + uol.get(i).getIpaddress() + "</Ipaddress>");
				xml.append("<Port>" + uol.get(i).getPort() + "</Port>");
				xml.append("<Longitude>" + df2.format(uol.get(i).getLongitude()) + "</Longitude>");
				xml.append("<Latitude>" + df2.format(uol.get(i).getLatitude())+ "</Latitude>");
				xml.append("<Elevation>" + df1.format(uol.get(i).getElevation())+ "</Elevation>");
				xml.append("<Height>" + df1.format(uol.get(i).getHeight()) + "</Height>");
				xml.append("<Velocity>" + df1.format(uol.get(i).getVelocity())+ "</Velocity>");
				xml.append("<Powerconsumption>"+df1.format(uol.get(i).getPowerconsumption())+ "</Powerconsumption>");
				xml.append("<Remainingpower>" + uol.get(i).getRemainingpower()+ "</Remainingpower>");
				xml.append("<Version>" + uol.get(i).getVersion() + "</Version>");
				xml.append("</UAVInBase>");
			}
		}
		xml.append("</UAVInBaseList>");		
		System.out.println(xml.toString());
		out.print(xml);
		out.flush();
		out.close();
	}	
}
