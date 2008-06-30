<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Struts2</title>
	</head>

	<body>
		<s:form action="Login" method="post" theme="css_xhtml">
			<table align="center">
				<tr>
					<td>
						<s:text name="name" />
					</td>
					<td>
						<s:textfield name="username" />
					</td>
				</tr>

				<tr>
					<td>
						<s:text name="pwd" />
					</td>
					<td>
						<s:password name="password" />
					</td>
				</tr>
				<tr>
					<td>
						<s:submit value="µÇÂ¼" />
					</td>
					<td>
						<s:reset value="ÖØÖÃ" />
					</td>
				</tr>
			</table>

		</s:form>

	</body>
	<s:debug/>
</html>
