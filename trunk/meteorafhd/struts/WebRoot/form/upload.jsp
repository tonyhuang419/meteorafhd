<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>JSP for UploadForm form</title>
	</head>
	<body>
		<html:form action="/upload" method="POST" enctype="multipart/form-data">
			<html:file property="file"/></br></br>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

