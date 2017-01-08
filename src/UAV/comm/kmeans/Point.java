package UAV.comm.kmeans;

/* 
 * KMeans.java ; Cluster.java ; Point.java
 * 用于快递需求点划分的Kmeans算法
 *
*/

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import UAV.comm.MapDistance;
import UAV.entity.NeedPoint;

public class Point{
 
    private NeedPoint p = new NeedPoint();
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
	public static Point GetCenterPointFromListOfCoordinates(List<Point> geoCoordinateList)  
	{  
	    int total = geoCoordinateList.size();  
	    double X = 0, Y = 0, Z = 0;  
	    for(Point g : geoCoordinateList)  
	    {  
	        double lat, lon, x, y, z;  
	        lat = g.getP().getLatitude() * Math.PI / 180;  
	        lon = g.getP().getLongitude() * Math.PI / 180;  
	        x = Math.cos(lat) * Math.cos(lon);  
	        y = Math.cos(lat) * Math.sin(lon);  
	        z = Math.sin(lat);  
	        X += x;  
	        Y += y;  
	        Z += z;  
	    }  
	    X = X / total;  
	    Y = Y / total;  
	    Z = Z / total;  
	    double Lon = Math.atan2(Y, X);  
	    double Hyp = Math.sqrt(X * X + Y * Y);  
	    double Lat = Math.atan2(Z, Hyp);  
	    return new Point(Lon * 180 / Math.PI, Lat * 180 / Math.PI);  
	} 
	public static void main(String[] args) {
		Point x1 = new Point(101.0891846217,38.3671904620);//赵家庄
		Point x2 = new Point(101.1052408697,38.2999572427);//刘家沟
		Point x3 = new Point(101.1773203966,38.3354245243);//郭家湖
		Point x4 = new Point(101.1924604615,38.2802137677);//车家庄
		
		Point center;
		List<Point> psList = new ArrayList<Point>();
		psList.add(x1);
		psList.add(x2);
		psList.add(x3);
		center = GetCenterPointFromListOfCoordinates(psList);
		return;
	}
}
