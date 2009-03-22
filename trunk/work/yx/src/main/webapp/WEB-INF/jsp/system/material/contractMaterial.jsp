<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>添加应交材料设置</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
function searchCumsomer()
{
window.open('/yx/searchClient/searchClientQuery.action?method=getSelectClientlist','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');
}
function selectedClient(clientObj){
		document.forms(0).elements("contractMaterial.customerName").value = clientObj.clientId;
		document.forms(0).cusName.value = clientObj.clientName;
		var url="yx/system/material/contractMaterialSet.action";
		checkName(url,"checkCustomerName",clientObj.clientId);
}
function saveAllChecked()
{
	if(!validate1()){
		document.forms(0).elements("method").value="tempAddEnter";
		document.forms(0).submit();
	}
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
// 验证客户名称不能重复
function checkName(url, method, name){

	var ev=new Validator();   
  	var myRequest = new Request({url:url,async:false,method:'get'});
  	myRequest.addEvent("onSuccess",function(responseText, responseXML){
		if(responseText =='false'){
			document.forms(0).elements("contractMaterial.customerName").value = "";
			document.forms(0).cusName.value ="";
			ev.addError("客户名称已经存在，请重新输入！");
		} 
   	});
	myRequest.send("method="+method+"&contractMaterial.customerName="+name+"&randomNum="+Math.random());
	ev.writeErrors(errorsFrame, "errorsFrame");
}
function validate1()
{
	var ev2=new Validator();
	ev2.test("notblank","客户名称不能为空",$('cusName').value);
	ev2.writeErrors(errorsFrame,"errorsFrame");
	if(ev2.size()>0){
	return true;
	}
	return false;
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
<div align="left" style="color:#000000">当前页面：基础管理—>新增应交材料设置 </div>
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
				<td colspan="2" align="right" class="bg_table01" height="3"></td>	
			</tr>
		<tr class="bg_table02" >
			<td align="right" width="40%"><font color="red">* </font>客户名称：</td>
			<td width="60%"><s:hidden name="contractMaterial.customerName"></s:hidden> <s:textfield
				name="cusName" readonly="true" /><input type="button" value="..."
				onclick="searchCumsomer();" /></td>
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
		<tr class="bg_table03">
			<td colspan="2" align="center"><input type="button" value="保存" class="button01"
				onclick="addContractMaterialSet();" />
				<input type="button" value="保存并关闭" class="button01"
				onclick="addContractMaterialAndClose();" />
				<input type="button" value="返回" class="button01" onclick="goback();" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>