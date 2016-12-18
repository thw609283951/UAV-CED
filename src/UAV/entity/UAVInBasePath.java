package UAV.entity;

import java.util.List;

public class UAVInBasePath {

	public UAVInBasePath()
	{
		super();
	}
	public UAVInBasePath(UAVInBase uavinbase,List<TaskPoint> taskpointlist)
	{
		this.uavainbase=uavinbase;
		this.taskpointlist=taskpointlist;
	}
	private UAVInBase uavainbase;
	private List<TaskPoint> taskpointlist;
	public UAVInBase getUavainbase() {
		return uavainbase;
	}
	public void setUavainbase(UAVInBase uavainbase) {
		this.uavainbase = uavainbase;
	}
	public List<TaskPoint> getTaskpointlist() {
		return taskpointlist;
	}
	public void setTaskpointlist(List<TaskPoint> taskpointlist) {
		this.taskpointlist = taskpointlist;
	}
	

}
