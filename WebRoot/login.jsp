<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>无人机网络控制平台</title>
		<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="css/mystyle.css" rel="stylesheet" type="text/css" />
		<link href="css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			function Login(){
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
						if(txt=="TRUE")
						{
							window.location.href="jsp/welcome.jsp"; 
						}
						else if(txt=="FAILED")
						{
							alert("用户名密码错误！");
						}
					}
				};
				xmlhttp.open("POST", "jsp/LoginServlet.action", true);
				xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				var account=document.getElementById("account").value;
				var password=document.getElementById("password").value;
				xmlhttp.send("account="+account+"&password="+password);				
			}
		</script>
	</head>
	<body id="login">
		<div id="login-wrapper" class="png_bg myfont">
			<div id="login-top">
				<h1>无人机网络控制平台</h1>
				<a href="#"><img id="logo" src="images/logo.png" alt="The logo" /></a>
			</div>
			<div id="login-content">
					<div class="notification information png_bg">
						<div>
							请输入您的用户名和密码。
						</div>
					</div>
					<p>
						<label>用&nbsp;&nbsp;户&nbsp;&nbsp;名</label>
						<input class="text-input loginInput" type="text" id="account"/>
					</p>
					<div class="clear"></div>
					<p>
						<label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label>
						<input class="text-input loginInput" type="password" id="password"/>
					</p>
					<div class="clear"></div>
					<p align="right">
						<input class="btn btn-info"  type="button" value="  登  陆 "  onclick="Login()"/>
					</p>
			</div>
		</div>
		<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
		<script type="text/javascript" src="scripts/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
		<script type="text/javascript" src="scripts/facebox.js"></script>
		<script type="text/javascript" src="scripts/jquery.wysiwyg.js"></script>
	</body>
</html>
