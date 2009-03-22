<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>员工登陆密码修改</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="bg_table02">
<s:form action="personnelManager" theme="simple">
	<s:hidden name="method" value="changeSlefPwd" />
	<s:hidden name="isSelectId" />
	<table >
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	<table width="40%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
        <tr align="center">
		    <td colspan="2" class="bg_table01" height="1"><img src="../images/temp.gif" width="1" height="1"> <iframe name="errorsFrame" frameborder="0"
						framespacing="0" height="0" width="100%" scrolling="no"></iframe></td>
		</tr>
		<tr align="center">
			<td class="bg_table02" align="right">用户名:</td>
			<td class="bg_table02" align="left">
				<s:property value="per.username"/>
				
			</td>
		</tr>
		<tr align="center">
			<td class="bg_table02" align="right">原始密码:</td>
			<td class="bg_table02" align="left">
				<s:password name="firstPassword" />
			</td>
		</tr>
		<tr align="center">
			<td  class="bg_table02" align="right">新密码:</td>
			<td class="bg_table02" align="left"><s:password name="password" /></td> <input type="hidden" name="empId" value="<s:property value="per.id"/>" />
		</tr>
		<tr align="center">
			<td  class="bg_table02" align="right">确认密码:</td>
			<td  class="bg_table02" align="left"><s:password name="password1" /></td>
		</tr>
		<tr>
			<td class="bg_table03" colspan="2" align="center">
			<input type="button" name="button2" value="确  认" onClick="onSubmit();" class="button01"/>			
			<input type="button" name="close" value="返  回" class="button01" onClick="javascript:history.go(-1);" /></td>
		</tr>
	</table>
<script language="javascript">
  function back()
  {
     alert("aaaa");
     location.href="../personnelManager/selectPerQuery.action?method=doDefault";
  }
		function onSubmit(){
		    if(!validate()){       	
			   window.document.personnelManager.submit();
			   // alert("修改密码成功!此页面即将关闭...");	
			}	 
		}
			
		function validate(){
		    var ev2=new Validator();
		    with(personnelManager){
		  	   ev2.test("notblank","原始密码不能为空",window.document.personnelManager.firstPassword.value);
		       ev2.test("notblank","密码不能为空",window.document.personnelManager.password.value);
		       ev2.test("notblank","确认密码不能为空",window.document.personnelManager.password1.value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }else{
		   if(window.document.personnelManager.password.value!=window.document.personnelManager.password1.value){
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
