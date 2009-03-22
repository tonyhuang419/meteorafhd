<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协发票修改</title>
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
	function setParentValueByChild(returnObj){
		document.assistanceTicket.elements('ac.assistanceName').value= returnObj.acName;
		document.assistanceTicket.elements('assistanceConId').value= returnObj.acId;
		document.assistanceTicket.elements('supplierName').value= returnObj.supName;
		document.assistanceTicket.elements('supplierid').value= returnObj.supId;
		document.getElementById("acContractId").innerHTML = returnObj.acContractId;
		document.getElementById("acProjectId").innerHTML = returnObj.acProjectId;
	}
function saveEdit(){
	if( !validate() ){
		document.forms(0).submit();
	}
}


	//验证表单
	function validate(){
		var ev2=new Validator();
		with(assistanceTicket){
		       ev2.test("notblank","供应商为空,请输入供应商名称!",$('supplyId').value);
		       ev2.test("notblank","发票号为空,请输入发票号!",$('num').value);
		       ev2.test("+integer+0","发票号请输入数字!",$('at.num').value);
		       ev2.test("notblank","发票金额为空,请输入金额数字!!",$('money').value);
		       ev2.test("+float+0","发票金额不是数字,请输入0~9数字!",$('money').value);
		       ev2.test("dateYX","发票开具日期格式不正确！",$('ticketDate').value);
		       ev2.test("length","备注请少于200字符",$('remark').value,200);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	function checkPayNo(obj)
	{	
		var ev2=new Validator();
		var payNo = obj.value;
		var url = "/yx/assistance/assistanceTicket.action";
		var method = "checkPayNoNotExists";
		 var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		      if(responseText == "false"){
			 	ev2.addError("发票号：["+payNo+"] 已经存在！请重新输入");	
			 	obj.value = "";
			 	obj.focus();
			 }
			  ev2.writeErrors(errorsFrame, "errorsFrame");
		    });
		  var t = document.assistanceTicket.ticketId.value;
		   myRequest.send("method="+method+"&ticketNo="+payNo+"&ticketId="+t+"&randomNum="+Math.random());
	}
	
	function checkMoney(){
		var obj = $('at.money');
		var ev2=new Validator();
	    ev2.test("notblank","外协合同名称为空,请选择外协合同!",$('ac.assistanceName').value);
	    ev2.test("notblank","发票金额为空,请输入金额数字!!",$('at.money').value);
		ev2.test("+float+0","发票金额不是数字,请输入0~9数字!",$('at.money').value);
		if (ev2.size() > 0) {
			ev2.writeErrors(errorsFrame, "errorsFrame");
		     return ;
		 }
		var money = parseFloatNumber(obj.value);
		var url = "/yx/assistance/assistanceTicket.action";
		var method = "checkTicketMoney";
		var assistanceConId = $('assistanceConId').value;
		var ticketType = $('at.type').value;
		var ticketId = $('ticketId').value;
		  var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		      var result = JSON.decode(responseText);
			  if(result.isOverAmount){
			  	 obj.value="";
			     ev2.addError("你输入的金额不能超过:"+result.amount);
			  } 
		    });
		myRequest.send("method="+method+"&assistanceConId="+assistanceConId+"&ticketMoney="+money+"&ticketType="+ticketType+"&ticketId="+ticketId+"&randomNum="+Math.random());
	    ev2.writeErrors(errorsFrame, "errorsFrame");  
		formatInputNumber(obj);
	}
</script>
</head>
<body>
<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<s:form theme="simple" action="assistanceTicket">
<s:hidden name="method" value="editAssistanceTicket"></s:hidden>
<s:hidden name="assistanceConId"></s:hidden>
<s:hidden name="ticketId"></s:hidden>
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
         <tr align="center">
           <td width="13%" align="right" class="bg_table02">外协合同名称：</td>
            <td width="37%" align="left" class="bg_table02">
            	<s:textarea name="ac.assistanceName" rows="2" cols="20" readonly="true">            	
            	</s:textarea>
            	 <input type="button" value="…" onclick="javascript:openUrl('assistanceTicket.action?method=enterRelationContract');">
            </td>
            <td width="17%" align="right" class="bg_table02"><font color="red"> *</font>外协供应商：</td>
            <td width="33%" align="left" class="bg_table02">
            <s:textarea name="supplierName" id="supplyId" rows="2" cols="20"  readonly="true">
            </s:textarea>
            <s:hidden id="supplierid" name="supplierid"/>
           </td>
          </tr>
          
             <tr align="center">
          
          	<td class="bg_table02" align="right">外协合同号：</td>
            <td class="bg_table02" align="left">
            <label id="acContractId">
            	<s:property value="ac.assistanceId"/>
            </label>
            </td>
            <td class="bg_table02" align="left"><div align="right">主体项目号：</div></td>
            <td class="bg_table02" align="left">
            <label id="acProjectId">
            	<s:property value="ac.mainProjectId"/>
            </label>
          	</td>
          
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>发票号：</td>
            <td class="bg_table02" align="left"><s:textfield name="at.num" size="15" id="num" maxlength="15"  onkeyup="this.value=this.value.replace(/\D/g,'')" onblur="checkPayNo(this)"/><s:hidden name="ac.is_related"/></td>
            <td class="bg_table02" align="left"><div align="right"><font color="red"> *</font>发票类型：</div></td>
            <td class="bg_table02" align="left">
            <s:select name="at.type" list="invoiceList" listKey="typeSmall"  onchange="checkMoney();"
				listValue="typeName" id="billType"></s:select>
			</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>发票金额：</td>
            <td align="left" class="bg_table02"><label>
              <s:textfield name="at.money" size="15" onblur="checkMoney();" id="money"/>
            </label></td>
            <td class="bg_table02" align="right">开票日期：</td>
            <td class="bg_table02" align="left"><input type="text" id="ticketDate" name="ticketDate" value="<s:property value="at.ticket_Time"/>"  size="12" />
            <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('ticketDate')" align=absMiddle alt=""  /></td>
          </tr>
          <tr class="bg_table03" align="center">
            <td colspan="4"><table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td align="right" colspan="2"><input class="button01" type="button" name="gonext" value="保    存" onclick="saveEdit()" ></td>
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
