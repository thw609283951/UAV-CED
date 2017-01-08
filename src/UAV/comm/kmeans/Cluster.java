package UAV.comm.kmeans;

/* 
 * KMeans.java ; Cluster.java ; Point.java
 * 用于快递需求点划分的Kmeans算法
 *
*/

import java.util.ArrayList;
import java.util.List;

import UAV.entity.NeedPoint;

public class Cluster {
	
	public List<Point> points;
	public Point centroid;
	public int id;
	
	//Creates a new Cluster
	public Cluster(int id) {
		this.id = id;
		this.points = new ArrayList<Point>();
		this.centroid = null;
	}

	public List<Point> getPoints() {
		return points;
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public Point getCentroid() {
		return centroid;
	}

	public void setCentroid(Point centroid) {
		this.centroid = centroid;
	}

	public int getId() {
		return id;
	}
	
	public void clear() {
		points.clear();
	}
	
	public void plotCluster() {
		System.out.println("[Cluster: " + id+"]");
		System.out.println("[Centroid: " + centroid + "]");
		System.out.println("[Points: \n");
		for(Point p : points) {
			System.out.println(p);
		}
		System.out.println("]");
	}

	public ArrayList<NeedPoint> getNeedPoints() {
		// TODO Auto-generated method stub
		ArrayList<NeedPoint> need_points = new ArrayList<NeedPoint>();
		for (Point point : this.points){
			need_points.add(point.getP());
		}
		return need_points;
	}

}