package UAV.dao;

import java.util.List;

import UAV.entity.*;

public interface ExpressPathArrangeDAO {
	public List<Point> getTestCzPoints();
	public List<DockPoint> getAllDockPoints();
	public List<WarePoint> getAllWarePoints();
	public List<NeedPoint> getAllNeedPoints();
	
}
