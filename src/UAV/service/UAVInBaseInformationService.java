package UAV.service;

import java.util.List;


import UAV.comm.MapDistance;
import UAV.dao.HistoricalPathDAO;
import UAV.dao.UAVInBaseDAO;
import UAV.entity.HistoricalPath;
import UAV.entity.TaskPoint;
import UAV.entity.UAVInBase;
import UAV.entity.UAVInBasePath;
import UAV.factory.HistoricalPathDAOFactory;
import UAV.factory.UAVInBaseDAOFactory;

public class UAVInBaseInformationService {


	public UAVInBaseInformationService() 
	{

	}
	public boolean addUAVInBase(UAVInBase uavinbase)
	{
		UAVInBaseDAO uod=UAVInBaseDAOFactory.getInstance();
		return uod.addUAVInBase(uavinbase);
	}
	public boolean modifyUAVInBase(UAVInBase uavinbase)
	{
		UAVInBaseDAO uod=UAVInBaseDAOFactory.getInstance();
		HistoricalPathDAO hpd=HistoricalPathDAOFactory.getInstance();
		UAVInBase ul=uod.searchUAVInBase(uavinbase);
		boolean b=hpd.addHistoricalPath(new HistoricalPath(ul.getIpaddress(),ul.getPort(),ul.getLongitude(),ul.getLatitude()));
		if(b)
		{
			return uod.modifyUAVInBase(uavinbase);
		}
		else
		{
			return false;
		}
	}
	public boolean modifyUAVInBase(String ipaddress, int port,double longitude,double latitude )
	{
		UAVInBaseDAO uod=UAVInBaseDAOFactory.getInstance();
		HistoricalPathDAO hpd=HistoricalPathDAOFactory.getInstance();
		UAVInBase uavinbase=new UAVInBase();
		uavinbase.setIpaddress(ipaddress);
		uavinbase.setPort(port);
		UAVInBase ul=uod.searchUAVInBase(uavinbase);
		boolean b=hpd.addHistoricalPath(new HistoricalPath(ul.getIpaddress(),ul.getPort(),ul.getLongitude(),ul.getLatitude()));
		if(b)
		{
			return uod.modifyUAVInBase(uavinbase.getIpaddress(),uavinbase.getPort(),longitude,latitude);
		}
		else
		{
			return false;
		}
	}
	
