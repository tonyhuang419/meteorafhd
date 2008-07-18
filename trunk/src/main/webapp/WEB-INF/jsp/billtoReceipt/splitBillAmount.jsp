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
	function checkAmount()
	{
		var totalAmount = document.getElementById('realAmount').value;
		var splitAmount = $('splitAmount').value;
		if(splitAmount > totalAmount)
		{
			alert(totalAmount - splitAmount );
			alert("拆分金额不能大于原始开票金额");
			return;
		}
		document.forms(0).submit();
	}
	
	
</script>
<body class="bg_table02">
<s:form action="splitBillAmountQuery" theme="simple">
<s:hidden name="method" value="splitAmount"/>
<table >
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
        <tr align="center">
		    <td colspan="2" class="bg_table01" height="1"><img src="../images/temp.gif" width="1" height="1"> <iframe name="errorsFrame" frameborder="0"
						framespacing="0" height="0" width="100%" scrolling="no"></iframe></td>
		</tr>
		<tr align="center">
			<s:iterator value="realAmountList" id="real">
			<input type="hidden" name="realPlanId" value="<s:property value="#real[0]"/>"/>
				<td class="bg_table02" align="right">总金额:</td>
				<td class="bg_table02" align="left">
					<s:property value="#real[1]"  />  <input type="hidden" name="realAmount" id="realAmount" value="<s:property value="#real[1]"  /> " />
					
				</td>
			</s:iterator>
		</tr>
		<tr align="center">
			<td class="bg_table02" align="right"><font color="red"> *</font>拆分金额:</td>
			<td class="bg_table02" align="left">
				<s:textfield name="splitAmount" maxLength="15" size="16"></s:textfield>				
			</td>
		</tr>
			<td class="bg_table03" colspan="2" align="center">
				<input type="button" name="button2" value="确  认" class="button01" onclick="checkAmount()"/>	
		
			<input type="button" name="close" value="关  闭" class="button01" onClick="window.close()" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>