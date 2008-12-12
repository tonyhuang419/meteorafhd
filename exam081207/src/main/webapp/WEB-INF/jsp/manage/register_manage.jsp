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
<s:form  method="post" theme="simple" id="registerForm" action="registerM" >
<s:hidden id="formMethod" name="method" />
<table>
<tr>
<td>工     号 *</td>
<td><s:textfield name="emp.jobNum" id="jobNum" onblur="validateUsername(this);"/>&nbsp;<span id="vJobNum"></span></td>
<tr>
<td>密    码 *</td>
<td><s:password name="emp.password" id="password"></s:password></td>
</tr>
<tr>
<td>确认密码 *</td>
<td><s:password name="againPawword" id="againPawword"></s:password></td>
</tr>
<tr>
<td colspan="2" align="center">
<input type="button" value="注册" onclick="doRegister();" id="regButton"/>
<input type="button" value="返回" onclick="goBack();"/>
</td>
</tr>
</s:form>
</div>
</body>

<script type="text/javascript">
function goBack(){
	location.href="../manage/loginM.action?method=login";
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
		$("formMethod").value = "registerEmployee";
		formX.submit();
	}
}

function validate(){
	var ev = new Validator();
	with(registerForm){
      	ev.test("notblank","工号不能为空",$("jobNum").value);
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
	var jsonRequest = new Request.JSON({async:false,url:'/exam081207/jsonData.action?method=uniqueEmployee&jobNum='+obj.value, onComplete: function(jsonObj){
		if(jsonObj){
			$("vJobNum").innerHTML="验证通过"
			$("regButton").removeAttribute('disabled');
		}else{
			$("vJobNum").innerHTML="该工号已存在"
			$("regButton").disabled = "disabled";
		}
	}}).get({randerNum:Math.random()});	
}

</script>
</html>
