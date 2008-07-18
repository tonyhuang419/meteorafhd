<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>外协付款确认</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<script language="javascript">
	function check(){
		var inputCount = <s:property value="list.size()" />;
		for(i=0;i<inputCount;i++){
		var number = document.getElementById("tcMoney"+i).value;
			if(number.charAt(i)<'0'||number.charAt(i)>'9'){
				alert("对不起,您输入的不是数字!");
				return;
			}
		}
		document.forms(0).submit;
	}
	function aaa(){
		document.forms(0).method.value = "doTicket";
		document.forms(0).action="../assistance/apply.action?ids=<s:property value="#parameters.ids"/>&pid=<s:property value="#parameters.pid"/>";
		document.forms(0).submit();
	}
</script>

</head>
<body>
<s:form action="apply" theme="simple">
	<s:hidden name="method" value="doTicket"></s:hidden>
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<tr>
	<td align="center"> 
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="4" align="left" >当前页面：外协管理->关联发票</td>
			</tr>
						<tr>
            	<td colspan="5" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
	  </table>
	  <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="checkInfo">
<tr align="center">
	      <td width="5%" class="bg_table01">选择</td>
          <td width="14%" class="bg_table01">供应商名称</td>
          <td width="8%" class="bg_table01">发票号</td>
          <td width="9%" class="bg_table01">发票类型</td>
          <td width="9%" class="bg_table01">发票金额</td>
          <td width="9%" class="bg_table01">开票时间</td>
          <td width="22%" class="bg_table01">备注</td>
          <td width="12%" class="bg_table01">发票余额</td>
          <td width="12%" class="bg_table01">支付金额</td>
</tr>
<s:iterator id="result" value="list" status="status">
	    <tr align="center">
          <td><input type="checkbox" name="tc[<s:property value="#status.index" />].id"
							value="<s:property value="#result[0].id"/>" /></td>
						<td><s:property value="#result[1]" /></td>
						<td><s:property value="#result[0].num" /></td>
						<td><s:property value="#result[0].type" /></td>
						<td><s:property value="#result[0].money" /></td>
						<td><s:property value="#result[0].ticket_Time" /></td>
						<td><s:property value="#result[0].remark" /></td>
						<td><s:property value="" /></td>
						<td><s:textfield name="tc[%{#status.index}].money" id="tcMoney%{#status.index}" size="10"></s:textfield></td>
						<td><s:hidden name="tc[%{#status.index}].ticketId" value="%{#result[0].id}"></s:hidden></td>
	    </tr>
</s:iterator>	 
<s:hidden name="ticketId" value="#result[0].id"></s:hidden>   
	  </table>
<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>    </td>
  </tr>
  <tr>
  <td class="bg_table02"> <div align="center">
    <input type="button" name="SearchBtn" value="确认关联" class="button01" onClick="javascript:aaa();">　
    <input type="button" name="SearchBtn" value="关    闭" class="button01" onClick="javascript:window.close();">　
  </div></td>
  </tr>
</table>
</s:form>
<p>&nbsp;</p>
</body>
</html>
