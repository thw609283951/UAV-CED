<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="text/html; charset=utf-8">

<title>UAV-Online</title>
<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
<script type="text/javascript" src="../scripts/jquery.js"></script>
<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
 <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>
<script type="text/javascript">
    var mapObj;
    var markers=[];
    var ipaddressvalue=[];
    var portvalue=[];
	function ShowUAVOnline() 
	{
		 mapObj = new AMap.Map('UAV-Online-HistoricalPath', 
		{
			resizeEnable : true,
			zoom : 15,
			center : [ 126.633344, 45.745019 ]
		}
		);
		 mapObj.setFitView();
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
    					map: mapObj,	
    					extData:i
    				});
    				markers[i].setTitle("["+tmplongitude[0].firstChild.nodeValue+","+tmplatitude[0].firstChild.nodeValue+"]");
    				markers[i].on('click',function (e)
    				{     				  	
    				   ShowHistoricalPath(e);
    				}
    				);
				  }
			}
		};
		
				xmlhttp.open("POST", "../jsp/ShowUAVOnlineServlet.action", true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");			
				xmlhttp.send(); 
	 }

		function ShowHistoricalPath(e)
		{
			var lineArr = [];
			var num=e.target.getExtData();
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
					var elocityvalue=500;
					var HistoricalPath = xmlhttp.responseXML.documentElement.getElementsByTagName("HistoricalPath");		
					for (var i = 0; i < HistoricalPath.length; i++)
					 {	
					 	if(i!=HistoricalPath.length-1)
					 	{		
							var tmplongitude= HistoricalPath[i].getElementsByTagName("Longitude");
							var tmplatitude= HistoricalPath[i].getElementsByTagName("Latitude");
    			    		var longitudevalue=tmplongitude[0].firstChild.nodeValue;
    			   	 		var latitudevalue=tmplatitude[0].firstChild.nodeValue;
    						lineArr.push([longitudevalue,latitudevalue]);
    					}
    					else
    					{
    						var tmplongitude= HistoricalPath[i].getElementsByTagName("Longitude");
							var tmplatitude= HistoricalPath[i].getElementsByTagName("Latitude");
							var tmpvelocity= HistoricalPath[i].getElementsByTagName("Velocity");
    			    		var longitudevalue=tmplongitude[0].firstChild.nodeValue;
    			   	 		var latitudevalue=tmplatitude[0].firstChild.nodeValue;
    			   	 		elocityvalue=tmpvelocity[0].firstChild.nodeValue;
    						lineArr.push([longitudevalue,latitudevalue]);
    					}
				  	}
				  	var polyline = new AMap.Polyline({
            			map: mapObj,
            			path: lineArr,
            			strokeColor: "#00A",  //线颜色
            			strokeOpacity: 1,     //线透明度
            			strokeWeight: 3,      //线宽
           				 strokeStyle: "solid"  //线样式
       				 });
				  	e.target.moveAlong(lineArr, elocityvalue);
				}
			};
		
				xmlhttp.open("POST", "../jsp/ShowUAVOnlineHistoricalPathServlet.action", true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");			
				xmlhttp.send("ipaddress="+ipaddressvalue[num]+"&port="+portvalue[num]);
		}
</script>
</head>
<body onload="ShowUAVOnline()">
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
		<div id="UAV-Online-HistoricalPath" style="height: 600px"></div>
		<div id="footer">
		<small>&#169; Copyright 2016 Graduation Project | Powered by <a href="#">heyongcheng
							 </a> | <a href="#">Top</a> </small>
		</div>
	</div>
</body>
</html>