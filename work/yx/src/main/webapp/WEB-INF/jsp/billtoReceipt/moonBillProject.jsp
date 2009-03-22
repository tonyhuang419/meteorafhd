<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>月度开票计划</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	
	// 点击月份触发onchange事件
	function monthChange(){
		var yearSel = document.getElementById("yearSel").value;
		var monthSel = document.getElementById("monthSel").value;
	 	location.href="../billtoReceipt/showMoonBillQuery.action?method=selMonth&monthSel="+monthSel+"&yearSel="+yearSel;
	}
		
	function monthApply(){

		document.showMoonBillQuery.action="/yx/contract/formalContractManage/formalContractInvoiceApply.action";	
		document.showMoonBillQuery.submit();
	}	
</script>
<body >
<s:form action="showMoonBillQuery" theme="simple">
	<s:hidden name="method" value="selMonth"></s:hidden>
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<div align="left">
	<p>当前页面：开票管理 -> 月度开票计划</p>
	
	</div>
			</td>
		</tr>
		<tr>
			<td>
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill" bordercolor="#808080" style=" border-collapse: collapse;">
				<s:if test = "info.result.size()>0">
				<tr align="center" class="bg_table01">
					<td colspan="12">
						<input type="button" value="导出" onclick="downLoadExce();" class="button01"/>
					</td>
				</tr>
				</s:if>
				<tr align="center" class="bg_table01">
					<td nowrap>合同号</td>
					<td nowrap>项目号</td>
					<td nowrap>合同名称</td>
					<td nowrap>客户名称</td>
					<td nowrap>负责部门</td>
					<td nowrap>开票性质</td>
					<td nowrap>发票类型</td>
					<td nowrap>计划开票日期</td>
					<td nowrap>计划开票金额</td>
					<td nowrap>已开票金额</td>
					<td nowrap>计划状态</td>
					<td nowrap>销售员</td>
				</tr>
				<s:iterator value="info.result" id="month">
				<input type="hidden" name="rclist" value="<s:property value="#month[1].realConBillproSid"/>" />
				<input type="hidden" name="contractmainsid" value="<s:property value="#month[3].conMainInfoSid"/>" />
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
						<td align="left" ><s:property value="#month[3].conId" /></td>
						<td><s:property value="#month[5]" /> </td>
						<td><s:property value="#month[3].conName" /> </td>
						<td align="left" ><s:property value="#month[2].name" /></td>
						<td align="left" >
							<s:if test="#month[7]!=null">
								<s:property value="typeManageService.getYXTypeManage(1018,#month[7]).typeName" />
							</s:if>
							<s:else>
								<s:property value="typeManageService.getYXTypeManage(1018,#month[3].mainItemDept).typeName" />
							</s:else>
						</td>
						<td align="left" >
							<s:property value="typeManageService.getYXTypeManage(1012,#month[1].billNature).typeName" /></td>
						<td align="left" >
							<s:property value="typeManageService.getYXTypeManage(1004,#month[1].billType).typeName" /></td>
						<td align="center" >
							<s:property value="#month[1].realPredBillDate" /></td>
						<td align="right" >
							<s:property value="#month[0].billTaxAmount" /></td>
						<td align="right" >
						<s:if test="#month[4] == null">
							0.00
						</s:if>
						<s:else>
							<s:property value="#month[4]" />
						</s:else>
						</td>							
						<td align="center" >
						<s:if test="#month[4] > 0 && #month[4]!=null">
							已开票
						</s:if>
						<s:else>
							未开票
						</s:else>							
						</td>
						<td><s:property value="#month[6]" /></td>
					</tr>
				</s:iterator>
				<tr>
					<td align="right" colspan="8">总计：</td>
					<td align="right" >
						<s:if test="sumPlanInvoice2 == null">
							0.00
						</s:if>
						<s:else>
							<s:property value="sumPlanInvoice2" />
						</s:else>
					</td>
					<td align="right" >
						<s:if test="sumHasInvoice2 == null">
							0.00
						</s:if>
						<s:else>
							<s:property value="sumHasInvoice2" />
						</s:else>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>

			</td>
		</tr>
	</table>
</s:form>
<script type="text/javascript">
function downLoadExce(){
	document.exportX.submit();
}
</script>
<s:form action="showMoonBillQuery" theme="simple" name="exportX">
	<s:hidden name="method" value="doDefault"></s:hidden>
	<s:hidden name="exportX" value="1"></s:hidden>
</s:form>
</body>
</html>
