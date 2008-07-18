<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同变更</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script type="text/javascript">
function setNum(i){
var hidden=document.getElementById("tag");
hidden.setAttribute("value",i);
}

function setDelPurNum(button){
var hidden=document.getElementById("delPur");
hidden.setAttribute("value",button.id);
}

function time(img){
  var par=img.parentNode;
  var text=par.firstChild;
  ShowCalendar(text.id);
}
function   refreshPage(){ 
		document.forms(0).method.value = "refreshPurPage";
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
  <div  style="color:#000000">当前页面：合同管理 -&gt; 正式合同变更</div>
</div>
 <s:form action="formalContractModify" theme="simple">
 <s:hidden name="method" value="removeConPurchase" />
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

      <input type="hidden" name="delPur" id="delPur" >
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
          <div id="content5" class="hidecontent" >
            <!--未签开票关联开始-->
       
          
            <!--已关联清单开始-->
          </div>
          <div id="content6" >
            <!--申购清单结束-->
            <!--未签申购清单开始-->
              <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
                <tr align="center">
                  <td width="16%" class="bg_table01">申购单号</td>
                  <td width="16%" class="bg_table01">客户名称</td>
                  <td width="16%" class="bg_table01">申购日期</td>
                  <td width="31%" class="bg_table01">申购内容</td>
                  <td width="16%" class="bg_table01">申购金额</td>
                  <td width="5%" class="bg_table01">操作</td>
                </tr>
                <s:iterator value="purchaselist" >
                <tr align="center">
                  <td><s:property value="applyId"/></td>
                  <td><s:property value="customerId"/></td>
                  <td><s:date name="applyDate" format="yyyy-MM-dd"/></td>
                  <td><s:property value="applyContent"/></td>
                  <td><s:property value="applymoney"/></td>
                  <td><input type="submit" value="删除" disabled="true" id="<s:property value="id"/>" onclick="setNum(6);setDelPurNum(this)"></td>
                </tr>
                </s:iterator>
              </table>
<center><input type="button" value="添加" disabled="true" onclick="javascript:window.open('../contract/searchPurQuery.action?method=doDefault&mainid=<s:property value="mainid"/>','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');" ></center>
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
             <input  type="submit" value="提交变更"  onClick="setNum(0);" class="button02" >
       
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
