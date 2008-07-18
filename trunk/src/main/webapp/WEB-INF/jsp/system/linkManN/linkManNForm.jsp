<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>editGroupCode</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>

<s:form  action="linkManN">
	<s:hidden name="method" value="updateLM" />
    <s:hidden name="lmn.is_active" value="1"></s:hidden>
	<table width="100%" border="1">

		<tr>
			<td class="bg_table02" colspan="2" align="center">联系人性质修改</td>
		</tr>

		<tr>
			<td class="bg_table02"><s:hidden name="lmn.id"></s:hidden></td>
		</tr>

		
	
		<tr>
			<td class="bg_table02"><s:textfield label="联系人性质"
				name="lmn.name"></s:textfield></td>
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
        with(linkManN){
            ev.test("notblank", "联系人性质不能为空", $('linkManN_lmn_name').value);
		   
			
		}
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
		return false;
	}
</script>