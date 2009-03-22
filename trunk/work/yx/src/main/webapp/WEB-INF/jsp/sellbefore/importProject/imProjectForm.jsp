<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>添加重点工程项目</title>
</head>
<body>
<div align="left" style="color:#000000">
当前页面：售前合同->重点工程项目->新增重点工程项目
</div>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<s:form action="importantProject" theme="simple" validate="true">
<s:hidden name="method" />
<s:hidden name="imp.id" />
<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td colspan="2" align="right" class="bg_table01" height="3"></td>	
	</tr>
	<tr align="center" class="bg_table02">
		<td align="right"><font color="red">* </font>客户名称：</td>
		<td align="left">
					<input type="text" id="clientName"
							style="width: 140px; height: 21px; font-size: 10pt;"
							readonly="true" /><span style="width: 18px; border: 0px solid red;"> 
					<s:select id="imp_customerId" name="imp.customerId" list="yxClientCodeList"  
					listKey="cli.id" listValue="cli.name" headerKey="" headerValue="--请选择--" 
					cssStyle="margin-left:-140px;width:158px;" 
					 onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=600');">
					<s:hidden  id="clientId"></s:hidden>
		</td>
	 </tr>
	<tr align="center" class="bg_table02">
		<td  align="right"><font color="red">* </font>工程编号：</td>
	    <td  align="left"><s:textfield size="45" name="imp.projectNum"/></td>
	</tr>
	<tr>
		<td class="bg_table02" align="right"><font color="red">* </font>工程名称：</td>
		<td class="bg_table02" align="left"><s:textfield size="45" name="imp.projectName"/></td>
	</tr>
	<tr class="bg_table03" align="center" style="height: 42px">
		<td colspan="2">
			<s:if test=" comeFrom.equals('mod')">
				<input type="button" onclick="doModSave()" class="button01" value="保存修改" />
				<input type="button" onclick="goBack();" class="button01" value="返    回" />
			</s:if>
			<s:else>
				<input type="button" onclick="doSave()" class="button01" value="保  存" />
				<input type="button" onclick="goBack()" class="button01" value="返  回" />
			</s:else>
		</td>
	</tr>
</table>
<s:if test=" comeFrom.equals('mod')">
<table width="100%" border="0" cellspacing="1" cellpadding="1">
	<tr align="center" class="bg_table03">
		<td  nowrap align="center">修改项</td>
		<td  nowrap align="center" >修改前</td>
		<td  nowrap align="center">修改后</td>
		<td  nowrap align="center" >修改时间</td>
		<td  nowrap align="center" >修改人</td>
	</tr>
		<s:set name="groupId" value="0"/>
	<s:iterator value="impHisList" id="info" status="stat">
		 <s:if test="#info[0].groupId!=#groupId && #stat.index!=0 ">			   
			 <tr>
				<td colspan="5" class="bg_white">
					<hr align="center"/>
				</td>
			</tr>
		</s:if>
		<s:set name="groupId" value="#info[0].groupId"/>
		<tr align="center" class="bg_table02">
			<td  align="left"><s:property value="#info[0].type" /></td>
			<td  align="left" ><s:property value="#info[0].original" /></td>
			<td  align="left"><s:property value="#info[0].present" /></td>
			<td  align="center" ><s:date name="#info[0].updateBy" format="yyyy-M-d HH:mm:ss"/></td>
			<td  align="left" ><s:property value="#info[1]" /></td>
		</tr>
	</s:iterator>
</table>
</s:if>
</s:form>
</body>
<script type="text/javascript">
function doSave(){
	if(!validate()){
			document.forms(0).method.value = "saveIMP" ;
			document.forms(0).submit();
	}
}
function doModSave(){
	if(!validate()){
		document.forms(0).method.value = "modIMP" ;
		document.forms(0).submit();
	}
}
function clientChange(selObj){
	document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
	var selected =  selObj.options[selObj.selectedIndex].value;
}
function selectedClient(clientObj){
	var selectValue = clientObj.clientId;
	var selectTest = clientObj.clientName;
	document.getElementById("clientName").value = selectTest;
	document.getElementById("clientId").value = selectValue;
	addOptionX("imp_customerId", selectValue , selectTest , 1);
}
function goBack(){
	location.href="../sellbefore/importantProject.action?method=goQueryInfo";
}
function validate(){
	var ev =new Validator();
	with(importantProject){
		ev.test("notblank","工程编号不能为空!",$('imp.projectNum').value);   
		ev.test("notblank","工程名称不能为空",$('imp.projectName').value);
		ev.test("notblank","客户不能为空",$('imp.customerId').value);
		ev.test("length","工程编号请少于50字符",$('imp.projectNum').value,50); 
		ev.test("length","工程名称请少于50字符",$('imp.projectName').value,50); 
	 }  
	 if (ev.size() > 0) {
	     ev.writeErrors(errorsFrame, "errorsFrame");
	     return true;
	 }
	 return false;
}

$('clientName').value = $('imp_customerId').options[$('imp_customerId').selectedIndex].text;
</script> 
</html>