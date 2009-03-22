<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<head>
<title>项目号导入</title>
</head>
<body>
<div style ="font-size:12;color:black;padding: 3px; border: solid 1px #cccccc; text-align: center" > 
	<s:property escape="false" value="loggerResult" /><br>
	<span style="color:green"><s:property escape="false" value="loggerSuccess" /><br><span>
	<span style="color:red"><s:property escape="false" value="loggerError" /><br><span>
	<a href="#" onclick="window.history.back();">返回</a>
 </div > 
</body>
<script type="text/javascript">
	if(opener != null){
		opener.document.forms(0).submit();
	}
</script>
</html>

