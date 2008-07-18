<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>发票修改</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
function avaliableText(){
var selectX = document.getElementById("selectX");
var info;
for(var i=0; i<selectX.options.length; i++){
		if(selectX.options[i].selected){
			info = selectX.options[i].text;
		}
	}
	if( info == '是'){
		document.getElementById("ava1").disabled = false;
		document.getElementById("ava2").disabled = false;
		document.getElementById("ava3").disabled = false;
		document.getElementById("cc").onclick = function(){
			setday(this,CalendarSelector2,2000,2010,'yyyy-MM-dd');
		}
	}
	else{
		document.getElementById("ava1").disabled = true;
		document.getElementById("ava2").disabled = true;
		document.getElementById("ava3").disabled = true;
		document.getElementById("cc").onclick = null;
	}
}
function saveEdit(ids){
	document.forms(0).action = "/yx/assistance/assistanceTicket.action?method=editAssistanceTicket&ids="+ids;
	document.forms(0).submit();
}

</script>
</head>
<body>
<s:form theme="simple" action="assistanceTicket.action?method=editAssistanceTicket">
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center"><table width="96%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td height="3" align="left">当前页面:外协管理->修改外协发票</td>
      </tr>
      <tr>
        <td class="bg_table01" height="1"><img src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
      </tr>
    </table>
        <table width="96%" border="0" cellspacing="1" cellpadding="1">
        <s:iterator value="info.result" id="ticket">
         <tr align="center">         
            <td width="17%" align="right" class="bg_table02">外协供应商：</td>
            <td width="33%" align="left" class="bg_table02"><s:textfield name="#ticket[1]" readonly="true" id="supplyId"></s:textfield>
            <s:hidden name="supplierid" id="supplierid"></s:hidden>
            <input type="button" value="…" onclick="javascript:openUrl('chooseSup.action');"></td>
            <td width="13%" align="right" class="bg_table02">&nbsp;</td>
            <td width="37%" align="left" class="bg_table02">&nbsp;</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">发票号：</td>
            <td class="bg_table02" align="left"><s:textfield name="at.num" size="15"/></td>
            <td class="bg_table02" align="left"><div align="right">发票类型：</div></td>
            <td class="bg_table02" align="left"><select size="1" name="at.type">
                <option>增票</option>
                <option>普票</option>
            </select></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">发票金额：</td>
            <td align="left" class="bg_table02"><label>
              <s:textfield name="at.money" size="15"/>
            </label></td>
            <td class="bg_table02" align="right">开票日期：</td>
            <td class="bg_table02" align="left"><input type="text" id="ticketDate" name="ticketDate" readonly="readonly" value="<s:property value="at.ticket_Time"/>" onClick="javascript:ShowCalendar(this.id)" size="12" />
            <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('ticketDate')" align=absMiddle alt=""  /></td>
          </tr>
          <tr align="center">
            <td height="79" align="right" class="bg_table02">备注：</td>
            <td class="bg_table02" align="left" colspan="3" ><s:textarea name="at.remark" cols="30" rows="5"/></td>
          </tr>
          <tr><td><s:hidden name="at.id"/></td></tr>
          <tr><td><s:hidden name="at.customerId"/></td></tr>
          <tr><td><s:hidden name="at.contractId"/></td></tr>
          <tr><td><s:hidden name="at.is_active"/></td></tr>
		</s:iterator>

          <tr class="bg_table03" align="center" style="height:42px">
            <td colspan="4"><table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td align="right" colspan="2"><input class="button01" type="submit" name="gonext" value="保    存" ></td>
                    <td align="right" colspan="2"><input class="button01" type="button" name="gonext" value="关    闭" onClick="javascript:window.close()"></td>
                  </tr> 
                </tfoot>
            </table></td>
          </tr>
    </table></td>
  </tr>
</table>
</s:form>
</body>

</html>
