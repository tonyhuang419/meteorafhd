<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同新建</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script type="text/javascript">
function setNum(i){
var hidden=document.getElementById("tag");
hidden.setAttribute("value",i);
}
function setDelInvoiceNum(button){
var hidden=document.getElementById("delInvoice");
hidden.setAttribute("value",button.id);
}
function time(img){
  var par=img.parentNode;
  var text=par.firstChild;
  ShowCalendar(text.id);
}
function   refreshPage(){ 
		document.forms(0).method.value = "refreshInvoicePage";
		document.forms(0).submit();
}
</script>

<style type="text/css">
body {
	background-color: #FFFFFF;
}
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
 <s:hidden name="method" value="removeConInvoice" />
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
      <input type="hidden" name="delInvoice" id="delInvoice" >
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
          <div id="content5"  >
            <!--未签开票关联开始-->
               <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
               <tr>
                  <td width="14%" class="bg_table01" align="center">开票申请编号</td>
                  <td width="14%" class="bg_table01" align="center">开票性质</td>
                  <td width="14%" class="bg_table01" align="center">发票类型</td>
                  <td width="14%" class="bg_table01" align="center">申请开票金额</td>                
                  <td width="35%" class="bg_table01" align="center">开票内容</td>
                  <td width="9%" class="bg_table01" align="center">操作</td>
               </tr>
               <s:iterator value="invoicelist">
                <tr align="center">
                   <td><s:property value="billApplyNum"/></td>
                   <td><s:property  value="typeManageService.getYXTypeManage(1012,billNature).typeName"/></td>
                   <td><s:property  value="typeManageService.getYXTypeManage(1004,billType).typeName"/></td>
                   <td><s:property value="billAmountTax"/></td>
                   <td><s:property value="billContent"/></td>
                   <td><input type="submit" value="删除" id="<s:property value="billApplyId"/>" onclick="setNum(5);setDelInvoiceNum(this)"></td>
                 </tr>
                </s:iterator>
              </table>
              <center><input type="button" value="添加"onclick="javascript:window.open('../contract/searchInvoiceQuery.action?method=doDefault&mainid=<s:property value="mainid"/>','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');"></center>
          
            <!--已关联清单开始-->
          </div>
          <div id="content6" class="hidecontent">
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
            <input  type="submit" value="保存为草稿合同"  onClick="setNum(0);" class="button02"/>
        </s:if>
        <s:if test="isModify==1">
           <input  type="submit" value="保存"  onClick="setNum(1);" class="button01"/>
            <input  type="submit" value="确认提交"  onClick="setMethod('sureSubmit');{if(confirm('确认提交草稿合同')){refresh();return true;}return false;}" class="button01"/>
          <input  type="submit" value="删除"  onClick="setMethod('delContract');{if(confirm('确认删除草稿合同')){return true;}return false;}" class="button01"/>
           <input  type="button" value="返回"  onClick="javascript:history.back()" class="button01"/>
        </s:if>      
        </div>
<!--else-->
</s:form>
<script type="text/javascript">
function validate(){
		 return false;
}
</script>
</body>
</html>
