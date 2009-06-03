<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<html> 
	<head>
		<% 
		response.setHeader("Pragma","No-cache"); 
		response.setHeader("Cache-Control","no-cache"); 
		response.setDateHeader("Expires", 0); 
		%> 
		<title>Login</title>
	</head>
	<body>
		<html:form action="/jump" method="post">
			username : <html:text property="username"/><html:errors property="username"/><br/>
			password : <html:text property="password"/><html:errors property="password"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

