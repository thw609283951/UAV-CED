<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Welcome</title>		
		<link href="../css/bootstrap.min.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../scripts/bootstrap-carousel.js"></script>
		<script type="text/javascript" src="../scripts/jquery.js"></script>	
	</head>
	<body>
		<div id="body-wrapper" class="myfont">
			<div id="sidebar">
				<div id="sidebar-wrapper">
					<a href="#"><img id="logo" src="../images/logo.png" alt="The logo" /></a>
					<div id="profile-links">
						你好, <a href="javascript:void(0);" title="Edit your profile">${Identity.name}</a>！
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
									<a class="nav-top-item">设置飞行点</a>
									<ul>
										<li>
											<a href="addUAVInBase.jsp">设置飞行点</a>
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
                                <li>
									<a class="nav-top-item">多机协同任务规划</a>
									<ul>
										<li>
											<a href="LeastUAV.jsp">最少无人机额定派送任务</a>
										</li>
										<li>
											<a href="LessTime.jsp">最少时间额定派送任务</a>
										</li>
										<li>
											<a href="MaxTask.jsp">额定时间额定无人机最大派送任务</a>
										</li>			
										<li>
											<a href="TraveTest.jsp">车队-无人机集群快递派送任务</a>
										</li>							 
									</ul>
								</li>
							</ul>
						</li>
					</ul>					
				</div>
			</div>
			<div id="main-contindex">
				<h2>
					欢迎使用无人机网路控制系统！
				</h2>
				<p id="page-intro">
					请在左侧分区选择相关功能...
				</p>
				<div class="content-box">
					<div class="content-box-header">
						<ul class="content-box-tabs">
							<li>
								<a href="welcome.jsp" class="default-tab">无人机</a>
							</li>
						</ul>
					</div>
					<div class="content-box-content">
						<div class="tab-content default-tab" id="tab1">
							<div id="myCarousel" class="carousel slide">								
								<div class="carousel-inner">																	
									<div class="item active">
										<img class="indeximage"
											src="../images/biguav1.jpg"
											alt="">
										<div class="carousel-caption">
											<h4>
												Welcome to UAV Control Station
											</h4>
											<p>
												欢迎进入无人机网络控制平台。
											</p>
										</div>
									</div>
								</div>
								
							</div>
						</div>					
					</div>
				</div>
				
				<div class="clear"></div>
               </div>
				<div id="footer">
					<small>&#169; Copyright 2016 Graduation Project | Powered by <a href="welcome.jsp">heyongcheng
							 </a> | <a href="welcome.jsp">Top</a> </small>
				
			  </div>
		</div>
		<script type="text/javascript" src="../scripts/bootstrap.min.js"></script>
		<script type="text/javascript" src="../scripts/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="../scripts/simpla.jquery.configuration.js"></script>
		<script type="text/javascript" src="../scripts/facebox.js"></script>
		<script type="text/javascript" src="../scripts/jquery.wysiwyg.js"></script>
	</body>
</html>
