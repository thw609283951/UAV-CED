package UAV.listener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.HashMap;
import com.google.common.*;

import UAV.comm.Ipserver;
public class Server extends Thread
{
	public void run()
	{
        try{
            ServerSocket serverSocket = new ServerSocket(2334);
            Socket socket = null;
            int count = 0;
            System.out.println("----网络控制平台启动----");
            while (true)
            {
                socket = serverSocket.accept();
                ReceiveServerThread receiveServerThread = new ReceiveServerThread(socket);
                receiveServerThread.start();
                SendServerThread sendServerThread = new SendServerThread(socket);
                sendServerThread.start(); 
                count++;
                System.out.println("第"+count+"台无人机接入");
            }
 
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 		
	}
}
