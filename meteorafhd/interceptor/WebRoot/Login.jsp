<%@page language="java" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>Login</title>
	</head>
	<body>
		<h1>Login</h1>
		Please select a role below:
		<s:bean id="roles" name="com.test.interceptor.Roles" />
		<s:form action="Login">
			<s:radio list="#roles.roles" value="EMPLOYEE" name="role" label="Role" />
			<s:submit/>
		</s:form>
	</body>
</html>
