<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<script language="javascript">
	<s:if test="alertInfo!=null">
		alert('<s:property value="alertInfo"/>');
	</s:if>
	opener.refreshClient();
	window.close();
</script>
</head>
