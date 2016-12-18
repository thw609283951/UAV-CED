package UAV.service;

import java.util.List;

import UAV.dao.ZoneDAO;
import UAV.entity.Page;
import UAV.entity.ZonePoint;
import UAV.factory.ZoneDAOFactory;

public class ZoneInformationService 
{
	public ZoneInformationService() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public boolean addZone(ZonePoint zonePoint)
	{
		ZoneDAO uod=ZoneDAOFactory.getInstance();
		return uod.addZonePoint(zonePoint);
	}

	public boolean deleteZone(double longitude,double latitude)
	{
		ZoneDAO uod=ZoneDAOFactory.getInstance();
		return uod.deleteZonePoint(longitude,latitude);
	}
	
	public List<ZonePoint> searchAllZone()
	{
		ZoneDAO uod=ZoneDAOFactory.getInstance();
		List<ZonePoint> uol = uod.searchAllZonePoint();
		if(uol!=null&&uol.size()>0)
		{
			return uol;
		}
		else
		{
			return null;
		}		
	}		
	public int searchZoneTotal()
	{
		ZoneDAO uod=ZoneDAOFactory.getInstance();
		int uol = uod.searchZonePointTotal();
		return uol;
		
	}
	public List<ZonePoint> searchAllZone(Page page){		
		ZoneDAO rd=ZoneDAOFactory.getInstance();
		List<ZonePoint> rl = rd.searchZonePointLike(page);
		if(rl!=null&&rl.size()>0)
		{
			return rl;
		}
		else
		{
			return null;
		}										
	}
}
