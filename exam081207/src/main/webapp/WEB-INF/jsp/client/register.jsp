<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html>
<head>
<title>Login</title>
</head>
<body>
<br/><br/><br/><br/><br/><br/><br/><br/>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<s:iterator id="vm" value="validateMap" > 
	<s:property value="value"/><br/>
</s:iterator>
<s:form  method="post" theme="simple" id="registerForm" action="register" >
<s:hidden id="formMethod" name="method" />
<table align="center">
<tr  align="left">
<td><font color="red">* </font>用户名：</td>
<td><s:textfield name="customer.username" id="username"></s:textfield></td></tr>
<tr  align="left">
<td><font color="red">* </font>密  码：</td>
<td><s:password name="customer.password" id="password"></s:password></td></tr>
<tr  align="left">
<td><font color="red">* </font>确认密码：</td>
<td><s:password name="againPawword" id="againPawword"></s:password></td></tr>

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
	if(!validate()){
		var formX = $("registerForm");
		$("formMethod").value = "registerCustomer";
		formX.submit();
	}
}

function validate(){
	var ev = new Validator();
	with(registerForm){
      	ev.test("notblank","用户名不能为空",$("username").value);
      	ev.test("notblank","密码不能为空",$("password").value);
      	ev.test("equals","两次输入密码不同",$("password").value,$("againPawword").value );
	 }  
	 if (ev.size() > 0) {
	     ev.writeErrors(errorsFrame, "errorsFrame");
	     return true;
	 }
	 return false;
}

</script>
</html>
