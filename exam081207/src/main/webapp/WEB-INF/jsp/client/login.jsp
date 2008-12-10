<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html>
<head>
<title>Login</title>
</head>
<body>
<br/><br/><br/><br/><br/><br/><br/><br/>
<s:form  method="post" theme="simple" id="loginForm" >
<table align="center">
<tr  align="center">
<td>用户名：</td>
<td><s:textfield name="username" id="username"></s:textfield></td></tr>
<tr  align="center">
<td>密码：</td>
<td><s:password name="password" id="password"></s:password></td></tr>
<tr align="center" >
	<td colspan="2">
	<input type="button" value="登陆" onclick="doLogin();"/>
	<input type="button" value="注册"/>
	</td>
</tr>
</table>
</s:form>
</body>
<script type="text/javascript">
function doLogin(){
	alert($("username").value.length);
}
</script>
</html>
