<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>修改金额</title>
</head>

<script type="text/javascript">
	//验证通过表单提交
	function doSave(){
	      var amountw=document.getElementById("invoice").value;
	      //increaceAmount = billAmount - invoiceAmount
	      var increaceAmount = parseFloatNumber("<s:property value="increaceAmount"/>");
	      var sumamount=parseFloatNumber(amountw) + increaceAmount;
	      var appcount=parseFloatNumber("<s:property value="appAmount"/>");
		if(!validate()){
		    if(sumamount>appcount){
		       alert("开票金额不能大于"+(appcount - increaceAmount));
		       return;
		    }
			document.forms(0).submit();
		}
	}
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(billReceipt){
		       ev2.test("notblank","开票金额为空,请输入开票金额!",$('invoiceAmount').value);
		       ev2.test("notblank","开票号为空,请输入开票号!",$('invoiceNo').value);
		       ev2.test("notblank","日期为空,请输入开票日期!!",$('invoiceDate').value);
		       ev2.test("dateYX","日期输入格式不正确!如:yyyy-mm-dd",$('invoiceDate').value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	
function valNum(ev)   
{   
   if(window.event.shiftKey)   
        {   
           ev.returnValue = "";   
        }   
        else   
        {   
    var e = ev.keyCode;   
    //允许的有大、小键盘的数字，左右键，backspace, delete, Control + C, Control + V   
    if(e != 188 &&e != 110 && e != 48 && e != 49 && e != 50 && e != 51 && e != 52 && e != 53 && e != 54 && e != 55 && e != 56 && e != 57 && e != 96 && e != 97 && e != 98 && e != 99 && e != 100 && e != 101 && e != 102 && e != 103 && e != 104 && e != 105 && e != 37 && e != 39 && e != 13 && e != 8 && e != 46 && e != 9)   
    {   
        if(ev.ctrlKey == false)   
        {   
            //不允许的就清空!   
            ev.returnValue = "";   
        }   
        else   
        {   
            //验证剪贴板里的内容是否为数字!   
            valClip(ev);   
        }   
    }   
    }   
}  
</script>
<body>
<s:form action="billReceipt" theme="simple">
<div align="left" style="color: #000000">
<p>当前页面：开票管理 -> 修改发票金额管理</p>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
</div>
<div><font color="red"><s:property value="errorInfo" /></font></div>
<s:hidden name="method" value="updateMount" />
<s:hidden name="billId"/>
<s:hidden name="amountId"/>
 <table align="center" border=1 cellpadding="1" cellspacing=1  width="100%" id="billApplyList"  bordercolor="#808080" style="border-collapse: collapse;">
<tr class="bg_table02">
	<s:iterator value="billList" id="bList">
	<td  align="right">开票申请编号：</td>
	<td  align="left">
	<s:property value="#bList.billApplyNum" /></td>
	</s:iterator>
</tr>
<br/>
	<s:iterator value="invoiceList">
	<input type="hidden" name="invoiceID" value="<s:property value="id"/>" />
<tr class="bg_table02">
	<td  align="right"><font color="red">*
	</font>开票金额：</td>
	<td align="left">
	<s:textfield name="invoiceAmount" onkeydown="valNum(event);" id="invoice" maxlength="15" /></td>
</tr>
<tr class="bg_table02">
	<td  align="right"><font color="red">*
	</font>发票/收据号：</td>
	<td class="bg_table02" align="left">
	<s:textfield	name="invoiceNo" id="invoiceNo" maxlength="15" /></td>
</tr>
<tr  class="bg_table02" >
	<td align="right"><font color="red">*
		</font>开票日期：</td>
	<td align="left">
	<div align="left">
	<input type="text" id="billDate" name="invoiceDate" value="<s:property value="invoiceDate"/>"
			size="7"> <img src="/yx/commons/images/calendar.gif"
		onClick="javascript:ShowCalendar('billDate')" align=absMiddle
	alt="" /></div>
	</td>
</tr>
</s:iterator>
<tr>
	<td class="bg_table03" align="center" colspan="2"><input
		type="button" class="button01" onclick="doSave()" value="修  改" /> <input
			type="reset" class="button01" name="reset" value="重  置" /></td>
	</tr>
</table>
</s:form>
</body>
<html>