<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@ taglib  prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<fmt:setBundle basename="messages" scope="session"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'language.jsp' starting page</title>
    
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
   <form method="post" name="reg"><p>
   <fmt:message key="name"/>:<input type="text" name="nmae"></p><p>&nbsp;
   <fmt:message key="pwd" />: <input type="text" name="pwd"></p><p>
   <input type="submit" value="Submit" name="submit"><br></p></form> 
  </body>
</html>
