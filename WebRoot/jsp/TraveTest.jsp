<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=MLSjqW8E6qbunua0gTEvjTWMXqrjwC6x"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/LuShu/1.2/src/LuShu_min.js"></script>
</head>
<body >
	<div id="body-wrapper" class="myfont">
		<div id="sidebar">
			<div id="sidebar-wrapper">
				<a href="#"><img id="logo" src="../images/logo.png" alt="The logo" /></a>
				<div id="profile-links">
					你好,<a href="javascript:void(0);" title="Edit your profile">${Identity.name}</a>！
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
    	
		<div id="TraveTest" style="height:1000px"></div>
    	<div id="panel"></div>
    	
    	<div class="button-group">
    		<table>
    			<c:forEach var="j" begin="1" end="${fn:length(carsPath)}">
				   <tr>
				   <td><button class="btn btn-info" onclick=lushu${j}()>子区域${j }</button> </td>
				   <td><input type="button" class="btn btn-info" id=run${j} value="开始" onclick=""></td>
				   </tr>
				</c:forEach>
    		</table>
    		<input type="button" class="btn btn-info" value="添加点" id=showonline onclick="addPoint()"/>  
        </div>
       <%@ include file="var.jsp" %>
       <%@ include file="oneflight.jsp" %>
       <%@ include file="carroad.jsp" %>
       <%@ include file="showpoint.jsp" %>
       <%@ include file="showcarroad.jsp" %>
		<script type="text/javascript">
	// 百度地图API功能
			var myIcon = new BMap.Icon("../images/icons/flight.png", new BMap.Size(48,32), {imageOffset: new BMap.Size(0, 0)});
			var map = new BMap.Map("TraveTest");    // 创建Map实例
			map.centerAndZoom(new BMap.Point(126.63735,45.75214), 12);  // 初始化地图,设置中心点坐标和地图级别
			map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
			map.setCurrentCity("哈尔滨");          // 设置地图显示的城市 此项是必须设置的
			map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
			
			<c:forEach var="j" begin="1" end="${fn:length(carsPath)}">
			   for (i=0;i<UAVCars[${j}-1].length;i++){
					//console.log(positions[i]);
					x=UAVCars[${j}-1][i][0];
					y=UAVCars[${j}-1][i][1];
					var point = new BMap.Point(x,y); // 创建标注
					all${j}.push(point);
				}   
			</c:forEach>
			//倒是可以直接将点读取到positions中
			<c:forEach var="j" begin="1" end="${fn:length(carsPath)}">
			   function lushu${j}(){
					car${j}(all${j}[0],all${j}[0],all${j});
				
				}
			</c:forEach>
			
			/* function lushu1(){
				car1(all1[0],all1[0],all1);
				
			}
			function lushu2(){
				car2(all2[0],all2[0],all2);
			} */
			function test(j){
				o=j;
				setInterval("goWay(ex[o],ey[o],sx[o],sy[o],o)",1000);
				setInterval("changepoint(o,o)",2000);
			}
   			
			
		</script>
		
	</div> 
	 
</body>
</html>
