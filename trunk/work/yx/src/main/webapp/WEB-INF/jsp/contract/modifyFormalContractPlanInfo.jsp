﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同新建</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/card_f.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/public.js" type="text/javascript"></script>
<script type="text/javascript">
<s:if test="#isRelationSuccess == 'true' "> 
alert('保存成功');
window.close();
</s:if>
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
 <div align="left"  style="color:#000000">当前页面：合同管理 -&gt; 开票收款计划变更</div>
</div>
 <s:form action="formalContractPlanModify" theme="simple">
 <s:hidden name="method" value="savePlanInfo" />
 <div  align="left" style="color: #FF0000" >
<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" scrolling="no"></iframe></div>
<table width="100%"  border="0"  align="center" cellpadding="1" cellspacing="1" class="bg_table02" >
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
      </div>
      <s:hidden name="mainid" id="mainid"></s:hidden>
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
           <table align="center"  width="100%" border="1" bordercolor="#808080" style=" border-collapse: collapse;">
               <tr>
                <s:hidden name="maininfo.conTaxTamount"  ></s:hidden>
                  <td class="bg_table02" colspan="7"><div align="left">合同总金额（含税）:<s:property value="maininfo.conTaxTamount"/>&nbsp;&nbsp;&nbsp;&nbsp;未分配收款金额:<span id="remainReceAmount"/></div></td>
                </tr>   
            <s:set name="planIndex"  value="0"></s:set>
            <s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                <s:hidden id="init[%{moneytype}]" value="%{money}"/>
                  <td class="bg_table01" colspan="5"><div align="left"><s:property  value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/>——总金额：<s:property value="money"/></div></td>
                  <td class="bg_table01" nowrap="nowrap" colspan="3" ><div id="<s:property value="moneytype"/>" align="left"></div></td>
                  <td class="bg_table01" nowrap="nowrap" ><div id="<s:property value="moneytype"/>" align="left"></div></td>          
                </tr>
                <tr>
                   <td align="center" nowrap="nowrap" >合同阶段</td>
                   <td align="center" nowrap="nowrap" >合同负责部门</td>
                   <td align="center" nowrap="nowrap" >计划开票日期</td>
                   <td align="center" nowrap="nowrap">计划收款日期</td>
                   <td align="center" nowrap="nowrap">开票类型</td>
                   <td align="center" nowrap="nowrap">开票金额</td>
                   <td align="center" nowrap="nowrap">阶段调整</td>
                   <td align="center" nowrap="nowrap">部门调整</td>
                   <td align="center" nowrap="nowrap">收款金额</td>
                </tr>
                <s:iterator value="planlist"   status="ilist">
                 <s:if test="moneytype == billNature">
	                <tr>
	                   <s:hidden name="%{'planlist['+#planIndex+'].billType'}" value="%{billType}"></s:hidden>
	                   <s:hidden name="%{'planlist['+#planIndex+'].conMainInfoSid'}" value="%{conMainInfoSid}" ></s:hidden>
	                   <s:hidden name="%{'planlist['+#planIndex+'].realConBillproSid'}" value="%{realConBillproSid}" />
	                   <td align="left"  ><s:property value="contractservice.findStageName(conItemStage)" /></td>
	                   <td align="left"  >
	                   <s:if test="contractItemMaininfo==null">
	                    <s:property value="contractservice.findDepNameByMainid(mainid)"/>
	                   </s:if>
	                   <s:else>
	                    <s:property value="contractservice.findDepName(contractItemMaininfo)" />
	                   </s:else>
	                  
	                   
	                   </td>
	                   <td align="center" nowrap="nowrap" ><s:property value="realPredBillDate"/></td>
	                   <td align="center" nowrap="nowrap"  ><s:property value="realPredReceDate" /></td>
	                   <td align="center">
	                   <s:property  value="typeManageService.getYXTypeManage(1004,billType).typeName" /></td>
	                   <td align="left"  id="<s:property value="moneytype"/>/<s:property value="conItemStage"/>/<s:property value="contractItemMaininfo"/>/<s:property value="realConBillproSid"/>/<s:property value="billType"/>" >
	                   <s:if test="contractservice.isPlanModify(realConBillproSid) == 'true' " > 
	                    <s:textfield  name="%{'planlist['+#planIndex+'].realBillAmount'}" size="12"  value="%{realBillAmount}" onblur="formatInputNumberReturnZero(this);balanceAmountTip(this);operationbalance(this);"></s:textfield> 
	                   </s:if>
	                 <s:if test="contractservice.isPlanModify(realConBillproSid) == 'false' " >
	                    <s:textfield  name="%{'planlist['+#planIndex+'].realBillAmount'}"  readonly="true" value="%{realBillAmount}" onblur="formatInputNumber(this);balanceAmountTip(this);operationbalance(this);"></s:textfield> 
	                   </s:if>
	                  </td>
	                  <td>
	                   <span id="spanstage/<s:property value="moneytype"/>/<s:property value="conItemStage"/>/<s:property value="contractItemMaininfo"/>/<s:property value="realConBillproSid"/>/<s:property value="billType"/>"></span>
	                  </td>
	                  <td>
	                  <span id="span/<s:property value="moneytype"/>/<s:property value="conItemStage"/>/<s:property value="contractItemMaininfo"/>/<s:property value="realConBillproSid"/>/<s:property value="billType"/>"></span>
	                  </td>
	                  <td>
	                  <s:textfield name="%{'planlist['+#planIndex+'].realReceAmount'}" onblur="formatInputNumberReturnZero(this);caculateReceMoney();" value="%{realReceAmount}" size="10"></s:textfield>   
	                  </td>
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
            <input  type="button" value="保   存" onclick="javascript:if(!validate()){setNum(4);document.formalContractPlanModify.submit();}" class="button02"/>
            <input  type="button" value="关   闭" onClick="if(confirm('确认关闭？')){{window.close();}return false;}"  class="button02"/>
        </div>
