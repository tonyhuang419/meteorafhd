<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>项目号修改</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
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
   if(isEmpty(document.forms(0).upResNubmer.value)){
          alert("请输入项目号!");
          document.forms(0).upResNubmer.focus();
          return false;
    }
   document.itemForm.action="/yx/contract/contractItemUpdateManager.action?method=doUpdateConItemId";
  
   document.itemForm.submit();
   
   //window.opener.window.document.forms(0).submit(); 
  // window.close();
   
  
   }
function doSubmitClose(){
  //window.opener.window.document.forms(0).submit(); 
  window.close();
}
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
<div align="center"><font color="red"><s:property value="message"/></font></div>
<form method="post" name="itemForm" >
<input type="hidden" value="itemsNo<s:property value="resNo"/>" name="thjr">
<input type="hidden" value="<s:property value="resNo"/>" name="resNo">
<p>&nbsp;</p>
<table width="100%" border=0   cellpadding="1" cellspacing=1 >
    <tr>
    <td  colspan="2"align="right" class="bg_table01"  height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td width="42%"  class="bg_table02"><div align="right">原项目号：</div></td>
    <td class="bg_table02"><div align="left"><input readonly="readonly" name="resNubmer" type="text" id="resNubmer" size="15" maxlength="20" value="<s:property value="resNubmer"/>"></div></td>
  </tr>
  <tr>
    <td  class="bg_table02"><div align="right">项目号改为：    </div></td>
    <td class="bg_table02"><input name="upResNubmer" type="text" id="resNubmer" size="15" maxlength="20"></td>
  </tr>
    <tr>
    <td colspan="5"  class="bg_table04">
      <div align="center">
        <input type="button" name="button" id="button" value="保  存" class="button01" onclick="doSubmit();">      
        <input type="button" name="button2" id="button2" value="关  闭" class="button01"  onclick="doSubmitClose();">
      </div></td>
  </tr>
</table>
</form>
</body>
</html>
