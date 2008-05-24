<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>

	<head>
		<title><s:text name="login" />
		</title>
	</head>

	<body>
		<H1>
			<s:text name="login" />
		</H1>
		<s:form action="login" method="post">
			<s:textfield name="name" key="name" />
			<s:password name="pwd" key="pwd" />
			<s:submit key="login" />
		</s:form>
		<s:debug />
	</body>
</html>