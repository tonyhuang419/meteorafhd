<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>开票计划统计主页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<script type="text/javascript">
function popwin()
{
	window.open('yx/statistics/realBillStat.action?method=popQuery','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');
	//window.open('yx/statistics/realBillStat.action?method=popQuery');
}
function printInvoiceList(){
	window.open('yx/statistics/realBillStat.action?method=printInvoiceList');
}
function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
}
</script>
</head>
<body>
<s:form action="realBillStat" theme="simple">
	<s:hidden name="method" value="showMaininfo"></s:hidden>
	<div align="left" style="color: #000000">
	<p>当前页面：统计查询 -> 实际开票统计</p>
	</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%"
		class="bg_table03">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr>
			<td align="center">
				<input type="button" onclick="popwin();" value="查  询" class="button01" />
				<s:if test="info!=null">
				<input type="button" onclick="printInvoiceList();" value="打印开票清单" class="button01" />
				</s:if>
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
				<td align="center">计划开票日期</td>
				<td align="center">实际开票日期</td>
				<td align="center">实际录入日期</td>
				<td align="center">票据类型</td>
				<td align="center">预计开票金额</td>
				<td align="center">已开票金额</td>
				
			</tr>
			<s:set name="subTotalPreBill" value="0.00"></s:set>
			<s:set name="subTotalRealBill" value="0.00"></s:set>
			<s:iterator value="info.result" id="bill">
				<tr class="bg_table02">
					<td nowrap="nowrap"><s:property value="#bill[0]" /></td>
					<td><s:property value="#bill[1]" /></td>
					<td><s:property value="#bill[2]" /></td>
					<td>
					<s:if test="#bill[9]!= null">
					<a href="#" onclick="openCon('<s:property value="#bill[9].longValue()"/>')" ><s:property value="#bill[3]" /></a>
					</s:if>	
					<s:else>
						<s:property value="#bill[3]" />
					</s:else>
					
						</td>
					<td align="center"><s:property value="#bill[4]" /></td>
					<td align="center"><s:property value="#bill[8]" /></td>
					<td align="center"><s:property value="#bill[10]"/></td>
					<td><s:property value="#bill[5]" /></td>
					<td align="right"><s:property value="#bill[6]" /></td>
					<td align="right"><s:if test="#bill[7]==null">
					0.00
					</s:if> <s:else>
						<s:property value="#bill[7]" />
					</s:else></td>
				</tr>
				<s:set name="subTotalPreBill" value="#subTotalPreBill+#bill[6]"></s:set>
				<s:if test="#bill[7]!=null">
					<s:set name="subTotalRealBill" value="#subTotalRealBill+#bill[7]"></s:set>
				</s:if>
			</s:iterator>
			<tr class="bg_table02">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td align="center"></td>
					<td></td>
					<td></td>
					<td align="right">本次小计：</td>
					<td align="right"><s:property value="#subTotalPreBill" /></td>
					<td align="right"><s:property value="#subTotalRealBill" /></td>
			</tr>
			<tr class="bg_table02">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td align="center"></td>
					<td></td>
					<td></td>
					<td align="right">全部累计：</td>
					<td align="right"><s:property value="totalPreBill" /></td>
					<td align="right"><s:property value="totalRealBill" /></td>
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
	<s:hidden name="method" value="showMaininfo"></s:hidden>
	<s:hidden name="exportX" value="1" />
</s:form>
</body>
</html>