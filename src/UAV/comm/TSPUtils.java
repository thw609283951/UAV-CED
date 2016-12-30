package UAV.comm;

import java.util.ArrayList;
import java.util.List;

import UAV.entity.Point;

public class TSPUtils {
	
	/**
	 * 获取停靠点遍历序列
	 * @param czPs 
	 * @param BestTour 字符串类型序列 ："6;11;13;2;0;10;1;5;4;14;3;12;9;8;7;"
	 * @return List<Point>
	 */
	public static List<Point> getPointSequence(List<Point> czPs, String bestTour) {
		String[] points = bestTour.split(";");
		int tag = 0; //仓库点标志
		for(int i=0;i<points.length;i++) {
			if(Integer.parseInt(points[i])==0) {
				tag = i;
				break;
			}
		}
		
		List<Point> poList = new ArrayList<Point>();
		for(int i=tag;i<points.length;i++) {
			poList.add(czPs.get(Integer.parseInt(points[i])));
		}
		for(int j=0;j<tag;j++) {
			poList.add(czPs.get(Integer.parseInt(points[j])));
		}
		return poList;
	}
	
	public static void main(String[] args) {
		String bestTour = "6;11;13;2;10;1;5;4;14;3;12;9;8;7;0;";
		List<Point> pointList = new ArrayList<Point>();
		List<Point> poList = new ArrayList<Point>();
		for(int i=0;i<15;i++) {
			Point point = new Point();
			point.setId(i);
			point.setLatitude(i + 0.0);
			point.setLongitude(i + 0.0);
			poList.add(point);
		}
		pointList = TSPUtils.getPointSequence(poList, bestTour);
		for(Point point:pointList) {
			System.out.print(point.getId());
			System.out.print(point.getLatitude());
			System.out.print(point.getLongitude());
			System.out.print("***");
		}
	}


}
