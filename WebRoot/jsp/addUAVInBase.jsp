<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>addUAVInBase</title>		
		<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
		<script type="text/javascript" src="../scripts/jquery.js"></script>
		<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
		 <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>	
		<script type="text/javascript">
		var  markers=[];
		var mapObj;
		var color=["#FF44FF","#FF0000","#006000","#00EC00","#B15BFF","#9AFF02","#D2A2CC","#81C0C0"];						 
	 function Action() 
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
				var UAVInBasePath = xmlhttp.responseXML.documentElement.getElementsByTagName("UAVInBasePath");
				for (var i = 0; i <  UAVInBasePath.length; i++)
				  {	
				  		var lineArr = [];	
						var LonAndLat=UAVInBasePath[i].getElementsByTagName("LonAndLat");
						var velocity=UAVInBasePath[i].getElementsByTagName("Velocity");
						var temvelocity=velocity[0].firstChild.nodeValue;
						for (var x = 0; x <LonAndLat.length; x++)
				 		{		
				 			
							var tmplongitude=LonAndLat[x].getElementsByTagName("Longitude");
							var tmplatitude=LonAndLat[x].getElementsByTagName("Latitude");		
							var longitude=tmplongitude[0].firstChild.nodeValue;
							var latitude=tmplatitude[0].firstChild.nodeValue;					
						    lineArr.push([longitude,latitude]);
				    	}
				    	/*  var markers= new AMap.Marker(
                   		 {
				    		icon: "../images/uav.png",
    						position:lineArr[0],
    						map:mapObj,	
    					}); */
    					
    					var polyline = new AMap.Polyline({
            			map: mapObj,
            			path: lineArr,
            			strokeColor: color[i],  //线颜色
            			strokeOpacity: 1,     //线透明度
            			strokeWeight: 3,      //线宽
           				 strokeStyle: "solid"  //线样式
       				 });
       				markerss[i].moveAlong(lineArr, temvelocity);
				    	
				  }
			} 
		};
		        var limittime =document.getElementById("limiytimeinput").value;
				xmlhttp.open("POST", "../jsp/MaxTaskServlet.action", true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");			
				xmlhttp.send("limittime="+limittime);
	 }
	 
	  function AddUAV()
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
					ShowTask();
				}
				else if(txt=="FAILED")
				{
					alert("出错了");
				}
			}
		};
		
			xmlhttp.open("POST", "../jsp/AddUAVInBaseServlet.action", true);
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			var newlongitude=document.getElementById("newlongitudecomment").value;
            var newlatitude=document.getElementById("newlatitudecomment").value;
            var sumpower=document.getElementById("sumpowerinput").value;	
			var remianingpower=document.getElementById("remianingpowerinput").value;	
			var consumpower=document.getElementById("consumpowerinput").value;	
			var height=document.getElementById("heightinput").value;	
			var elevation=document.getElementById("elevationinput").value;	
			var velocity=document.getElementById("velocityinput").value;	
			var version=document.getElementById("versioninput").value;	
			var port=document.getElementById("portinput").value;	
			var Ipaddress=document.getElementById("ipaddressinput").value;					
			xmlhttp.send("newlongitude="+newlongitude+"&newlatitude="+newlatitude+
			"&sumpower="+sumpower+
			"&remianingpower="+remianingpower+
			"&consumpower="+consumpower+
			"&height="+height+
			"&elevation="+elevation+
			"&velocity="+velocity+
			"&version="+version+
			"&port="+port+
			"&Ipaddress="+Ipaddress
			);   
        ShowUAVInBase(); 
     }
     	function deletefunction()
		{
			var xmlhttp;
			if(window.XMLHttpRequest){
				xmlhttp=new XMLHttpRequest();
			}else{
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){
					var txt=xmlhttp.responseText;
					if(txt!="FAILED"){
						 ShowTask();
					}else{
						alert("删除失败");
					}
				}				
			};
			
			xmlhttp.open("POST", "../jsp/DeleteUAVInBaseServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var number=document.getElementById("num").value.trim();
			
			//var longitude=markers[number].getPosition().getLng();
			//var latitude=markers[number].getPosition().getLat();
			xmlhttp.send("num="+number);
			 ShowUAVInBase(); 
		}
		
		function ShowUAVInBase() 
	    {
		 	var xmlhttp;
			mapObj = new AMap.Map('UAVShow', 
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
				var UAV = xmlhttp.responseXML.documentElement.getElementsByTagName("UAVInBase");
				for (var i = 0; i <  UAV.length; i++)
				 {			
					var tmplongitude= UAV[i].getElementsByTagName("Longitude");
					var tmplatitude= UAV[i].getElementsByTagName("Latitude");
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
    				    document.getElementById("num").value=e.target.getExtData();
    				}
    				);
				  }
				  var localhost = xmlhttp.responseXML.documentElement.getElementsByTagName("localhost");
				  var localhostip= localhost[0].firstChild.nodeValue;
				  document.getElementById("ipaddressinput").value=localhostip;
			}
		};
		
				xmlhttp.open("POST", "../jsp/ShowUAVInBaseServlet.action", true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");			
				xmlhttp.send();
	 }
	</script>
	</head>
	<body onload="ShowUAVInBase()">
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
			<div id="UAVShow" style="height: 600px"></div>
			<div class="button-group">
			<div id="getlanlat"><input type="text" placeholder="经纬度" readonly="true" id="lnglat"></div>
			<!-- <div id="LimitTime"><input type="text"  id="limiytimeinput" style="width: 60px"></div> -->
			
			<div id="sumpower"><input placeholder="总电量" type="text"  id="sumpowerinput" style="width: 60px"></div>	
			<div id="remianingpower"><input placeholder="剩余电量" type="text"  id="remianingpowerinput" style="width: 60px"></div>
			<div id="consumpower"><input placeholder="耗电量" type="text"  id="consumpowerinput" style="width: 60px"></div>
			<div id="height"><input placeholder="高度" type="text"  id="heightinput" style="width: 60px"></div>
			<div id="elevation"><input placeholder="仰角" type="text"  id="elevationinput" style="width: 60px"></div>
			<div id="velocity"><input placeholder="速度" type="text"  id="velocityinput" style="width: 60px"></div>
			<div id="version"><input placeholder="型号" type="text"  id="versioninput" style="width: 60px"></div>
			<div id="port"><input placeholder="(端口)标识" type="text"  id="portinput" style="width: 60px"></div>
			<div id="Ipaddress"><input placeholder="本机IP地址" type="text"  id="ipaddressinput" readonly="true"  style="width: 100px"></div>
																																				
    		<input type="button" class="btn btn-info" value="添加无人机" id="adduavinbaseonmap" onclick="AddUAV()"/>
    		<input type="button" class="btn btn-info" value="删除无人机" id="deleteuavinbaseonmap" onclick="deletefunction()"/>       		
        	</div>
        	<input type="hidden" id="newlongitudecomment" />
			<input type="hidden" id="newlatitudecomment" />
			<input type="hidden" id="num" />
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
