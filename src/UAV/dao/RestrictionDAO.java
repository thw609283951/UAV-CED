package UAV.dao;

import java.util.List;

import UAV.entity.Page;
import UAV.entity.Restriction;

public interface RestrictionDAO {
	public boolean addRestriction(Restriction restriction);
	public boolean modifyRestriction(Restriction restriction);
	public List<Restriction> searchAllRestriction();
	public Restriction searchRestriction(String version);
	public List<Restriction> searchRestrictionLike(Page page);
	public int searchRestrictionTotal();
	public boolean deleteRestriction(String version);
}
