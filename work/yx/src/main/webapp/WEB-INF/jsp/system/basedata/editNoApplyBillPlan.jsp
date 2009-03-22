<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>拆分金额</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function submitEdit()
	{
		var ev = new Validator();
		ev.test("notblank", "预计开票日期不能为空", $('noApplyBillPlan.realPredBillDate').value);
		ev.test("dateYX", "预计开票日期格式不正确", $('noApplyBillPlan.realPredBillDate').value);
		ev.test("notblank", "预计收款日期不能为空", $('noApplyBillPlan.realPredReceDate').value);
		ev.test("dateYX", "预计收款日期格式不正确", $('noApplyBillPlan.realPredReceDate').value);
	    ev.test("notblank", "开票金额不能为空", $('noApplyBillPlan.realBillAmount').value);
		ev.test("+float+0", "开票金额必须是数字", $('noApplyBillPlan.realBillAmount').value);
		ev.test("notblank", "收款款金额不能为空", $('noApplyBillPlan.realReceAmount').value);
		ev.test("+float", "收款款金额必须是大于0的数字", $('noApplyBillPlan.realReceAmount').value);
		ev.writeErrors(errorsFrame, "errorsFrame");
		if (ev.size() == 0) {
			document.forms(0).submit();
		}
	}
	
	
</script>
<body class="bg_table02" leftmargin="0">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<s:form action="baseData" theme="simple">
<s:hidden name="method" value="updateNoApplyBillPlan"/>
<s:hidden name="noApplyBillPlan.realConBillproSid"/>
<table >
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
        <tr align="center">
		    <td colspan="2" class="bg_table01" height="1"><img src="../images/temp.gif" width="1" height="1">
		    </td>
		</tr>
		<tr align="center">
			<td class="bg_table02" align="right">预计开票日期:</td>
			<td class="bg_table02" align="left">
				<s:textfield name="noApplyBillPlan.realPredBillDate" size="10" id="preBillDate"></s:textfield>
				<img src="/yx/commons/images/calendar.gif" onClick="ShowCalendar('preBillDate')" align=absMiddle alt=""  />
			</td>
		</tr>
		<tr align="center">
			<td class="bg_table02" align="right">预计收款日期:</td>
			<td class="bg_table02" align="left">
				<s:textfield name="noApplyBillPlan.realPredReceDate" size="10" id="preReceDate"></s:textfield>
				<img src="/yx/commons/images/calendar.gif" onClick="ShowCalendar('preReceDate')" align=absMiddle alt=""  />
			</td>
		</tr>
		<tr align="center">
			<td class="bg_table02" align="right"><font color="red"> *</font>开票金额（基准）:</td>
			<td class="bg_table02" align="left">
				<s:textfield name="noApplyBillPlan.realBillAmount" maxLength="15" size="16" onkeyup="quanjiao(this)" onblur="formatInputNumber(this)"></s:textfield>
				（原<s:property value="noApplyBillPlan.realBillAmount"/>）	
			</td>
		</tr>
		<tr align="center">
			<td class="bg_table02" align="right"><font color="red"> *</font>收款款金额:</td>
			<td class="bg_table02" align="left">
				<s:textfield name="noApplyBillPlan.realReceAmount" maxLength="15" size="16" onkeyup="quanjiao(this)" onblur="formatInputNumber(this)"></s:textfield>				
				（原<s:property value="noApplyBillPlan.realReceAmount"/>）
			</td>
		</tr>
			<td class="bg_table03" colspan="2" align="center">
				<input type="button" name="button2" value="确  认" class="button01" onclick="submitEdit()"/>	
				<input type="button" name="close" value="关  闭" class="button01" onClick="window.close()" />
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>