<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title><s:text name="login" /></title>
	</head>

	<body>
		<table width="60%" height="60%" align="center">
			<tr>
				<td>
					<div align="center">
						<s:form action="pwdLogin" method="post">
							<s:password name="pwd" key="pwd" />
							<s:submit key="login" align="center"/>
						</s:form>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>