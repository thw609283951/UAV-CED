package UAV.comm.kmeans;

/* 
 * KMeans.java ; Cluster.java ; Point.java
 * 用于快递需求点划分的Kmeans算法
 *
*/

import java.util.ArrayList;
import java.util.List;

import UAV.entity.NeedPoint;

import UAV.comm.kmeans.Point;
import UAV.comm.kmeans.Cluster;
public class KMeans {

	//Number of Clusters. This metric should be related to the number of points
    private int NUM_CLUSTERS = 10;    
    //Number of Points
    //private int NUM_POINTS = 150;
    //Min and Max X and Y
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 100;
    
    private List<Point> points;
    private List<Cluster> clusters;
    
    public KMeans() {
    	this.points = new ArrayList<Point>();
    	this.setClusters(new ArrayList<Cluster>());    	
    }
    
    //Initializes the process
    public void init(List<NeedPoint> s) {
    	//Set Points
//    	points = Point.createRandomPoints(MIN_COORDINATE,MAX_COORDINATE,NUM_POINTS);
    	points = Point.createPoints(s);
    	//Create Clusters
    	//Set Random Centroids
    	for (int i = 0; i < getNUM_CLUSTERS(); i++) {
    		Cluster cluster = new Cluster(i);
    		//可以随意设置中心点，但不能设置样本点。
    		
    		Point centroid = new Point(points.get(i).getP().getLongitude(),points.get(i).getP().getLatitude());
    		cluster.setCentroid(centroid);
    		getClusters().add(cluster);
    	}
    	
    	//Print Initial state
    	plotClusters();
    }

	private void plotClusters() {
    	for (int i = 0; i < getNUM_CLUSTERS(); i++) {
    		Cluster c = getClusters().get(i);
    		c.plotCluster();
    	}
    }
    
	//The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(!finish) {
        	//Clear cluster state��delete all the points in cluser
        	clearClusters();
        	
        	List<Point> lastCentroids = getCentroids();
        	
        	//Assign points to the closer cluster
        	assignCluster();
            
            //Calculate new centroids.
        	calculateCentroids();
        	
        	iteration++;
        	
        	List<Point> currentCentroids = getCentroids();
        	
        	//Calculates total distance between new and old Centroids
        	double distance = 0;
        	for(int i = 0; i < lastCentroids.size(); i++) {
        		distance += Point.distance(lastCentroids.get(i),currentCentroids.get(i));
        	}
        	System.out.println("#################");
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid distances: " + distance);
        	plotClusters();
        	        	
        	if(distance == 0) {
        		finish = true;
        	}
        }
    }
    
    private void clearClusters() {
    	for(Cluster cluster : getClusters()) {
    		cluster.clear();
    	}
    }
    
    private List<Point> getCentroids() {//获取所有的中心点
    	List<Point> centroids = new ArrayList<Point>(getNUM_CLUSTERS());
    	for(Cluster cluster : getClusters()) {
    		Point aux = cluster.getCentroid();
    		Point point = new Point(aux.getP().getLongitude(),aux.getP().getLatitude());
    		centroids.add(point);
    	}
    	return centroids;
    }
    
    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max; 
        int cluster = 0;                 
        double distance = 0.0; 
        
        for(Point point : points) {
        	min = max;
            for(int i = 0; i < getNUM_CLUSTERS(); i++) {
            	Cluster c = getClusters().get(i);
                distance = Point.distance(point, c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            point.setCluster(cluster);
            getClusters().get(cluster).addPoint(point);
        }
    }
    
    private void calculateCentroids() {//更新簇中心点
        for(Cluster cluster : getClusters()) {
            List<Point> list = cluster.getPoints();
            Point centroid = Point.GetCenterPointFromListOfCoordinates(list);
            cluster.setCentroid(centroid);
        }
    }

	public List<Cluster> getClusters() {
		return clusters;
	}

	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}

	public int getNUM_CLUSTERS() {
		return NUM_CLUSTERS;
	}

	public void setNUM_CLUSTERS(int nUM_CLUSTERS) {
		NUM_CLUSTERS = nUM_CLUSTERS;
	}
}