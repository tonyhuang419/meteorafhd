<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同新建</title>
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
<div align="left">
    <s:if test="isModify==0" >
  <div  style="color:#000000">当前页面：合同管理 -&gt; 合同新建</div>
</s:if>
<s:if test="isModify==1">
 <div  style="color:#000000">当前页面：合同管理 -&gt; 草稿合同修改</div>
</s:if>
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
             <s:form name="contract" action="contract" theme="simple">
             	<s:hidden name="method" value="toPage" />
                  <%@ include file="/WEB-INF/jsp/contract/ContractTopTab.jsp"%>  
             </s:form>
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
                  <td class="bg_table02" colspan="7"><div align="left">合同总金额（含税）:<s:property value="maininfo.conTaxTamount"/>&nbsp;&nbsp;&nbsp;&nbsp;未分配收款金额:<s:property value="remainReceAmount"/></div></td>
                </tr>               
                <s:iterator value="mainMoneyWithPlanAmountList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01" colspan="7"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,#maininfoPart[0].moneytype).typeName"/>&nbsp;&nbsp;&nbsp;&nbsp;总金额:<s:property value="#maininfoPart[0].money"/>&nbsp;&nbsp;&nbsp;&nbsp;未分配开票金额:<s:property value="#maininfoPart[0].money - #maininfoPart[1]"/></div></td>
                </tr>
                  	<tr>
                  		<td align="center" nowrap="nowrap">阶段名称</td>
                  		<td align="center" nowrap="nowrap">阶段辅助</td>
                  		<td align="center" nowrap="nowrap">开票金额</td>
                  		<td align="center" nowrap="nowrap">收款金额</td>
                  		<td align="center" nowrap="nowrap">开票类型</td>
                  		<td align="center" nowrap="nowrap">预计开票日期</td>
                  		<td align="center" nowrap="nowrap">操作</td>
                  	</tr>
                  	   <s:iterator value="stagePlanlist">
                  		<s:if test="contractMaininfoPart.id == #maininfoPart[0].id">
	                  	<tr>
	                  		<td align="left"><s:property value="typeManageService.getYXTypeManage(1023,contractItemStage.itemStageName).typeName" /></td>
	                  		<td align="left"><s:property value="stageRemark"></s:property></td>
	                  		<td align="right"><s:property value="stageAmout"></s:property></td>
	                  		<td align="right"><s:property value="reveAmount"></s:property></td>
	                  		<td align="left" nowrap="nowrap"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
	                  		<td align="center"><s:property value="makeInvoiceDate"></s:property></td>
	                  		<td align="center" nowrap="nowrap"><a href="javascript:editStagePlan(<s:property value="id"/>)" >修改</a>   <a href="javascript:deleteStagePlan(<s:property value="id"></s:property>)" >删除</a></td>
	                  	</tr>
	                  	</s:if>
                  	</s:iterator>
                  	<s:if test="#maininfoPart[0].money - #maininfoPart[1] > 0 || remainReceAmount > 0">
                  	<s:form name="stagePlanAddForm%{#maininfoPart[0].id}" id="stagePlanAddForm" action="contract" theme="simple">
	                  	<s:hidden name="method" value="saveStageInfo" />
	                  	<input type="hidden" name="remainStatePlanAmount" value="<s:property value="#maininfoPart[0].money - #maininfoPart[1]" />"/>
	                  	<input type="hidden" name="totalStatePlanAmount" value="<s:property value="#maininfoPart[0].money" />"/>
	                  	<input type="hidden" name="stagePlan.contractMainSid" value="<s:property value="mainid" />"/>
	                  	<input type="hidden" name="stagePlan.contractMaininfoPart.id" value="<s:property value="#maininfoPart[0].id" />"/>
	                  	
	                  	<!-- 切换tab页参数 -->
	                  	<s:hidden name="tag"></s:hidden>
						<s:hidden name="mainid"/>
						<s:hidden name="isModify" ></s:hidden>
						<s:hidden name="clietId"/>
						<s:hidden name="isFromNewPage"/>
						<!-- 切换tab页参数 -->
	                  	<tr>
	                  		<td align="center" nowrap="nowrap"><font color="red">* </font><s:select name="stagePlan.contractItemStage.itemStageName" listKey="typeSmall" listValue="typeName" list="projectPhaseList"></s:select></td>
	                  		<td align="center" nowrap="nowrap"><input type="text" name="stagePlan.stageRemark" size="15" /></td>
	                  		<td align="center" nowrap="nowrap"><font color="red">* </font><input type="text" name="percentAmount" size="1" onblur="calculateByPercent(this)" maxlength="3"/>%  <input type="text" name="stagePlan.stageAmout" onblur="formatInputNumber(this);resetReveAmount(this)" size="12"></td>
	                  		<td align="center" nowrap="nowrap"><font color="red">* <input type="text" name="stagePlan.reveAmount" onblur="formatInputNumber(this)" size="12" /></td>
	                  		<s:if test="#maininfoPart[0].money - #maininfoPart[1] > 0 ">
	                  			<td align="center" nowrap="nowrap"><font color="red">* </font><s:select name="stagePlan.ticketType" listKey="typeSmall" listValue="typeName" value="#maininfoPart[0].ticketType" list="%{contractservice.getTicketTypeByPartId(#maininfoPart[0].ticketType)}"></s:select></td>
	                  		</s:if>
	                  		<s:else>
	                  			<td align="left" nowrap="nowrap">收据<input type="hidden" name="stagePlan.ticketType" value="4"></td>
	                  		</s:else>
	                  		<td align="center" nowrap="nowrap"><font color="red">* </font><input type="text" name="stagePlan.makeInvoiceDate" id="stageAdd<s:property value="#maininfoPartStatus.index" />" size="10"/>
	                  		<img src="/yx/commons/images/calendar.gif" onClick="ShowCalendar('stageAdd<s:property value="#maininfoPartStatus.index" />')" align=absMiddle alt=""  />
	                  		</td>
	                  		<td align="center" nowrap="nowrap"><a href="javascript:if(validateAddForm(document.stagePlanAddForm<s:property value="#maininfoPart[0].id" />)){getOwnerForm(document.stagePlanAddForm<s:property value="#maininfoPart[0].id" />).submit();}" >新增</a></td>
	                  	</tr>
                  	</s:form>
                  	</s:if>
               </s:iterator>
              </table>
            
         
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
  </div><!--总体结束DIV-->
  <s:form name="bottomSubmitFrom" id="bottomSubmitFrom" action="contract" theme="simple">
		<s:hidden name="method" value="toPage" />
	    <!-- 切换tab页参数 -->
	    <s:hidden name="tag"></s:hidden>
		<s:hidden name="mainid"/>
		<s:hidden name="isModify" ></s:hidden>
		<s:hidden name="clietId"/>
		<s:hidden name="isFromNewPage"/>
		<!-- 切换tab页参数 -->
        <div align="center">
           <s:if test="isModify==0" >
            <input  type="submit" value="保    存" class="button02"/>
            <input  type="button" value="保存并关闭" onClick=" javascript:if(!validate()){{if(confirm('确认保存并关闭草稿合同')){getOwnerForm(this).tag.value=0;getOwnerForm(this).submit();}return false;}}"  class="button02"/>
        </s:if>
        <s:if test="isModify==1">
           <input  type="submit" value="保存" onClick="getOwnerForm(this).method.value = 'toPage';" class="button01"/>
        </s:if>
        <s:if test="mainid==null">
          </s:if>
          <s:else>
           <input  type="submit" value="确认提交"  onClick="{if(!validate()&&confirm('确认提交草稿合同')){getOwnerForm(this).method.value = 'sureSubmit';return true;}return false;}" class="button01"/>
          <input type="button" value="合同拆分导出" class="button01" onClick="javascript:window.open('/yx/contract/contractSplitTable.action?contractMainInfoSid=<s:property value="mainid"/>')"/>
          </s:else>
        <s:if test="isModify==1">  
           <input  type="submit" value="删除"  onClick="{if(confirm('确认删除草稿合同')){getOwnerForm(this).method.value = 'delContract';return true;}return false;}" class="button01"/>
           <input  type="submit" value="返回" onClick="if(!validate()){getOwnerForm(this).method.value='goback';return true;}else{return false;}"  class="button01"/>
           </s:if>      
        </div>
</s:form>
<s:form name="deleteStagePlanForm" id="deleteStagePlanForm" action="contract" theme="simple">
	<input type="hidden" name="stagePlan.id" value=""/>
	<s:hidden name="method" value="deleteStageInfo" />
    <!-- 切换tab页参数 -->
    <s:hidden name="tag"></s:hidden>
	<s:hidden name="mainid"/>
	<s:hidden name="isModify" ></s:hidden>
	<s:hidden name="clietId"/>
	<s:hidden name="isFromNewPage"/>
	<!-- 切换tab页参数 -->
</s:form>
<!--else-->
<script type="text/javascript">
/////////////////////////
var existStage = new Array();
<s:iterator value="stagePlanlist">
existStage.push("<s:property value="contractMaininfoPart.id"/>_<s:property value="contractItemStage.itemStageName"/>");
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
	if(hasAmountToAssign()){
		var ev2=new Validator();
	    <s:if test="importFromFile" >
	   	</s:if>
	   	<s:else>
		ev2.addError("还有剩余金额未分配");
		ev2.writeErrors(errorsFrame, "errorsFrame");
		return true;
		</s:else>
	}
	return false;
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
