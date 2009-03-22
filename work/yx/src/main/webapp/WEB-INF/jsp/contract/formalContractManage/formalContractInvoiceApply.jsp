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
		if(isTaxNoOfBillClientBlank(<s:property value="billClient.id" />) && 
			$('invoiceType').value == "2" //增值税发票
		){ 
			alert("开票客户没有税号");
			return;
		}
		document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=saveInvoice&saveSign=2";
		document.forms(0).submit();
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
						calc();
					}
			}
		}}).get({randerNum:Math.random()});	 	
}


function calc()
	{
		var b = document.getElementById("tax").value;
		var c = document.getElementById("billBase").value;  //基准
							
		var m = parseFloatNumber(document.getElementById("money").value);    //申请金额
		document.getElementById("moneyApply").value = m;
				
		if(m!=null && m !="" && m!=0){
			var moneyx = parseFloatNumber(m);	
			if(c==1)  //含税
			{
				document.getElementById('lowerY').value= Math.round( moneyx * 100)/100;
				document.getElementById('capitalizationY').value=Chinese( document.getElementById('lowerY').value );
				document.getElementById('lowerN').value=Math.round( document.getElementById('lowerY').value  /b*100 ) /100;
				document.getElementById('capitalizationN').value=Chinese( document.getElementById('lowerN').value );
			}
			else if(c==2)  //不含税
			{
				document.getElementById('lowerN').value= Math.round( moneyx * 100)/100;
				document.getElementById('capitalizationN').value=Chinese(  document.getElementById('lowerN').value );
				document.getElementById('lowerY').value=Math.round( document.getElementById('lowerN').value * b*100)/100;
				document.getElementById('capitalizationY').value=Chinese( document.getElementById('lowerY').value );
			}
			document.getElementById('lowerY').value = formatNumber(document.getElementById('lowerY').value,'#,###.00');
			document.getElementById('lowerN').value = formatNumber(document.getElementById('lowerN').value,'#,###.00');

		}
		else{
			document.getElementById('lowerN').value= 0;
			document.getElementById('capitalizationN').value="零";
			document.getElementById('lowerY').value= 0;
			document.getElementById('capitalizationY').value="零";
		}
		
		<s:if test="comeFrom == 1 || comeFrom == 2 || comeFrom == 3 ">
			var maxAmount = document.getElementById("maxAmount").value;
			var applyAmount =  document.getElementById("moneyApply").value;
			if(applyAmount > maxAmount ){
				document.getElementById("warn").style.display="block";
			}
			else{
				document.getElementById("warn").style.display="none";
			}
		</s:if>
	}


</script>

<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.title {
	font-size: 16px;
	font-weight: bold;
}
.tdZero{
	padding:0px 0px 0px 0px;
}
-->
</style>

</head>
<body leftmargin="0">
<div align="left" style="color:#000000">&nbsp;
  <p align="center" class="title">开票申请表</p>
</div>
<br/>
<iframe name="errorsFrame" frameborder="0"	framespacing="0" height="0" scrolling="no"></iframe>

<div id="warn" style="display:none ; color:#FF0000" >提醒：申请金额已超过最大计划金额 <s:property value="maxAmount"/></div>

<s:form action="" theme="simple" validate="true" id="applybill">
 <table  class="bg_table02" width="100%" border="1" align="center" id="formalCon"  bordercolor="#808080" style=" border-collapse: collapse;">
	<s:hidden id="cmisid" value="${cmi.conMainInfoSid}"/>
    <s:hidden id="billBase" value="${cmi.standard}"/>
	<input type="hidden" id="tax" value="" />
	 <s:hidden name="comeFrom" />
	<s:hidden name="maxAmount"  id="maxAmount" />
	<s:hidden name="maxAmount"  id="maxAmountOrg" />
	<input type="hidden" id="moneyApply" value="" />

  <tr>
    <td width="10%"><div align="right">申请部门： </div></td>
    <td width="20%"><div align="left"><s:property value="applyDept"/></div></td>
    <td width="10%"><div align="right">申请人： </div></td>
    <td width="25%"><div align="left"><s:property value="applyName"/></div></td>
    <td colspan="2" width="10%">&nbsp; </td>
  </tr>
  <tr>
    <td> <div align="right">单位名称： </div></td>
    <td><div align="left"><s:property value="billClient.billName" />
    </div></td>
    <td><div align="right">地址： </div></td>
    <td><div align="left"><s:property value="billClient.address"/>
