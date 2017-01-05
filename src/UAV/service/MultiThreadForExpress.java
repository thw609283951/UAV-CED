package UAV.service;

import java.util.ArrayList;
import java.util.List;

import UAV.entity.ChildZone;
import UAV.entity.Point;

/**
 * 
 * @author tuomingxiang
 *多线程运行快递路径规划
 */
public class MultiThreadForExpress implements Runnable{
	private Thread t;
	private ChildZone childZone;
	private int id;
	public MultiThreadForExpress(ChildZone childZone,int id) {
		// TODO Auto-generated constructor stub
		this.childZone = childZone;
		this.id=id;
	}
	public void run() {
		System.out.println("Thread "+id+" start time:"+System.currentTimeMillis());
		List<Point> czPoints = childZone.getCzPoints();
		double[][] pDis = ExpressPathArrangeService.getPointDisByRoad(czPoints);
		//List<Point> carPath = carPathArrange(czPoints, pDis);
		childZone.setCzPoints((ArrayList<Point>) ExpressPathArrangeService.carPathArrange(czPoints, pDis));//TODO: 不确定这句话对不对
		ExpressPathArrangeService.UAVArrange(childZone);
		System.out.println("Thread "+id+" end time:"+System.currentTimeMillis());
	}
	public void start(){
		if (t == null) {
	         t = new Thread (this);
	         t.start ();
	      }
	}
}
