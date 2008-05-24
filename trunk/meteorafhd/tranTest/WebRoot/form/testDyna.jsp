<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for DynaValidatorForm form</title>
	</head>
	<body>
		<html:form action="/testDyna">
			info : <html:text property="info"/><html:errors property="info"/><br/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

