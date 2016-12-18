<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>AddRestrictHeightAndremainingpower.jsp</title>		
		<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
		<script type="text/javascript" src="../scripts/jquery.js"></script>	
		<script type="text/javascript">
		function addRestrictHeightAndremainingpower(){
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
						alert("添加成功！");
						SearchAllRestriction();
					}else{
						alert("添加失败！");
					}					
				}				
			};
			xmlhttp.open("POST", "../jsp/AddRestrictServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var version = document.getElementById("version").value.trim();
			var maxheight = document.getElementById("maxheight").value.trim();
			var minrp = document.getElementById("minrp").value.trim();
			xmlhttp.send("version="+version+"&maxheight="+maxheight+"&minrp="+minrp);
		}
		function checkversion()
		{
			var reg=new RegExp(/^\d{1,5}$/);
			var x=document.getElementById("version").value.trim();
			if(x.match(reg)==null)
			{
				alert("型号格式不正确");
			}
		}
		function checkheight()
		{
			var reg=new RegExp(/^[1-9]\d{0,3}\.\d{2}$/);
			var x=document.getElementById("maxheight").value.trim();
			if(x.match(reg)==null)
			{
				alert("高度格式不正确");
			}
		}
		function checknewheight()
		{
			var reg=new RegExp(/^[1-9]\d{0,3}\.\d{2}$/);
			var x=$("#newheightcomment").val();
			if(x.match(reg)==null)
			{
				alert("新高度格式不正确");
			}
		}
		function checkremainingpower()
		{
			var reg=new RegExp(/^[1-9]\d?$/);
			var x=document.getElementById("minrp").value.trim();
			if(x.match(reg)==null)
			{
				alert("剩余电量格式不正确");
			}
		}
		function checknewremainingpower()
		{
			var reg=new RegExp(/^[1-9]\d?$/);
			var x=$('#newminrpcomment').val();
			if(x.match(reg)==null)
			{
				alert("新剩余电量格式不正确");
			}
		}
		
		function SearchAllRestriction(){
			var xmlhttp;
			if(window.XMLHttpRequest){
				xmlhttp=new XMLHttpRequest();
			}else{
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){									
					var RestrictionList=xmlhttp.responseXML.documentElement.getElementsByTagName("Restriction");			
					var HTML="<tr><td>无人机型号</td><td>最大高度</td><td>最低剩余电量</td><td>修改</td><td>删除</td></tr>";
					var i;
					for(i=0;i<RestrictionList.length;i++){
						HTML=HTML+"<tr>";
						tmp=RestrictionList[i].getElementsByTagName("Version");
						HTML=HTML+"<td id=\"VERSION"+(i+1)+"\">"+tmp[0].firstChild.nodeValue+"</td>";
						tmp=RestrictionList[i].getElementsByTagName("Height");
						HTML=HTML+"<td>"+tmp[0].firstChild.nodeValue+"</td>";
						tmp=RestrictionList[i].getElementsByTagName("Remainingpower");
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
						HTML=HTML+"<a href=\"javascript:SearchAllRestrictionWithPage(1)\">首页</a>| <a href=\"javascript:SearchAllRestrictionWithPage("+prePage+")\">前一页</a>";
					}else{
						HTML=HTML+"首页 | 前一页";
					}
					HTML=HTML+"<span style=\"margin-left: 50px;margin-right: 50px;\">第&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+currentPage+"\"/>&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+totalPage+"\"/>&nbsp;&nbsp;页</span>";
					if(hasNextPage=="true"){
						var nexPage=parseInt(currentPage)+1;
						HTML=HTML+"<a href=\"javascript:SearchAllRestrictionWithPage("+nexPage+")\">后一页</a>| <a href=\"javascript:SearchAllRestrictionWithPage("+totalPage+")\">末页</a>";
					}else{
						HTML=HTML+"后一页 | 末页";
					}					
					document.getElementById("allresriction").innerHTML=HTML;							
				}
				
			};
			xmlhttp.open("POST", "../jsp/SearchAllRestrictionServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xmlhttp.send(null);
		}
		
		
		function SearchAllRestrictionWithPage(currentPage){
			var xmlhttp;
			if(window.XMLHttpRequest){
				xmlhttp=new XMLHttpRequest();
			}else{
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function(){
				if(xmlhttp.readyState==4&&xmlhttp.status==200){										
					var RestrictionList=xmlhttp.responseXML.documentElement.getElementsByTagName("Restriction");			
					var HTML="<tr><td>无人机型号</td><td>最大高度</td><td>最低剩余电量</td><td>修改</td><td>删除</td></tr>";
					var i;
					for(i=0;i<RestrictionList.length;i++){
						HTML=HTML+"<tr>";
						tmp=RestrictionList[i].getElementsByTagName("Version");
						HTML=HTML+"<td id=\"VERSION"+(i+1)+"\">"+tmp[0].firstChild.nodeValue+"</td>";
						tmp=RestrictionList[i].getElementsByTagName("Height");
						HTML=HTML+"<td>"+tmp[0].firstChild.nodeValue+"</td>";
						tmp=RestrictionList[i].getElementsByTagName("Remainingpower");
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
						HTML=HTML+"<a href=\"javascript:SearchAllRestrictionWithPage(1)\">首页</a>| <a href=\"javascript:SearchAllRestrictionWithPage("+prePage+")\">前一页</a>";
					}else{
						HTML=HTML+"首页 | 前一页";
					}
					HTML=HTML+"<span style=\"margin-left: 50px;margin-right: 50px;\">第&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+currentPage+"\"/>&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;共&nbsp;&nbsp;<input type=\"button\" class=\"btn btn-info\" disabled=\"disabled\" value=\""+totalPage+"\"/>&nbsp;&nbsp;页</span>";
					if(hasNextPage=="true"){
						var nexPage=parseInt(currentPage)+1;
						HTML=HTML+"<a href=\"javascript:SearchAllRestrictionWithPage("+nexPage+")\">后一页</a>| <a href=\"javascript:SearchAllRestrictionWithPage("+totalPage+")\">末页</a>";
					}else{
						HTML=HTML+"后一页 | 末页";
					}
					document.getElementById("allresriction").innerHTML=HTML;										
				}				
			};
			xmlhttp.open("POST", "../jsp/SearchAllRestrictionServlet.action", true);
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
						SearchAllRestriction();
					}else{
						alert("删除失败");
					}
				}				
			};
			
			xmlhttp.open("POST", "../jsp/DeleteRestrictionServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var number=document.getElementById("num").value.trim();
			var VERSION=document.getElementById("VERSION"+number).innerHTML;
			xmlhttp.send("version="+VERSION);
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
			document.getElementById("version").value='';
			document.getElementById("maxheight").value='';
			document.getElementById("minrp").value='';
		}
		function ModifyRestriction()
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
						SearchAllRestriction();
					}else{
						alert("修改失败");
					}
				}				
			};
			
			xmlhttp.open("POST", "../jsp/ModifyRestrictionServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			var number=document.getElementById("num").value.trim();
			var VERSION=document.getElementById("VERSION"+number).innerHTML;
			var newmaxheight=document.getElementById("newheightcomment").value;
			var newminrp=document.getElementById("newminrpcomment").value;
			xmlhttp.send("version="+VERSION+"&newmaxheight="+newmaxheight+"&newminrp="+newminrp);
		}
		</script>
	</head>
	<body onload="SearchAllRestriction()">
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
								<table class="table mytable" style="width: 541px; ">
									<tr>
										<td>最大高度</td>
										<td>
											<input type="text"  maxlength="7" id="newmaxheight" oninput="javascript:document.getElementById('newheightcomment').value=this.value;" onblur="checknewheight()"/> 
											<span id="" class="input-notification information png_bg"></span>
										</td>
										<td >最小剩余电量</td>
										<td>
										<input type="text"  maxlength="50" id="newminrp" oninput="javascript:document.getElementById('newminrpcomment').value=this.value;" onblur="checknewremainingpower()">
										<span id="" class="input-notification information png_bg"></span>
										</td>
									</tr>
									<tr>
										<td colspan="4">
										<input type="button" class="btn btn-info" value="提交" onclick="javascript:if(confirm('确认提交？')){ModifyRestriction();$(document).trigger('close.facebox');}"/>
										</td>
									</tr>
								</table>
							</fieldset>
					</div>					
				</div>
			</div>
			<div id="main-content">
				<p class="main-cont-font">
					添加限制
				</p>
				<table class="table mytable">
					<tr>
						<td>无人机型号</td>
						<td>
						<input type="text" name="dName"  maxlength="5" id="version" onblur=""/>
						<span id="" class="input-notification information png_bg"></span>
						</td>
					</tr>
					<tr>
						<td>最大高度</td>
						<td>
						<input type="text"  maxlength="7" id="maxheight" onblur="checkheight()"/>
						<span id="" class="input-notification information png_bg"></span>
						</td>
					</tr>	
					<tr>
						<td >最小剩余电量</td>
						<td >
						<input type="text"  maxlength="2" id="minrp" onblur="checkremainingpower()">
						<span id="" class="input-notification information png_bg"></span>
						</td>
					</tr>
									
					<tr>
						<td colspan="2">
						<input type="button" class="btn btn-info" value="添加" onclick="javascript:if(confirm('确认提交？')){addRestrictHeightAndremainingpower();$(document).trigger('close.facebox');}" />
						<input type="reset" class="btn btn-info"  value = "重置" id="" onclick="reset()"/>
						</td>
					</tr>
				</table>
				<p class="main-cont-font">
					所有限制
				</p>
				<table class="table mytable" id="allresriction">
				</table>
				<input type="hidden" id="num" />
				<input type="hidden" id="newheightcomment" />
				<input type="hidden" id="newminrpcomment" />
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
