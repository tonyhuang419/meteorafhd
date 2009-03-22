<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>应交材料设置显示</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>

</head>
<body>
<div align="left" style="color:#000000">当前页面：基础管理—>应交材料设置显示 </div>
<div><iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe></div>
<s:form action="yx/system/material/contractMaterialSet.action"
	theme="simple">
	<s:hidden name="method"></s:hidden>
	<s:hidden name="oldCustomerName"
		value="%{contractMaterial.customerName}"></s:hidden>
	<s:hidden name="oldCusProjectType"
		value="%{contractMaterial.customerProjectType}"></s:hidden>
	<table border="0" cellspacing="1" cellpadding="1" width="100%"
		class="bg_white" align="center">
		<tr>
		 <td class="bg_table01" height="1" colspan="2"><img
		 	src="../../images/temp.gif" width="1" height="1">	
		 </td>
		</tr>
		<tr class="bg_table02" >
			<td align="right" width="40%">客户名称：</td>
			<td width="60%"> <s:textfield
				name="cusName" readonly="true" /></td>
		</tr>
		<tr class="bg_table02" >
			<td align="right">客户项目类型：</td>
			<td><s:property value="typeManageService.getYXTypeManage(1007L,contractMaterial.customerProjectType).typeName"/></td>
		</tr>
		<s:iterator value="materialList">
			<tr class="bg_table02" >
				<td align="right">&nbsp;</td>
				<td><s:property value="materialName" /></td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="2" align="center"><input type="button" value="关闭" onclick="window.close()" class="button01"></td>
		</tr>
	</table>
</s:form>
</body>
</html>