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
    	function addPoint() {
				var opts = {
				width : 250,     // 信息窗口宽度
				height: 80,     // 信息窗口高度
				title : "信息窗口" , // 信息窗口标题
				enableMessage:true//设置允许信息窗发送短息
			   };
				for (i=0;i<needpoints.length;i++){
					//console.log(positions[i]);
					x=needpoints[i][0];
					y=needpoints[i][1];
					var content = needpoints[i][2];
					var point = new BMap.Point(x,y); // 创建标注
					var marker = new BMap.Marker(point);// 将标注添加到地图中
					map.addOverlay(marker);
					addClickHandler(content,marker);
				}     
				for (i=0;i<positions.length;i++){
					//console.log(positions[i]);
					x=positions[i][0];
					y=positions[i][1];
					var content = positions[i][2];
					var point = new BMap.Point(x,y); // 创建标注
					//var myIcon = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(300,157));
					//var myIcon = new BMap.Icon("../images/icons/flight.png", new BMap.Size(48,32));
					var myIcon = new BMap.Icon("../images/icons/mark_b.png", new BMap.Size(48,32));
					var marker =  new BMap.Marker(point,{icon:myIcon});// 将标注添加到地图中
					map.addOverlay(marker);
					addClickHandler(content,marker);
				}     
				//marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				function addClickHandler(content,marker){
					marker.addEventListener("click",function(e){
						openInfo(content,e)}
					);
				}
				function openInfo(content,e){
					var p = e.target;
					var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
					var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
					map.openInfoWindow(infoWindow,point); //开启信息窗口
				}
			}
    </script>
  </body>
</html>
