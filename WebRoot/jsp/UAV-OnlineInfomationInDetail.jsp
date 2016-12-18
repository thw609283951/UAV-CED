<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>UAV-OnlineInformationDetail</title>		
		<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
		<script type="text/javascript" src="../scripts/jquery.js"></script>
		<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
		<script type="text/javascript" src="../scripts/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../scripts/simpla.jquery.configuration.js"></script>
		<script type="text/javascript" src="../scripts/facebox.js"></script>
		<script type="text/javascript" src="../scripts/jquery.wysiwyg.js"></script>	
		<script type="text/javascript">
		function ShowOnlineInfo()
		{
			var xmlhttp;
			if(window.XMLHttpRequest)
			{
				xmlhttp=new XMLHttpRequest();
			}
			else
			{
				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange=function()
			{
				if(xmlhttp.readyState==4&&xmlhttp.status==200)
				{
					var txt="";
					txt=xmlhttp.responseText; 
					 if(txt=="FAILED")
					 {
						alert("显示失败");
						location.reload(true);
				     }
					else
					{					 
						//var  UAV = xmlhttp.responseXML.documentElement.getElementsByTagName("UAVOnline");
						var tmpipaddress=xmlhttp.responseXML.documentElement.getElementsByTagName("Ipaddress");			
						var tmpport=xmlhttp.responseXML.documentElement.getElementsByTagName("Port");
						var tmplongitude=xmlhttp.responseXML.documentElement.getElementsByTagName("Longitude");
						var tmplatitude= xmlhttp.responseXML.documentElement.getElementsByTagName("Latitude");			
						var tmpelevation= xmlhttp.responseXML.documentElement.getElementsByTagName("Elevation");
						var tmpheight= xmlhttp.responseXML.documentElement.getElementsByTagName("Height");
						var tmpheightjudge= xmlhttp.responseXML.documentElement.getElementsByTagName("HeightJudge");
						var tmpvelocity= xmlhttp.responseXML.documentElement.getElementsByTagName("Velocity");
    			   		var tmppowerconsumption=xmlhttp.responseXML.documentElement.getElementsByTagName("Powerconsumption");
    			   	 	var tmpremainingpower=xmlhttp.responseXML.documentElement.getElementsByTagName("Remainingpower");
    			   	 	var tmpremainingpowerjudge=xmlhttp.responseXML.documentElement.getElementsByTagName("RemainingpowerJudge");
    			   	 	var tmpversion= xmlhttp.responseXML.documentElement.getElementsByTagName("Version");
    			       			    
    			    	var elementip=document.getElementById("ipaddress");
    			    	var elementpo=document.getElementById("port");
    			    	var elementlo=document.getElementById("longitude");
    			    	var elementla=document.getElementById("latitude");
    			   		var elementel=document.getElementById("elevation");
    			    	var elementhe=document.getElementById("height");
    			    	var elementve=document.getElementById("velocity");	 
    			    	var elementpow=document.getElementById("powerconsumption");
    			    	var elementre=document.getElementById("remainingpower");
    			    	var elementver=document.getElementById("version"); 
    			    
    			    	var ip=tmpipaddress[0].firstChild.nodeValue;
						var po=tmpport[0].firstChild.nodeValue;
						var lo=tmplongitude[0].firstChild.nodeValue;
						var la=tmplatitude[0].firstChild.nodeValue;			
						var el=tmpelevation[0].firstChild.nodeValue;
						var he=tmpheight[0].firstChild.nodeValue;
						var hej=tmpheightjudge[0].firstChild.nodeValue;
						var ve=tmpvelocity[0].firstChild.nodeValue;
    			    	var pow=tmppowerconsumption[0].firstChild.nodeValue;
    			    	var re=tmpremainingpower[0].firstChild.nodeValue;
    			    	var rej=tmpremainingpowerjudge[0].firstChild.nodeValue;
    			   	 	var ver=tmpversion[0].firstChild.nodeValue;
    			    
    			    	elementip.value=ip;
    			    	elementpo.value=po;
    			    	elementlo.value=lo;
    			    	elementla.value=la;
    			    	elementel.value=el;
    			    	elementhe.value=he;
    			    	elementve.value=ve;
    			    	elementpow.value=pow;
    			    	elementre.value=re;
    			    	elementver.value=ver;  
    			    	if(hej=="true")
    			    	{
    			    		document.getElementById("heightjudge").style.backgroundImage="url('../images/icons/tick_circle.png')"; 
    			    	}
    			    	if(rej=="true")
    			    	{
    			    		document.getElementById("repowerjudgejudge").style.backgroundImage="url('../images/icons/tick_circle.png')"; 
    			    	}
    			    	
    				 }
    			}			            
			};				
			var ipaddress = getUrlParam('ipaddress');
		    var port = getUrlParam('port');
			xmlhttp.open("POST", "../jsp/ShowUAVOnlineInfoServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");			
			xmlhttp.send("ipaddress="+ipaddress+"&port="+port);
		}
		
				
		function ModifyOnlineInfo(){
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
					if(txt=="SUCCESSED"){
						alert("发送成功！");
						ShowOnlineInfo()
					}else if(txt=="FAILED"){
						alert("发送失败！");
						location.reload(true);
					}
					
				}
				
			};
			xmlhttp.open("POST", "../jsp/ModifyUAVOnlineInfoServlet.action", true);
			xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			
			var ipaddress=document.getElementById("ipaddress").value.trim();
			var port=document.getElementById("port").value.trim();
			var longitude=document.getElementById("longitude").value.trim();			
			var latitude=document.getElementById("latitude").value.trim();
			var elevation=document.getElementById("elevation").value.trim();
			var height=document.getElementById("height").value.trim();
			var velocity=document.getElementById("velocity").value.trim();
			xmlhttp.send("ipaddress="+ipaddress+"&port="+port+"&longitude="+longitude+"&latitude="+latitude+"&elevation="+elevation+"&height="+height+"&velocity="+velocity);
		}
		
		function rt(){
			document.getElementById("longitude").value='';
			document.getElementById("latitude").value='';
			document.getElementById("elevation").value='';
			document.getElementById("height").value='';
			document.getElementById("velocity").value='';
		}
		
		 function getUrlParam(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) 
            return unescape(r[2]); //返回参数值
            return null; 
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
		function checklatitude()
		{
			var reg=new RegExp(/^-?90\.\d{6}$|^-?[1-8]?\d\.\d{6}$/);
			var x=document.getElementById("latitude").value.trim();
			if(x.match(reg)==null)
			{
				alert("纬度格式不正确");
			}
		}
		function checkelvation()
		{
			var reg=new RegExp(/^-?90\.00$|^-?[1-8]?\d\.\d{2}?$/);
			var x=document.getElementById("elevation").value.trim();
			if(x.match(reg)==null)
			{
				alert("仰角格式不正确");
			}
		}
		function checkheight()
		{
			var reg=new RegExp(/^[1-9]\d{0,3}\.\d{2}$/);
			var x=document.getElementById("height").value.trim();
			if(x.match(reg)==null)
			{
				alert("高度格式不正确");
			}
		}
		function checkvelocity()
		{
			var reg=new RegExp(/^[1-9]\d{0,3}\.\d{2}$/);
			var x=document.getElementById("velocity").value.trim();
			if(x.match(reg)==null)
			{
				alert("速度格式不正确");
			}
		}
	
		</script>	
	</head>
	<body onload="ShowOnlineInfo()">
		<div id="body-wrapper" class="myfont" style="height: 670px; ">
			<div id="sidebar">
				<div id="sidebar-wrapper">
					<a href="#"><img id="logo" src="../images/logo.png" alt="The logo" style="width: 203px; "/></a>
					<div id="profile-links">
						你好, <a href="javascript:void(0);" title="Edit your profile">${Identity.name}</a>！
						<br />
						<br />
						<a href="../jsp/LogoutServlet.action" title="Sign Out">登出</a>
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
			<div id="main-contindex">
				<p class="main-cont-font">
					在线无人机详细信息
				</p>
				<table class="table mytable">
					<tr>
						<td>IP地址</td>
						<td>
						<input type="text"  maxlength="15" id="ipaddress" onblur="" disabled="disabled" value=""/>
						<span id="" class="input-notification"></span>
						</td>
					</tr>
					<tr>
						<td>端口号</td>
						<td>
						<input type="text"  maxlength="10" id="port" onblur="" disabled="disabled" value=""/>
						<span id="" class="input-notification"></span>
						</td>
					</tr>
					<tr>
						<td>经度</td>
						<td>
						<input type="text"  maxlength="11" id="longitude" onblur="checklongitude()"  value=""/>
						<span id="" class="input-notification"></span>
						</td>
					</tr>
					<tr>
						<td>纬度</td>
						<td>
						<input type="text"  maxlength="10" id="latitude" onblur="checklatitude()"  value=""/>
						<span id="" class="input-notification"></span>
						</td>
					</tr>
					<tr>
						<td>仰角</td>
						<td>
						<input type="text"  maxlength="6" id="elevation" onblur="checkelvation()"  value=""/>
						<span id="" class="input-notification"></span>
						</td>
					</tr>
					<tr>
						<td>高度</td>
						<td>
						<input type="text"  maxlength="8" id="height" onblur="checkheight()"  value=""/>
						<span id="heightjudge" class="input-notification warning"></span>
						</td>
					</tr>
					<tr>
						<td>速度</td>
						<td>
						<input type="text"  maxlength="8" id="velocity" onblur="checkvelocity()"  value=""/>
						<span id="" class="input-notification"></span></td>
					</tr>
					<tr>
						<td>耗电量</td>
						<td>
						<input type="text"  maxlength="10" id="powerconsumption" onblur="" disabled="disabled" value=""/>
						<span id="" class="input-notification"></span>
						</td>
					</tr>
					<tr>
						<td>剩余电量</td>
						<td>
						<input type="text"  maxlength="10" id="remainingpower" onblur="" disabled="disabled" value=""/>
						<span id="repowerjudgejudge" class="input-notification warning"></span>
						</td>
					</tr>
					<tr>
						<td>型号</td>
						<td>
							<input type="text" id="version" maxlength="10" disabled="disabled" value=""/>
							<span id="" class="input-notification"></span></td>
						<td>
					</tr>
					<tr>
						<td colspan="2">
						<a class="btn btn-info" href="javascript:if(confirm('确认发送?'))ModifyOnlineInfo();">发送命令</a>
						<input type="reset" class="btn btn-info" value="重置" onclick="rt()" />
						</td>
					</tr>
				</table>		
			</div>
		</div>
		<div id="footer">
					 <small>&#169; Copyright 2016 Graduation Project | Powered by <a href="#">heyongcheng
							 </a> | <a href="#">Top</a> </small>
	   </div>	
	</body>
</html>
