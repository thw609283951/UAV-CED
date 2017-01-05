<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
<script type="text/javascript" src="../scripts/jquery.js"></script>
<script src="http://cache.amap.com/lbs/static/es5.min.js"></script>

<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=MLSjqW8E6qbunua0gTEvjTWMXqrjwC6x"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
  </head>
    <script type="text/javascript">
      
    	function goWay(x1,y1,x2,y2,j){  
		       	startLong[j] = endLong[j];  
		       	startLat[j] = endLat[j];  
		       	endLong[j] = getRound(endLong[j],x1,x2);  
		       	endLat[j] = getRound(endLat[j],y1,y2);  
		       	drawIcon(startLong,startLat,endLong,endLat,j);  
		   }   	
		     
		   	function getRound(temp,x,y){  
		       return temp+(x-y)/2;
		   }   
		   function changepoint(j,k){//修改经过点
		        console.log(pointnumber[j]);
		        console.log(road[j].length);
		        if (pointnumber[j]==road[j].length-1){
		            pointnumber[j]=0;
		            clearInterval(clock1);
		            clearInterval(clock2);
		        }
		        else{
		             pointnumber[j]++;
		        }
		       console.log(k);
		        sx[k]=ex[k];
		        sy[k]=ey[k];
		        ex[k]=road[j][pointnumber[j]][0];
		        ey[k]=road[j][pointnumber[j]][1];
		    }
		   function doloop(j){
		        index[j]++;
		        console.log(j);
		        if  (index[j]==2){
		            index=0;
		            console.log("111");
		            clearInterval(clock1);
		            clearInterval(clock2);
		        }
		        else{
		            goWay(ex,ey,sx,sy,j);
		        }
		    }
		   	function drawGreenLine(startLong,startLat,endLong,endLat){  
		       	var polyline = new BMap.Polyline([  
		                                          new BMap.Point(startLong,startLat),//起始点的经纬度  
		                                          new BMap.Point(endLong,endLat)//终止点的经纬度  
		                                          ], {strokeColor:"green",//设置颜色   
		                                          strokeWeight:3, //宽度  
		                                          strokeOpacity:1});//透明度  
		       	map.addOverlay(polyline);  
		   	}  
		   	function drawIcon(startLong,startLat,endLong,endLat,j){  
		       	if(carMk[j]){  
		           	map.removeOverlay(carMk[j]);  
		       	}  
		       	carMk[j] = new BMap.Marker(  
		                   new BMap.Point(endLong[j],endLat[j]),//起始点的经纬度  
		                  {icon:myIcon});  
		       	map.addOverlay(carMk[j]);  
		       	drawGreenLine(startLong[j],startLat[j],endLong[j],endLat[j]);  
		   	}  
		   	
		function UAVflight1() {//负责控制子区域1的所有飞行
			for (var wc=0;wc<2;wc++){
				if(wc==0) UAVflight11();
				else if(wc==1) UAVflight12();
			}
		}
		function UAVflight2(){//负责控制子区域2的所有飞行
			
		}
		function UAVZoneNumber(j) {
			if(j==0){
				console.log("j=",j);
				UAVIndex[0]++;
			}
			if(j==1){
				console.log("j=",j);
				UAVIndex[1]++;
			}
		}
		function UAVflight11(){//负责一号飞机的飞行
			ex[0]=UAVFlight1[UAVIndex[0]][1][0];
			ey[0]=UAVFlight1[UAVIndex[0]][1][1];
			sx[0]=UAVFlight1[UAVIndex[0]][0][0];
			sy[0]=UAVFlight1[UAVIndex[0]][0][1];
			startLong[0]=sx[0];
			startLat[0]=sy[0];
			endLong[0]=sx[0];
			endLat[0]=sy[0];
			clock11=setInterval("goWay(ex[0],ey[0],sx[0],sy[0],0)",1000);
			clock12=setInterval("changepoint1(0,0)",2000);
			setTimeout("map.removeOverlay(carMk[0])", UAVFlight1[UAVIndex[0]].length*2000-1000);
			setTimeout("UAVZoneNumber(0)",UAVFlight1[UAVIndex[0]].length*2000);
		}
		
		function UAVflight12(){//负责二号飞机的飞行
			ex[1]=UAVFlight2[UAVIndex[1]][1][0];
			ey[1]=UAVFlight2[UAVIndex[1]][1][1];
			sx[1]=UAVFlight2[UAVIndex[1]][0][0];
			sy[1]=UAVFlight2[UAVIndex[1]][0][1];
			startLong[1]=sx[1];
			startLat[1]=sy[1];
			endLong[1]=sx[1];
			endLat[1]=sy[1];
			clock21=setInterval("goWay(ex[1],ey[1],sx[1],sy[1],1)",1000);
			clock22=setInterval("changepoint2(1,1)",2000);
			setTimeout("map.removeOverlay(carMk[1])", UAVFlight2[UAVIndex[1]].length*2000-1000);
			setTimeout("UAVZoneNumber(1)",UAVFlight2[UAVIndex[1]].length*2000);
		}
		var clock11,clock12;
		var clock21,clock22;
		function changepoint1(j,k){//修改经过点
				console.log("pointnumber[j]=",pointnumber[j]);
		        console.log("UAVFlight1[0][0][UAVIndex[0]].length=",UAVFlight1[UAVIndex[j]].length);
		        if (pointnumber[j]==UAVFlight1[UAVIndex[j]].length-1){
		            pointnumber[j]=1;
		            clearInterval(clock11);
		            clearInterval(clock12);
		        }
		        else{
		             pointnumber[j]++;
		        }
		       console.log(k);
		        sx[k]=ex[k];
		        sy[k]=ey[k];
		        ex[k]=UAVFlight1[UAVIndex[j]][pointnumber[j]][0];
		        ey[k]=UAVFlight1[UAVIndex[j]][pointnumber[j]][1];
		    }
		function changepoint2(j,k){//修改经过点
		        console.log(pointnumber[j]);
		        console.log(road[j].length);
		        if (pointnumber[j]==UAVFlight2[UAVIndex[j]].length-1){
		            pointnumber[j]=1;
		            clearInterval(clock21);
		            clearInterval(clock22);
		        }
		        else{
		             pointnumber[j]++;
		        }
		       console.log(k);
		        sx[k]=ex[k];
		        sy[k]=ey[k];
		        ex[k]=UAVFlight2[UAVIndex[j]][pointnumber[j]][0];
		        ey[k]=UAVFlight2[UAVIndex[j]][pointnumber[j]][1];
		    }    
		    
		
    </script>
  </body>
</html>
