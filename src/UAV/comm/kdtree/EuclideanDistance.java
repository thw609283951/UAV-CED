// Hamming distance metric class

package UAV.comm.kdtree;

import UAV.comm.MapDistance;

class EuclideanDistance extends DistanceMetric {
//    
//    protected double distance(double [] a, double [] b)  {
//	
//	return Math.sqrt(sqrdist(a, b));
//	
//    }
//    
    protected static double sqrdist(double [] a, double [] b) {
		double lng1 = a[0];
		double lat1 = a[1];
		double lng2 = b[0];
		double lat2 = b[1];
    	double dis = MapDistance.GetDistance(lng1, lat1, lng2, lat2);
    	return dis*dis;
    }     
	
	
	protected double distance(double [] a, double [] b)  {
		double lng1 = a[0];
		double lat1 = a[1];
		double lng2 = b[0];
		double lat2 = b[1];
		return MapDistance.GetDistance(lng1, lat1, lng2, lat2);
	}
	
}
