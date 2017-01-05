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
		
		
		var endLong = [[126.637637,126.644249],[126.637637, 126.644249]];  // 几架飞机就设几个
		var endLat = [[45.752744,45.748719],[126.637637, 126.644249]]; 
		var startLong = [[126.637637,126.644249],[126.637637, 126.644249]]; 
		var startLat = [[45.752744,45.748719],[126.637637, 126.644249]]; 
   		var carMk=[0,0,0,0,0,0,0]; 
		var sx = [[126.637637, 126.644249],[126.637637, 126.644249]];
        var sy = [[45.752744, 45.748719],[126.637637, 126.644249]];
        var ex = [[126.63735, 126.649998],[126.637637, 126.644249]];
        var ey = [[45.751436, 45.746605],[126.637637, 126.644249]]; 
        
		
		//以下通过模板创建
		//UAVFlight[子区域][飞机号]
		var UAVFlight11=[//2表示子区域飞机
							[[126.63735, 45.75214], [126.637062, 45.753951], [126.637637, 45.752744], [126.63735, 45.75214]],
							[[126.645111, 45.752542], [126.644608, 45.753146], [126.645111, 45.752542]],
							[[126.644968, 45.747209], [126.644249, 45.748719], [126.64353, 45.746304], [126.644968, 45.747209]],
						];
		
		var UAVFlight12=[
						[[126.63735, 45.75214], [126.635769, 45.751436], [126.63735, 45.751436], [126.63735, 45.75214]],
						[[126.645111, 45.752542], [126.646549, 45.75385], [126.645111, 45.752542]],
						[[126.644968, 45.747209], [126.649998, 45.746605], [126.644968, 45.747209]],
					   ];
		var UAVFlight21=[
					[[126.65007, 45.728539], [126.649279, 45.727583], [126.65007, 45.728539]], 
					[[126.655819, 45.73171], [126.6574, 45.73171], [126.655819, 45.73171]],
					[[126.649926, 45.73317], [126.647537, 45.734723], [126.649926, 45.73317]],
				   ];			
		var UAVFlight22=[
					[[126.65007, 45.728539], [126.649208, 45.730301], [126.648058, 45.72869], [126.65007, 45.728539]], 
					[[126.655819, 45.73171], [126.657256, 45.733321], [126.655819, 45.73171]], 
					[[126.649926, 45.73317]]
				];
		//UAVIndex[子区域][飞机号列表]
		var UAVIndex=[[0,0,0,0,0,0],[0,0,0]];
		
		<c:forEach var="i" begin="1" end="5">
   			<c:out value="var UAVFlight${i}=${uavsPath[0]}"/>
		</c:forEach>
    </script>
  </body>
</html>
