<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>外协信息修改</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>

<s:form  action="assistance">
	<s:hidden name="method" value="updateAssistance" />

	<table width="100%" border="1">

		<tr>
			<td class="bg_table02" colspan="2" align="center">外协信息修改</td>
		</tr>

		<tr>
			<td class="bg_table02"><s:hidden name="a.id"></s:hidden></td>
		</tr>

		
	
		<tr>
			<td class="bg_table02"><s:textfield label="供应商代码"
				name="a.suplierId"></s:textfield></td>
		</tr>

		<tr>
			<td class="bg_table02"><s:textfield label="开户银行"
				name="a.bank"></s:textfield></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:textfield label="银行帐号"
				name="a.acount"></s:textfield></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:textfield label="税号"
				name="a.taxNum"></s:textfield></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:textfield label="联系人"
				name="a.linkMan"></s:textfield></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:textfield label="电话"
				name="a.phone"></s:textfield></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:textfield label="手机"
				name="a.cellPhone"></s:textfield></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:textfield label="供应商类型"
				name="a.suplierSort"></s:textfield></td>
		</tr>
	
		<tr>
			<td align="center" colspan="2"><input type="submit" value="保存修改"/>&nbsp;&nbsp; <input type="button" value="返回"
				onclick="javascript:history.go(-1);" /></td>
		</tr>
	</table>
</s:form>


</body>
</html>

