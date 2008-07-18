<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style>
</head>
<body leftmargin="0">
<s:form action="contractSmall" name="form1" theme="simple">
<s:hidden name="num" value="3"></s:hidden>
<p>&nbsp;</p>
<table width="100%" border=0   cellpadding="1" cellspacing=1 >
  <tr>
    <td  colspan="4"align="right" class="bg_table01"  height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td colspan="2"  class="bg_table02"><div align="center"><strong>竣工验收</strong></div></td>
  </tr>
  <tr>
    <td width="50%"  class="bg_table02"><div align="center">合同号：</div></td>
    <td width="50%"  class="bg_table02"><div align="center"><s:property value="contractId"/></div></td>
    <s:hidden name="contract"></s:hidden>

  </tr>
  
  <tr>
    <td  class="bg_table02"><div align="center">竣工验收：</div></td>
    <td  class="bg_table02">
     <div align="left">
			  	<input type="text" id="bidDate" name="time" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('bidDate')" align=absMiddle alt=""  />
			  	</div>
  </tr>
    <tr>
    <td colspan="7"  class="bg_table04">
      <div align="center">
        <input type="submit" name="button" id="button" value="保  存" class="button01">
        <input type="button" name="button2" id="button2" value="关闭" class="button01"  onClick="window.close()">
      </div></td>
  </tr>
</table>
</s:form>
</body>
</html>
