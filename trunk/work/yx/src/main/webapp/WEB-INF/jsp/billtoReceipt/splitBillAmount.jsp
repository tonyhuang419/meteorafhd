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
		var totalAmount = parseFloatNumber(document.getElementById('realAmount').value);
		var totalReceAmount = parseFloatNumber(document.getElementById('receAmount').value);
		var splitAmountLast = $('splitAmount').value;
		var splitAmount = parseFloatNumber($('splitAmount').value);
		
		var splitReceAmountL = $('splitReceAmount').value;
		var splitReceAmount = parseFloatNumber($('splitReceAmount').value);
		var msg=" 确定要拆分出吗?";
		if(splitAmountLast != "" && splitReceAmountL != ""){
			
				if(splitAmount == 0){
					alert("拆分开票金额不能为0.");
					return;
				}
				if(splitAmount == totalAmount){
					alert("不可以全额拆分!");
					return;
				}
				if(splitAmount > totalAmount)
				{
					alert("拆分开票金额不能大于原始开票金额.");
					return;
				}
				if(splitReceAmount > totalReceAmount)
				{
					alert("拆分到款金额不能大于原始倒到款金额.");
					return;
				}
				if(splitAmountLast != splitReceAmountL){
					alert("拆分的开票和到款金额需要相同才可拆分.");
					return;
				}
				if(window.confirm(msg)){	
					document.forms(0).submit();
				}
		}else{
			if(splitAmountLast == ""){
				alert("请输入需要拆分的开票金额数字!");	
			}
			if(splitReceAmountL == ""){
				alert("请输入需要拆分的到款金额数字!");	
			}
			return;			
		}
	}
	
	
</script>
<body class="bg_table02" leftmargin="0">
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
<s:if test="splitSelect == 0">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
        <tr align="center">
		    <td colspan="2" class="bg_table01" height="1"><img src="../images/temp.gif" width="1" height="1"> <iframe name="errorsFrame" frameborder="0"
						framespacing="0" height="0" width="100%" scrolling="no"></iframe></td>
		</tr>
	<s:iterator value="realAmountList" id="real">
		<tr align="center">
			<input type="hidden" name="realPlanId" value="<s:property value="realConBillproSid"/>"/>
				<td class="bg_table02" align="right">开票总金额:</td>
				<td class="bg_table02" align="left">
					<s:property value="realBillAmount"  />  <input type="hidden" name="realAmount" id="realAmount" value="<s:property value="realBillAmount"  /> " />
				</td>
		</tr>
		<tr align="center">
			<td class="bg_table02" align="right"><font color="red"> *</font>拆分开票金额:</td>
			<td class="bg_table02" align="left">
				<s:textfield name="splitAmount" maxLength="15" size="16" onkeyup="quanjiao(this)" onblur="formatInputNumber(this)"></s:textfield>				
			</td>
		</tr>
		<tr align="center">
				<td class="bg_table02" align="right">到款总金额:</td>
				<td class="bg_table02" align="left">
					<s:property value="realReceAmount"  /><input type="hidden" name="receAmount" id="realAmount" value="<s:property value="realReceAmount"  /> " />
				</td>
		</tr>
	</s:iterator>
		<tr align="center">
			<td class="bg_table02" align="right"><font color="red"> *</font>拆分到款金额:</td>
			<td class="bg_table02" align="left">
				<s:textfield name="splitReceAmount" maxLength="15" size="16" onkeyup="quanjiao(this)" onblur="formatInputNumber(this)"></s:textfield>				
			</td>
		</tr>
		
			<td class="bg_table03" colspan="2" align="center">
				<input type="button" name="button2" value="确  认" class="button01" onclick="checkAmount()"/>	
		
			<input type="button" name="close" value="关  闭" class="button01" onClick="window.close()" /></td>
		</tr>
	</table>
</s:if>
<s:if test="splitSelect == 1">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr class="bg_table02">
			<td align="center">
				<font color="red">该收据已经被关联不能被拆分!</font>
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="center">
				<input type="button" name="12" value=" 关  闭 " class="button01" onclick="window.close()">
			</td>
		</tr>
	</table>
</s:if>
<s:if test="splitSelect == 2">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr class="bg_table02">
			<td align="center">
				<font color="red">该发票计划已经关联过收据不能被拆分!</font>
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="center">
				<input type="button" name="12" value=" 关  闭 " class="button01" onclick="window.close()">
			</td>
		</tr>
	</table>
</s:if>
</s:form>
</body>
</html>