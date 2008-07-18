<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>已存在合同变更</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">


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
<body  leftmargin="0" >
<form method="post" name="itemForm" target="content">
<input type="hidden" value="itemsNo<s:property value="resNo"/>" name="thjr">
<input type="hidden" value="<s:property value="resNo"/>" name="resNo">
<p>&nbsp;</p>
<table  border=0   cellpadding="1" cellspacing=1 >
    <tr>
    <td  colspan="5"align="right" class="bg_table01"  height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td width="42%"  class="bg_table02" align="center" colspan="5">&nbsp;</td>
  </tr>
  <tr>
    <td  class="bg_table02" align="center"></td>
  </tr>
  <tr>
    <td  align="center" colspan="2">已存在待确认的变更<br>无法继续提交变更申请</td>
  </tr>
    <tr>
    <td colspan="5"  class="bg_table04">
      <div align="center">
        <input type="button" name="button2" id="button2" value="关  闭" class="button01"  onclick="window.close();">
      </div></td>
  </tr>
</table>
</form>
<script type="text/javascript">
window.resizeTo(screen.width-500,screen.height-500);
</script>
</body>
</html>
