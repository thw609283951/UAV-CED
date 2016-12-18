<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>AddBanArea</title>		
		<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
		<script type="text/javascript" src="../scripts/jquery.js"></script>
		<script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=您申请的key值"></script>
		<script type="text/javascript" src="http://cache.amap.com/lbs/static/addToolbar.js"></script>		
		<script type="text/javascript">
			function addBanArea(){
			var xmlhttp;
			if(window.XMLHttpRequest){
				xmlhttp=new XMLHttpRequest();
			}else{
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){
					var txt="";
					txt=xmlhttp.responseText;
					if(txt!="FAILED"){
						alert("您添加的区域已加入到禁飞区域");
						SearchAllBanArea();
					}else{
						alert("您添加的区域加入到禁飞区域失败！");
					}					
				}				
			};
			xmlhttp.open("POST", "../jsp/AddBanAreaServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var longitude= document.getElementById("longitude").value.trim();
			var latitude= document.getElementById("latitude").value.trim();
			var radius= document.getElementById("radius").value.trim();
			var bignewradius=Number(radius)+Number(1);
			var circle = new AMap.Circle
                  	({
       	 				center: new AMap.LngLat(longitude,latitude),// 圆心位置
        				radius:bignewradius,//半径
   				 	});		 	
   			var northeastlng=circle.getBounds().getNorthEast().getLng();
   			var northeastlat=circle.getBounds().getNorthEast().getLat();
   			var southwestlng=circle.getBounds().getSouthWest().getLng();
   			var southwestlat=circle.getBounds().getSouthWest().getLat();
   			
			xmlhttp.send("longitude="+longitude+"&latitude="+latitude+"&radius="+radius+"&northeastlng="+northeastlng+"&northeastlat="+northeastlat+"&southwestlng="+southwestlng+"&southwestlat="+southwestlat);
		}
		function SearchAllBanArea(){
			var xmlhttp;
			if(window.XMLHttpRequest){
				xmlhttp=new XMLHttpRequest();
			}else{
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){									
					var BanAreaList=xmlhttp.responseXML.documentElement.getElementsByTagName("BanArea");			
					var HTML="<tr><td>圆心经度</td><td>圆心纬度</td><td>半径长度(m)</td><td>修改</td><td>删除</td></tr>";
					var i;
					for(i=0;i<BanAreaList.length;i++){
						HTML=HTML+"<tr>";
						tmp=BanAreaList[i].getElementsByTagName("Longitude");
						HTML=HTML+"<td id=\"LONGITUDE"+(i+1)+"\">"+tmp[0].firstChild.nodeValue+"</td>";
						tmp=BanAreaList[i].getElementsByTagName("Latitude");
						HTML=HTML+"<td id=\"LATITUDE"+(i+1)+"\">"+tmp[0].firstChild.nodeValue+"</td>";
						tmp=BanAreaList[i].getElementsByTagName("Radius");
						HTML=HTML+"<td>"+tmp[0].firstChild.nodeValue+"</td>";			
						HTML=HTML+"<td><a id=\"a"+(i+1)+"\" class=\"btn btn-info\" href=\"javascript:setMark("+(i+1)+");\" >修改</a></td>";
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
						HTML=HTML+"<a href=\"javascript:SearchAllBanAreaWithPage(1)\">首页</a>| <a href=\"javascript:SearchAllBanAreaWithPage("+prePage+")\">前一页</a>";
					}else{
						HTML=HTML+"首页 | 前一页";
					}
					HTML=HTML+"<span style=\"margin-left: 50px;margin-right: 50px;\">第&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+currentPage+"\"/>&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+totalPage+"\"/>&nbsp;&nbsp;页</span>";
					if(hasNextPage=="true"){
						var nexPage=parseInt(currentPage)+1;
						HTML=HTML+"<a href=\"javascript:SearchAllBanAreaWithPage("+nexPage+")\">后一页</a>| <a href=\"javascript:SearchAllBanAreaWithPage("+totalPage+")\">末页</a>";
					}else{
						HTML=HTML+"后一页 | 末页";
					}					
					document.getElementById("allbanarea").innerHTML=HTML;							
				}
				
			};
			xmlhttp.open("POST", "../jsp/SearchAllBanAreaServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xmlhttp.send(null);
		}
		
		
		function SearchAllBanAreaWithPage(currentPage){
			var xmlhttp;
			if(window.XMLHttpRequest){
				xmlhttp=new XMLHttpRequest();
			}else{
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){										
					var BanAreaList=xmlhttp.responseXML.documentElement.getElementsByTagName("BanArea");			
					var HTML="<tr><td>圆心经度</td><td>圆心纬度</td><td>半径长度(m)</td><td>修改</td><td>删除</td></tr>";
					var i;
					for(i=0;i<BanAreaList.length;i++){
						HTML=HTML+"<tr>";
						tmp=BanAreaList[i].getElementsByTagName("Longitude");
						HTML=HTML+"<td id=\"LONGITUDE"+(i+1)+"\">"+tmp[0].firstChild.nodeValue+"</td>";
						tmp=BanAreaList[i].getElementsByTagName("Latitude");
						HTML=HTML+"<td id=\"LATITUDE"+(i+1)+"\">"+tmp[0].firstChild.nodeValue+"</td>";
						tmp=BanAreaList[i].getElementsByTagName("Radius");
						HTML=HTML+"<td>"+tmp[0].firstChild.nodeValue+"</td>";			
						HTML=HTML+"<td><a id=\"a"+(i+1)+"\" class=\"btn btn-info\" href=\"javascript:setMark("+(i+1)+");\" >修改</a></td>";
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
						HTML=HTML+"<a href=\"javascript:SearchAllBanAreaWithPage(1)\">首页</a>| <a href=\"javascript:SearchAllBanAreaWithPage("+prePage+")\">前一页</a>";
					}else{
						HTML=HTML+"首页 | 前一页";
					}
					HTML=HTML+"<span style=\"margin-left: 50px;margin-right: 50px;\">第&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+currentPage+"\"/>&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+totalPage+"\"/>&nbsp;&nbsp;页</span>";
					if(hasNextPage=="true"){
						var nexPage=parseInt(currentPage)+1;
						HTML=HTML+"<a href=\"javascript:SearchAllBanAreaWithPage("+nexPage+")\">后一页</a>| <a href=\"javascript:SearchAllBanAreaWithPage("+totalPage+")\">末页</a>";
					}else{
						HTML=HTML+"后一页 | 末页";
					}
					document.getElementById("allbanarea").innerHTML=HTML;										
				}				
			};
			xmlhttp.open("POST", "../jsp/SearchAllBanAreaServlet.action", true);
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
						SearchAllBanArea();
					}else{
						alert("删除失败");
					}
				}				
			};
			
			xmlhttp.open("POST", "../jsp/DeleteBanAreaServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var number=document.getElementById("num").value.trim();
			var longitude=document.getElementById("LONGITUDE"+number).innerHTML;
			var latitude=document.getElementById("LATITUDE"+number).innerHTML;
			xmlhttp.send("longitude="+longitude+"&latitude="+latitude);
		}
		function setMark(num){
			document.getElementById("num").value=num;
			$("#modify").click();
		}
		function setMarker(num){
			document.getElementById("num").value=num;
			deletefunction();
		}
		function reset()
		{
			document.getElementById("longitude").value='';
			document.getElementById("latitude").value='';
			document.getElementById("radius").value='';
		}
		
		
		
		function ModifyBanArea()
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
						alert("修改成功");
						SearchAllBanArea();
					}else{
						alert("修改失败");
					}
				}				
			};
			
			xmlhttp.open("POST", "../jsp/ModifyBanAreaServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var number=document.getElementById("num").value.trim();
			var longitude=document.getElementById("LONGITUDE"+number).innerHTML;
			var latitude=document.getElementById("LATITUDE"+number).innerHTML;
			var newradius=document.getElementById("newradiuscomment").value;
			
			var bignewradius=Number(newradius)+Number(1);
			var circle = new AMap.Circle
                  	({
       	 				center: new AMap.LngLat(longitude,latitude),// 圆心位置
        				radius:bignewradius, //半径
   				 	});
   				 			 	
   			var northeastlng=circle.getBounds().getNorthEast().getLng();
   			var northeastlat=circle.getBounds().getNorthEast().getLat();
   			var southwestlng=circle.getBounds().getSouthWest().getLng();
   			var southwestlat=circle.getBounds().getSouthWest().getLat();
   			
			xmlhttp.send("longitude="+longitude+"&latitude="+latitude+"&newradius="+newradius+"&northeastlng="+northeastlng+"&northeastlat="+northeastlat+"&southwestlng="+southwestlng+"&southwestlat="+southwestlat);
		}
		
		
		
		function checklatitude()
		{
			var reg=new RegExp(/^-?90\.\d{6}$|^-?[1-8]?\d\.\d{6}$/);
			var x=document.getElementById("latitude").value.trim();
			if(x.match(reg)==null)
			{
				alert("纬度格式不正确");
			}
		}
		function checklongitude()
		{
			var reg=new RegExp(/^-?180\.\d{6}$|^-?1[0-7]\d\.\d{6}$|^-?[1-9]?\d\.\d{6}$/);
			var x=document.getElementById("longitude").value;
			if(x.match(reg)==null)
			{
				alert("经度格式不正确");
			}
		}
		function checkradius()
		{
			var reg=new RegExp(/^[1-9]?\d?\.\d{2}$/);
			var x=document.getElementById("radius").value;
			if(x.match(reg)==null)
			{
				alert("半径格式不正确");
			}
		}
		function checknewradius()
		{
			var reg=new RegExp(/^[1-9]?\d?\.\d{2}$/);
			var x=document.getElementById("newradiuscomment").value;
			if(x.match(reg)==null)
			{
				alert("新半径格式不正确");
			}
		}
		</script>
	</head>
	<body onload="SearchAllBanArea()">
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
					<div id="messages" style="display: none">
						<h3>修改</h3>
							<fieldset>
								<table class="table mytable" style="width: 254px; ">
									<tr>
										<td>新半径</td>
										<td>
											<input type="text"  maxlength="5" id="newradius" oninput="javascript:document.getElementById('newradiuscomment').value=this.value;" onblur="checknewradius()"/> 
											<span id="" class="input-notification information png_bg"></span>
										</td>
									</tr>
									<tr>
										<td colspan="2">
										<input type="button" class="btn btn-info" value="提交" onclick="javascript:if(confirm('确认提交？')){ModifyBanArea();$(document).trigger('close.facebox');}"/>
										</td>
									</tr>
								</table>
							</fieldset>
					</div>					
				</div>
			</div>
			<div id="main-content">
				<p class="main-cont-font">
					添加禁飞区
				</p>
				<table class="table mytable">
					<tr>
						<td>经度</td>
						<td>
						<input type="text"  maxlength="11" id="longitude" onblur="checklongitude()"/>
						<span id="" class="input-notification information png_bg"></span>
						</td>
					</tr>
					<tr>
						<td>纬度</td>
						<td>
						<input type="text"  maxlength="10" id="latitude" onblur="checklatitude()"/>
						<span id="" class="input-notification information png_bg"></span>
						</td>
					</tr>	
					<tr>
						<td >半径</td>
						<td >
						<input type="text"  maxlength="5" id="radius" onblur="checkradius()">
						<span id="" class="input-notification information png_bg"></span>
						</td>
					</tr>
									
					<tr>
						<td colspan="2">
						<input type="button" class="btn btn-info" value="添加" onclick="javascript:if(confirm('确认提交？')){addBanArea();$(document).trigger('close.facebox');}" />
						<input type="reset" class="btn btn-info"  value = "重置" id="" onclick="reset()"/>
						</td>
					</tr>
				</table>
				<p class="main-cont-font">
					所有禁飞区
				</p>
				<table class="table mytable" id="allbanarea">
				</table>
				<input type="hidden" id="num" />
				<input type="hidden" id="newradiuscomment" />
				<a href="#messages" rel="modal" id="modify" style="display: none;"></a>
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
