<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>Login</title>
	</head>
	<body>
		<html:form action="/gologindy">
			username : <html:text property="usernamedy"/><br/>
			password : <html:text property="passworddy"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

