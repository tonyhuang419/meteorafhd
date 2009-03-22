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

<title>分配角色</title>

</head>

<body>


	
	<table width="96%" border="0" cellspacing="0" cellpadding="0"
		align="center">
		<tr>
			<td width="30%">当前页面：权限管理->分配角色</td>
			<td width="4%">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
	</table>
	
	<s:form action="userAuthority" method="post" theme="simple">
		<s:hidden name="method" value="saveRole" />
		<s:hidden name="userId" />
		
		<table align="center" width="100%" class="bg_table">
			<tr>
				<td width="13" height="0.5" colspan="4" align="right" class="bg_table01">
					<img src="../blue/images/temp.gif" width="1" height="1" />
				</td>
			</tr>
			<tr align="center" class="bg_table01">
				<td width="15%" align="center">选择</td>
				<td width="23%">角色代码</td>
				<td align="center">角色名称</td>
				<td align="center">角色描述</td>
			</tr>
		
			<s:iterator id="result" value="roleList">
				<tr onMouseOver="this.bgColor='#BBBBFF';" onMouseOut="this.bgColor='#FFFFFF';">
					<td align="center">
						<input type="checkbox" name="checkedrole" value='<s:property value="#result[0].id" />' 
							<s:if test="#result[1]!=0">checked="true"</s:if> />
					</td>
					<td align="center">
						<s:property value="#result[0].code" />
					</td>
					<td align="center">
						<s:property value="#result[0].roleName" />
					</td>
					<td align="center">
						<s:property value="#result[0].roleDesc" />
					</td>
				</tr>
			</s:iterator>
			
			<tr class="bg_table03" align="center">
				<td colspan="4">
					<input type="button" class="button01" value='确定' onclick="doSubmit();" />
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>


<script language="javascript">
function openUrl(url)
{
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}

function doSubmit()
{
	document.forms[0].submit();
}
</script>















