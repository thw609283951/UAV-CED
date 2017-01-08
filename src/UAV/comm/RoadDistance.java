package UAV.comm;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.plaf.synth.Region;

import net.sf.json.JSONObject;

public class RoadDistance {
	
	
	private final static String region = "哈尔滨";
	private final static String ak = "kDUDetLj7Tcg8gQ2CMzr2RHDC7FeR46x";
	
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
   
	public static double getDistanceByRoad(double lng1,double lat1,  double lng2,double lat2) throws Exception {
		String url = "http://api.map.baidu.com/direction/v1?mode=driving&origin=" + lat1 + "," + lng1 +
				"&destination=" + lat2+","+lng2 + "&origin_region="+ region+"&destination_region="+ region +"&output=json&ak=" + ak;
//		System.out.println(url);
		String json = loadJSON(url);
		JSONObject obj = JSONObject.fromObject(json);
		if(obj.get("status").toString().equals("0")){

			System.out.println("success road dis");
			return ((JSONObject)obj.getJSONObject("result").getJSONArray("routes").get(0)).getDouble("distance");
		}else{
        	System.out.println(url);
        	System.out.println(obj.get("message").toString());
        	System.out.println(obj.get("status").toString());
        	throw new Exception("get Road Distance Error!");
        }
	}
    
}
