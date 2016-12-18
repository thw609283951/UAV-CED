package UAV.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import UAV.entity.UAVInBase;
import UAV.service.UAVInBaseInformationService;

public class AddUAVInBaseServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		System.out.println("-----------AddUAVInBaseServlet----------");
		UAVInBaseInformationService bis=new UAVInBaseInformationService();
	    System.out.println(request.toString());
        String longitude = request.getParameter("newlongitude");
        String latitude=request.getParameter("newlatitude");
        String sumpower=request.getParameter("sumpower");
        String remianingpower=request.getParameter("remianingpower");
        String consumpower=request.getParameter("consumpower");
        String height=request.getParameter("height");
        String elevation=request.getParameter("elevation");
        String velocity=request.getParameter("velocity");
        
        String version=request.getParameter("version");
        
        String port=request.getParameter("port");
        
        String Ipaddress=request.getParameter("Ipaddress");
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        double longitudedouble=Double.parseDouble(longitude);
        double latitudedouble=Double.parseDouble(latitude);
        double sumpowerdounle =Double.parseDouble(sumpower);
        int remianingpowerdounle =Integer.parseInt(remianingpower);
        double consumpowerdounle =Double.parseDouble(consumpower);
        double heightdouble =Double.parseDouble(height);
        double elevationdouble =Double.parseDouble(elevation);
        double velocitydouble =Double.parseDouble(velocity);
        
        int portint =Integer.parseInt(port);
        
    	UAVInBase z=new UAVInBase(Ipaddress,portint,longitudedouble,latitudedouble,
    			elevationdouble,heightdouble,velocitydouble,consumpowerdounle,
    			sumpowerdounle,remianingpowerdounle,version);
     
		boolean bool=bis.addUAVInBase(z);
		if(bool){
			out.print("SUCCESSED");
		}else{
			out.print("FAILED");
		}
		out.flush();
		out.close();				
	}
}
