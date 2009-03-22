<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>结算转决算</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/time.js" type="text/javascript"></script>
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
 <div align="left" style="color:#000000">当前页面：合同管理 -&gt; 结算转决算</div>
</div>
 <s:form action="finalToclose" theme="simple">
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
    <%@ include file="/WEB-INF/jsp/contract/finalToclose/ContractTopTab.jsp"%> 
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
           <table align="center" border=1 cellpadding="0" cellspacing="0" bordercolor="#808080" style=" border-collapse: collapse;" width="100%" >
            <s:set name="planIndex"  value="0"></s:set>
            <s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                <s:hidden id="init[%{moneytype}]" value="%{money}"/>
                  <td class="bg_table01" colspan="4"><div align="left"><s:property  value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/>——总金额：<s:property value="money"/></div></td>
                  <td class="bg_table01" colspan="2" align="left">未分配金额：<span id="<s:property value="moneytype"/>">0.00</span></td>
                </tr>
                <tr>
                   <td align="center" >合同阶段</td>
                   <td align="center" >合同负责部门</td>
                   <td align="center" >计划开票日期</td>
                   <td align="center" >计划收款日期</td>
                   <td align="center">开票金额</td>
                   <td align="center">收款金额</td>
                </tr>
                <s:iterator value="planlist"  id="plan"  status="ilist">
                 <s:if test="moneytype == billNature">
	                <tr>
	                   <s:hidden name="%{'planlist['+#planIndex+'].realConBillproSid'}" value="%{realConBillproSid}" />
	                   <td align="center" width="25%" ><s:property value="contractservice.findStageName(conItemStage)" /></td>
	                   <td align="center" width="25%" >
	                   <s:if test="contractItemMaininfo==null">
	                    <s:property value="contractservice.findDepNameByMainid(mainid)"/>
	                   </s:if>
	                   <s:else>
	                    <s:property value="contractservice.findDepName(contractItemMaininfo)" />
	                   </s:else>                  
	                   </td>
	                   <td align="center" width="25%" ><s:property value="realPredBillDate"/></td>
	                   <td align="center" width="25%" ><s:property value="realPredReceDate"/></td>
	                   
	                  <s:if test="contractservice.checkRealContractBillAndApplyBill(realConBillproSid)">
	                  <td align="center" width="25%" id="<s:property value="moneytype"/>/<s:property value="conItemStage"/>/<s:property value="conItemInfo"/>" >
	                   <s:textfield name="%{'planlist['+#planIndex+'].realBillAmount'}" value="%{realBillAmount}" readonly="true">
	                   </s:textfield>
	                     </td>
	                    <td><s:textfield name="%{'planlist['+#planIndex+'].realReceAmount'}"  value="%{realReceAmount}" readonly="true"/>
	                	</td>
	                   </s:if>
	                   <s:else>
	                   <td align="center" width="25%" id="<s:property value="moneytype"/>/<s:property value="conItemStage"/>/<s:property value="conItemInfo"/>" >
	                   <s:textfield name="%{'planlist['+#planIndex+'].realBillAmount'}" value="%{realBillAmount}" onblur="operationbalance(this,'%{getTaxAmount(#plan.realArriveAmount,#maininfoPart.moneytype)}');">
	                   </s:textfield>
	                     <span id="<s:property value="#maininfoPart.id"/>/<s:property value="realConBillproSid"/>"></span>
	                   <td><s:textfield name="%{'planlist['+#planIndex+'].realReceAmount'}"  value="%{realReceAmount}"  onblur="formatInputNumber(this)"/>
	                	</td>
	                   </s:else>
	                </tr>                
	                <s:set name="planIndex" value="%{#planIndex+1}"></s:set>
                 </s:if>
                </s:iterator>
            </s:iterator>
            <s:hidden name="planIndex" id="planIndex" value="%{#planIndex}" ></s:hidden>
            <tr class="bg_table01">
            <td align="center">
            	费用组成
            	</td>
            <td>
            	<select name="partId" onchange="setValueToItemAndStage(this.value)">
            		<option value="-1">--请选择--</option>
            		<s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
            			<option value="<s:property value="id"/>"><s:property  value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/></option>
            		</s:iterator>
            	</select>
            	</td>
            	<td align="center">
            		&nbsp;
            	</td>
            	<tD align="center">
            		&nbsp;
            	</tD>
            	<td align="center">
            		&nbsp;
            	</td>
            	<td align="center">
            		<a href="javascript:addRealPlan();">添加</a>
            	</td>       
            </tr>
            <tr>
            	<tD align="center">
            		阶段
            	</tD>
            	<td align="center">
            		合同负责部门
            	</td>
            	<tD align="center">
            		计划开票日期
            	</tD>
            	<td align="center">
            		计划收款日期
            	</td>
            	<td align="center">
            		计划开票金额
            	</td>
            	<td align="center">
            		计划收款金额
            	</td>
            </tr>
               <tr>
            
            	<tD>
            		<select name="changeRealPlan.conItemStage">
            		<option value="">--请选择--</option>
            		</select>
            	</tD>
            	<td>
            		<select name="changeRealPlan.contractItemMaininfo">
            		<option value="">--请选择--</option>
            		</select>
            	</td>
            	<tD nowrap="nowrap">
            		<s:textfield name="changeRealPlan.realPredBillDate" id="realPredBillDate" size="12"/><img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('realPredBillDate')" align="absMiddle"/>
            	</tD>
            	<td nowrap="nowrap">
            		<s:textfield name="changeRealPlan.realPredReceDate" id="realPredReceDate" size="12"/><img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('realPredReceDate')" align="absMiddle"/>
            	</td>
            	<td>
            		<s:textfield name="changeRealPlan.realBillAmount" onblur="formatInputNumber(this);"></s:textfield>
            	</td>
            	<td>
            		<s:textfield name="changeRealPlan.realReceAmount" onblur="formatInputNumber(this);"></s:textfield>
            	</td>
            </tr>
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
          <input  type="button" value="保存"  onClick="if(!validate()){setNum(4);document.finalToclose.submit();}" class="button01" >   
          <input type="button" value="申请提交" onclick="if(!validate()){setNum(0);tijiao();}" class="button01"/>   
          <input type="button" value="关闭" onclick="setNum(0);document.finalToclose.submit();" class="button01"> 
        </div>
