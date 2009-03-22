<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>开票申请确认</title>

<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style>
</head>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<body>
 <s:form action="billApplyVerifyQuery" theme="simple">
<div align="left" style="color:#000 ">
  当前页面：开票管理 -> 开票申请确认
</div>
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
        <tr>
          <td align="right" class="bg_table01"  height="3"><img src="./../images/temp.gif" width="1" height="1"></td>
        </tr>
        <tr class="bg_table02">
          <td align="right" class="bg_table03"  ><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;销售员：          
              <s:select name="expId" id="selectId" onchange="" list="listExp" listKey="id" listValue="name" required="true" 
              headerKey="" headerValue="全部"
              onchange="javascript:queryByExp();">
              </s:select>
				&nbsp;&nbsp;
			  <input type="button" name="SearchBtn" value="通  过" onClick="javascript:aaa()" class="button01">
              <input type="button" name="SearchBtn2" value="退  回" onClick="javascript:confirmReturn()" class="button01">
			  <input type="button" name="update" value="修改备注" onClick="javascript:updateRemark()" class="button01"/>
              
          </div></td>
        </tr>
      </table>

      <table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" id="billApplySure" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr align="center">
          <td width="6%" class="bg_table01"><div align="center">选择</div></td>
      <%--    <td width="9%" class="bg_table01"><div align="center">开票申请编号</div></td> --%> 
          <td nowrap width="11%" class="bg_table01"><div align="center">合同号</div></td>
          <td nowrap width="11%" class="bg_table01"><div align="center">项目号</div></td>
          <td nowrap width="11%" class="bg_table01"><div align="center">申请日期</div></td>
          <td nowrap width="10%" class="bg_table01"><div align="center">申请金额</div></td>
          <td nowrap width="11%" class="bg_table01"><div align="center">申请类型</div></td>
          <td nowrap width="10%" class="bg_table01"><div align="center">开票性质</div></td>
          <td nowrap width="13%" class="bg_table01"><div align="center">票据类型</div></td>
          <td nowrap width="11%" class="bg_table01"><div align="center">甲方接收人</div></td>
          <td nowrap width="22%" class="bg_table01"><div align="center">开票内容</div></td>
          <td nowrap width="8%" class="bg_table01"><div align="center">申请人</div></td>
        </tr>
        
        <s:iterator id="result" value="info.result">
		<input type="hidden" name="isNoCon<s:property value="#result[0].billApplyId"/>" value="<s:property value="#result[0].initIsNoContract"/>" />
        <tr align="center" onMouseOver="this.bgColor='#BBBBFF'; " onMouseOut="this.bgColor='#FFFFFF';">
          <td ><div align="center">
           <input type="checkbox" name="billApplyId" value="<s:property value="#result[0].billApplyId"/>" />
          </div></td>
          		<s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')">
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')">
				</s:elseif >
				<s:else><td></s:else>
        		  <div align="left"><s:property value="#result[2]"/></div></td>
        		  
        		  <s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')">
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')">
				</s:elseif >
				<s:else><td></s:else>
        		  <div align="left">
        		  <s:iterator id="s" value="itemNoList.get(#result[0].billApplyId)"  status="stat2">    
	            			<s:property value="s"/><s:if test="!#stat2.last"><br/></s:if>
	           		 </s:iterator></div></td>
          	<s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')">
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')">
				</s:elseif >
				<s:else><td></s:else>
        <%--   <div align="center"><s:property value="#result[0].billApplyNum"/></div></td>
          	<s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')">
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')">
				</s:elseif >
				<s:else><td></s:else>   --%> 
          <div align="center"><s:date name="#result[0].applyId" format="yyyy-MM-dd HH:mm"/></div></td>
          	<s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')">
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')">
				</s:elseif >
				<s:else><td></s:else>
          <div align="right"><s:property value="#result[0].billAmountTax"/></div></td>
