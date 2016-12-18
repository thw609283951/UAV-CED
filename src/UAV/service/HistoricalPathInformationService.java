package UAV.service;

import java.util.List;

import UAV.dao.HistoricalPathDAO;
import UAV.entity.HistoricalPath;
import UAV.factory.HistoricalPathDAOFactory;

public class HistoricalPathInformationService 
{
	public HistoricalPathInformationService() 
	{
		// TODO Auto-generated constructor stub
	}
	public List<HistoricalPath> search(String ipaddress,int port)
	{
		HistoricalPathDAO hpd=HistoricalPathDAOFactory.getInstance();
		List<HistoricalPath> hpl = hpd.seach(ipaddress,port);
		if(hpl!=null&&hpl.size()>0)
		{
			return hpl;
		}
		else
		{
			return null;
		}		
	}	
}
