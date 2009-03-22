<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>关闭</title>
</head>
<body>
<s:form action="receivableStatisticsQuery">
<s:hidden name="method" value="downLoadExcel"></s:hidden>
</s:form>
</body>
<script type="text/javascript">
function downAndClose(){
	document.forms(0).submit()
	window.close();
}
downAndClose();
</script>
</html>