<!--else-->
</s:form>
<script type="text/javascript">
var monTypeArr=new Array();
<s:iterator value="mainMoneyList">
monTypeArr[monTypeArr.length]=new Array("<s:property value="moneytype"/>","<s:property value="money"/>");
</s:iterator>
function setValueToItemAndStage(pid){
	var mid = document.finalToclose.mainid.value;
	var selectNode=document.finalToclose.elements("changeRealPlan.conItemStage");
	//给阶段list赋值
	ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetStageByConAndPart&mainid="+mid+"&partId="+pid,document.finalToclose.elements("changeRealPlan.conItemStage"),"key","value",{defaultAsync:false,value:"",text:"--请选择--"});
	//给项目list赋值
	ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetItemByConAndPart&mainid="+mid+"&partId="+pid,document.finalToclose.elements("changeRealPlan.contractItemMaininfo"),"key","value",{defaultAsync:false,value:"",text:"--请选择--"});
}
function addRealPlan()
{
	if(!validateAdd()){
		document.finalToclose.method.value="addChangeRealPlan";
		document.finalToclose.submit();
	}
}
function validateAdd(){
	var ev2 =new Validator();
	var pselectNode = document.finalToclose.elements("partId").selectedIndex;
	var itemSelectNode = document.finalToclose.elements("changeRealPlan.contractItemMaininfo").selectedIndex;
	var stageSelectNode = document.finalToclose.elements("changeRealPlan.conItemStage").selectedIndex;
	if(pselectNode==0){
		ev2.addError("请选择费用组成");
	}
	if(itemSelectNode==0){
		ev2.addError("请选择项目负责部门");
	}
	if(stageSelectNode==0){
		ev2.addError("请选择项目阶段");
	}
	ev2.test("dateYX","开票日期输入格式不正确！",$('changeRealPlan.realPredBillDate').value);
	ev2.test("dateYX","收款日期输入格式不正确！",$('changeRealPlan.realPredReceDate').value);
	ev2.test("float"," 开票金额必须为大于0的数字！",$('changeRealPlan.realBillAmount').value);
	ev2.test("float"," 收款金额必须为大于0的数字！",$('changeRealPlan.realReceAmount').value);
	ev2.writeErrors(errorsFrame, "errorsFrame");
    if(ev2.size()>0){
     return true;
    }
    return false;
}

function validate(){
	var ev2=new Validator();
	var count = document.finalToclose.planIndex.value;
	for(var k=0;k<count;k++){
		var billMoney = document.finalToclose.elements("planlist["+k+"].realBillAmount").value;
		var receMoney = document.finalToclose.elements("planlist["+k+"].realReceAmount").value;
     	ev2.test("float"," 开票金额必须为大于0的数字！",billMoney);
     	ev2.test("float"," 收款金额必须为大于0的数字！",receMoney);
     }
     ev2.writeErrors(errorsFrame, "errorsFrame");
     if(ev2.size()>0){
       	return true;
     }
     return false;
}
function tijiao()
{
	document.finalToclose.method.value="applySubmitByPlan";
	document.finalToclose.submit();
}
function operationbalance(a,stard){

  var ev2=new Validator();
  var i=document.getElementById("planIndex").value;

   formatInputNumber(a);
   var x=a.parentNode.id;   
   var list=x.split("/");
   operationbalanceByPart(list[0]);
}

function operationbalanceAllPart(){
  var ev2=new Validator();
  var i=document.getElementById("planIndex").value;
  for(var j=0;j<i;j++){
    ev2.test("float","开票金额必须是数字!",document.getElementById("finalToclose_planlist_"+j+"__realBillAmount").value);
  	ev2.writeErrors(errorsFrame, "errorsFrame");
  	if(ev2.size()>0){
  	  return;
  	}
  }
  ////////
  for(var k = 0;k<monTypeArr.length;k++){
  	operationbalanceByPart(monTypeArr[k][0]);
  }
  ///////
}

function operationbalanceByPart(moneyType){

  var ev2=new Validator();
  var i=document.getElementById("planIndex").value; 
   var alltextmoney = 0;
   for(var k=0;k<i;k++){
     var money = document.getElementById("finalToclose_planlist_"+k+"__realBillAmount");
     var td=money.parentNode.id;
     var attributes = td.split("/");
     if(attributes[0]==moneyType){
        alltextmoney=alltextmoney+parseFloatNumber(money.value);
     }
   }
   var initallmoney=document.getElementById("init["+moneyType+"]").value;
   var bal=parseFloatNumber(initallmoney)-alltextmoney;
   var showdiv = document.getElementById(moneyType);
   bal=bal+"";
   showdiv.innerHTML=number_format(bal);
}
operationbalanceAllPart();
</script>
</body>
</html>