<!--          <td ><div align="center"><s:property value="#result[0].isNoContract"/></div></td>-->
          	<s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')">
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')">
				</s:elseif >
				<s:else><td></s:else>
          <s:if test="#result[0].isNoContract">
            未签申请</s:if>
          <s:else>已签申请
          </s:else>
          </td>
          	
          	<s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')"><div align="left">
				<s:property	value="typeManageService.getYXTypeManage(1012,#result[0].billNature).typeName" />
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')"><div align="left">
					<s:property	value="typeManageService.getYXTypeManage(1012,#result[0].billNature).typeName" />
				</s:elseif >
				<s:else><td></s:else>
          </div></td>
          
          
          	<s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')">
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')">
				</s:elseif >
				<s:else><td></s:else>
          <div align="left">
          <s:property value="typeManageService.getYXTypeManage(1004,#result[0].billType).typeName" />
          </div></td>
         	<s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')">
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')">
				</s:elseif >
				<s:else><td></s:else>
				
          <div align="left"><s:property value="#result[0].firstReceiveMan"/></div></td>
          	<s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')">
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')">
				</s:elseif >
				<s:else><td></s:else>
          <div align="left"><s:property value="#result[0].billContent"/></div></td>
          	<s:if test="#result[0].initIsNoContract==1">
				<td	 onclick="openOneInvoice('<s:property value="#result[0].billApplyId"/>')">
				</s:if>
				<s:elseif  test="#result[0].initIsNoContract==0">
					<td onclick="openOneInvoiceHasContract('<s:property value="#result[0].billApplyId"/>')">
				</s:elseif >
				<s:else><td></s:else>
          <div align="left"><s:property value="#result[1]" /></div></td>
        </tr>
       </s:iterator> 
     </table>
     
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			
</s:form>
</body>
<s:if test="succSave == 6">
	<script language="javascript"> 
		alert("修改已保存!");
	</script>
</s:if>
</html>

<script language="javascript">
	//确认通过
	function aaa() 
	{ 
		var checkArr=document.getElementsByName("billApplyId");
	    var checkStr="";
	    var flag = 0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	          checkStr=checkStr+","+checkArr[i].value;
	          flag++;
	        }
	    }
	    if(flag>0){
	    	if(confirm("确定要通过申请确认吗?")){
				location.href="../billtoReceipt/billApplyVerify.action?method=verifyState&"+$(billApplyVerifyQuery).toQueryString();
			}
	    }
	    else{
	    	alert("请选择一个申请单确认!");
	    }
	} 
	//确认退回
	function confirmReturn() 
	{ 
	  var checkArr=document.getElementsByName("billApplyId");
	    var checkStr="";
	    var flag=0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	          checkStr=checkStr+","+checkArr[i].value;
	          flag++;
	        }
	    }
	    if(flag==1){
	    		openWin("/yx/billtoReceipt/billApplyVerifyQuery.action?method=returnReason&billApplyId="+checkStr.substring(1),400,300);
				//location.href="../billtoReceipt/billApplyVerify.action?method=verifyStateT&"+$(billApplyVerifyQuery).toQueryString(); 
		}else{
			alert("请选择一个申请单确认！");
		}
	} 
	//修改备注
	function updateRemark() 
	{ 
	  var checkArr=document.getElementsByName("billApplyId");
	    var checkStr="";
	    var flag=0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	          checkStr=checkStr+","+checkArr[i].value;
	          flag++;
	        }
	    }
	    if(flag==1){
	    		var isCon = "isNoCon"+checkStr.substring(1);
	    		if($(isCon).value == 0){
	    			openWin("/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=showUpdateRemark&applyBillSid="+checkStr.substring(1),800,600);
				}
				else{
	    			openWin("/yx/invoice/createInvoice.action?method=showInvoiceInfor&isCon=true&applyBillId="+checkStr.substring(1),800,600);
				}
				//location.href="../billtoReceipt/billApplyVerify.action?method=verifyStateT&"+$(billApplyVerifyQuery).toQueryString(); 
		}else{
			alert("请选择一个申请单确认！");
		}
	} 

function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
 
 function queryByExp(){
    var expId=document.getElementById("selectId").value;
   // alert(expId);
	location.href="../billtoReceipt/billApplyVerifyQuery.action?expId="+expId+"&resetCondition=true"; 
}
	//已签开票申请open
	function openOneInvoiceHasContract(oid)
	{
	   window.open("/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=showInvoice&applyBillSid="+oid+"&opType=01","","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=850");
 			//window.open("/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=showInvoice&applyBillSid="+oid+"&opType=01");
	}
			
	//未签开票申请open
	function openOneInvoice(oid)
	{
	  window.open("/yx/invoice/createInvoice.action?method=showInvoiceInfor&applyBillId="+oid+"&opType=01","","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=850");
	 //window.open("/yx/invoice/createInvoice.action?method=showInvoiceInfor&applyBillId="+oid+"&opType=01");
	}
	function reflushPage()
	{
		location.href = "/yx/billtoReceipt/billApplyVerifyQuery.action";
	}
</script>
