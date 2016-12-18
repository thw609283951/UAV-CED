package UAV.service;

import java.util.ArrayList;
import java.util.List;

import UAV.comm.MapDistance;
import UAV.dao.HistoricalPathDAO;
import UAV.dao.UAVOnlineDAO;
import UAV.entity.HistoricalPath;
import UAV.entity.UAVOnline;
import UAV.entity.UAVOnlinePath;
import UAV.entity.ZonePoint;
import UAV.factory.HistoricalPathDAOFactory;
import UAV.factory.UAVOnlineDAOFactory;


public class UAVOnlineInformationService {

	public UAVOnlineInformationService() 
	{
		// TODO Auto-generated constructor stub
	}
	public boolean addUAVOnline(UAVOnline uavonline)
	{
		UAVOnlineDAO uod=UAVOnlineDAOFactory.getInstance();
		return uod.addUAVOnline(uavonline);
	}
	public boolean modifyUAVOnline(UAVOnline uavonline)
	{
		UAVOnlineDAO uod=UAVOnlineDAOFactory.getInstance();
		HistoricalPathDAO hpd=HistoricalPathDAOFactory.getInstance();
		UAVOnline ul=uod.searchUAVOnline(uavonline);
		boolean b=hpd.addHistoricalPath(new HistoricalPath(ul.getIpaddress(),ul.getPort(),ul.getLongitude(),ul.getLatitude()));
		if(b)
		{
			return uod.modifyUAVOnline(uavonline);
		}
		else
		{
			return false;
		}
	}
	public boolean modifyUAVOnline(String ipaddress, int port,double longitude,double latitude )
	{
		UAVOnlineDAO uod=UAVOnlineDAOFactory.getInstance();
		HistoricalPathDAO hpd=HistoricalPathDAOFactory.getInstance();
		UAVOnline uavonline=new UAVOnline();
		uavonline.setIpaddress(ipaddress);
		uavonline.setPort(port);
		UAVOnline ul=uod.searchUAVOnline(uavonline);
		boolean b=hpd.addHistoricalPath(new HistoricalPath(ul.getIpaddress(),ul.getPort(),ul.getLongitude(),ul.getLatitude()));
		if(b)
		{
			return uod.modifyUAVOnline(uavonline.getIpaddress(),uavonline.getPort(),longitude,latitude);
		}
		else
		{
			return false;
		}
	}
	
