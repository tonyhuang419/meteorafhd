<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>取消收款</title>
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
	<s:hidden name="method" value="cancelReceByReceQuery"></s:hidden>
	<s:hidden name="resetCondition" value="false" id="resetCondition"/>
	<table align="center" width="100%">
	<tr>
	<td>
		<table align=left>
			<tr>
				<td>合同号：<s:textfield name="conId" size="11" />&nbsp;
					<input type="button" onclick="search()" value="搜索" class="button01" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" onclick="cancelReceByRece()" value="取消收款" class="button01" />
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
						<td nowrap>销售员</td>
	 					<td nowrap>合同号</td>
	 					<td nowrap>项目号</td>
	 					<td nowrap>到款金额</td>
	 					<td nowrap>到款方式</td>
	 					<td nowrap>到款日期</td>
					</tr>
					<s:iterator value="resultList" id="rl" status="status">
						<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
							onMouseOut="this.bgColor='#FFFFFF';">
							<td><input type="radio" name="receId" value="<s:property value="#rl[2].id"/>" /></td>
							<td align="left"><s:property value="#rl[3]"/></td>
	 						<td align="left"><s:property value="#rl[0].conId"/></td>
	 						<td align="left"><s:property value="#rl[1].conItemId"/></td>
	 						<td align="right"><s:property value="#rl[2].amount"/></td>
	 						<td align="left"><s:property value="typeManageService.getYXTypeManage(1017L,#rl[2].receType).typeName"/></td>
	 						<td align="center"><s:property value="#rl[2].amountTime"/></td>
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
	var checkedNode=$$("input[name=receId][checked]");
	if(checkedNode.length==null||checkedNode.length<=0){
		alert("请选择收款");
		return;
	}
	if(confirm("确定要取消收款吗？")){
		document.baseData.method.value="cancelReceByRece";
		document.baseData.submit();
	}
}
</script>
</body>
</html>