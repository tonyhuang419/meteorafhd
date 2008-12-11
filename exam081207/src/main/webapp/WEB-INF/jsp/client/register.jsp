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
<div align="center" >
<s:form  method="post" theme="simple" id="registerForm" action="register" >
<s:hidden id="formMethod" name="method" />
<div class="tdx">用户名 * 
<s:textfield name="customer.username" id="username" onblur="validateUsername(this);"/>&nsbp;<span id="vCustomer"></span>
</div>
<div class="tdx">
密  码 *
<s:password name="customer.password" id="password"></s:password>
</div>
<div class="tdx">
确认密码 *
<s:password name="againPawword" id="againPawword"></s:password>
</div>
<div class="tdx">
<a href="#" onclick="displayMore();" >More</a></div>
<div style="display: none" id="moreINFO">
<div class="tdx">
电子邮件
<s:password name="customer.email" id="email"></s:password>
</div>
<div class="tdx">
电       话
<s:password name="customer.phone" id="email"></s:password>
</div>
<div class="tdx">
地      址
<s:password name="customer.street" id="email"></s:password>
</div>
</div>
<input type="button" value="注册" onclick="doRegister();" id="regButton"/>
<input type="button" value="返回" onclick=""/>
</s:form>
</div>
</body>

<script type="text/javascript">
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
      	ev.test("equals","两次输入密码不同",$("password").value,$("againPawword").value );
	 }  
	 if (ev.size() > 0) {
	     ev.writeErrors(errorsFrame, "errorsFrame");
	     return true;
	 }
	 return false;
}
function validateUsername(obj){
	var jsonRequest = new Request.JSON({async:false,url:'/exam081207/jsonData.action?method=uniqueUser&username='+obj.value, onComplete: function(jsonObj){
		if(jsonObj){
			$("vCustomer").innerHTML="验证通过"
			$("regButton").removeAttribute('disabled');
		}else{
			$("vCustomer").innerHTML="用户名已存在"
			$("regButton").disabled = "disabled";
		}
	}}).get({randerNum:Math.random()});	
}

</script>
</html>
