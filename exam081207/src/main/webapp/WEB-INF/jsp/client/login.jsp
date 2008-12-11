<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html>
<head>
<style type="text/css">
<!--  
.loginForm   {  
  text-align:   center;  
}  
  -->   
</style>
<title>Login</title>
</head>
<body>
<br/><br/><br/><br/><br/><br/><br/><br/>
<s:div id="login" cssClass="loginForm">
<s:form  method="post" id="loginForm" action="login" theme="simple" >
<s:hidden id="formMethod" name="method" />
<s:iterator id="erMessage" value="rs.errorMessages">
<font color="red"><strong><s:property value="#erMessage"/></strong></font><br/>
</s:iterator>
<table align="center">
<tr><td>用户名：</td>
<td>
<s:textfield name="username" id="username" size="20"></s:textfield></td></tr>
<tr><td>密 码：</td>
<td>
<s:password name="password" id="password"  size="20"></s:password></td></tr>
<tr align="center"><td colspan="2">
<input type="button" value="登陆" onclick="doLogin();"/>
<input type="button" value="注册" onclick="doRegister();"/></td>
</table>
</s:form>
</s:div>
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
