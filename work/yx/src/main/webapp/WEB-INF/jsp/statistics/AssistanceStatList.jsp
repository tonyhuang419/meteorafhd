<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>外协合同统计</title>
</head>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
function popQuery(){
	window.open('../statistics/assistanceStat.action?method=showQueryList');
}
function openPage(customId){
	 window.open("../statistics/arriveStat.action?method=queryList&customId="+customId+" ");
}
function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
}
function showDetail(realIdVal)
{ 
	openWin2('/yx/assistance/assistanceMLeftQuery.action?method=showDetail&detailComeFrom=stat&assistanceId='+realIdVal,700,500,"outsourceDetail");
}
</script>
<body>
<s:form action="assistanceStat"  theme="simple">
  <s:hidden name="method" value="queryResult" ></s:hidden>
	<div align="left" style="color: #000000">
	<p>当前页面：统计查询 -> 外协合同统计</p>
	</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%"
		class="bg_table03">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr>
			<td align="center">
			<input type="button" onclick="popQuery()" value="查  询" class="button01" />
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
			<tr align="center" class="bg_table01">
				<td nowrap="nowrap">销售员</td>
				<td nowrap="nowrap">外协合同号</td>
				<td nowrap="nowrap">外协合同名称</td>
				<td nowrap="nowrap">主项目号</td>
				<td nowrap="nowrap">工程部门</td>
				<td nowrap="nowrap">外协供应商</td>
				<td nowrap="nowrap">签订日期</td>
				<td nowrap="nowrap">预计结束日期</td>
				<td nowrap="nowrap">外协合同金额</td>
				<td nowrap="nowrap">已支付金额</td>
				<td nowrap="nowrap">余额</td>
			</tr>
			<s:set name="calContractMoney" value="0.00"></s:set>
			<s:set name="calPayAmount" value="0.00"></s:set>
			<s:set name="calBalance" value="0.00"></s:set>
			<s:iterator value="info.result" id="assis">
				<tr class="bg_table02">
					<td nowrap="nowrap"><s:property value="#assis[2]" /></td>
					<td><s:property value="#assis[0].assistanceId" /></td>
					<td><a href="#" onclick="showDetail('<s:property value="#assis[0].id"/>')"><s:property value="#assis[0].assistanceName" /></a></td>
					<td><s:property value="#assis[3].conItemId" /></td>
					<td><s:property value="typeManageService.getYXTypeManage(1018L,#assis[3].itemResDept).typeName" /></td>
					<td><s:property value="#assis[1]" /></td>
					<td align="center"><s:property value="#assis[0].contractDate" /></td>
					<td align="center"><s:property value="#assis[0].endDate" /></td>
					<td align="right"><s:property value="#assis[0].contractMoney" /></td>
					<td align="right">
					<s:if test="#assis[0].hasPayAmount == null">
						0.00
					</s:if>
					<s:else>
						<s:property value="#assis[0].hasPayAmount" />
					</s:else>
					</td>
					<td align="right"><s:property value="#assis[0].contractMoney - #assis[0].hasPayAmount" /></td>
				</tr>
					<s:set name="calContractMoney" value="#calContractMoney + #assis[0].contractMoney"></s:set>
					<s:if test="#assis[0].hasPayAmount == null">
					<s:set name="calPayAmount" value="#calPayAmount + 0.00"></s:set>
					</s:if>
					<s:else>
						<s:set name="calPayAmount" value="#calPayAmount + #assis[0].hasPayAmount"></s:set>
					</s:else>
					<s:set name="calBalance" value="#calBalance +( #assis[0].contractMoney - #assis[0].hasPayAmount )"></s:set>
			</s:iterator>
			<tr class="bg_table02">
				<td colspan="7"></td>
				<td align="right"><font style="font-weight:bold">小计:</font></td>
				<td align="right"><s:property value="#calContractMoney"/></td>
				<td align="right"><s:property value="#calPayAmount"/></td>
				<td align="right"><s:property value="#calBalance"/></td>
			</tr>
			<tr class="bg_table02">
				<td colspan="7"></td>
				<td align="right"><font style="font-weight:bold">总计:</font></td>
				<td align="right"><s:property value="sumContractMoney"/></td>
				<td align="right">
				<s:if test="sumPay != null">
					<s:property value="sumPay"/>
				</s:if>
				<s:else>
					0.00
				</s:else>
				</td>
				<td align="right"><s:property value="sumContractMoney - sumPay "/></td>
			</tr>
			<tr class="bg_table04">		
				<td colspan="12" align="center"><table cellSpacing=1 cellPadding=2 width="100%" border=0>
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
	<s:hidden name="method" value="queryResult"></s:hidden>
	<s:hidden name="exportX" value="1" />
</s:form>

</body>
</html>