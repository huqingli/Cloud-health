<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
	<head>
	<meta charset="utf-8">
	<base href="<%=basePath%>">
    
    <title>My JSP 'weatherJsp.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>
	<body> 
	
		<img src="img/good.png" width="100px" height="100px"    align="right"/>
				<p>推荐饮食</p>
				<ul >
					<li>鱼类</li>
					<li>豆类，如黄豆</li>
					<li>黑芝麻</li>
					<li>蔬菜，如菠菜，胡萝卜</li>
				</ul>
				<p>忌讳饮食</p>
				<ul >
					<li>辛辣食物</li>
					<li>烧烤</li>
					<li>膨化食品</li>
					<li>高热量食物卜</li>
				</ul>
				

			
	</body>
</html>
