package UAV.dao;

import java.util.List;

import UAV.entity.Point;

public interface ExpressPathArrangeDAO {
	public List<Point> getTestCzPoints();
	public List<Point> getAllDockPoints();
	public List<Point> getAllWarePoints();
	public List<Point> getAllNeedPoints();
	
}
