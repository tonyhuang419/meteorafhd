<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同变更</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script type="text/javascript">
function setNum(i){
var hidden=document.getElementById("tag");
hidden.setAttribute("value",i);
}
function setDelselfproduct(button){
     var hidden=document.getElementById("delselfproduct");
      hidden.setAttribute("value",button.id)
}
function   refreshPage(){ 
		document.forms(0).method.value = "refreshSelfProductPage";
		document.forms(0).submit();
}

function sum(i){
   //alert(i);
   var amount = document.getElementById("contract_ownproductlist_"+i+"__conOwnProdAmount");
   var price = document.getElementById("contract_ownproductlist_"+i+"__conOwnProdPrice");
   var sum = amount.value*price.value;
   var accountmoney=document.getElementById("accountmoney["+i+"]");
   accountmoney.innerHTML ="<p>"+sum+"</p>";  
 }
 
 function showsum(){
 }
</script>

<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
</head>
<body onload="showsum()">
<div align="left">
   <div  style="color:#000000">当前页面：合同管理 -&gt; 正式合同变更</div>
</div>
 <s:form action="formalContractModify" theme="simple">
 <s:hidden name="method" value="selfProduct" />
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
      <input type="hidden" name="delselfproduct" id="delselfproduct"/>
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
             
  
         
            <!--开票和收款计划结束-->
          </div>
          <div id="content5" >
            <!--未签开票关联开始-->
     
          
            <!--已关联清单开始-->
          </div>
          <div id="content6" class="hidecontent">
            <!--申购清单结束-->
            <!--未签申购清单开始-->
            <!--未签申购清单结束-->
            <!--未签申购关联结束-->
          </div>
          <div id="content7">
              <!--自有产品开始-->
                <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
                  <tr align="center">
                    <td  class="bg_table01">名称</td>
                    <td  class="bg_table01">数量</td>
                    <td  class="bg_table01">价格</td>
                    <td  class="bg_table01">总计金额</td>
                    <td  class="bg_table01">操作</td>
                  </tr>
                  <s:iterator value="ownproductlist" status="oplist">
                  <tr align="center">
                    <td width="30%"><s:property value="contractservice.findSelfProductNameById(ownProduceId)"/><s:hidden name="%{'ownproductlist['+#oplist.index+'].ownProduceId'}" ></s:hidden></td>
                    <td width="10%">
                   <s:hidden name="%{'ownproductlist['+#oplist.index+'].conOwnProdSid'}" ></s:hidden>
                   <s:hidden name="%{'ownproductlist['+#oplist.index+'].conMinfo'}"></s:hidden>
                        <s:textfield name="%{'ownproductlist['+#oplist.index+'].conOwnProdAmount'}" size="2" onblur="sum(%{#oplist.index})" ></s:textfield>
                    </td>
                    <td width="30%">
                        <s:textfield name="%{'ownproductlist['+#oplist.index+'].conOwnProdPrice'}" onblur="sum(%{#oplist.index})"></s:textfield>
                    </td>
                    <td id="accountmoney[<s:property value="#oplist.index"/>]" width="20%"></td>
                    <td width="10%">                 
                    <input type="button" disabled="true" value="删除" id="<s:property value="conOwnProdSid"/>" onclick="setNum(7);setDelselfproduct(this);document.contract.submit();" ></td>
                  </tr>    
                  </s:iterator>    
                </table>
                <center>  <input type="button" disabled="true" value="添加" onclick="javascript:window.open('../contract/searchSelfProductQuery.action?method=doDefault&mainid=<s:property value="mainid"/>','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');" ></center>
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
<s:iterator value="ownproductlist" status="oplist">
sum(<s:property value="#oplist.index"/>);
</s:iterator>
function validate(){
	   var ev2=new Validator();
       with(formalContractModify){    
       <s:iterator value="ownproductlist" status="oplist">
           ev2.test("integer","数量为空或不是数字",$('ownproductlist[<s:property value="#oplist.index"/>].conOwnProdAmount').value);
           ev2.test("float","金额为空或不是数字",$('ownproductlist[<s:property value="#oplist.index"/>].conOwnProdPrice').value);
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
