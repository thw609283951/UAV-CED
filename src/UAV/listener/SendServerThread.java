package UAV.listener;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import UAV.comm.Ipserver;
import UAV.entity.UAVOnline;
import UAV.service.UAVOnlineInformationService;

public class SendServerThread extends Thread 
{
	Socket socket = null;
	OutputStream os = null;
	PrintWriter pw=null;
    String []strArrayStrings=new String[9]; 
	public SendServerThread(Socket socket)
	{
		this.socket = socket;
	}
	public void run() 
	{
		
		System.out.println("-----send开始服务-----");
		try {
			os = socket.getOutputStream();
			pw = new PrintWriter(os); 
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
			int port=this.socket.getPort();
			InetAddress ipaddress=this.socket.getInetAddress();
			String ipstr=ipaddress.toString();
			if(ipstr.equals("/127.0.0.1"))
			{
				ipstr=Ipserver.GetAddressIP();
			}
			UAVOnline uavonline=new UAVOnline();
			UAVOnline uav= new UAVOnline();
			uavonline.setIpaddress(ipstr);
			uavonline.setPort(port);
			UAVOnlineInformationService uois=new UAVOnlineInformationService();
			String packet;
			while(socket!= null)
			{
				uav=uois.searchUAVOnline(uavonline);
			if (uav!= null) 
			{
				System.out.println("----send server is working----");
				packet = uav.getIpaddress() + "#"
						+ uav.getLongitude() + "#"
						+ uav.getLatitude() + "#"
						+ uav.getElevation() + "#"
						+ uav.getHeight() + "#" 
						+ uav.getVelocity()
						+ "#" + uav.getPowerconsumption() + "#"
						+ uav.getRemainingpower() + "#"
						+ uav.getVersion();
				if (packet!= null) 
				{
					System.out.println("----send server is working----");
					pw.write(packet);
					//System.out.println(packet);
					pw.flush();
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
			if (pw != null)
				pw.close();
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (socket != null)
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
	}
}
