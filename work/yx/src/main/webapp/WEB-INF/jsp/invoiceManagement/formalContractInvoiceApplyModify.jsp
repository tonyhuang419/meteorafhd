<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>开票申请修改</title>
<link href="/yx/commons/styles/foramlContractStyles/style_f.css" type="text/css" rel="stylesheet">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/casemoney.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/thousands.js" type="text/javascript"></script>
<script type="text/javascript">
function isTaxNoOfBillClientBlank(billClientId){
		var isBlank=false;
		var jsonRequest = new Request.JSON({async:false,url:'/yx/jsonData.action?method=isTaxNoOfBillClientBlank&billClientId='+billClientId, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
				isBlank = jsonObj.jsonData.isTaxNumberBlank;
			}else{
				alert("验证税号出现失败");
			}
		}}).get({randerNum:Math.random()});	
		return isBlank;
}
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

	
function saveAndApply()
{	
	if(isTaxNoOfBillClientBlank(<s:property value="billClient.id" />) && 
	"<s:property value="applybill.billType" />" == "2" //增值税发票
	){ 
		alert("申请单中开票客户没有税号");
		return;
	}else if(confirm("确认要提交吗？")){
		document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=updateSave&saveUpdate=2";   <%--saveUpdate表明申请并提交--%>
		document.forms(0).submit();
	}
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
<%-- 
		var table = document.getElementById("itemamount");
		var inputs = table.getElementsByTagName("input")
		for(var i=0 ;i<inputs.length;i++){
		amount = eval(amount + "+ "+ inputs[i].value);
	}
--%>	
	amount =  parseFloatNumber(document.getElementById("applyAmount").value); 	
	document.getElementById("moneyApply").value = amount;
	
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
		
		document.getElementById('taxmoney').innerHTML= formatNumber(document.getElementById('txMoney').value,'#,###.00');
		document.getElementById('notaxmoney').innerHTML= formatNumber(document.getElementById('notxMoney').value,'#,###.00');
		
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
body {
	background-color: #FFFFFF;
}
.tdZero{
	padding:0px 0px 0px 0px;
}
-->
</style>

</head>
<body leftmargin="0">
<div align="center" style="color:#000000">
   开票申请修改
</div>
<br/>
<div id="warn" style="display:none ; color:#FF0000" >提醒：申请金额已超过最大计划金额 <s:property value="maxAmount" /></div>
<iframe name="errorsFrame" frameborder="0"	framespacing="0" height="0" scrolling="no"></iframe>
<s:form action="" theme="simple" validate="true" id="applybill">
<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style=" border-collapse: collapse; ">
   <s:hidden name="amountString" id="amountString" value=""/>
   <s:hidden id="cmisid" value="${applybill.contractMainInfo}"/>
   <s:hidden id="rclist" value="${rclist}"/>
   
   <s:if test =  "applybill.applyWay ==  2" >
   	<s:hidden id="billType" value="${applybill.billType}"/>
   </s:if>
   
   <s:hidden id="billBase" value="${applybill.base}"/>
   <s:hidden name ="txMoney" id="txMoney" value="0"/>
   <s:hidden name ="notxMoney" id="notxMoney" value="0"/>
   <s:hidden id="maxAmount" value="${maxAmount}"/>    
 
   <input type="hidden" id="moneyApply" value="" />
 
   <s:if test =  "applybill.applyWay ==  2" >
   <input type="hidden" id="tax" value="1" />
   </s:if>
   <s:else>
    <input type="hidden" id="tax" value="1" />
   </s:else>

  <tr>
    <td width="10%" align="right"><div align="right">申请部门： </div></td>
    <td width="20%" align="left" ><div align="left"><s:property value="applyDept"/></div></td>
    <td width="10%" align="right" ><div align="right">申请人： </div></td>
    <td width="25%" align="right" ><div align="left"><s:property value="applyName"/></div></td>
    <td colspan="2" width="35%" align="right" >&nbsp;</td>
  </tr>

  <tr>
    <td align="right" > <div align="right">开票名称： </div></td>
    <td align="left" ><div align="left"><s:property value="billClient.billName" />
    </div></td>
    <td align="right" > <div align="right">地址： </div></td>
    <td align="right" ><div align="left"><s:property value="billClient.billAdd"/></div></td>
    <td align="right" nowrap ><div align="right">联系电话：</div></td>
    <td width="25%"  align="right" ><div align="left"><s:property value="billClient.billPhone"/></div></td>
  </tr>
  <tr>
    <td  align="right" nowrap ><div align="right">开户银行：</div></td>
    <td align="left" ><div align="left"><s:property value="billClient.billBank"/></div></td>
    <td align="right"><div align="right">帐号： </div></td>
    <td align="right"><div align="left"><s:property value="billClient.account"/></div></td>
    <td align="right"><div align="right">税号：</div></td>
    <td align="right"><div align="left"><s:property value="billClient.taxNumber"/></div></td>
  </tr>

  <s:if test="rcPlanList!=null">
<tr><td colspan="6" class="tdZero">
<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style=" border-collapse: collapse;"  frame="void">
  <tr>
    <td align="right" ><div align="right">合同号：</div></td>
    <td align="left" ><div align="left">
    <s:property value="foramlContractService.getConSn(applybill.contractMainInfo)"/>
    </div></td>
    <td align="left" ><div align="right">合同名称：</div></td>
    <td colspan="5" align="left" >
	<s:property value="foramlContractService.getConName(applybill.contractMainInfo)"/></td>
  </tr> 
    
<s:if test =  "applybill.applyWay ==  1" > <%--合同已签开票申请 --%>
    	<%--<td width="14%" align="right" ><div align="left">
		<s:textfield name="applyAmount"  id = "applyAmount" onchange="changeBillType();" readonly="true"  
		size="15" maxlength="15"  onkeypress="JHshNumberText();" onblur="statAmount(); " ></s:textfield>
		--%>
			
	<s:iterator id="rc" value="rcPlanList" status="stats">    <%--是个map--%>
	  <tr>
		<td align="right"><div align="right">项目号： </div></td>
		<td align="left" ><div align="left"><s:property value="invoiceService.getCimiName(key)"/></div></td>		
		<td align="right" ><div align="right">开票金额：</div></td>
		<td align="right" ><div align="left">
	     <s:property value="value"/>
	    </div></td>

    	<s:if test =  "applybill.applyWay !=  2 " > <%--不是收据申请，就显示开票性质 --%> 
    		<td  align="right" >开票性质：</td>
    		<td  align="left" >
    		 <s:property value="typeManageService.getYXTypeManage(1012,invoiceService.getBillTypeFromRCPlan(key)).typeName "/>
    		</td>
		</s:if>
		<td><div align="right">阶段名称：</div></td>
 		<td><div align="left"><s:property value="invoiceService.getStageByRCPlan(key)"/></div></td>
 	  </tr>
 	</s:iterator>
</s:if>
   
<s:elseif test =  " applybill.applyWay ==  0 || applybill.applyWay ==  2  " > <%--收据申请--%>

<s:iterator  id="rp" value="rcPlanList"  status="s">
    <tr>
    <td align="right" ><div align="right">项目号： </div></td>   
    <td width="10%" align="left" ><div align="left">
    <s:property value="invoiceService.getCimiName(key)" />
    </div></td> 
    <td  align="right" >开票性质：</td>
  <td  align="left" >
 <s:property value="typeManageService.getYXTypeManage(1012,invoiceService.getBillTypeFromRCPlan(key)).typeName "/>
    </td>
    <td><div align="right">阶段名称：</div></td>
 	<td><div align="left"><s:property value="invoiceService.getStageByRCPlan(key)"/></div></td>
    <s:if test="#s.index==0">
     <td ><div align="right"><font color="red">*</font>申请金额：</div></td>
    <td width="14%" ><div align="left">
      <s:textfield name="applyAmount" id="money"  
       size="15" maxlength="15"  onkeypress="JHshNumberText();" onblur="calc();formatInputNumber(this);warn();" ></s:textfield>
    </div></td>
    </s:if>
    <s:else>
    	<td>&nbsp;</td><td>&nbsp;</td>
    </s:else>
  </tr>
 </s:iterator>	
</s:elseif> 
 </tr>
</s:if>
</table>
</td></tr>
 <tr>
    <td align="right" ><div align="right">票据类型：</div></td>
    <td  align="left" ><div align="left">
 	<s:property value="typeManageService.getYXTypeManage(1004,applybill.billType).typeName"/>
 	</div></td>
 	<td align="right" ><div align="right">基准：</div></td>
    <td colspan="3" align="left" ><div align="left">
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
    <td colspan="3" align="left" ><div align="left">发票金额小写(不含税)： 
    	<s:label id="notaxmoney" name="applybill.billAmountNotax"/>
     </td>
  </tr>
  <tr>
    <td colspan="3" align="left" ><div align="left">发票金额大写(含税)： <s:label id="bigMoney"/></td>
    <td colspan="3" align="left" ><div align="left">发票金额大写(不含税)：<s:label id="bigMoneyN"/></td>
  </tr>

  <tr>
   <td  align="right" ><div align="right">一次出库：</div></td>
    <td  align="left" ><s:checkbox name="applybill.oneOut" ></s:checkbox>
     </td>
    <td  align="right" ><div align="right"><font color="red">*</font>取票地点： </div></td>
    <td  align="left" ><div align="left">
    <s:textfield  name="applybill.billSpot" id="billSpot" ></s:textfield>
      </div></td>
    <td  align="right" ><div align="right">库存组织： </div></td>
    <td  align="left" ><div align="left">
				<s:select name="applybill.stockOrg" list="stockOrgList" listKey="typeSmall"
							listValue="typeName" id="stockOrgId" ></s:select>
      </div></td>
  </tr>
 
  <tr>
    <td align="right" ><div align="right"><font color="red">*</font>开票内容：</div></td>
    <td colspan="5" align="left" ><div align="left">
        <s:textarea  cols="50" name="applybill.billContent" id="billContent"></s:textarea>
      </div></td>
  </tr>
  <tr>
    <td  align="right"><div align="right">备注：</div></td>
    <td  colspan="3" align="left" ><label>
      <s:textarea cols="50" rows="3" id = "remarks"  name="applybill.remarks" ></s:textarea>
      </label></td>
    <td  align="right"><div align="right">甲方接收人：</div></td>
      <td>
	      <s:textarea cols="20" rows="3" id = "firstReceiveMan"  name="applybill.firstReceiveMan" ></s:textarea>
      </td>
  </tr>
 <s:if test="applybill.applyBillState == 4">
  <tr>
    <td  align="right"><div align="right">退回理由：</div></td>
    <td  colspan="5" align="left" ><label>
      <s:property value="applybill.returnReason" ></s:property>
      </label></td>
  </tr>
  </s:if>
</table>
<br>
<s:if test =  "applybill.applyWay !=  2" > <%--收据申请 --%>
<table width="100%" border="1" class="bg_table02" bordercolor="#808080" style=" border-collapse: collapse;">
  <tr class="bg_table01">
    <td width="14%" align="right"><div align="center">申购关联：</div></td>
    <td width="10%" align="left"><div align="center">项目号</div></td>
    <td width="11%" align="left"><div align="center">申购单号</div></td>
    <td align="left"><div align="center">申购内容</div></td>
    <td align="right" ><div align="center">操作</div></td>
  </tr>
  <s:iterator id="pl" value="messageList">
    <tr class="bg_table02">
      <td align="right" ><div align="center"></div></td>
      <td align="left" ><div align="center">
          <s:property value="#pl.eventId"/>
        </div></td>
      <td align="left" ><div align="center">
          <s:property value="#pl.applyId"/>
        </div></td>
      <td align="left" ><div align="center">
          <s:property value="#pl.applyContent"/>
        </div></td>
      <td width="11%" align="right" ><div align="center">
    	<a href="#" onclick="javascript:deleter('<s:property value="#pl.id"/>')">删除</a></div>
    	</td>
    </tr>
  </s:iterator>
   <tr>
    <td colspan="5" align="left" ><div align="center">
    	<a href="#" onclick="javascript:openPurchaseList();">添加 </a>
    </div></td>
  </tr>
</s:if>
  <tr class="bg_table04" height="42px">
    <td  colspan="7" ><div align="center">
    	 <input type="button" value=" 返回" class="button01"  onClick="goBack2();">
        <input type="button"  value="保存" class="button01"  onClick="javascript:if(!validate1()){updateSave();}">
        <input type="button" value="提交申请" class="button01"	onclick="javascript:if(!validate1()){saveAndApply();}">
      </div></td>
  </tr>
</table>
</s:form>
	
	
</body>

<script type="text/javascript">

function validate1(){
     var ev2=new Validator();
     with(applybill){ 
     <s:if  test="applybill.applyWay != 1" >
       ev2.test("notblank","申请金额不能为空",$('applyAmount').value); 
       ev2.test("float","申请金额必须为数字",$('applyAmount').value); 
	  </s:if>
    
       ev2.test("notblank","取票地点不能为空",$('billSpot').value); 
       ev2.test("notblank","开票内容不能为空",$('billContent').value); 
       ev2.test("length","开票内容请少于300字符",$('billContent').value,300); 
       ev2.test("length","备注请少于300字符",$('remarks').value,500);  
 	}
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}	 
		
function goBack2()
{
//	var form = document.getElementById("findConition");
	location.href = "/yx/invoice/createInvoice.action?method=findInvoiceApplications";
//	form.submit();
} 
		 


<s:if test="applybill.applyWay == 1">
function change1(){
		var a = parseFloatNumber(document.getElementById('taxmoney').innerHTML) ;
		var b = parseFloatNumber(document.getElementById('notaxmoney').innerHTML) ;
		document.getElementById('bigMoney').innerHTML= Chinese(eval(a));
		document.getElementById('bigMoneyN').innerHTML=Chinese(eval(b));
}
change1();
</s:if>	

<s:elseif test="applybill.applyWay == 2">
function change()
	{
	
		document.getElementById("moneyApply").value = parseFloatNumber(  document.getElementById("money").value    );

		var a = parseFloatNumber(document.getElementById('taxmoney').innerHTML) ;
		var b = parseFloatNumber(document.getElementById('notaxmoney').innerHTML) ;

		document.getElementById('bigMoney').innerHTML= Chinese(eval(a));
		document.getElementById('bigMoneyN').innerHTML=Chinese(eval(b));
		
		document.getElementById("txMoney").value =  a;
		document.getElementById("notxMoney").value =  b;
	}
	change();
</s:elseif>	

<s:else>
	calc();
</s:else>
	
	
<s:if test =  " applybill.applyWay ==  0 || applybill.applyWay ==  2  " > 
function warn(){
	moneyApplyX =  $('moneyApply').value*1;
	maxAmountX = $('maxAmount').value*1;
	if(  moneyApplyX > maxAmountX ){
		var warn  = document.getElementById("warn");
		warn.style.display = "block";
	}
	else{
		var warn  = document.getElementById("warn");
		warn.style.display = "none";
	}
}
warn();
</s:if>
</script>

</html>
