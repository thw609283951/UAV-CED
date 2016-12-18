package UAV.service;
import java.util.List;

import UAV.dao.RestrictionDAO;
import UAV.entity.Page;
import UAV.entity.Restriction;
import UAV.factory.RestrictionDAOFactory;

public class RestrictionInformationService {

	public RestrictionInformationService() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean addRestriction(Restriction restriction)
	{
		RestrictionDAO uod=RestrictionDAOFactory.getInstance();
		return uod.addRestriction(restriction);
	}
	
	public boolean modifyRestriction(Restriction restriction)
	{
		RestrictionDAO uod=RestrictionDAOFactory.getInstance();
		return uod.modifyRestriction(restriction);
	}
	
	public boolean deleteRestriction(String version)
	{
		RestrictionDAO uod=RestrictionDAOFactory.getInstance();
		return uod.deleteRestriction(version);
	}
	
	public List<Restriction> searchAllRestriction()
	{
		RestrictionDAO uod=RestrictionDAOFactory.getInstance();
		List<Restriction> uol = uod.searchAllRestriction();
		if(uol!=null&&uol.size()>0)
		{
			return uol;
		}
		else
		{
			return null;
		}		
	}	
	public Restriction searchRestriction(String version)
	{
		RestrictionDAO uod=RestrictionDAOFactory.getInstance();
		Restriction uol = uod.searchRestriction(version);
		if(uol!=null)
		{
			return uol;
		}
		else
		{
			return null;
		}		
	}	
	public int searchRestrictionTotal()
	{
		RestrictionDAO uod=RestrictionDAOFactory.getInstance();
		int uol = uod.searchRestrictionTotal();
		return uol;
		
	}
	public List<Restriction> searchAllRestriction(Page page)
	{		
		RestrictionDAO rd=RestrictionDAOFactory.getInstance();
		List<Restriction> rl = rd.searchRestrictionLike(page);
		if(rl!=null&&rl.size()>0)
		{
			return rl;
		}
		else
		{
			return null;
		}										
	}
}
