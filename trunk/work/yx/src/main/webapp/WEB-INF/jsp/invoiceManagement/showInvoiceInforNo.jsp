<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>

<html>
<head>
<title><s:property value="applybill.billApplyNum"/>&nbsp;开票申请明细</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/casemoney.js" type="text/javascript"></script>
<script type="text/javascript">

	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	
	function asd()
	{
		document.getElementById('capitalizationY').innerHTML = Chinese(parseFloatNumber(document.getElementById('lowerY').value));
		document.getElementById('capitalizationN').innerHTML = Chinese(parseFloatNumber(document.getElementById('lowerN').value));
	}

	function printList()
	{
		var aid=document.forms(0).hiddenid.value;
		window.open("applyBillPDF.action?method=applyBillJaspser&paramId="+aid,"","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
	}
	function downLoadApplyBill(){
		var aid=document.forms(0).hiddenid.value;
		var url="applyBillPDF.action?method=downLoadApplyBill&paramId="+aid;
		downFrame.location=url;
	}
	//确认通过
function aaa() 
{ 
var aid=document.forms(0).hiddenid.value;
var act="pass";
location.href="/yx/billtoReceipt/billApplyVerify.action?method=verifyState&&billApplyId="+aid+"&fowardType=parents"; 
} 
//确认退回
function ccc() 
{ 
 	var aid=document.forms(0).hiddenid.value;
	location.href="/yx/billtoReceipt/billApplyVerifyQuery.action?method=returnReason&billApplyId="+aid; 
} 
</script>
</head>
<body  style="margin: 0px;" >
<iframe id="downFrame" style="display: none;"></iframe>
<div align="left" style="color:#000000">&nbsp;
  <p align="center" class="STYLE2 STYLE5"><span class="STYLE5">
  <s:property value="applybill.billApplyNum"/>开票申请明细</span></p>
</div>
<s:form action="createInvoice" theme="simple" validate="true">
	<s:hidden id="hiddenids" name="hiddenid" />
	<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr class="">
					<td  width="10%" align="right">申请部门：</td>
					<td  width="20%" align="left"><s:property value="position" /></td>
					<td  width="10%" align="right">申请人：</td>
					<td  width="25%" align="left"><s:property value="name" /></td>
					<td  width="10%" align="right">申请日期：</td>
					<td  width="25%" align="left" id="datew"><s:property value="applybill.applyId" /></td>
				</tr>

				<tr>
					<td align="right" nowrap class="">合同客户：</td>
					<td align="left"><s:property value="hetongkehu" /></td>
					<td align="right" class="">合同名称：</td>
					<td align="left" colspan="2" ><s:property value="applybill.contactName" /></td><td colspan="2" align="right">&nbsp;</td>
				</tr>
				
				<tr>
					<td align="right" nowrap>开票单位名称：</td>
					<td align="left"><s:property value="yxClientCode.billName" /></td>
					<td align="right">地址：</td>
					<td align="left" id="address"><s:property value="yxClientCode.billAdd" /></td>
					<td align="right" nowrap >联系电话：</td>
					<td align="left"  id="tax"><s:property value="yxClientCode.billPhone" /></td>
				</tr>
				<tr>
					<td align="right" >开户银行：</td>
					<td align="left"  id="bank"><s:property value="yxClientCode.billBank" /></td>
					<td  align="right">帐号：</td>
					<td  align="left" id="zhanghao"><s:property value="yxClientCode.account" /></td>
					<td align="right" >税号：</td>
					<td align="left"  id="shuihao">
					<s:property value="yxClientCode.taxNumber" /></td>
				</tr>

				<tr>
					<td  align="right">开票性质：</td>
					<td align="left" >
					<s:property value="typeManageService.getYXTypeManage(1012,invoiceNature).typeName "/>
					</td>
					<td  align="right">票据类型:</td>
					<td align="left"  id="piaotype">
					<s:property value="typeManageService.getYXTypeManage(1004,invoiceType).typeName"/>
					</td>
					<td colspan="2" align="right">&nbsp;</td>
				</tr>
				<tr>
					<td  align="right">申请金额：</td>
					<td align="left"><s:property value="money"/><s:hidden name="money" id="money"/></td>
					<td align="right" nowrap >基准：</td>
					<td  align="left"  id="base">
						<s:if test="base==0">
							<s:label id="hastax" value="不含税"/>
						</s:if>
						<s:else>
							<s:label id="hastax" value="含税"/>
					</s:else></td>
					<td colspan="2" align="right">&nbsp;</td>
				</tr>
				
				<tr>				
					<td colspan="3" align="left" >发票金额小写(含税):
						<s:property value="lowercaseMoney"/>
						<s:hidden name="lowercaseMoney" id="lowerY"/>
					<td colspan="3" align=""left"" >发票金额小写(不含税)：
						<s:property value="lowercaseMoneyN"/>
						<s:hidden name="lowercaseMoneyN" id="lowerN"/>
					</td>
				</tr>
				<tr>	
					<td  colspan="3" align="left">			
					<div align="left">发票金额大写(含税)：
					<label id="capitalizationY"></div>
					</td>		
					<td colspan="3" align="left">发票金额大写(不含税)：
					<label  id="capitalizationN"></div>
					</td>
				</tr>
				<tr>	
					<td align="right" >一次出库:</td>
					<td  align="left" ><s:if
						test="ischu">
						&radic;		
					</s:if> <s:else>
						&times;
					</s:else></td>
					<td align="right" >
					<div align="right">取票地点：</div>
					</td>
					<td  align="left" >
					<s:property value="billSpot"/>
					</td>
					<td align="right" >库存组织：</td>
					<td   align="left" >
					 <s:property value="typeManageService.getYXTypeManage(1021,stockOrg).typeName "/>
					</td>
				</tr>

				<tr>
					<td align="right" >开票内容：</td>
					<td colspan="5" align="left" >
					<s:property value="invoiceContent"/>
					</td>					
				</tr>

				<tr>
					<td align="right" >备注：</td>
					<td colspan="3" align="left" ><s:property value="remarks"/></td>
					<td  align="right" nowrap>甲方接收人：</td> 
      				<td colspan="3">
      			 		<s:property   value="applybill.firstReceiveMan" />
     				 </td>
				</tr>
			<s:if test="applyBillState == 4">
				<tr>
					<td align="right" >退回理由：</td>
					<td colspan="3" align="left" >
						<s:property value="returnReason"/>
					</td>
					<td align="right" >退回日期：</td>
					<td colspan="3" align="left" >
						<s:date name="returnDate" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
			</s:if>
			</table>
			
			<br/>
			<table width="100%" border="1" class="bg_table02" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr class="bg_table01">
					<td width="11%" align="right" >
					<div align="center">申购关联：</div>
					</td>
					<td width="15%" align="left" >
					<div align="center">申购单号</div>
					</td>
					<td width="58%" align="right" >
					<div align="center">申购内容</div>
					</td>
					<td width="58%" align="right" >
						&nbsp;
					</td>
				</tr>
						<s:iterator value="messageList">
				<tr class="bg_table02">
							<td align="left" >
								&nbsp;
							</td>
							<td align="left" >
								<div align="center"><s:property value="applyId"/></div>
							</td>
							<td align="left" >
								<div align="center"><s:property value="applyContent"/></div>
							</td>
							<td align="left" >
								&nbsp;
							</td>
						</tr>
				</s:iterator>
			</table>
			<br/>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td align="center">
					<s:if test="opType==01">
					<input type="button" name="SearchBtn" value="通  过" onClick="javascript:aaa()" class="button01">
             		 <input type="button" name="SearchBtn2" value="退  回" onClick="javascript:ccc()" class="button01">
					</s:if>
					<input type="button" name="button3" id="button3" value="打印申请单" class="button01" onclick="printList();">
					<input type="button" name="button3" id="button3" value="下载申请单" class="button01" onclick="downLoadApplyBill();">
					<s:if test="hasInvoice">
    					<input type="button" value="打印签收单" onclick="printQianshou();" class="button01">
      			 </s:if>
					<input type="button" name="button4" id="button4" value="关闭" class="button01"  	 onClick="window.close();">
					</td>
				</tr>
			</TABLE>

</s:form>
</body>
<script type="text/javascript">
	asd();
	
	// 打印签收单
function printQianshou()
 {
	var aid=document.forms(0).hiddenid.value;
	window.open("billAndInvoicePDF.action?method=BillAndInvoice&paramId="+aid,"","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
 }
</script>
</html>