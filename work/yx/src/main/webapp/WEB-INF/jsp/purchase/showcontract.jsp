<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>申购选择合同</title>
<SCRIPT language=JavaScript src="./js/public.js"></SCRIPT>

<script language="javascript">
	
	
	function checkString(){
	var contractNames=document.getElementsByName("contractName");
	var contractIds=document.getElementsByName("contractId");
	var projectIds=document.getElementsByName("projectId");
	var customerNames=document.getElementsByName("customerName");
	var customerIds=document.getElementsByName("customerId");
    var ids=document.getElementsByName("ids");
    var conTaxTamounts = document.getElementsByName("conTaxTamount");
    var itemSids = document.getElementsByName("itemSid");
    
    var contractName;
    var contractId;
    var projectId;
    var customerName;
    var customerId;
    var id;
    var conTaxTamount;
    var itemSid;
    
    for(var i=0;i<contractNames.length;i++){
        if(ids[i].checked){
             id=ids[i].value;       <%--合同主体id--%>
             contractName=contractNames[i].value;   <%--合同名称--%>
             contractId=contractIds[i].value;		 <%--合同号--%>
             projectId=projectIds[i].value;				<%--项目号--%>
             customerName=customerNames[i].value;		<%--客户名称--%>
             customerId=customerIds[i].value;			<%--客户id--%>
             conTaxTamount=conTaxTamounts[i].value;		<%--项目金额--%>
             itemSid=itemSids[i].value;		<%--项目主体系统号--%>
             	window.opener.document.getElementById("purchase_am_mainId").value=id;
				window.opener.document.getElementById("purchase_contractName").value=contractName;
				window.opener.document.getElementById("purchase_contractId").value=contractId;
				window.opener.document.getElementById("purchase_am_eventId").value=projectId;
				window.opener.document.getElementById("purchase_cName2").value=customerName;
				window.opener.document.getElementById("purchase_cNameId2").value=customerId;
				window.opener.document.getElementById("itemAmount").value = parseFloatNumber(conTaxTamount);
				window.opener.document.getElementById("itemSid").value = itemSid;
       }
    }
  
   this.window.close();
}
	function link(){
		var ids=document.getElementsByName("ids");
<!--		var amids=document.getElementsByName("amids");-->
		var id;
		var j=0;
		for(var i=0;i<ids.length;i++){
		  
			if(ids[i].checked){
			  j++;
				id=ids[i].value;
			}
		}
		  if(j==1){
			if(window.confirm("确定要关联吗?")){
		document.forms(0).action="/yx/purchase/purchase.action?method=link";
		document.forms(0).submit();
		  }
		}
		
		  if(j==0){
		  
		  alert("请选择关联对象！");
		  }
		     if(j>1){
    
      alert("不能选择多个关联对象！");
   }
	}
</script>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body style="margin: 0px;">
<s:form action="contractQuery" theme="simple">
<br>
<s:hidden name="applyType"></s:hidden>

			<table align="center" border=0 width="100%">
				<tr class="bg_table02">
					<td width="6%" align="right">合同名称：</td>
					<td width="10%" align="left"><s:textfield name="conName"></s:textfield></td>
					<td width="6%" align="right">工程部门：</td>
					<td width="10%" align="left">
					<s:select name="mainItemDept" list="dutyList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="请选择工程部门" headerKey="-1"></s:select>
					</td>
				</tr>
				<tr class="bg_table02">
					<td width="6%" align="right">合同号：</td>
					<td width="10%" align="left"><s:textfield name="conId"></s:textfield></td>
					<td width="6%" align="right">项目号：</td>
					<td width="10%" align="left"><s:textfield name="partyAProId"></s:textfield></td>
				</tr>
			</table>

			<table align="center" border=1 cellpadding="0" cellspacing=0 bordercolor="#808080" style=" border-collapse: collapse;"
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

				<s:iterator id="contract" value="info.result">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td>
							<input type="radio" name="ids"	value="<s:property value="#contract[1].conMainInfoSid"/>">
						</td>
						<td align="left">
						<input type="hidden" name="contractId" value="<s:property value="#contract[1].conId" />">
						<s:property value="#contract[1].conId" /></td>
						<td align="left">
						<input type="hidden" name="contractName" value="<s:property value="#contract[1].conName" />" >
						<s:property value="#contract[1].conName" /></td>
						<td align="left">
						<input type="hidden" name="projectId" value="<s:property value="#contract[0].conItemId" />" >
						<s:property value="#contract[0].conItemId" />
						<input type="hidden" name="itemSid" value="<s:property value="#contract[4]" />" >
						</td>
						<td align="left">
						<input type="hidden" name="department" value="<s:property value="#contract[0].itemResDept" />" >
						<s:property value="typemanageservice.getYXTypeManage(1018,#contract[0].itemResDept).typeName" /></td>
						<td align="left">
						<input type="hidden" name="mainItemPeople" value="<s:property value="#contract[0].itemResDeptP" />">
						<s:property value="#contract[0].itemResDeptP" /></td>
						<td align="right">
						<input type="hidden" name="conTaxTamount" value="<s:property value="#contract[2]" />">
							<s:property value="#contract[2]" />
								<input type="hidden" name="customerId" value="<s:property value="#contract[1].conCustomer" />" />
								<input type="hidden"  name="customerName" value="<s:property value="#contract[3]"/>" />
						</td>
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
		
	<table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">		
		<s:if test="'link'.equals(action)"> <%--申购关联合同用系列按钮 --%>
		<tr class="bg_table03" height="42px">
			<td align="center" colspan="8"> <input
				class="button01" value="查  询" type="submit"
				onclick="javascript:document.forms(0).action='/yx/purchase/contractQuery.action'" />
			<input class="button01" type="button" value="关  联"
				onclick="link();">
				<input class="button01" type="button" value="关  闭" onclick="window.close()"></td>
		</tr>
		</s:if><s:elseif test="action==null">
		<tr class="bg_table03" height="42px">
			<td align="center" colspan="8"> <input
				class="button01" value="查  询" type="submit"
				onclick="javascript:document.forms(0).action='/yx/purchase/contractQuery.action'" />
			<input class="button01" type="button" value="添  加"
				onclick="checkString()">
				<input class="button01" type="button" value="关  闭" onclick="window.close()"></td>
		</tr>
		</s:elseif>
	</table>
</s:form>
</body>
</html>

