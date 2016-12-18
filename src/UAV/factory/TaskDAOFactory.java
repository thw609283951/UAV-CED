package UAV.factory;

import UAV.dao.TaskDAO;
import UAV.daoimpl.TaskDAOImpl;

public class TaskDAOFactory {

	public TaskDAOFactory() 
	{
		
	}
	public static TaskDAO getInstance()
	{
		return new TaskDAOImpl();
	}
}
