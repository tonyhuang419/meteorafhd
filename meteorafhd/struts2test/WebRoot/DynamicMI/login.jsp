<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'login.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
 function another(){
 	targetForm = document.forms[0];
 	targetForm.action = "DynamicMI/anotherAction.action"
 	targetForm.submit();
 }
</script>
	</head>
	<body>
		<s:form action="loginAction" method="post">
			<table align="center">
				<tr>
					<td>
						<s:textfield label="%{getText('name')}" name="username" />
					</td>
				</tr>

				<tr>
					<td>
						<s:password label="%{getText('pwd')}" name="password" />
					</td>
				</tr>
				<tr>
					<td>
						<input type=submit value="µÇÂ¼" />
					</td>
					<td>
						<input type=button value="ÖØÖÃ" onClick="another();"/>
					</td>
				</tr>
			</table>
		</s:form>

	</body>
</html>
