<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>显示客户开票信息</title>
</head>
<body>
<s:form action="showOneClientInfo" theme="simple">
<table align="center" border=1 cellpadding="1" cellspacing=1
			width="100%" id="mouthlyBill" bordercolor="#808080"
			style="border-collapse: collapse;">
	<tr>
		<td colspan="2" align="center" class="txt_title02">客户开票资料</td>
	</tr>
	<tr class="bg_table02">
		<td width="10%">名&nbsp;&nbsp;称：</td>
		<td><s:property value="yxClientCode.billName" /></td>
	</tr>
	<tr class="bg_table02">
		<td>地&nbsp;&nbsp;址：</td>
		<td><s:property value="yxClientCode.billAdd" /></td>
	</tr>
	<tr class="bg_table02">
		<td>税&nbsp;&nbsp;号：</td>
		<td><s:property value="yxClientCode.taxNumber" /></td>
	</tr>
	<tr class="bg_table02">
		<td>开户行：</td>
		<td><s:property value="yxClientCode.billBank" /></td>
	</tr>
	<tr class="bg_table02">
		<td>帐&nbsp;&nbsp;号：</td>
		<td><s:property value="yxClientCode.account" /></td>
	</tr>	
	<tr class="bg_table02">
		<td>电&nbsp;&nbsp;话：</td>
		<td><s:property value="yxClientCode.billPhone" /></td>
	</tr>	
	<tr class="bg_table02">
		<td colspan="2" align="center"><input type="button" value="关闭" onclick="window.close();" class="button01"/></td>
	</tr>
</table>
</s:form>
</body>
</html>