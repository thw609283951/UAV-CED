<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>DeleteZonePoint</title>		
		<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
		<script type="text/javascript" src="../scripts/jquery.js"></script>	
		<script type="text/javascript">
		function SearchAllZone(){
			var xmlhttp;
			if(window.XMLHttpRequest){
				xmlhttp=new XMLHttpRequest();
			}else{
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){									
					var ZoneList=xmlhttp.responseXML.documentElement.getElementsByTagName("ZonePoint");			
					var HTML="<tr><td>定点经度</td><td>定点纬度</td><td>删除</td></tr>";
					var i;
					for(i=0;i<ZoneList.length;i++){
						HTML=HTML+"<tr>";
						tmp=ZoneList[i].getElementsByTagName("Longitude");
						HTML=HTML+"<td id=\"LONGITUDE"+(i+1)+"\">"+tmp[0].firstChild.nodeValue+"</td>";
						tmp=ZoneList[i].getElementsByTagName("Latitude");
						HTML=HTML+"<td id=\"LATITUDE"+(i+1)+"\">"+tmp[0].firstChild.nodeValue+"</td>";			
						HTML=HTML+"<td><a id=\"a"+(i+1)+"\" class=\"btn btn-info\" href=\"javascript:setMarker("+(i+1)+");\" >删除</a></td></tr>";
					}									
					//分页
					var page=xmlhttp.responseXML.documentElement.getElementsByTagName("page");
					var hasPrePage=page[0].getElementsByTagName("hasPrePage")[0].firstChild.nodeValue;
					var hasNextPage=page[0].getElementsByTagName("hasNextPage")[0].firstChild.nodeValue;
					var totalPage=page[0].getElementsByTagName("totalPage")[0].firstChild.nodeValue;
					var currentPage=page[0].getElementsByTagName("currentPage")[0].firstChild.nodeValue;
					HTML=HTML+"<tr><td colspan=\"8\">";
					if(hasPrePage=="true"){
						var prePage=parseInt(currentPage)-1;
						HTML=HTML+"<a href=\"javascript:SearchAllZoneWithPage(1)\">首页</a>| <a href=\"javascript:SearchAllZoneWithPage("+prePage+")\">前一页</a>";
					}else{
						HTML=HTML+"首页 | 前一页";
					}
					HTML=HTML+"<span style=\"margin-left: 50px;margin-right: 50px;\">第&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+currentPage+"\"/>&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+totalPage+"\"/>&nbsp;&nbsp;页</span>";
					if(hasNextPage=="true"){
						var nexPage=parseInt(currentPage)+1;
						HTML=HTML+"<a href=\"javascript:SearchAllZoneWithPage("+nexPage+")\">后一页</a>| <a href=\"javascript:SearchAllZoneWithPage("+totalPage+")\">末页</a>";
					}else{
						HTML=HTML+"后一页 | 末页";
					}					
					document.getElementById("allzone").innerHTML=HTML;							
				}
				
			};
			xmlhttp.open("POST", "../jsp/SearchAllZoneServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xmlhttp.send(null);
		}
		
		
		function SearchAllZoneWithPage(currentPage){
			var xmlhttp;
			if(window.XMLHttpRequest){
				xmlhttp=new XMLHttpRequest();
			}else{
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){										
					var ZoneList=xmlhttp.responseXML.documentElement.getElementsByTagName("ZonePoint");			
					var HTML="<tr><td>定点经度</td><td>顶点纬度</td><td>删除</td></tr>";
					var i;
					for(i=0;i<ZoneList.length;i++){
						HTML=HTML+"<tr>";
						tmp=ZoneList[i].getElementsByTagName("Longitude");
						HTML=HTML+"<td id=\"LONGITUDE"+(i+1)+"\">"+tmp[0].firstChild.nodeValue+"</td>";
						tmp=ZoneList[i].getElementsByTagName("Latitude");
						HTML=HTML+"<td id=\"LATITUDE"+(i+1)+"\">"+tmp[0].firstChild.nodeValue+"</td>";		
						HTML=HTML+"<td><a id=\"a"+(i+1)+"\" class=\"btn btn-info\" href=\"javascript:setMarker("+(i+1)+");\" >删除</a></td></tr>";
					}									
					//分页
					var page=xmlhttp.responseXML.documentElement.getElementsByTagName("page");
					var hasPrePage=page[0].getElementsByTagName("hasPrePage")[0].firstChild.nodeValue;
					var hasNextPage=page[0].getElementsByTagName("hasNextPage")[0].firstChild.nodeValue;
					var totalPage=page[0].getElementsByTagName("totalPage")[0].firstChild.nodeValue;
					var currentPage=page[0].getElementsByTagName("currentPage")[0].firstChild.nodeValue;
					HTML=HTML+"<tr><td colspan=\"8\">";
					if(hasPrePage=="true"){
						var prePage=parseInt(currentPage)-1;
						HTML=HTML+"<a href=\"javascript:SearchAllZoneWithPage(1)\">首页</a>| <a href=\"javascript:SearchAllZoneWithPage("+prePage+")\">前一页</a>";
					}else{
						HTML=HTML+"首页 | 前一页";
					}
					HTML=HTML+"<span style=\"margin-left: 50px;margin-right: 50px;\">第&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+currentPage+"\"/>&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+totalPage+"\"/>&nbsp;&nbsp;页</span>";
					if(hasNextPage=="true"){
						var nexPage=parseInt(currentPage)+1;
						HTML=HTML+"<a href=\"javascript:SearchAllZoneWithPage("+nexPage+")\">后一页</a>| <a href=\"javascript:SearchAllZoneWithPage("+totalPage+")\">末页</a>";
					}else{
						HTML=HTML+"后一页 | 末页";
					}
					document.getElementById("allzone").innerHTML=HTML;										
				}				
			};
			xmlhttp.open("POST", "../jsp/SearchAllZoneServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xmlhttp.send("currentPage="+currentPage);
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
						alert("删除成功");
						SearchAllZone();
					}else{
						alert("删除失败");
					}
				}				
			};
			
			xmlhttp.open("POST", "../jsp/DeleteZonePointServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var number=document.getElementById("num").value.trim();
			var longitude=document.getElementById("LONGITUDE"+number).innerHTML;
			var latitude=document.getElementById("LATITUDE"+number).innerHTML;
			xmlhttp.send("longitude="+longitude+"&latitude="+latitude);
		}
		function setMarker(num){
			document.getElementById("num").value=num;
			deletefunction();
		}
		</script>
	</head>
	<body onload="SearchAllZone()">
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
			<div id="main-content">
				<p class="main-cont-font">
					覆盖区定点
				</p>
				<table class="table mytable" id="allzone">
				</table>
				<input type="hidden" id="num" />
				</div>
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
