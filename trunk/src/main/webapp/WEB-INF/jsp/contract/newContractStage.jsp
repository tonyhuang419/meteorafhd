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
                      
              <!--开票和收款计划开始-->
               <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
               <s:iterator value="mainMoneyWithPlanAmountList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,#maininfoPart[0].moneytype).typeName"/>&nbsp;&nbsp;&nbsp;&nbsp;总金额:<s:property value="#maininfoPart[0].money"/>&nbsp;&nbsp;&nbsp;&nbsp;未分配金额:<s:property value="#maininfoPart[0].money - #maininfoPart[1]"/></div></td>
                </tr>
                <tr>
                  <td>
                  <table align="center" border=0 cellpadding="0" cellspacing="0" width="100%">
                  	<tr>
                  		<td>阶段名称</td>
                  		<td>阶段金额</td>
                  		<td>预计开票日期</td>
                  		<td>操作</td>
                  	</tr>
                  	   <s:iterator value="stagePlanlist">
                  		<s:if test="contractMaininfoPart.id == #maininfoPart[0].id">
	                  	<tr>
	                  		<td><s:property value="typeManageService.getYXTypeManage(1023,contractItemStage.itemStageName).typeName" /></td>
	                  		<td><s:property value="stageAmout"></s:property></td>
	                  		<td><s:property value="makeInvoiceDate"></s:property></td>
	                  		<td><input type="button" onclick="editStagePlan(<s:property value="id"/>)" value="修改"/><input type="button" onclick="deleteStagePlan(<s:property value="id"></s:property>)" value="删除"/></td>
	                  	</tr>
	                  	</s:if>
                  	</s:iterator>
                  	<s:form name="stagePlanAddForm" id="stagePlanAddForm" action="contract" theme="simple">
	                  	<s:hidden name="method" value="saveStageInfo" />
	                  	<input type="hidden" name="remainStatePlanAmount" value="<s:property value="#maininfoPart[0].money - #maininfoPart[1]" />"/>
	                  	<input type="hidden" name="stagePlan.contractMainSid" value="<s:property value="mainid" />"/>
	                  	<input type="hidden" name="stagePlan.contractMaininfoPart.id" value="<s:property value="#maininfoPart[0].id" />"/>
	                  	<!-- 切换tab页参数 -->
	                  	<s:hidden name="tag"></s:hidden>
						<s:hidden name="mainid"/>
						<s:hidden name="isModify" ></s:hidden>
						<s:hidden name="clietId"/>
						<!-- 切换tab页参数 -->
	                  	<tr>
	                  		<td><font color="red">* </font><s:select name="stagePlan.contractItemStage.itemStageName" listKey="typeSmall" listValue="typeName" list="projectPhaseList"></s:select></td>
	                  		<td><font color="red">* </font><input type="text" name="stagePlan.stageAmout"></td>
	                  		<td><font color="red">* </font><input type="text" name="stagePlan.makeInvoiceDate" readonly="readonly" onclick="ShowCalendar('stageAdd<s:property value="#maininfoPartStatus.index" />')" id="stageAdd<s:property value="#maininfoPartStatus.index" />"/>
	                  		<img src="/yx/commons/images/calendar.gif" onClick="ShowCalendar('stageAdd<s:property value="#maininfoPartStatus.index" />')" align=absMiddle alt=""  />
	                  		</td>
	                  		<td><input type="submit" onclick="return validateAddForm(this)" value="新增"/></td>
	                  	</tr>
                  	</s:form>
                  	
                  </table>
                  </td>
                </tr>
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
		<!-- 切换tab页参数 -->
        <div align="center">
           <s:if test="isModify==0" >
            <input  type="submit" value="保    存" class="button02"/>
            <input  type="button" value="保存并关闭" onClick=" javascript:if(!validate()){{if(confirm('确认保存并关闭草稿合同')){getOwnerForm(this).tag.value=0;document.contract.submit();}return false;}}"  class="button02"/>
        </s:if>
        <s:if test="isModify==1">
           <input  type="submit" value="保存" onClick="getOwnerForm(this).method.value = 'toPage';" class="button01"/>
           <input  type="submit" value="确认提交"  onClick="getOwnerForm(this).method.value = 'sureSubmit';{if(!validate()&&confirm('确认提交草稿合同')){refresh();return true;}return false;}" class="button01"/>
           <input  type="submit" value="删除"  onClick="getOwnerForm(this).method.value = 'delContract';{if(confirm('确认删除草稿合同')){return true;}return false;}" class="button01"/>
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
	<!-- 切换tab页参数 -->
</s:form>
<!--else-->
<script type="text/javascript">
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
		ev2.addError("还有剩余金额未分配");
		ev2.writeErrors(errorsFrame, "errorsFrame");
		return true;
	}
	return false;
}
function validateAddForm(buttonObj){
	   var ev2=new Validator();
	   var form = getOwnerForm(buttonObj);
       with(form){
       		ev2.test("notblank","请选择阶段",form.elements("stagePlan.contractItemStage.itemStageName").value);
			ev2.test("+float","阶段金额必须是大于0的数字",form.elements("stagePlan.stageAmout").value);
			ev2.test("notblank","请选择预计开票日期",form.elements("stagePlan.makeInvoiceDate").value);
       }
       if(ev2.size() == 0 && parseFloatNumber(form.elements("stagePlan.stageAmout").value) > parseFloatNumber(form.remainStatePlanAmount.value)){
	  		if(parseFloatNumber(form.remainStatePlanAmount.value) == 0){
	  			ev2.addError("没有可分配的阶段金额");
	  		}else{
	  			ev2.addError("阶段金额不能大于"+form.remainStatePlanAmount.value);
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
		if( parseFloatNumber('<s:property value="#maininfoPart[0].money - #maininfoPart[1]"/>') > 0){
			has = true;
		}
	</s:iterator>
	return has;
}
<s:if test="#stageDuplicate == true">
stageDuplicate();
</s:if>
</script>
</body>
</html>
