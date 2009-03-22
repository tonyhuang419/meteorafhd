<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>关联预付款选项</title>
<script type="text/javascript">
	function goNext()
	{
		//////目前这个地方不用验证是否选中
		document.assistance.submit();
	}
</script>
</head>
<body>
<s:form theme="simple" action="assistance">
<s:hidden name="method" value="enterNextApply"></s:hidden>
<s:hidden name="ids"></s:hidden>
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr> 
		<td align="left" colspan="6">当前页面:外协管理->外协合同确认</td>
	</tr>
	<tr class="bg_table01">
           <td align="right"  colspan="6"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
    </tr>
	<tr class="bg_table02">
		<td>选择</td>
		<td>外协合同名称</td>
		<td>供应商名称</td>
		<td>申请日期</td>
		<td>申请金额</td>
		<td>实际收款日期</td>
	</tr>
	<s:iterator value="prepApplyPayList">
	<tr class="bg_table02">
		<td><input type="checkbox" name="prepayIds" value="<s:property value="id"/>"/></td>
		<td><s:property value="assistanceService.getContactByPayInfoId(id).assistanceName"/></td>
		<td><s:property value="assistanceService.getSupplierBySupplyId(supplyId).supplierName"/></td>
		<td><s:property value="applyDate"/></td>
		<td><s:property value="payNum"/></td>
		<td><s:property value="realPayTime"/></td>
	</tr>
	</s:iterator>
	<tr class="bg_table01">
	<tD colspan="6" align="center">
		<input type="button" value="确定" onclick="goNext();" class="button01">
		<input type="button" value="关闭" onclick="window.close();" class="button01">
	</tD>
	</tr>
	
</table>
</s:form>

</body>
</html>