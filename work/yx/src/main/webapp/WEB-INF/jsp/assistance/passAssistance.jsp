<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>提交确认通过</title>
</head>
<body>
<script language="javascript"> 
	alert("已提交确认!");
	window.location.href("../assistance/assistanceMLeftQuery.action?method=query");
	window.close();
</script>
</body>
</html>