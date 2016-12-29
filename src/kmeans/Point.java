package kmeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import UAV.comm.MapDistance;
import UAV.entity.NeedPoint;

public class Point{
 
    private NeedPoint p;
    private int cluster_number = 0;
 
    public Point(double x, double y)
    {
        this.getP().setLongitude(x);
        this.getP().setLatitude(y);
    }
    public Point(NeedPoint s)
    {
        this.p = s;
    }
    public void setCluster(int n) {
        this.cluster_number = n;
    }
    
    public int getCluster() {
        return this.cluster_number;
    }
    
    //Calculates the distance between two points.
    protected static double distance(Point p, Point centroid) {
    	
        return getDistanceByAir(p.getP(),centroid.getP());
    }
    
    //Creates random point
    protected static Point createRandomPoint(int min, int max) {
    	Random r = new Random();
    	double x = min + (max - min) * r.nextDouble();
    	double y = min + (max - min) * r.nextDouble();
    	return new Point(x,y);
    }
    
    protected static List<Point> createRandomPoints(int min, int max, int number) {
    	List<Point> points = new ArrayList<Point>(number);
    	for(int i = 0; i < number; i++) {
    		points.add(createRandomPoint(min,max));
    	}
    	return points;
    }
    
    public String toString() {
    	return "("+p.getLongitude()+","+p.getLatitude()+")";
    }

	public NeedPoint getP() {
		return p;
	}

	public void setP(NeedPoint p) {
		this.p = p;
	}

	public static List<Point> createPoints(List<NeedPoint> s) {
		// TODO Auto-generated method stub
		List<Point> points = new ArrayList<Point>();
		for (NeedPoint s0 : s){
			Point tmp_point = new Point(s0);
			points.add(tmp_point);
		}
		return points;
	}
	public void setP(double newX, double newY) {
		// TODO Auto-generated method stub
		this.p.setLongitude(newX);
		this.p.setLatitude(newY);
	}
	
	public static double getDistanceByAir(NeedPoint a, NeedPoint b) {
		return MapDistance.GetDistance(a.getLongitude(), 
										a.getLatitude(),
										b.getLongitude(),
										b.getLatitude());
	}
}
