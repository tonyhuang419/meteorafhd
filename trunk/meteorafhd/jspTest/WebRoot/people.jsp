
<%@ page language="java" import="java.util.*,chatServlet.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
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
	<% 
  	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");	
	response.addHeader("refresh", "2");
	%>
    <p><B>Online:</B></p>
    <%
 	Iterator<String> it = CollectionX.readNameList();
	while(it.hasNext()){
		out.println(it.next()+"<br>");
	}
   %>
  </body>
</html>
