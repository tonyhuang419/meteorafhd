<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for GoForm form</title>
	</head>
	<body>
		<html:form action="/go">
			id : <html:text property="id"/><html:errors property="id"/><br/>		
			<html:submit/>
		</html:form>
	</body>
</html>

