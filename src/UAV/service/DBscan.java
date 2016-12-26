package UAV.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import UAV.entity.DockPoint;
import UAV.service.Utility;

public class DBscan {
	private final static double e=0.005;//ε半径
	private final static int minp=3;//密度阈值
	private static List<DockPoint> pointsList=new ArrayList<DockPoint>();//存储原始样本点
 	private static List<List<DockPoint>> resultList=new ArrayList<List<DockPoint>>();//存储最后的聚类结果
 	private static List<DockPoint> notclassed=new ArrayList<DockPoint>();//存放没有被分类的点

 	private static void applyDbscan() throws IOException{
 		pointsList=Utility.getPointsList();
 		for(int index=0;index<pointsList.size();++index){
 			List<DockPoint> tmpLst=new ArrayList<DockPoint>();
 			DockPoint p=pointsList.get(index);
 			if(p.isClassed()){
 				continue; 
 			}
 			tmpLst=Utility.isKeyPoint(pointsList, p, e, minp);
 			if(tmpLst!=null){
 				resultList.add(tmpLst);
 			}
 			else{
 				List<DockPoint> tmp=Utility.notkeyPoint(pointsList, p, e, minp);
 				for (DockPoint i:tmp){
 					if(notclassed.contains(i)){
 						continue;
 					}
 					else{
 						notclassed.add(i);
 					}
 				}
 			}
 		}
 		int length=resultList.size();
 		for(int i=0;i<length;++i){
 			for(int j=0;j<length;++j){
 				if(i!=j){
 					if(Utility.mergeList(resultList.get(i), resultList.get(j))){//合并两个链表
 						resultList.get(j).clear();
 					}
 				}
 			}
 		}
	}
 	public static void main(String[] args) {
 		try {
 			//调用DBSCAN的实现算法
 			applyDbscan();
 			Utility.display(resultList);
 			Utility.display_notclassed(notclassed);
 			int number=0;
 			for (DockPoint i:notclassed){
 				Utility.input_not_classed_point(resultList,i);
 			}
 			notclassed.clear();
 			Utility.display(resultList);
 			Utility.display_notclassed(notclassed);
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}     
 	}
}
