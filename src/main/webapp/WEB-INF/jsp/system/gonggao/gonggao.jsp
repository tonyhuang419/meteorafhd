<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>公告发布</title>
<%@ include file="/commons/jsp/meta.jsp"%>
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>

<s:form action="gonggao">
	<s:hidden name="method" value="save" />
	<s:hidden name="gonggao.id"></s:hidden>
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td align="center"> 
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="3" align="left" >当前页面：基础管理->公告发布</td>
			</tr>
			<tr>
            	<td colspan="3" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
            	<td colspan="3" align="right" class="bg_table03">&nbsp;</td>
			    
			    <tr class="bg_table02">
			      <td width="33%"  align="right" class="bg_table02">公告内容：</td>
			      <td width="67%"  align="right" class="bg_table02"><div align="left">
			        <s:textarea name="gonggao.content"></s:textarea>   </textarea>
		          </div></td>
	      </tr>
			    <tr align="center">
			      <td  colspan="3" class="bg_table02">&nbsp;</td>
	      </tr>
			    <tr align="center">
                  <td colspan="3" class="bg_table03"><input type="button"
						name="SaveBtn" value="　发   布　" class="button02"
						onClick="doSave()"></td>
	      </tr>
		</table>

</table>
</s:form>
</body>
</html>

<script type="text/javascript">

function doSave()
{
	document.forms(0).submit();
}
function validate()
{
	var ev = new Validator();

	with(gonggao)
	{
		ev.test("notblank", "权限不能为空", $('authority_authority_authorityName').value);
		ev.test("notblank", "URL不能为空", $('authority_authority_url').value);
		ev.test("notblank", "operate不能为空", $('authority_authority_operate').value);
	}
	if (ev.size() >	0)
	{
		ev.writeErrors(errorsFrame, "errorsFrame"); 
		return true;
	}
	return false; 
}

</script>