	public boolean deleteUAVOnline(String ipaddress,int port)
	{
		UAVOnlineDAO uod=UAVOnlineDAOFactory.getInstance();
		return uod.deleteUAVOnline(ipaddress,port);
	}
	public List<UAVOnline> searchAllUAVOnline()
	{
		UAVOnlineDAO uod=UAVOnlineDAOFactory.getInstance();
		List<UAVOnline> uol = uod.searchAllUAVOnline();
		if(uol!=null&&uol.size()>0)
		{
			return uol;
		}
		else
		{
			return null;
		}		
	}
	public UAVOnline searchUAVOnline(UAVOnline uavonline)
	{
		UAVOnlineDAO uod=UAVOnlineDAOFactory.getInstance();
		UAVOnline uol = uod.searchUAVOnline(uavonline);
		if(uol!=null)
		{
			return uol;
		}
		else
		{
			return null;
		}		
	}
	/*List<UAVOnline> uavonline在线无人机列表
	 * List<ZonePoint> zonepointlist需要覆盖的点的列表
	 * */
	public void getUVAOnlineCoverPath(List<UAVOnlinePath> uavonlinepath,List<UAVOnline> uavonline,List<ZonePoint> zonepointlist)
	{
		if(zonepointlist.size()<1)
		{
			return ;
		}
		else
		{
			/*if((zonepointlist.size()>=uavonline.size())&&(uavonlinepath.get(0).getZonepointlist().size()<2))
			{
				for(int j=0;j<uavonline.size();j++)
				{
					double longitude=uavonline.get(j).getLongitude();
					double latitude=uavonline.get(j).getLatitude();
					double mindiatance=MapDistance.GetDistance(longitude, latitude, zonepointlist.get(0).getLongitude(), zonepointlist.get(0).getLatitude());
					int tag=0;
					for(int i=1;i<zonepointlist.size();i++)
					{
						double distance=MapDistance.GetDistance(longitude, latitude, zonepointlist.get(i).getLongitude(), zonepointlist.get(i).getLatitude());
						if(distance<mindiatance)
						{
							mindiatance=distance;
							tag=i;
						}
					}
					uavonlinepath.get(j).getZonepointlist().add(zonepointlist.get(tag));
					uavonline.get(j).setLongitude(zonepointlist.get(tag).getLongitude());
					uavonline.get(j).setLatitude(zonepointlist.get(tag).getLatitude());
					zonepointlist.remove(tag);
					
				}*/
			int number= (zonepointlist.size()%uavonline.size()==0)?zonepointlist.size()/uavonline.size():(zonepointlist.size()/uavonline.size()+1);
			//System.out.println("一共有"+zonepointlist.size()+"个点");
			for(int j=0;j<uavonline.size();j++)
			{
				for(int k=1;(zonepointlist.size()>0&&(k<=number));k++)
				{
					double longitude=uavonline.get(j).getLongitude();
					double latitude=uavonline.get(j).getLatitude();
					double mindiatance=MapDistance.GetDistance(longitude, latitude, zonepointlist.get(0).getLongitude(), zonepointlist.get(0).getLatitude());
					int tag=0;
					for(int i=1;i<zonepointlist.size();i++)
					{
						double distance=MapDistance.GetDistance(longitude, latitude, zonepointlist.get(i).getLongitude(), zonepointlist.get(i).getLatitude());
						if(distance<mindiatance)
						{
							mindiatance=distance;
							tag=i;
						}
					}
					uavonlinepath.get(j).getZonepointlist().add(zonepointlist.get(tag));
					uavonline.get(j).setLongitude(zonepointlist.get(tag).getLongitude());
					uavonline.get(j).setLatitude(zonepointlist.get(tag).getLatitude());
					zonepointlist.remove(tag);
					//System.out.println(j+"台无人机覆盖了第"+k+"个点");
				}
				while(zonepointlist.size()>0)
				{
					double longitude=uavonline.get(j).getLongitude();
					double latitude=uavonline.get(j).getLatitude();
					double mindiatance=MapDistance.GetDistance(longitude, latitude, zonepointlist.get(0).getLongitude(), zonepointlist.get(0).getLatitude());
					int tag=0;
					for(int i=1;i<zonepointlist.size();i++)
					{
						double distance=MapDistance.GetDistance(longitude, latitude, zonepointlist.get(i).getLongitude(), zonepointlist.get(i).getLatitude());
						if(distance<mindiatance)
						{
							mindiatance=distance;
							tag=i;
						}
					}
					if(zonepointlist.get(tag).getLongitude()==uavonline.get(j).getLongitude())
					{
						uavonlinepath.get(j).getZonepointlist().add(zonepointlist.get(tag));
						uavonline.get(j).setLongitude(zonepointlist.get(tag).getLongitude());
						uavonline.get(j).setLatitude(zonepointlist.get(tag).getLatitude());
						zonepointlist.remove(tag);
					}
					else
						break;
					
					//System.out.println(j+"台无人机覆盖了第"+k+"个点");
				}
			}
			/*if(zonepointlist.size()>=uavonline.size())
			{
				for(int j=0;j<uavonline.size();j++)
				{
					double longitude=uavonline.get(j).getLongitude();
					double latitude=uavonline.get(j).getLatitude();
					double mindiatance=MapDistance.GetDistance(longitude, latitude, zonepointlist.get(0).getLongitude(), zonepointlist.get(0).getLatitude());
					int tag=0;
					for(int i=1;i<zonepointlist.size();i++)
					{
						double distance=MapDistance.GetDistance(longitude, latitude, zonepointlist.get(i).getLongitude(), zonepointlist.get(i).getLatitude());
						if(distance<mindiatance)
						{
							mindiatance=distance;
							tag=i;
						}
					}
					uavonlinepath.get(j).getZonepointlist().add(zonepointlist.get(tag));
					uavonline.get(j).setLongitude(zonepointlist.get(tag).getLongitude());
					uavonline.get(j).setLatitude(zonepointlist.get(tag).getLatitude());
					zonepointlist.remove(tag);
					
				}			
			}*/
			/*else
			{
				double mindiatance=MapDistance.GetDistance(uavonline.get(0).getLongitude(), uavonline.get(0).getLatitude(), zonepointlist.get(0).getLongitude(), zonepointlist.get(0).getLatitude());
				int uavtag=0;
				int zonepointtag=0;
				for(int j=0;j<uavonline.size();j++)
				{
					double longitude=uavonline.get(j).getLongitude();
					double latitude=uavonline.get(j).getLatitude();
					for(int k=0;k< zonepointlist.size();k++)
					{
						double distance=MapDistance.GetDistance(longitude, latitude, zonepointlist.get(k).getLongitude(), zonepointlist.get(k).getLatitude());
						if(distance<mindiatance)
						{
							mindiatance=distance;
							uavtag=j;
							zonepointtag=k;
						}	
					}
					
				    	
				}
				uavonlinepath.get(uavtag).getZonepointlist().add(zonepointlist.get(zonepointtag));
				System.out.println(uavonlinepath.get(uavtag).getUavaonline().getLongitude());	
				uavonline.get(uavtag).setLongitude(zonepointlist.get(zonepointtag).getLongitude());
				uavonline.get(uavtag).setLatitude(zonepointlist.get(zonepointtag).getLatitude());	
				zonepointlist.remove(zonepointtag);
			}*/
			/*else
			{
				double mindiatance=MapDistance.GetDistance(uavonline.get(0).getLongitude(), uavonline.get(0).getLatitude(), zonepointlist.get(0).getLongitude(), zonepointlist.get(0).getLatitude());
				int uavtag=0;
				for(int j=1;j<uavonline.size();j++)
				{
					double longitude=uavonline.get(j).getLongitude();
					double latitude=uavonline.get(j).getLatitude();					
					double distance=MapDistance.GetDistance(longitude, latitude, zonepointlist.get(0).getLongitude(), zonepointlist.get(0).getLatitude());
					if(distance<mindiatance)
					{
						mindiatance=distance;
						uavtag=j;												
					}									    	
				}
				uavonlinepath.get(uavtag).getZonepointlist().add(zonepointlist.get(0));
				System.out.println(uavonlinepath.get(uavtag).getUavaonline().getLongitude());	
				uavonline.get(uavtag).setLongitude(zonepointlist.get(0).getLongitude());
				uavonline.get(uavtag).setLatitude(zonepointlist.get(0).getLatitude());	
				zonepointlist.remove(0);
			}*/
			
			
			//getUVAOnlineCoverPath(uavonlinepath,uavonline,zonepointlist);
		}
	}
}
