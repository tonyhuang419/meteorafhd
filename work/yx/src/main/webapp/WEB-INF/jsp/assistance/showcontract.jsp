<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>外协选择合同</title>
<SCRIPT language=JavaScript src="./js/public.js"></SCRIPT>

<script language="javascript">
	
function check(){
	var names = document.getElementsByName("contractName");
	var ids = document.getElementsByName("ids");
	var projectIds = document.getElementsByName("projectId");
	var itemIds = document.getElementsByName("itemIds");
	var projectId;
	var name;
	var id;
	var itemId;
	var k=0;
	for(i=0;i<names.length;i++){
		if(ids[i].checked){
			id = ids[i].value;
			name = names[i].value;
			projectId = projectIds[i].value;
			itemId = itemIds[i].value;
			k++;
		}
	}
	if(k==1){
		if(window.opener.setItemValue != null){
				window.opener.setItemValue({name:name,id:id,projectId:projectId,itemId:itemId});
		}
		this.window.close();
	}else{
		alert("您还没有选择项目！");
	}
	
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
<body style="margin: 0px;">
<s:form action="chooseContract" theme="simple">
<br>

			<table align="center" border=0 width="100%" bordercolor="#808080" style=" border-collapse: collapse;"> 
				<tr class="bg_table02">
					<td width="6%" align="right">合同名称：</td>
					<td width="10%" align="left"><s:textfield name="conName" size="15"/></td>
					<td width="6%" align="right">工程部门：</td>
					<td width="10%" align="left">
					<s:select name="mainItemDept" list="dutyList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="请选择工程部门" headerKey="-1"></s:select>
					</td>
				</tr>
				<tr class="bg_table02">
					<td width="6%" align="right">合同号：</td>
					<td width="10%" align="left"><s:textfield name="conId" size="15"/>    </td>
					<td width="6%" align="right">项目号：</td>
					<td width="10%" align="left"><s:textfield name="partyAProId" size="15"/></td>
				</tr>
			</table>
			
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01">主体合同号</td>
					<td class="bg_table01">合同名称</td>
					<td class="bg_table01">项目号</td>
					<td class="bg_table01">工程责任部门</td>
					<td class="bg_table01">责任人</td>
					<td class="bg_table01">项目金额</td>
				</tr>
				<s:iterator id="contract" value="info.result">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="radio" name="ids"
							value="<s:property value="#contract[1].conMainInfoSid"/>"
							onclick=""></td>
						<td align="left"><input type="hidden" name="contractId"
							value="<s:property value="#contract[1].conId" />" style="border:0px"><s:property value="#contract[1].conId" /></td>
						<td align="left"><input type="hidden" name="contractName"
							value="<s:property value="#contract[1].conName" />" style="border:0px"><s:property value="#contract[1].conName" /></td>
						<td align="left"><input type="hidden" name="projectId"
							value="<s:property value="#contract[0].conItemId" />" style="border:0px">
							<input type="hidden" name="itemIds"
							value="<s:property value="#contract[0].conItemMinfoSid" />" style="border:0px">
							<s:property value="#contract[0].conItemId" /></td>
						<td align="left"><input type="hidden" name="department"
							value="<s:property value="#contract[0].itemResDept" />" style="border:0px">
							<s:property value="typemanageservice.getYXTypeManage(1018,#contract[0].itemResDept).typeName"/>
							</td>
						<td align="left"><input type="hidden" name="mainItemPeople"
							value="<s:property value="#contract[0].itemResDeptP" />" style="border:0px"><s:property value="#contract[0].itemResDeptP" /></td>
						<td align="right"><input type="hidden" name="conTaxTamount"
							value="<s:property value="#contract[2]" />" style="border:0px"><s:property value="#contract[2]" /></td>
						<input type="hidden" name="customerId" value="<s:property value="#contract[0].itemCustomer" />" /><input type="hidden"  name="customerName" value="<s:property value="#contract[1]"/>" />
					</tr>
				</s:iterator>
				<s:hidden name="amids"></s:hidden>
			</table>

			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table02"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>


	<table align="center" border=0 width="100%" > 
		<tr class="bg_table03" height="42px">
			<td align="center" colspan="8">
			 <input class="button01" value="查  询" type="submit"
				onclick="javascript:document.forms(0).action='<s:url action="chooseContract"></s:url>'" />
			<input class="button01" type="button" value="添  加"
				onclick="check()">
			<input class="button01" type="button" value="关  闭" onclick="window.close()"></td>
		</tr>
	</table>
</s:form>
</body>
</html>

