 
<%@ page language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<html> 
	<head>
		<title>JSP for loginForm form</title>
	</head>
	<body>
		<html:form action="/login" >
<!--		onsubmit="return validateLoginForm(this);">-->
		username : <html:text property="username"/><html:errors property="username"/><br/>
		password : <html:password property="password"/><html:errors property="password"/><br/>
			
			<html:submit/><html:cancel/>
		</html:form>
		
		<logic:notEmpty name="loginIsOk" scope="session">
			<bean:write name="loginIsOk" />
		</logic:notEmpty>
		
<!--		<html:javascript formName="loginForm" staticJavascript="true" />-->
	</body>
</html>
