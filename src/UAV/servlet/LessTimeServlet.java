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

import UAV.comm.MapDistance;
import UAV.entity.UAVInBase;
import UAV.entity.UAVInBasePath;
import UAV.entity.TaskPoint;
import UAV.service.TaskInformationService;
import UAV.service.UAVInBaseInformationService;


public class LessTimeServlet extends HttpServlet{

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
		System.out.println("**************LessTimeServlet****************");
		
		
		UAVInBaseInformationService uis=new UAVInBaseInformationService();
		List<UAVInBase> uavinbaselist=new ArrayList<UAVInBase>();
		uavinbaselist=uis.searchAllUAVInBase();	
		sort(uavinbaselist,0,uavinbaselist.size()-1);
		double baselongitude=uavinbaselist.get(0).getLongitude();
		double baselatitude=uavinbaselist.get(0).getLatitude();			
		TaskInformationService tis=new TaskInformationService();
		List<TaskPoint> tpl=new ArrayList<TaskPoint>();
		tpl=tis.searchAllTask();
		System.out.println("任务点的总数量"+tpl.size());
		sort(tpl,0,tpl.size()-1,baselongitude,baselatitude);						
		StringBuffer xml=new StringBuffer("<TaskPathList>");
		DecimalFormat  df2   = new DecimalFormat("#0.000000");						
		List<UAVInBasePath> uabinbasepath=new ArrayList<UAVInBasePath>();										
		if(tpl!=null&&uavinbaselist!=null&&tpl.size()>0&&uavinbaselist.size()>0)
		{
			for(int i=0;i<uavinbaselist.size();i++)
			{
				List<TaskPoint> taskpointlist=new ArrayList<TaskPoint>();
				taskpointlist.add(new TaskPoint(uavinbaselist.get(i).getLongitude(), uavinbaselist.get(i).getLatitude()));
				//System.out.println(""+uavinbaselist.get(i).getVelocity());
				uabinbasepath.add(new UAVInBasePath(uavinbaselist.get(i),taskpointlist));				
			}
			double UAVmaxdistance=uavinbaselist.get(0).getGotodistance();
			for(int q=1;q<uavinbaselist.size();q++)
			{
				if(uavinbaselist.get(q).getGotodistance()>UAVmaxdistance)
					UAVmaxdistance=uavinbaselist.get(q).getGotodistance();
			}
			for(int q=0;q<tpl.size();q++)
			{
				double distance_point_base=MapDistance.GetDistance(tpl.get(q).getLongitude(),tpl.get(q).getLatitude(), uavinbaselist.get(0).getLongitude(), uavinbaselist.get(0).getLatitude());
				if(distance_point_base>UAVmaxdistance/2)
				{
					tpl.remove(q);
				}
			}
			System.out.println("任务点的新数量"+tpl.size());
			Integer sum=new Integer(0);
			Double sumdistance=new Double(0);
			Double time=new Double(0);
			
			
			
			
			uis.getLessTime(uabinbasepath,uavinbaselist,tpl,sum,sumdistance,time);		
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
	
	public void sort(List<TaskPoint> taskpointlist, int low,int high,double baselongitude,double latitude)
	{
		if(low<high)
		{
			int  pivotloc=Partition(taskpointlist,low,high,baselongitude,latitude);
			sort(taskpointlist,low,pivotloc-1,baselongitude,latitude);
			sort(taskpointlist,pivotloc+1,high,baselongitude,latitude);
		}
	}
	public int  Partition(List<TaskPoint> taskpointlist,int low,int high,double baselongitude,double latitude)
	{
		TaskPoint taskpoint=taskpointlist.get(low);
		double privotkey=MapDistance.GetDistance(taskpointlist.get(low).getLongitude(), taskpointlist.get(low).getLatitude(), baselongitude, latitude);
		while(low<high)
		{
			while(low<high&&(MapDistance.GetDistance(taskpointlist.get(high).getLongitude(), taskpointlist.get(high).getLatitude(), baselongitude, latitude)>=privotkey))
			{
				--high;
			}
			taskpointlist.set(low, taskpointlist.get(high));
			while(low<high&&(MapDistance.GetDistance(taskpointlist.get(low).getLongitude(), taskpointlist.get(low).getLatitude(), baselongitude, latitude)<=privotkey))
			{
				++low;
			}
			taskpointlist.set(high, taskpointlist.get(low));
		}
		taskpointlist.set(low, taskpoint);
		return low;
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
		double privotkey=uavinbaselist.get(low).getVelocity();
		while(low<high)
		{
			while(low<high&&(uavinbaselist.get(high).getVelocity()<=privotkey))
			{
				--high;
			}
			uavinbaselist.set(low, uavinbaselist.get(high));
			while(low<high&&(uavinbaselist.get(low).getVelocity()>=privotkey))
			{
				++low;
			}
			uavinbaselist.set(high, uavinbaselist.get(low));
		}
		uavinbaselist.set(low, uavinbase);
		return low;
	}
	
	
	
}
