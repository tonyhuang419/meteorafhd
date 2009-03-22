<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>取消收据关联发票</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
<script type="text/javascript">
function search(){
	document.forms(0).submit();
}
function relraseRelationReceipt()
{

	var checkedNode=$$("input[name=planId][checked]");
	if(checkedNode.length==null||checkedNode.length<=0){
		alert("请选择计划");
		return;
	}else{
		if(confirm("确定要取消关联吗？")){
			var f = document.getElementById("release");
			f.proSids.value = checkedNode[0].value;
			f.submit();
		}
	}
}

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
	<s:hidden name="method" value="releaseRelationBillAndReceiptPlan"></s:hidden>
	<table align="center" width="100%">
	<tr>
	<td>
		<table align=left>
			<tr>
				<td>合同号：<s:textfield name="conId" size="11" />
				&nbsp;&nbsp;<input type="button" onclick="search()" value="搜索"
					class="button01" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="relraseRelationReceipt()" value="取消关联"
					class="button01" /></td>
				<td></td>
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
					<tr align="center"  class="bg_table01">
						<td>选择</td>
						<td>合同号</td>
						<td>项目号</td>
						<td>合同名称</td>
						<td>客户名称</td>
						<td>负责部门</td>
						<td>销售员</td>
						<td>阶段</td>
						<td>计划开票日期</td>
						<td>开票性质</td>
						<td>发票类型</td>
						<td>开票金额</td>
						<td>收款日期</td>
						<td>收款金额</td>
						<td>已开票金额</td>
						<td>已收款金额</td>
					</tr>
					<s:iterator value="resultList" id="month" status="status">
						<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
							onMouseOut="this.bgColor='#FFFFFF';">
							<td><input type="radio" name="planId" value="<s:property value="#month[0].realConBillproSid"/>" /></td>
							<td align="left"><s:property value="#month[2].conId" /></td>
							<td align="left"><s:property value="#month[3].conItemId" /></td>
							<td align="left"><s:property value="#month[2].conName" /></td>
							<td align="left"><s:property value="#month[1].name" /></td>
							<td align="left"><s:if test="#month[3] != null">
								<s:property
									value="typeManageService.getYXTypeManage(1018,#month[3].itemResDept).typeName" />
							</s:if> <s:else>
								<s:property
									value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName" />
							</s:else></td>
							<td><s:property
								value="#month[7].name" /> </td>
							<td align="left"><s:property
								value="typeManageService.getYXTypeManage(1023,#month[4]).typeName" />
							</td>
							<td align="center"><s:property
								value="#month[0].realPredBillDate" /></td>
							<td align="left"><s:property
								value="typeManageService.getYXTypeManage(1012,#month[0].billNature).typeName" /></td>
							<td align="left"><s:property
								value="typeManageService.getYXTypeManage(1004,#month[0].billType).typeName" /></td>
							<td align="right"><s:property
								value="#month[0].realBillAmount" /></td>
							<td align="center"><s:property
								value="#month[0].realPredReceDate" /></td>

							<td><s:property value="#month[0].realTaxReceAmount" /></td>

							<td><s:if test="#month[0].billInvoiceAmount!=null">
								<s:property value="#month[0].billInvoiceAmount" />
							</s:if> <s:else>
						0.00
						</s:else></td>
							<td><s:if test="#month[0].realArriveAmount!=null">
								<s:property value="#month[0].realArriveAmount" />
							</s:if> <s:else>
						0.00
						</s:else></td>
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
<s:form id="release" action="baseData">
	<s:hidden name="method" value="releaseRelationBillRecipt"/>
	<s:hidden name="proSids"/>
</s:form>
<%-- 
<s:if test="sign!=null">
<script type="text/javascript">
	alert("<s:property value="sign"/>");
</script>
</s:if>
--%>
</body>
</html>