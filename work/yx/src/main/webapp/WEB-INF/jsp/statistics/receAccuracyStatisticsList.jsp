<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<title>收款精度统计</title>
<script type="text/javascript">
	function showQueryList(){
		openWin("../statistics/recePrecisionStat.action?method=showQueryList",700,500);
	}
	function showSubmit(){
		document.recePrecisionStat.submit();
	}
	function showDetial(saleManVal){
		openWin("../statistics/recePrecisionStat.action?method=showReceDetial&saleMan="+saleManVal+"&yearSel="+$('yearSel').value+"&monthSel="+$('monthSel').value,900,600);
	}
	function exportInfo(){
		var formX = document.getElementById("export");
		formX.submit();
	}
</script>
<body>
<s:form action="recePrecisionStat" theme="simple">
<s:hidden name="method" value="queryResult"></s:hidden>
<s:hidden name="yearSel"></s:hidden>
<s:hidden name="monthSel"></s:hidden>
<div align="left" style="color:#000000">当前页面:统计查询—>收款精度统计</div>
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
				<td class="bg_table01" width="5%">计划内收款笔数</td>
				<td class="bg_table01" width="5%">计划内收款额度</td>
				<td class="bg_table01" width="5%">实际计划内收款笔数</td>
				<td class="bg_table01" width="5%">实际计划内额度</td>
				<td class="bg_table01" width="5%">实际收款笔数</td>
				<td class="bg_table01" width="5%">实际额度</td>
				<td class="bg_table01" width="5%">计划外收款笔数</td>
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
		<s:iterator value="info.result" id="rece">
			<tr onclick="showDetial(<s:property value="#rece[0].id"/>)" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
				<td><s:property value="#rece[0].name"/></td>
				<td align="right">
					<s:property value="#rece[1]"/>
				</td>
				<td align="right">
					<s:if test="#rece[2] != null">
						<s:property value="#rece[2]"/>
					</s:if>
					<s:else>
						0.00
					</s:else>
				</td>
				<td align="right">
					<s:property value="#rece[5]"/>
				</td>
				<td align="right">
					<s:if test="#rece[6] != null"><s:property value="#rece[6]"/></s:if>
					<s:else>0.00</s:else>
				</td>
				<td align="right">
					<s:property value="#rece[3]"/>
				</td>
				<td align="right">
					<s:if test="#rece[4] != null"><s:property value="#rece[4]"/></s:if>
					<s:else>0.00</s:else>
				</td>
				<td align="right">
					<s:property value="#rece[3]-#rece[5]"/>
				</td>
				<td align="right">
					<s:property value="#rece[4]-#rece[6]"/>
				</td>
				<td align="right">
					<s:if test="applyBillService.calPercentage(#rece[5],#rece[1]) != null">
						<s:property value="applyBillService.calPercentage(#rece[5],#rece[1])"/>%
					</s:if>
					<s:else>
						0.00%
					</s:else>
				</td>
				<td align="right">
					<s:if test="applyBillService.calPercentage(#rece[6],#rece[2]) != null">
						<s:property value="applyBillService.calPercentage(#rece[6],#rece[2])"/>%
					</s:if>
				</td>
				<td align="right">
					<s:if test="applyBillService.calPercentage((#rece[3]-#rece[5]),#rece[1]) != null">
						<s:property value="applyBillService.calPercentage((#rece[3]-#rece[5]),#rece[1])"/>%
					</s:if>
					<s:else>
						0.00%
					</s:else>
				</td>
				<td align="right">
					<s:if test="applyBillService.calPercentage((#rece[4]-#rece[6]),#rece[2]) != null">
						<s:property value="applyBillService.calPercentage((#rece[4]-#rece[6]),#rece[2])"/>%
					</s:if>
					<s:else>
						0.00%
					</s:else>
				</td>
			</tr>
			<s:if test="#rece[1] != null">		
					<s:set name="countPlanNei" value="#countPlanNei + #rece[1]"></s:set>
				</s:if>
				<s:if test="#rece[2] != null">
					<s:set name="sumPlanNei" value="#sumPlanNei  + #rece[2]"></s:set>
				</s:if>	
				<s:if test="#rece[5] != null">
					<s:set name="countShijiPlanNei" value="#countShijiPlanNei + #rece[5]"></s:set>
				</s:if>	
				<s:if test="#rece[6] != null">
					<s:set name="sumShijiPlanNei" value="#sumShijiPlanNei + #rece[6]"></s:set>
				</s:if>
				<s:if test="#rece[3] != null">
					<s:set name="countShijiPlan" value="#countShijiPlan + #rece[3]"></s:set>
				</s:if>	
				<s:if test="#rece[4] != null">
					<s:set name="sumShijiPlan" value="#sumShijiPlan + #rece[4]"></s:set>
				</s:if>	
				<s:if test="#rece[3] - #rece[5] != null">
					<s:set name="countPlanWai" value="#countPlanWai + (#rece[3] - #rece[5]) "></s:set>
				</s:if>	
				<s:if test="#rece[4] - #rece[6] != null">
					<s:set name="sumPlanWai" value="#sumPlanWai +( #rece[4] - #rece[6])"></s:set>
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
					<td align="right">
						<s:if test="#aaa[1] != null">
							<s:property value="#aaa[1]"/>
						</s:if>
						<s:else>
							0.00
						</s:else>
					</td>
				</s:iterator>
				<s:iterator value="totalRealPlan" id="bbb">
					<td align="right"><s:property value="#bbb[0]"/></td>
					<td align="right">
						<s:if test="#bbb[1] != null">
							<s:property value="#bbb[1]"/>
						</s:if>
						<s:else>
							0.00
						</s:else>			
					</td>
				</s:iterator>
				<s:iterator value="totalReal" id="ccc">
					<td align="right"><s:property value="#ccc[0]"/></td>
					<td align="right">
						<s:if test="#ccc[1] != null">
							<s:property value="#ccc[1]"/>
						</s:if>
						<s:else>
							0.00
						</s:else>	
				</s:iterator>
					<td align="right"><s:property value="#ccc[0] - #bbb[0]"/></td>
					<td align="right">
						<s:if test="#ccc[1] - #bbb[1] != null">
							<s:property value="#ccc[1] - #bbb[1]"/>
						</s:if>
						<s:else>
							0.00
						</s:else>
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
</body>
<s:form id="export">
	<s:hidden name="method" value="queryResult"></s:hidden>
	<s:hidden name="exportX" value="1" />
</s:form>
</html>