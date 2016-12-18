package UAV.comm;

import UAV.service.UAVOnlineInformationService;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true)
		{
         UAVOnlineInformationService u=new UAVOnlineInformationService();
         u.searchAllUAVOnline();
         System.out.println("%%%%%%%%%%%%%%%%");
         try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

}
