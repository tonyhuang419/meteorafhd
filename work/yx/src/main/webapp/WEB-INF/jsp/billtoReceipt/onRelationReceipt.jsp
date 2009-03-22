<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>关联收据计划</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
	function showNum(){
   		 var checkArr = document.getElementsByName("billAndReceiptList[<s:property value="#planIndex"/>].receiptRealId");
  		 var checkStr="";
         for(var i=0;i<checkArr.length;i++){
            if(checkArr[i].checked){
                 checkStr+=","+checkArr[i].value;
             }
         }
         if(checkStr.length>1){
        	checkStr=checkStr.substring(1);
        	return checkStr;
        }
	}

	//通过checkbox框拿到对应的文本框的value值
	function getInputValue(cobj){
		var trNode=cobj.parentNode.parentNode;
		var tdNode=trNode.lastChild;
		var inputNode=tdNode.firstChild;
		return inputNode.value;
	}
	//选中checkBox时候触发
	function checkoneBox(billVal,receitVal,cobj)
	{
		var trNode=cobj.parentNode.parentNode;
		var tdNode=trNode.lastChild;
		var inputNode=tdNode.firstChild;
		if(cobj.checked){
			inputNode.disabled=false;	
			checkInputValue(billVal,receitVal,inputNode);
		}else{
			inputNode.disabled=true;
			var billAmount = parseFloatNumber(document.getElementById('billAmount'+billVal).value);  // 票的可关联总额
			var receBalance = parseFloatNumber($("remainReceipt"+receitVal).value);
			var remainBillAmountTd = document.getElementById('billyue'+billVal);     //关联余额
			var inputReceiptAmount = parseFloatNumber(inputNode.value); // 文本框输入的值
			remainBillAmountTd.innerHTML = number_format(billAmount - getRelationAmount(billVal)) ;
			inputNode.value = number_format(receBalance);
			caculateRemainReceiptAmount(receitVal);
		}
	}
	//获取checkBox的值,获取收据Id一样的收据余额  
	// 同一页面上相同收据的能关联余额需要一起改变
	function caculateRemainReceiptAmount(receitVal){
		var checkBoxs = $$("input[type='checkbox'][value="+receitVal+"]");
		var receBalance = parseFloatNumber($("remainReceipt"+receitVal).value);   //收款可以关联的余额
		var inputAmount = 0;
		for(var i=0;i<checkBoxs.length;i++){
			var chk = checkBoxs[i];
			if(chk.checked){
				inputAmount+=parseFloatNumber(getInputElement(chk.parentNode.parentNode).value);
			}
		}
		for(var i=0;i<checkBoxs.length;i++){
			var chk = checkBoxs[i];
			getRemainReciptAmountTd(chk.parentNode.parentNode).innerHTML = number_format(receBalance - inputAmount);
		}		
	}
	
	//获取收据关联余额
	function caculateRemainReceiptExcept(receitVal,exceptInput){
		var checkBoxs = $$("input[type='checkbox'][value="+receitVal+"]");
		var receBalance = parseFloatNumber($("remainReceipt"+receitVal).value);   //收款可以关联的余额
		var inputAmount = 0;
		for(var i=0;i<checkBoxs.length;i++){
			var chk = checkBoxs[i];
			var inpuObj = getInputElement(chk.parentNode.parentNode);
			if(chk.checked && inpuObj.name != exceptInput.name){
				inputAmount+=parseFloatNumber(inpuObj.value);
			}
		}
		return receBalance - inputAmount;
	}
	//获取关联金额的位置
	function getInputElement(trObj){
		return trObj.cells(8).firstChild;
	}
	// 获取收款余额的位置
	function getRemainReciptAmountTd(trObj){
		return trObj.cells(7);
	}
	
	function checkInputValue(billVal,receitVal,inputVal){
		var remainReciptAmount = caculateRemainReceiptExcept(receitVal,inputVal); // 收据关联余额
		// 输入值比收据可关联的余额大
		if(parseFloatNumber(inputVal.value) > remainReciptAmount){
			inputVal.value= number_format(remainReciptAmount);
		}
		///////////////////////////////////
		var inputReceiptAmount = parseFloatNumber(inputVal.value); // 文本框输入的值
		var inputValues = getRelationAmount(billVal); //所有输入关联金额总额
		var billAmount = parseFloatNumber(document.getElementById('billAmount'+billVal).value);  // 票的可关联总额
		var remainBillAmountTd = document.getElementById('billyue'+billVal);     //关联余额
		var remainBillAmount = parseFloatNumber(remainBillAmountTd.innerHTML);
		////////////////////////////////		
		// 发票可关联余额大于输入值总额
		if(billAmount >= inputValues){
			remainBillAmountTd.innerHTML = number_format(billAmount - inputValues);
		}
		else{
			//关联金额大于可关联余额
			inputVal.value = number_format(Math.min(billAmount - inputValues + inputReceiptAmount , remainReciptAmount));
			remainBillAmountTd.innerHTML = number_format(billAmount - getRelationAmount(billVal));
		}
		caculateRemainReceiptAmount(receitVal);
	}
	//求出所有开票下面所输入的收据关联金额
	function getRelationAmount(billVal){
	    var amount = 0;
		var inputTrs = $$("[id=tr"+billVal+"]");
		for(var i = 0 ;i < inputTrs.length ; i++){
			var inputTxt = getInputElement(inputTrs[i]);
			if(inputTxt.disabled == false){
				amount+=parseFloatNumber(inputTxt.value);
			}
		}
		return amount;
	}
	
	function checkBillAmountIsNotNumm(){
		var inputTrs = $$("[id=bigTr]");
		for(var i = 0 ;i < inputTrs.length ; i++){
			var inputTxt = inputTrs[i].cells(7);
			if(inputTxt.innerHTML != 0.00){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	function saveSubmit(){
		if(!checkBillAmountIsNotNumm()){
			document.forms(0).submit();
		}
		else{
			alert("您选取的票据还没关联完收据!");
		}
	}
</script>
</head>
<body>
<s:form action="applyBill" theme="simple">
<s:hidden name="method" value="relationBillRecipt"></s:hidden>
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<div align="left">
				<p>当前页面：开票管理 -> 计划开票关联计划收据</p>
			</div>
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill"  bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td class="bg_table01">操作</td>
					<td class="bg_table01">合同号</td>
					<td class="bg_table01">合同名称</td>
					<td class="bg_table01">负责部门</td>
					<td class="bg_table01">阶段</td>
					<td class="bg_table01">开票金额</td>
					<td class="bg_table01">收款金额</td>
					<td class="bg_table01">关联余额</td>
					<td class="bg_table01">关联金额</td>
				</tr>
			<s:set name="planIndex" value="0"></s:set>
			<s:iterator value="billList" id="month" status="status">
				<input type="hidden" name="billInvoiceAmount" value="<s:property value="#month[0].billInvoiceAmount"/>"/>
				<input type="hidden" name="realReceAmount" value="<s:property value="#month[0].realReceAmount"/>"/>
				<input type="hidden" name="billid" value="<s:property value="#month[0].realConBillproSid"/>"/>
				<input type="hidden" id="billAmount<s:property value="#month[0].realConBillproSid"/>" value="<s:property  value="#month[0].realTaxBillAmount - #month[0].realTaxReceAmount - #month[0].relationReceAmount" />"/>
					<tr align="center" id="bigTr" class="bg_table02" onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
						<td></td>
						<td align="left"><s:property value="#month[2].conId" /></td>
						<td align="left"><s:property value="#month[2].conName" /></td>
						<td align="left">
							<s:if test="#month[4] != null">
								<s:property value="typeManageService.getYXTypeManage(1018,#month[4]).typeName" />
							</s:if>
							<s:else>
								<s:property value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName" />
							</s:else>
						</td>
						<td align="left"><s:property
							value="typeManageService.getYXTypeManage(1023,#month[3]).typeName"  /></td>
							<td align="right"><s:property value="#month[0].realTaxBillAmount" /></td>
							<td align="right"><s:property value="#month[0].realTaxReceAmount" /></td>
						<td align="right" id="billyue<s:property value="#month[0].realConBillproSid"/>">
							<s:property value="#month[0].realTaxBillAmount - #month[0].realTaxReceAmount - #month[0].relationReceAmount " />
						</td>
						<td></td>
					</tr>
					<s:iterator value="receiptList" id="receipt" status="status">
						<s:if test="#receipt[0].billNature == #month[0].billNature">
							<tr align="center" id="tr<s:property value="#month[0].realConBillproSid"/>" >
								<td>
									<input type="checkbox" onclick="checkoneBox(<s:property value="#month[0].realConBillproSid"/>,<s:property value="#receipt[0].realConBillproSid"/>,this)"  
									 <s:if test="#receipt[0].realTaxReceAmount - #receipt[0].relationReceAmount == 0.00"> 
									 	disabled="disabled"
									 </s:if>
										name="billAndReceiptList[<s:property value="#planIndex"/>].receiptRealId" value="<s:property value="#receipt[0].realConBillproSid"/>" />
										<input type="hidden" name="billAndReceiptList[<s:property value="#planIndex"/>].billRealId" value="<s:property value="#month[0].realConBillproSid"/>" />
								</td>
								<td align="left"><s:property value="#receipt[2].conId" /></td>
								<td align="left"><s:property value="#receipt[2].conName" /></td>
								<td align="left">
								<s:if test="#receipt[4] != null">
									<s:property value="typeManageService.getYXTypeManage(1018,#receipt[4]).typeName" />
								</s:if>
								<s:else>
									<s:property value="typeManageService.getYXTypeManage(1018,#receipt[2].mainItemDept).typeName" />
								</s:else>
							</td>
							<td align="left"><s:property
								value="typeManageService.getYXTypeManage(1023,#receipt[3]).typeName"  /></td>
								<td align="right"><s:property value="#receipt[0].realTaxBillAmount" /></td>
								<td align="right">
									<s:property value="#receipt[0].realTaxReceAmount" />
								</td>
								<td align="right" id="guanlianyue<s:property value="#receipt[0].realConBillproSid"/><s:property value="#month[0].realConBillproSid"/>">
									<s:property value="#receipt[0].realTaxReceAmount - #receipt[0].relationReceAmount"/>
								</td>
								
								<td align="right">
									<s:textfield name="billAndReceiptList[%{#planIndex}].relationAmount" 
									value="%{#receipt[0].realTaxReceAmount - #receipt[0].relationReceAmount}" 
									size="15" onblur="formatInputNumber(this);checkInputValue('%{#month[0].realConBillproSid}','%{#receipt[0].realConBillproSid}',this)" disabled="true"></s:textfield>
								
									<input type="hidden" id="remainReceipt<s:property value="#receipt[0].realConBillproSid"/>" value="<s:property  value="#receipt[0].realTaxReceAmount - #receipt[0].relationReceAmount" />"/>
								</td>
							</tr>
							
							<s:set name="planIndex" value="%{#planIndex + 1}"></s:set>
						</s:if>
					</s:iterator>
			</s:iterator>
					<tr class="bg_table03">
						<td colspan="12" align="center">
							<input type="button" name="21" value=" 保  存 " onclick="saveSubmit()" class="button01"/>
							<input type="button" name="23" value=" 返  回 " onclick="window.close();" class="button01" />
						</td>
					</tr>					
			</table>
		</tr>
	</table>		
</s:form>
</body>
</html>