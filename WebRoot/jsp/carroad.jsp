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
    	function allcar(p1,p2,q1,q2,past1,past2){
    		lushu1();
    		lushu2();
    	}
    	function $(element){
			return document.getElementById(element);
		}
    	function car1 (p,q,all) {
				
				// 实例化一个驾车导航用来生成路线
				var drv1 = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
	    		var drv = new BMap.DrivingRoute('哈尔滨',{
		        onSearchComplete: function(res) {
		            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
		                var plan = res.getPlan(0);
		                var arrPois =[];
		                for(var j=0;j<plan.getNumRoutes();j++){
		                    var route = plan.getRoute(j);
		                    arrPois= arrPois.concat(route.getPath());
		                }
		                map.addOverlay(new BMap.Polyline(arrPois, {strokeColor: '#111'}));
		                map.setViewport(arrPois);
		                lushu = new BMapLib.LuShu(map,arrPois,{
		                defaultContent:"",//"从天安门到百度大厦"
		                autoView:true,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
		                icon  : new BMap.Icon('http://developer.baidu.com/map/jsdemo/img/car.png', new BMap.Size(52,26),{anchor : new BMap.Size(27, 13)}),
		                speed: 500,
		                enableRotation:true,//是否设置marker随着道路的走向进行旋转
		                landmarkPois: [
		                   {lng:116.314782,lat:39.913508,html:'加油站',pauseTime:2},
		                   {lng:116.315391,lat:39.964429,html:'高速公路收费<div><img src="http://map.baidu.com/img/logo-map.gif"/></div>',pauseTime:3},
		                   {lng:116.381476,lat:39.974073,html:'肯德基早餐<div><img src="http://ishouji.baidu.com/resource/images/map/show_pic04.gif"/></div>',pauseTime:2}
	                	]});          
		            }
		            console.log(lushu);
		            //lushu.start();
		            //setTimeout("alert(222)",2000);	            
		            //setTimeout(function(){ lushu.pause();},2000);
		            //setTimeout(function(){ lushu.start();},4000);
		        }
		    	});
		    	ac=document.getElementById("run1")
				ac.onclick = function(){
					var buttoname1=document.getElementById("run1").value;
					if (buttoname1=="第一辆车开始"){
						document.getElementById("run1").value="第一辆车暂停";
						lushu.start();
					}
					else if (buttoname1=="第一辆车暂停"){
						document.getElementById("run1").value="第一辆车开始";
						lushu.pause();
					}
				}
		    	//lushu.start();
				drv1.search(p,q,{waypoints:all});
				//console.log(lushu);
				drv.search(p,q,{waypoints:all})
				//console.log(lushu);
				//setTimeout("lushu.pause()",2000);
				// setTimeout("lushu.start()", 2000 )
				/* console.log("out");
				console.log(lushu);
				$("run").onclick = function(){
				console.log("clickin");
				console.log(lushu);
					lushu.start();
				} */
			}
			
			
		function car2 (p,q,all) {
				var lushu;
				// 实例化一个驾车导航用来生成路线
				var drv1 = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
	    		var drv = new BMap.DrivingRoute('哈尔滨',{
		        onSearchComplete: function(res) {
		            if (drv.getStatus() == BMAP_STATUS_SUCCESS) {
		                var plan = res.getPlan(0);
		                var arrPois =[];
		                for(var j=0;j<plan.getNumRoutes();j++){
		                    var route = plan.getRoute(j);
		                    arrPois= arrPois.concat(route.getPath());
		                }
		                map.addOverlay(new BMap.Polyline(arrPois, {strokeColor: '#111'}));
		                map.setViewport(arrPois);
		                lushu = new BMapLib.LuShu(map,arrPois,{
		                defaultContent:"",//"从天安门到百度大厦"
		                autoView:false,//是否开启自动视野调整，如果开启那么路书在运动过程中会根据视野自动调整
		                icon  : new BMap.Icon('http://developer.baidu.com/map/jsdemo/img/car.png', new BMap.Size(52,26),{anchor : new BMap.Size(27, 13)}),
		                speed: 500,
		                enableRotation:true,//是否设置marker随着道路的走向进行旋转
		                landmarkPois: [
		                   {lng:116.314782,lat:39.913508,html:'加油站',pauseTime:2},
		                   {lng:116.315391,lat:39.964429,html:'高速公路收费<div><img src="http://map.baidu.com/img/logo-map.gif"/></div>',pauseTime:3},
		                   {lng:116.381476,lat:39.974073,html:'肯德基早餐<div><img src="http://ishouji.baidu.com/resource/images/map/show_pic04.gif"/></div>',pauseTime:2}
	                	]});          
		            }
		            console.log(lushu);
		           // lushu.start();
		            //setTimeout("alert(222)",2000);	            
		            //setTimeout(function(){ lushu.pause();},2000);
		            //setTimeout(function(){ lushu.start();},4000);
		        }
		    	});
				ac2=document.getElementById("run2");
				ac2.onclick = function(){
					var buttoname2=document.getElementById("run2").value;
					if (buttoname2=="第二辆车开始"){
						document.getElementById("run2").value="第二辆车暂停";
						lushu.start();
					}
					else if (buttoname2=="第二辆车暂停"){
						document.getElementById("run2").value="第二辆车开始";
						lushu.pause();
					}
				}
		    	//lushu.start();
				drv1.search(p,q,{waypoints:all});
				//console.log(lushu);
				drv.search(p,q,{waypoints:all});
				//console.log(lushu);
				//setTimeout("lushu.pause()",2000);
				// setTimeout("lushu.start()", 2000 )
				/* console.log("out");
				console.log(lushu);
				$("run").onclick = function(){
				console.log("clickin");
				console.log(lushu);
					lushu.start();
				} */
			}
    </script>
  </body>
</html>
