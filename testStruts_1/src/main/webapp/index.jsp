<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>Login</title>
	</head>
	<body>
		<html:form action="/login">
			userName : <html:text property="userName"/><html:errors property="userName"/><br/>
			passWord : <html:text property="passWord"/><html:errors property="passWord"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

