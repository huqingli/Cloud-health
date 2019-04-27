<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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

  <p ><font size="+3" color="#3399FF" >监测报告</font></p>
  <c:forEach var="stu" items="${list}" >  
      <p  align="center" > ${stu.grade} </p>
      <p>  <td>${stu.resultcomment}</td> </p>
  </c:forEach>  

 
  <p align="center" >您的心脏年龄：30岁  </p> 
  <p> 您的心脏年龄是30岁，比您的实际年龄大9岁。您拥有一颗成熟的心脏，在紧张的工作活动之余，睡眠，适量运动及合理膳食有助于心脏健康</p>
  <p align="center" >您的心脏风险：低 </p>
  <p>您的心脏变异率稍低。低心率变异率可能与心脏病发作风险相关。积极锻炼和饮食平衡可以帮助降低心脏病发作风险。</p>
  
  

  <p align="center" class="STYLE2">&nbsp;</p>
</body>
</html>
