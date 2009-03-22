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
 <s:hidden name="method" value="saveOtherInfo" />
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
          <div id="content8"  >
          <s:hidden name="otherinfo.otherInfoId"></s:hidden>
              <!--else-->
              <table width="100%"  >
                <tr>
                  <td width="150" class="bg_table02" align="right">是否需要开工报告：</td>
                  <td width="27" class="bg_table02" align="center">
                     <s:checkbox disabled="true" name="otherinfo.needPerativeReport"></s:checkbox>
                      </td>
                  <td width="573" class="bg_table02"></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right">
                          是否需要竣工验收证书：
                  </td>
                  <td class="bg_table02" align="center">
                  <s:checkbox disabled="true" name="otherinfo.needFinallyReport"></s:checkbox>
                  </td>
                  <td class="bg_table02">
                     </td>
                </tr>
                <tr>
                  <td  class="bg_table02" align="right">是否需要实物交接：</td>
                  <td class="bg_table02" align="center">
                    <s:checkbox disabled="true" name="otherinfo.needRecivedThing"></s:checkbox>                 
                  </td>
                  <td class="bg_table02">
                      </td>
                </tr>
                <tr class="bg_table02">
                  <td class="bg_table02" align="right">备注：</td>
                  <td colspan="2" align="left" class="bg_table02">
                      <s:textarea disabled="true" name="otherinfo.otherRemarks" cols="50" rows="5"></s:textarea>
                  </td>
                </tr>
                <tr class="bg_table02">
                   <td  height="*" class="bg_table02" align="right">&nbsp;</td>
                   <td colspan="2" align="right" class="bg_table02"> </td>
                </tr>
              </table>
            <!-- ELSE END -->
          </div>
  </div><!--总体结束DIV-->
        <div align="center">
          <input  type="submit" value="变更提交"  onClick="setNum(0);" class="button02" >
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
