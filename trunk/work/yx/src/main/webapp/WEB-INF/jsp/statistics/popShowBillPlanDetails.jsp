<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css"
	rel="stylesheet" type="text/css">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>开票变更历史详细信息</title>
</head>
<body>
<div align="center" class="txt_title01">
<p>开票计划变更履历</p>
</div>
<table align="center" border=1 cellpadding="1" cellspacing=1
	width="100%" id="family" bordercolor="#808080"
	style="border-collapse: collapse;">
	<tr class="bg_table01">
		<td align="right">编号</td>
		<td align="center">变更前开票时间</td>
		<td align="center">变更后开票时间</td>
		<td align="center">变更类型</td>
		<td align="center">变更前收款时间</td>
		<td align="center">变更后收款时间</td>
		<td align="center">变更操作人</td>
		<td align="center">变更操作时间</td>
		<td align="center">变更理由</td>
	</tr>
	<s:iterator value="historyList" id="history" status="hisIndex">
		<tr class="bg_table02">
			<td><s:property value="#hisIndex.index+1" /></td>
			<td align="center"><s:property value="beforBillDate" /></td>
			<td align="center"><s:property value="afterChangeDate" /></td>
			<td><s:property
				value="yxTypeManageService.getYXTypeManage(1024L,billChangeType).typeName" />
			</td>
			<td align="center"><s:property value="beforReceDate" /></td>
			<td align="center"><s:property value="afterReceDate" /></td>
			<td><s:property value="getOpName(changePeople)" /></td>
			<td align="center"><s:property value="changeTime" /></td>
			<s:if test="changeExp!=null&&changeExp.length()>10">
				<td title="<s:property value="changeExp" />"><s:property
					value="changeExp.substring(0,10)" />……</td>
			</s:if>
			<s:else>
				<td><s:property value="changeExp" /></td>
			</s:else>
		</tr>
	</s:iterator>
	<tr class="bg_table02">
		<td colspan="9" align="center"><input type="button" value="关闭"
			class="button01" onclick="window.close();" /></td>
	</tr>
</table>
</body>
</html>