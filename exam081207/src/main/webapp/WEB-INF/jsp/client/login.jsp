<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html>
<head>
<title>Login</title>
</head>
<body>
<br/><br/><br/><br/><br/><br/><br/><br/>
<s:form  method="post" theme="simple" id="loginForm" action="login" >
<s:hidden id="formMethod" name="method" />
<table align="center">
<tr align="center">
	<td colspan="2">
	<s:iterator id="erMessage" value="rs.errorMessages">
	<font color="red"><strong><s:property value="#erMessage"/></strong></font><br/>
	</s:iterator>
	</td>
</tr>
<tr  align="center">
<td>用户名：</td>
<td><s:textfield name="username" id="username"></s:textfield></td></tr>
<tr  align="center">
<td>密码：</td>
<td><s:password name="password" id="password"></s:password></td></tr>
<tr align="center" >
	<td colspan="2">
	<input type="button" value="登陆" onclick="doLogin();"/>
	<input type="button" value="注册" onclick="doRegister();"/>
	</td>
</tr>
</table>
</s:form>
</body>
<script type="text/javascript">
function doLogin(){
	var formX = $("loginForm");
	$("formMethod").value = "validateUser";
	formX.submit();
}
function doRegister(){
	var formX = $("loginForm");
	$("formMethod").value = "register";
	formX.submit();
}
</script>
</html>
