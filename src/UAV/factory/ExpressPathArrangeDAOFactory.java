package UAV.factory;

import UAV.dao.ExpressPathArrangeDAO;
import UAV.daoimpl.ExpressPathArrangeDAOImpl;

public class ExpressPathArrangeDAOFactory {

	public ExpressPathArrangeDAOFactory() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static ExpressPathArrangeDAO getInstance() {
		return new ExpressPathArrangeDAOImpl();
	}
	
}
