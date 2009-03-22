<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>项目跟踪状态修改</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>

<s:form  action="projectStateFollow">
	<s:hidden name="method" value="updateProjectStateFollow" />

	<table width="100%" border="1">

		<tr>
			<td class="bg_table02" colspan="2" align="center">项目跟踪状态修改</td>
		</tr>

		<tr>
			<td class="bg_table02"><s:hidden name="psf.type"></s:hidden></td>
		</tr>

		<tr>
			<td class="bg_table02"><s:textfield label="项目状态"
				name="zc.name"></s:textfield></td>
		</tr>

	
	
		<tr>
			<td align="center" colspan="2"><input type="submit" value="保存修改"/>&nbsp;&nbsp; <input type="button" value="返回"
				onclick="javascript:history.go(-1);" /></td>
		</tr>
	</table>
</s:form>


</body>
</html>

