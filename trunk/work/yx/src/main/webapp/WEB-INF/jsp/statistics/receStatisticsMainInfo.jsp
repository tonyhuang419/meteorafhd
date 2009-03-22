<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>收款计划统计主页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<script type="text/javascript">
function popwin()
{
	window.open('yx/statistics/receStat.action?method=popQuery','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=400,width=620');
	//window.open('yx/statistics/billStat.action?method=popQuery');
}
function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
}
</script>
</head>
<body>
<s:form action="receStat" theme="simple">
	<s:hidden name="method" value="showMainInfo"></s:hidden>
	<div align="left" style="color: #000000">
	<p>当前页面：统计查询 -> 预计收款统计</p>
	</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%"
		class="bg_table03">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr>
			<td align="center"><input type="button" onclick="popwin();"
				value="查询" class="button01" />
			<s:if test="info.result.size() > 0 ">
				<input type="button" value=" 导  出 " onclick="exportInfo();" class="button01" />
			</s:if>	
			</td>
		</tr>
	</table>
	<s:if test="info!=null">
		<table align="center" border=1 cellpadding="1" cellspacing=1
			width="100%" id="mouthlyBill" bordercolor="#808080"
			style="border-collapse: collapse;">
			<tr class="bg_table01">
				<td nowrap="nowrap" align="center">销售员</td>
				<td align="center">合同号</td>
				<td align="center">项目号</td>
				<td align="center">合同名称</td>
				<td align="center">客户名称</td>
				<td align="center">计划收款日期</td>
				<td align="center">计划收款金额</td>
				<td align="center">已到款金额</td>
				<td align="center">收款余额</td>
				<td align="center">已开票金额</td>
			</tr>
			<s:set name="subTotalPreRece" value="0.00"></s:set>
			<s:set name="subAccumulateRealRece" value="0.00"></s:set>
			<s:set name="subAccumulatePreRece" value="0.00"></s:set>
			<s:set name="subTotalRealRece" value="0.00"></s:set>
			<s:set name="subBalance" value="0.00"></s:set>
			<s:set name="subBillAmount" value="0.00"></s:set>
			<s:iterator value="info.result" id="bill">
				<tr class="bg_table02">
					<td nowrap="nowrap"><s:property value="#bill[0]" /></td>
					<td><s:property value="#bill[1]" /></td>
					<td><s:property value="#bill[2]" /></td>
					<td><a href="#" onclick="openCon('<s:property value="#bill[11].longValue()"/>')" ><s:property value="#bill[3]" /></a></td>
					<td><s:property value="#bill[10]" /></td>
					<td align="center"><s:property value="#bill[4]" /></td>
					<td align="right"><s:property value="#bill[8]" /></td>
					<td align="right"><s:property value="#bill[9]" /></td>
					<td align="right"><s:property value="#bill[8] - #bill[9]" /></td>
					<td align="right"><s:property value="#bill[13]" /></td>
				</tr>
				<s:set name="subTotalPreRece" value="#subTotalPreRece+#bill[6]"></s:set>
				<s:if test="#bill[7]!=null">
					<s:set name="subTotalRealRece" value="#subTotalRealRece+#bill[7]"></s:set>
				</s:if>
				<s:set name="subAccumulatePreRece" value="#subAccumulatePreRece+#bill[8]"></s:set>
				<s:set name="subAccumulateRealRece" value="#subAccumulateRealRece+#bill[9]"></s:set>
				<s:set name="subBalance" value="#subBalance + (#bill[8] - #bill[9])"></s:set>
				<s:set name="subBillAmount" value="#subBillAmount + #bill[13]"></s:set>
			</s:iterator>
			<tr class="bg_table02">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td align="center"></td>
					<td align="right">本次小计：</td>
					<td align="right"><s:property value="#subAccumulatePreRece" /></td>
					<td align="right"><s:property value="#subAccumulateRealRece" /></td>
					<td align="right"><s:property value="#subBalance" /></td>
					<td align="right"><s:property value="#subBillAmount" /></td>
			</tr>
			<tr class="bg_table02">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td align="center"></td>
					<td align="right">全部累计：</td>
					<td align="right"><s:property value="accumulatePreRece" /></td>
					<td align="right"><s:property value="accumulateRealRece" /></td>
					<td align="right"><s:property value="accumulatePreRece - accumulateRealRece" /></td>
					<td align="right"><s:property value="billInvoiceAmount" /></td>
					
			</tr>
			<tr class="bg_table04">		
				<td colspan="10" align="center"><table cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04"><baozi:pages value="info"
							beanName="info" formName="forms(0)" /></td>
						</tr>
					</table></td>				
			</tr>
		</table>
	</s:if>
</s:form>
<s:form id="export">
	<s:hidden name="method" value="showMainInfo"></s:hidden>
	<s:hidden name="exportX" value="1" />
</s:form>
</body>
</html>