<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>ZoneShow</title>		
		<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
		<script type="text/javascript" src="../scripts/jquery.js"></script>
		<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
		 <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>	
		<script type="text/javascript">
		var  markers=[];
		var mapObj;		
		function ShowZone(){
		 mapObj = new AMap.Map('ZoneShow', 
		{
			resizeEnable : true,
			zoom : 15,
			center : [ 126.633344, 45.745019 ]
		}
		);
		 mapObj.setFitView();
		var clickEventListener = mapObj.on('click', function(e) {
        document.getElementById("lnglat").value = e.lnglat.getLng() + ',' + e.lnglat.getLat();
        document.getElementById("newlongitudecomment").value=e.lnglat.getLng() ;
        document.getElementById("newlatitudecomment").value=e.lnglat.getLat();
      });
		var xmlhttp;
		if (window.XMLHttpRequest) 
		{
			xmlhttp = new XMLHttpRequest();
		} 
		else 
		{
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() 
		{
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) 
			{
				var polygonArr = new Array();
				var Zone = xmlhttp.responseXML.documentElement.getElementsByTagName("ZonePoint");
				for (var i = 0; i <Zone.length; i++)
				 {			
					var tmplongitude=Zone[i].getElementsByTagName("Longitude");
					var tmplatitude=Zone[i].getElementsByTagName("Latitude");					
					var longitude=tmplongitude[0].firstChild.nodeValue;
					var latitude=tmplatitude[0].firstChild.nodeValue;
                  	polygonArr.push([longitude,latitude]);
                  	markers[i] = new AMap.Marker(
                    {
				    	icon: "../images/icons/mark_b.png",
    					position:[longitude,latitude],
    					map: mapObj,
    					title:"这是第"+(i+1)+"点"+",位置是["+longitude+","+latitude+"]",	
    					extData:i
    				});
				  }
				  	
				  var  polygon = new AMap.Polygon({
        			path: polygonArr,//设置多边形边界路径
       				strokeColor: "#FF33FF", //线颜色
        			strokeOpacity: 0.2, //线透明度
        			strokeWeight: 3,    //线宽
        			fillColor: "#1791fc", //填充色
        			fillOpacity: 0.35//填充透明度
    				});
    				polygon.setMap(mapObj);
    				mapObj.setFitView();
			}
		};		
				xmlhttp.open("POST", "../jsp/ShowZoneServlet.action", true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");			
				xmlhttp.send();    
		}
	 function AddPoint()
     {    
      	var xmlhttp;
		if (window.XMLHttpRequest) 
		{
			xmlhttp = new XMLHttpRequest();
		} 
		else 
		{
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() 
		{
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) 
			{
				var txt="";
				txt=xmlhttp.responseText;
				if(txt=="SUCCESSED")
				{
					ShowZone();
				}
				else if(txt=="FAILED")
				{
					alert("出错了");
				}
			}
		};
		
			xmlhttp.open("POST", "../jsp/AddPointServlet.action", true);
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			var newlongitude=document.getElementById("newlongitudecomment").value;
            var newlatitude=document.getElementById("newlatitudecomment").value;		
			xmlhttp.send("newlongitude="+newlongitude+"&newlatitude="+newlatitude);    
     }
		</script>
	</head>
	<body onload="ShowZone()">
		<div id="body-wrapper" class="myfont">
			<div id="sidebar">
				<div id="sidebar-wrapper">
					<a href="#"><img id="logo" src="../images/logo.png" alt="The logo" /></a>
					<div id="profile-links">
						你好, <a href="javascript:void(0);" title="Edit your profile">${Identity.name}</a>!
						<br />
						<br />
						 | <a href="../jsp/LogoutServlet.action" title="Sign Out">登出</a>
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
									<a class="nav-top-item">在线无人机</a>
									<ul>
										<li>
											<a href="UAV-Online.jsp">地图位置显示</a>
										</li>
										<li>
											<a href="Historicalpath.jsp">历史路径演示</a>
										</li>
										<li>
											<a href="Record.jsp">接入历史查询</a>
										</li>										 
									</ul>
								</li>
								<li>
									<a  class="nav-top-item">禁飞区管理</a>
									<ul>
										<li>
											<a href="ModifyBanArea.jsp">禁飞区添加与删除</a>
										</li>
										<li>
											<a href="BanAreaShow.jsp">禁飞区显示</a>
										</li>
									</ul>
								</li>
								<li>
									<a  class="nav-top-item">警告管理</a>
									<ul>
										<li>
											<a href="AddRestrictHeightAndremainingpower.jsp">警告值修改与添加</a>
										</li>
									</ul>
								</li>
								<li>
									<a  class="nav-top-item">覆盖区管理</a>
									<ul>
										<li>
											<a href="DeleteZonePoint.jsp">覆盖区顶点删除</a>
										</li>
										<li>
											<a href="ZoneShow.jsp">覆盖区显示与顶点添加</a>
										</li>
									</ul>
								</li>
								<li>
									<a  class="nav-top-item">多机控制</a>
									<ul>
										<li>
											<a href="GotoDestination.jsp">多机控制</a>
										</li>
										<li>
											<a href="ShortestPath.jsp">禁飞区最短路径</a>
										</li>
										<li>
											<a href="AreaCoverage.jsp">多机区域覆盖</a>
										</li>
									</ul>
								</li>

							</ul>
						</li>
					</ul>							
				</div>
			</div>
			<div id="ZoneShow" style="height: 600px"></div>
			<div id="getlanlat"><input type="text" readonly="true" id="lnglat"></div>
			<div class="button-group">
    		<input type="button" class="btn btn-info" value="添加点" id="showonline" onclick="AddPoint()"/>    		
        	</div>
			<input type="hidden" id="newlongitudecomment" />
			<input type="hidden" id="newlatitudecomment" />
			<div id="footer">
				<small>&#169; Copyright 2016 Graduation Project | Powered by <a href="#">heyongcheng
						</a> | <a href="#">Top</a> </small>
			</div>
		</div>
		<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
		<script type="text/javascript" src="../scripts/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../scripts/simpla.jquery.configuration.js"></script>
		<script type="text/javascript" src="../scripts/facebox.js"></script>
		<script type="text/javascript" src="../scripts/jquery.wysiwyg.js"></script>
	</body>
</html>
