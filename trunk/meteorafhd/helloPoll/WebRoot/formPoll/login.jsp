<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>

	<head>
		<title><s:text name="login" />
		</title>
	</head>

	<body>
		<table width="60%" height="60%" align="center">
			<tr>
				<td>
					<div align="center">
						<s:form action="nameLogin" method="post">
							<s:textfield name="name" key="name" />
							<s:submit key="login" align="center"/>
						</s:form>
					</div>
				</td>
			</tr>
		</table>
	</body>
	<%--<s:debug/>--%>
</html>