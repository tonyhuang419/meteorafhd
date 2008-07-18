<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>开票申请表</title>
<link href="/yx/commons/styles/foramlContractStyles/style_f.css" type="text/css" rel="stylesheet">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/casemoney.js" type="text/javascript"></script>
<script type="text/javascript">
function openPurchaseList(){
	var cmisid = document.getElementById("cmisid").value;
	window.open('/yx/invoice/createInvoice.action?method=openAssociationByConsid&contractmainsid='+cmisid,null,'menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');
}

function refreshSuper()	{
	document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=doDefaultT";
	document.forms(0).submit();
}
function deleter(id){
		document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=applicationsDelete&deleteId="+id;
		document.forms(0).submit();
}
function saveApply(){
		document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=saveInvoice&saveSign=1";
		document.forms(0).submit();
}
function submitApply(){
		document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=saveInvoice&saveSign=2";
		document.forms(0).submit();
}

function check(){
	var max = document.getElementById("maxMoney").value;
	var now = document.getElementById("applyAmount").value;

	if(max*1<now*1){
		alert("输入金额大于计划金额，请重新输入")
		document.getElementById("applyAmount").value = max;
	}
	calc();
}



//统计项目数，计算总金额
function calc(){
	var amount = 0;   //总金额....看基准   0-不含税；1-含税；
	var txamount = 0;   //总金额含税
	var notxamount = 0;   //总金额不含税
//	var table = document.getElementById("itemamount");
//	var inputs = table.getElementsByTagName("input")
//	for(var i=0 ;i<inputs.length;i++){
//		amount = eval(amount + "+ "+ inputs[i].value);
//	}
	amount  = document.getElementById("applyAmount").value;
	
	var taxrate = 0;   //税率
	var billType = -1;
	billType = document.getElementById("billType").value;
	if(billType==1){
		taxrate=1.055;
	}
	else if(billType==2){
		taxrate=1.17;
	}
	else if(billType==3){
		taxrate=1.17;
	}
	else if(billType==4){
		taxrate=1;
	}

	var base = -1;
	base = document.getElementById("billBase").value;
	if(base==1){   //基准含税
		txamount = amount*100/100;
		notxamount = Math.round(txamount/taxrate*100)/100;

	}
	else if(base==0){  //基准不含税
		notxamount = amount*100/100;
		txamount = Math.round(notxamount*taxrate*100)/100;

	}	
	document.getElementById("txMoney").value = txamount;
	document.getElementById("notxMoney").value = notxamount;
	document.getElementById("taxmoney").innerHTML = txamount;
	document.getElementById("notaxmoney").innerHTML = notxamount;
	document.getElementById("bigMoney").innerHTML = Chinese(txamount);
	document.getElementById("bigMoneyN").innerHTML = Chinese(notxamount);
}

//function statAmount(){
//var amount = document.getElementsByName("applyAmount");
//var amountString = document.getElementById("amountString").value;
//for(var i=0;i<amount.length;i++){
//	amountString = amountString + amount[i].value+",";
//}
//document.getElementById("amountString").value = amountString;
//}
</script>

<style type="text/css">
<!--
.STYLE3 {
	color: #000000
}
body {
	background-color: #FFFFFF;
}
.STYLE5 {
	font-size: 16px;
	font-weight: bold;
}
.STYLE6 {
	font-size: 16px
}
-->
</style>

</head>
<body leftmargin="0">
<div align="left" style="color:#000000">&nbsp;
  <p align="center" class="STYLE2 STYLE5"><span class="STYLE5">开票申请表</span></p>
</div>

<s:form action="" theme="simple" validate="true" id="applybill">
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<s:hidden id="rclist" value="${rclist}"/>
	<s:hidden id="cmisid" value="${cmi.conMainInfoSid}"/>
   <s:hidden id="billType" value="${rcPlan.billType}"/>
   <s:hidden id="billBase" value="${rcPlan.base}"/>
   <s:hidden name ="txMoney" id="txMoney" value="0"/>
   <s:hidden name ="notxMoney" id="notxMoney" value="0"/>
   <s:hidden id ="maxMoney" value="${rcPlan.realBillAmount}"/>
  <tr>
      <td colspan="10" align="right" class="bg_table01" height="3"> </td>
  </tr>
  <tr class="bg_table02">
    <td width="16%" class="bg_table02" align="right"><div align="right">申请部门： </div></td>
    <td colspan="2" align="left" class="bg_table02"><div align="left"><s:property value="applyDept"/></div></td>
    <td width="8%" align="right" class="bg_table02"><div align="right">申请人： </div></td>
    <td colspan="2" align="right" class="bg_table02"><div align="left"><s:property value="applyName"/></div></td>
    <td width="8%" align="right" class="bg_table02"><div align="right">申请日期： </div></td>
    <td width="35%" colspan="3" align="right" class="bg_table02"><div align="left">
