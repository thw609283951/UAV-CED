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
		var needpoints=[[126.637637,45.752744 ,"11"],
							[126.63735,45.751436 ,"12"],
							[126.635769,45.751436 ,"13"],
							[126.637062,45.753951,"14"],
							[126.644249,45.748719 ,"21"],
							[126.649998,45.746605 ,"22"],
							[126.64353,45.746304,"23"],
							[126.644608,45.753146 ,"31"],
							[126.646549,45.75385,"32"],
							[126.649279,45.727583 ,"41"],
							[126.648058,45.72869 ,"42"],
							[126.649208,45.730301,"43"],
							[126.6574,45.73171 ,"51"],
							[126.657256,45.733321,"52"],
							[126.647537,45.734723,"61"],
							];
		var flightline=[[126.637637,45.752744 ,"11"],
							[126.63735,45.751436 ,"12"],
							[126.635769,45.751436 ,"13"],
							[126.637062,45.753951,"14"],
							];
		var positions1=[[126.636613,45.738045,"0"],
							[126.63735,45.75214,"1"],
							[126.644968,45.747209,"2" ],
							[126.645111,45.752542,"3" ],
							];
		var positions=[[126.63735,45.75214,"1"],
							[126.644968,45.747209,"2" ],
							[126.645111,45.752542,"3" ],
							[126.65007,45.728539,"4"],
							[126.655819,45.73171,"5"],
							[126.649926,45.73317,"6"],

						];
		var positions2=[[126.636613,45.738045,"0"],
							[126.65007,45.728539,"4"],
							[126.655819,45.73171,"5"],
							[126.649926,45.73317,"6"],
							];
		var	q1 = new BMap.Point(126.660917,45.759976);
		var q2 = new BMap.Point(126.665166,45.760425);
		var q3 = new BMap.Point(126.651304,45.751143);
		var q4 = new BMap.Point(126.629932,45.737052);
		var flightline=[[126.637637,45.752744 ,"11"],
							[126.63735,45.751436 ,"12"],
							[126.635769,45.751436 ,"13"],
							[126.637062,45.753951,"14"],
							];
		var index=[1,1];
		var flightnumber=[0,1,2,3,4,5];//飞机编码
		var pointnumber=[1,1];
		var endLong = [126.637637,126.644249,126.636613,126.636613,126.636613];  // 几架飞机就设几个
		var endLat = [45.752744,45.748719,45.738045,45.738045,45.738045]; 
		var startLong = [126.637637,126.644249,126.636613,126.636613,126.636613]; 
		var startLat = [45.752744,45.748719,45.738045,45.738045,45.738045]; 
   		var carMk=[0,0,0,0,0,0,0]; 
   		var sx = [126.637637, 126.644249];
        var sy = [45.752744, 45.748719];
        var ex = [126.63735, 126.649998];
        var ey = [45.751436, 45.746605]; 
		    road = [[[126.637637, 45.752744], [126.63735, 45.751436], [126.635769, 45.751436], [126.637062, 45.753951], [126.637637, 45.752744]],
		    		 [[126.644249, 45.748719], [126.649998, 45.746605], [126.64353, 45.746304], [126.644249, 45.748719]],
            ];
        var o=[0,1];
            
    </script>
  </body>
</html>