<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>修改阶段计划</title>
</head>
<body>
<script language="javascript" for="document" event="onkeydown">

<!--
if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!=''){
	event.keyCode=9;
}
-->
</script>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:form action="contract" theme="simple">
<s:hidden name="stagePlan.id"></s:hidden>
<s:hidden name="method" value="updateStageInfo"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td align="center"> 
		<table align="center"  border=1  cellpadding=1 cellspacing=1 width="100%">
          	<tr class="bg_table02">
			      <td width="100"  align="left" class="bg_table02">费用名称：</td>
			      <td align="left" class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1012,stagePlan.contractMaininfoPart.moneytype).typeName" /></td>
	      </tr>   
          <tr class="bg_table02">
			      <td align="left" class="bg_table02">阶段名称：</td>
			      <td align="left" class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1023,stagePlan.contractItemStage.itemStageName).typeName" /></td>
	      </tr> 
          <tr class="bg_table02">
			      <td align="left" class="bg_table02">阶段辅助：</td>
			      <td align="left" class="bg_table02"><s:textfield name="stagePlan.stageRemark" /></td>
	      </tr> 
          <tr class="bg_table02">
			      <td align="left" class="bg_table02">百分比：</td>
			      <td align="left" class="bg_table02"><input type="text" name="percentAmount" size="1" onblur="calculateByPercent(this)" maxlength="3"/>%</td>
	      </tr>         
          <tr class="bg_table02">
			      <td align="left" class="bg_table02"><font color="red">* </font>阶段金额：</td>
			      <td align="left" class="bg_table02"><s:textfield name="stagePlan.stageAmout" onblur="formatInputNumber(this);resetReveAmount(this)" maxlength="15" /></td>
	      </tr> 
          <tr class="bg_table02">
			      <td align="left" class="bg_table02"><font color="red">* </font>收款金额：</td>
			      <td align="left" class="bg_table02"><s:textfield name="stagePlan.reveAmount" onblur="formatInputNumber(this)" maxlength="15" /></td>
	      </tr> 
          <tr class="bg_table02">
			      <td align="left" class="bg_table02"><font color="red">* </font>开票类型：</td>
			      <td align="left" class="bg_table02"><s:select name="stagePlan.ticketType" listKey="typeSmall" listValue="typeName" list="tickettype"></s:select></td>
	      </tr> 
	      <tr class="bg_table02">
			      <td align="left" class="bg_table02"><font color="red">* </font>预计开票日期：</td>
			      <td align="left" class="bg_table02"><s:textfield id="makeInvoiceDate" name="stagePlan.makeInvoiceDate" />
			      <img src="/yx/commons/images/calendar.gif" onclick="ShowCalendar('makeInvoiceDate')" align=absMiddle alt=""  />
			      </td>
	      </tr> 
		  <tr align="center">
                  <td colspan="2" class="bg_table03">
                  <input type="button" name="SaveBtn" onclick="saveStagePlan(this)" value="　保存　" class="button02">
               	</td>
	      </tr>
		</table>
</table>
</s:form>
<script type="text/javascript">
	function saveStagePlan(buttonObj){
	   var ev2=new Validator();
	   var form = getOwnerForm(buttonObj);
       with(form){    
			ev2.test("+float+0","阶段金额必须是数字",form.elements("stagePlan.stageAmout").value);
			ev2.test("+float","收款金额必须是大于0的数字",form.elements("stagePlan.reveAmount").value);			
			ev2.test("notblank","请选择预计开票日期",form.elements("stagePlan.makeInvoiceDate").value);
			ev2.test("dateYX","预计开票日期格式不正确",form.elements("stagePlan.makeInvoiceDate").value);
       } 
       if(ev2.size() == 0){
       		var remainAmount = parseFloatNumber('<s:property value="#remainPlanAmount"/>');
       		if(isNaN(remainAmount)){
       			remainAmount=0.0;
       		}
       		var oldAmount = parseFloatNumber("<s:property value="stagePlan.stageAmout" />");
       		var newAmount = parseFloatNumber(form.elements("stagePlan.stageAmout").value);
       		if(newAmount - oldAmount > remainAmount && form.elements("stagePlan.ticketType").value != '4' ){
       			ev2.addError("阶段金额不能大于"+(oldAmount+remainAmount));
       		}
       }
       /* 由于含税不含税问题，先不验证
       if(ev2.size() == 0 && parseFloatNumber(form.elements("stagePlan.reveAmount").value) > parseFloatNumber(form.elements("stagePlan.stageAmout").value)){
	  		ev2.addError("收款金额不能大于阶段金额");
	   }*/
	   var remainReceAmount = parseFloatNumber("<s:property value="remainReceAmount"/>")+parseFloatNumber("<s:property value="stagePlan.reveAmount" />");
	   	   if(ev2.size() == 0 && parseFloatNumber(form.elements("stagePlan.reveAmount").value) > remainReceAmount){
	   		ev2.addError("收款金额不能大于"+remainReceAmount);
	   }
	   
       ev2.writeErrors(errorsFrame, "errorsFrame");
       if (ev2.size() > 0) {
	     return;
	  }else{
	  	form.submit();
	  }	
	}
function calculateByPercent(percentInputObj){
	var ev2=new Validator();
	var form = getOwnerForm(percentInputObj);
	if(percentInputObj.value.length >0){
		ev2.test("+integer","百分比必须是大于0的整数",form.elements("percentAmount").value);
		form.elements("stagePlan.stageAmout").value = "";
	}else{
		return;
	}
	if(ev2.size()==0){
		if(parseFloatNumber(form.elements("percentAmount").value)>100 ){
			ev2.addError("百分数不能大于100");
			form.elements("stagePlan.stageAmout").value = "";
		}
	}
	ev2.writeErrors(errorsFrame, "errorsFrame");
	if(ev2.size()==0){
		var percent = parseFloat(percentInputObj.value)/100;
   		var remAmount = parseFloatNumber('<s:property value="#remainPlanAmount"/>');
   		if(isNaN(remAmount)){
   			remAmount=0.0;
   		}
		var totalAmount = parseFloatNumber('<s:property value="stagePlan.contractMaininfoPart.money"/>');
		var oldAmount = parseFloatNumber("<s:property value="stagePlan.stageAmout" />");
		var phaseAmount = totalAmount * percent;
		if(phaseAmount > ( remAmount + oldAmount )){
			form.elements("stagePlan.stageAmout").value = number_format(remAmount + oldAmount) ;
			form.elements("stagePlan.reveAmount").value = form.elements("stagePlan.stageAmout").value;
		}else{
			form.elements("stagePlan.stageAmout").value = number_format(phaseAmount);
			form.elements("stagePlan.reveAmount").value = form.elements("stagePlan.stageAmout").value;
		}
	}
}
function resetReveAmount(phaseAmountInputObj){
	var form = getOwnerForm(phaseAmountInputObj);
	form.elements("stagePlan.reveAmount").value = form.elements("stagePlan.stageAmout").value;
}
</script>
</body>