package UAV.service;

import java.util.List;

import UAV.dao.TaskDAO;
import UAV.entity.Page;
import UAV.entity.TaskPoint;
import UAV.factory.TaskDAOFactory;

public class TaskInformationService {

	public TaskInformationService() 
	{
		
	}
	
	public boolean addTask(TaskPoint taskPoint)
	{
		TaskDAO uod=TaskDAOFactory.getInstance();
		return uod.addTaskPoint(taskPoint);
	}

	public boolean deleteTask(double longitude,double latitude)
	{
		TaskDAO uod=TaskDAOFactory.getInstance();
		return uod.deleteTaskPoint(longitude,latitude);
	}
	
	public List<TaskPoint> searchAllTask()
	{
		TaskDAO uod=TaskDAOFactory.getInstance();
		List<TaskPoint> uol = uod.searchAllTaskPoint();
		if(uol!=null&&uol.size()>0)
		{
			return uol;
		}
		else
		{
			return null;
		}		
	}		
	public int searchTaskTotal()
	{
		TaskDAO uod=TaskDAOFactory.getInstance();
		int uol = uod.searchTaskPointTotal();
		return uol;
		
	}
}
