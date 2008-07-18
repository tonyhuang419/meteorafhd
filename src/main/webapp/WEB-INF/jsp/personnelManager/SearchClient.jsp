<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>选择关联客户</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<script language="javascript">
	
	function onSubmit(){
		document.selectPerQuery.action="/yx/personnelManager/selectPerQuery.action";
		document.selectPerQuery.method.value="associateClient";
		document.selectPerQuery.submit();
	}	
</script>
<body>
<s:form action="selectPerQuery" theme="simple">
	<s:hidden name="method" value="selectClient"></s:hidden>
	<s:hidden name="perId"></s:hidden>
	<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
				<tr class="bg_table03">
					<td colspan="3" class="bg_table03">客户名称：<s:textfield name="cName"></s:textfield></td>
					<td colspan="3" class="bg_table03">客户性质：<s:select  name="tName" list="clientNIDList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="">
						</s:select>
					</td>
				</tr>
				<tr class="bg_table03">
					<td colspan="6" class="bg_table03">
					<div align="center">
				<input value="查询"
						type="submit" class="button01"/>	
				</tr>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01">客户</td>
					<td class="bg_table01">客户性质</td>
					<td class="bg_table01">行业类型</td>
					<td class="bg_table01">客户开票名称</td>
					<td class="bg_table01">客户开户银行</td>
					<td class="bg_table01">开户帐号</td>
					<td class="bg_table01">税号</td>
				</tr>
				<s:iterator value="info.result" id="result" status="status">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="empClients[<s:property value="#status.Index"/>].cli.id"
							value="<s:property value="id"/>" />
							<input type="hidden" name="empClients[<s:property value="#status.Index"/>].exp.id"
														value="<s:property value="perId"/>" />
						<td>
							<input type="hidden" name="clName" value="<s:property value="name" />" />
							<s:property value="name" />
						</td>
						
						<td>
							<input type="hidden" name="clType" value="<s:property value="typeManageService.getYXTypeManage(1001,clientNID).typeName"/>" />
							<s:property value="typeManageService.getYXTypeManage(1001,clientNID).typeName"/>
						</td>
						<td>
							<input type="hidden" name="buType" value="<s:property value="typeManageService.getYXTypeManage(1002,businessID).typeName"/>" />
							<s:property value="typeManageService.getYXTypeManage(1002,businessID).typeName"/>
						</td>
						<td><s:property value="billName" /></td>
						<td><s:property value="billBank" /></td>
						<td><s:property value="account" /></td>
						<td><s:property value="taxNumber" /></td>
					</tr>
				</s:iterator>
				<tr class="bg_table03">
					<td colspan="8" align="center" class="bg_table03"><input type="button" name="SearchBtn" value="　确认关联　" class="button01" onClick="onSubmit();">
					<input type="button" name="close" value="　返 回　" class="button01" onClick="javascript:window.close();"></td>
				</tr>
			</table>

			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>

			</td>
		</tr>
	</table>
</s:form>
</body>

</html>