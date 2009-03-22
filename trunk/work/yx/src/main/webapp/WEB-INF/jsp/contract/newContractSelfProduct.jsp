<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同新建</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script language="javascript" for="document" event="onkeydown">

<!--
if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!=''){
	event.keyCode=9;
}
-->
</script>

<script type="text/javascript">
function setNum(i){
var hidden=document.getElementById("tag");
hidden.setAttribute("value",i);
}
function setDelselfproduct(buttonvalue){
     var hidden=document.getElementById("delselfproduct");
      hidden.setAttribute("value",buttonvalue)
}
function   refreshPage(){ 
		document.forms(0).method.value = "refreshSelfProductPage";
		document.forms(0).submit();
}

function sum(i){
   //alert(i);
	var ev2=new Validator();
    var amount = document.getElementById("contract_ownproductlist_"+i+"__conOwnProdAmount");
    var price = document.getElementById("contract_ownproductlist_"+i+"__conOwnProdPrice");
    if(amount.value!=null&&amount.value!=""){
    	ev2.test("+integer","数量必须是大于0的数字!",amount.value);
    }
    if(price.value!=null&&price.value!=""){
   		 ev2.test("+float","单价必须是大于0的数字!",price.value);
    }
    var sum=0;
    if(ev2.size()>0){
       sum=0;
    }else{
      var sum = parseFloatNumber(amount.value)*parseFloatNumber(price.value);
      var accountmoney=document.getElementById("accountmoney["+i+"]");
      sum=sum+"";
  	  accountmoney.innerHTML ="<p>"+number_format(sum)+"</p>"; 
    }
        var allmoney=0;
        <s:iterator value="ownproductlist" status="oplist">
           var amount = document.getElementById("contract_ownproductlist_"+<s:property value="#oplist.index"/>+"__conOwnProdAmount");
   		   var price = document.getElementById("contract_ownproductlist_"+<s:property value="#oplist.index"/>+"__conOwnProdPrice");
           if(amount.value==""||price.value==""){
             var sum=0;
           }else{
             var sum = parseFloatNumber(amount.value)*parseFloatNumber(price.value);
           }
           allmoney=allmoney+sum;
        </s:iterator>
        var eqmoney=0
        <s:iterator value="eqinfo">
          eqmoney=eqmoney+parseFloatNumber("<s:property value="money"/>");
        </s:iterator>
        <s:if test="importFromFile" >
	    </s:if>
	    <s:else>	
        if((parseFloatNumber(allmoney)-parseFloatNumber(eqmoney))>0){
          ev2.addError("自有产品金额超过设备总金额");
        }
        </s:else>
     ev2.writeErrors(errorsFrame, "errorsFrame");  
 }
