<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@ page
	import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<html>
<head>
<title>Acegi sample application: Login page</title>
</head>
<body>
<h2>Login to Sample Applicaiton !</h2>
<br />
<%-- this form-login-page form is also used as the form-error-page to ask for a login again. --%>
<c:if test="${not empty param.login_error}">
	<font color="red">Invalid username or password, try again !<BR>
	</font>
</c:if>
Username : alice
<br />
Password : 123
<form action="<c:url value='j_acegi_security_check'/>" method="POST">
<table>
	<tr>
		<td>Username:</td>
		<td><input type='text' name='j_username'
			<c:if test="${not empty param.login_error}">value='<%= session.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY) %>'</c:if>></td>
	</tr>
	<tr>
		<td>Password:</td>
		<td><input type='password' name='j_password'></td>
	</tr>
	<tr>
		<td><input name="reset" type="reset"></td>
		<td><input name="submit" type="submit"></td>
	</tr>
</table>
</form>
</body>
</html>
