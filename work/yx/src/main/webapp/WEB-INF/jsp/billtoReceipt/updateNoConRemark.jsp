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
	function save(){
		document.form(0).action.value = "/yx/contract/formalContractManage/formalContractInvoiceApply.action";
		document.form(0).submit();
	}
</script>
</head>
<body  style="margin: 0px;" >
<iframe id="downFrame" style="display: none;"></iframe>
<div align="left" style="color:#000000">&nbsp;
  <p align="center" class="STYLE2 STYLE5"><span class="STYLE5">
  <s:property value="applybill.billApplyNum"/>开票申请明细</span></p>
</div>
<s:form theme="simple"  action="/contract/formalContractManage/formalContractInvoiceApply.action">
<s:hidden name="method" value="updateRemark"></s:hidden>
	<s:hidden id="hiddenids" name="hiddenid" />
	<input type="hidden" name="aBillId" value="<s:property value="hiddenid"/>"/>
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
					<td colspan="5" align="left" >
						<s:textarea	cols="50" rows="4" name="remarks" value="%{applybill.remarks}"></s:textarea>
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
						<s:property value="returnDate"/>
					</td>
				</tr>
			</s:if>
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td align="center">
					<input type="submit" name="button3" id="button3" value=" 修  改 " class="button01" ">
					<input type="button" name="button3" id="button3" value=" 取  消 " class="button01" onclick="window.close();">
					</td>
				</tr>
			</TABLE>

</s:form>
</body>
<script type="text/javascript">
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