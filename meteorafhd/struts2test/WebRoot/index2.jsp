<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Struts2</title>
	</head>

	<body>
		<s:form action="Login" method="post">
			<table align="center">
				<tr>
					<td>
						name
					</td>
					<td>
						<input type="text" name="username" />
					</td>
				</tr>
				<tr>
					<td>
						pwd
					</td>
					<td>
						<input type="text" name="password" />
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="Submit" />
					</td>
					<td>
						<input type="reset" value="Reset" />
					</td>
				</tr>
			</table>
		</s:form>
	</body>
</html>
