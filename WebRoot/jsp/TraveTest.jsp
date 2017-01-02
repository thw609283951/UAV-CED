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
    	<script type="text/javascript">
    		var marker, map = new AMap.Map("TraveTest", {
	            resizeEnable: true,
	            center: [126.629889,45.744779],
	            zoom: 13
	        });
	        var markers=[];
	        var positions=[];//倒是可以直接将点读取到positions中
	        var clickEventListener = map.on('click', function(e) {
	            markers=[];
	            document.getElementById("lnglat").value = e.lnglat.getLng() + ',' + e.lnglat.getLat();
	            document.getElementById("newlongitudecomment").value=e.lnglat.getLng() ;
	            document.getElementById("newlatitudecomment").value=e.lnglat.getLat();
	            var x=document.getElementById("newlongitudecomment").value;
	            var y=document.getElementById("newlatitudecomment").value;
	            //console.log(x,y);
	            positions.push([x,y]);
	            //console.log(positions);
	            for (var i = 0, marker; i < positions.length; i++) {
	                    marker = new AMap.Marker({
	                    map: map,
	                    position: positions[i]
	                });
	                markers.push(marker);
	                //console.log(markers);
	            }   
	        });
    		function showroad() {
    			var index=document.getElementById("index").value;
    			var alldriving=[];
    			var driving = new AMap.Driving({
        			map: map,
    			}); 
    			// 根据起终点经纬度规划驾车导航路线
    			console.log(index);
              	index++;
              	index--;
              	console.log(index);
              	
              	driving.search([positions[index][0],positions[index][1]], [positions[index+1][0],positions[index+1][1]], function(status, result) {});
    			document.getElementById("index").value=index+1;
     			//TODO 解析返回结果，自己生成操作界面和地图展示界面
    		}
    	</script>
		<div class="button-group">
    		<input type="button" class="btn btn-info" value="展示路径" id="showonline" onclick="showroad()"/>    		
        </div>
	</div> 
	 
</body>
</html>
