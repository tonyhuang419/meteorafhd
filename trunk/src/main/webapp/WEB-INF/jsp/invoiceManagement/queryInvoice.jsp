<%@ page contentType="text/html;charset=UTF-8"%>

<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>查询</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function doSubmit()
{
	document.forms[0].submit();
}
function selectedClient(valueObj)
{
	document.forms[0].customerName.value=valueObj.clientName;
	document.forms[0].clientId.value=valueObj.clientId;
}
function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
</script>
</head>
<body leftmargin="0">
<s:form action="createInvoice.action" theme="simple" target="rightFrame">
	<s:hidden name="method" value="findInvoiceApplications" />
	<table width="100%" class="bg_table02">
		<tr>
			<td colspan="2" align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr>
			<td width="30%" class="bg_table02">
			<div align="right">客户名称：</div>
			</td>
			<td width="70%" class="bg_table02" align="left">
	
					<input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="clientSelectId" list="yxClientCodeList" emptyOption="true" cssStyle="margin-left:-150px;width:168px;" listKey="cli.id" listValue="cli.name" onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
					<s:hidden name="customerId" id="clientId"></s:hidden>
			</td>
		</tr>
		<tr>
			<td class="bg_table02">
			<div align="right">申请编号：</div>
			</td>
			<td class="bg_table02">
			<div align="left"><s:textfield name="billApplyNum" size="10"
				maxlength="10" /></div>
			</td>
		</tr>
		<tr>
			<td class="bg_table02">
			<div align="right">申请日期：</div>
			</td>
			<td class="bg_table02">
			<div align="left">从：
			<div align="left"><input type="text" id="beginApplyDate"
				name="beginApplyDate" readonly="readonly"
				onClick="javascript:ShowCalendar(this.id)" size="12"> <img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('beginApplyDate')" align=absMiddle
				alt="" /></div>
			至：
			<div align="left"><input type="text" id="endApplyDate"
				name="endApplyDate" readonly="readonly"
				onClick="javascript:ShowCalendar(this.id)" size="12"> <img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('endApplyDate')" align=absMiddle
				alt="" /></div>
			</td>
		</tr>
		<tr>
			<td class="bg_table02">
			<div align="right">月份计划：</div>
			</td>
			<td class="bg_table02" align="left"><s:select
				name="monthPlanYear"
				list="#@java.util.TreeMap@{'0000':'全部','2008':'2008','2009':'2009','2010':'2010','2011':'2011','2012':'2012','2013':'2013','2014':'2014','2015':'2015','2016':'2016','2017':'2017','2018':'2018','2019':'2019','2020':'2020'}">
			</s:select> 年 <s:select name="monthPlanMonth"
				list="#@java.util.TreeMap@{'00':'全部','01':'1','02':'2','03':'3','04':'4','05':'5','06':'6','07':'7','08':'8','09':'9','10':'10','11':'11','12':'12'}">
			</s:select> 月</td>
		</tr>
		<tr>
			<td class="bg_table02">
			<div align="right">申请状态：</div>
			</td>
			<td class="bg_table02" align="left"><s:select
				list="#@java.util.TreeMap@{0:'全部',1:'保存',2:'待确认',3:'确认通过',4:'确认退出'}"
				name="applyStatus"></s:select></td>
		</tr>
		<tr class="bg_table04">
			<td colspan="2">
			<div align="center"><input type="button" name="button2"
				id="button2" value="查询" class="button01" onclick="doSubmit();" /></div>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>