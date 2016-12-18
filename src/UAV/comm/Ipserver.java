package UAV.comm;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Ipserver {

	 public static String GetAddressIP()
     {
		 InetAddress ia=null;
		 try 
		 {
			ia=InetAddress.getLocalHost();
		} 
		 catch (UnknownHostException e) 
		 {
			e.printStackTrace();
		}
		String localip=ia.getHostAddress();
        return localip;
     }
}
