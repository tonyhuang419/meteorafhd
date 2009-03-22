<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>开票申请明细</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/casemoney.js" type="text/javascript"></script>
<script type="text/javascript">
	//确认通过
	function aaa() 
	{ 
		var aid=document.forms(0).hiddenId.value;
		location.href="/yx/billtoReceipt/billApplyVerify.action?method=verifyState&billApplyId="+aid+"&fowardType=parents"; 
	} 
	//确认退回
	function ccc() 
	{ 
		var aid=document.forms(0).hiddenId.value;
		location.href="/yx/billtoReceipt/billApplyVerifyQuery.action?method=returnReason&billApplyId="+aid; 
	} 

</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.tdZero{
	padding:0px 0px 0px 0px;
}
-->
</style>
</head>
<body  leftmargin="0">
<div align="left" style="color:#000000">&nbsp;
  <p align="center">
  <s:property value="applybill.billApplyNum"/>&nbsp;开票申请明细</p>
</div>
<iframe id="downFrame" style="display:none"></iframe>
<s:form theme="simple" >
<s:hidden name="hiddenId"/>
<br/>
<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style=" border-collapse: collapse;">
  <tr >
    <td  width="10%" align="right"><div align="right">申请部门： </div></td>
    <td  width="20%" align="left" ><div align="left">
       <s:property value="applyDept"/>
    </div></td>
    <td  width="10%"   align="right" ><div align="right">申请人： </div></td>
    <td  width="25%"  align="right" ><div align="left">
        <s:property value="applyName"/>
    </div></td>
    <td  width="10%"    align="right" ><div align="right">申请日期： </div></td>
    <td  width="25%"  align="right" ><div align="left">
        <s:date name="applybill.applyId" format="yyyy-MM-dd" />
      </div></td>
  </tr>

  <tr>
    <td align="right" ><div align="right">单位名称： </div></td>
    <td  align="left" ><div align="left">
    <s:property value=" billClient.billName"/>
    </div></td>
    <td align="right" ><div align="right">地址： </div></td>
    <td  align="right" ><div align="left">
	<s:property value=" billClient.billAdd"/>
	</div></td>
    <td align="right" nowrap ><div align="right">联系电话： </div></td>
    <td  align="right" ><div align="left">
<s:property value=" billClient.billPhone"/>
 </div></td>
  </tr>
  <tr>
    <td   align="right" nowrap ><div align="right">开户银行： </div></td>
    <td  align="left" ><div align="left">
<s:property value="billClient.billBank"/>
</div></td>
    <td align="right"  ><div align="right">帐号：</div></td>
    <td   align="right"  ><div align="left">
<s:property value=" billClient.account"/>
</div></td>
    <td align="right"  ><div align="right">税号：</div></td>
    <td  align="right"  ><div align="left">
