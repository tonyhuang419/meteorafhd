<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>项目号修改</title>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 16px
}
body {
	background-color: #FFFFFF;
}
.AutoNewline {
	word-break: break-all;/*必须*/
}
-->
</style>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<body  leftmargin="0">

<p>&nbsp;</p>
<s:form name="contractProjectInputModify" action="contractProjectInput" theme="simple">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:hidden name="method" value="modifySubmit" />
<s:hidden name="id" value="%{projectInfo1[0].conItemMinfoSid}" />
	<table width="100%" border=0   cellpadding="1" cellspacing=1 >
  <tr>
    <td  colspan="4"align="right" class="bg_table01"  height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td  class="bg_table02"><div align="center"><strong>合同号：<s:property value="projectInfo1[1]" /> </strong></div></td>
    <td  class="bg_table02"><div align="center"><strong>项目号：<s:property value="projectInfo1[0].conItemId" /> </strong></div></td>
    <td class="bg_table02"><div align="center"></div></td>
  </tr>
  <tr>
    <td width="27%" class="bg_table02"><div align="center"></div></td>
    <td width="26%" class="bg_table02"><div align="center">剩余外协</div></td>
    <td width="47%" class="bg_table02"><div align="center">剩余发票</div></td>
  </tr>
  <tr>
    <td class="bg_table02"><div align="center">原金额：</div></td>
    <td class="bg_table02"><div align="center"><s:property value="projectInfo1[0].remainAssistance" /></div></td>
    <td class="bg_table02"><div align="center"><s:property value="projectInfo1[0].remainBill" /></div></td>
  </tr>
  <tr>
   <td class="bg_table02"><div align="center"><font color="red">* </font>修改为：</div></td>
    <td class="bg_table02"> 
      <div align="center"><s:textfield onkeypress="with(window.event)return keyCode<58&&keyCode>47||keyCode==46" name="remainAssistance"></s:textfield></div></td>
    <td class="bg_table02">    
      <div align="center"><s:textfield onkeypress="with(window.event)return keyCode<58&&keyCode>47||keyCode==46" name="remainBill"></s:textfield></div></td>
  </tr>
  <tr>
    <td colspan="3"  class="bg_table04"><div align="center">
        <input type="button" name="button" id="button" value=" 保  存 " class="button01"  onclick="return check()">&nbsp;
        <input type="button" name="button2" id="button2" value=" 关  闭 " class="button01"  onClick="window.close();">
   
      </div></td>
  </tr>

</table>
<script language="javascript"> 
function check(){	
	if(!validate()){
		document.forms(0).submit();
	}
	return false;
}

function validate(){
       var ev2=new Validator();
       with(contractProjectInputModify){  
       ev2.test("notblank","剩余外协不能为空",$('remainAssistance').value);       
       ev2.test("notblank","剩余发票不能为空",$('remainBill').value);
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}
</script>
</s:form>
</body>
</html>

