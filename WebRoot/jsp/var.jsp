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
  
  <body>
    <script type="text/javascript">
    	var all1=[];
		var all2=[];
		var	q1 = new BMap.Point(126.660917,45.759976);
		var q2 = new BMap.Point(126.665166,45.760425);
		var q3 = new BMap.Point(126.651304,45.751143);
		var q4 = new BMap.Point(126.629932,45.737052);
		var index=[1,1];
		var flightnumber=[0,1,2,3,4,5];//飞机编码
		var pointnumber=[[1,1],[1,1]];
        var o=[0,1];
        //下列是模拟真实数据
        var UAVWarePoints=${warePoints};
		var UAVDockPoints=${dockPoints};
		var UAVNeedPoints=${needPoints};
		var UAVCars=${carsPath};
		var UAVFlights=${uavsPath};
		var UAVsInEveryCar=${uavsInEveryCar};
		
   		var carMk=
   		[
   		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
			0,
		</c:forEach>
		];
   		var sx=[
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
			[
		   	<c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   	0,
			</c:forEach>
			],
		</c:forEach>
		];
		var sy=[
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
			[
		   	<c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   	0,
			</c:forEach>
			],
		</c:forEach>
		];
		var ex=[
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
			[
		   	<c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   	0,
			</c:forEach>
			],
		</c:forEach>
		];
		var ey=[
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
			[
		   	<c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   	0,
			</c:forEach>
			],
		</c:forEach>
		];
        var tmp=0;
        var endLong=[
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
			[
		   	<c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   	0,
			</c:forEach>
			],
		</c:forEach>
		];
		var endLat=[
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
			[
		   	<c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   	0,
			</c:forEach>
			],
		</c:forEach>
		];
		var startLong=[
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
			[
		   	<c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   	0,
			</c:forEach>
			],
		</c:forEach>
		];
		var startLat=[
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
			[
		   	<c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   	0,
			</c:forEach>
			],
		</c:forEach>
		];
		//以下通过模板创建
		//UAVFlight[子区域][飞机号]
		//UAVIndex[子区域][飞机号列表]
		var UAVIndex=[
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
			[
		   	<c:forEach var="j" begin="1" end="${uavsInEveryCar}">
			   	0,
			</c:forEach>
			],
		</c:forEach>
		];
		<c:forEach var="i" begin="1" end="${fn:length(carsPath)}">
		   <c:forEach var="j" begin="1" end="${uavsInEveryCar}">
		   var UAVFlight${i}${j}=UAVFlights[2*${i}+${j}-3];
		   console.log(UAVFlight${i}${j});
			</c:forEach>
		</c:forEach>
		
    </script>
  </body>
</html>
