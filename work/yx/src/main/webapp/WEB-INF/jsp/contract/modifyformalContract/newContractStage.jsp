<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同变更</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/public.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script type="text/javascript">
function setNum(i){
var hidden=document.getElementById("tag");
hidden.setAttribute("value",i);
}

function time(img){
  var par=img.parentNode;
  var text=par.firstChild;
  ShowCalendar(text.id);
}
</script>
<script language="javascript" for="document" event="onkeydown">

<!--
if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!=''){
	event.keyCode=9;
}
-->
</script>

<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
</head>
<body style="background-color: #FFFFFF;">
 <s:form name="formalContractModify" action="formalContractModify" theme="simple">
    	<!-- 切换tab页参数 -->
            <s:hidden name="tag"></s:hidden>
		 	<s:hidden name="isModify" ></s:hidden>
			<s:hidden name="clietId"/>
			<s:hidden name="isFromNewPage"/>
			<s:hidden name="method" value="saveStageInfo"/>
			<s:hidden name="stageIndex" value="-1"></s:hidden>
		<!-- 切换tab页参数 -->
<div align="left">
 <div  style="color:#000000">当前页面：合同管理 -&gt; 正式合同管理 -&gt;合同变更
</div>
 <div  align="left" style="color: #FF0000" >
<iframe name="errorsFrame" frameborder="0" width="100%"
				framespacing="0" height="0" scrolling="no"></iframe></div>
