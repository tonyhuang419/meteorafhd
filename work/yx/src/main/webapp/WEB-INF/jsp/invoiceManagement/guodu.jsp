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
	<s:if test="applySid!=null" >
			document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=updateApplyBill&applyBillSid="+<s:property value="applySid"/>;
			document.forms(0).submit();
	</s:if>


	<s:else>
		document.forms(0).action="/yx/invoice/createInvoice.action?method=findInvoiceApplications";
		document.forms(0).submit();
	</s:else>
	
	
</script>
