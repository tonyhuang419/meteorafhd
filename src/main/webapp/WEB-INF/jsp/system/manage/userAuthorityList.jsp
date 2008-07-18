<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>

<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css" />

<title>...</title>

</head>

<body >

<table width="100%">
	<tr>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
				<tr>
					<td width="30%">当前页面：权限管理->用户权限管理</td>
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
			</table>
			
			<s:form action="userAuthorityQuery" theme="simple">
				<table align="center" width="100%" class="bg_table">
					<tr align="center" class="bg_table04">
						<td colspan="4">
							<input type="button" class="button01" value='操作权限' onClick="distributeAuthority();" />
							<input type="button" class="button01" value='操作角色' onClick="distributeRole();">
						</td>
					</tr>
					<tr align="center" class="bg_table01">
						<td align="center">选择</td>
						<td align="center">工号</td>
						<td align="center">员工名称</td>
						<td align="center">职位名称</td>
					</tr>
				
					<s:iterator id="result" value="info.result">
						<tr>
							<td align="center" nowrap>
								<input type="checkbox" name="checkeduser" value="<s:property value="#result[0].id" />">
							</td>
							<td align="center" nowrap>
								<s:property value="#result[0].workId" />
							</td>
							<td align="center" nowrap>
								<s:property value="#result[0].name" />
							</td>
							<td align="center" nowrap>
								<s:property value="#result[1]" />
							</td>
						</tr>
					</s:iterator>
				</table>
				
				<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
					<tr valign="top">
						<td class="bg_table04">
							<s:if test="info.result!=null">
								<baozi:pages value="info" beanName="info" formName="forms(0)" />
							</s:if>
						</td>
					</tr>
				</TABLE>
			</s:form>
			
		</td>
	</tr>
</table>


</body>
</html>

<script language="javascript">

function openUrl(url)
{
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}

function distributeAuthority()
{
	// 取得选中的checkbox的value
	var id = 0;
	
	var l = document.getElementsByName("checkeduser");
	
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
		openUrl('/yx/system/manage/userAuthority.action?method=editAuthority&userId='+id);
	}
	
}

function distributeRole()
{
	
	// 取得选中的checkbox的value
	var id = 0;
	
	var l = document.getElementsByName("checkeduser");
	
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
		openUrl('/yx/system/manage/userAuthority.action?method=editRole&userId='+id);
	}
	
	
	
}


</script>




