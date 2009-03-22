<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>选择合同未签开票</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
<script language="javascript">
	function addInvoice(){
		document.searchInvoiceQuery.action="<s:url action="searchInvoiceQuery"><s:param name="method">conInvoice</s:param></s:url>";
		document.searchInvoiceQuery.submit();
	}
<s:if test="#isRelationSuccess == 'true' ">
window.opener.refreshPage(); 
window.close();
</s:if>
</script>

</head>
<body>
<s:form action="searchInvoiceQuery" theme="simple">
   <s:hidden name="mainid"></s:hidden>
			<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
				<tr>
					<td align="right" colspan="8" class="bg_table01" height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr>
				    <td width="97"></td>
					<td  align="left">
					</td>
				    <td colspan="5" align="left"></td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
				<tr align="center">
					<td width="9%" class="bg_table01"  nowrap>选择</td>
					<td width="13%" class="bg_table01"  nowrap>开票申请编号</td>
					<td width="18%" class="bg_table01"  nowrap>开票性质</td>
					<td width="14%" class="bg_table01"  nowrap>发票类型</td>
					<td width="14%" class="bg_table01"  nowrap>开票申请金额</td>
					<td width="14%" class="bg_table01"  nowrap>开票内容</td>
				</tr>
				<s:iterator value="info.result">
					<tr align="center">
						<td><input type="checkbox" name="selectid" value="<s:property value="billApplyId"/>" /></td>
						<td nowrap><s:property value="billApplyNum"/></td>
						<td nowrap><s:property  value="typeManageService.getYXTypeManage(1012,billNature).typeName"/></td>
						<td nowrap><s:property  value="typeManageService.getYXTypeManage(1004,billType).typeName"/></td>
						<td nowrap><s:property value="billAmountTax"/></td>
						<td nowrap><s:property value="billContent"/></td>
					</tr>
			   </s:iterator>
			</table>
			<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				  <tr valign="top">
					  <td class="bg_table04"><s:if test="info.result!=null"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></s:if></td>
				  </tr>
		    </TABLE>
		    <TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				  <tr valign="top">
					  <td class="bg_table02" align="center">
					    <input type="button" value="添加" onclick="addInvoice()">
					    <input type="button" value="关闭" onclick="window.close()">
					  </td>
				  </tr>
		    </TABLE>
  </s:form>
</body>
</html>