</div></td>
    <td nowrap ><div align="right">联系电话：
        </div>
    </td>
    <td ><div align="left"><s:property value="billClient.billPhone"/></div></td>
  </tr>
  <tr>
    <td nowrap><div align="right">开户银行：
        </div>
   </td>
    <td><div align="left"><s:property value="billClient.billBank"/></div></td>
    <td><div align="right">帐号：</div></td>
    <td><div align="left"><s:property value="billClient.account"/></div></td>
    <td><div align="right">税号：</div></td>
    <td width="25%"  align="right" ><div align="left"><s:property value="billClient.taxNumber"/></div></td>
  </tr>

  <tr>
    <td><div align="right">合同号：</div></td>
    <td><div align="left"><s:property value="cmi.conMainInfoSid"/></div></td>
    <td><div align="right">合同名称：</div></td>
    <td><div align="left"><s:property value="cmi.conName"/></div></td>
    <td colspan="2">&nbsp;</td>
  </tr>
<tr><td colspan="6" class="tdZero">
<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style=" border-collapse: collapse;"  frame="void">
<%-- 开收据...and..预先决算开票 --%>
<s:if test="comeFrom == 1 || comeFrom == 2 || comeFrom == 3 ">

 	<s:iterator  id="rp" value="rrcPlanList" status="s">
 	  <tr>
    	<td align="right" ><div align="right">项目号： </div></td>   
    	<td align="left" ><div align="left">  	
   	 	<s:property value="invoiceService.getCimiName(key)" />
   	 	</div></td>
   	 	  <%--不是开收据--%>
  	<s:if test="comeFrom != 1 && comeFrom != 2 ">
   	 	<td  align="right" >开票性质：</td>
    	<td  align="left" > <s:property value="typeManageService.getYXTypeManage(1012,invoiceService.getBillTypeFromRCPlan(key)).typeName "/></td>
 	</s:if>
    <s:else>
    	<td colspan="2">&nbsp;</td>
    </s:else>
     <td><div align="right">阶段名称：</div></td>
 	<td><div align="left"><s:property value="invoiceService.getStageByRCPlan(key)"/></div></td>
      <s:if test="#s.index==0">
   	 <td nowrap><div align="right"><font color="red">*</font>申请金额：</div></td>
    <td><div align="left">
      <s:textfield name="applyAmount" id="money"   size="15" maxlength="15"  
      onkeypress="JHshNumberText();" onblur="calc();formatInputNumber(this);"></s:textfield>
    </div></td>	
    </s:if>
 	</s:iterator>	
	
	<%--
    <td align="right" ><div align="right">计划金额：</div></td>
    <td align="right" ><div align="left">
      <input name="applyAmount" type="text"  class="txtinput" value="<s:property value="value"/>"  disabled="true" >
    </div></td>
    <td >&nbsp;</td>
    </tr>
  <tr>
    <td ><div align="right">&nbsp;</div></td>   
    <td width="10%" ><div align="left"><s:property value="itemName"/></div></td>
     --%>
  
  </tr> 
</s:if>
</table>
</td></tr>
<%-- 
 <s:else>
  <tr>
    <td><div align="right">项目号： </div></td>   
    <td><div align="left"><s:property value="itemName"/></div></td>
    <td><div align="right"><font color="red">*</font>申请金额1：</div></td>
    <td><div align="left">
      <s:textfield name="applyAmount" id="money"   size="15" maxlength="15"  onkeypress="JHshNumberText();" onblur="calc();formatInputNumber(this);" ></s:textfield>
    </div></td>
    <td >&nbsp;</td>
  </tr>
</s:else>
--%>
<tr>
    <td align="right" ><div align="right">票据类型：</div></td>
    <td  align="left" ><div align="left">
 	<%--开收据..预决算--%>
	<s:if test="comeFrom == 1 || comeFrom == 2  || comeFrom == 3  ">
		<s:hidden name="invoiceType" id="invoiceType"/>
	<s:if test = "comeFrom == 3">
			<s:hidden name="applyWay" value="0"/>
	</s:if>
	<s:else>
		<s:hidden name="applyWay" value="2"/>
	</s:else>
	<s:select name="invoiceType" list="invoiceList" listKey="typeSmall" id="piaotype" listValue="typeName"  disabled="true" > </s:select>
	</s:if>
	<s:else>
		<s:select name="invoiceType" list="invoiceList" listKey="typeSmall" id="piaotype" listValue="typeName"  onchange="changeBillType(this);"></s:select>
	</s:else>
		</div></td>	
	<td align="right" ><div align="right">基准：</div></td>
    <td colspan="3" align="left" ><div align="left">
           <s:if test="cmi.standard == 2 ">不含税</s:if>
           <s:else> 含税 </s:else>
    </div></td>	
