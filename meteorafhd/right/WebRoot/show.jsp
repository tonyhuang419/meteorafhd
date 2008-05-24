<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>

	<head>
		<title>校验成功</title>
	</head>

	<body>
		<H3>
			校验成功
		</H3>
		用户名：
		<s:property value="name" />
		<br>
		密码：
		<s:property value="pwd" />
		<br>
	</body>

</html>