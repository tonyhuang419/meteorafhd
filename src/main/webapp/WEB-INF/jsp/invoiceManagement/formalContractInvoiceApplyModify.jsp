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
<script src="/yx/commons/scripts/thousands.js" type="text/javascript"></script>
<script type="text/javascript">
function openPurchaseList(){
	var cmisid = document.getElementById("cmisid").value;
	window.open('/yx/invoice/createInvoice.action?method=openAssociationByConsidUpdate&contractmainsid='+cmisid,null,'menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');
}

function refreshSuper()	{
	document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?modify=true&method=refreshUpdate";
	document.forms(0).submit();
}
function deleter(id){
		document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=applicationsDelete&modify=true&deleteId="+id;
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


function updateSave()
{
	document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=updateSave";
	document.forms(0).submit();
}


function changeBillType(obj){
		billType = document.getElementById('billType').value;
		//alert(billType);
		changeMoney("/yx/jsonData.action?method=doGetTax&billType=" + billType);
	}
	

function changeMoney(jsonObjGetUrl){
		var jsonRequest = new Request.JSON({url:jsonObjGetUrl, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
					if(jsonObj.jsonData!=null){
						//alert(jsonObj.jsonData);
						document.getElementById("tax").value = jsonObj.jsonData;
						calc();
					}
			}
		}}).get({randerNum:Math.random()});	 	
}
	
	
	

//统计项目数，计算总金额
function calc(){
	var amount = 0;   //总金额....看基准   0-不含税；1-含税；
	var txamount = 0;   //总金额含税
	var notxamount = 0;   //总金额不含税
	var taxrate = 1;   //税率

	//var table = document.getElementById("itemamount");
	//var inputs = table.getElementsByTagName("input")
	//for(var i=0 ;i<inputs.length;i++){
	//	amount = eval(amount + "+ "+ inputs[i].value);
	//}
	
	amount =  parseFloatNumber(document.getElementById("applyAmount").value); 
	
	if(amount!=null && amount !="" && amount!=0){

		taxrate = document.getElementById("tax").value;
		var base = -1;
		base = document.getElementById("billBase").value;
		if(base==1){   //基准含税
			txamount = amount;
			notxamount = Math.round(txamount/taxrate*100)/100;
		}
		else if(base==0){  //基准不含税
			notxamount = amount;
			txamount = Math.round(notxamount*taxrate*100)/100;
		}	
		document.getElementById("txMoney").value = txamount;
		document.getElementById("notxMoney").value = notxamount;
		document.getElementById("taxmoney").innerHTML = txamount;
		document.getElementById("notaxmoney").innerHTML = notxamount;
		document.getElementById("bigMoney").innerHTML = Chinese(txamount);
		document.getElementById("bigMoneyN").innerHTML = Chinese(notxamount);
		
		document.getElementById('taxmoney').innerHTML= formatNumber(document.getElementById('txMoney').value,'#,###.0#');
		document.getElementById('notaxmoney').innerHTML= formatNumber(document.getElementById('notxMoney').value,'#,###.0#');
		
	}
	else{
		document.getElementById("txMoney").value =  0;
		document.getElementById("notxMoney").value =  0;
		document.getElementById("taxmoney").innerHTML =  0;
		document.getElementById("notaxmoney").innerHTML =  0;
		document.getElementById("bigMoney").innerHTML = "零";
		document.getElementById("bigMoneyN").innerHTML = "零";
	}
}

function statAmount(){
	document.getElementById("amountString").value = ""
	var amount = document.getElementsByName("value");
	
	var amountString = document.getElementById("amountString").value;
	for(var i=0;i<amount.length;i++){
		amountString = amountString + amount[i].value+",";
	}
	document.getElementById("amountString").value = amountString;
}

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
<div align="left" style="color:#000000">
   <p align="center">
   开票申请修改</p>
