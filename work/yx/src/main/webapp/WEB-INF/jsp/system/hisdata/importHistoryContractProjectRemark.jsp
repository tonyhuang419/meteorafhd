<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>合同导入</title>
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
<s:form action="importHistoryContractProjectRemark" method ="POST" theme="simple" enctype ="multipart/form-data">

<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td  height="0.5" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr class="bg_table02">
    <td><br>     <div align="center">
        <input type="file" name="excelFile">
      </div><br></td>
  </tr>
  
  <tr class="bg_table04">
    <td><div align="center">
        <input type="submit" name="save3232" value="确  定" onclick="return check()" class="button01">
        <input type="button" name="button2" id="button2" value="关  闭" class="button01"  onClick="window.close();">
    </div></td>
  </tr>
</table>
<script language="javascript"> 
if('<s:property value="#isSuccess"/>' == 'true'){
	alert("EXCEL文件已导入成功！");
}

function check(){	
	if(!validate()){
		document.forms(0).submit();
	}
	return false;
}

function validate(){
       var ev2=new Validator();
       with(importHistoryContractProjectRemark){  
       ev2.test("notblank","上传EXCEL文件不能为空",$('excelFile').value);       
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

