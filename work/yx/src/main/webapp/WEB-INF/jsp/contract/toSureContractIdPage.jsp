<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>

<title>确认合同号</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script type="text/javascript">
var whitespace=" \t\n\r";
function isEmpty(s){
var i;
if((s==null)||(s.length==0))
   return true;
for(i=0;i<s.length;i++){
    var c=s.charAt(i);
    if(whitespace.indexOf(c)==-1)
      return false;
 }
 return true;
}

function doSubmit(){
    	document.searchContractOkList.action="/yx/contract/searchContractOkList.action?method=changeTrueState";
    	document.searchContractOkList.submit();
}
  
function cancel(){
       	document.searchContractOkList.action="/yx/contract/searchContractOkList.action?method=doDefault";
    	document.searchContractOkList.submit();
}
function isInput(inputId){
    var initId = document.getElementById("initContractId");
    if(inputId!=initId.value){
      var isModify = document.getElementById("isModify");
      isModify.value=1;
    }else{
       var isModify = document.getElementById("isModify");
      isModify.value=0;
    }


}
<s:if test="#isfalse == 'true'">
    <s:if test="#isInput == 'true' ">
    alert("所填合同号重复,请重新检查合同号！");
    </s:if>
    <s:else>
    alert("原合同号已被他人抢先使用，已重新生成新合同号，请重新确认！");
    </s:else>
</s:if>
</script>

<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.STYLE1 {
	font-size: 16px;
	color: #000000;
}
.STYLE2 {
	font-size: 14px
}
-->
</style>
</head>
<body leftmargin="0">
<s:form method="post" target="content" action="searchContractOkList" theme="simple"  >
<input type="hidden" name="groupId" value="<s:property value="groupId"/>"/>
<input type="hidden" name="expId" value="<s:property value="expId"/>"/>
<input type="hidden" name="customerId" value="<s:property value="customerId"/>"/>
<input type="hidden" name="conType" value="<s:property value="conType"/>"/>
<input type="hidden" name="minMoney" value="<s:property value="minMoney"/>"/>
<input type="hidden" name="maxMoney" value="<s:property value="maxMoney"/>"/>
<input type="hidden" name="minConSignDate" value="<s:property value="minConSignDate"/>"/>
<input type="hidden" name="maxConSignDate" value="<s:property value="maxConSignDate"/>"/>
<input type="hidden" name="conState" value="<s:property value="conState"/>"/>
<input type="hidden" value="<s:property value="conMainId[0]"/>" name="conMainId">
<input type="hidden" name="isSureContractId" value="true"/>
<p>&nbsp;</p>
<table width="100%" border=0   cellpadding="1" cellspacing=1 >
    <tr>
    <td  colspan="2"align="right" class="bg_table01"  height="0.5"><input type="hidden" id="issave" name="issave"/><img src="../../images/temp.gif" width="1" height="1"></td>
  </tr>

  <tr>
    <td width="40%" class="bg_table02"><input type="hidden" id="initContractId" value="<s:property value="contractId"/>" /><div align="right">合同号：  </div></td>
    <td class="bg_table02"> <s:textfield name="contractId" onblur="isInput(this.value)" maxlength="11" /></td>
  </tr>
  
    <tr>
    <td colspan="5"  class="bg_table04">
      <div align="center">
        <input type="hidden" name="isModify" id="isModify" />
        <input type="button" name="button" id="button" value="确  认" class="button01" onclick="doSubmit();">      
        <input type="button" name="button2" id="button2" value="取  消" class="button01" onclick="cancel();"  >
      </div></td>
  </tr>
</table>
</s:form>
</body>
</html>
