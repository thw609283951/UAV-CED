package UAV.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Date;

import UAV.comm.DateAndTime;
import UAV.entity.Record;
import UAV.entity.UAVOnline;
import UAV.service.RecordInformationService;
import UAV.service.UAVOnlineInformationService;

public class ReceiveServerThread extends Thread 
{
	Socket socket = null;
	InputStream is = null;
	InputStreamReader isr = null;
	BufferedReader br = null;
    String []strArrayStrings=new String[9]; 
	public ReceiveServerThread(Socket socket)
	{
		this.socket = socket;
		try 
		{
			this.is=socket.getInputStream();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		try 
		{
			this.isr = new InputStreamReader(is,"utf-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		this.br = new BufferedReader(isr);
	}
	public void run() 
	{
		System.out.println("-----receive开始服务-----");
		try {
			String info = null;
			while((info=br.readLine())!=null)
			{
				//System.out.println("-----receiver server is working-----");
				//System.out.println(info);
				strArrayStrings=info.split("#");
				UAVOnlineInformationService uois=new UAVOnlineInformationService();
				RecordInformationService ris=new RecordInformationService();
				UAVOnline uavonline=new UAVOnline();
				String ipaddress=strArrayStrings[0];
				int port=this.socket.getPort();
				double longitude=Double.parseDouble(strArrayStrings[1]);
				double latitude=Double.parseDouble(strArrayStrings[2]);
				double elevation=Double.parseDouble(strArrayStrings[3]);
			    double height=Double.parseDouble(strArrayStrings[4]);
			    double velocity=Double.parseDouble(strArrayStrings[5]);
			    double powerconsumption=Double.parseDouble(strArrayStrings[6]);
			    int remainingpower=Integer.parseInt(strArrayStrings[7]);
			    String vertion=strArrayStrings[8];
			    uavonline.setIpaddress(ipaddress);
			    uavonline.setPort(port);
				uavonline.setLongitude(longitude);
				uavonline.setLatitude(latitude);
				uavonline.setElevation(elevation);
				uavonline.setVelocity(velocity);
				uavonline.setHeight(height);
				uavonline.setPowerconsumption(powerconsumption);
				uavonline.setRemainingpower(remainingpower);
				uavonline.setVersion(vertion);
				
				Record record=new Record();
				record.setIpaddress(ipaddress);
				record.setPort(port);
				java.util.Date utildate=new Date();
				if(strArrayStrings[1].equals("0"))
				{
					java.sql.Date outdate=DateAndTime.getSqlDatefromUtilDate(utildate);
					record.setOutdate(outdate);
					java.sql.Time outtime=DateAndTime.GetsqlTime(utildate);
					record.setOuttime(outtime);
					if(uois.searchUAVOnline(uavonline)!=null)
					{
						uois.deleteUAVOnline(ipaddress, port);
						ris.modifyRecord(record);
					}
					else
					{
						;
					}
				}
				else
				{
					java.sql.Date indate=DateAndTime.getSqlDatefromUtilDate(utildate);
					record.setIndate(indate);
					java.sql.Time intime=DateAndTime.GetsqlTime(utildate);
					record.setIndate(indate);
					record.setIntime(intime);
					if(uois.searchUAVOnline(uavonline)!=null)
					{
						uois.modifyUAVOnline(uavonline);
					}
					else
					{
						ris.addRecord(record);
						uois.addUAVOnline(uavonline);
					}
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
				if (is != null)
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (isr != null)
					try {
						isr.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (br != null)
					try {
						br.close();
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
