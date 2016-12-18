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

import UAV.entity.UAVInBase;
import UAV.entity.UAVInBasePath;
import UAV.entity.TaskPoint;
import UAV.service.TaskInformationService;
import UAV.service.UAVInBaseInformationService;


public class LessUAVServlet extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{

		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		System.out.println("**************LessUAVServlet****************");
		
		TaskInformationService tis=new TaskInformationService();
		List<TaskPoint> tpl=new ArrayList<TaskPoint>();
		tpl=tis.searchAllTask();
		StringBuffer xml=new StringBuffer("<TaskPathList>");
		DecimalFormat  df2   = new DecimalFormat("#0.000000");
		
		UAVInBaseInformationService uis=new UAVInBaseInformationService();
		List<UAVInBase> uavinbaselist=new ArrayList<UAVInBase>();
		uavinbaselist=uis.searchAllUAVInBase();
		/*for(int i=0;i<uavinbaselist.size();i++)
		{
			System.out.println(uavinbaselist.get(i).getGotodistance());				
		}*/
		List<UAVInBasePath> uabinbasepath=new ArrayList<UAVInBasePath>();
		
		
		
		sort(uavinbaselist,0,uavinbaselist.size()-1);
		
		if(tpl!=null&&uavinbaselist!=null&&tpl.size()>0&&uavinbaselist.size()>0)
		{
			for(int i=0;i<uavinbaselist.size();i++)
			{
				List<TaskPoint> taskpointlist=new ArrayList<TaskPoint>();
				taskpointlist.add(new TaskPoint(uavinbaselist.get(i).getLongitude(), uavinbaselist.get(i).getLatitude()));
				System.out.println(uavinbaselist.get(i).getGotodistance());
				uabinbasepath.add(new UAVInBasePath(uavinbaselist.get(i),taskpointlist));				
			}
			
			int number=0;
			while(tpl!=null&&uavinbaselist!=null&&tpl.size()!=0&&number<10)
			{
				++number;
				uis.getLessUAV(uabinbasepath,uavinbaselist,tpl);		
		    }
			for(int p=0;p<uavinbaselist.size();p++)
			{
				xml.append("<UAVInBasePath>");
				xml.append("<Velocity>");
				xml.append(uavinbaselist.get(p).getVelocity());
				xml.append("</Velocity>");
				for(int q=0;q<uabinbasepath.get(p).getTaskpointlist().size();q++)
				{
					xml.append("<LonAndLat>");	
					xml.append("<Longitude>");
					xml.append(uabinbasepath.get(p).getTaskpointlist().get(q).getLongitude());
					xml.append("</Longitude>");
					xml.append("<Latitude>");
					xml.append(uabinbasepath.get(p).getTaskpointlist().get(q).getLatitude());
					xml.append("</Latitude>");
					xml.append("</LonAndLat>");	
				}
				xml.append("</UAVInBasePath>");
			}
			xml.append("</TaskPathList>");
			System.out.println(xml.toString());		
			out.print(xml);
			out.flush();
			out.close();
		}
	}
	
	public void sort(List<UAVInBase> uavinbaselist, int low,int high)
	{
		if(low<high)
		{
			int  pivotloc=Partition(uavinbaselist,low,high);
			sort(uavinbaselist,low,pivotloc-1);
			sort(uavinbaselist,pivotloc+1,high);
		}
	}
	public int  Partition(List<UAVInBase> uavinbaselist,int low,int high)
	{
		UAVInBase uavinbase=uavinbaselist.get(low);
		double privotkey=uavinbaselist.get(low).getGotodistance();
		while(low<high)
		{
			while(low<high&&(uavinbaselist.get(high).getGotodistance()<=privotkey))
			{
				--high;
			}
			uavinbaselist.set(low, uavinbaselist.get(high));
			while(low<high&&(uavinbaselist.get(low).getGotodistance()>=privotkey))
			{
				++low;
			}
			uavinbaselist.set(high, uavinbaselist.get(low));
		}
		uavinbaselist.set(low, uavinbase);
		return low;
	}
	
}
