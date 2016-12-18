package UAV.comm;

import java.awt.geom.Point2D;
import java.awt.Polygon;
import java.util.List;

import UAV.entity.ZonePoint;

public class PolygonPoint {
	
	public static Polygon CreatePolygon(List<ZonePoint> polygon)
	{
		Polygon p = new Polygon();

		final int TIMES = 1000000;
		for (ZonePoint d : polygon) 
		{
			int x = (int)(d.getLongitude()*TIMES);
			int y = (int)(d.getLatitude()*TIMES);
			p.addPoint(x, y);
		}
		return p;
	}
	
	public static boolean checkWithJdkPolygon(Point2D.Double point, Polygon p) 
	{

		final int TIMES = 1000000;
		int x = (int)( point.x * TIMES);
		int y = (int) (point.y * TIMES);
		return p.contains(x, y);
	}

}
