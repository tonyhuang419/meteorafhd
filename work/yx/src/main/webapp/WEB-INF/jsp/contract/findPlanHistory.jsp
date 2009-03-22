<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>项目号修改</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css"
	rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript"
	src="<s:url value="/yx/commons/scripts/CalendarSelector.js"/>"></script>
<script type="text/javascript"
	src="<s:url value="/commons/scripts/time.js"/>"></script>

<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}

.STYLE3 {
	font-size: 18px;
	font-weight: bold;
}
-->
</style>
</head>
<body>
<div>
<div align="center" class="STYLE3">
<p>开票收款计划变更履历</p>
<p>&nbsp;</p>
</div>
</div>
<table align="center" border=1 cellpadding="1" cellspacing=1
	width="100%" id="family" bordercolor="#808080"
	style="border-collapse: collapse;">
	<tr class="bg_table01">
		<td nowrap="nowrap" align="right">编号</td>
		<td nowrap="nowrap" align="center">变更前开票时间</td>
		<td nowrap="nowrap" align="center">变更后开票时间</td>
		<td nowrap="nowrap" align="center">变更类型</td>
		<td nowrap="nowrap" align="center">变更前收款时间</td>
		<td nowrap="nowrap" align="center">变更后收款时间</td>
		<td nowrap="nowrap" align="center">变更操作时间</td>
		<td nowrap="nowrap" align="left">变更理由</td>
	</tr>
	<s:iterator value="bilhistoryList" id="history" status="hisIndex">
		<tr class="bg_table02">
			<td><s:property value="#hisIndex.index+1" /></td>
			<td nowrap="nowrap" align="center"><s:property value="beforBillDate" /></td>
			<td nowrap="nowrap"><s:property value="afterChangeDate" /></td>
			<td nowrap="nowrap"><s:property
				value="typeManageService.getYXTypeManage(1024L,billChangeType).typeName" />
			</td>
			<td nowrap="nowrap" align="center"><s:property value="beforReceDate" /></td>
			<td nowrap="nowrap" align="center"><s:property value="afterReceDate" /></td>
			<td nowrap="nowrap"><s:property value="changeTime" /></td>
			<td title="<s:property value="changeExp" />" nowrap="nowrap"><s:if test="changeExp!=null&&changeExp.length()>20">
				<s:property value="changeExp.substring(0,10)" />……
			</s:if> <s:else>
				<s:property value="changeExp" />
			</s:else></td>
		</tr>
	</s:iterator>
	<tr class="bg_table02">
		<td colspan="8" align="center"><input type="button" value="关闭"
			class="button01" onclick="window.close();" /></td>
	</tr>
</table>
</body>
</html>
