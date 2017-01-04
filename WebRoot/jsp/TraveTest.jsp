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
<body >
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
    	
    	<input type="hidden" id="road1" value=0>;
    	
		<div id="ZoneShow" style="height:600px"></div>
    	<div id="panel"></div>
    	<div class="button-group">
    		<button id="run">开始</button> 
    		<br>
    		<button id="run1">开始1</button> 
    		<br>   
    		<button id="run2">开始1</button> 
    		<br>  		
    		<input type="button" class="btn btn-info" value="添加点" id=showonline onclick="addPoint()"/>  
   
    		 		
        </div>
		<script type="text/javascript">
	// 百度地图API功能
	
			
			var map = new BMap.Map("ZoneShow");    // 创建Map实例
			map.centerAndZoom(new BMap.Point(126.644968,45.747209), 15);  // 初始化地图,设置中心点坐标和地图级别
			map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
			map.setCurrentCity("哈尔滨");          // 设置地图显示的城市 此项是必须设置的
			map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
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
			
			var positions1=[[126.636613,45.738045,"0"],//roadpointList------
							[126.63735,45.75214,"1"],
							[126.644968,45.747209,"2" ],
							[126.645111,45.752542,"3" ],
							];
			for (i=0;i<positions1.length;i++){
					//console.log(positions[i]);
					x=positions1[i][0];
					y=positions1[i][1];
					var point = new BMap.Point(x,y); // 创建标注
					all1.push(point);
				}     
			var positions2=[[126.636613,45.738045,"0"],
							[126.65007,45.728539,"4"],
							[126.655819,45.73171,"5"],
							[126.649926,45.73317,"6"],
							];
			for (i=0;i<positions1.length;i++){
					//console.log(positions[i]);
					x=positions2[i][0];
					y=positions2[i][1];
					var point = new BMap.Point(x,y); // 创建标注
					all2.push(point);
				}  
			var positions=[[126.63735,45.75214,"1"],
							[126.644968,45.747209,"2" ],
							[126.645111,45.752542,"3" ],
							[126.65007,45.728539,"4"],
							[126.655819,45.73171,"5"],
							[126.649926,45.73317,"6"],

						];//倒是可以直接将点读取到positions中
			function test (p,q,all) {
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
		                speed: 450,
		                enableRotation:true,//是否设置marker随着道路的走向进行旋转
		                landmarkPois: [
		                   {lng:116.314782,lat:39.913508,html:'加油站',pauseTime:2},
		                   {lng:116.315391,lat:39.964429,html:'高速公路收费<div><img src="http://map.baidu.com/img/logo-map.gif"/></div>',pauseTime:3},
		                   {lng:116.381476,lat:39.974073,html:'肯德基早餐<div><img src="http://ishouji.baidu.com/resource/images/map/show_pic04.gif"/></div>',pauseTime:2}
	                	]});          
		            }
		            console.log(lushu);
		            lushu.start();	
		            //setTimeout("alert(222)",2000);	            
		            //setTimeout(function(){ lushu.pause();},2000);
		            //setTimeout(function(){ lushu.start();},4000);
		        }
		    	});
	      		var p1 = new BMap.Point(126.660917,45.759976);
				var p2 = new BMap.Point(126.665166,45.760425);
				var p3 = new BMap.Point(126.651304,45.751143);
				var p4 = new BMap.Point(126.629932,45.737052);
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
			var	q1 = new BMap.Point(126.660917,45.759976);
			var q2 = new BMap.Point(126.665166,45.760425);
			var q3 = new BMap.Point(126.651304,45.751143);
			var q4 = new BMap.Point(126.629932,45.737052);
			$("run").onclick = function(){
				test(all1[0],all1[0],all1);
				test(all2[0],all2[0],all2);
				
			}
			$("run1").onclick = function(){
				test(q2,q3);
			}
			$("run2").onclick = function(){
				test(q3,q4);
			}
			function $(element){
				return document.getElementById(element);
			}
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
					var myIcon = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(300,157));
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
		
	</div> 
	 
</body>
</html>
