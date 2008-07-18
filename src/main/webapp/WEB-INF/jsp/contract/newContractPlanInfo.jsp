<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同新建</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script type="text/javascript">
function setNum(i){
var hidden=document.getElementById("tag");
hidden.setAttribute("value",i);
}
function setInfo(i,j){
 var hiddenstage=document.getElementById("splitstageNum");
 var hiddenitem=document.getElementById("splititemNum");
 hiddenstage.setAttribute("value",j);
 hiddenitem.setAttribute("value",i);

}
</script>

<style type="text/css">
body {
	background-color: #FFFFFF;
}
.hidecontent {display:none;}
</style>
</head>
<body>
<div align="left">
    <s:if test="isModify==0" >
  <div  style="color:#000000">当前页面：合同管理 -&gt; 合同新建</div>
</s:if>
<s:if test="isModify==1">
 <div  style="color:#000000">当前页面：合同管理 -&gt; 草稿合同修改</div>
</s:if>
</div>
 <s:form action="contract" theme="simple">
 <s:hidden name="method" value="savePlanInfo" />
 <div  align="left" style="color: #FF0000" >
<iframe name="errorsFrame" frameborder="0"
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
    <%@ include file="/WEB-INF/jsp/contract/ContractTopTab.jsp"%> 
      </div>
      <s:hidden name="tag" id="tag"></s:hidden>
      <s:hidden name="splititemNum" id="splititemNum"></s:hidden>
      <s:hidden name="splitstageNum" id="splitstageNum"></s:hidden>
        <div id="content" class="content1" >
            <div  id="content1"  >
             

            </div>
          <div id="content2" class="hidecontent">
            <!--合同项目开始-->
        
              <!--合同项目结束-->
          </div>
          <div id="content3"    class="hidecontent">
           
            <!--开票和收款阶段结束-->
          </div>
    <div id="content4"  >  
              <!--开票和收款计划开始-->
           <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
            <s:set name="planIndex"  value="0"></s:set>
            <s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01" colspan="3"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/>——总金额：<s:property value="money"/></div></td>
                  <td class="bg_table01" ><div id="balance" align="left"></div></td>
                </tr>
                <tr>
                   <td align="center" >合同阶段</td>
                   <td align="center" >合同负责部门</td>
                   <td align="center" >计划开票日期</td>
                   <td align="center">开票金额</td>
                </tr>
                <s:iterator value="planlist"  status="ilist">
                 <s:if test="moneytype == billNature">
	                <tr>
	                   <s:hidden name="%{'planlist['+#planIndex+'].initConBillproSid'}" value="%{initConBillproSid}" />
	                   <td align="center" width="25%" ><s:property value="contractservice.findStageName(conItemStage)" /></td>
	                   <td align="center" width="25%" ><s:property value="contractservice.findDepName(conItemInfo)" /></td>
	                   <td align="center" width="25%" ><s:property value="initBillDate"/></td>
	                   <td align="center" width="25%" ><s:textfield name="%{'planlist['+#planIndex+'].initBillAmount'}" value="%{initBillAmount}" onblur="operationbalance();" ></s:textfield> </td>
	                </tr>
	                <s:set name="planIndex" value="%{#planIndex+1}"></s:set>
                 </s:if>
                </s:iterator>
            </s:iterator>
            <s:hidden name="planIndex" id="planIndex" value="%{#planIndex}" ></s:hidden>
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
        <div align="center">
        <s:if test="isModify==0" >
            <input  type="button" value="保    存" onclick="javascript:if(!validate()){document.contract.submit();}" class="button02"/>
            <input  type="button" value="保存并关闭" onClick="javascript:if(!validate()){{if(confirm('确认保存并关闭草稿合同')){setNum(0);document.contract.submit();}return false;}}"  class="button02"/>
        </s:if>
        <s:if test="isModify==1">
           <input  type="button" value="保存"  onClick="javascript:if(!validate()){setNum(1);document.contract.submit();}" class="button01"/>
             <input  type="submit" value="确认提交"  onClick="setMethod('sureSubmit');{if(confirm('确认提交草稿合同')){refresh();return true;}return false;}" class="button01"/>
          <input  type="submit" value="删除"  onClick="setMethod('delContract');{if(confirm('确认删除草稿合同')){return true;}return false;}" class="button01"/>
           <input  type="submit" value="返回"  onClick="setMethod('goback');"  class="button01"/>
        </s:if>      
        </div>
<!--else-->
</s:form>
<script type="text/javascript">
operationbalance();
function validate(){
  var ev2=new Validator();
  var balancediv=document.getElementById("balance");
  var a=0;
  var i=document.getElementById("planIndex").value;
  for(var j=0;j<i;j++){
    ev2.test("+float","开票金额必须是大于0的数字!",document.getElementById("contract_planlist_"+j+"__initBillAmount").value);
  	if(ev2.size()>0)
  	{
  		ev2.writeErrors(errorsFrame, "errorsFrame");
  		return true;
  	}  
    a=a+parseFloatNumber(document.getElementById("contract_planlist_"+j+"__initBillAmount").value);
  }
  var b=0;
   <s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
      b=b+parseFloatNumber("<s:property value="money"/>");
   </s:iterator>
   	   if(b>a&&a!=0){
   	     ev2.addError("金额未分配完");
   	   }
   	   if(a>b){
   	     ev2.addError("分配金额超出");
   	   }
   	   ev2.writeErrors(errorsFrame, "errorsFrame");
   	   if(ev2.size()>0)
	 	{
	  		return true;
	  	}
		 return false;
}
function operationbalance(){
  var ev2=new Validator();
  var balancediv=document.getElementById("balance");
  var a=0;
  var i=document.getElementById("planIndex").value;
  for(var j=0;j<i;j++){
    ev2.test("+float","开票金额必须是大于0的数字!",document.getElementById("contract_planlist_"+j+"__initBillAmount").value);
  	ev2.writeErrors(errorsFrame, "errorsFrame");
  	if(ev2.size()>0)
  	{
  		return;
  	}
    a=a+parseFloatNumber(document.getElementById("contract_planlist_"+j+"__initBillAmount").value);
  }
  var b=0;
   <s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
      b=b+parseFloatNumber("<s:property value="money"/>");
   </s:iterator>
  balancediv.innerHTML="合同未分配金额："+(b-a).toFixed(2)+"";
}
</script>
</body>
</html>
