<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>

</head>
<body>
<s:form action="createInvoice" theme="simple" validate="true">
</s:form>
</body>
</html>
<script language="javascript">
	document.forms(0).action="/yx/invoice/createInvoice.action?method=findInvoiceApplications";
	document.forms(0).submit();
</script>
