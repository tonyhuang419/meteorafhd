<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<title>开票精度统计</title>
<script type="text/javascript">
	function showQueryList(){
		openWin("../statistics/billAccuracyStatisticsQuery.action?method=showQueryList",500,300);
	}
	function showSubmit(){
		document.receivableStatisticsQuery.submit();
	}
	function showDetial(saleManVal){
		openWin("../statistics/billAccuracyStatisticsQuery.action?method=showBillDetial&saleMan="+saleManVal+"&yearSel="+$('yearSel').value+"&monthSel="+$('monthSel').value,800,600);
	}
	function exportInfo(){
		var formX = document.getElementById("export");
		formX.submit();
	}
</script>
<body>
<s:form action="billAccuracyStatisticsQuery" theme="simple">
<s:hidden name="method" value="queryResult"></s:hidden>
<s:hidden name="yearSel"></s:hidden>
<s:hidden name="monthSel"></s:hidden>
<div align="left" style="color:#000000">当前页面:统计查询—>开票精度统计</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
			<tr>
				<td colspan="8" align="right" class="bg_table01" height="3">
					
				</td>	
			</tr>
		<tr>
			<td align="center" class="bg_table03">
				<input type="button" value=" 查  询 " name="1" onclick="showQueryList()" class="button01" />
				<s:if test="info.result.size() > 0 ">
					<input type="button" value=" 导  出 " onclick="exportInfo();" class="button01" />
				</s:if>
			</td>
		</tr>
	</table>
	<s:if test="info != null">
		<table align="center" border=1 cellpadding=1 cellspacing=1 width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
			<tr align="center" class="bg_table01">
				<td class="bg_table01" width="5%">销售员</td>
				<td class="bg_table01" width="5%">计划内开票笔数</td>
				<td class="bg_table01" width="5%">计划内额度</td>
				<td class="bg_table01" width="5%">实际计划内开票笔数</td>
				<td class="bg_table01" width="5%">实际计划内额度</td>
				<td class="bg_table01" width="5%">实际开票笔数</td>
				<td class="bg_table01" width="5%">实际额度</td>
				<td class="bg_table01" width="5%">计划外开票笔数</td>
				<td class="bg_table01" width="5%">计划外额度</td>
				<td class="bg_table01" width="5%">计划内笔数%</td>
				<td class="bg_table01" width="5%">计划内额度%</td>
				<td class="bg_table01" width="7%">计划外笔数/计划内笔数%</td>
				<td class="bg_table01" width="7%">计划外额度/计划内额度%</td>
			</tr>
		<s:set name="countPlanNei" value="0"></s:set>
		<s:set name="sumPlanNei" value="0.00"></s:set>
		
		<s:set name="countShijiPlanNei" value="0"></s:set>
		<s:set name="sumShijiPlanNei" value="0.00"></s:set>

		<s:set name="countShijiPlan" value="0"></s:set>
		<s:set name="sumShijiPlan" value="0.00"></s:set>
		
		<s:set name="countPlanWai" value="0"></s:set>
		<s:set name="sumPlanWai" value="0.00"></s:set>
		
		<s:iterator value="info.result" id="bill">
			<tr onclick="showDetial(<s:property value="#bill[0].id"/>)" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
				<td><s:property value="#bill[0].name"/></td>
				<td align="right"><s:property value="#bill[1]"/></td>
				<td align="right">
					<s:if test="#bill[2] != null">
						<s:property value="#bill[2]"/>
					</s:if>
					<s:else>
						0.00
					</s:else>
				</td>
				<td align="right">
					<s:property value="#bill[5]"/>
				</td>
				<td align="right">
					<s:if test="#bill[6] != null">
						<s:property value="#bill[6]"/>
					</s:if>
					<s:else>
						0.00
					</s:else>
				</td>
				<td align="right">
				<s:if test="#bill[3] != null">
					<s:property value="#bill[3]"/>
				</s:if>
				<s:else>
					0
				</s:else>
				</td>
				<td align="right">
					<s:if test="#bill[4] != null">
						<s:property value="#bill[4]"/>
					</s:if>
					<s:else>
						0.00
					</s:else>
				</td>
				<td align="right">
					<s:property value="#bill[3]-#bill[5]"/>
				</td>
				<td align="right">
					<s:property value="#bill[4]-#bill[6]"/>
				</td>
					<td align="right">
					<s:if test="applyBillService.calPercentage(#bill[5],#bill[1]) != null">
						<s:property value="applyBillService.calPercentage(#bill[5],#bill[1])"/>%
					</s:if>
					<s:else>
						0.00%
					</s:else>
				</td>
				<td align="right">
					<s:if test="applyBillService.calPercentage(#bill[6],#bill[2]) != null">
						<s:property value="applyBillService.calPercentage(#bill[6],#bill[2])"/>%
					</s:if>
					<s:else>
						0.00%
					</s:else>
				</td>
				<td align="right">
					<s:if test="applyBillService.calPercentage((#bill[3]-#bill[5]),#bill[1]) != null">
						<s:property value="applyBillService.calPercentage((#bill[3]-#bill[5]),#bill[1])"/>%
					</s:if>
					<s:else>
						0.00%
					</s:else>
				</td>
				<td align="right">
					<s:if test="applyBillService.calPercentage((#bill[4]-#bill[6]),#bill[2]) != null">
						<s:property value="applyBillService.calPercentage((#bill[4]-#bill[6]),#bill[2])"/>%
					</s:if>
					<s:else>
						0.00%
					</s:else>
				</td>
			</tr>
				<s:if test="#bill[1] != null">		
					<s:set name="countPlanNei" value="#countPlanNei + #bill[1]"></s:set>
				</s:if>
				<s:if test="#bill[2] != null">
					<s:set name="sumPlanNei" value="#sumPlanNei  + #bill[2]"></s:set>
				</s:if>	
				<s:if test="#bill[5] != null">
					<s:set name="countShijiPlanNei" value="#countShijiPlanNei + #bill[5]"></s:set>
				</s:if>	
				<s:if test="#bill[6] != null">
					<s:set name="sumShijiPlanNei" value="#sumShijiPlanNei + #bill[6]"></s:set>
				</s:if>
				<s:if test="#bill[3] != null">
					<s:set name="countShijiPlan" value="#countShijiPlan + #bill[3]"></s:set>
				</s:if>	
				<s:if test="#bill[4] != null">
					<s:set name="sumShijiPlan" value="#sumShijiPlan + #bill[4]"></s:set>
				</s:if>	
				<s:if test="#bill[3] - #bill[5] != null">
					<s:set name="countPlanWai" value="#countPlanWai + (#bill[3] - #bill[5]) "></s:set>
				</s:if>	
				<s:if test="#bill[4] - #bill[6] != null">
					<s:set name="sumPlanWai" value="#sumPlanWai +( #bill[4] - #bill[6])"></s:set>
				</s:if>	
		</s:iterator>
			<tr>
				<td align="right">	
					<font style="font-weight:bold">小计:</font>
				</td>
				<td align="right"><s:property value="#countPlanNei" /></td>
				<td align="right"><s:property value="#sumPlanNei" /></td>
				<td align="right"><s:property value="#countShijiPlanNei" /></td>
				<td align="right"><s:property value="#sumShijiPlanNei" /></td>
				<td align="right"><s:property value="#countShijiPlan" /></td>
				<td align="right"><s:property value="#sumShijiPlan" /></td>
				<td align="right"><s:property value="#countPlanWai" /></td>
				<td align="right"><s:property value="#sumPlanWai" /></td>
				<td colspan="4"></td>
			</tr>
			<tr>
				<td align="right">	
					<font style="font-weight:bold">总计:</font>
				</td>
				<s:iterator value="countPlan" id="aaa">
					<td align="right"><s:property value="#aaa[0]"/></td>
					<td align="right"><s:property value="#aaa[1]"/></td>
				</s:iterator>
				<s:iterator value="totalRealPlan" id="bbb">
					<td align="right"><s:property value="#bbb[0]"/></td>
					<td align="right"><s:property value="#bbb[1]"/></td>
				</s:iterator>
				<s:iterator value="totalReal" id="ccc">
					<td align="right"><s:property value="#ccc[0]"/></td>
					<td align="right"><s:property value="#ccc[1]"/></td>
				</s:iterator>
					<td align="right"><s:property value="#ccc[0] - #bbb[0]"/></td>
					<td align="right"><s:property value="#ccc[1] - #bbb[1]"/></td>
				<td colspan="4"></td>
			</tr>
			
			<tr>
				<td colspan="13">
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