<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>

<link href="./css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function yearMonthChange(){
   document.forms(0).submit();
}
function generateMonthlyPlan(){
  if(confirm("确定要生成本月月度计划吗？")){
   	document.forms(0).action="/yx/harvestMeansManager/moonHarvestProgram.action";
   	document.forms(0).method.value="generateMonthPlan";
   	document.forms(0).submit();
   	document.getElementById("genMonthReceBtn").disabled = true;
  }
}
function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
}
</script>

</head>
<body  leftmargin="0">
<s:form action="moonHarvestProgram" theme="simple">
<s:hidden name="method" value="queryList" ></s:hidden>
			<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
				  <tr><td height="3" align="left" >当前页面：收款管理->月度计划收款管理</td></tr>	
			<tr>
 			   <td colspan="2" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
 			 </tr>
			<tr class="bg_table03">		
				<td align="center" class="bg_table03">&nbsp;
				 <s:if test="userService.hasAuthority('moonHarvestProgram.action','generateMonthPlan','3')">
				
				<input type="button" onclick="generateMonthlyPlan()"<s:if test="showMonthlyButton">disabled ="true" </s:if> name="button" id="genMonthReceBtn" value="生成本月收款计划" class="button02">
				
				</s:if>
				<s:if test="info.result.size() > 0 ">
				<input type="button" value=" 导  出 " onclick="exportInfo();" class="button01" />
				</s:if>
			</td>	
       	 	 </td>
			</table>
			
			<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" bordercolor="#808080" style="border-collapse: collapse;">
				<tr align="center" class="bg_table01">	
					<td nowrap>合同号</td>
					<td nowrap>项目号</td>
					<td nowrap>销售员</td>
					<td nowrap>合同名称</td>
					<td nowrap>客户名称</td>
					<td nowrap>负责部门</td>
					<td nowrap>已开票金额</td>
					<td nowrap>开票日期</td>
					<td nowrap>计划收款日期</td>
					<td nowrap>计划金额</td>
					<td nowrap>到款金额</td>
					<td nowrap>收款状态</td>
					<td nowrap>实际到款日期</td>
				</tr>

				<s:iterator value="info.result" id="monthlyPlan">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF';" onMouseOut="this.bgColor='#FFFFFF';">		
					    <td align="left"><s:property value="#monthlyPlan[0].conId" /></td>
					    <td align="left"><s:property value="#monthlyPlan[7]" /></td>
					    <td align="left"><s:property value="#monthlyPlan[6]" /></td>
					    <td align="left"><s:property value="#monthlyPlan[0].conName" /></td>
						<td align="left"><s:property value="#monthlyPlan[1]"/></td> 
						<td align="left"><s:property value="monthHarvestPlanService.getDepName(#monthlyPlan[2])" /></td>
						<td align="right"><s:property value="#monthlyPlan[11]"/></td> 
						<td><s:date name="#monthlyPlan[12]" format="yyyy-MM-dd" /></td>
						<td><s:date name="#monthlyPlan[3]" format="yyyy-MM-dd" /></td>
					    <td align="right"><s:property value="#monthlyPlan[4]" /></td>

					    <td align="right" >
					    <s:if test="#monthlyPlan[5]!=null" ><s:property value="#monthlyPlan[5]" /></s:if>
					    <s:else>0.00</s:else>
					    </td>
					    
						<td align="center">
						<s:if test="#monthlyPlan[5]==null||#monthlyPlan[5]==0">
							未收款
						</s:if>
						<s:elseif test="#monthlyPlan[5]>0 &&#monthlyPlan[5]< #monthlyPlan[9].receTaxAmount">
						部分收款
						</s:elseif>
						<s:elseif test="#monthlyPlan[5]==#monthlyPlan[9].receTaxAmount">
							全部收款
						</s:elseif>
						</td> 
						<td align="center">
						<s:date name="#monthlyPlan[9].actualArrivedDate" format="yyyy-MM-dd"/>
						</td>
					</tr>
				</s:iterator>
				<tr>
					<td align="right" colspan="6">总计：</td>
					  <td align="right" >
					    <s:if test="sumBillAmount!=null" ><s:property value="sumBillAmount" /></s:if>
					    <s:else>0.00</s:else>
					    </td>
					 <td>&nbsp;</td>
					 <td>&nbsp;</td>
					
					  <td align="right" >
					    <s:if test="sumMonthRevePro!=null" ><s:property value="sumMonthRevePro" /></s:if>
					    <s:else>0.00</s:else>
					    </td>
					  <td align="right" >
					    <s:if test="sumArrivalAmount!=null" ><s:property value="sumArrivalAmount" /></s:if>
					    <s:else>0.00</s:else>
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
			
</s:form>

<s:form id="export">
	<s:hidden name="method" value="queryList"></s:hidden>
	<s:hidden name="exportX" value="1" />
</s:form>

<script type="text/javascript">
<s:if test="#generateSuccess == true">alert("月度计划生成成功");</s:if>
<s:if test="#repeatGenerate == true">alert("本月月度计划已经生成，不能重复生成")</s:if>
</script>
</body>
</html>
