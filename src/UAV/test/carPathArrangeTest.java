package UAV.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import UAV.dao.ExpressPathArrangeDAO;
import UAV.entity.Point;
import UAV.factory.ExpressPathArrangeDAOFactory;
import UAV.service.ExpressPathArrangeService;

public class carPathArrangeTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ExpressPathArrangeDAO EPADAO = ExpressPathArrangeDAOFactory.getInstance();
		List<Point> testPs = EPADAO.getTestCzPoints();
		System.out.println("finish get Ps");
//		for (Point point : testPs) {
//			System.out.println(point.getId());
//		}
		double dis[][] = ExpressPathArrangeService.getPointDisByRoad(testPs);
		//System.out.println(dis);
//		for (int i = 0; i < dis.length; i++) {
//			for (int j = 0; j < dis[i].length; j++) {
//				System.out.print(dis[i][j] + "\t");
//			}
//			System.out.println("");
//		} 
//		for(int i=0;i<10;i++) {
			ExpressPathArrangeService expressPathArrangeService = new ExpressPathArrangeService();
			List<Point> carPath = new ArrayList<Point>();
			carPath= expressPathArrangeService.carPathArrange(testPs,dis);
//		}

		
		for(int i=0;i<testPs.size();i++) {
			System.out.println(carPath.get(i).getId());
		}
//		Random random = new Random();
//	     double ss = random.nextDouble();
//	     System.out.println(ss);
	     
	}


}
