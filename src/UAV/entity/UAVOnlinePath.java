package UAV.entity;

import java.util.ArrayList;
import java.util.List;

public class UAVOnlinePath {

	public UAVOnlinePath()
	{
		super();
	}
	public UAVOnlinePath(UAVOnline uavonline,List<ZonePoint> zonepointlist)
	{
		this.uavaonline=uavonline;
		this.zonepointlist=zonepointlist;
	}
	private UAVOnline uavaonline;
	private List<ZonePoint> zonepointlist;
	public UAVOnline getUavaonline() {
		return uavaonline;
	}
	public void setUavaonline(UAVOnline uavaonline) {
		this.uavaonline = uavaonline;
	}
	public List<ZonePoint> getZonepointlist() {
		return zonepointlist;
	}
	public void setZonepointlist(List<ZonePoint> zonepointlist) {
		this.zonepointlist = zonepointlist;
	}
	
}
