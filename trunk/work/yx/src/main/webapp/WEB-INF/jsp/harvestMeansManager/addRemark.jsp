<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>添加备注信息</title>
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
  	if(isEmpty(document.forms(0).remark.value)){
          	alert("请输入备注信息!");
          	return false;
    }else{
     	document.forms(0).submit();
    }
   }
<s:if test="#isSuccess == 'true'" >
   opener.reflushPage();
   this.window.close(); 
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
<s:form method="post" name="noContractHarvestManage" theme="simple" >
<s:if test="fromWhichMethod == 1" >
   <s:hidden name="method" value="sureCheck" />
</s:if>
<s:elseif test="fromWhichMethod == 2" >
   <s:hidden name="method" value="sureHisCheck" />
</s:elseif>

<s:hidden name="hasRemark" value="1"/>
<s:hidden name="selectedid" />
<s:hidden name="fromWhichMethod"/>

<table width="100%" border=0   cellpadding="1" cellspacing=1 >
    <tr>
    <td  colspan="2"align="right" class="bg_table01"  height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td class="bg_table02" align="right">请填写备注： </td>
    <td class="bg_table02"><s:textarea rows="5" name="remark" ></s:textarea></td>
  </tr>
    <tr>
    <td colspan="5"  class="bg_table04">
      <div align="center">
      <s:if test="fromWhichMethod == 1" >
         <input type="button" name="button" id="button" value="核   销" class="button01" onclick="doSubmit();">     
      </s:if>
      <s:elseif test="fromWhichMethod == 2" >
         <input type="button" name="button" id="button" value="历史核销" class="button01" onclick="doSubmit();">     
      </s:elseif>
         
        <input type="button" name="button2" id="button2" value="关  闭" class="button01"  onclick="window.close();">
      </div></td>
  </tr>
</table>
</s:form>
</body>
</html>