<table width="100%" height="100%" border="0"  align="center" cellpadding="1" cellspacing="1" class="bg_table02">
  <tr>
    <td  colspan="4"  align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td align="center"><table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
      </table></td>
  <tr>
    <td colspan="4" align="center" height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr class="bg_table02">
    <td colspan="4" align="center" class="bg_table02"><div id="container" class="bg_table02">
        <div id="title" class="bg_table02">
                 <%@ include file="/WEB-INF/jsp/contract/modifyformalContract/ContractTopTab.jsp"%> 

      </div>

        <div id="content" class="content1" >
            <div  id="content1"  >
           <!--合同主信息 -->

            </div>
   
          <div id="content2" class="hidecontent">
            <!--合同项目开始-->
        
              <!--合同项目结束-->
          </div>
          <div id="content3"    class="hidecontent">
           
            <!--开票和收款阶段结束-->
          </div>
          <div id="content4"   >
               <s:hidden name="maininfo.conSignDate" ></s:hidden>
               <s:hidden name="maininfo.conStartDate" ></s:hidden>       
               <s:hidden name="maininfo.conEndDate"></s:hidden>
              <!--开票和收款计划开始-->
               <table align="center" border="1" cellpadding="0" cellspacing="0" bordercolor="#808080" style=" border-collapse: collapse;" width="100%">
                <tr>
                  <td class="bg_table02" colspan="6"><div align="left">合同总金额（含税）:<s:property value="maininfo.conTaxTamount"/>&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                </tr>     
                <s:set name="planIndex" value="0"></s:set>          
                <s:iterator value="mainMoneyWithPlanAmountList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01" colspan="7"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,#maininfoPart[0].moneytype).typeName"/>&nbsp;&nbsp;&nbsp;&nbsp;总金额:<s:property value="#maininfoPart[0].money"/>&nbsp;&nbsp;&nbsp;&nbsp;</div></td>
                </tr>
                  	<tr>
                  		<td align="center" nowrap="nowrap">阶段名称</td>
                  		<td align="center" nowrap="nowrap">阶段辅助</td>
                  		<td align="center" nowrap="nowrap">开票金额</td>
                  		<td align="center" nowrap="nowrap">收款金额</td>
                  		<td align="center" nowrap="nowrap">开票类型</td>
                  		<td align="center" nowrap="nowrap">预计开票日期</td>
                  	</tr>
                  	   <s:iterator value="stagePlanlist">
                  		<s:if test="ccontractMaininfoPart.id == #maininfoPart[0].id">
	                  	<tr>
	                  		<td align="left"><s:property value="typeManageService.getYXTypeManage(1023,ccontractItemStage.itemStageName).typeName" /></td>
	                  		<td align="left"><s:property value="stageRemark"></s:property></td>
	                  		<td align="right"><s:hidden name="stagePlanlist[%{#planIndex}].id" value="%{id}"></s:hidden><s:textfield name="stagePlanlist[%{#planIndex}].stageAmout" value="%{stageAmout}" onblur="formatInputNumber(this)"></s:textfield></td>
	                  		<td align="right"><s:textfield name="stagePlanlist[%{#planIndex}].reveAmount" value="%{reveAmount}" onblur="formatInputNumber(this)"></s:textfield></td>
	                  		<td align="left" nowrap="nowrap"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
	                  		<td align="center"><s:property value="makeInvoiceDate"></s:property></td>
	                  		<td>&nbsp;</td>
	                  	</tr>
	                  	   <s:set name="planIndex" value="#planIndex+1"></s:set>  
	                  	</s:if>
                  	</s:iterator>
                  	<tr>
                  		<input type="hidden" name="addStagePlanList[<s:property value="#maininfoPartStatus.index"/>].ccontractMaininfoPart.id" value="<s:property value="#maininfoPart[0].id"/>"/>
	                	<td align="center" nowrap="nowrap"><font color="red">* </font><s:select name="addStagePlanList[%{#maininfoPartStatus.index}].ccontractItemStage.itemStageName" listKey="typeSmall" listValue="typeName" list="projectPhaseList"></s:select></td>
	                  		<td align="center" nowrap="nowrap"><input type="text" name="addStagePlanList[<s:property value="#maininfoPartStatus.index"/>].stageRemark" size="15" /></td>
	                  		<td align="center" nowrap="nowrap"><font color="red">* </font><input type="text" name="addStagePlanList[<s:property value="#maininfoPartStatus.index"/>].stageAmout" onblur="formatInputNumber(this);" size="12"></td>
	                  		<td align="center" nowrap="nowrap"><font color="red">* <input type="text" name="addStagePlanList[<s:property value="#maininfoPartStatus.index"/>].reveAmount" onblur="formatInputNumber(this)" size="12" /></td>
	                  		<td align="center" nowrap="nowrap"><font color="red">* </font><s:select name="addStagePlanList[%{#maininfoPartStatus.index}].ticketType" listKey="typeSmall" listValue="typeName" value="#maininfoPart[0].ticketType" list="%{contractservice1.getTicketTypeByPartId(#maininfoPart[0].ticketType)}"></s:select></td>
	                  		<td align="center" nowrap="nowrap"><font color="red">* </font><input type="text" name="addStagePlanList[<s:property value="#maininfoPartStatus.index"/>].makeInvoiceDate" id="stageAdd<s:property value="#maininfoPartStatus.index" />" size="10"/>
	                  		<img src="/yx/commons/images/calendar.gif" onClick="ShowCalendar('stageAdd<s:property value="#maininfoPartStatus.index" />')" align=absMiddle alt=""  />
	                  		</td>
	                  		<td align="center" nowrap="nowrap"><a href="javascript:if(!validateAdd('<s:property value="#maininfoPartStatus.index" />')){setNum(2);addStage('<s:property value="#maininfoPartStatus.index" />');}" >新增</a></td>
	                </tr>
               </s:iterator>
              </table>
            <input type="hidden" name="stagePlanIndex" value="<s:property value="#planIndex"/>"/>
         
            <!--开票和收款计划结束-->
          </div>
          <div id="content5" class="hidecontent" >
            <!--未签开票关联开始-->
            <!--已关联清单开始-->
      
  
            <!--申购清单结束-->
            <!--未签申购清单开始-->
            <!--未签申购清单结束-->
            <!--未签申购关联结束-->
          </div>
          <div id="content7" class="hidecontent">
              <!--自有产品开始-->
           
              <!--自有产品结束-->
          </div>
          <div id="content8" class="hidecontent" >
              <!--else-->
            
          </div>
  </div>
        <div align="center">
          <input  type="button" value="保存"  onClick="if(!validate()){setNum(2);document.formalContractModify.submit();}" class="button01" />   
          <input type="button" value="申请提交" onclick="if(!validate()){setNum(0);tijiao();}" class="button01"/>   
           <input type="button" value="合同拆分导出" onclick="javascript:window.open('/yx/contract/contractSplitTable.action?method=doDefaultChange&contractMainInfoSid=<s:property value="maininfo.conMainInfoSid"/>');" class="button01"> 
          <input type="button" value="关闭" onclick="window.close();" class="button01"/> 
        </div>
</s:form>
<!--else-->
<script type="text/javascript">
function tijiao()
{
	document.formalContractModify.method.value="applySubmitByStagePlan";
	document.formalContractModify.submit();
}

