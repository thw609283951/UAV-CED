package UAV.dao;

import java.util.List;

import UAV.entity.BanArea;
import UAV.entity.Page;

public interface  BanAreaDAO 
{
	public boolean addBanArea(BanArea banarea);
	public boolean modifyBanArea(BanArea banarea);
	public List<BanArea> searchAllBanArea();
	public BanArea searchBanArea(double  longitude,double  latitude,double  radius);
	public List<BanArea> searchBanAreaLike(Page page);
	public int searchBanAreaTotal();
	public boolean deleteBanArea(double  longitude,double  latitude);

}
