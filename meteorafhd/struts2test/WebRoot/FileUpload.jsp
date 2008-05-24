<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC"-//W3C//DTDXHTML1.0Transitional//EN""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Struts2FileUpload</title>
</head>
<body>
	<br />
	<s:form action="fileUpload" method="POST" enctype="multipart/form-data">
		<s:file name ="myFile" label="ImageFile" />
		<s:textfield name ="caption" label="Caption" />
		<s:submit />
		</s:form>
</body>
</html>