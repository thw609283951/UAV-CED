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
    			console.log("1");
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
		   	
		function flight(j){
			for (var wc=0;wc<2;wc++){
				if(wc==0)flight1(0);
				else if(wc==1) flight2(1);
			}
		}
		var clock11,clock12;
		var clock21,clock22;
		function flight1(j){
			clock11=setInterval("goWay(ex[0],ey[0],sx[0],sy[0],0)",1000);
			clock12=setInterval("changepoint1(0,0)",2000);
			setTimeout("map.removeOverlay(carMk[0]);", road[j].length*2000-1000);
		}
		function changepoint1(j,k){//修改经过点
		        console.log(pointnumber[j]);
		        console.log(road[j].length);
		        if (pointnumber[j]==road[j].length-1){
		            pointnumber[j]=0;
		            clearInterval(clock11);
		            clearInterval(clock12);
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
		function flight2(j){
			clock21=setInterval("goWay(ex[1],ey[1],sx[1],sy[1],1)",1000);
			clock22=setInterval("changepoint2(1,1)",2000);
			setTimeout("map.removeOverlay(carMk[1]);", road[j].length*2000-1000);
		}
		function changepoint2(j,k){//修改经过点
		        console.log(pointnumber[j]);
		        console.log(road[j].length);
		        if (pointnumber[j]==road[j].length-1){
		            pointnumber[j]=0;
		            clearInterval(clock21);
		            clearInterval(clock22);
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
    </script>
  </body>
</html>
