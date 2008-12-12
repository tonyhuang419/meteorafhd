<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html>
<head>
<title>Login</title>
</head>
<body>
<br/><br/><br/><br/>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<s:iterator id="vm" value="validateMap" > 
	<s:property value="value"/><br/>
</s:iterator>
<div align="center" >
<s:form  method="post" theme="simple" id="registerForm" action="register" >
<s:hidden id="formMethod" name="method" />
<table>
<tr>
	<td>用户名 * </td>
	<td><s:textfield name="customer.username" id="username" onblur="validateUsername(this);"/>&nbsp;<span id="vCustomer"></span></td>
</tr>
<tr>
	<td>密    码 *</td>
	<td><s:password name="customer.password" id="password"></s:password></td>
<tr>
	<td>确认密码 *</td>
	<td><s:password name="againPassword" id="againPassword"></s:password></td>
<tr>
	<td colspan="2"><a href="#" onclick="displayMore();" >More</a></td></tr>
<tr><td colspan="2">
<div style="display: none" id="moreINFO">
<table>
<tr>
	<td>电子邮件&nbsp;&nbsp;</td>
	<td><s:password name="customer.email" id="email"></s:password></td></tr>
<tr>
	<td>电    话   </td>
	<td><s:password name="customer.phone" id="phone"></s:password></td></tr>
<tr>
	<td>地    址   </td>
	<td><s:password name="customer.street" id="street"></s:password></td></tr>
<tr>
</table>
</div>
</td></tr>
<td colspan="2" align="center">
<input type="button" value="注册" onclick="doRegister();" id="regButton"/>
<input type="button" value="返回" onclick="goBack();"/></td></tr>
</table>
</s:form>
</div>



<script type="text/javascript">
function goBack(){
	location.href="../client/login.action?method=login";
}
function displayMore(){
	var moreinfo = $("moreINFO");
	if(moreinfo.style.display=="block"){
		moreinfo.style.display="none";
	}
	else{
		moreinfo.style.display="block";
	}
}

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
      	ev.test("equals","两次输入密码不同",$("password").value,$("againPassword").value );
	 }  
	 if (ev.size() > 0) {
	     ev.writeErrors(errorsFrame, "errorsFrame");
	     return true;
	 }
	 return false;
}
function validateUsername(obj){
	var jsonRequest = new Request.JSON({async:false,url:'/exam081207/jsonData.action?method=uniqueUser&username='+obj.value, onComplete: function(jsonObj){
		if(parseInt(jsonObj.jsonData)){
			$("vCustomer").innerHTML="验证通过,该用户名可以被注册"
			$("regButton").removeAttribute('disabled');
		}else{
			$("vCustomer").innerHTML="该用户名已存在"
			$("regButton").disabled = "disabled";
		}
	}}).get({randerNum:Math.random()});	
}

</script>

</body>

</html>
