package UAV.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import UAV.entity.DockPoint;

//测试用

public final class Utility {
	//计算两点之间的距离
	public static double getDistance(DockPoint p,DockPoint q){
		double dx=p.getLongitude()-q.getLongitude();
		double dy=p.getLatitude()-q.getLatitude();
		double distance=Math.sqrt(dx*dx+dy*dy);
		return distance;
	}
		//检测p点是不是核心点，tmpLst存储核心点的直达点
	public static List<DockPoint> isKeyPoint(List<DockPoint> lst,DockPoint p,double e,int minp){
		int count=0;
		List<DockPoint> tmpLst=new ArrayList<DockPoint>();
		for(Iterator<DockPoint> it=lst.iterator();it.hasNext();){
			DockPoint q=it.next();
			if(getDistance(p,q)<=e){
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
	//获取文本中的样本点集合
	public static List<DockPoint> getPointsList() throws IOException{
		List<DockPoint> lst=new ArrayList<DockPoint>();
		String txtPath="src\\UAV\\service\\points.txt";
		BufferedReader br=new BufferedReader(new FileReader(txtPath));
		String str="";
		while((str=br.readLine())!=null && str!=""){
			lst.add(new DockPoint(str));
		}
		br.close();
		return lst;
	}
	   //显示聚类的结果
	public static void display(List<List<DockPoint>> resultList){
		int index=1;
		for(Iterator<List<DockPoint>> it=resultList.iterator();it.hasNext();){
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
}
