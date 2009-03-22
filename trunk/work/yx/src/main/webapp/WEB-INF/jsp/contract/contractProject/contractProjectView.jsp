<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>合同项目成本确认</title>
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
</head>
<body  leftmargin="0">

<p>&nbsp;</p>
<s:form name="contractProjectView" action="" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td height="0.5" colspan="4" align="right" class="bg_table01"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr>
			<td class="bg_table02">
			<div align="center"><strong>合同号：<s:property value="projectInfo1[1]" /></strong></div>
			</td>
			<td class="bg_table02">
			<div align="center"><strong>项目号：<s:property value="projectInfo1[0].conItemId" /></strong></div>
			</td>
		</tr>
		<tr>
			<td class="bg_table02">
			<div align="center">剩余外协：</div>
			</td>
			<td width="51%" class="bg_table02">
			<div align="center"><s:property value="projectInfo1[0].remainAssistance" /></div>
			</td>
		</tr>
		<tr>
			<td width="49%" class="bg_table02">
			<div align="center">剩余发票：</div>
			</td>
			<td class="bg_table02">
			<div align="center"><s:property value="projectInfo1[0].remainBill" /></div>
			</td>
		</tr>
		<s:iterator value="info.result" id="projectlist" status="status">
		<tr>
			<td class="bg_table02">
			<div align="center">退回说明<s:property value="#status.index"/>：</div>
			</td>
			<td class="bg_table02"><s:property value="#projectlist.feedbackInfo" /></td>
		</tr>
		</s:iterator>
		<tr>
			<td colspan="7" class="bg_table04">

			<div align="center"><input type="submit" name="button2"
				id="button2" value="关  闭" class="button01" onClick="window.close();">
			</div>
			</td>
		</tr>
	</table>
<script language="javascript"> 

</script>
</s:form>
</body>
</html>

