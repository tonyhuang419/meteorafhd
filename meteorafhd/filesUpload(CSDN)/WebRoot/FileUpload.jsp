<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<title>文件上传页面</title>
	</head>
	<body>
		<s:fielderror></s:fielderror>
		<s:form action="fileUpload" method="POST" enctype="multipart/form-data">
			<s:file name="myFile" label="Image File"/>
			<s:textfield name="caption" label="Caption"/>
			<s:submit/>
		</s:form>
	</body>
</html>