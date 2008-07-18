<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同变更</title>
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
function showInvoiceContent(i){
  var par=i.parentNode;
  var text=par.firstChild;
  var div=document.getElementById(text.value);
  if(i.checked){ 
  div.className="";
  }else{
  div.className="hidecontent";
  }
}
function showInvoiceContentAll(x){
  var button=document.getElementById(x);
  showInvoiceContent(button);
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
 <div  style="color:#000000">当前页面：合同管理 -&gt; 正式合同变更</div>
</div>
 <s:form action="formalContractModify" theme="simple">
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
    <%@ include file="/WEB-INF/jsp/contract/modifyformalContract/ContractTopTab.jsp"%> 
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
             <tr align="center">
              <td width="15%" class="bg_table01" align="center">收款和开票阶段</td>
              <td width="10%" class="bg_table01" align="center" >负责部门</td>
              <td width="8%" class="bg_table01" align="center" >开票性质</td>
              <td width="19%" class="bg_table01" align="center">发票类型</td>
              <td width="9%" class="bg_table01" align="center" >基准</td>
              <td width="12%" class="bg_table01" align="center" >开票金额</td>
              <td width="10%" class="bg_table01" align="center" >开票确定<br>
                  收入标志</td>
              <td   class="bg_table01" >&nbsp;统一<br>
                  &nbsp;开票</td>
               <td  class="bg_table01" >操作</td>
            </tr>
   
    <s:iterator value="planlist" status="plist" >  
            <tr>
              <s:hidden name="%{'planlist['+#plist.index+'].initConBillproSid'}" ></s:hidden>
              <s:hidden name="%{'planlist['+#plist.index+'].conMainInfoSid'}" ></s:hidden>
              <td  class="bg_table02" align="center"><s:property  value="contractservice.findStageName(conItemStage)"/> <s:hidden name="%{'planlist['+#plist.index+'].conItemStage'}" ></s:hidden></td>
              <td  class="bg_table02" align="center"><s:property  value="contractservice.findDepName(conItemInfo)"/><s:hidden name="%{'planlist['+#plist.index+'].conItemInfo'}" ></s:hidden></td>
              <td  class="bg_table02">
                       <s:select  name="%{'planlist['+#plist.index+'].billNature'}" list="itemdesigntypelist" listKey="typeSmall" listValue="typeName" >
						</s:select>
              </td>
              <td  class="bg_table02" align="center">
                       <s:select name="%{'planlist['+#plist.index+'].billType'}"  list="tickettype" listKey="typeSmall" listValue="typeName" >
						</s:select>
              </td>
              <td width="10%" class="bg_table02">
              <s:select name="%{'planlist['+#plist.index+'].base'}" list="#@java.util.HashMap@{0:'不含税',1:'含税'}"></s:select>
             
              </td>
              <td width="12%" class="bg_table02" align="center">
                    <s:textfield name="%{'planlist['+#plist.index+'].initBillAmount'}" size="10"></s:textfield>
              </td>
              <td width="9%" class="bg_table02" align="center">
                  <s:checkbox name="%{'planlist['+#plist.index+'].billRecvSign'}"></s:checkbox>
              </td>
              <td width="6%" class="bg_table02" align="center">
                 <s:hidden   name="initConBillproSid"></s:hidden> <s:checkbox name="%{'planlist['+#plist.index+'].UnifyBill'}"  onclick="showInvoiceContent(this);"></s:checkbox>             
              </td>
              <td width="5%" class="bg_table02" align="center" >
                 <input type="button" value="拆分" onclick="setNum(4);setInfo(<s:property value="conItemStage"/>,<s:property  value="conItemInfo"/>);document.contract.submit();">
              </td>
            </tr> 
         
            <tr> 

                    <td colspan="9">
                            <div id="<s:property value="initConBillproSid"/>" class="hidecontent" >
                          开票内容：<s:textfield name="%{'planlist['+#plist.index+'].billInfo'}"  size="50">
                             </s:textfield>
                           </div>  
                    </td>              
           
            </tr>
                 
            <tr>
                 <td colspan="9"><hr></td>
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
        <div align="center">
         <input  type="submit" value="变更提交"  onClick="setNum(0);" class="button02" >  
        </div>
<!--else-->
</s:form>
<script type="text/javascript">
<s:iterator value="planlist" status="plist">
showInvoiceContentAll("contract_planlist_<s:property value="%{#plist.index}"/>__UnifyBill");
</s:iterator>
function validate(){
	   var ev2=new Validator();
       with(formalContractModify){    
    <s:iterator value="planlist" status="plist">
           ev2.test("float","开票金额为空或者不是数字",$('planlist[<s:property value="#plist.index"/>].initBillAmount').value);
       </s:iterator>                                                            
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}
</script>
</body>
</html>
