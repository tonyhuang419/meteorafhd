<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>宝资HR信息系统</title>
<%@ include file="/commons/jsp/meta.jsp"%>
</head>
<body style="position: relative;">
<s:form action="user.action" theme="simple">
<table width="96%" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td width="30%">当前页面:权限管理->员工信息管理</td>
		<td width="4%">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
<table align="center" width="96%" class="bg_table">
	<tr class="bg_table03" align="center">
		<td>&nbsp;<input class="button01" type=submit value="新  增" onClick="javascript:document.forms(0).action='<s:url action="user"></s:url>'"></input></td>
	</tr>
</table>
<table align="center" width="96%" class="bg_table">
	<tr align="center" class="bg_table01">
		<td align="left" nowrap>用户名</td>
		<td align="left" nowrap>员工名称</td>
		<td align="left" nowrap>工号</td>
		<td nowrap>&nbsp;</td>
	</tr>
	<s:iterator value="info.result" status="status">
	<tr class="bg_table02">
		<td align="left" nowrap="true"><s:property value="username"/></td>
		<td align="left" nowrap="true"><s:property value="name"/></td>
		<td align="left" nowrap="true"><s:property value="userCode"/></td>
		<td align="center" nowrap><input type="button" class="button01" value='重置密码' onClick="openUrl('<s:url action="user.action"><s:param name="user.id"><s:property value="id"/></s:param><s:param name="method">doChangePWD</s:param></s:url>');"/><input type="submit" class="button01" value='修改'
			onClick="javascript:document.forms(0).action='<s:url action="user"><s:param name="user.id"><s:property value="id"/></s:param></s:url>'" style=" width : 48px; height : 21px;"/> <input type="submit" class="button01" value='删除' onClick="javascript:document.forms(0).action='<s:url action="user.action"><s:param name="user.id"><s:property value="id"/></s:param><s:param name="method">delete</s:param></s:url>'"/></td>
	</tr>
	</s:iterator>    
	<tr class="bg_table02">
		<td colspan="4">
		<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
			<tr valign="top">
				<td class="bg_table04"><baozi:pages value="info" beanName="info" formName="forms(0)" /></td>
			</tr>
		</TABLE>		
		</td>
	</tr>
</table>
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
</script>
</s:form>
</body>
</html>