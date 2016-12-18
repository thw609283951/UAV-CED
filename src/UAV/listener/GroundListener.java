package UAV.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GroundListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) 
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void contextInitialized(ServletContextEvent arg0) 
	{
		// TODO Auto-generated method stub
		Server sh = new Server();
		sh.start();
	}
}