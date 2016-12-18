package UAV.dao;

import java.util.List;

import UAV.entity.HistoricalPath;

public interface HistoricalPathDAO 
{
	public List<HistoricalPath> seach(String ipaddress,int port);
	public boolean addHistoricalPath(HistoricalPath historicalpath);
}
