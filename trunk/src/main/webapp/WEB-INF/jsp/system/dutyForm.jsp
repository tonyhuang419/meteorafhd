<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>addNewDepartment</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<h2>职责修改</h2>
<s:form name="editForm" action="dutyCode">
	<s:hidden name="method" value="updateDuty" />
    <s:hidden name="dc.is_active" value="1"></s:hidden>
	<table width="100%" border="1">

		<tr>
			<td class="bg_table02" colspan="2" align="center">职责代码修改页</td>
		</tr>

		<tr>
			<td class="bg_table02"><s:hidden name="dc.id"></s:hidden></td>
		</tr>

		
	
		<tr>
			<td class="bg_table02"><s:textfield label="职责名称"
				name="dc.name"></s:textfield></td>
		</tr>

	
	
		<tr>
			<td align="center" colspan="2"><input type="button" value="保存修改"
				onClick="check()" />&nbsp;&nbsp; <input type="button" value="返回"
				onclick="javascript:history.go(-1);" /></td>
		</tr>
	</table>
</s:form>


</body>
</html>
<script type="text/javascript">
function check(){
	if(!validate()){
	document.forms(0).submit();
	}
}
function validate()
	{
		var ev = new Validator();
        with(dutyCode){
            ev.test("notblank", "职责名不能为空", $('dutyCode_dc_name').value);
		   
			
		}
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
		return false;
	}
</script>