	public boolean deleteUAVInBase(String ipaddress,int port)
	{
		UAVInBaseDAO uod=UAVInBaseDAOFactory.getInstance();
		return uod.deleteUAVInBase(ipaddress,port);
	}
	public List<UAVInBase> searchAllUAVInBase()
	{
		UAVInBaseDAO uod=UAVInBaseDAOFactory.getInstance();
		List<UAVInBase> uol = uod.searchAllUAVInBase();
		if(uol!=null&&uol.size()>0)
		{
			return uol;
		}
		else
		{
			return null;
		}		
	}
	public UAVInBase searchUAVInBase(UAVInBase uavinbase)
	{
		UAVInBaseDAO uod=UAVInBaseDAOFactory.getInstance();
		UAVInBase uol = uod.searchUAVInBase(uavinbase);
		if(uol!=null)
		{
			return uol;
		}
		else
		{
			return null;
		}		
	}
	
	
	public void getLessUAV(List<UAVInBasePath> uabinbasepath,List<UAVInBase> uavinbaselist,List<TaskPoint> taskpointlist)
	{		
		for(int i=0;i<uavinbaselist.size();i++)
		{
			double Maxdistance=uavinbaselist.get(i).getGotodistance();			
			while(taskpointlist.size()>0)
			{
				double mindiatance=MapDistance.GetDistance(uavinbaselist.get(i).getLongitude(), uavinbaselist.get(i).getLatitude(), taskpointlist.get(0).getLongitude(), taskpointlist.get(0).getLatitude());
				int tag=0;
				for(int k=1;k<taskpointlist.size();k++)
				{
					double distance=MapDistance.GetDistance(uavinbaselist.get(i).getLongitude(), uavinbaselist.get(i).getLatitude(), taskpointlist.get(k).getLongitude(), taskpointlist.get(k).getLatitude());
					if(distance<mindiatance)
					{
						mindiatance=distance;
						tag=k;
					}
				}
				if(Maxdistance>(MapDistance.GetDistance(taskpointlist.get(tag).getLongitude(), taskpointlist.get(tag).getLatitude(), uavinbaselist.get(i).getLongitude(), uavinbaselist.get(i).getLatitude())+MapDistance.GetDistance(taskpointlist.get(tag).getLongitude(), taskpointlist.get(tag).getLatitude(), uabinbasepath.get(i).getTaskpointlist().get(0).getLongitude(), uabinbasepath.get(i).getTaskpointlist().get(0).getLatitude())))
				{
					Maxdistance=Maxdistance-MapDistance.GetDistance(taskpointlist.get(tag).getLongitude(),taskpointlist.get(tag).getLatitude(), uavinbaselist.get(i).getLongitude(), uavinbaselist.get(i).getLatitude());
					//System.out.println(distance);
					uabinbasepath.get(i).getTaskpointlist().add(taskpointlist.get(tag));
					uavinbaselist.get(i).setLongitude(taskpointlist.get(tag).getLongitude());
					uavinbaselist.get(i).setLatitude(taskpointlist.get(tag).getLatitude());								
					taskpointlist.remove(tag);
					if(taskpointlist.size()==0)
					{
						uabinbasepath.get(i).getTaskpointlist().add(uabinbasepath.get(i).getTaskpointlist().get(0));
						uavinbaselist.get(i).setLongitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLongitude());
						uavinbaselist.get(i).setLatitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLatitude());
						return ;
					}
					
				}
				else
				{
					uabinbasepath.get(i).getTaskpointlist().add(uabinbasepath.get(i).getTaskpointlist().get(0));
					uavinbaselist.get(i).setLongitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLongitude());
					uavinbaselist.get(i).setLatitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLatitude());
					break;
				}
			}
		}
	}
	
	
	public void getLessTime(List<UAVInBasePath> uabinbasepath,List<UAVInBase> uavinbaselist,List<TaskPoint> taskpointlist,Integer sum,Double sumdiatance,Double time)
	{
		System.out.println("任务点总数"+taskpointlist.size());
		int canmoveUAV=getWaitUAV(uavinbaselist);
		if(canmoveUAV<0||taskpointlist.size()<1)
		{
			System.out.println(canmoveUAV+"要返回");
			for(int m=0;m<uavinbaselist.size();m++)
			{
				if(!uavinbaselist.get(m).isInbase())
				{
					uabinbasepath.get(m).getTaskpointlist().add(uabinbasepath.get(m).getTaskpointlist().get(0));
					uavinbaselist.get(m).setLongitude(uabinbasepath.get(m).getTaskpointlist().get(0).getLongitude());
					uavinbaselist.get(m).setLatitude(uabinbasepath.get(m).getTaskpointlist().get(0).getLatitude());
					uavinbaselist.get(m).resetGotodistance(); 
					uavinbaselist.get(m).setInbase(true);
				}
			}
			return ;
		}
			
		/*for(int i=0;(uavinbaselist.get(i).isInbase()&&i<uavinbaselist.size()&&taskpointlist.size()>0);i++)
		{
			int number=(taskpointlist.size()%uavinbaselist.size()==0)? taskpointlist.size()/uavinbaselist.size():taskpointlist.size()/uavinbaselist.size()+1;
			int tasktag=i*number+number;
			int _tasktag=(tasktag<taskpointlist.size()-1)? tasktag:taskpointlist.size()-1;
			
			uabinbasepath.get(i).getTaskpointlist().add(taskpointlist.get(_tasktag));
			uavinbaselist.get(i).setLongitude(taskpointlist.get(_tasktag).getLongitude());
			uavinbaselist.get(i).setLatitude(taskpointlist.get(_tasktag).getLatitude());								
			taskpointlist.remove(_tasktag);
		}*/
		/*if(uavinbaselist.get(canmoveUAV).isInbase())
		{
			int number=(taskpointlist.size()%uavinbaselist.size()==0)? taskpointlist.size()/uavinbaselist.size():taskpointlist.size()/uavinbaselist.size()+1;
			int tasktag=canmoveUAV*number+number;
			int _tasktag=(tasktag<taskpointlist.size()-1)? tasktag:taskpointlist.size()-1;
			
			uabinbasepath.get(canmoveUAV).getTaskpointlist().add(taskpointlist.get(_tasktag));
			uavinbaselist.get(canmoveUAV).setLongitude(taskpointlist.get(_tasktag).getLongitude());
			uavinbaselist.get(canmoveUAV).setLatitude(taskpointlist.get(_tasktag).getLatitude());
			uavinbaselist.get(canmoveUAV).setInbase(false);
			taskpointlist.remove(_tasktag);
		}
		*/
		double Maxdistance=uavinbaselist.get(canmoveUAV).getGotodistance();
		double longitude=uavinbaselist.get(canmoveUAV).getLongitude();
		double latitude=uavinbaselist.get(canmoveUAV).getLatitude();
		double mindiatance=MapDistance.GetDistance(longitude, latitude, taskpointlist.get(0).getLongitude(), taskpointlist.get(0).getLatitude());
		int tag=0;
		for(int i=1;i<taskpointlist.size();i++)
		{
			double distance=MapDistance.GetDistance(longitude, latitude, taskpointlist.get(i).getLongitude(), taskpointlist.get(i).getLatitude());
			if(distance<mindiatance)
			{
				mindiatance=distance;
				tag=i;
			}
		}
		if(Maxdistance>(MapDistance.GetDistance(taskpointlist.get(tag).getLongitude(), taskpointlist.get(tag).getLatitude(), uavinbaselist.get(canmoveUAV).getLongitude(), uavinbaselist.get(canmoveUAV).getLatitude())+MapDistance.GetDistance(taskpointlist.get(tag).getLongitude(), taskpointlist.get(tag).getLatitude(), uabinbasepath.get(canmoveUAV).getTaskpointlist().get(0).getLongitude(), uabinbasepath.get(canmoveUAV).getTaskpointlist().get(0).getLatitude())))
		{
			System.out.println(canmoveUAV+"台无人机需要走的最短路程是"+mindiatance);
			uabinbasepath.get(canmoveUAV).getTaskpointlist().add(taskpointlist.get(tag));
			uavinbaselist.get(canmoveUAV).setLongitude(taskpointlist.get(tag).getLongitude());
			uavinbaselist.get(canmoveUAV).setLatitude(taskpointlist.get(tag).getLatitude());
			uavinbaselist.get(canmoveUAV).setGotodistance(mindiatance);
			System.out.println(canmoveUAV+"台无人机可以走的距离是"+uavinbaselist.get(canmoveUAV).getGotodistance()+"米");
			uavinbaselist.get(canmoveUAV).setMovetime((mindiatance/1000)/(uavinbaselist.get(canmoveUAV).getElevation()));
			uavinbaselist.get(canmoveUAV).setInbase(false);
			System.out.println(canmoveUAV+"台无人机走了"+uavinbaselist.get(canmoveUAV).getMovetime()+"小时");
			taskpointlist.remove(tag);
		}
		else
		{
			if(uavinbaselist.get(canmoveUAV).isInbase())
			{
				uavinbaselist.get(canmoveUAV).setCanmove(false);
			}
			else
			{
				uabinbasepath.get(canmoveUAV).getTaskpointlist().add(uabinbasepath.get(canmoveUAV).getTaskpointlist().get(0));
				uavinbaselist.get(canmoveUAV).setLongitude(uabinbasepath.get(canmoveUAV).getTaskpointlist().get(0).getLongitude());
				uavinbaselist.get(canmoveUAV).setLatitude(uabinbasepath.get(canmoveUAV).getTaskpointlist().get(0).getLatitude());
				uavinbaselist.get(canmoveUAV).resetGotodistance(); 
				uavinbaselist.get(canmoveUAV).setInbase(true);
			}			
		}		
	/*	for(int i=0;i<uavinbaselist.size();i++)
		{
			double distance=uavinbaselist.get(i).getGotodistance();
			while(taskpointlist.size()>0)
			{
				if(distance>(MapDistance.GetDistance(taskpointlist.get(0).getLongitude(), taskpointlist.get(0).getLatitude(), uavinbaselist.get(i).getLongitude(), uavinbaselist.get(i).getLatitude())+MapDistance.GetDistance(taskpointlist.get(0).getLongitude(), taskpointlist.get(0).getLatitude(), uabinbasepath.get(i).getTaskpointlist().get(0).getLongitude(), uabinbasepath.get(i).getTaskpointlist().get(0).getLatitude())))
				{
					distance=distance-MapDistance.GetDistance(taskpointlist.get(0).getLongitude(),taskpointlist.get(0).getLatitude(), uavinbaselist.get(i).getLongitude(), uavinbaselist.get(i).getLatitude());
					uabinbasepath.get(i).getTaskpointlist().add(taskpointlist.get(0));
					uavinbaselist.get(i).setLongitude(taskpointlist.get(0).getLongitude());
					uavinbaselist.get(i).setLatitude(taskpointlist.get(0).getLatitude());								
					taskpointlist.remove(0);
					if(taskpointlist.size()==0)
					{
						uabinbasepath.get(i).getTaskpointlist().add(uabinbasepath.get(i).getTaskpointlist().get(0));
						uavinbaselist.get(i).setLongitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLongitude());
						uavinbaselist.get(i).setLatitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLatitude());
						return ;
					}
					
				}
				else
				{
					uabinbasepath.get(i).getTaskpointlist().add(uabinbasepath.get(i).getTaskpointlist().get(0));
					uavinbaselist.get(i).setLongitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLongitude());
					uavinbaselist.get(i).setLatitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLatitude());
					break;
				}
			}
		}*/
		getLessTime(uabinbasepath,uavinbaselist,taskpointlist,sum,sumdiatance,time);
	}
	
	
	public void getMoreTask(List<UAVInBasePath> uabinbasepath,List<UAVInBase> uavinbaselist,List<TaskPoint> taskpointlist,double limittime)
	{
		System.out.println("限制的时间是"+limittime);
		int canmoveUAV=getWaitUAV(uavinbaselist);
		if(canmoveUAV<0||taskpointlist.size()<1)
			return ;
		
		for(int j=canmoveUAV;j==canmoveUAV;j++)
		{
			double longitude=uavinbaselist.get(j).getLongitude();
			double latitude=uavinbaselist.get(j).getLatitude();
			double mindiatance=MapDistance.GetDistance(longitude, latitude, taskpointlist.get(0).getLongitude(), taskpointlist.get(0).getLatitude());
			int tag=0;
			for(int i=1;i<taskpointlist.size();i++)
			{
				double distance=MapDistance.GetDistance(longitude, latitude, taskpointlist.get(i).getLongitude(), taskpointlist.get(i).getLatitude());
				if(distance<mindiatance)
				{
					mindiatance=distance;
					tag=i;
				}
			}
			if(mindiatance<uavinbaselist.get(j).getGotodistance()&&(limittime>uavinbaselist.get(j).getMovetime()+(mindiatance/1000)/(uavinbaselist.get(j).getElevation())))
			{
				System.out.println(j+"台无人机需要走的最短路程是"+mindiatance);
				uabinbasepath.get(j).getTaskpointlist().add(taskpointlist.get(tag));
				uavinbaselist.get(j).setLongitude(taskpointlist.get(tag).getLongitude());
				uavinbaselist.get(j).setLatitude(taskpointlist.get(tag).getLatitude());
				uavinbaselist.get(j).setGotodistance(mindiatance);
				System.out.println(j+"台无人机可以走的距离是"+uavinbaselist.get(j).getGotodistance()+"米");
				uavinbaselist.get(j).setMovetime((mindiatance/1000)/(uavinbaselist.get(j).getElevation()));
				System.out.println(j+"台无人机走了"+uavinbaselist.get(j).getMovetime()+"小时");
				taskpointlist.remove(tag);
			}
			else
			{
				uavinbaselist.get(j).setCanmove(false);
			}			
		}

		getMoreTask(uabinbasepath,uavinbaselist,taskpointlist,limittime);
	/*	for(int i=0;i<uavinbaselist.size();i++)
		{
			double distance=uavinbaselist.get(i).getGotodistance();
			while(taskpointlist.size()>0)
			{
				if(distance>(MapDistance.GetDistance(taskpointlist.get(0).getLongitude(), taskpointlist.get(0).getLatitude(), uavinbaselist.get(i).getLongitude(), uavinbaselist.get(i).getLatitude())+MapDistance.GetDistance(taskpointlist.get(0).getLongitude(), taskpointlist.get(0).getLatitude(), uabinbasepath.get(i).getTaskpointlist().get(0).getLongitude(), uabinbasepath.get(i).getTaskpointlist().get(0).getLatitude())))
				{
					distance=distance-MapDistance.GetDistance(taskpointlist.get(0).getLongitude(),taskpointlist.get(0).getLatitude(), uavinbaselist.get(i).getLongitude(), uavinbaselist.get(i).getLatitude());
					uabinbasepath.get(i).getTaskpointlist().add(taskpointlist.get(0));
					uavinbaselist.get(i).setLongitude(taskpointlist.get(0).getLongitude());
					uavinbaselist.get(i).setLatitude(taskpointlist.get(0).getLatitude());								
					taskpointlist.remove(0);
					if(taskpointlist.size()==0)
					{
						uabinbasepath.get(i).getTaskpointlist().add(uabinbasepath.get(i).getTaskpointlist().get(0));
						uavinbaselist.get(i).setLongitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLongitude());
						uavinbaselist.get(i).setLatitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLatitude());
						return ;
					}
					
				}
				else
				{
					uabinbasepath.get(i).getTaskpointlist().add(uabinbasepath.get(i).getTaskpointlist().get(0));
					uavinbaselist.get(i).setLongitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLongitude());
					uavinbaselist.get(i).setLatitude(uabinbasepath.get(i).getTaskpointlist().get(0).getLatitude());
					break;
				}
			}
		}*/
	}
	
	public int getWaitUAV(List<UAVInBase> uavinbaselist)
	{
		int tag=0;
		double mintime=uavinbaselist.get(tag).getMovetime();
		for(int k=0;k<uavinbaselist.size();k++)
		{
			if(uavinbaselist.get(k).isCanmove())
			{
				tag=k;
				mintime=uavinbaselist.get(k).getMovetime();
				break;
			}
		}
		for(int i=1;i<uavinbaselist.size();i++)
		{
			if(uavinbaselist.get(i).getMovetime()<mintime&&uavinbaselist.get(i).isCanmove())
			{
				mintime=uavinbaselist.get(i).getMovetime();
				tag=i;
			}
		}
		if(uavinbaselist.get(tag).isCanmove())
		return tag;
		else 
			return -1;
	}
}
