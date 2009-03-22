<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<head>
<title>收款导入</title>
<script type="text/javascript">
function queding()
{
	var hasError=document.forms(0).hasErrors.value;
	if(hasError=='yes'){
		alert("导入的数据有错误，请修正错误以后再确认！");
		return;
	}
	document.forms(0).action ="confirmReveInfo.action?method=confirmReve";
	document.forms(0).submit();
}
</script>
</head>
<body>
<s:form action="importReveInfo" theme="simple">
<s:set name="hes" value="'no'"></s:set>
<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill" bordercolor="#808080"
				style="border-collapse: collapse;">
<tr class="bg_table01">
<td colspan="11" align="center"><input type="button" value="确认" onclick="queding();" class="button01"/> <input type="button" value="关闭" onclick="window.close();" class="button01"/></td>
</tr>
<tr class="bg_table01">
	<td nowrap="nowrap">序号</td>
	<td nowrap="nowrap">Excel行号</td>
	<td nowrap="nowrap">收款日期</td>
	<td nowrap="nowrap">客户名称</td>
	<tD nowrap="nowrap">收款金额</tD>
	<tD nowrap="nowrap">合同号</tD>
	<td nowrap="nowrap">项目号</td>
	<td nowrap="nowrap">销售员</td>
	<td nowrap="nowrap">预收</td>
	<td nowrap="nowrap">到款方式</td>
	<tD nowrap="nowrap">提示信息</tD>
</tr>
<s:iterator value="confirmList" status="indexs">
	<tr >
	<s:if test="errorState==1">
		<s:set name="hes" value="'yes'"></s:set>
	</s:if>
	<tD <s:if test="errorState==1">style="color:red"</s:if>><s:property value="#indexs.index+1"/></tD>
	<tD <s:if test="errorState==1">style="color:red"</s:if>><s:property value="excelRowNo"/></tD>
	<td <s:if test="errorState==1">style="color:red"</s:if> nowrap="nowrap"><s:property value="reveDate"/></td>
	<td <s:if test="errorState==1">style="color:red"</s:if>><s:property value="customerName"/></td>
	<td <s:if test="errorState==1">style="color:red"</s:if>><s:property value="reveAmount"/></td>
	<td <s:if test="errorState==1">style="color:red"</s:if>><s:property value="conNo"/></td>
	<td <s:if test="errorState==1">style="color:red"</s:if>><s:property value="itemNo"/></td>
	<td <s:if test="errorState==1">style="color:red"</s:if> nowrap="nowrap"><s:property value="saleMan"/></td>
	<td <s:if test="errorState==1">style="color:red"</s:if> nowrap="nowrap"><s:property value="isPerArrive"/></td>
	<td <s:if test="errorState==1">style="color:red"</s:if>><s:property value="arriveType"/></td>
	<td <s:if test="errorState==1">style="color:red"</s:if>><s:property escape="false" value="errorMsg"/></td>
	</tr>
</s:iterator>
</table>
<input type="hidden" name="hasErrors" value="<s:property value="#hes"/>"/>
</s:form>
</body>
<script type="text/javascript">
<s:if test = "#hasErrorMsg == false">
	alert("还没有生成该月的月度收款计划，不能收款！");
</s:if>
</script>
</html>