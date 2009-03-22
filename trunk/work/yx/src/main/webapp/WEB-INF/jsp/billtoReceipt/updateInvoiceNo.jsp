<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>修改发票号</title>
</head>
<script type="text/javascript">
	function doSave(){
		if(!validate()){
			document.forms(0).submit();
		}
	}
	
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(billReceipt){
		       ev2.test("notblank","发票号码不能为空",$('invoiceNo').value);
		       ev2.test("notblank","开票日期不能为空",$('invoiceDate').value);
		       ev2.test("dateYX","开票日期日期格式不正确",$('invoiceDate').value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	
</script>
<s:if test="errorInfo == 1">
	<script type="text/javascript">
		opener.closeO1();
	</script>
</s:if>
<body>
<s:form action="billReceipt" theme="simple">
<s:hidden name="method" value="updateInvoiceNo" />
 <div align="left" style="color: #000000">
	<p>当前页面：开票管理 -> 修改发票号</p>
	<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
</div>
<div><font color="red"><s:property value="errorInfo" /></font></div>
<s:hidden name="billId"/>
<s:hidden name="amountId"/>
<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" id="billApplyList"  bordercolor="#808080" style="border-collapse: collapse;">
<tr class="bg_table02">
	<td align="right">原发票号：</td>
	<td align="left"><s:property value="ii.invoiceNo"/></td>
</tr>
<tr class="bg_table02">
	<td align="right">原开票日期：</td>
	<td align="left"><s:property value="ii.invoiceDate"/></td>
</tr>
<tr class="bg_table02">
	<td align="right">发票号修改为：</td>
	<td align="left"><s:textfield size="15" name="invoiceNo"/></td>
</tr>
<tr class="bg_table02">
	<td align="right">开票日期修改为：</td>
	<td align="left"><s:textfield size="15" name="invoiceDate" id=""/>
	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('invoiceDate')" align=absMiddle alt=""  />
	</td>
</tr>
<tr class="bg_table03">
	<td  align="center" colspan="2">
	<input type="button" class="button01" onclick="doSave()" value="修  改" />
	<input type="button" onclick="window.close()" class="button01" name="reset" value="关  闭" />
	</td>
</tr>
</table>		
</s:form>
</body>
<html>