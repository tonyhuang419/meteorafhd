<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>

<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>角色信息</title>
</head>
<body>

<table width="780" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td valign="top" align="center">

			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td class="txt_title01" height="3" align="center">角色表单</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img
						src="../../images/temp.gif" width="1" height="1"></td>
				</tr>
			</table>
	
			<s:form  action="dutyCode" theme="simple">
					<s:hidden name="method" value="saveDutyCode" />
				<table width="94%" border="0" cellspacing="1" cellpadding="1">
					<tr align="center">
						<td class="bg_table02" width="40%" align="right">职责名称：</td>
						<td class="bg_table02" align="left">
							<s:textfield name="dc.name" />
						</td>
					</tr>
					
					<tr align="center">
						<td align="right">
							<input type="submit" class="button01" value="保存" onClick="" />
						</td>
						<td align="left">
							<a href="javascript:history.go(-1)">返回</a>
						</td>
					</tr>
				</table>
			</s:form>
			<iframe name="errorsFrame" frameborder="0" height="0" width="100%" scrolling="no"></iframe>
		</td>
	</tr>
</table>
</body>
</html>


<script type="text/javascript">

function doSave()
{
	if(!validate())
	{
		document.all.roleForm.method.value="saveRole";
		document.forms(0).submit();
	}
}
function validate()
{
	var ev = new Validator();
		ev.test("notblank", "权限不能为空", $('').value);
	if (ev.size() > 0) 
	{
		ev.writeErrors(errorsFrame, "errorsFrame");
		return true;
	}
	return false;
}
function getAuthorityIds(str)
{
	$('authority_ids').value=str;
	
}
</script>
