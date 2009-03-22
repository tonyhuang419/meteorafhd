<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>宝信营销管理系统</title>
<%@ include file="/commons/jsp/meta.jsp"%>
</head>
<body>
<script type="text/javascript">
	if(parent.main_view != null){
		parent.location = "/yx/login.action?randomNum="+Math.random();
	}else if(parent.parent.main_view != null){
		parent.parent.location = "/yx/login.action?randomNum="+Math.random();
	}else if(parent.parent.parent.main_view != null){
		parent.parent.parent.location = "/yx/login.action?randomNum="+Math.random();
	}
	
	if(opener != null){
		opener.location = "/yx/login.action?randomNum="+Math.random();
		window.close();
	}
</script>
<div style="color: red; align: center;"><s:fielderror /></div>
<s:form action="login" theme="simple">
<table align="center" width="918px" height="639px">
	<tr>
		<td background="/yx/commons/images/yxLogin.jpg" style="background-repeat:no-repeat;height:639px;width:918px;">
			<s:hidden name="method" value="login" />
			<table border="0">
			<tr>
					<td colspan="7" height="360">&nbsp;</td>
			</tr>
			<tr>
					<td width="190">&nbsp;</td>
					<td colspan="6">
					<s:if test="#userNotExist == true">
						<font color="red">用户不存在</font>
					</s:if>
					<s:if test="#passwordInvalid == true">
						<font color="red">密码错误</font>
					</s:if>
					</td>
			</tr>
			<tr>
					<td >&nbsp;</td>
					<td width="50">用户名：</td>
					<td><s:textfield name="username" cssClass="input01" size="15" /></td>
					<td width="45">密 码：</td>
					<td><s:password name="password" cssClass="input01" size="15" /></td>
					<td align="right"></td>
					<td><s:submit cssClass="button01" value="登　录" id="login_b" /></td>
			</tr>
			</table>
		</td>
	</tr>
</table>
</s:form>
</body>
<script type="text/javascript">
	document.forms(0).username.focus();
	
function document.onkeyup(){
	if(event.keyCode==13){
		if( document.forms(0).username.value!=""  &&  document.forms(0).password.value!="" ){
			document.getElementById("login_b").click();
		}
	}
}
</script>
</html>