<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>关联外协合同</title>
</head>
<body>
<script language="javascript"> 
	alert("确认提交成功");
	if(window.opener != null){
		if(window.opener.reflushParent != null){
			window.opener.reflushParent();
		}
	}
	window.close();
</script>
</body>
</html>

