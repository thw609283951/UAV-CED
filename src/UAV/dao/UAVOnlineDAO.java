package UAV.dao;

import java.util.List;
import UAV.entity.UAVOnline;
public interface UAVOnlineDAO 
{
	public boolean addUAVOnline(UAVOnline uavonline);
	public boolean modifyUAVOnline(UAVOnline uavonline);
	public boolean modifyUAVOnline(String ipaddreaa,int port,double longitude,double latitude);
	public List<UAVOnline> searchAllUAVOnline();	
	public int searchUAVOnlineTotal();
	public UAVOnline  searchUAVOnline(UAVOnline uavonline);
	public boolean deleteUAVOnline(String ipaddress,int port);
}
