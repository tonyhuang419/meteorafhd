<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>员工登陆密码成功</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body class="bg_table02">
	<table align="center">
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr align="center">
		<td>
				<br>
				<font color="red"><h2>修改密码成功...</h2></font>
				<br>
				<s:if test="changeState == 1">
					<h4><font color="red"><a href="/yx/personnelManager/selectPerQuery.action" >返回</a></font></h4>
				</s:if>
				<s:else>
					<h4><font color="red"><a href="/yx/mainView.action">返回首页</a></font></h4>
				</s:else>
			</td>
		</tr>
	</table>

</body>
</html>
