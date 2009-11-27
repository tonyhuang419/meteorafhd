<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html>
<head>
<title>test</title>
</head>
<body>
<s:form theme="simple" action="order" >
	<s:hidden name="method" value="saveOrder"></s:hidden>
	<s:textfield name="order.orderNum"></s:textfield>
	<s:submit value="submit"></s:submit>
</s:form>
</body>
</html>