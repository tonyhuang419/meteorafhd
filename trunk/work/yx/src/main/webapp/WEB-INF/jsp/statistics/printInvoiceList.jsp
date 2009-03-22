<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>开票计划查询条件</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<%@ include file="/commons/jsp/scriptx.jsp" %>
<style   media=print>       
  .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->       
</style> 
<title>打印发票清单</title>
</head>
<body>
<s:form action="realBillStat" theme="simple">
	<table align="center"  cellpadding="0" cellspacing="0" border="1"
			width="100%" id="mouthlyBill" bordercolor="#000000"
			style="border-collapse: collapse;">
			<tr class="Noprint">
		<td colspan="11" align="center"><input type="button" value="打印" onclick="Print({horizontal:true})"/><input type="button" value="关闭" onclick="window.close()"/></td>
	</tr>
	<tr height="30">
		<td nowrap="nowrap" align="center" width="65">销售员</td>
		<td align="center" width="75">合同号</td>
		<td align="center" width="75">项目号</td>
		<td align="center" width="75">客户名称</td>
		<td align="center" width="75">申请日期</td>
		<td align="center" width="75">开票日期</td>
		<td align="center" width="75">发票号</td>
		<td align="center" width="75">开票金额</td>
		<td align="center" width="75" nowrap="nowrap">发票类型</td>
		<td nowrap="nowrap" align="center" width="75">签收人</td>
		<td nowrap="nowrap" align="center" width="75">签收时间</td>
	</tr>
	<s:iterator value="printInvoiceList" id="invoice" status="invoiceIndex">
	<tr height="30">
		<td nowrap="nowrap" width="65"><s:property value="#invoice[1]"/></td>
		<td width="75"><s:property value="#invoice[2]"/></td>
		<td width="75"><s:property value="showItemNo(#invoice[8])"/></td>
		<td><s:property value="#invoice[9]"/></td>
		<td align="center"><s:property value="#invoice[10]"/></td>
		<td align="center"><s:property value="#invoice[7]"/></td>
		<td ><s:property value="#invoice[5]"/></td>
		<td align="right"><s:property value="#invoice[6]"/></td>
		<td align="center" nowrap="nowrap"><s:property value="#invoice[11]"/></td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	</s:iterator>
	</table>

</s:form>
</body>
</html>