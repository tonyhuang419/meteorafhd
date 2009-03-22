<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<title>收款计划管理查询</title>
<script type="text/javascript">
function doSubmit(){
	if(!validate()){
		document.realConBillpro.submit();
	}
}
function validate()
{
	var ev2=new Validator();
	ev2.test("dateYX","计划收款开始日期格式不正确！",$('startDate').value);
	ev2.test("dateYX","计划收款结束日期格式不正确！",$('endDate').value);
	ev2.writeErrors(errorsFrame,"errorsFrame");
	if(ev2.size()>0){
		return true;
	}
	return false;
}
</script>
</head>
<body style="margin: 0px;">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" scrolling="no"></iframe>
<s:form action="realConBillpro" theme="simple" target="rightFrame">
<s:hidden name="resetCondition" value="true"/>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_table02" align="center">
		<tr>
			<td align="right">合同号：</td>
			<td align="left"> 
				<s:textfield  name="conId"></s:textfield>
			</td>
		</tr>
		<tr>
			<td align="right">项目号：</td>
		<td align="left">
			<s:textfield name="itemId"></s:textfield>
		</td>
		</tr>
		<tr align="right">
		<td rowspan="2">
		计划收款日期：
		</td>
		<td align="left">
			从<s:textfield name="startDate" id="startDate" size="12"/>  <img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('startDate')" align=absMiddle alt="" />
		</td>
		</tr>
		<tr align="left" >
		<td nowrap="nowrap">至<s:textfield name="endDate" id="sDate" size="12"/> <img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('endDate')" align=absMiddle alt="" />
				</td>
		</tr>
		<tr class="bg_table04">
		<td colspan="2" align="center">
			<input type="button" class="button01" value="查询" onclick="doSubmit();"/>
		</td>
		</tr>
	</table>
</s:form>
</body>
</html>