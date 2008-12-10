<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html>
<head>
<title>Login</title>
</head>
<body>
<br/><br/><br/><br/><br/><br/><br/><br/>
<s:form  method="post" theme="simple" id="registerForm" action="register" >
<s:hidden id="formMethod" name="method" />
<table align="center">

<tr  align="center">
<td>用户名：</td>
<td><s:textfield name="customer.username" id="username"></s:textfield></td></tr>
<tr  align="center">
<td>密  码：</td>
<td><s:password name="customer.password" id="password"></s:password></td></tr>
<td>确认密码：</td>
<td><s:password name="password" id="password"></s:password></td></tr>

<tr align="center" >
	<td colspan="2">
	<input type="button" value="注册" onclick="doRegister();"/>
	<input type="button" value="返回" onclick=""/>
	</td>
</tr>


</table>
</s:form>
</body>

<script type="text/javascript">
function doRegister(){
	var formX = $("registerForm");
	$("formMethod").value = "registerCustomer";
	formX.submit();
}
</script>
</html>
