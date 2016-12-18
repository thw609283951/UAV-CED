package UAV.service;

import java.util.List;

import UAV.comm.MapDistance;
import UAV.dao.BanAreaDAO;
import UAV.entity.BanArea;
import UAV.entity.Page;
import UAV.factory.BanAreaDAOFactory;


public class BanAreaInformationService {
	public BanAreaInformationService() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean addBanArea(BanArea banarea){//避免禁飞区域的重叠
		BanAreaDAO bd=BanAreaDAOFactory.getInstance();
		List<BanArea> bl = bd.searchAllBanArea();
		if(bl!=null&&bl.size()>0)
		{
			for (int i = 0; i < bl.size(); i++) 
			{
				BanArea banareainlist=new BanArea();
				banareainlist=bl.get(i);				
				if(this.isinclude(banarea,banareainlist)!=null)
				{
					if(this.isinclude(banarea,banareainlist)==banarea)
					{
						bd.deleteBanArea(banareainlist.getLongitude(), banareainlist.getLatitude());
						return bd.addBanArea(banarea);
					}
					else
					{
						return true;
					}	
				}
			}			
				return bd.addBanArea(banarea);
		}
		else
		{
			return bd.addBanArea(banarea);
		}		
	}
	
	public boolean modifyBanArea(BanArea banarea)
	{
		BanAreaDAO bd=BanAreaDAOFactory.getInstance();
		return bd.modifyBanArea(banarea);
	}
	
	public boolean deleteBanArea(double longitude,double latitude){
		BanAreaDAO bd=BanAreaDAOFactory.getInstance();
		return bd.deleteBanArea(longitude,latitude);
	}
	
	public List<BanArea> searchAllBanArea()
	{
		BanAreaDAO bd=BanAreaDAOFactory.getInstance();
		List<BanArea> bl = bd.searchAllBanArea();
		if(bl!=null&&bl.size()>0)
		{
			return bl;
		}
		else
		{
			return null;
		}		
	}	
	public BanArea searchBanArea(double longitude,double latitude,double radius)
	{
		BanAreaDAO bd=BanAreaDAOFactory.getInstance();
		BanArea b = bd.searchBanArea(longitude,latitude,radius);
		if(b!=null)
		{
			return b;
		}
		else
		{
			return null;
		}		
	}	
	public int searchBanAreaTotal()
	{
		BanAreaDAO bd=BanAreaDAOFactory.getInstance();
		int btotal = bd.searchBanAreaTotal();
		return btotal;
		
	}
	public List<BanArea> searchAllBanArea(Page page){		
		BanAreaDAO bd=BanAreaDAOFactory.getInstance();
		List<BanArea> bl = bd.searchBanAreaLike(page);
		if(bl!=null&&bl.size()>0){
			return bl;
		}else{
			return null;
		}										
	}
	public BanArea isinclude(BanArea banarea1,BanArea banarea2){//包含时返回半径大的禁飞区对象，否则返回null		
		double Longitude1=banarea1.getLongitude();
		double latitude1=banarea1.getLatitude();
		double radius1=banarea1.getRadius();
		double Longitude2=banarea2.getLongitude();
		double latitude2=banarea2.getLatitude();
		double radius2=banarea2.getRadius();
		double centersdistance=MapDistance.GetDistance(Longitude1, latitude1, Longitude2, latitude2);
		double differenceofradius=Math.abs(radius1-radius2);
		if(differenceofradius>=centersdistance){
			if(radius1>=radius2)
			{
				return banarea1;
			}
			else
			{
				return banarea2;
			}
		}
		else
		{
			return null;
		}										
	}
}
