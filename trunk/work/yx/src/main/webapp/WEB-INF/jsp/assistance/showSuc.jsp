<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>Insert title here</title>

<s:if test="sign == 4 ">
	<script language="javascript"> 
		alert("确认成功");
	</script>
</s:if>

</head>
<body>
	<script type="text/javascript">
		opener.refresh();
		window.close()
	</script>
</body>
</html>