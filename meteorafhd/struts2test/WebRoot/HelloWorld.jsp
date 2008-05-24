<%@ page  contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>Hello World</title>
</head>
<body>
    <h2><s:text name="HelloWorld"/></h2>
    <h2><s:property value="%{getText('HelloWorld')}"/></h2>
</body>
</html>