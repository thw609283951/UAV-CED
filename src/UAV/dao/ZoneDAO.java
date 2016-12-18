package UAV.dao;

import java.util.List;

import UAV.entity.Page;
import UAV.entity.ZonePoint;

public interface ZoneDAO 
{
	public boolean addZonePoint(ZonePoint zonePoint);
	public List<ZonePoint> searchAllZonePoint();
	public List<ZonePoint> searchZonePointLike(Page page);
	public int searchZonePointTotal();
	public boolean deleteZonePoint(double longitude,double latitude);
}
