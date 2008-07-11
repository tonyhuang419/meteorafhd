<%@ page language="java"  pageEncoding="GB18030"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>test</title>
	</head>

	<body> 
	<s:form action="/stryts2test/testAction.action" theme="simple">
		<s:property value="s.name"/><br>
		<s:textfield name="s.name"></s:textfield>
		<br>
		<s:property value="s.age"/><br>
		<s:textfield name="s.age"></s:textfield><br>
		<s:submit value="Ìá½»"/>
		</s:form>
	</body>
</html>
<s:debug/>
