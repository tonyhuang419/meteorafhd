<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src='..//js/function.js'></script>
<script type="text/javascript" src='..//js/page.js'></script>
<script type="text/javascript" src='..//js/jquery.js'></script>
<script type="text/javascript" src='..//js/jquery.tablesorter.js'></script>
<script type="text/javascript" src='..//js/sorttable.js'></script>
<script type="text/javascript" src='..//js/YS_checkboxHelper.js'></script>

</head>

<body>

<s:form action="role" method="post">
	<s:hidden name="method" value="deleteRole" />
	<table width="96%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td width="30%">当前页面：基础管理->客户管理</td>
			<td width="4%">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>

	<table align="center" width="100%" class="bg_table">
		<tr>
			<td width="13" height="0.5" align="right" class="bg_table01">
				<img src="../blue/images/temp.gif" width="1" height="1">
			</td>
		</tr>
		<tr class="bg_table03" align="center">
			<td>
				<input type="button" class="button01" value="新增" onClick="window.open('/yx/system/manage/role.action?method=addRole','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=200,width=400');">
				<input type="button" class="button01" value='修改' onClick="editRole();" />
				<input type="button" class="button01" value='分配权限' onClick="distributeAuthority();" />
				<input type="button" class="button01" value='删除' onClick="deleteRoles();" />
			</td>
		</tr>
	</table>

	<table align="center" width="100%" class="bg_table">
		<tr align="center" class="bg_table01">
			<td width="15%" align="center">选择</td>
			<td width="23%">角色代码</td>
			<td align="center">角色名称</td>
			<td align="center">角色描述</td>
		</tr>
	
		<s:iterator value="info.result">
			<tr class="bg_table02">
				<td align="center">
				<s:if test="code != '01'">
					<input type="checkbox" name="checkboxrole" value="<s:property value="id" />">
				</s:if>
				</td>
				<td align="center">
					<s:property value="code" />
				</td>
				<td align="center">
					<s:property value="roleName" />
				</td>
				<td align="center">
					<s:property value="roleDesc" />
				</td>
			</tr>
		</s:iterator>
			
	</table>
</s:form>




</body>
</html>

<script language="javascript">
function openUrl(url)
{
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}


function editRole()
{
	var id = 0;
	// 拿到选中的checkbox
	var l = document.getElementsByName("checkboxrole");
	
	for(var i=0; i<l.length; i++)
	{
		if(l[i].checked == true)
		{
			id = l[i].value;
			break;
		}
	}
	
	if(id == 0)
	{
		alert("请选择一个角色以编辑！");
	}
	else
	{
		window.open('/yx/system/manage/role.action?method=editRole&role.id='+id,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=200,width=400');
	}
	
}

function deleteRoles()
{
	var id = 0;
	// 拿到选中的checkbox
	var l = document.getElementsByName("checkboxrole");
	
	for(var i=0; i<l.length; i++)
	{
		if(l[i].checked == true)
		{
			id = l[i].value;
			break;
		}
	}
	if(id == 0)
	{
		alert("请选择要删除的角色！");
	}
	else if(confirm("确定删除选中的记录"))
	{
		document.forms[0].submit();
	}
	
}

function distributeAuthority()
{
	var id = 0;
	// 拿到选中的checkbox
	var l = document.getElementsByName("checkboxrole");
	
	for(var i=0; i<l.length; i++)
	{
		if(l[i].checked == true)
		{
			id = l[i].value;
			break;
		}
	}
	
	if(id == 0)
	{
		alert("请选择一个角色以编辑！");
	}
	else
	{
		openUrl('/yx/system/manage/role.action?method=distributeAuthority&role.id='+id);
	}
	
}

</script>








