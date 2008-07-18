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
		document.getElementById('capitalizationY').value=Chinese(parseFloatNumber(document.getElementById('lowerY').value));
		document.getElementById('capitalizationN').value=Chinese(parseFloatNumber(document.getElementById('lowerN').value));
	}
	
	function show(){
		var g=new Date();
		var y=g.getYear();
		var m=g.getMonth();
		var d=g.getDate();
		document.getElementById("datew").innerHTML=y+"年"+(m+1)+"月"+d+"日";
	}
	function printList()
	{
		var aid=document.forms(0).hiddenid.value;
		window.open("applyBillPDF.action?method=applyBillJaspser&paramId="+aid,"","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
	}
</script>
</head>
<body  style="margin: 0px;" >
<div align="left" style="color:#000000">&nbsp;
  <p align="center" class="STYLE2 STYLE5"><span class="STYLE5">
  开票申请明细</span></p>
</div>
<s:form action="createInvoice" theme="simple" validate="true">
	<s:hidden id="hiddenids" name="hiddenid" />
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
					<td colspan="7" align="right" class="bg_table01" height="3"></td>
				<tr class="bg_table02">
					<td width="7%" align="right">申请部门：</td>
					<td width="25%" align="left"><s:property value="position" /></td>
					<td width="15%" align="right">申请人：</td>
					<td width="19%" align="left"><s:property value="user.name" />
					</td>
					<td align="right">申请日期：</td>
					<td width="18%" colspan="2" align="left" id="datew"><s:property
						value="applybill.applyId" /></td>
				</tr>
				<tr>
					<td colspan="7" align="right" nowrap class="bg_table02">
					<hr>
					</td>
				</tr>
				<tr>
					<td align="right" nowrap class="bg_table02">合同客户：</td>
					<td class="bg_table02" align="left"><s:property
						value="hetongkehu" /></td>
					<td align="right" class="bg_table02">合同名称：</td>
					<td class="bg_table02" align="left"><s:property
						value="applybill.contactName" /></td>
					<td colspan="3" align="right" class="bg_table02">&nbsp;</td>
				</tr>
				<tr>
					<td align="right" class="bg_table02">开票单位名称：</td>
					<td class="bg_table02" align="left"><s:property
						value="yxClientCode.billName" /></td>
					<td class="bg_table02" align="right">地址：</td>
					<td class="bg_table02" align="left" id="address"><s:property
						value="yxClientCode.billAdd" /></td>
					<td align="right" nowrap class="bg_table02">联系电话：</td>
					<td colspan="2" align="left" class="bg_table02" id="tax"><s:property
						value="yxClientCode.billPhone" /></td>
				</tr>
				<tr>
					<td align="right" nowrap class="bg_table02">开户银行：</td>
					<td align="left" class="bg_table02" id="bank"><s:property
						value="yxClientCode.billBank" /></td>
					<td class="bg_table02" align="right"><span class="STYLE3">帐号：</span>
					</td>
					<td class="bg_table02" align="left" id="zhanghao"><s:property
						value="yxClientCode.account" /></td>
					<td align="right" class="bg_table02">税号：</td>
					<td colspan="2" align="left" class="bg_table02" id="shuihao">
					<s:property value="yxClientCode.taxNumber" /></td>
				</tr>

				<tr>
					<td colspan="7" align="right" class="bg_table02">
					<hr>
					</td>
				</tr>

				<tr>
					<td class="bg_table02" align="right">开票性质：</td>
					<td align="left" class="bg_table02"><s:property
						value="invoiceNature" /></td>
					<td align="right" class="bg_table02">
					<div align="right">发票金额小写(含税):</div>
					</td>
					<td align="left" class="bg_table02">
					<div align="left"><s:textfield id="lowerY"
						name="lowercaseMoney" readonly="true"  disabled="true"></s:textfield>
					</div>
					</td>
					<td align="right" class="bg_table02">发票金额小写(不含税)：</td>
					<td colspan="2" align="left" class="bg_table02"><s:textfield
						id="lowerN" name="lowercaseMoneyN" readonly="true"  disabled="true"></s:textfield>
					</td>
				</tr>
				<tr>
					<td class="bg_table02" align="right">金额：</td>
					<td align="left" class="bg_table02"><s:textfield name="money"
						id="money" readonly="true"  disabled="true"></s:textfield></td>
					<td class="bg_table02" align="right">
					<div align="right">发票金额大写(含税)：</div>
					</td>
					<td class="bg_table02" align="left">
					<div align="left"><input name="code2" id="capitalizationY"
						type="text"  value="" readonly="readonly"  disabled="true"></div>
					</td>
					<td align="right" class="bg_table02">发票金额大写(不含税)：</td>
					<td colspan="2" align="left" class="bg_table02"><input
						name="code6" id="capitalizationN" type="text" 
						value="" readonly="readonly"  disabled="true"/></td>
				</tr>
				<tr>
					<td class="bg_table02" align="right">票据类型:</td>
					<td align="left" class="bg_table02" id="piaotype"><s:property value="invoiceType" /></td>
					<td align="right" nowrap class="bg_table02">基准：</td>
					<td class="bg_table02" align="left"  id="base">
						<s:if test="base==0">
							<s:label id="hastax" value="不含税"/>
						</s:if>
						<s:else>
							<s:label id="hastax" value="含税"/>
						</s:else></td>
					<td align="right" class="bg_table02">一次出库:</td>
					<td colspan="2" align="left" class="bg_table02"><s:if
						test="ischu">
						&radic;		
					</s:if> <s:else>
						&times;
					</s:else></td>
				</tr>

				<tr>
					<td rowspan="2" align="right" class="bg_table02">开票内容：</td>
					<td rowspan="2" align="left" class="bg_table02"><s:textarea
						name="invoiceContent" cols="25" rows="4" readonly="true"  disabled="true"> </s:textarea></td>
					<td rowspan="2" align="right" class="bg_table02">
					<div align="right"></div>
					</td>
					<td rowspan="2" align="left" class="bg_table02">
					<div align="left"></div>
					</td>
					<td width="16%" align="right" class="bg_table02">库存组织：</td>
					<td colspan="2" align="left" class="bg_table02">
					 <s:property value="typeManageService.getYXTypeManage(1021,stockOrg).typeName "/>
					</td>
				</tr>
				<tr>
					<td align="right" class="bg_table02">
					<div align="right">取票地点：</div>
					</td>
					<td colspan="2" align="left" class="bg_table02">
					<s:property value="billSpot"/> 
				</tr>
				<tr>
					<td align="right" class="bg_table02">备注：</td>
					<td colspan="6" align="left" class="bg_table02"><s:textarea
						name="remarks" readonly="true"  disabled="true"></s:textarea></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="1"
				class="bg_white" align="center">
				<tr>
					<td colspan="7" align="right" class="bg_table02">
					<hr>
					</td>
				</tr>
				<tr>
					<td width="11%" align="right" class="bg_table02">
					<div align="center">申购关联：</div>
					</td>
					<td width="15%" align="left" class="bg_table02">
					<div align="center">申购单号</div>
					</td>
					<td width="58%" align="right" class="bg_table02">
					<div align="center">申购内容</div>
					</td>
					<td width="58%" align="right" class="bg_table02">
						&nbsp;
					</td>
				</tr>
						<s:iterator value="messageList">
						<tr>
							<td align="left" class="bg_table02">
								&nbsp;
							</td>
							<td align="left" class="bg_table02">
								<div align="center"><s:property value="applyId"/></div>
							</td>
							<td align="left" class="bg_table02">
								<div align="center"><s:property value="applyContent"/></div>
							</td>
							<td align="left" class="bg_table02">
								&nbsp;
							</td>
						</tr>
				</s:iterator>
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td align="center"><input type="button" name="button3" id="button3" value="打印申请单"
						class="button01" onclick="printList();">

					<input type="button" name="button4" id="button4" value="关闭" class="button01"  	 onClick="window.close();">

					</td>
				</tr>
			</TABLE>
			</td>
		</tr>
	</table>
</s:form>
</body>
<script type="text/javascript">
	asd();
</script>
</html>
