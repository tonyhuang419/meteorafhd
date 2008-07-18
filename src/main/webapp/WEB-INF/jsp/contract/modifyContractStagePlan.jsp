<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>修改阶段计划</title>
</head>
<body>
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
			      <td align="left" class="bg_table02"><font color="red">* </font>阶段金额：</td>
			      <td align="left" class="bg_table02"><s:textfield name="stagePlan.stageAmout" maxlength="15" /></td>
	      </tr> 
	      <tr class="bg_table02">
			      <td align="left" class="bg_table02"><font color="red">* </font>预计开票日期：</td>
			      <td align="left" class="bg_table02"><s:textfield id="makeInvoiceDate" name="stagePlan.makeInvoiceDate" readonly="true" onclick="ShowCalendar('makeInvoiceDate')" />
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
			ev2.test("+float","阶段金额必须是大于0的数字",form.elements("stagePlan.stageAmout").value);
			ev2.test("notblank","请选择预计开票日期",form.elements("stagePlan.makeInvoiceDate").value);
       } 
       if(ev2.size() == 0){
       		var remainAmount = parseFloatNumber('<s:property value="#remainPlanAmount"/>');
       		if(isNaN(remainAmount)){
       			remainAmount=0.0;
       		}
       		var oldAmount = parseFloatNumber("<s:property value="stagePlan.stageAmout" />");
       		var newAmount = parseFloatNumber(form.elements("stagePlan.stageAmout").value);
       		if(newAmount - oldAmount > remainAmount){
       			ev2.addError("阶段金额不能大于"+(oldAmount+remainAmount));
       		}
       }
       ev2.writeErrors(errorsFrame, "errorsFrame");
       if (ev2.size() > 0) {
	     return;
	  }else{
	  	form.submit();
	  }	
	}
</script>
</body>