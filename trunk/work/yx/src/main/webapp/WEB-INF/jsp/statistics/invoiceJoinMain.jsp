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
	window.open('yx/statistics/invoiceJoinStat.action?method=popQuery','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');
	//window.open('yx/statistics/billStat.action?method=popQuery');
}
function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
}
</script>
</head>
<body>
<s:form action="invoiceJoinStat" theme="simple">
	<s:hidden name="method" value="queryMain"></s:hidden>
	
	<div align="left" style="color: #000000">
	<p>当前页面：统计查询 -> 发票交接统计</p>
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
				<td nowrap="nowrap" align="center">编号</td>
				<td align="center">项目号</td>
				<td align="center">客户名称</td>
				<td align="center">发票号</td>
				<td align="center">开票日期</td>
				<td align="center">开票金额</td>
				<td align="center">开票类型</td>
			</tr>
			<s:set name="subTotalInvoiceAmount" value="0.00"></s:set>
			<s:iterator value="info.result" id="bill">
				<tr class="bg_table02">
				<td nowrap="nowrap" align="left">
					<s:if test="#bill[1]!=null">
						<s:property value="#bill[1]+'_'+#bill[0]"/>
					</s:if>
					<s:else>
						<s:property value="#bill[0]"/>
					</s:else>
				</td>
				<td align="left"><s:property value="getItemNoByInvoiceId(#bill[2])" escape="false"/></td>
				<td align="left"><s:property value="#bill[7]"/></td>
				<td align="left"><s:property value="#bill[3]"/></td>
				<td align="center"><s:property value="#bill[4]"/></td>
				<td align="right"><s:property value="#bill[5]"/></td>
				<td align="left"><s:property value="#bill[6]"/></td>
				</tr>
			<s:set name="subTotalInvoiceAmount" value="#subTotalInvoiceAmount+#bill[5]"></s:set>
			</s:iterator>
			<tr class="bg_table02">
			
					<td></td>
					<td></td>
						<td></td>
					<td align="center"></td>
					
					<td align="right">本次小计：</td>
					<td align="right"><s:property value="#subTotalInvoiceAmount" /></td>
					<td align="center"></td>
					
			</tr>
			<tr class="bg_table02">
				
					<td></td>
					<td></td>
					<td></td>
					<td align="center"></td>
					<td align="right">全部累计：</td>
					<td align="right"><s:property value="totalInvoiceAmount" /></td>
					<td align="right"></td>
						
			</tr>
			<tr class="bg_table04">		
				<td colspan="7" align="center"><table cellSpacing=1 cellPadding=2 width="100%" border=0>
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
	<s:hidden name="method" value="queryMain"></s:hidden>
	<s:hidden name="exportX" value="1" />
</s:form>
</body>
</html>