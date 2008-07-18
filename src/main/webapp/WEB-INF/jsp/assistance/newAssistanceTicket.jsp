<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协合同新建</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
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
//验证通过表单提交
	function doSave(){
		if(!validate()){
			text = document.getElementById("remark").value;
			if(text.length>200){
				alert("备注过长!");
			}else{
				document.forms(0).action = "<s:url action="assistanceTicket"><s:param name="method">saveAssistanceTicket</s:param></s:url>";
				document.forms(0).submit();
			}
		}
	}
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(assistanceTicket){
		       ev2.test("notblank","供应商为空,请输入供应商名称!",$('supplierName').value);
		       ev2.test("notblank","发票号为空,请输入发票号!",$('at.num').value);
		       ev2.test("notblank","发票金额为空,请输入金额数字!!",$('at.money').value);
		       ev2.test("+float+0","发票金额不是数字,请输入0~9数字!",$('at.money').value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
</script>
</head>
<body>
<s:form action="assistanceTicket" theme="simple">
	<s:hidden name="at.id"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center"><table width="96%" border="0" cellspacing="1" cellpadding="1">
    <iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
      <tr>
        <td height="3" align="left">当前页面:外协管理->外协发票录入</td>
      </tr>
      <tr>
        <td class="bg_table01" height="1"><img src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
      </tr>
    </table>
        <table width="96%" border="0" cellspacing="1" cellpadding="1">
          <tr align="center">
            <td width="17%" align="right" class="bg_table02">外协供应商：</td>
            <td width="33%" align="left" class="bg_table02"><s:textfield name="supplierName" id="supplyId" size="15" readonly="true"></s:textfield>
            <s:hidden id="supplierid" name="supplierid"/>
            <input type="button" value="…" onclick="javascript:openUrl('chooseSup.action');"></td>
            <td width="13%" align="right" class="bg_table02">&nbsp;</td>
            <td width="37%" align="left" class="bg_table02">&nbsp;</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">发票号：</td>
            <td class="bg_table02" align="left"><s:textfield name="at.num" size="15"></s:textfield></td>
            <td class="bg_table02" align="left"><div align="right">发票类型：</div></td>
            <td class="bg_table02" align="left"><select size="1" name="at.type">
                <option>增票</option>
                <option>普票</option>
            </select> </td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">发票金额：</td>
            <td align="left" class="bg_table02">
              <s:textfield name="at.money" size="15"></s:textfield></td>
            <td class="bg_table02" align="right">开票日期：</td>
            <td class="bg_table02" align="left"><input type="text" id="ticketDate" name="ticketDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('ticketDate')" align=absMiddle alt=""  /></td>
          </tr>
          <tr align="center">
            <td height="79" align="right" class="bg_table02">备注：</td>
            <td class="bg_table02" align="left" colspan="3">
            <s:textarea name="at.remark" cols="30" rows="5" id="remark"></s:textarea></td>
          </tr>
          <tr class="bg_table03" align="center" style="height:42px">
            <td colspan="4"><table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td align="right" colspan="2"><input class="button01" type="button" name="gonext" value="保    存" onClick="javascript:doSave();"></td>
                    <td align="right" colspan="2"><input class="button01" type="reset" name="gonext" value="重    填" ></td>
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
