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
<body>
<div align="left">
 <div  style="color:#000000">当前页面：合同管理 -&gt; 正式合同变更</div>
</div>
 <s:form action="formalContractModify" theme="simple">
 <s:hidden name="method" value="saveStageInfo" />
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
                <tr>
                  <td width="21%" class="bg_table01"><div align="center">预计完工日期</div></td>
                  <td width="19%" class="bg_table01"><div align="center">收款和开票阶段</div></td>
                  <td width="20%" class="bg_table01"><div align="center">预计开票日期</div></td>
                  <td width="20%" class="bg_table01" ><div align="center">预计收款日期</div></td>
                  <td width="12%"  class="bg_table01"><div align="center">阶段金额</div></td>
                  <td width="8%" nowrap class="bg_table01"><div align="center">尾款标志</div></td>
                </tr>
         <s:iterator id="stagelist" value="stagelist" status="slist"  >
                <tr>
                  <td width="21%"  class="bg_table02" nowrap align="center">
                      <s:textfield disabled="true" name="%{'stagelist['+#slist.index+'].ItemStagePredFdate'}" size="14" id="%{'ItemStagePredFdate'+#slist.index}" readonly="true"  size="12" onclick="javascript:ShowCalendar(this.id)"   />
                  <img  src="/yx/commons/images/calendar.gif"   alt=""  />
                  </td>
                  <td width="19%"  class="bg_table02"  ><div align="center">
                      <s:textfield disabled="true" name="%{'stagelist['+#slist.index+'].ItemStageName'}"  size="14" />
                    </div></td>
                  <td width="20%"  class="bg_table02" nowrap><div align="center">
                      <s:textfield disabled="true" name="%{'stagelist['+#slist.index+'].initBillPdate'}"  size="14" id="%{'initBillPdate'+#slist.index}" readonly="true"  size="12" onclick="javascript:ShowCalendar(this.id)"/>
                      <img  src="/yx/commons/images/calendar.gif"  align=absMiddle alt=""  />
                  </div>
                  </td>
                  <td width="20%" class="bg_table02" nowrap><div align="center">
                     <s:textfield disabled="true" name="%{'stagelist['+#slist.index+'].initRecePdate'}"   size="10" id="%{'initRecePdate'+#slist.index}" readonly="true"  size="12" onclick="javascript:ShowCalendar(this.id)"/>
                     <img src="/yx/commons/images/calendar.gif"  align=absMiddle alt=""  /></div></td>
                  <td width="12%" class="bg_table02"><div align="center">
                       <s:textfield disabled="true" name="%{'stagelist['+#slist.index+'].stageAmout'}"   />
                    </div></td>
                  <td width="8%" class="bg_table02"><div align="center">
                  <s:checkbox disabled="true" name="%{'stagelist['+#slist.index+'].lastAmount'}"></s:checkbox>
                    </div></td>
                </tr>
                <tr>
                  <td colspan="7"><hr></td>
                </tr>
              <s:hidden name="%{'stagelist['+#slist.index+'].conIStageSid'}"></s:hidden>
              <s:hidden name="%{'stagelist['+#slist.index+'].contractMainSid'}"></s:hidden>
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
