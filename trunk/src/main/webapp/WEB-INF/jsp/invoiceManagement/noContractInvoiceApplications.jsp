<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>

<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/casemoney.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/thousands.js" type="text/javascript"></script>
<script type="text/javascript">
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById('clientId').value = selObj.value;
	}
	
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
	
	function selectedClientY(clientObj){
		document.getElementById("clientNameY").value = clientObj.clientNameY;
		document.getElementById("clientIdY").value = clientObj.clientIdY;
		liandong("/yx/jsonData.action?method=doGetLinkMainOfUnits&unitsId="+clientObj.clientIdY);
	}
	
	function clientChangeY(selObj){
		document.getElementById('clientNameY').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById('clientIdY').value = selObj.value;
     	liandong("/yx/jsonData.action?method=doGetLinkMainOfUnits&unitsId="+selObj.value);
	}
	
	function changeBillType(obj){
		billType = obj.value;
		changeMoney("/yx/jsonData.action?method=doGetTax&billType=" + billType);
	}
	

	function changeMoney(jsonObjGetUrl){
		var jsonRequest = new Request.JSON({url:jsonObjGetUrl, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
					if(jsonObj.jsonData!=null){
						//alert(jsonObj.jsonData);
						document.getElementById("tax").value = jsonObj.jsonData;
						asd();
					}
			}
		}}).get({randerNum:Math.random()});	 	
	}
	
		
	function liandong(jsonObjGetUrl)
	{
			var jsonRequest = new Request.JSON({url:jsonObjGetUrl, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
					if(jsonObj.jsonData.billAdd!=null){
					document.getElementById('address').innerHTML=jsonObj.jsonData.billAdd;
					}else{document.getElementById('address').innerHTML="";}
					
					if(jsonObj.jsonData.billPhone!=null){
					document.getElementById('phone').innerHTML=jsonObj.jsonData.billPhone;
					}else{
					document.getElementById('phone').innerHTML="";
					}
					
					if(jsonObj.jsonData.billBank!=null){
					document.getElementById('bank').innerHTML=jsonObj.jsonData.billBank;}
					else{
					document.getElementById('bank').innerHTML="";
					}
					
					if(jsonObj.jsonData.account!=null){
					document.getElementById('zhanghao').innerHTML=jsonObj.jsonData.account;}
					else{
					document.getElementById('zhanghao').innerHTML="";
					}
					
					if(jsonObj.jsonData.taxNumber!=null){
					document.getElementById('shuihao').innerHTML=jsonObj.jsonData.taxNumber;}
					else{
					document.getElementById('shuihao').innerHTML="";
					}
			}
		}}).get({randerNum:Math.random()});	 	
	}
	
	function asd()
	{
		//var d=document.getElementById('piaotype').value;
		
		var b = document.getElementById("tax").value;
		var c = document.getElementById('base').value;
		var moneyx = document.getElementById('money').value;
		
		if(moneyx!=null && moneyx !="" && moneyx != 0){
			if(c==1)
			{
			document.getElementById('lowerY').value= Math.round(  parseFloatNumber(moneyx) * 100 )/100 ;
			document.getElementById('capitalizationY').value=Chinese(document.getElementById('lowerY').value);
			document.getElementById('lowerN').value=Math.round( document.getElementById('lowerY').value/b*100 ) /100;
			document.getElementById('capitalizationN').value=Chinese(document.getElementById('lowerN').value);
			}
			else if(c==0)
			{
			document.getElementById('lowerN').value= Math.round( parseFloatNumber(moneyx) * 100)/100;
			document.getElementById('capitalizationN').value=Chinese(document.getElementById('lowerN').value);
			document.getElementById('lowerY').value=Math.round(document.getElementById('lowerN').value*b*100)/100;
			document.getElementById('capitalizationY').value=Chinese(document.getElementById('lowerY').value);
			}

			document.getElementById('lowerY').value = formatNumber(document.getElementById('lowerY').value,'#,###.0#');
			document.getElementById('lowerN').value = formatNumber(document.getElementById('lowerN').value,'#,###.0#');
			
		}
		else{
			document.getElementById('lowerN').value= 0;
			document.getElementById('capitalizationN').value="零";
			document.getElementById('lowerY').value= 0;
			document.getElementById('capitalizationY').value="零";
		}
	}

	function openUrl()
	{
		window.open('createInvoice.action?method=openAssociation','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');
	}
	function refreshSuper()	
	{
		document.forms(0).action="createInvoice.action?method=doDefaultT";
		document.forms(0).submit();
	}
	function deleter(a)
	{
		document.forms(0).action="createInvoice.action?method=applicationsDelete&deleteId="+a;
		document.forms(0).submit();
	}
	function saveuu(type)
	{	 
		if(type == 0)
		{
			document.forms(0).action="createInvoice.action?method=saveInvoice";
			document.forms(0).submit();
		}else if(type == 1){
			document.forms(0).action="createInvoice.action?method=saveInvoiceAndApplications";
			document.forms(0).submit();
		}
	}
	function show(){
	var g=new Date();
	var y=g.getYear();
	var m=g.getMonth();
	var d=g.getDate();
	document.getElementById("datew").innerHTML=y+"年"+(m+1)+"月"+d+"日";
	var ff=document.getElementById("fff").value;
//	if(ff=="1"){
//		alert("保存成功");
//		ff="0";
//	}
	}
	
	
