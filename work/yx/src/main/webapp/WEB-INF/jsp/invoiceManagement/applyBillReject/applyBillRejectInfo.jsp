<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>开票申请退回信息</title>

<script language="javascript">
//已签开票申请open
function openOneInvoiceHasContract(oid)
{
	 window.open("/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=showInvoice&applyBillSid="+oid+"&opType=01","","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=850");
}
		
//未签开票申请open
function openOneInvoice(oid)
{
	 window.open("/yx/invoice/createInvoice.action?method=showInvoiceInfor&applyBillId="+oid+"&opType=01","","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=850");
}

function openApplyBill(initHasCon,applybillSid){
	if(initHasCon==1){
		openOneInvoice(applybillSid);
	}
	else{
		openOneInvoiceHasContract(applybillSid);
	}
}
</script>

<style type="text/css">

</style>
</head>
<body>

<s:form action="applyBillReject" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<div align="left">
   <div  style="color:#000000"><p>当前页面：开票管理->开票申请退回查看</p></div>
</div>
<table width="100%" border="1"   id="recordItemNo" bordercolor="#808080" style=" border-collapse: collapse;">
   <tr>
     <td  nowrap   class="bg_table01"><div align="center">合同号</div></td>
     <td  nowrap   class="bg_table01"><div align="center">项目号</div></td>
     <td  nowrap   class="bg_table01"><div align="center">申请金额</div></td>
     <td  nowrap   class="bg_table01"><div align="center">申请类型</div></td>
     <td  nowrap   class="bg_table01"><div align="center">开票性质</div></td>
     <td  nowrap   class="bg_table01"><div align="center">票据类型</div></td>
     <td  nowrap   class="bg_table01"><div align="center">开票内容</div></td>
     <td  nowrap   class="bg_table01"><div align="center">退回理由</div></td>
    <td  nowrap   class="bg_table01"><div align="center">申请人</div></td>
   </tr>
<s:iterator value="info.result" id="ab" status="status">
    <tr onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';" 
   			 onclick="openApplyBill('<s:property value="#ab[0].initIsNoContract"/>','<s:property value="#ab[0].billApplyId"/>')">
   		<td><div align="right"><s:property value="#ab[2]"/></div></td> 
   		<td><div align="left">
        	<s:iterator id="s" value="itemNoList.get(#ab[0].billApplyId)"  status="stat2">    
	            	<s:property value="s"/><s:if test="!#stat2.last"><br/></s:if>
	        </s:iterator></div></td> 
        <td><div align="right"><s:property value="#ab[0].billAmountTax"/></div></td> 
        <td><div align="left"> 
        <s:if test="#ab[0].isNoContract">未签申请</s:if>
         <s:else>已签申请</s:else>
         </div></td> 
         <td><div align="left"> <s:property value="typeManageService.getYXTypeManage(1012,#ab[0].billNature).typeName" /></div></td>
         <td><div align="left"> <s:property value="typeManageService.getYXTypeManage(1004,#ab[0].billType).typeName" /></div></td> 
         <td><div align="left"><s:property value="#ab[0].billContent"/></div></td> 
		 <td><div align="left"><s:property value="#ab[0].returnReason"/></div></td>
		 <td><div align="left"><s:property value="#ab[1]"/></div></td>
	</tr>
</s:iterator>             
</table>

<table cellSpacing=1 cellPadding=2 width="100%" border=0>
	<tr>
		<td class="bg_table04"><baozi:pages value="info"  beanName="info" formName="forms(0)" /></td>
	</tr>
</table>

</s:form>

</body>
</html>
