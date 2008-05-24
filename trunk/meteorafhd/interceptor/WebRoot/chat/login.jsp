<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>Login</title>

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
	<s:label name="notMatch" value= "" />	
			<s:form action="chatLogin" method="post">
				<table align="center">
					<caption>
						<h3>
							<s:text name="login" />
						</h3>
					</caption>
					<tr>
						<td>
							<s:textfield key='name' name="username" />
						</td>
					</tr>
					<tr>
						<td>
							<s:password key='pwd' name="password" />
						</td>
					</tr>
					<tr align="center">
						<td>
							<s:submit key='login.in' />
						</td>
						<td>
							<s:reset key="login.reload" />
						</td>
					</tr>
				</table>
			</s:form>
	</body>
</html>