<s:property value="billClient.taxNumber"/>
</div></td>
</tr>
<tr><td colspan="6" class="tdZero">
<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style=" border-collapse: collapse;"  frame="void">
<s:if test="rcPlanList!=null">
  <tr>
    <td align="right" ><div align="right">合同号：</div></td>
    <td align="left" ><div align="left">
    <s:property value="foramlContractService.getConSn(applybill.contractMainInfo)"/>
    </div></td>
    <td  align="left" ><div align="right">合同名称：</div></td>
    <td colspan="5" align="left" >
	<s:property value="foramlContractService.getConName(applybill.contractMainInfo)"/></td>
  </tr>
 <s:if test="applybill.initIsNoContract == 1"> <%-- 未签合同--%>
  <tr>
	<td align="right" ><div align="right">项目号： </div></td>
	<td align="left" ><div align="left">
	<s:property value="itemName"/>
	</div></td>
	<td align="right" ><div align="right">申请金额：</div></td>
	<td   align="right" ><div align="left">
     <s:property value="applyAmount"/>
    </div></td>
    <td colspan="3"  align="right" >&nbsp;</td>
  </tr>
 </s:if>
 
  <s:elseif test="applybill.initIsNoContract == 0">  <%--已签合同 --%> 
		   <s:if test=" applybill.applyWay ==  1">  <%--已签开票、合并开票--%>
			<s:iterator id="rc" value="rcPlanList"  status="stats">    <%--是个map--%>
			  <tr>
				<td ><div align="right">项目号： </div></td>
				<td ><div align="left">
				<s:property value="invoiceService.getCimiName(key)"/>
				</div></td>
				<td><div align="right">开票金额：</div></td>
				<td><div align="left">
			     <s:property value="value"/>
			    </div></td>
			    <s:if test=" applybill.applyWay !=  2 ">  <%--收据开票--%>
  						<td align="right">开票性质：</td>
  						<td align="left">
  						<s:if test="applybill.applyWay!=2">
  							 <s:property value="typeManageService.getYXTypeManage(1012,invoiceService.getBillTypeFromRCPlan(key)).typeName "/>
  						 </s:if></td>
 				 </s:if>
 				<td><div align="right">阶段名称：</div></td>
 				<td><div align="left"><s:property value="invoiceService.getStageByRCPlan(key)"/></div></td>
			  </tr>
			</s:iterator>
			</s:if>
	
		<s:elseif test=" applybill.applyWay ==  0  ||  applybill.applyWay ==  2">  <%--收据开票, 预决算--%>		
		<s:iterator  id="rp" value="rcPlanList" status="s" >
		<tr>
			<td align="right" ><div align="right">项目号： </div></td>
			<td align="left" ><div align="left">
	    	<s:property value="invoiceService.getCimiName(key)" />
	 		</div></td>
	 		<td align="right">开票性质：</td>
  			<td align="left">
  			<s:if test="applybill.applyWay!=2">
  				 <s:property value="typeManageService.getYXTypeManage(1012,invoiceService.getBillTypeFromRCPlan(key)).typeName "/>
  			 </s:if>
  		 </td>
	 	 <td  align="right">阶段名称：</td>
	 	 <td  align="left" ><s:property value="invoiceService.getStageByRCPlan(key)"/></td>
	 	 </tr>
	 	</s:iterator>
		
		</s:elseif>
 </s:elseif>
</s:if>
</table>
</td></tr>
  <tr>
    <td align="right" ><div align="right">票据类型：</div></td>
    <td align="left" ><div align="left">
     <s:if test="applybill.applyWay!=2">
        <s:property value="typeManageService.getYXTypeManage(1004,applybill.billType).typeName"/>
      </s:if>
      <s:else>
      	收据
      </s:else>
      </div></td>
   
   <td align="left" ><div align="right">基准：</div></td>
    <td  colspan="3"  align="left" ><div align="left">
       <s:if test="applybill.base==0">
			<s:label id="hastax" value="不含税"/>
		  </s:if>
			<s:else>
				<s:label id="hastax" value="含税"/>
		</s:else>
      </div></td>
     </tr>
 
   <tr>
    <td colspan="3" align="left" ><div align="left">发票金额小写(含税)： 
    	<s:label id="taxmoney" name="applybill.billAmountTax"/>  
    </td>
    <td colspan="3"  align="right" ><div align="left">发票金额小写(不含税)： 
       <s:label id="notaxmoney" name="applybill.billAmountNotax"/>  
     <s:property value=""/>
     </td>
  </tr>
  <tr>
    <td  align="right" colspan="3" ><div align="left">发票金额大写(含税)：  <s:label id="bigMoney"/>  </td>
    <td  align="right" colspan="3" ><div align="left">发票金额大写(不含税)：<s:label id="bigMoneyN"/> </td>
  </tr>
  <tr>
    <td  align="right" ><div align="right">一次出库：</div></td>
    <td  align="left"><s:if	test="applybill.oneOut"> &radic; </s:if>
      <s:else> &times; </s:else></td>
         <td  align="right" ><div align="right">取票地点： </div></td>
    <td  align="left" ><div align="left">
        <s:property value="applybill.billSpot"/>
      </div></td>
    <td  align="right" ><div align="right">库存组织： </div></td>
    <td  align="left" ><div align="left">
     <s:property value="typeManageService.getYXTypeManage(1021,applybill.stockOrg).typeName"/>      
      </div></td>
  </tr>
  <tr>
  	 <td align="right" >开票内容：</td>
    <td  colspan="5" align="left" ><div align="left">
     	<s:property value="applybill.billContent"/>    
      </div></td>
	</tr>
  <tr>
    <td  align="right">备注：</td>
    <td colspan="3" align="left" >
      <s:property   value="applybill.remarks" />
      </td>
      <td  align="right"  nowrap>甲方接收人：</td> 
      <td>
       <s:property   value="applybill.firstReceiveMan" />
      </td>
  </tr>
 <s:if test="receiptList.size() > 0">
  <tr>
  <td colspan="6">
  	<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style=" border-collapse: collapse;">
			<tr>
				<td colspan="5" align="left">
					预收款信息:
				</td>
			</tr>
			<tr>
	  		 	<td></td>
	  		 	<td>收据金额</td>
	  		 	<td>开据日期</td>
	  		 	<td>收款金额</td>
	  		 	<td>收款日期</td>
			</tr>
		<s:iterator value="receiptList" id="invoice" >
	  		 <tr>
	  		 	<td></td>
	  		 	<td><s:property value="#invoice.billInvoiceAmount" /></td>
	  		 	<td><s:property value="#invoice.realNowBillDate" /></td>
	  		 	<td>
	  		 		<s:if test="#invoice.realArriveAmount != null ">
	  		 			<s:property value="#invoice.realArriveAmount" />
	  		 		</s:if>
	  		 		<s:else>
	  		 			0.00
	  		 		</s:else>
	  		 	</td>
	  		 	<td>
	  		 		<s:property value="#invoice.realNowReceDate" />
	  		 	</td>
			  </tr>
		 </s:iterator>
  	</table>
  </td>
  </tr>
 </s:if>
