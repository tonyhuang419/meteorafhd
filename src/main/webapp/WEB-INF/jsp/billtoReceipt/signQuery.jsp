<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>签收管理查询</title>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}

.STYLE1 {
	font-size: 16px;
	color: #000000;
}

.STYLE2 {
	font-size: 14px
}
-->
</style>
</head>
<body leftmargin="0">
<s:form action="signManageQuery" target="rightFrame" theme="simple">
   <s:hidden name="resetCondition" value="true"></s:hidden>
<div id="find" clss="sketchConManSide" align="left">
<div id="cname">
<table width="100%" class="bg_table02">
	<tr>
		<td colspan="2" align="right" class="bg_table01" height="3"><img
			src="./../images/temp.gif" width="1" height="1"></td>
	</tr>
	<tr>
		<td width="25%">
		<div align="right">客户名称：</div>
		</td>
		<td align="left">
		<div align="left">
		<input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly />
					
				
					<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');">
					<s:hidden name="CId" id="clientId"></s:hidden>
			</div>
		</td>
	</tr>
	<tr>
		<td>
		<div align="right">销售员：</div>
		</td>
		<td width="75%">
		<div align="left"><s:select name="expId" id="selectId"
			onchange="" list="listExp" listKey="id" listValue="name" emptyOption="true"
			required="true" headerKey="" headerValue="全部">
		</s:select></div>
		</td>
	</tr>
	<tr>
		<td>
		<div align="right">发票号：</div>
		</td>
		<td>
		<div align="left"><input name="billNumber" type="text"
			id="textfield2" size="10" maxlength="10"> <br>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div align="right">签收状态：</div>
		</td>
		<td>
		<div align="left"><select name="signState">
		    <option value="0">全部</option>			
			<option value="1">已签收</option>
			<option value="2">未签收</option>
		</select></div>
		</td>
	</tr>
	<tr class="bg_table04">
		<td colspan="2">
		<div align="center"><input type="submit" name="button"
			id="button2" value="查询" class="button01" 
			 /></div>
		</td>
	</tr>
</table>
</div>
<br>
</div>
</s:form>
</body>
</html>
<script language="javascript">
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
</script>