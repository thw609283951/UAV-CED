package UAV.dao;

import java.util.List;

import UAV.entity.UAVInBase;

public interface UAVInBaseDAO {

	public boolean addUAVInBase(UAVInBase uavinbase);
	public boolean modifyUAVInBase(UAVInBase uavinbase);
	public boolean modifyUAVInBase(String ipaddreaa,int port,double longitude,double latitude);
	public List<UAVInBase> searchAllUAVInBase();	
	public int searchUAVInBaseTotal();
	public UAVInBase  searchUAVInBase(UAVInBase uavinbase);
	public boolean deleteUAVInBase(String ipaddress,int port);
}
