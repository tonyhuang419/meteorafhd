<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>取消月度收款</title>
<script type="text/javascript">

</script>
</head>
<body>
<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:iterator id="erMessage" value="processResult.errorMessages">
	<font color="red"><strong><s:property value="#erMessage"/></strong></font><br/>
</s:iterator>
<s:form action="baseData" theme="simple">
	<s:hidden name="method" value="cancelMonthReceQuery"></s:hidden>
	<s:hidden name="resetCondition" value="false" id="resetCondition"/>
	<table align="center" width="100%">
	<tr>
	<td>
		<table align=left>
			<tr>
				<td>合同号：<s:textfield name="conId" size="11" />&nbsp;
					<input type="button" onclick="search()" value="搜索" class="button01" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" onclick="cancelReceByRece()" value="取消计划" class="button01" />
				</td>
			</tr>
		</table>
		</td>
		</tr>
		<tr>
			<td>
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill" bordercolor="#808080"
				style="border-collapse: collapse;">
				<s:if test="info != null">
					<s:set name="resultList" value="info.result"></s:set>
					<tr align="center"  class="bg_table01" >
						<td nowrap>选择</td>
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
					</tr>
					<s:iterator value="resultList" id="monthlyPlan" status="status">
						<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; " onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="radio" name="planId" value="<s:property value="#monthlyPlan[9].monthlyReceproSid"/>" /></td>
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
						</tr>
					</s:iterator>
					<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04"><baozi:pages value="info"
								beanName="info" formName="forms(0)" /></td>
						</tr>
					</TABLE>
				</s:if>
			</table>
			</td>
		</tr>
	</table>
</s:form>
<script type="text/javascript">
function search()
{
	$("resetCondition").value="true";
	document.baseData.submit();
}
function cancelReceByRece()
{
	var checkedNode=$$("input[name=planId][checked]");
	if(checkedNode.length==null||checkedNode.length<=0){
		alert("请选择计划");
		return;
	}
	if(confirm("确定要取消吗？")){
		document.baseData.method.value="cancelMonthRece";
		document.baseData.submit();
	}
}
</script>
</body>
</html>