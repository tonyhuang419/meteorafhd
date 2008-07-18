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
function setDelNum(button){
     var hidden=document.getElementById("eventdelid");
      hidden.setAttribute("value",button.id)
}
function setAddNum(button){
 var hidden=document.getElementById("eventaddid");
  hidden.setAttribute("value",button.id);
  
}
</script>

<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
</head>
<body  >
<div align="left">
  <div  style="color:#000000">当前页面：合同管理 -&gt; 正式合同变更</div>
</div>
 <s:form action="formalContractModify" theme="simple">
 <s:hidden name="method" value="saveEventInfo" />
 <div  align="left" style="color: #FF0000" >
<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" scrolling="no"></iframe></div>
<table width="100%" height="100%" border="0"  align="center" cellpadding="1" cellspacing="1" class="bg_table02">
  <tr><td  colspan="4"  align="right" class="bg_table01"  height="0.5">
    <img src="./../images/temp.gif" width="1" height="1"></td>
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
            <input type="hidden"  name="eventaddid" id="eventaddid"/>
            <input type="hidden"  name="eventdelid" id="eventdelid"/>
        <div id="content" class="content1" >
            <div  id="content1"  >
     
            </div>
   
          <div id="content2" >
            <!--合同项目开始-->
          <table   align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
             <tr>
             <td width="551" class="bg_table01" align="center">项目号</td>
             <td width="10%" class="bg_table01" align="center">是否WAR项目</td>
             <td width="20%" class="bg_table01" align="center">工程部门</td>
             <td width="10%" class="bg_table01" align="center">负责人</td>
             <td width="20%" class="bg_table01" align="center">项目组成</td>
             <td width="10%" class="bg_table01" align="center">含税金额</td>
             <td width="7%"  class="bg_table01" align="center">操作</td>
             </tr>
             <s:iterator value="iteminfolist" status="ilist"   >
             <tr>
             <td width="3%"  class="bg_table02" align="center"><s:textfield disabled="true"  name="%{'iteminfolist['+#ilist.index+'].conItemId'}"  size="14" /></td>
             <td width="10%" class="bg_table02" align="center"><s:checkbox disabled="true" name="%{'iteminfolist['+#ilist.index+'].War'}"></s:checkbox></td>
             <td width="20%" class="bg_table02" align="center"><s:select disabled="true"  name="%{'iteminfolist['+#ilist.index+'].itemResDept'}" headerKey="" headerValue="--请选择--" list="dutydepartmentlist" listKey="typeSmall" listValue="typeName" >
						</s:select></td>
             <td width="10%" class="bg_table02" align="center"><s:textfield disabled="true" name="%{'iteminfolist['+#ilist.index+'].itemResDeptP'}" size="12" /></td>
             <td width="20%" class="bg_table02" align="center">总费用</td>
             <td width="10%" class="bg_table02" align="center"><s:property value="accountmoney"/></td>
             <td width="7%" class="bg_table02"> <s:hidden name="%{'iteminfolist['+#ilist.index+'].contractMainInfo'}" ></s:hidden>
              <s:hidden name="%{'iteminfolist['+#ilist.index+'].conItemMinfoSid'}" ></s:hidden>
             </td>
             </tr>
             <s:iterator value="contractItemInfolist" status="infolist">
                <tr>
                  <td colspan="4"  class="bg_table02"></td>
                  <td class="bg_table02" align="center"><s:property  value="typeManageService.getYXTypeManage(1012,itemcontrent).typeName"/><s:hidden name="%{'iteminfolist['+#ilist.index+'].contractItemInfolist['+#infolist.index+'].itemcontrent'}" ></s:hidden></td>
                  <td class="bg_table02 STYLE4" align="center"><s:property  value="conItemAmountWithTax"/><s:hidden name="%{'iteminfolist['+#ilist.index+'].contractItemInfolist['+#infolist.index+'].conItemAmountWithTax'}"></s:hidden></td>
                  <td class="bg_table02" align="center"><input type="button" id="<s:property value="conItemInfoSid"/>" value="删除" disabled="true" onclick="setNum(2);setDelNum(this);document.contract.submit();" /></td>
              </tr>   
              </s:iterator>                   
             <tr>
             <td colspan="4"  class="bg_table02">&nbsp;</td>
             <td class="bg_table02" align="center">
                <s:select disabled="true" headerKey="" headerValue="--请选择--" name="moneytype" list="itemdesigntypelist" listKey="typeSmall" listValue="typeName" >
						</s:select></td>
              <td class="bg_table02 STYLE4" align="center"><input name="money" disabled="true" type="text" id="textfield" size="10" maxlength="10" /></td>
              <td class="bg_table02" align="center"><input type="button" disabled="true" id="<s:property value="conItemMinfoSid"/>" value="添加" onclick="setNum(2);setAddNum(this);document.contract.submit();" /></td>
              </tr>              
              <tr><td colspan="8"><hr></td>
              </tr>
              <s:hidden></s:hidden>
            </s:iterator>
          </table>
              <!--合同项目结束-->
          </div>
          <div id="content3"    class="hidecontent">
           
            <!--开票和收款阶段结束-->
          </div>
          <div id="content4"   class="hidecontent"  >
                      
              <!--开票和收款计划开始-->
      
  
         
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
function validate(){

		 return false;
}
</script>
</body>
</html>