function showsum(i){  
   var amount = document.getElementById("contract_ownproductlist_"+i+"__conOwnProdAmount");
   var price = document.getElementById("contract_ownproductlist_"+i+"__conOwnProdPrice");
   var sum=0;
   var sum = parseFloatNumber(amount.value)*parseFloatNumber(price.value);
   var accountmoney=document.getElementById("accountmoney["+i+"]");
   sum=sum+"";
   accountmoney.innerHTML ="<p>"+number_format(sum)+"</p>"; 
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
    <s:if test="isModify==0" >
  <div  style="color:#000000">当前页面：合同管理 -&gt; 合同新建</div>
</s:if>
<s:if test="isModify==1">
 <div  style="color:#000000">当前页面：合同管理 -&gt; 草稿合同修改</div>
</s:if>
</div>
 <s:form action="contract" theme="simple">
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
                  <%@ include file="/WEB-INF/jsp/contract/ContractTopTab.jsp"%>  
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
              <s:hidden name="eqinfo.money" ></s:hidden>
                <table align="center" border="1" bordercolor="#808080" style=" border-collapse: collapse;" width="100%">
                  <tr align="center">
                    <td  class="bg_table01">名称</td>
                    <td  class="bg_table01">数量</td>
                    <td  class="bg_table01">价格</td>
                    <td  class="bg_table01">总计金额</td>
                    <td  class="bg_table01">操作</td>
                  </tr>
                  <s:iterator value="ownproductlist" status="oplist">
                  <tr align="center">
                   <td align="left" width="30%"><s:property value="contractservice.findSelfProductNameById(ownProduceId)"/><s:hidden name="%{'ownproductlist['+#oplist.index+'].ownProduceId'}" ></s:hidden></td>
                   <td width="10%">
                   <s:hidden name="%{'ownproductlist['+#oplist.index+'].conOwnProdSid'}" ></s:hidden>
                   <s:hidden name="%{'ownproductlist['+#oplist.index+'].conMinfo'}"></s:hidden>
                        <s:textfield name="%{'ownproductlist['+#oplist.index+'].conOwnProdAmount'}" size="2" onblur="sum(%{#oplist.index})" ></s:textfield>
                    </td>
                    <td width="30%">
                        <s:textfield name="%{'ownproductlist['+#oplist.index+'].conOwnProdPrice'}" onblur="formatInputNumber(this);sum(%{#oplist.index});"></s:textfield>
                    </td>
                    <td align="right" id="accountmoney[<s:property value="#oplist.index"/>]" width="20%"></td>
                    <td width="10%">   
                    <a href="javascript:{if(confirm('是否确认删除')){setNum(5);setDelselfproduct('<s:property value="conOwnProdSid"/>');document.contract.submit();}}" >删除</a>              </td>
                  </tr>    
                  </s:iterator>    
                </table>
                <center>  
                <s:if test="eqinfo.size()!=0">
                <a href="" ></a>
                <input type="button" value="添加" onclick="javascript:window.open('../contract/searchSelfProductQuery.action?method=doDefault&mainid=<s:property value="mainid"/>','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');" ></center>
                </s:if>
              <!--自有产品结束-->
          </div>
          <div id="content8" class="hidecontent" >
              <!--else-->
            
          </div>
  </div><!--总体结束DIV-->
        <div align="center">
            <s:if test="isModify==0" >
             <input  type="button" value="保    存" onclick="javascript:if(!validate()){setMethod('selfProduct');setNum(5);document.contract.submit();}" class="button02"/>
            <input  type="button" value="保存并关闭" onClick="javascript:if(!validate()){{if(confirm('确认保存并关闭草稿合同')){setMethod('selfProduct');setNum(0);document.contract.submit();}return false;}}"  class="button02"/>
        </s:if>
        <s:if test="isModify==1">
           <input  type="submit" value="保存"  onClick="if(!validate()){setMethod('selfProduct');setNum(5);return true;}else{return false;}" class="button01"/>
        </s:if>
        <s:if test="mainid==null">
          </s:if>
          <s:else>
           <input  type="button" value="确认提交"  onClick="if(!validate()){{if(confirm('确认提交草稿合同')){setMethod('sureSubmit');document.contract.submit();}}}" class="button01"/>
          <input type="button" value="合同拆分导出" class="button01" onClick="javascript:window.open('/yx/contract/contractSplitTable.action?contractMainInfoSid=<s:property value="mainid"/>')"/>
          </s:else>
          <s:if test="isModify==1">
          <input  type="submit" value="删除"  onClick="{if(confirm('确认删除草稿合同')){setMethod('delContract');return true;}return false;}" class="button01"/>
           <input  type="submit" value="返回"  onClick="setMethod('goback');"  class="button01"/>
           </s:if>      
        </div>
<!--else-->
</s:form>
<script type="text/javascript">
<s:iterator value="ownproductlist" status="oplist">
showsum(<s:property value="#oplist.index"/>);
</s:iterator>
 function validate(){
	   var ev2=new Validator();
       with(contract){    
        <s:iterator value="ownproductlist" status="oplist">
           ev2.test("integer","数量为空或不是数字",$('ownproductlist[<s:property value="#oplist.index"/>].conOwnProdAmount').value);
           ev2.test("float","价格为空或不是数字",$('ownproductlist[<s:property value="#oplist.index"/>].conOwnProdPrice').value);
        </s:iterator>
        
        var allmoney=0;
        <s:iterator value="ownproductlist" status="oplist">
           var amount = document.getElementById("contract_ownproductlist_"+<s:property value="#oplist.index"/>+"__conOwnProdAmount");
   		   var price = document.getElementById("contract_ownproductlist_"+<s:property value="#oplist.index"/>+"__conOwnProdPrice");
           if(amount.value==""||price.value==""){
             var sum=0;
           }else{
             var sum = parseFloatNumber(amount.value)*parseFloatNumber(price.value);
           }
           allmoney=allmoney+sum;
        </s:iterator>
        var eqmoney=0
        <s:iterator value="eqinfo">
          eqmoney=eqmoney+parseFloatNumber("<s:property value="money"/>");
        </s:iterator>
        <s:if test="importFromFile" >
	    </s:if>
	    <s:else>	 
        if((parseFloatNumber(allmoney)-parseFloatNumber(eqmoney))>0){
          ev2.addError("自有产品金额超过设备总金额");
        }
        </s:else>                                                            
        }           
        	if (ev2.size() > 0) {
		        	ev2.writeErrors(errorsFrame, "errorsFrame");
		    	 return true;
		    }
		 return false;
}
function sureSubmit()
{
	if(!validate())
	{
		if(window.confirm("确认提交草稿合同？")){
			refreshPage();
			document.contract.submit();
		}
	}
}
</script>
</body>
</html>