</div>
<iframe name="errorsFrame" frameborder="0"	framespacing="0" height="0" scrolling="no"></iframe>
<s:form action="" theme="simple" validate="true" id="applybill">
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <s:hidden name="amountString" id="amountString" value=""/>
	<s:hidden id="cmisid" value="${applybill.contractMainInfo}"/>
   <s:hidden id="rclist" value="${rclist}"/>
   <s:hidden id="billType" value="${applybill.billType}"/>
   <s:hidden id="billBase" value="${applybill.base}"/>
   <s:hidden name ="txMoney" id="txMoney" value="0"/>
   <s:hidden name ="notxMoney" id="notxMoney" value="0"/>
   <input type="hidden" id="tax" value="" />
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
		<s:property value="applybill.applyId"/>
    </div></td>
  </tr>
  <tr class="bg_table02">
    <td colspan="10" align="right" class="bg_table02"><hr align="right"></td>
  </tr>
  <tr>
    <td align="right" class="bg_table02"> <div align="right">单位名称： </div></td>
    <td colspan="2" align="left" class="bg_table02"><div align="left"><s:property value="billClient.name" />
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

  <s:if test="rcPlanList!=null">
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center" id="itemamount">
  <tr>
    <td width="12%" align="right" class="bg_table02"><div align="right">合同号：</div></td>
    <td align="left" class="bg_table02"><div align="left">
    <s:property value="foramlContractService.getConSn(applybill.contractMainInfo)"/>
    </div></td>
    <td width="8%" align="left" class="bg_table02"><div align="right">合同名称：</div></td>
    <td colspan="3" align="left" class="bg_table02">
	<s:property value="foramlContractService.getConName(applybill.contractMainInfo)"/></td>
    <td width="18%" colspan="5" rowspan="3" align="left" class="bg_table02"><div align="center"></div>   <div align="center"></div></td>
  </tr>

  <tr>
	<td align="right" class="bg_table02"><div align="right">项目号： </div></td>
	<td width="10%" align="left" class="bg_table02"><div align="left">
	<s:property value="itemName"/>
</div></td>
	<td align="right" class="bg_table02"><div align="right">申请金额：</div></td>
	<td width="14%" align="right" class="bg_table02"><div align="left">
	<s:textfield name="applyAmount"  id = "applyAmount" onchange="changeBillType();" readonly="true" disabled="true" 
	size="15" maxlength="15"  onkeypress="JHshNumberText();" onblur="statAmount(); " ></s:textfield>
    </div></td>
    <td colspan="2" align="right" class="bg_table02">&nbsp;</td>
  </tr>

  <tr>
    <td colspan="11" align="right" class="bg_table02"><hr align="right"></td>
  </tr>
  </table>
</s:if>

