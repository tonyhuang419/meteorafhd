<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>新建发票</title>
</head>
<body>
<script language="javascript"> 
	alert("已保存");
	if(window.opener.reflushParent != null ){
		window.opener.reflushParent();
	}
	window.close();
</script>
</body>
</html>