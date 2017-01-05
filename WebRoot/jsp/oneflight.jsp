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
      	//z子区域 j飞机号
    	function goWay(x1,y1,x2,y2,j,z){  
    			//console.log("z=",z);
		       	startLong[z][j] = endLong[z][j];  
		       	startLat[z][j] = endLat[z][j];  
		       	endLong[z][j] = getRound(endLong[z][j],x1,x2);  
		       	endLat[z][j] = getRound(endLat[z][j],y1,y2);  
		       	drawIcon(startLong,startLat,endLong,endLat,j,z);  
		   }   	
		     
		   	function getRound(temp,x,y){  
		       return temp+(x-y)/2;
		   }   
/* 		   function changepoint(j,k){//修改经过点
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
 */		  /*  function doloop(j){
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
		    } */
		   	function drawGreenLine(startLong,startLat,endLong,endLat){  
		       	var polyline = new BMap.Polyline([  
		                                          new BMap.Point(startLong,startLat),//起始点的经纬度  
		                                          new BMap.Point(endLong,endLat)//终止点的经纬度  
		                                          ], {strokeColor:"green",//设置颜色   
		                                          strokeWeight:3, //宽度  
		                                          strokeOpacity:1});//透明度  
		       	map.addOverlay(polyline);  
		   	}  
		   	function drawIcon(startLong,startLat,endLong,endLat,j,z){  
		   		console.log("flight:",j+2*z);
		       	if(carMk[j+z*2+1]){  
		           	map.removeOverlay(carMk[2*z+j+1]);  
		       	}  
		       	carMk[j+z*2+1] = new BMap.Marker(  
		                   new BMap.Point(endLong[z][j],endLat[z][j]),//起始点的经纬度  
		                  {icon:myIcon});  
		       	map.addOverlay(carMk[2*z+j+1]);  
		       	drawGreenLine(startLong[z][j],startLat[z][j],endLong[z][j],endLat[z][j]);  
		   	}  
		   	
		function UAVflight1() {//负责控制子区域1的所有飞行
			for (var wc=0;wc<2;wc++){
				if(wc==0) UAVflight11();
				else if(wc==1) UAVflight12();
			}
		}
		function UAVflight2(){//负责控制子区域2的所有飞行
			for (var wc=0;wc<2;wc++){
				if(wc==0) UAVflight21();
				else if(wc==1) UAVflight22();
			}
		}
		function UAVZoneNumber(j,z) {
			if(j==0){
				console.log("j=",j);
				UAVIndex[z][0]++;
			}
			if(j==1){
				console.log("j=",j);
				UAVIndex[z][1]++;
			}
		}
		function UAVflight11(){//负责一号飞机的飞行
			ex[0][0]=UAVFlight11[UAVIndex[0][0]][1][0];
			ey[0][0]=UAVFlight11[UAVIndex[0][0]][1][1];
			sx[0][0]=UAVFlight11[UAVIndex[0][0]][0][0];
			sy[0][0]=UAVFlight11[UAVIndex[0][0]][0][1];
			startLong[0][0]=sx[0][0];
			startLat[0][0]=sy[0][0];
			endLong[0][0]=sx[0][0];
			endLat[0][0]=sy[0][0];
			clock111=setInterval("goWay(ex[0][0],ey[0][0],sx[0][0],sy[0][0],0,0)",1000);
			clock112=setInterval("changepoint11(0,0)",2000);
			setTimeout("map.removeOverlay(carMk[0])", UAVFlight11[UAVIndex[0][0]].length*2000-1000);
			setTimeout("UAVZoneNumber(0,0)",UAVFlight11[UAVIndex[0][0]].length*2000);
		}
		
		function UAVflight12(){//负责二号飞机的飞行
			ex[0][1]=UAVFlight12[UAVIndex[0][1]][1][0];
			ey[0][1]=UAVFlight12[UAVIndex[0][1]][1][1];
			sx[0][1]=UAVFlight12[UAVIndex[0][1]][0][0];
			sy[0][1]=UAVFlight12[UAVIndex[0][1]][0][1];
			startLong[0][1]=sx[0][1];
			startLat[0][1]=sy[0][1];
			endLong[0][1]=sx[0][1];
			endLat[0][1]=sy[0][1];
			clock121=setInterval("goWay(ex[0][1],ey[0][1],sx[0][1],sy[0][1],1,0)",1000);
			clock122=setInterval("changepoint12(1,1)",2000);
			setTimeout("map.removeOverlay(carMk[1])", UAVFlight12[UAVIndex[0][1]].length*2000-1000);
			setTimeout("UAVZoneNumber(1,0)",UAVFlight12[UAVIndex[0][1]].length*2000);
		}
		function UAVflight21(){//负责一号飞机的飞行
			ex[1][0]=UAVFlight21[UAVIndex[1][0]][1][0];
			ey[1][0]=UAVFlight21[UAVIndex[1][0]][1][1];
			sx[1][0]=UAVFlight21[UAVIndex[1][0]][0][0];
			sy[1][0]=UAVFlight21[UAVIndex[1][0]][0][1];
			startLong[1][0]=sx[1][0];
			startLat[1][0]=sy[1][0];
			endLong[1][0]=sx[1][0];
			endLat[1][0]=sy[1][0];
			clock211=setInterval("goWay(ex[1][0],ey[1][0],sx[1][0],sy[1][0],0,1)",1000);
			clock212=setInterval("changepoint21(0,0)",2000);
			setTimeout("map.removeOverlay(carMk[3])", UAVFlight21[UAVIndex[1][0]].length*2000-1000);
			setTimeout("UAVZoneNumber(0,1)",UAVFlight21[UAVIndex[1][0]].length*2000);
		}
		
		function UAVflight22(){//负责二号飞机的飞行
			ex[1][1]=UAVFlight22[UAVIndex[1][1]][1][0];
			ey[1][1]=UAVFlight22[UAVIndex[1][1]][1][1];
			sx[1][1]=UAVFlight22[UAVIndex[1][1]][0][0];
			sy[1][1]=UAVFlight22[UAVIndex[1][1]][0][1];
			startLong[1][1]=sx[1][1];
			startLat[1][1]=sy[1][1];
			endLong[1][1]=sx[1][1];
			endLat[1][1]=sy[1][1];
			clock221=setInterval("goWay(ex[1][1],ey[1][1],sx[1][1],sy[1][1],1,1)",1000);
			clock222=setInterval("changepoint22(1,1)",2000);
			setTimeout("map.removeOverlay(carMk[4])", UAVFlight22[UAVIndex[1][1]].length*2000-1000);
			setTimeout("UAVZoneNumber(1,1)",UAVFlight22[UAVIndex[1][1]].length*2000);
		}
		//clock[子区域][飞机号][1表示飞机图标，2表示路径图标]
		var clock111,clock112;
		var clock121,clock122;
		var clock211,clock212;
		var clock221,clock222;
		//function changepoint[子区域][飞机号]
		function changepoint11(j,k){//修改经过点
				//console.log("pointnumber[j]=",pointnumber[j]);
		      //console.log("UAVFlight1[0][0][UAVIndex[0][0]].length=",UAVFlight1[UAVIndex[0][j]].length);
		        if (pointnumber[0][j]==UAVFlight11[UAVIndex[0][j]].length-1){
		            pointnumber[0][j]=1;
		            clearInterval(clock111);
		            clearInterval(clock112);
		        }
		        else{
		             pointnumber[0][j]++;
		        }
		       console.log(k);
		        sx[0][k]=ex[0][k];
		        sy[0][k]=ey[0][k];
		        ex[0][k]=UAVFlight11[UAVIndex[0][j]][pointnumber[0][j]][0];
		        ey[0][k]=UAVFlight11[UAVIndex[0][j]][pointnumber[0][j]][1];
		    }
		//changepoint[子区域][飞机号]
		function changepoint12(j,k){//修改经过点
		        if (pointnumber[0][j]==UAVFlight12[UAVIndex[0][j]].length-1){
		            pointnumber[0][j]=1;
		            clearInterval(clock121);
		            clearInterval(clock122);
		        }
		        else{
		             pointnumber[0][j]++;
		        }
		       console.log(k);
		        sx[0][k]=ex[0][k];
		        sy[0][k]=ey[0][k];
		        ex[0][k]=UAVFlight12[UAVIndex[0][j]][pointnumber[0][j]][0];
		        ey[0][k]=UAVFlight12[UAVIndex[0][j]][pointnumber[0][j]][1];
		    }    
		function changepoint21(j,k){//修改经过点
		        if (pointnumber[1][j]==UAVFlight21[UAVIndex[1][j]].length-1){
		            pointnumber[1][j]=1;
		            clearInterval(clock211);
		            clearInterval(clock212);
		        }
		        else{
		             pointnumber[1][j]++;
		        }
		       console.log("k=",k);
		        sx[1][k]=ex[1][k];
		        sy[1][k]=ey[1][k];
		        ex[1][k]=UAVFlight21[UAVIndex[1][j]][pointnumber[1][j]][0];
		        ey[1][k]=UAVFlight21[UAVIndex[1][j]][pointnumber[1][j]][1];
		    }
		function changepoint22(j,k){//修改经过点
		        if (pointnumber[1][j]==UAVFlight22[UAVIndex[1][j]].length-1){
		            pointnumber[1][j]=1;
		            clearInterval(clock221);
		            clearInterval(clock222);
		        }
		        else{
		             pointnumber[1][j]++;
		        }
		       console.log(k);
		        sx[1][k]=ex[1][k];
		        sy[1][k]=ey[1][k];
		        ex[1][k]=UAVFlight22[UAVIndex[1][j]][pointnumber[1][j]][0];
		        ey[1][k]=UAVFlight22[UAVIndex[1][j]][pointnumber[1][j]][1];
		    }   
		
    </script>
  </body>
</html>