<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td width="17%" align="right" class="bg_table02" >开票性质：</td>
    <td width="18%"  align="left" class="bg_table02" ><s:property value="typeManageService.getYXTypeManage(1012,applybill.billNature).typeName "/>
    </td>
    <td width="16%"  align="right" class="bg_table02" >&nbsp;</td>
    <td width="13%" align="left" class="bg_table02">&nbsp;</td>
    <td width="16%"  align="right" class="bg_table02">&nbsp;</td>
    <td width="20%"  align="left" class="bg_table02">&nbsp;</td>
  </tr>
  <tr>
    <td align="right" class="bg_table02"><div align="right">票据类型：</div></td>
    <td  align="left" class="bg_table02"><div align="left">
        <s:property value="typeManageService.getYXTypeManage(1004,applybill.billType).typeName"/>
      </div></td>
    <td  align="right" class="bg_table02"><div align="right">发票金额小写(含税)： </div></td>
    <td align="left" class="bg_table02" id="taxmoney">
    <s:property value="applybill.billAmountTax"/>
    </td>
    <td  align="right" class="bg_table02"><div align="right">发票金额小写(不含税)： </div></td>
     <td align="left" class="bg_table02" id="notaxmoney">
     <s:property value="applybill.billAmountNotax"/>
     </td>
  </tr>
  <tr>
    <td align="right" class="bg_table02"><div align="right">基准：</div></td>
    <td  align="left" class="bg_table02"><div align="left">
       <s:if test="applybill.base==0">
							<s:label id="hastax" value="不含税"/>
		  </s:if>
						<s:else>
							<s:label id="hastax" value="含税"/>
		</s:else>
      </div></td>
    <td  align="right" class="bg_table02"><div align="right">发票金额大写(含税)： </div></td>
    <td class="bg_table02" align="left" id="bigMoney"></td>
    <td  align="right" class="bg_table02"><div align="right">发票金额大写(不含税)：</div></td>
    <td  align="left" class="bg_table02" id="bigMoneyN"></td>
  </tr>
  <tr>
    <td rowspan="2" align="right" class="bg_table02"><div align="right">开票内容：</div></td>
    <td  rowspan="2" align="left" class="bg_table02"><div align="left">
        <s:textarea   name="applybill.billContent" id="billContent"></s:textarea>
      </div></td>
    <td  align="right" class="bg_table02">&nbsp;</td>
    <td class="bg_table02" align="left"></td>
    <td  align="right" class="bg_table02"><div align="right">库存组织： </div></td>
    <td  align="left" class="bg_table02"><div align="left">
				<s:select name="applybill.stockOrg" list="stockOrgList" listKey="typeSmall"
							listValue="typeName" id="stockOrgId" ></s:select>
      </div></td>
  </tr>
  <tr>
    <td  align="right" class="bg_table02"><div align="right">一次出库：</div></td>
    <td  align="left" class="bg_table02"><s:checkbox name="applybill.oneOut" ></s:checkbox>
     </td>
    <td  align="right" class="bg_table02"><div align="right">取票地点： </div></td>
    <td  align="left" class="bg_table02"><div align="left">
    <s:textfield  name="applybill.billSpot" id="billSpot" ></s:textfield>
      </div></td>
  </tr>
  <tr>
    <td class="bg_table02" align="right"><div align="right">备注：</div></td>
    <td colspan="10" align="left" class="bg_table02"><label>
      <s:textarea cols="100"  name="applybill.remarks" ></s:textarea>
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
    <td width="11%" align="left" class="bg_table02"><div align="center">申购单号</div></td>
    <td colspan="2" align="left" class="bg_table02"><div align="center">申购内容</div></td>
    <td align="right" class="bg_table02"><div align="center">操作</div></td>
  </tr>
  <s:iterator id="pl" value="messageList">
    <tr>
      <td align="right" class="bg_table02"><div align="center"></div></td>
      <td align="left" class="bg_table02"><div align="center">
          <s:property value="#pl.eventId"/>
        </div></td>
      <td align="left" class="bg_table02"><div align="center">
          <s:property value="#pl.applyId"/>
        </div></td>
      <td colspan="2" align="left" class="bg_table02"><div align="center">
          <s:property value="#pl.applyContent"/>
        </div></td>
      <td width="11%" align="right" class="bg_table02"><div align="center">
    <a href="#" onclick="javascript:deleter('<s:property value="#pl.id"/>')">删除</a></div>
    </td>
    </tr>
  </s:iterator>
   <tr>
    <td colspan="7" align="left" class="bg_table02"><div align="center"><a href="#"></a><a href="javascript:openPurchaseList();">添加 </a></div></td>
  </tr>
  <tr>
    <td  colspan="7" ><div align="center">
        <input type="button" name="button4" id="button4" value="保存" class="button01"  onClick="javascript:if(!validate1()){updateSave();}">
      </div></td>
  </tr>
</table>
</s:form>
</body>

<script type="text/javascript">

function validate1(){
     var ev2=new Validator();
     with(applybill){  
       ev2.test("notblank","取票地点不能为空",$('billSpot').value); 
       ev2.test("notblank","开票内容不能为空",$('billContent').value); 
 	}
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}	 
		 
		 
function change()
	{
		var a = parseFloatNumber(document.getElementById('taxmoney').innerHTML) ;
		var b = parseFloatNumber(document.getElementById('notaxmoney').innerHTML) ;

		document.getElementById('bigMoney').innerHTML= Chinese(eval(a));
		document.getElementById('bigMoneyN').innerHTML=Chinese(eval(b));
	}
	change();
	
	
</script>

</html>
