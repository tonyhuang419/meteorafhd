<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>关联发票</title>
</head>
<body>
<script language="javascript"> 
	opener.relationTicket(<s:property value="id"/>);
	window.close();
</script>
</body>
</html>