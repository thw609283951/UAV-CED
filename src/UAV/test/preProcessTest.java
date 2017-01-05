package UAV.test;

import java.util.List;

import UAV.comm.kdtree.KDTree;
import UAV.comm.kdtree.KeyDuplicateException;
import UAV.comm.kdtree.KeySizeException;
import UAV.dao.ExpressPathArrangeDAO;
import UAV.entity.DockPoint;
import UAV.entity.NeedPoint;
import UAV.factory.ExpressPathArrangeDAOFactory;
import UAV.service.ExpressPathArrangeService;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import net.sf.json.JSONObject;

public class preProcessTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		
//		ExpressPathArrangeService arrangeService = new ExpressPathArrangeService();
//		arrangeService.pointPreProcess();
//		
//		System.out.println(getLngAndLat("百度大厦"));
		
		//getDistanceByRoad(116.332556,40.009424,116.316176,39.997743);
		getDistanceByRoad(126.638715,45.749146, 126.6173,45.726199);
	}
//ak=MLSjqW8E6qbunua0gTEvjTWMXqrjwC6x"
	
	public static Map<String,Double> getLngAndLat(String address){
        Map<String,Double> map=new HashMap<String, Double>();
        String url = "http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak=MLSjqW8E6qbunua0gTEvjTWMXqrjwC6x";
        String json = loadJSON(url);
        JSONObject obj = JSONObject.fromObject(json);
        if(obj.get("status").toString().equals("0")){
            double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
            double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
            map.put("lng", lng);
            map.put("lat", lat);
            //System.out.println("经度："+lng+"---纬度："+lat);
        }else{
            //System.out.println("未找到相匹配的经纬度！");
        }
        return map;
    }
	
	public static double getDistanceByRoad(double lng1,double lat1,  double lng2,double lat2) {
		String url = "http://api.map.baidu.com/direction/v1?mode=driving&origin=" + lat1 + "," + lng1 +
				"&destination=" + lat2+","+lng2 + "&origin_region=哈尔滨&destination_region=哈尔滨&output=json&ak=MLSjqW8E6qbunua0gTEvjTWMXqrjwC6x";
		System.out.println(url);
		String json = loadJSON(url);
		JSONObject obj = JSONObject.fromObject(json);
		if(obj.get("status").toString().equals("0")){
//            double lng=obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
//            double lat=obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
//            map.put("lng", lng);
//            map.put("lat", lat);
			
			System.out.println(((JSONObject)obj.getJSONObject("result").getJSONArray("routes").get(0)).getDouble("distance"));
			System.out.println("get success!");
			
            //System.out.println("经度："+lng+"---纬度："+lat);
        }else{
            //System.out.println("未找到相匹配的经纬度！");
        	System.out.println(obj.get("status").toString());
        }
		System.out.println(obj.get("status").toString());
		return 1;
	}
	
	
    public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }
	
}
