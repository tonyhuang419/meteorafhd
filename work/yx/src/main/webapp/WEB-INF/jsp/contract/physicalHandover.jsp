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
<script type="text/javascript">
function save()
{
   if(document.forms(0).sb.value=='1')
   {
       alert("请输入日期！");
   }
   else
   {
       alert("开工日期为未确定状态，系统自动关闭！");
       window.close();
   }
}
</script>
</head>
<body leftmargin="0">
<s:form action="contractSmall" name="form1" theme="simple">
<s:hidden name="num" value="2"></s:hidden>
<p>&nbsp;</p>

<s:if test="flag==1"><s:hidden value="1" name="sb"></s:hidden></s:if>
<s:if test="flag==2"><s:hidden value="2" name="sb"></s:hidden></s:if>
<table width="100%" border=0   cellpadding="1" cellspacing=1 >
  <tr>
    <td  colspan="4"align="right" class="bg_table01"  height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td colspan="2"  class="bg_table02"><div align="center"><strong>实物交接确认</strong></div></td>
  </tr>
  <tr>
    <td width="50%"  class="bg_table02"><div align="center">合同号：</div></td>
    <td width="50%"  class="bg_table02"><div align="center"><s:property value="contractId"/></div></td>
    <s:hidden name="contract"></s:hidden>

  </tr>
  
  <tr>
    <td  class="bg_table02"><div align="center">实物交接：</div></td>
    <td  class="bg_table02">
     <div align="left">
			  	<input type="text" id="bidDate" name="time"   size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('bidDate')" align=absMiddle alt=""  />
			  	</div>
  </tr>
    <tr>
    <td colspan="7"  class="bg_table04">
      <div align="center">
        <input type="submit" name="button" id="button" value="保  存" class="button01" >
        <input type="button" name="button2" id="button2" value="关闭" class="button01"  onClick="window.close()">
      </div></td>
  </tr>
</table>
</s:form>
</body>
</html>
