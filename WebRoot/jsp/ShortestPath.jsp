<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>ShortestPath</title>		
		<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
		<script type="text/javascript" src="../scripts/jquery.js"></script>
		<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
		 <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>	
		<script type="text/javascript">		
	var map;
    var markers=[];
    var circle=[];
    var ipaddressvalue=[];
    var portvalue=[];
     
	function ShowUAVOnline() 
	{
		map = new AMap.Map('ShortestPath', 
		{
			resizeEnable : true,
			zoom : 15,
			center : [ 126.633344, 45.745019 ]
		}
		);
		 map.setFitView();
		 var clickEventListener = map.on('click', function(e) {
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
				var UAV = xmlhttp.responseXML.documentElement.getElementsByTagName("UAVOnline");
				for (var i = 0; i <  UAV.length; i++)
				 {			
					var tmplongitude= UAV[i].getElementsByTagName("Longitude");
					var tmplatitude= UAV[i].getElementsByTagName("Latitude");
					var tmpipaddress= UAV[i].getElementsByTagName("Ipaddress");
    			    var tmpport= UAV[i].getElementsByTagName("Port");
    			    ipaddressvalue[i]=tmpipaddress[0].firstChild.nodeValue;
    			    portvalue[i]=tmpport[0].firstChild.nodeValue;
                     markers[i] = new AMap.Marker(
                    {
				    	icon: "../images/uav.png",
    					position:[tmplongitude[0].firstChild.nodeValue,tmplatitude[0].firstChild.nodeValue],
    					map: map,	
    					extData:i
    				});
    				markers[i].setTitle("["+tmplongitude[0].firstChild.nodeValue+","+tmplatitude[0].firstChild.nodeValue+"]");
    				markers[i].on('click',function (e)
    				{   	   				  	
    				    whichone(e);
    				}
    				);
				  }
			}
		};
		
				xmlhttp.open("POST", "../jsp/ShowUAVOnlineServlet.action", true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");			
				xmlhttp.send();
				ShowBanArea();    
	 }
		function ShowBanArea(){
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
				var BanArea = xmlhttp.responseXML.documentElement.getElementsByTagName("BanArea");
				for (var i = 0; i <BanArea.length; i++)
				 {			
					var tmplongitude=BanArea[i].getElementsByTagName("Longitude");
					var tmplatitude=BanArea[i].getElementsByTagName("Latitude");
					var tmpradius=BanArea[i].getElementsByTagName("Radius");
					
					var longitude=tmplongitude[0].firstChild.nodeValue;
					var latitude=tmplatitude[0].firstChild.nodeValue;
					var radius=tmpradius[0].firstChild.nodeValue;
                  	circle[i] = new AMap.Circle
                  	({
       	 				center: new AMap.LngLat(longitude,latitude),// 圆心位置
        				radius:radius, //半径
        				strokeColor: "#F33", //线颜色
        				strokeOpacity: 1, //线透明度
        				strokeWeight: 3, //线粗细度
        				fillColor: "#ee2200", //填充颜色
        				fillOpacity: 0.35//填充透明度
   				 	});
    				circle[i].setMap(map);
    				map.setFitView();
				  }
			}
		};		
				xmlhttp.open("POST", "../jsp/ShowBanAreaServlet.action", true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");			
				xmlhttp.send();    
		}
		function whichone(e)
    	{
    	     var num=e.target.getExtData();
    	     document.getElementById("ipaddresscomment").value = ipaddressvalue[num];
    	     document.getElementById("portcomment").value = portvalue[num];
    	     document.getElementById("marknum").value =num;
       }
       
       
       function Executive()
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
				var lineArr = [];
				var txt="";
				var Result=xmlhttp.responseXML.documentElement.getElementsByTagName("Result");
				var tmpresult= Result[0].firstChild;
				txt=tmpresult.nodeValue;
				var Velocity=xmlhttp.responseXML.documentElement.getElementsByTagName("Velocity");
				var tmpvelocity= Velocity[0].firstChild;
				var elocityvalue=tmpvelocity.nodeValue;
				if(txt=="SUCCESSED")
				{
					var marknum= document.getElementById("marknum").value;
					var oldlongitude=markers[marknum].getPosition().getLng();
					var oldlatitude=markers[marknum].getPosition().getLat();
					lineArr.push([oldlongitude,oldlatitude]);
					
					var LonAndLat = xmlhttp.responseXML.documentElement.getElementsByTagName("LonAndLat");
					
					for (var x = 0; x <LonAndLat.length; x++)
				 	{			
						var tmplongitude=LonAndLat[x].getElementsByTagName("Longitude");
						var tmplatitude=LonAndLat[x].getElementsByTagName("Latitude");
					
						var longitude=tmplongitude[0].firstChild.nodeValue;
						var latitude=tmplatitude[0].firstChild.nodeValue;					
						lineArr.push([longitude,latitude]);
        
				   }
					
					
					
					//var newlongitude=document.getElementById("newlongitudecomment").value;
            		//var newlatitude=document.getElementById("newlatitudecomment").value;
					//lineArr.push([newlongitude,newlatitude]);
					var polyline = new AMap.Polyline({
            			map: map,
            			path: lineArr,
            			strokeColor: "#00A",  //线颜色
            			strokeOpacity: 1,     //线透明度
            			strokeWeight: 3,      //线宽
           				 strokeStyle: "solid"  //线样式
       				 });
       				markers[marknum].moveAlong(lineArr, elocityvalue);
				}
				else if(txt=="FAILED")
				{
					alert("出错了");
				}
			}
		};
		
			xmlhttp.open("POST", "../jsp/ShortestPathServlet.action", true);
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
						
			var ipaddress=document.getElementById("ipaddresscomment").value;
			var port=document.getElementById("portcomment").value;
			var newlongitude=document.getElementById("newlongitudecomment").value;
            var newlatitude=document.getElementById("newlatitudecomment").value;
            var mapcenterlongitude=map.getCenter().getLng();
            var mapcenterlatitude=map.getCenter().getLat();
            var lnglat = new AMap.LngLat(mapcenterlongitude,mapcenterlatitude);
            var perlongdistance=lnglat.distance([mapcenterlongitude+0.00001,mapcenterlatitude]);
            var perlatitudedistance=lnglat.distance([mapcenterlongitude,mapcenterlatitude+0.00001]);		
			xmlhttp.send("ipaddress="+ipaddress+"&port="+port+"&newlongitude="+newlongitude+"&newlatitude="+newlatitude+"&perlongdistance="+perlongdistance+"&perlatitudedistance="+perlatitudedistance);    
     }
		</script>
	</head>
	<body onload="ShowUAVOnline()">
		<div id="body-wrapper" class="myfont">
			<div id="sidebar">
				<div id="sidebar-wrapper">
					<a href="#"><img id="logo" src="../images/logo.png" alt="The logo" /><div id="profile-links" style="height: 53px; ">
						你好, <a href="javascript:void(0);" title="Edit your profile">${Identity.name}</a>!
						<br>
						<br>
						 | <a href="../jsp/LogoutServlet.action" title="Sign Out">登出</a>
					</div></a>					
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
											<a href="ShortestPath.jsp">多机区域覆盖</a>
										</li>
									</ul>
								</li>
							</ul>
						</li>
					</ul>							
				</div>
			</div>
			<div id="ShortestPath" style="height: 600px">
			</div>
			<div id="getlanlat"><input type="text" readonly="true" id="lnglat"></div>
			<div class="button-group">
    		<input type="button" class="btn btn-info" value="执行飞行命令" id="showonline" onclick="Executive()"/>    		
       	 	</div>
			<input type="hidden" id="marknum" />
        	<input type="hidden" id="ipaddresscomment" />
			<input type="hidden" id="portcomment" />
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
