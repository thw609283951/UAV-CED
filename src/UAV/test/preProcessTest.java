package UAV.test;

import java.util.List;

import UAV.comm.kdtree.KDTree;
import UAV.comm.kdtree.KeyDuplicateException;
import UAV.comm.kdtree.KeySizeException;
import UAV.dao.ExpressPathArrangeDAO;
import UAV.entity.DockPoint;
import UAV.entity.NeedPoint;
import UAV.factory.ExpressPathArrangeDAOFactory;

public class preProcessTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		ExpressPathArrangeDAO epaDao = ExpressPathArrangeDAOFactory.getInstance();
//		List<DockPoint> allDockPoints = epaDao.getAllDockPoints();
//		List<NeedPoint> allNeedPoints = epaDao.getAllNeedPoints();
////		for (NeedPoint needPoint : allNeedPoints) {
////			System.out.println(needPoint.getId());
////		}
////		System.out.println("********");
////		for (DockPoint dockPoint : allDockPoints) {
////			System.out.println(dockPoint.getId());
////		}
////		
//		KDTree<Integer> kdTree = new KDTree<Integer>(2);
//		
//		for (DockPoint dockPoint : allDockPoints) {
//			double[] coord = {dockPoint.getLongitude().doubleValue(),
//					dockPoint.getLatitude().doubleValue()};
//			try {
//				System.out.println("kkkkk"+dockPoint.getId());
//				kdTree.insert(coord, dockPoint.getId());
//			} catch (KeySizeException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (KeyDuplicateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		System.out.println("dock\t\tneed");
//		for (NeedPoint needPoint : allNeedPoints) {
//			double[] coord = {needPoint.getLongitude().doubleValue(),
//					needPoint.getLatitude().doubleValue()};
//			Integer id = null;
//			try {
//				id = kdTree.nearest(coord);
//				System.out.println(id + "\t\t" + needPoint.getId());
//			} catch (KeySizeException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
		
		
		
		
		
		
		
	}

	
	
}
