<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for GoForm form</title>
	</head>
	<body>
		<bean:write name="goForm" property="id"/> </br>
		<bean:write name="goForm" property="name"/>
	</body>
</html>