<s:if test="applybill.applyBillState == 4">
  <tr>
    <td  align="right"><div align="right">退回理由：</div></td>
    <td colspan="3" align="left" >
      <s:property value="applybill.returnReason" />
      </td>
    <td  align="right"><div align="right">退回日期：</div></td>
    <td colspan="3" align="left" >
      <s:date name="applybill.returnDate" format="yyyy-MM-dd HH:mm:ss" />
      </td>
  </tr>
  </s:if>
</table>

<br/>
<table width="100%" border="1" class="bg_table02" bordercolor="#808080" style=" border-collapse: collapse;">
  <tr class="bg_table01">
    <td width="14%" align="right" ><div align="center">申购关联：</div></td>
    <td width="10%" align="left" ><div align="center">项目号</div></td>
    <td width="11%" align="left" ><div align="center">申购单号</div></td>
    <td colspan="2" align="left" ><div align="center">申购内容</div></td>
  </tr>
  <s:iterator id="pl" value="messageList">
    <tr>
      <td align="right" ><div align="center"></div></td>
      <td align="left" ><div align="center">
          <s:property value="#pl.eventId"/>
        </div></td>
      <td align="left" ><div align="center">
          <s:property value="#pl.applyId"/>
        </div></td>
      <td colspan="2" align="left" ><div align="center">
          <s:property value="#pl.applyContent"/>
        </div></td>
    </tr>
  </s:iterator>
 </table>
 <br/>
 <table width="100%" border="0">
  <tr>
    <td colspan="7"><div align="center">
    	<s:if test="opType==01">
    		<input type="button" name="SearchBtn" value="通  过" onClick="javascript:aaa()" class="button01">
        	<input type="button" name="SearchBtn2" value="退  回" onClick="javascript:ccc()" class="button01">
    	</s:if>
    	<input type="button" value="打印申请单" onclick="printList();" class="button01"> 
    	<input type="button" value="下载申请单" onclick="downLoadAppybill();" class="button01">   	
    	<s:if test="hasInvoice">
    		<input type="button" value="打印签收单" onclick="printQianshou();" class="button01">
        </s:if>       
        <input type="button" name="button4" id="button4" value="关闭" class="button01"  onClick="window.close();">
      </div></td>
  </tr>


</s:form>


</body>
<script type="text/javascript">
function printList()
{
	var aid=document.forms(0).hiddenId.value;
	window.open("applyBillPDF.action?method=applyBillJaspser&paramId="+aid,"","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
}
// 打印签收单
function printQianshou()
 {
	var aid=document.forms(0).hiddenId.value;
	window.open("billAndInvoicePDF.action?method=BillAndInvoice&paramId="+aid,"","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
 }
 function downLoadAppybill()
 {
 	var aid=document.forms(0).hiddenId.value;
	downFrame.location="applyBillPDF.action?method=downLoadApplyBill&paramId="+aid;
 }
function change()
	{
		var a = document.getElementById('taxmoney').innerHTML ;
		var b = document.getElementById('notaxmoney').innerHTML ;

		document.getElementById('bigMoney').innerHTML= Chinese(eval(parseFloatNumber(a)));
		document.getElementById('bigMoneyN').innerHTML=Chinese(eval(parseFloatNumber(b)));
	}
change();
</script>
</html>