<s:date name="now" format="yyyy-MM-dd" />
    </div></td>
  </tr>
  <tr class="bg_table02">
    <td colspan="10" align="right" class="bg_table02"><hr align="right"></td>
  </tr>
  <tr>
    <td align="right" class="bg_table02"> <div align="right">单位名称： </div></td>
    <td colspan="2" align="left" class="bg_table02"><div align="left"><s:property value="billClient.billName" />
    </div></td>
    <td align="right" class="bg_table02"> <div align="right">地址： </div></td>
    <td colspan="2" align="right" class="bg_table02"><div align="left"><s:property value="billClient.address"/>
</div></td>
    <td align="right" nowrap class="bg_table02"><div align="right">联系电话：
        </div>
    </td>
    <td colspan="3" align="right" class="bg_table02"><div align="left"><s:property value="billClient.billPhone"/></div></td>
  </tr>
  <tr>
    <td   align="right" nowrap class="bg_table02"><div align="right">开户银行：
        </div>
   </td>
    <td colspan="2"  align="left" class="bg_table02"><div align="left"><s:property value="billClient.billBank"/></div></td>
    <td align="right"  class="bg_table02"><div align="right"><span class="STYLE3">帐号：</span>   
    </div></td>
    <td colspan="2" align="right"  class="bg_table02"><div align="left"><s:property value="billClient.account"/></div></td>
    <td align="right"  class="bg_table02"><div align="right">税号：</div></td>
    <td colspan="3" align="right"  class="bg_table02"><div align="left"><s:property value="billClient.taxNumber"/></div></td>
  </tr>
    <tr>
    <td colspan="10" align="right" class="bg_table02"><hr align="right"></td>
  </tr>
  </table>

  <table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center" id="itemamount">
  <tr>
    <td width="15%" align="right" class="bg_table02"><div align="right">合同号：</div></td>
    <td align="left" class="bg_table02"><div align="left"><s:property value="cmi.conMainInfoSid"/></div></td>
    <td width="8%" align="left" class="bg_table02"><div align="right">合同名称：</div></td>
    <td width="49%" align="left" class="bg_table02"><s:property value="cmi.conName"/></td>
    <td width="18%" colspan="5" rowspan="3" align="left" class="bg_table02"></td>
  </tr>
  

  <tr>
    <td align="right" class="bg_table02"><div align="right">项目号： </div></td>   
    <td width="10%" align="left" class="bg_table02"><div align="left"><s:property value="itemName"/></div></td>
    <td align="right" class="bg_table02"><div align="right">申请金额：</div></td>
    <td align="right" class="bg_table02"><div align="left">
      <s:textfield name="applyAmount"   onchange="calc();" size="15" maxlength="15"  onkeypress="JHshNumberText();" onchange="calc();" onblur="check();" ></s:textfield>
      
      <!--<input name="applyAmount" id ="applyAmount" type="text"  class="txtinput" value="<s:property value="rcPlan.realBillAmount"/>" 
      onchange="calc();" onblur="check();"  size="15" maxlength="15"  onKeypress="JHshNumberText();" > -->
    </div></td>
    </tr>

  
  <tr>
    <td colspan="11" align="right" class="bg_table02"><hr align="right"></td>
  </tr>
  </table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td width="15%" align="right" class="bg_table02">开票性质：</td>
    <td width="20%"  align="left" class="bg_table02">
     <s:property value="typeManageService.getYXTypeManage(1012,rcPlan.billNature).typeName "/>
    </td>
    <td width="15%"  align="right" class="bg_table02">&nbsp;</td>
    <td width="15%" align="left" class="bg_table02">&nbsp;</td>
    <td width="15%"  align="right" class="bg_table02">&nbsp;</td>
    <td width="*"  align="left" class="bg_table02">&nbsp;</td>
  </tr>
  <tr>
    <td align="right" class="bg_table02"><div align="right">票据类型：</div></td>
    <td  align="left" class="bg_table02"><div align="left">
							<s:property
								value="typeManageService.getYXTypeManage(1004,rcPlan.billType).typeName" />
						</div></td>
    <td  align="right" class="bg_table02"><div align="right">发票金额小写(含税)： </div></td>
    <td align="left" class="bg_table02" id="taxmoney">
        
    </td>
    <td  align="right" class="bg_table02"><div align="right">发票金额小写(不含税)： </div></td>
    <td  align="left" class="bg_table02" id="notaxmoney">
     
    </td>
  </tr>
  <tr>
    <td align="right" class="bg_table02"><div align="right">基准：</div></td>
    <td  align="left" class="bg_table02"><div align="left">
           <s:if test="rcPlan.base == 0 ">不含税</s:if>
           <s:elseif test="rcPlan.base"> 含税 </s:elseif>
           <s:else> 状态错误 </s:else>
    </div></td>
    <td  align="right" class="bg_table02"><div align="right">发票金额大写(含税)： </div></td>
    <td class="bg_table02" align="left" id="bigMoney"> 
    </td>
    <td  align="right" class="bg_table02"><div align="right">发票金额大写(不含税)：</div></td>
    <td  align="left" class="bg_table02" id="bigMoneyN">
	</td>
  </tr>
  <tr>
    <td rowspan="2" align="right" class="bg_table02"><div align="right">开票内容：</div></td>
    <td  rowspan="2" align="left" class="bg_table02">
      <div align="left">
        <s:textarea   name="invoiceContent"></s:textarea>
      </div></td>
    <td  align="right" class="bg_table02">&nbsp;</td>
    <td class="bg_table02" align="left">&nbsp;</td>
    <td  align="right" class="bg_table02"><div align="right">库存组织： </div></td>
		<td align="left" class="bg_table02">
				<s:select name="stockOrg" list="stockOrgList" listKey="typeSmall"
							listValue="typeName" id="stockOrgId" ></s:select>
		</td>
  </tr>
  <tr>
    <td  align="right" class="bg_table02"><div align="right">一次出库</div></td>
    <td class="bg_table02" align="left"><div align="left">
      <s:checkbox name="oneOut"></s:checkbox>
    </div></td>
    <td  align="right" class="bg_table02"><div align="right">取票地点： </div></td>
    <td  align="left" class="bg_table02"><div align="left">
        <s:textarea name="billSpot" ></s:textarea>
    </div></td>
  </tr>
  <tr>
    <td class="bg_table02" align="right"><div align="right">备注：</div></td>
    <td colspan="10" align="left" class="bg_table02"><label>
   	 <s:textarea cols="100" name="remarks"></s:textarea>
    </label></td>
  </tr>
  <tr>
    <td colspan="6" align="right" class="bg_table02"><hr></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td width="14%" align="right" class="bg_table02"><div align="center">申购关联：</div></td>
    <td width="10%" align="left" class="bg_table02"><div align="center">项目号</div></td>
    <td width="12%" align="left" class="bg_table02"><div align="center">申购单号</div></td>
    <td colspan="2" align="left" class="bg_table02"><div align="center">申购内容</div></td>
    <td align="right" class="bg_table02"><div align="center">操作</div></td>
  </tr>
  <s:iterator id="pl" value="messageList">
  <tr>
    <td align="right" class="bg_table02"><div align="center"></div></td>
    <td align="left" class="bg_table02"><div align="center"><s:property value="#pl.eventId"/></div></td>
    <td align="left" class="bg_table02"><div align="center"><s:property value="#pl.applyId"/></div></td>
    <td colspan="2" align="left" class="bg_table02"><div align="center"><s:property value="#pl.applyContent"/></div></td>
    <td width="11%" align="right" class="bg_table02"><div align="center">
    <a href="#" onclick="javascript:deleter('<s:property value="#pl.id"/>')">删除</a></div>
    </td>
  </tr>
  </s:iterator>
  <tr>
    <td colspan="7" align="left" class="bg_table02"><div align="center"><a href="#"></a><a href="javascript:openPurchaseList();">添加 </a></div></td>
  </tr>
  <tr>
    <td  colspan="7" class="bg_table03">
      <div align="center">
        <input type="button" name="button2" id="button2" value="保  存" class="button01" onclick="saveApply();">
        <input type="button" name="button" id="button" value="提交申请" class="button01" onclick="submitApply();"">
        <input type="button" name="button4" id="button4" value="关闭" class="button01"  
        onClick="window.close();">
      </div></td>
  </tr>
</table>
</s:form>
</body>
<script type="text/javascript">
	calc();
	//statAmount();
</script>
</html>
