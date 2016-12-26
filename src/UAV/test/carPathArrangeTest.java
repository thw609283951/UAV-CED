package UAV.test;

import java.util.List;

import UAV.dao.ExpressPathArrangeDAO;
import UAV.entity.Point;
import UAV.factory.ExpressPathArrangeDAOFactory;

public class carPathArrangeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExpressPathArrangeDAO EPADAO = ExpressPathArrangeDAOFactory.getInstance();
		List<Point> testPs = EPADAO.getTestCzPoints();
		System.out.println("finish get Ps");
		for (Point point : testPs) {
			System.out.println(point.getId());
		}
	}

}
