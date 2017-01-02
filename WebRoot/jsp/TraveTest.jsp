<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="text/html; charset=utf-8">

<title>TraveTest</title>
<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
<script type="text/javascript" src="../scripts/jquery.js"></script>
<script src="http://cache.amap.com/lbs/static/es5.min.js"></script>

<link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值&plugin=AMap.Driving"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=MLSjqW8E6qbunua0gTEvjTWMXqrjwC6x"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
</head>
<body>
	<div id="body-wrapper" class="myfont">
		<div id="sidebar">
			<div id="sidebar-wrapper">
				<a href="#"><img id="logo" src="../images/logo.png" alt="The logo" /></a>
				<div id="profile-links">
					你好, <a href="javascript:void(0);" title="Edit your profile">${Identity.name}</a>！
					<br /> <br /> <a href="../jsp/LogoutServlet.action" title="Sign Out">登出</a>
				</div>
				<ul id="main-nav">
						<li>
							<a href="" class="nav-top-item current">基本功能</a>
							<ul>
								<li>
									<a class="nav-top-item">返回首页</a>
									<ul>
										<li>
											<a href="welcome.jsp">返回首页</a>
										</li>																			 
									</ul>
								</li>
								<li>
									<a class="nav-top-item">功能</a>
									<ul>
										<li>
											<a href="#">添加点</a>
										</li>
									</ul>
								</li>
							</ul>
						</li>
					</ul>		
			</div>
		</div>
		<div id="getlanlat"><input type="text" readonly="true" id="lnglat"></div>
    	<input type="hidden" id="newlongitudecomment" />
    	<input type="hidden" id="newlatitudecomment" />
    	<input type="hidden" id="index" value=0>
		<div id="TraveTest" style="height:600px"></div>
    	<div id="panel"></div>
    	<div class="button-group">
    		<button id="run">开始</button> 
    		<br>    		
    		<input type="button" class="btn btn-info" value="添加点" id=showonline onclick="addPoint()"/>  
   
    		 		
        </div>
		<script type="text/javascript">
	// 百度地图API功能
			var map = new BMap.Map("TraveTest");    // 创建Map实例
			map.centerAndZoom(new BMap.Point(126.629889,45.744779), 15);  // 初始化地图,设置中心点坐标和地图级别
			map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
			map.setCurrentCity("哈尔滨");          // 设置地图显示的城市 此项是必须设置的
			map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
			var positions=[[126.660917,45.759976],[126.665166,45.760425],[126.668771,45.760156]];//倒是可以直接将点读取到positions中
			var lushu;
			// 实例化一个驾车导航用来生成路线
    		var drv = new BMap.DrivingRoute('哈尔滨', {
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
	                speed: 450,
	                enableRotation:true,//是否设置marker随着道路的走向进行旋转
	                landmarkPois: [
	                   {lng:116.314782,lat:39.913508,html:'加油站',pauseTime:2},
	                   {lng:116.315391,lat:39.964429,html:'高速公路收费<div><img src="http://map126.629889,45.744779com/img/logo-map.gif"/></div>',pauseTime:3},
	                   {lng:116.381476,lat:39.974073,html:'肯德基早餐<div><img src="http://ishouji.baidu.com/resource/images/map/show_pic04.gif"/></div>',pauseTime:2}
	                ]});          
	            }
	        }
	    	});
      		var p1 = new BMap.Point(126.660917,45.759976);
			var p2 = new BMap.Point(126.665166,45.760425);
			var p3 = new BMap.Point(126.651304,45.751143);
			drv.search(p2,p3);
			$("run").onclick = function(p,q){
				lushu.start();
			}
			function $(element){
				return document.getElementById(element);
			}
			function addPoint() {
				for (i=0;i<positions.length;i++){
					//console.log(positions[i]);
					x=positions[i][0];
					y=positions[i][1];
					var point = new BMap.Point(x,y); // 创建标注
					var marker = new BMap.Marker(point);// 将标注添加到地图中
					map.addOverlay(marker);
				}      
				//marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
			}
			
	        
	       
			
		</script>
		
	</div> 
	 
</body>
</html>