</script>

<style type="text/css">
<!--
.STYLE1 {
	font-size: 16px;
	font-weight: bold;
}
-->
</style>

</head>

<s:if test="succSave == 0">
	<script language="javascript"> 
		alert("保存成功");
	</script>
</s:if>
<s:elseif test="succSave == 1">
	<script language="javascript"> 
		alert("提交成功");
	</script>
</s:elseif>

<body style="margin: 0px;">
<br>
	<div  style="color:#000000">
		<p align="center" class="STYLE1">
			未签合同开票申请
		</p>
	</div>
	<iframe name="errorsFrame" frameborder="0"	framespacing="0" height="0" scrolling="no"></iframe>
	<s:form id="noConApplyBill" action="createInvoice" theme="simple" validate="true">
	<input type="hidden" id="fff" value="<s:property value="success"/>" />
	<input type="hidden" id="tax" value="" />
		<table width="100%" border="0" cellspacing="1" cellpadding="1"
			class="bg_white" align="center">
			<tr>
				<td align="center">
					<table align="center" border=0 cellpadding=1 cellspacing=1
						width="100%">
						<tr>
							<td colspan="7" align="right" class="bg_table01" height="3"></td>
						</tr>
						<tr class="bg_table02">
							<td width="7%" align="right">
								申请部门：
							</td>
							<td width="25%" align="left">
								<s:property value="position"/>
							</td>
							<td width="15%" align="right">
								申请人：
							</td>
							<td width="19%" align="left">
								<s:property value="user.name"/>
							</td>
							<td align="right">
								申请日期：
							</td>
							<td width="18%" colspan="2" align="left" id="datew">
							</td>
						</tr>
						<tr>
							<td colspan="7" align="right" nowrap class="bg_table02">
								<hr>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap class="bg_table02">
								合同客户：
							</td>
							<td class="bg_table02" align="left">
							<input type="text" id="clientName" name="hetongkehu" value="<s:property value="hetongkehu"/>" style="width:150px;height:21px;font-size:10pt;" readonly="true" /><span style="width:18px;border:0px solid red;"> 
							<s:select list="yxClientCodeList"  headerKey="" headerValue="--请选择--"
										cssStyle="margin-left:-150px;width:168px;" listKey="id"
										listValue="name" onchange="clientChange(this)"></s:select></span>
								<input type="button" value="…"
									onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
								<s:hidden name="billCustomer" id="clientId"></s:hidden>
							</td>
							<td align="right" class="bg_table02">
								合同名称：
							</td>
							<td class="bg_table02" align="left">
								<s:textfield name="contactName" id="contactName"></s:textfield>
							</td>
							<td colspan="3" align="right" class="bg_table02">
								&nbsp;
							</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">
								开票单位名称：
							</td>
							<td class="bg_table02" align="left">
								<input type="text" id="clientNameY" name="kaipiaodanwei"  value="<s:property value="kaipiaodanwei"/>" style="width:150px;height:21px;font-size:10pt;" readonly="true" /><span style="width:18px;border:0px solid red;">
								 <s:select list="billCustomerList"  headerKey="" headerValue="--请选择--"
										cssStyle="margin-left:-150px;width:168px;" listKey="id"
										listValue="name" onchange="clientChangeY(this)"></s:select> </span>
								<input type="button" value="…"
									onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientListY','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
								<s:hidden name="clientNameY" id="clientIdY"></s:hidden>
							</td>
							<td class="bg_table02" align="right">
								地址：
							</td>
							<td class="bg_table02" align="left" id="address">
								
							</td>
							<td align="right" nowrap class="bg_table02">
								联系电话：
							</td>
							<td colspan="2" align="left" class="bg_table02" id="phone">
								
							</td>
						</tr>
						<tr>
							<td align="right" nowrap class="bg_table02">
								开户银行：
							
							</td>
							<td align="left" class="bg_table02" id="bank">
								
							</td>
							<td class="bg_table02" align="right">
								<span class="STYLE3">帐号：</span>
							
							</td>
							<td class="bg_table02" align="left" id="zhanghao">
								
							</td>
							<td align="right" class="bg_table02">
								税号：
							</td>
							<td colspan="2" align="left" class="bg_table02" id="shuihao">
									
							</td>
						</tr>

						<tr>
							<td colspan="7" align="right" class="bg_table02">
								<hr>
							</td>
						</tr>

						<tr>
							<td class="bg_table02" align="right">
								开票性质：
							</td>
							<td align="left" class="bg_table02">
								<s:select name="invoiceNature" list="invoiceNatureList" listKey="typeSmall"
									listValue="typeName"></s:select>
							</td>
							<td align="right" class="bg_table02">
								<div align="right">
									发票金额小写(含税):
								</div>
							</td>
							<td align="left" class="bg_table02">
								<div align="left">
									<s:textfield id="lowerY" name="lowercaseMoney" readonly="true"  disabled="false"></s:textfield>
								</div>
							</td>
							<td align="right" class="bg_table02">
								发票金额小写(不含税)：
							</td>
							<td colspan="2" align="left" class="bg_table02">
								<s:textfield id="lowerN" name="lowercaseMoneyN" readonly="true" disabled="false" ></s:textfield>
							</td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">
								金额：
							</td>
							<td align="left" class="bg_table02">
								<s:textfield name="money" id="money" onchange="javascript:asd();" onkeypress="JHshNumberText();"></s:textfield>
							</td>
							<td class="bg_table02" align="right">
								<div align="right">
									发票金额大写(含税)：
								</div>
							</td>
							<td class="bg_table02" align="left">
								<div align="left">
									<input name="code2" id="capitalizationY" type="text" 
										value=""  readonly="true" disabled="false">
								</div>
							</td>
							<td align="right" class="bg_table02">
								发票金额大写(不含税)：
							</td>
							<td colspan="2" align="left" class="bg_table02">
								<input name="code6" id="capitalizationN" type="text" 
									value="" readonly="true" disabled="false" />
							</td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">
								票据类型:
							</td>
							<td align="left" class="bg_table02">
								<s:select name="invoiceType" list="invoiceList" listKey="typeSmall"
									listValue="typeName" id="piaotype" onchange="changeBillType(this);"></s:select>
							</td>
							<td align="right" nowrap class="bg_table02">
								基准：
							</td>
							<td class="bg_table02" align="left">
							<s:select list="#@java.util.HashMap@{0:'不含税',1:'含税'}" name="base" id="base" onchange="javascript:asd();">
							</s:select>
							</td>
							<td align="right" class="bg_table02">
								一次出库:
							</td>
							<td colspan="2" align="left" class="bg_table02">
								<s:checkbox name="ischu"></s:checkbox>
							</td>
						</tr>

						<tr>
							<td rowspan="2" align="right" class="bg_table02">
								开票内容：
							</td>
							<td rowspan="2" align="left" class="bg_table02">
								<s:textarea  name="invoiceContent" cols="25" rows="4"  id="content" ></s:textarea>
							</td>
							<td rowspan="2" align="right" class="bg_table02">
								<div align="right"></div>
							</td>
							<td rowspan="2" align="left" class="bg_table02">
								<div align="left"></div>
							</td>
							<td width="16%" align="right" class="bg_table02">
								库存组织：
							</td>
							<td align="left" class="bg_table02">
								<s:select name="stockOrg" list="stockOrgList" listKey="typeSmall"
									listValue="typeName" id="stockOrgId" ></s:select>
							</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">
								<div align="right">
									取票地点：
								</div>
							</td>
							<td colspan="2" align="right" class="bg_table02">
								<div align="left">
									<s:textfield name="billSpot" id="billSpot"></s:textfield>
								</div>
							</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">
								备注：
							</td>
							<td colspan="6" align="left" class="bg_table02">
								<s:textarea name="remarks" id="remak"></s:textarea>
							</td>
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
								<div align="center">
									申购关联：
								</div>
							</td>
							<td width="15%" align="left" class="bg_table02">
								<div align="center">
									申购单号
								</div>
							</td>
							<td width="58%" align="right" class="bg_table02">
								<div align="center">
									申购内容
								</div>
							</td>
							<td width="16%" align="left" class="bg_table02">
								&nbsp;
							</td>
						</tr>
						<s:iterator value="messageList">
						<tr>
							<td align="left" class="bg_table02">&nbsp;</td>
							<td align="left" class="bg_table02">
								<div align="center"><s:property value="applyId"/></div>
							</td>
							<td align="left" class="bg_table02">
								<div align="center"><s:property value="applyContent"/></div>
							</td>
							<td align="left" class="bg_table02">
								<div align="center"><a href="#" onclick="javascript:deleter('<s:property value="id"/>')">删除</a></div>
							</td>
						</tr>
						</s:iterator>
						<tr>
							<td align="center" class="bg_table02" colspan="4">
								<div align="center">
									<a href="#" onclick="javascript:openUrl()">添加</a>
								</div>
							</td>
							
						</tr>
					</table>
					<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04">
								<div align="center">
									<input type="button" value="保  存" class="button01"
										onclick="
										javascript:if(!validate1()){saveuu(0);}">
									<input type="button" value="提交申请" class="button01"
										onClick="
										javascript:if(!validate1()){saveuu(1);}">
								</div>
							</td>
						</tr>
					</TABLE>
				</td>
			</tr>
		</table>
	</s:form>
</body>

<script type="text/javascript">

	changeBillType(document.getElementById("invoiceType"));

function validate1(){
     var ev2=new Validator();
     with(noConApplyBill){  
       ev2.test("notblank","合同客户不能为空",$('clientId').value);   
       ev2.test("notblank","开票单位不能为空",$('clientIdY').value); 
       ev2.test("notblank","合同名称不能为空",$('contactName').value); 
       ev2.test("notblank","合同金额不能为空",$('money').value); 
       ev2.test("notblank","取票地点不能为空",$('billSpot').value); 
       ev2.test("notblank","开票内容不能为空",$('content').value); 
       ev2.test("float","申请金额必须为数字",$('money').value);  
 	}
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}
</script>
</html>
