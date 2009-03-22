<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>申购采购管理关联合同</title>
</head>
<body>
<script language="javascript"> 
<s:if test="opState==7">
	alert("关联成功");
</s:if>
	opener.document.forms(0).submit();
	window.close();
</script>
</body>
</html>

