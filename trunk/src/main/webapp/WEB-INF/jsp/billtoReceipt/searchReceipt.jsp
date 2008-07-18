<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>开票/收据管理</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<script language="javascript" src="<s:url value="/commons/scripts/checkLength.js"/>"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
	function check(){
		if(!validate()){
		   document.forms(0).submit();
		}
	}
	function validate()
	{
		var ev = new Validator();
	    with(receiptToBillQuery){
			 ev.test("notblank", "开票金额起始不正确,请重新输入...", compareData(getNumberChar(document.getElementById('minBillAmount').value),getNumberChar(document.getElementById('maxBillAmount').value)));
		     ev.test("notblank", "开票时间开始时间不能大于结束时间...", checkTime($('stratReceiptDate').value,$('endReceiptDate').value));
	   }
	    ev.writeErrors(errorsFrame, "errorsFrame");
	   if (ev.size() > 0) {
		   return true;
		  
		}
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
<body>
<s:form action="receiptToBillQuery"  theme="simple" target="rightFrame">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<table width="100%" class="bg_table02">
	<tr><td></td></tr>
	<tr>
		<td class="bg_table02" align="right">合同号：</td>
		<td width="75%">
		<s:textfield name="contractId" />
		</td>
	</tr>
	<tr>
		<td  align="right">	客户名称：</td>
		<td class="bg_table02"  align="left" >
					<input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="clientSelectId" list="yxClientCodeList" emptyOption="true" cssStyle="margin-left:-150px;width:168px;" listKey="cli.id" listValue="cli.name" onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
					<s:hidden name="customerId" id="clientId"></s:hidden>
					</td>
	</tr>
	<tr>
		<td>
		<div align="right">开票金额：</div>
		</td>
		<td class="bg_table02" align="left">
		 大于：<s:textfield name="minBillAmount" size="10" maxLength="10" onkeyup="quanjiao(this)" /> <br>
		 小于：<s:textfield name="maxBillAmount" size="10" maxLength="10" onkeyup="quanjiao(this)" />
		</td>
	</tr>
	<tr>
		<td class="bg_table02" align="right">
			开票日期:
		</td>
	<td class="bg_table02" align="left">
		<div align="lift">从：
			  	<input type="text" id="receiptDate" name="stratReceiptDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="7">
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('receiptDate')" align=absMiddle alt=""  />
			</div>
		<br>
		<div align="lift">至：
			  	<input type="text" id="receiptDate1" name="endReceiptDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="7">
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('receiptDate1')" align=absMiddle alt=""  />
			</div>
		</td>
	</tr>
	<tr class="bg_table04">
		<td colspan="2" align="center">
		<input type="button" name="button2"	id="button2" value="查询" class="button01" onClick="check()" />
		</td>
	</tr>
</table>
</s:form>
</body>
</html>
