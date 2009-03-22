<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>editGroupCode</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="<s:url value="/commons/scripts/checkLength.js"/>"
	type="text/javascript"></script>
<link href="./css/style.css" rel="stylesheet" type="text/css">


</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>

<s:form action="businessType">
	<s:hidden name="method" value="updateBT" />
	<s:hidden name="bt.is_active" value="1"></s:hidden>
	<table width="100%" border="1">

		<tr>
			<td class="bg_table02" colspan="2" align="center">行业类型修改</td>
		</tr>

		<tr>
			<td class="bg_table02"><s:hidden name="bt.id"></s:hidden></td>
		</tr>



		<tr>
			<td class="bg_table02"><s:textfield label="行业类型" name="bt.name"></s:textfield></td>
		</tr>



		<tr align="center">
					<td colspan="4" class="bg_table03"><input type="button"
						name="SearchBtn" value="　保   存　" class="button01"
						onClick="check()">  <input type="button"
						name="return" value="　返   回  " class="button01"
						onclick="javascript:history.go(-1)"></td>
				</tr>
		
	</table>
</s:form>


</body>
</html>

<script language="javascript">
function check(){
	if(!validate()){
	document.forms(0).submit();
	}
}
function validate()
	{
		var ev = new Validator();
        with(businessType){
        ev.test("notblank", "行业类型名长度应为1－100个字符，请重新输入",checkLength(1,100,$('businessType_bt_name').value));
			
		}
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
		return false;
	}
</script>