/////////////////////////
var existStage = new Array();
<s:iterator value="stagePlanlist">
existStage.push("<s:property value="ccontractMaininfoPart.id"/>_<s:property value="ccontractItemStage.itemStageName"/>");
</s:iterator>
//其他信息，用来读取固定时间配置
var typeManageHash = new Hash();
<s:iterator value="typeManageService.getYXTypeManage(1023)">
typeManageHash.set("<s:property value="typeSmall"/>","<s:property value="info"/>");
</s:iterator>
/////////////////////////
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
		var remAmount = parseFloatNumber(form.remainStatePlanAmount.value);
		var totalAmount = parseFloatNumber(form.totalStatePlanAmount.value);
		var phaseAmount = totalAmount * percent;
		if(phaseAmount > remAmount){
			form.elements("stagePlan.stageAmout").value = number_format(remAmount);
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
function deleteStagePlan(planId){
	if(confirm("确定要删除吗？")){
		document.deleteStagePlanForm.elements("stagePlan.id").value = planId;
		document.deleteStagePlanForm.submit();
	}
}
//tab切换时调用
function validate(){
 	var ev2=new Validator();
 	var count = document.formalContractModify.stagePlanIndex.value;
 	for(var k=0;k<count;k++){
 		var planReveMoney = document.formalContractModify.elements("stagePlanlist["+k+"].reveAmount").value;
 		var planStageMoney = document.formalContractModify.elements("stagePlanlist["+k+"].stageAmout").value;
 		ev2.test("float","收款金额金额必须为大于0的数字！",planReveMoney);
 		ev2.test("float","开票金额金额必须为大于0的数字！",planStageMoney);
 	}
	 ev2.writeErrors(errorsFrame, "errorsFrame");
     if(ev2.size()>0){
     	return true;
     }
     return false;
}

function validateAdd(buttonobj){
	 var ev2=new Validator();
	 var form = document.formalContractModify;
	 var beginForm = "addStagePlanList["+buttonobj+"]";
	 //alert(beginForm);
	      with(form){
       		ev2.test("notblank","请选择阶段",form.elements(beginForm+".ccontractItemStage.itemStageName").value);
       		if(form.elements(beginForm+".ticketType").value=="4"){
       			ev2.test("+float+0","阶段金额必须是大于等于0的数字",form.elements(beginForm+".stageAmout").value);
       		}else{
				ev2.test("+float","阶段金额必须是大于0的数字",form.elements(beginForm+".stageAmout").value);
			}
			ev2.test("+float","收款金额必须是大于0的数字",form.elements(beginForm+".reveAmount").value);
			ev2.test("notblank","请选择预计开票日期",form.elements(beginForm+".makeInvoiceDate").value);
			ev2.test("dateYX","预计开票日期格式不正确",form.elements(beginForm+".makeInvoiceDate").value);
			var stageDate=form.elements(beginForm+".makeInvoiceDate").value;
			var conStartDate = document.getElementById("formalContractModify_maininfo_conStartDate").value;
			var conEndDate = document.getElementById("formalContractModify_maininfo_conEndDate").value;
			
			//转成成数组，分别为年，月，日，下同
			var arrJHRQ=stageDate.split('-'); //预计开票日期数组
            var arrJHWCSJ=conStartDate.split('-');//合同起始日期数组
            var arrSignDate=conEndDate.split('-');//合同结束日期数组
            //新建日期对象
            var dateStage=new Date(parseInt(arrJHRQ[0]),parseInt(arrJHRQ[1])-1,parseInt(arrJHRQ[2]),0,0,0); //预计开票日期
            var dateStartDate=new Date(parseInt(arrJHWCSJ[0]),parseInt(arrJHWCSJ[1])-1,parseInt(arrJHWCSJ[2]),0,0,0);//合同起始日期
            var dateEndDate=new Date(parseInt(arrSignDate[0]),parseInt(arrSignDate[1])-1,parseInt(arrSignDate[2]),0,0,0);//合同结束日期
   
            if(dateStage.getTime()<dateStartDate.getTime()) {
               ev2.addError("预计开票日期小于合同起始日期");
            }   
       
            if(dateStage.getTime()>dateEndDate.getTime()){
               ev2.addError("预计开票日期大于合同结束日期");
            } 
			
       }
	   //已经存在且不是固定时间，判断重复
       if(ev2.size() == 0 && existStage.contains(form.elements(beginForm+".ccontractMaininfoPart.id").value+"_"+form.elements(beginForm+".ccontractItemStage.itemStageName").value)){
       	    if(!confirm("阶段名称重复，是否仍要添加？")){
       	    	return true;
       	    }
       }
       ev2.writeErrors(errorsFrame, "errorsFrame");
       if (ev2.size() > 0) {
	     return true;
	   }
	  	 return false;
	 
	
}
function addStage(buttonobj){
	document.formalContractModify.stageIndex.value=buttonobj;
	//alert("aa:  "+document.formalContractModify.stageIndex.value);
	document.formalContractModify.method.value = "addStageInfo";
	document.formalContractModify.submit();
}

function validateAddForm(buttonObj){

	   var ev2=new Validator();
	   var form = getOwnerForm(buttonObj);
       with(form){
       		ev2.test("notblank","请选择阶段",form.elements("stagePlan.contractItemStage.itemStageName").value);
       		if(form.elements("stagePlan.ticketType").value=="4"){
       			ev2.test("+float+0","阶段金额必须是大于等于0的数字",form.elements("stagePlan.stageAmout").value);
       		}else{
				ev2.test("+float","阶段金额必须是大于0的数字",form.elements("stagePlan.stageAmout").value);
			}
			ev2.test("+float","收款金额必须是大于0的数字",form.elements("stagePlan.reveAmount").value);
			ev2.test("notblank","请选择预计开票日期",form.elements("stagePlan.makeInvoiceDate").value);
			ev2.test("dateYX","预计开票日期格式不正确",form.elements("stagePlan.makeInvoiceDate").value);
			var stageDate=form.elements("stagePlan.makeInvoiceDate").value;
			var conStartDate = document.getElementById("maininfo_conStartDate").value;
			var conEndDate = document.getElementById("maininfo_conEndDate").value;
			
			//转成成数组，分别为年，月，日，下同
			var arrJHRQ=stageDate.split('-'); //预计开票日期数组
            var arrJHWCSJ=conStartDate.split('-');//合同起始日期数组
            var arrSignDate=conEndDate.split('-');//合同结束日期数组
            //新建日期对象
            var dateStage=new Date(parseInt(arrJHRQ[0]),parseInt(arrJHRQ[1])-1,parseInt(arrJHRQ[2]),0,0,0); //预计开票日期
            var dateStartDate=new Date(parseInt(arrJHWCSJ[0]),parseInt(arrJHWCSJ[1])-1,parseInt(arrJHWCSJ[2]),0,0,0);//合同起始日期
            var dateEndDate=new Date(parseInt(arrSignDate[0]),parseInt(arrSignDate[1])-1,parseInt(arrSignDate[2]),0,0,0);//合同结束日期
   
            if(dateStage.getTime()<dateStartDate.getTime()) {
               ev2.addError("预计开票日期小于合同起始日期");
            }   
       
            if(dateStage.getTime()>dateEndDate.getTime()){
               ev2.addError("预计开票日期大于合同结束日期");
            } 
			
       }
       /* 由于含税不含税问题，先不验证
       if(ev2.size() == 0 && parseFloatNumber(form.elements("stagePlan.reveAmount").value) > parseFloatNumber(form.elements("stagePlan.stageAmout").value)){
	  		ev2.addError("收款金额不能大于阶段金额");
	   }*/
       if(ev2.size() == 0 && form.elements("stagePlan.ticketType").value != 4 && parseFloatNumber(form.elements("stagePlan.stageAmout").value) > parseFloatNumber(form.remainStatePlanAmount.value)){
	  		if(parseFloatNumber(form.remainStatePlanAmount.value) == 0){
	  			ev2.addError("没有可分配的阶段金额");
	  		}else{
	  			ev2.addError("阶段金额不能大于"+form.remainStatePlanAmount.value);
	  		}
	   }
	   //
	   var remainReceAmount = parseFloatNumber("<s:property value="remainReceAmount"/>");
	   if(ev2.size() == 0 && parseFloatNumber(form.elements("stagePlan.reveAmount").value) > remainReceAmount){
	   		ev2.addError("收款金额不能大于合同可分配收款金额"+remainReceAmount);
	   }
	   //已经存在且不是固定时间，判断重复
       if(ev2.size() == 0 && existStage.contains(form.elements("stagePlan.contractMaininfoPart.id").value+"_"+form.elements("stagePlan.contractItemStage.itemStageName").value)){
       	    if(!confirm("阶段名称重复，是否仍要添加？")){
       	    	return false;
       	    }
       }
       ev2.writeErrors(errorsFrame, "errorsFrame");
       if (ev2.size() > 0) {
	     return false;
	   }else{
	  	 return true;
	  }
}
function editStagePlan(planId){
	window.open('/yx/contract/contract.action?method=enterEditStagePlan&stagePlan.id='+planId,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=300,width=500');
}
function refreshPage(){
	document.forms(0).submit();
}
function stageDuplicate(){
 var ev2=new Validator();
 ev2.addError("阶段重复");
 ev2.writeErrors(errorsFrame, "errorsFrame");
}
function hasAmountToAssign(){
	var has = false;
	<s:iterator value="mainMoneyWithPlanAmountList" id="maininfoPart" status="maininfoPartStatus">
		if( parseFloatNumber('<s:property value="#maininfoPart[0].money - #maininfoPart[1]"/>') != 0){
			has = true;
		}
	</s:iterator>
	var remainReceAmount = parseFloatNumber("<s:property value="remainReceAmount"/>");
	if(remainReceAmount != 0){
		has = true;
	}
	return has;
}
<s:if test="#stageDuplicate == true">
stageDuplicate();
</s:if>
</script>
</body>
</html>
