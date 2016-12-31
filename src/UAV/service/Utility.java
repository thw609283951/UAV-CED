package UAV.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mysql.jdbc.Connection;

import UAV.comm.MapDistance;
import UAV.entity.DockPoint;

//测试用

public final class Utility {
	//计算两点之间的距离
	public static double getDistance(DockPoint p,DockPoint q){
		double dx=p.getLongitude()-q.getLongitude();
		double dy=p.getLatitude()-q.getLatitude();
		double distance=Math.sqrt(dx*dx+dy*dy);
		//double distance = 
		return distance;
	}
	//检测p点是不是核心点，tmpLst存储核心点的直达点
	public static ArrayList<DockPoint> isKeyPoint(List<DockPoint> lst,DockPoint p,double e,int minp){
		int count=0;
		double distance=0;
		ArrayList<DockPoint> tmpLst=new ArrayList<DockPoint>();
		for(Iterator<DockPoint> it=lst.iterator();it.hasNext();){	//遍历lst中的点
			DockPoint q=it.next();
			distance=MapDistance.GetDistance(p.getLatitude(), p.getLongitude(), q.getLatitude(), q.getLongitude());
//			System.out.println("<"+p.getName()+","+q.getName()+">"+distance);
			if(distance<=e){
				++count;
				if(!tmpLst.contains(q)){
					tmpLst.add(q);
				}
			}
		}
		if(count>=minp){
			p.setKey(true);
			return tmpLst;
		}
		return null;
	}
	public static List<DockPoint> notkeyPoint(List<DockPoint> lst,DockPoint p,double e,int minp){
		int count=0;
		List<DockPoint> tmpLst=new ArrayList<DockPoint>();
		for(Iterator<DockPoint> it=lst.iterator();it.hasNext();){//遍历lst中的点
			DockPoint q=it.next();
			//
			if(getDistance(p,q)<=e){
				++count;
				if(!tmpLst.contains(q)){
					tmpLst.add(q);
				}
			}
		}
		return tmpLst;
	}
	//合并两个链表，前提是b中的核心点包含在a中
	public static boolean mergeList(List<DockPoint> a,List<DockPoint> b){
		boolean merge=false;
		if(a==null || b==null){
			return false;
		}
		for(int index=0;index<b.size();++index){
			DockPoint p=b.get(index);
			if(p.isKey() && a.contains(p)){
				merge=true;
				break;
			}
		}
		if(merge){
			for(int index=0;index<b.size();++index){
				if(!a.contains(b.get(index))){
					a.add(b.get(index));
				}
			}
		}
		return merge;
	}
	public static void input_not_classed_point(List<ArrayList<DockPoint>> resultList,DockPoint notclassed){
		int index=0;
		double mindistance=-1;
		double distance=0;
		List<DockPoint> tmp = null;
		for(Iterator<ArrayList<DockPoint>> it=resultList.iterator();it.hasNext();){
			List<DockPoint> lst=it.next();
			if(lst.isEmpty()){
				continue;
			}
			int number=1;
			for(Iterator<DockPoint> it1=lst.iterator();it1.hasNext();){
				DockPoint p=it1.next();
				distance=getDistance(notclassed,p);
				if (mindistance==-1 || mindistance>distance){
					mindistance=distance;
					tmp=lst;
				}
				number++;
			}
			index++;
	    }
		if(!tmp.contains(notclassed)){
			tmp.add(notclassed);
		}
		
	}
	//获取文本中的样本点集合
	public static List<DockPoint> getPointsList() throws IOException{
		//链接数据库，将所有的DockPoint中select=true的点添加到lst中
		//现在是模拟，真实操作的时候需要修改代码
		List<DockPoint> lst=new ArrayList<DockPoint>();
		String txtPath="src\\UAV\\service\\points.txt";
		BufferedReader br=new BufferedReader(new FileReader(txtPath));
		String str="";
		int id=0;
		while((str=br.readLine())!=null && str!=""){
			lst.add(new DockPoint(str,id));
			id++;
		}
		br.close();
		return lst;
	}
	   //显示聚类的结果
	public static void display(List<ArrayList<DockPoint>> resultList){
		int index=1;
		for(Iterator<ArrayList<DockPoint>> it=resultList.iterator();it.hasNext();){
			List<DockPoint> lst=it.next();
			if(lst.isEmpty()){
				continue;
			}
			System.out.println("-----第"+index+"个聚类-----");
			int number=1;
			for(Iterator<DockPoint> it1=lst.iterator();it1.hasNext();){
				DockPoint p=it1.next();
				System.out.print(number);
				number++;
				System.out.println(":"+p.print());
				
			}
			index++;
	    }
	}
	public static void display_notclassed(List<DockPoint> notclassed) {
		System.out.println("这里是未分配的点");
		for(DockPoint i:notclassed){
			System.out.println(i.print());
		}
	}
}
