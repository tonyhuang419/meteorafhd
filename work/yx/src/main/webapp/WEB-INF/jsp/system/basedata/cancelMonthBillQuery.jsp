<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>取消月度开票</title>
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
	<s:hidden name="method" value="cancelMonthBillQuery"></s:hidden>
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
						<td nowrap>合同名称</td>
						<td nowrap>客户名称</td>
						<td nowrap>负责部门</td>
						<td nowrap>开票性质</td>
						<td nowrap>发票类型</td>
						<td nowrap>计划开票日期</td>
						<td nowrap>计划开票金额</td>
						<td nowrap>已开票金额</td>
						<td nowrap>销售员</td>
					</tr>
					<s:iterator value="resultList" id="month" status="status">
						<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; " onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="radio" name="planId" value="<s:property value="#month[0].monthlyBillproSid"/>" /></td>
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
						<td><s:property value="#month[6]" /></td>
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
		document.baseData.method.value="cancelMonthBill";
		document.baseData.submit();
	}
}
</script>
</body>
</html>