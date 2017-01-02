package UAV.test;

import java.util.List;

import UAV.comm.MapDistance;
import UAV.comm.kdtree.KDTree;
import UAV.comm.kdtree.KeyDuplicateException;
import UAV.comm.kdtree.KeySizeException;
import UAV.dao.ExpressPathArrangeDAO;
import UAV.entity.DockPoint;
import UAV.entity.NeedPoint;
import UAV.entity.WarePoint;
import UAV.factory.ExpressPathArrangeDAOFactory;

public class arrangeWarePoint {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KDTree<Integer> kdTree = new KDTree<Integer>(2);
		ExpressPathArrangeDAO epadao = ExpressPathArrangeDAOFactory.getInstance();
		List<WarePoint> warePoints = epadao.getAllWarePoints();
		List<DockPoint> dockPoints = epadao.getAllDockPoints();
		for (WarePoint warePoint : warePoints) {
			double[] coord = {warePoint.getLongitude().doubleValue(),
					warePoint.getLatitude().doubleValue()};
			try {
				kdTree.insert(coord, warePoint.getId());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		System.out.println("dockpoint\t\twarepoint");
		for (DockPoint dockPoint : dockPoints) {
			double[] coord = {dockPoint.getLongitude().doubleValue(),
					dockPoint.getLatitude().doubleValue()};
			Integer id = null;
			try {
				id = kdTree.nearest(coord);
				dockPoint.setWrid(id);
				System.out.println(dockPoint.getId() + "\t\t" + dockPoint.getWrid() );
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

}
