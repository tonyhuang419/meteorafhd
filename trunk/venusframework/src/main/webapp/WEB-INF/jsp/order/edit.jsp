<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html>
<head>
<title>test</title>
</head>
<body>
<s:form action="order" theme="simple" method="post" id="orderForm">  
	<s:if test="order.id != null">
		<s:hidden name="_method" value="put" /> 
		<s:hidden name="order.id"></s:hidden>
	</s:if>
	<s:textfield name="order.orderNum"></s:textfield>
	<s:submit value="submit"></s:submit>
</s:form>
<script>
<s:if test="order.id != null">
	var preAction = document.getElementById("orderForm").action;
	document.getElementById("orderForm").action = preAction+"/<s:property value="order.id"></s:property>";
</s:if>
</script>
</body>
</html>