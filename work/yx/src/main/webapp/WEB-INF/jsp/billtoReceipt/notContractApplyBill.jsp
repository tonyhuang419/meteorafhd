<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<title>未签开票关联合同</title>
</head>
<body style="margin: 0px;">
<div align="left" style="color: #000000">当前页面：开票管理 -> 未签合同开票</div>
<s:form action="notContractApplyBill.action" theme="simple" >

	<table align="center" border=1 cellpadding="1" cellspacing=1 
		width="100%" id="billApplyList"  bordercolor="#808080" style=" border-collapse: collapse;">
		<tr align="center">
			<td class="bg_table01">开票申请编号</td>
			<td class="bg_table01">申请日期</td>
			<td class="bg_table01">申请金额</td>
			<td class="bg_table01">开票性质</td>
			<td class="bg_table01">票据类型</td>
			<td class="bg_table01">开票内容</td>
			<td class="bg_table01">申请状态</td>
		</tr>
		<s:iterator value="info.result">
			<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
			
				<td align="left"><s:property value="billApplyNum"/></td>
				<td align="center"><s:property value="applyId"/></td>
				<td align="right">
					<s:property value="billAmountTax"/>
					<input type="hidden" name="taxAmount<s:property value="billApplyId"/>" value="<s:property value="billAmountTax"/>"/>
				</td>
				<td align="left"><s:property value="typeManageService.getYXTypeManage(1012L ,billNature).typeName"/></td>
				<td align="left"><s:property value="typeManageService.getYXTypeManage(1004L ,billType).typeName"/></td>
				<td align="left"><s:property value="billContent"/></td>
				<td align="left">	
					<s:if test="applyBillState==1">保存</s:if>
						<s:if test="applyBillState==2">待确认</s:if>
						<s:if test="applyBillState==3">确认通过</s:if>
						<s:if test="applyBillState==4">确认退回</s:if></td>
			</tr>
		</s:iterator>
	</table>
	<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
	</TABLE>
</s:form>

</body>
</html>
