<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>修改应交材料设置</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
function saveAllChecked()
{
	document.forms(0).elements("method").value="tempUpdateEnter";
	document.forms(0).submit();
}
function addContractMaterialSet(){
	if(!validate()){
		document.forms(0).elements("method").value="addContractMaterialSet";
		document.forms(0).submit();
	}
}
function addContractMaterialAndClose()
{
	if(!validate()){
		document.forms(0).elements("method").value="addContractMaterialAndClose";
		document.forms(0).submit();
	}
}
function goback()
{
	document.forms(0).elements("method").value=null;
	document.forms(0).submit();
}
function validate()
{
	var ev2=new Validator();
	var selectNode=$('contractMaterial.customerProjectType');
	ev2.test("notblank","客户名称不能为空",$('cusName').value);
	if(selectNode.options[selectNode.selectedIndex].value==""){
		ev2.addError("请选择客户项目类型！");
	}
	ev2.writeErrors(errorsFrame,"errorsFrame");
	if(ev2.size()>0){
		return true;
	}
	return false;
}
</script>
</head>
<body>
<div align="left" style="color:#000000">当前页面：基础管理—>修改应交材料设置 </div>
<div><iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe></div>
<s:form action="yx/system/material/contractMaterialSet.action"
	theme="simple">
	<s:hidden name="method"></s:hidden>
	<s:hidden name="oldCustomerName"
		value="%{contractMaterial.customerName}"></s:hidden>
	<s:hidden name="oldCusProjectType"
		value="%{contractMaterial.customerProjectType}"></s:hidden>
		<s:hidden name="mid"></s:hidden>
	<table border="0" cellspacing="1" cellpadding="1" width="100%"
		class="bg_white" align="center">
		<tr>
		 <td class="bg_table01" height="1" colspan="2"><img
		 	src="../../images/temp.gif" width="1" height="1">	
		 </td>
		</tr>
		<tr class="bg_table02" >
			<td align="right" width="40%"><font color="red">* </font>客户名称：</td>
			<td width="60%"><s:hidden name="contractMaterial.customerName"></s:hidden> <s:textfield
				name="cusName" readonly="true" /></td>
		</tr>
		<tr class="bg_table02" >
			<td align="right"><font color="red">* </font>客户项目类型：</td>
			<td><s:select name="contractMaterial.customerProjectType"
				headerKey="" headerValue="--请选择--" list="typeManageList"
				listKey="typeSmall" listValue="typeName"
				onchange="saveAllChecked();"></s:select></td>
		</tr>
		<s:iterator value="materialManageList">
			<tr class="bg_table02" >
				<td align="right"><input type="checkbox" name="customerMaterial"
					<s:if test="isMaterialChecked(materialCode)">checked</s:if>
					value="<s:property value="materialCode"/>" /></td>
				<td><s:property value="materialName" /></td>
			</tr>
		</s:iterator>
		<tr class="bg_table01">
			<td colspan="2" align="center"><input type="button" value="保存" class="button01"
				onclick="addContractMaterialSet();" /> &nbsp;&nbsp;<input
				type="button" value="保存并关闭" class="button01"
				onclick="addContractMaterialAndClose();" /> <input type="button"
				value="返回" class="button01" onclick="goback();" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>