<!--else-->
</s:form>
<script type="text/javascript"><!--
function validate(){
       var ev2=new Validator();
       var i=document.getElementById("planIndex").value;
       for(var j=0;j<i;j++){
   	      ev2.test("float","开票金额必须是数字!",document.getElementById("formalContractPlanModify_planlist_"+j+"__realBillAmount").value);
          ev2.test("float","收款金额必须是数字!",document.getElementById("formalContractPlanModify_planlist_"+j+"__realReceAmount").value);
   	   if(ev2.size()>0)
	 	  {
	 	   ev2.writeErrors(errorsFrame, "errorsFrame");
	  		return true;
	  	  }
       }
       var alltexteventmoney1=0;
                 for(var j=0;j<i;j++){
                     var money = document.getElementById("formalContractPlanModify_planlist_"+j+"__realBillAmount");
	    			 var td=money.parentNode.id;
	    			 var attributes = td.split("/");
	    			 if(attributes[3]!='4'){
	     			   	alltexteventmoney1=alltexteventmoney1+parseFloatNumber(money.value);
                     }
                 }
  	   var eventmoneyArr=new Array();
       <s:iterator value="eventallmoney"  id="money">
       eventmoneyArr[eventmoneyArr.length]=new Array("<s:property value="#money[0]"/>","<s:property value="#money[2]"/>","<s:property value="#money[3]"/>","<s:property value="#money[1]"/>");
       </s:iterator>
       

       var stagemoneyArr=new Array();
       <s:iterator value="stageallmoney"  id="money">
       stagemoneyArr[stagemoneyArr.length]=new Array("<s:property value="#money[0]"/>","<s:property value="#money[2]"/>","<s:property value="#money[3]"/>","<s:property value="#money[1]"/>");
       </s:iterator>
       
//      if(alltexteventmoney1!=0){
	       if(eventmoneyArr.length!=0){
             var alltexteventmoney=0;
              for(var x=0;x<eventmoneyArr.length;x++){
                 for(var j=0;j<i;j++){
                     var money = document.getElementById("formalContractPlanModify_planlist_"+j+"__realBillAmount");
	    			 var td=money.parentNode.id;
	    			 var attributes = td.split("/");
	    			 if(attributes[2]==eventmoneyArr[x][0]&&attributes[0]==eventmoneyArr[x][3]){
		    			   if(attributes[3]!='4'){
		     			   		alltexteventmoney=alltexteventmoney+parseFloatNumber(money.value);
		   		           }
	   		         }
                 }
                 if(Math.abs(parseFloatNumber(eventmoneyArr[x][1])-parseFloatNumber(alltexteventmoney))>=1){
	  			   ev2.addError(""+eventmoneyArr[x][2]+"金额不匹配");  			     
	  		   	 }
	  		       alltexteventmoney=0;
               }
            }
	        
	       if(stagemoneyArr.length!=0){
	            var alltextstagemoney=0;

	            for(var x=0;x<stagemoneyArr.length;x++){
		            for(var k=0;k<i;k++){
		     		     var money = document.getElementById("formalContractPlanModify_planlist_"+k+"__realBillAmount");
		    			 var td=money.parentNode.id;
		    			 var attributes = td.split("/");
		    			 if(attributes[1]==stagemoneyArr[x][0]&&attributes[0]==stagemoneyArr[x][3]){
		     			   if(attributes[3]!='4'){
		     			   		alltextstagemoney=alltextstagemoney+parseFloatNumber(money.value);
		   		           }
		   		         }
		  			}					
		  			if(Math.abs(parseFloatNumber(stagemoneyArr[x][1])-parseFloatNumber(alltextstagemoney))>=1){
		  			   ev2.addError(""+stagemoneyArr[x][2]+"金额不匹配");
		  			}
		  		    alltextstagemoney=0;
		  		}
	       }
  //     }
       var allReveMoney = document.getElementById("formalContractPlanModify_maininfo_conTaxTamount").value;
       var alltextReveMoney=0;
           for(var j=0;j<i;j++){
               var money = document.getElementById("formalContractPlanModify_planlist_"+j+"__realReceAmount");
	     	   alltextReveMoney=alltextReveMoney+parseFloatNumber(money.value);
           } 
       if(Math.abs(parseFloatNumber(allReveMoney)-parseFloatNumber(alltextReveMoney))>0.1){
           ev2.addError("合同收款金额（"+number_format(allReveMoney)+"）不等于页面收款金额（"+number_format(alltextReveMoney)+"）");
       }
       

   	   if(ev2.size()>0)
	 	{
	 	    ev2.writeErrors(errorsFrame, "errorsFrame");
	  		return true;
	  	}
		 return false;
}
function caculateReceMoney(){
   var i=document.getElementById("planIndex").value;
   var alltextmoney = 0;
   for(var k=0;k<i;k++){
     var money = document.getElementById("formalContractPlanModify_planlist_"+k+"__realReceAmount");
     alltextmoney=alltextmoney+parseFloatNumber(money.value);
   }
   var allcontractmoney=document.getElementById("formalContractPlanModify_maininfo_conTaxTamount").value;
   var bal=parseFloatNumber(allcontractmoney)-alltextmoney;
   var showdiv = document.getElementById("remainReceAmount");
   bal=bal+"";
   showdiv.innerHTML=number_format(bal);   
}


function operationbalance(a){
  var ev2=new Validator();
  var i=document.getElementById("planIndex").value;
  for(var j=0;j<i;j++){
    ev2.test("float","开票金额必须是数字!",document.getElementById("formalContractPlanModify_planlist_"+j+"__realBillAmount").value);
  	ev2.writeErrors(errorsFrame, "errorsFrame");
  	if(ev2.size()>0){
  	  return;
  	}
  }
   var x=a.parentNode.id;
   var list=x.split("/");
   caculateBalance(list[0]);
}

function caculateBalance(mainPartType){
   var i=document.getElementById("planIndex").value;
   var alltextmoney = 0;
   for(var k=0;k<i;k++){
     var money = document.getElementById("formalContractPlanModify_planlist_"+k+"__realBillAmount");
     var td=money.parentNode.id;
     var attributes = td.split("/");
     if(attributes[0]==mainPartType){
        alltextmoney=alltextmoney+parseFloatNumber(money.value);
     }
   }
   var initallmoney=document.getElementById("init["+mainPartType+"]").value;
   var bal=parseFloatNumber(initallmoney)-alltextmoney;
   var showdiv = document.getElementById(mainPartType);
   bal=bal+"";
   showdiv.innerHTML="<p>未分配金额："+number_format(bal)+"</p>";   
}
///////////////////////////////////////////////////////////////////
var projectMap = new Hash();
<s:iterator value="eventallmoney" id="eventList">
projectMap.set('<s:property value="#eventList[1]"/>_<s:property value="#eventList[0]"/>',{id:<s:property value="#eventList[0]"/>,mainPart:'<s:property value="#eventList[1]"/>',money:parseFloatNumber('<s:property value="#eventList[2]"/>')});
</s:iterator>
var stageMap = new Hash();
<s:iterator value="stageallmoney" id="stageList">
stageMap.set('<s:property value="#stageList[1]"/>_<s:property value="#stageList[0]"/>',{id:<s:property value="#stageList[0]"/>,mainPart:'<s:property value="#stageList[1]"/>',money:parseFloatNumber('<s:property value="#stageList[2]"/>')});
</s:iterator>
///////////////////////////////////////////////////////////////////
 <s:iterator value="mainMoneyList" id="maininfoPart" >
 caculateBalance('<s:property value="moneytype"/>');
 </s:iterator>

function balanceAmountTip(textfild){
   var x=textfild.parentNode.id;
   var list=x.split("/");
   var partId = list[0];
   //本行行号
   var tr=textfild.parentNode.parentNode;
   //费用组成的条数
   var totalcount=document.getElementById("planIndex").value;
     
   //清空页面提示信息
   for(k=0;k<totalcount;k++){
      var money = document.getElementById("formalContractPlanModify_planlist_"+k+"__realBillAmount");
      var td=money.parentNode.id;
      var showspan=document.getElementById("span/"+td+"");
      var showspanstage=document.getElementById("spanstage/"+td+"");
      showspan.innerHTML="";
      showspanstage.innerHTML="";
   }
   ///////////////////////////////////计算费用下的所有项目/////////////////////////////////////
   	var pjKeys = projectMap.getKeys();
	for(var i=0;i<pjKeys.length;i++){
		var key = pjKeys[i];
		//相同费用组成的项目
		if(key.indexOf(partId)==0){
		   //项目id
		   var projectId = projectMap.get(key).id+"";
		   //计算此费用下的修改框所对应的项目费用总金额
		   var accountTextEventMoney=0;
		   var lastIndex = -1;
		   for(var k=0;k<totalcount;k++){
		     var money = document.getElementById("formalContractPlanModify_planlist_"+k+"__realBillAmount");
		     var td=money.parentNode.id;
		     var lastTr=money.parentNode.parentNode;
		     var attributes = td.split("/");
		     if(attributes[2]==projectId&&attributes[0]==partId){
		         accountTextEventMoney=accountTextEventMoney+parseFloatNumber(money.value);
		         lastIndex = lastTr.rowIndex;
		     }     
		   }
		   //在同一种费用下，有和修改框同样负责部门的计划进行提示
		   for(var j=0;j<totalcount;j++){
		       //获得每行的计划
		       var money = document.getElementById("formalContractPlanModify_planlist_"+j+"__realBillAmount");
		       //行号
		       var td=money.parentNode.id;
		       var tr1=money.parentNode.parentNode;
		       //分割id，费用组成/阶段/项目
		       var attributes = td.split("/");
		       
		       //只显示当前费用下的项目
		       if(attributes[2]==projectId&&attributes[0]==partId&&(tr1.rowIndex!=tr.rowIndex)){
		          var existMoney = projectMap.get(""+partId+"_"+projectId+"").money;
		          var showspan = document.getElementById("span/"+td+"");
		          if((accountTextEventMoney-existMoney) <0){
		          	showspan.style.color="red";
		          	showBlanceTip(showspan,"+"+number_format(Math.abs(accountTextEventMoney-existMoney)+""));
		       	  }else{
		          	showspan.style.color="green";
		          	showBlanceTip(showspan,"-"+number_format(Math.abs(accountTextEventMoney-existMoney)+""));
		       	  }
		       }
		   }
		}
	}
	///////////////////////////////////计算费用下的所有阶段/////////////////////////////////////
   	var stageKeys = stageMap.getKeys();
	for(var i=0;i<stageKeys.length;i++){
		var key = stageKeys[i];
		//相同费用组成的项目
		if(key.indexOf(partId)==0){
		   //阶段id
		   var stageId = stageMap.get(key).id+"";
		   //计算此费用下的修改框所对应的项目费用总金额
		   var accountTextStageMoney=0;
		   var lastIndex = -1;
		   for(var k=0;k<totalcount;k++){
		     var money = document.getElementById("formalContractPlanModify_planlist_"+k+"__realBillAmount");
		     var td=money.parentNode.id;
		     var lastTr=money.parentNode.parentNode;
		     var attributes = td.split("/");
		     if(attributes[1]==stageId&&attributes[0]==partId){
		         accountTextStageMoney=accountTextStageMoney+parseFloatNumber(money.value);
		         lastIndex = lastTr.rowIndex;
		     }
		   }
		   //在同一种费用下，有和修改框同样负责部门的计划进行提示
		   for(var j=0;j<totalcount;j++){
		       //获得每行的计划
		       var money = document.getElementById("formalContractPlanModify_planlist_"+j+"__realBillAmount");
		       //行号
		       var td=money.parentNode.id;
		       var tr1=money.parentNode.parentNode;
		       //分割id，费用组成/阶段/项目
		       var attributes = td.split("/");
		       //只显示当前费用下的项目)
		       if(attributes[1]==stageId&&attributes[0]==partId&&(tr1.rowIndex!=tr.rowIndex)){
		          var existMoney = stageMap.get(""+partId+"_"+stageId+"").money;
		          var showspanstage=document.getElementById("spanstage/"+td+"");
		          if((accountTextStageMoney-existMoney) <0){
		          	showspanstage.style.color="red";
		          	showBlanceTip(showspanstage,"+"+number_format(Math.abs(accountTextStageMoney-existMoney)+""));
		       	  }else{
		          	showspanstage.style.color="green";
		          	showBlanceTip(showspanstage,"-"+number_format(Math.abs(accountTextStageMoney-existMoney)+""));
		       	  }
		       }
		   }
		}
	}	
//////////////////////////////////////////////////////////////////////////////////////////
}
function showBlanceTip(span,amount){
	var innerAmount = span.innerHTML;
	if(innerAmount.length == 0){
		var newAmount = parseFloatNumber(amount);
		if(Math.abs(newAmount) < 0.01){
			span.innerHTML="";
		}else{
			span.innerHTML = amount;
		}
	}else{
		if(span.innerHTML=="+-" || span.innerHTML==""){
			innerAmount="0";
		}
		var existAmount = parseFloatNumber(innerAmount);
		var newAmount = parseFloatNumber(amount);
		if(Math.abs(existAmount) < 0.01 && Math.abs(newAmount) < 0.01 ){
			span.innerHTML="";
		}else if(existAmount >= 0 && newAmount >=0){
		    span.style.color="red";
			span.innerHTML="+"+number_format(Math.abs(Math.max(existAmount,newAmount))+"");
		}else if(existAmount <= 0 && newAmount <= 0){
			span.style.color="green";
			span.innerHTML="-"+number_format(Math.abs(Math.min(existAmount,newAmount))+"");
		}else{
			span.style.color="gray";
			span.innerHTML="+-";
		}
	}
}
--></script>
</body>
</html>
