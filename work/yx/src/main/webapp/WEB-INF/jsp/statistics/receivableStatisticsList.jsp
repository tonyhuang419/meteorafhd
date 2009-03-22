<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<title>应收款统计</title>
<script type="text/javascript">
	function showQueryList(){
		openWin("../statistics/receivableStatisticsQuery.action?method=showQueryList",700,500);
	}
	function showSubmit(){
		document.receivableStatisticsQuery.submit();
	}	
	function exportInfo(){
		var formX = document.getElementById("export");
		formX.submit();
	}
	function checkShould(){
		var url = "/yx/system/hisdata/importDueFromCompare.action";
		window.open(url);
	}
</script>
<body>
<s:form action="receivableStatisticsQuery" theme="simple">
<s:hidden name="method" value="queryResult"></s:hidden>
<div align="left" style="color:#000000">当前页面:统计查询—>应收款统计</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
			<tr>
				<td colspan="8" align="right" class="bg_table01" height="3">
					
				</td>	
			</tr>
		<tr>
			<td align="center" class="bg_table03">
				<input type="button" value = "检查应收" class="button01" onclick="checkShould();">
				<input type="button" value=" 查  询 " name="1" onclick="showQueryList()" class="button01" />
				<s:if test="info.result.size() > 0 ">
					<input type="button" value=" 导  出 " onclick="exportInfo();" class="button01" />
				</s:if>
			</td>
		</tr>
	</table>
	<s:if test="info != null">
		<table align="center" id="infoX" border=1 cellpadding=1 cellspacing=1 width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
			<tr align="center" class="bg_table01">
				<td class="bg_table01" nowrap="nowrap">销售员</td>
				<td class="bg_table01" nowrap="nowrap">合同号</td>
				<td class="bg_table01" nowrap="nowrap">项目号</td>
				<td class="bg_table01" nowrap="nowrap">合同名称</td>
				<td class="bg_table01" nowrap="nowrap">客户名称</td>
				<td class="bg_table01" nowrap="nowrap">开票类型</td>
				<td class="bg_table01" nowrap="nowrap">应收票据类型</td>
				<td class="bg_table01" nowrap="nowrap">计划开票金额</td>
				<td class="bg_table01" nowrap="nowrap">实际开票金额</td>
				<td class="bg_table01" nowrap="nowrap">计划收款金额</td>
				<td class="bg_table01" nowrap="nowrap">实际收款金额</td>
				<td class="bg_table01" nowrap="nowrap">应收余额</td>
				<td class="bg_table01" nowrap="nowrap">逻天</td>
				<td class="bg_table01" nowrap="nowrap">逻月</td>
				<td class="bg_table01" nowrap="nowrap">实天</td>
				<td class="bg_table01" nowrap="nowrap">实月</td>
			</tr>
		<s:set name="totalRealBillAmount" value="0.00"></s:set>
		<s:set name="totalRealReceAmount" value="0.00"></s:set>
		<s:set name="totalRealTaxBillAmount" value="0.00"></s:set>
		<s:set name="totalRealTaxReceAmount" value="0.00"></s:set>
		<s:set name="totalYue" value="0.00"></s:set>
		<s:iterator value="info.result" id="rece">
			<tr class="bg_table02">
				<td><s:property value="#rece[15]"/></td>
				<td><s:property value="#rece[12]"/></td>
				<td><s:property value="#rece[14]"/></td>
				<td width="250"><a href="#" onclick="openCon('<s:property value="#rece[11].longValue()"/>')" ><s:property value="#rece[13]"/></a></td>
				<td><s:property value="#rece[16]"/></td>
				<td><s:property value="#rece[1]"/></td>
				<td><s:property value="getShouldTicketType(#rece[0])"/></td>
				<td align="right">
					<s:if test="#rece[2] != null">
						<s:property value="#rece[2]"/>
					</s:if>
					<s:else>
						0.00
					</s:else>
				</td>
				<td align="right">
					<s:if test="#rece[3] != null">
						<s:property value="#rece[3]"/>
					</s:if>
					<s:else>
						0.00
					</s:else>
				</td>
				<td align="right"><s:property value="#rece[4]"/></td>
				<td align="right">
					<s:if test="#rece[5] != null">
						<s:property value="#rece[5]"/>
					</s:if>
					<s:else>
						0.00
					</s:else>
				</td>
				<td align="right">
				<s:if test="#rece[6] != null">
					<s:property value="#rece[6]"/>
				</s:if>
				<s:else>
					0.00
				</s:else>
				</td>
				<td>
					<s:if test="#rece[7].longValue() != null">
						<s:property value="#rece[7].longValue()"/>
					</s:if>
				</td>
				<td>
					<s:if test="#rece[8].longValue() != null">
						<s:property value="#rece[8].longValue()"/>
					</s:if>
				</td>
				<td>
				<s:if test="#rece[9].longValue() != null">
					<s:property value="#rece[9].longValue()"/>
				</s:if>
				</td>
				<td>
				<s:if test="#rece[10].longValue() != null">
					<s:property value="#rece[10].longValue()"/>
				</s:if>
				</td>
			</tr>
			<s:if test="#rece[3] != null">
				<s:set name="totalRealBillAmount" value="#totalRealBillAmount + #rece[3]"></s:set>
			</s:if>
			<s:else>
				<s:set name="totalRealBillAmount" value="#totalRealBillAmount+0.00"></s:set>
			</s:else>
			<s:if test="#rece[5] != null">
				<s:set name="totalRealReceAmount" value="#totalRealReceAmount + #rece[5]"></s:set>
			</s:if>
			<s:else>
				<s:set name="totalRealReceAmount" value="#totalRealReceAmount+0.00"></s:set>
			</s:else>
			<s:set name="totalRealTaxBillAmount" value="#totalRealTaxBillAmount + #rece[2]"></s:set>
			<s:set name="totalRealTaxReceAmount" value="#totalRealTaxReceAmount + #rece[4]"></s:set>
			<s:if test="#rece[6] != null">
				<s:set name="totalYue" value="#totalYue + (#rece[6])"></s:set>
			</s:if>
		</s:iterator>
			<tr class="bg_table02">
				<td colspan="6"></td>
				<td align="right">	
					<font style="font-weight:bold">小计:</font>
				</td>
				<td align="right"><s:property value="#totalRealTaxBillAmount" /></td>
				<td align="right"><s:property value="#totalRealBillAmount" /></td>
				<td align="right"><s:property value="#totalRealTaxReceAmount" /></td>
				<td align="right">
					<s:if test="sumRealReceAmount != null">
						<s:property value="#totalRealReceAmount" />
					</s:if>
					<s:else>
						0.00
					</s:else>
				
				</td>
				<td align="right"><s:property value="#totalYue" /></td>
				<td colspan="4"></td>
			</tr>
			<tr class="bg_table02">
				<td colspan="6"></td>
				<td align="right">	
					<font style="font-weight:bold">累计:</font>
				</td>
				<td align="right"><s:property value="sumRealTaxBillAmount" /></td>
				<td align="right"><s:property value="sumRealBillAmount" /></td>
				<td align="right"><s:property value="sumRealTaxReceAmount" /></td>
				<td align="right">
					<s:if test="sumRealReceAmount != null">
						<s:property value="sumRealReceAmount" />
					</s:if>
					<s:else>
						0.00
					</s:else>
				</td>
				<td align="right"><s:property value="sumShouldReceAmount - sumRealArriveAmount" /></td>
				<td colspan="4"></td>
			</tr>
			<tr>
				<td colspan="16">
					<table cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04"><baozi:pages value="info"
							beanName="info" formName="forms(0)" /></td>
						</tr>
					</table>
				</td>
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