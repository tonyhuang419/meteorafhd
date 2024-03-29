<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>

<html>
<head>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css" />

<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>

<title>角色管理</title>

</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" class="bg_img01">

<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>

<s:form action="role" method="POST" theme="simple">
	<s:hidden name="method" value="saveRole" />
	<s:hidden name="role.id" />
	<table width="100%" border="0" cellspacing="1" cellpadding="3" align="center" class="bg_white">
		<tr>
		<s:if test="role.id != null">
			<td class="a_title01" align="center" height="3" colspan="2">编辑角色信息</td>
		</s:if>
		<s:else>
			<td class="a_title01" align="center" height="3" colspan="2">新增角色信息</td>
		</s:else>
		</tr>
		<tr>
			<td width="50%" align="right" class="bg_table02">
				角色代码<font color=red>*</font>:
			</td>
			<td width="50%" class="bg_table02">
				<s:textfield name="role.code" maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td align="right" class="bg_table02">
				角色名称<font color=red>*</font>:
			</td>
			<td class="bg_table02">
				<s:textfield name="role.roleName" maxlength="20" />
			</td>
		</tr>
		<tr>
			<td align="right" class="bg_table02">
				角色描述:
			</td>
			<td class="bg_table02">
				<s:textarea name="role.roleDesc" />
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<input type="button" value="保存" onclick="doSubmit();" />
			</td>
		</tr>
	</table>
</s:form>

</body>
</html>



<script type="text/javascript">

function doSubmit()
{
	if(!validate())
	{
		document.forms[0].submit();
	}
}

function validate()
{
	var ev = new Validator();
    with(role)
    {
		ev.test("notblank", "角色代码不能为空", $('role.code').value);
	    ev.test("notblank", "角色名称不能为空", $('role.roleName').value);
   }
   
   if (ev.size() > 0) 
   {
	   ev.writeErrors(errorsFrame, "errorsFrame");
	   return true;
	}
	return false;
}

function doCheck()
{
	if(document.RoleMgrForm.roleNo.value == "")
	{
		alert("角色代码必须输入!");
		document.RoleMgrForm.roleNo.focus();
		return false;
	}
	if(document.RoleMgrForm.roleDesc.value == "")
	{
		alert("角色描述必须输入!");
		document.RoleMgrForm.roleDesc.focus();
		return false;
	}	
}

function popInfo(respInfo)
{
	if(respInfo=="修改成功!")
	{
		alert(respInfo);
		opener.location = opener.location;
		window.close();
	}
	else
	{
		alert(respInfo);
	}
}

function openSelUser()
{
	var url="/eps_uup/selectUser.do?userIDObj=roleAdmin&userLoginNoObj=roleAdminName&userNum=10";
	window.open(url,"selOrg","location=no,resizable=yes,toolbar=no,scrollbars=yes,directories=no,menubar=no,width=800,height=600,top=20,left=20");
}

<s:if test="#codeOrNameDuplicate == true">
	alert("代码或角色名称重复");
</s:if>
</script>

