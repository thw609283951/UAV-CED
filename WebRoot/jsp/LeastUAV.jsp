<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>LeastUAV</title>		
		<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
		<script type="text/javascript" src="../scripts/jquery.js"></script>
		<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
		 <script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>	
		<script type="text/javascript">
		var  markers=[];
		var  markerss=[];
		var mapObj;
		var color=["#FF44FF","#FF0000","#006000","#00EC00","#B15BFF","#9AFF02","#D2A2CC","#81C0C0"];
		function ShowTask(){
		 mapObj = new AMap.Map('TaskShow', 
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
				var Zone = xmlhttp.responseXML.documentElement.getElementsByTagName("TaskPoint");
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
    					extData:i
    				});
    				markers[i].on('click',function (e)
    				{   	   				  	
    				    document.getElementById("num").value=e.target.getExtData();
    				}
    				);
				  }
			}
		};		
				xmlhttp.open("POST", "../jsp/ShowTaskServlet.action", true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");			
				xmlhttp.send();
				ShowUAVInBase(); 
		}
		
			 
	 function LeastUAV() 
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
		
				xmlhttp.open("POST", "../jsp/LeastUAVServlet.action", true);
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
					ShowTask();
				}
				else if(txt=="FAILED")
				{
					alert("出错了");
				}
			}
		};
		
			xmlhttp.open("POST", "../jsp/AddTaskServlet.action", true);
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			var newlongitude=document.getElementById("newlongitudecomment").value;
            var newlatitude=document.getElementById("newlatitudecomment").value;		
			xmlhttp.send("newlongitude="+newlongitude+"&newlatitude="+newlatitude);    
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
			
			xmlhttp.open("POST", "../jsp/DeleteTaskPointServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var number=document.getElementById("num").value.trim();
			
			var longitude=markers[number].getPosition().getLng();
			var latitude=markers[number].getPosition().getLat();
			xmlhttp.send("longitude="+longitude+"&latitude="+latitude);
		}
		
		function ShowUAVInBase() 
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
				var UAV = xmlhttp.responseXML.documentElement.getElementsByTagName("UAVInBase");
				for (var i = 0; i <  UAV.length; i++)
				 {			
					var tmplongitude= UAV[i].getElementsByTagName("Longitude");
					var tmplatitude= UAV[i].getElementsByTagName("Latitude");
                     markerss[i] = new AMap.Marker(
                    {
				    	icon: "../images/uav.png",
    					position:[tmplongitude[0].firstChild.nodeValue,tmplatitude[0].firstChild.nodeValue],
    					map: mapObj,	
    					extData:i
    				});
    				markerss[i].setTitle("["+tmplongitude[0].firstChild.nodeValue+","+tmplatitude[0].firstChild.nodeValue+"]");
				  }
			}
		};
		
				xmlhttp.open("POST", "../jsp/ShowUAVInBaseServlet.action", true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");			
				xmlhttp.send();
	 }
		</script>
	</head>
	<body onload="ShowTask()">
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
			<div id="TaskShow" style="height: 600px"></div>
			<div class="button-group">
			<div id="getlanlat"><input type="text" readonly="true" id="lnglat"></div>
    		
			<div id="SumDistanceDiv"><input readonly="true" placeholder="总路程(米)" type="text"  id="consumpowerinput" style="width: 60px"></div>
			<div id="TimeDiv"><input readonly="true" placeholder="时间(分钟)" type="text"  id="heightinput" style="width: 90px"></div>
			<div id="SumDiv"><input readonly="true" placeholder="架次" type="text"  id="elevationinput" style="width: 60px"></div>
    		<input type="button" class="btn btn-info" value="较少无人机" id="showonline" onclick="LeastUAV()"/>
    		<input type="button" class="btn btn-info" value="添加任务点" id="addpoint" onclick="AddPoint()"/>
    		<input type="button" class="btn btn-info" value="删除任务点" id="deletepoint" onclick="deletefunction()"/>       		
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
