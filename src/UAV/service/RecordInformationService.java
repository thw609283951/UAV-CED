package UAV.service;

import java.util.List;

import UAV.dao.RecordDAO;
import UAV.entity.Record;
import UAV.entity.Page;
import UAV.factory.RecordDAOFactory;

public class RecordInformationService 
{
	public RecordInformationService() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public boolean addRecord(Record record)
	{
		RecordDAO uod=RecordDAOFactory.getInstance();
		return uod.addRecord(record);
	}
	public boolean modifyRecord(Record record)
	{
		RecordDAO bd=RecordDAOFactory.getInstance();
		return bd.modifyRecord(record);
	}
	public List<Record> searchAllRecord()
	{
		RecordDAO uod=RecordDAOFactory.getInstance();
		List<Record> uol = uod.searchAllRecord();
		if(uol!=null&&uol.size()>0)
		{
			return uol;
		}
		else
		{
			return null;
		}		
	}		
	public int searchRecordTotal()
	{
		RecordDAO uod=RecordDAOFactory.getInstance();
		int uol = uod.searchRecordTotal();
		return uol;
		
	}
	public List<Record> searchAllRecord(Page page)
	{		
		RecordDAO rd=RecordDAOFactory.getInstance();
		List<Record> rl = rd.searchRecordLike(page);
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
