<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form name="userForm" action="user.action" theme="simple">
	<s:hidden name="method" value="saveorUpdate" />
	<input type="hidden" name="user.id" value="<s:property value='user.id'/>" />
	<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
        <tr>
		    <td colspan="2" class="bg_table01" height="1"><img src="../images/temp.gif" width="1" height="1"> <iframe name="errorsFrame" frameborder="0"
						framespacing="0" height="0" width="100%" scrolling="no"></iframe></td>
		</tr>
		<tr>
			<td align="right">用户名:</td>
			<td align="left"><s:property value="user.username" /></td>
		</tr>
		<tr>
			<td align="right">密码:</td>
			<td align="left"><s:password name="password" /></td>
		</tr>
		<tr>
			<td align="right">确认密码:</td>
			<td align="left"><s:password name="password1" /></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="button" name="button2" value="确 认" onClick="onSubmit();" /><input type="button" name="close"
				value="关闭" onClick="javascript:window.close();" /></td>
		</tr>
	</table>
	<script language="javascript">
function onSubmit(){
    if(!validate()){
	   window.document.userForm.submit();
	   window.close();	
	}	 
}

	
function validate(){
    var ev2=new Validator();
    with(userForm){
       ev2.test("notblank","密码不能为空",window.document.userForm.password.value);
       ev2.test("notblank","确认密码不能为空",window.document.userForm.password1.value);
   }  
  if (ev2.size() > 0) {
     ev2.writeErrors(errorsFrame, "errorsFrame");
     return true;
 }else{
   if(window.document.userForm.password.value!=window.document.userForm.password1.value){
     ev2.addError("密码和确认密码不相等!");
     ev2.writeErrors(errorsFrame, "errorsFrame");
     return true;
   }
 }
 return false;
}
	
	</script>
</s:form>
</body>
</html>
