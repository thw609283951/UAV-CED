package UAV.comm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public  class MapDistance 
{
	private static  double EARTH_RADIUS = 6378.137;
	public static double MAX_Distance=6378137;
	private static double rad(double d)
	{
	   return d * Math.PI / 180.0;	  
	}

	public static double GetDistance(double lng1,double lat1,  double lng2,double lat2)
	{
	   double radLat1 = rad(lat1);
	   double radLat2 = rad(lat2);
	   double a = radLat1 - radLat2;
	   double b = rad(lng1) - rad(lng2);
	   double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
	    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
	   s = s * EARTH_RADIUS;
	   s = s * 10000/ 10;
	   return s;
	}
	
	
	public static boolean ispasscircle(double lng1,double lat1,  double lng2,double lat2,double centerlng,double centerlat,double radius)
	{
		double a=GetDistance(lng1,lat1,lng2,lat2);
		double b=GetDistance(lng1,lat1,centerlng,centerlat);
		double c=GetDistance(centerlng,centerlat,lng2,lat2);
		if(a==0)
		{
			if(GetDistance(lng1,lat1,centerlng,centerlat)<(radius))
			{
				return true;
			}
			else
			{
				return false;
			}				
		}
		else if((GetDistance(lng1,lat1,centerlng,centerlat)<(radius))||(GetDistance(lng2,lat2,centerlng,centerlat)<(radius)))
		{
			return true;
		}
		else
		{
			double d=0.50*(a+b+c);
			BigDecimal d_big=BigDecimal.valueOf(d);
			BigDecimal a_big=BigDecimal.valueOf(a);
			BigDecimal b_big=BigDecimal.valueOf(b);
			BigDecimal c_big=BigDecimal.valueOf(c);
			BigDecimal s_big=BigDecimal.valueOf(1);
			//double s=Math.sqrt(d*(d-a)*(d-b)*(d-c));
			s_big=d_big.multiply(d_big.subtract(a_big));
			s_big=s_big.multiply(d_big.subtract(b_big));
			//此行结束后是是s_big=面积的平方
			s_big=s_big.multiply(d_big.subtract(c_big));
			double h;
			//h_big是h的平方
			BigDecimal h_big=s_big.multiply(BigDecimal.valueOf(4)).divide(a_big.multiply(a_big),12,BigDecimal.ROUND_HALF_EVEN);
			h=Math.sqrt(h_big.doubleValue());	
			if(h>=(radius))
			{
				return false;
			}
			else
			{
				double cosc=(Math.pow(a, 2)+Math.pow(b, 2)-Math.pow(c, 2))/(2*a*b);
				double cosb=(Math.pow(a, 2)+Math.pow(c, 2)-Math.pow(b, 2))/(2*a*c);
				if(cosc<0||cosb<0)
					return false;
				else
					return true;
			
			}
				
			
		}
	}
	public static int[][] Flod(double A[][],double C[][],int n,int P[][])
	{
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				{
					A[i][j]=C[i][j];					
						P[i][j]=-1;				
				}
		for(int k=0;k<n;k++)
			for(int i=0;i<n;i++)
				for(int j=0;j<n;j++)
					if((A[i][k]>0&&A[k][j]>0&&A[i][k]<MAX_Distance&&A[k][j]<MAX_Distance)&&(A[i][k]+A[k][j]<A[i][j]))
					{
						A[i][j]=A[i][k]+A[k][j];
						System.out.println(i+"到"+j+"经过了"+k);
						P[i][j]=k;
					}
		return P;
	}
	public static void Path(ArrayList<Integer> pathpoint,int i,int j,int P[][])
	{
		
		int k=P[i][j];
		if(k!=-1)
		{
			Path(pathpoint,i,k,P);
			pathpoint.add(k);
			Path(pathpoint,k,j,P);
		}
	}
	
	public static void main(String[] args)
	{
		System.out.println(GetDistance(126.633344,45.745630,126.634219,45.745630));
		boolean b=ispasscircle(126.633344,45.745630,126.634219,45.745630,126.633344,45.745019,68.12);
		if(b)
		{
			System.out.println("等于0");
		}
		
	}
	
}
