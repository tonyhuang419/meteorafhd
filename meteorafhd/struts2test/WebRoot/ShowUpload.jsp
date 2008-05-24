
<%@page language  =  "java"   contentType  =  "text/html; charset=utf-8"   pageEncoding = "utf-8" %>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC"-//W3C//DTDXHTML1.0Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Struts2FileUpload</title>
</head>
<body>
		<div
			style="padding: 3px; border: solid 1px #cccccc; text-align: center">
			<img src='UploadImages/<s:property value  ="imageFileName"/>' />
			<br />
			<s:property value="caption" />
		</div>
	</body>
</html>