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
		       return temp+(x-y)*0.05;
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
		       	if(carMk[j+z*2]){  
		           	map.removeOverlay(carMk[2*z+j]);  
		       	}  
		       	carMk[j+z*2] = new BMap.Marker(  
		                   new BMap.Point(endLong[z][j],endLat[z][j]),//起始点的经纬度  
		                  {icon:myIcon});  
		       	map.addOverlay(carMk[2*z+j]);  
		       	drawGreenLine(startLong[z][j],startLat[z][j],endLong[z][j],endLat[z][j]);  
		   	}  
		   	
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
		   function UAVflight${i}() {//负责控制子区域1的所有飞行
				for (var wc=0;wc<${uavsInEveryCar};wc++){
					if(wc==0){
						UAVflight${i}1();
						console.log("wc=",wc);
					}
					<c:forEach var="j" begin="2" end="${uavsInEveryCar}">
					else if(wc==${j}-1) {
							console.log("wc=",wc);
							UAVflight${i}${j}();
						}
					</c:forEach>

				}
			}
		</c:forEach>
		function UAVZoneNumber(j,z) {
			if(j==0){
				console.log("j=",j);
				UAVIndex[z][0]++;
			}
			if(j==1){
				console.log("j=",j);
				UAVIndex[z][1]++;
			}
			if(j==2){
				UAVIndex[z][2]++;
			}
			if(j==3){
				console.log("j=",j);
				UAVIndex[z][3]++;
			}
			if(j==4){
				UAVIndex[z][4]++;
			}
		}
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
		   <c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   function UAVflight${i}${j}(){//负责一号飞机的飞行
					ex[${i}-1][${j}-1]=UAVFlight${i}${j}[UAVIndex[${i}-1][${j}-1]][1][0];
					ey[${i}-1][${j}-1]=UAVFlight${i}${j}[UAVIndex[${i}-1][${j}-1]][1][1];
					sx[${i}-1][${j}-1]=UAVFlight${i}${j}[UAVIndex[${i}-1][${j}-1]][0][0];
					sy[${i}-1][${j}-1]=UAVFlight${i}${j}[UAVIndex[${i}-1][${j}-1]][0][1];
					startLong[${i}-1][${j}-1]=sx[${i}-1][${j}-1];
					startLat[${i}-1][${j}-1]=sy[${i}-1][${j}-1];
					endLong[${i}-1][${j}-1]=sx[${i}-1][${j}-1];
					endLat[${i}-1][${j}-1]=sy[${i}-1][${j}-1];
					clock${i}${j}1=setInterval("goWay(ex[${i}-1][${j}-1],ey[${i}-1][${j}-1],sx[${i}-1][${j}-1],sy[${i}-1][${j}-1],${j}-1,${i}-1)",100);
					clock${i}${j}2=setInterval("changepoint${i}${j}(${j}-1,${j}-1)",2001);
					setTimeout("map.removeOverlay(carMk[${i}+${j}-2])", UAVFlight${i}${j}[UAVIndex[${i}-1][${j}-1]].length*2000-1000);
					setTimeout("UAVZoneNumber(${j}-1,${i}-1)",UAVFlight${i}${j}[UAVIndex[${i}-1][${j}-1]].length*2000);
				}
			</c:forEach>
		</c:forEach>

		//clock[子区域][飞机号][1表示飞机图标，2表示路径图标]
		//clock[子区域][飞机号][1表示飞机图标，2表示路径图标]
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
		   <c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   var clock${i}${j}1,clock${i}${j}2;
			</c:forEach>
		</c:forEach>
		//function changepoint[子区域][飞机号]
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
		   <c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   function changepoint${i}${j}(j,k){//修改经过点
			  		console.log(UAVFlight${i}${j}[UAVIndex[${i}-1][j]].length-1);
			        if (pointnumber[${i}-1][j]==UAVFlight${i}${j}[UAVIndex[${i}-1][j]].length-1){
			            pointnumber[${i}-1][j]=1;
			            clearInterval(clock${i}${j}1);
			            clearInterval(clock${i}${j}2);
			        }
			        else{
			        	console.log(pointnumber[${i}-1][j]);
			             pointnumber[${i}-1][j]++;
			             console.log(pointnumber[${i}-1][j]);
			        }
			        sx[${i}-1][k]=ex[${i}-1][k];
			        sy[${i}-1][k]=ey[${i}-1][k];
			        ex[${i}-1][k]=UAVFlight${i}${j}[UAVIndex[${i}-1][j]][pointnumber[${i}-1][j]][0];
			        ey[${i}-1][k]=UAVFlight${i}${j}[UAVIndex[${i}-1][j]][pointnumber[${i}-1][j]][1];
			    }
			</c:forEach>
		</c:forEach>
		
    </script>
  </body>
</html>
