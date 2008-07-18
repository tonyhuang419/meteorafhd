<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<SCRIPT language=JavaScript src="./js/public.js"></SCRIPT>

<script language="javascript">
	
	
	function checkString(){
	var contractNames=document.getElementsByName("contractName");
	var contractIds=document.getElementsByName("contractId");
	var projectIds=document.getElementsByName("projectId");
	var customerNames=document.getElementsByName("customerName");
	var customerIds=document.getElementsByName("customerId");
    var ids=document.getElementsByName("ids");
    var contractName;
    var contractId;
    var projectId;
    var customerName;
    var customerId;
    var id;
    for(var i=0;i<contractNames.length;i++){
        if(ids[i].checked){
             id=ids[i].value;
             contractName=contractNames[i].value;
             contractId=contractIds[i].value;
             projectId=projectIds[i].value;
             customerName=customerNames[i].value;
             customerId=customerIds[i].value;
             	window.opener.document.getElementById("purchase_am_mainId").value=id;
				window.opener.document.getElementById("purchase_contractName").value=contractName;
				window.opener.document.getElementById("purchase_contractId").value=contractId;
				window.opener.document.getElementById("purchase_am_eventId").value=projectId;
				window.opener.document.getElementById("purchase_cName2").value=customerName;
				window.opener.document.getElementById("purchase_cNameId2").value=customerId;
       }
    }
  
   this.window.close();
}
function check(){
	var names = document.getElementsByName("contractName");
	var ids = document.getElementsByName("ids");
	var projectIds = document.getElementsByName("projectId");
	var projectId;
	var name;
	var id;
	for(i=0;i<names.length;i++){
		if(ids[i].checked){
			id = ids[i].value;
			name = names[i].value;
			projectId = projectIds[i].value;
			window.opener.document.getElementById("contractName").value = name;
			window.opener.document.getElementById("contractId").value = id;
			window.opener.document.getElementById("projectId").value = projectId;
		}
	}
	this.window.close();
}
	function link(){
		var ids=document.getElementsByName("ids");
<!--		var amids=document.getElementsByName("amids");-->
		var id;
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				id=ids[i].value;
			}
		}
		document.forms(0).action="/yx/purchase/purchase.action?method=link";
		document.forms(0).submit();
	}
</script>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="contractQuery" theme="simple">
<table width="98%" border="0" cellspacing="1" cellpadding="1" align="center">
				<tr>
					<td height="3" align="left">当前页面：外协管理->选择合同</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img
						src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
				</tr>
			</table>
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 width="100%">
				<tr class="bg_table02">
					<td width="6%" align="right">合同名称：</td>
					<td width="10%" align="left"><input name="conName" type="text"
						size="15"/ ></td>
					<td width="6%" align="right">工程部门：</td>
					<td width="10%" align="left">
					<s:select name="mainItemDept" list="dutyList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="请选择工程部门" headerKey="-1"></s:select>
					</td>
				</tr>
				<tr class="bg_table02">
					<td width="6%" align="right">合同号：</td>
					<td width="10%" align="left"><input name="conId" type="text"
						size="15"/ >    </td>
					<td width="6%" align="right">项目号：</td>
					<td width="10%" align="left"><input name="partyAProId"
						type="text" size="15"/ ></td>
				</tr>
			</table>
			</td>
		</tr>

		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01">主体合同号</td>
					<td class="bg_table01">合同名称</td>
					<td class="bg_table01">项目号</td>
					<td class="bg_table01">工程责任部门</td>
					<td class="bg_table01">责任人</td>
					<td class="bg_table01">项目金额</td>
				</tr>
				<s:iterator id="contract" value="conList">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="radio" name="ids"
							value="<s:property value="#contract[0].conMainInfoSid"/>"
							onclick=""></td>
						<td><input type="hidden" name="contractId"
							value="<s:property value="#contract[0].conId" />" style="border:0px"><s:property value="#contract[0].conId" /></td>
						<td><input type="hidden" name="contractName"
							value="<s:property value="#contract[0].conName" />" style="border:0px"><s:property value="#contract[0].conName" /></td>
						<td><input type="hidden" name="projectId"
							value="<s:property value="#contract[0].partyAProId" />" style="border:0px"><s:property value="#contract[0].partyAProId" /></td>
						<td><input type="hidden" name="department"
							value="<s:property value="#contract[2]" />" style="border:0px"><s:property value="#contract[2]" /></td>
						<td><input type="hidden" name="mainItemPeople"
							value="<s:property value="#contract[0].mainItemPeople" />" style="border:0px"><s:property value="#contract[0].mainItemPeople" /></td>
						<td><input type="hidden" name="conTaxTamount"
							value="<s:property value="#contract[0].conTaxTamount" />" style="border:0px"><s:property value="#contract[0].conTaxTamount" /></td>
						<input type="hidden" name="customerId" value="<s:property value="#contract[0].itemCustomer" />" /><input type="hidden"  name="customerName" value="<s:property value="#contract[1]"/>" />
					</tr>
				</s:iterator>
				<s:hidden name="amids"></s:hidden>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			</td>
		</tr>
		<tr class="bg_table01">
			<td align="center" colspan="8"> <input
				class="button01" value="查  询" type="submit"
				onclick="javascript:document.forms(0).action='<s:url action="chooseContract"></s:url>'" />
			<input class="button01" type="button" value="添  加"
				onclick="check()"><input class="button01" type="button" value="关  闭" onclick="window.close()"></td>
		</tr>
	</table>
</s:form>
</body>
</html>