</tr>

  <tr>
    <td colspan="3"><div align="left">发票金额小写(含税)： 
    	<s:textfield id="lowerY" name="txMoney"  readonly="true"></s:textfield>    
    </td>
    <td colspan="3"><div align="left">发票金额小写(不含税)：
     	<s:textfield id="lowerN" name="notxMoney" readonly="true"></s:textfield>
    </td>
  </tr>
  <tr>
    <td colspan="3"><div align="left">发票金额大写(含税)： 
    	<input name="code2" id="capitalizationY" type="text"  value=""  readonly="true" disabled="false">
    </td>
    <td colspan="3"><div align="left">发票金额大写(不含税)：
    	<input name="code2" id="capitalizationN" type="text" 	value=""  readonly="true" disabled="false">
	</td>
  </tr>
  
  <tr>
    <td><div align="right">一次出库</div></td>
    <td><div align="left">
      <s:checkbox name="oneOut"></s:checkbox>
    </div></td>
    <td nowrap><div align="right"><font color="red">*</font>取票地点： </div></td>
    <td><div align="left">
        <s:textfield name="billSpot" ></s:textfield>
    </div></td>
    <td  align="right" ><div align="right">库存组织： </div></td>
		<td align="left" >
				<s:select name="stockOrg" list="stockOrgList" listKey="typeSmall"  listValue="typeName" id="stockOrgId" ></s:select>
		</td>
  </tr>
  
  <tr>
    <td nowrap><div align="right"><font color="red">*</font>开票内容：</div></td>
    <td colspan="6">
      <div align="left">
        <s:textarea  cols="50" name="invoiceContent" id="billContent"></s:textarea>
      </div></td>
  </tr>
  
  
  <tr>
    <td  align="right">备注：</td>
    <td colspan="3" align="left" ><s:textarea cols="50" name="remarks" id="remarks"></s:textarea></td>
    <td  align="right">甲方接收人：</td>
	<td><s:textarea cols="20" rows="3"  name="firstReceiveMan" ></s:textarea></td>
  </tr>
  
<%--开收据--%>
<s:if test="comeFrom == 1 || comeFrom == 2">
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
</s:if>
<s:else>
</table>

<br/>
<table width="100%" border="1" class="bg_table02" bordercolor="#808080" style=" border-collapse: collapse;">
  <tr class="bg_table01">
    <td width="14%" align="right"><div align="center">申购关联：</div></td>
    <td width="10%" align="left"><div align="center">项目号</div></td>
    <td width="12%" align="left"><div align="center">申购单号</div></td>
    <td colspan="2" align="left"><div align="center">申购内容</div></td>
    <td align="right"><div align="center">操作</div></td>
  </tr>
  <s:iterator id="pl" value="messageList">
  <tr>
    <td><div align="center"></div></td>
    <td><div align="center"><s:property value="#pl.eventId"/></div></td>
    <td><div align="center"><s:property value="#pl.applyId"/></div></td>
    <td><div align="center"><s:property value="#pl.applyContent"/></div></td>
    <td><div align="center">
    <a href="#" onclick="javascript:deleter('<s:property value="#pl.id"/>')">删除</a></div>
    </td>
  </tr>
  </s:iterator>
  <tr>
    <td colspan="6" align="left"><div align="center"><a href="#"></a><a href="javascript:openPurchaseList();">添加 </a></div></td>
  </tr>
</s:else>
  <tr>
    <td  colspan="6" class="bg_table03">
      <div align="center">
        <input type="button" name="button2" id="button2" value="保  存" class="button01" onclick="javascript:if(!validate1()){saveApply(0);}">
        <input type="button" name="button" id="button" value="提交申请" class="button01" onclick="javascript:if(!validate1()){submitApply();}">
        <input type="button" name="button4" id="button4" value="关闭" class="button01"  
        onClick="window.close();">
      </div></td>
  </tr>
</table>



</s:form>
</body>
<script type="text/javascript">
	changeBillType(document.getElementById("invoiceType"));
	//statAmount();
	
function validate1(){
     var ev2=new Validator();
     with(applybill){
     <s:if test="comeFrom != 1 && comeFrom != 2 && && comeFrom != 3">
     	ev2.test("greater","申请金额不允许超过 "+$('maxAmountOrg').value , $('maxAmount').value , $('moneyApply').value   );
     </s:if>   
       ev2.test("notblank","取票地点不能为空",$('billSpot').value); 
       ev2.test("notblank","取票地点不能为空",$('billSpot').value); 
       ev2.test("notblank","开票内容不能为空",$('billContent').value); 
       ev2.test("length","开票内容请少于400字符",$('billContent').value,400); 
       ev2.test("length","备注请少于500字符",$('remarks').value,500);  

 	}
    if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}	

function change(){
	//var n = document.getElementById("maxAmountOrg").value;
	//document.getElementById("maxAmountOrg").value = formatNumber(document.getElementById('maxAmountOrg').value,'#,###.00');
	document.getElementById("money").value = document.getElementById('maxAmount').value;
	
	var f = document.getElementById("maxAmount");
	f.value = parseFloatNumber(f.value);
}
change();

</script>
</html>
