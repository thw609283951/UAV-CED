package UAV.dao;

import java.util.List;

import UAV.entity.Page;
import UAV.entity.Record;

public interface RecordDAO 
{
	public boolean addRecord(Record record);
	public boolean modifyRecord(Record record);
	public List<Record> searchAllRecord();
	public List<Record> searchRecordLike(Page page);
	public int searchRecordTotal();
}
