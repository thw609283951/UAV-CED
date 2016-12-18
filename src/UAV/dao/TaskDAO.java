package UAV.dao;

import java.util.List;

import UAV.entity.Page;
import UAV.entity.TaskPoint;

public interface TaskDAO {

	public boolean addTaskPoint(TaskPoint taskPoint);
	public List<TaskPoint> searchAllTaskPoint();
	public int searchTaskPointTotal();
	public boolean deleteTaskPoint(double longitude,double latitude);
}
