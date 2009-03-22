<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>JasperReport</title>
<script type="text/javascript">
function test()
{
	window.open("applyBillHTML.action?method=applyBillJaspser","","menubar=yes,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
}
</script>
</head>
<body>
	<center>开票申请</center>
<br><a href="applyBillHTML.action?method=applyBillJaspser">HTML</a>
<br><a href="applyBillPDF.action?method=applyBillJaspser">PDF</a>
<br><a href="applyBillXML.action?method=applyBillJaspser">XML</a>
<br><a href="applyBillCSV.action?method=applyBillJaspser">CSV</a>
<br><a href="applyBillXLS.action?method=applyBillJaspser">XLS</a>
<input type="button" value="HTML" onclick="test();"/>
<hr/>
<center>外协付款申请</center>
<br><a href="assistancePayForHTML.action?method=assistancePayFor" target="_blank">HTML</a>
<br><a href="assistancePayForPDF.action?method=assistancePayFor">PDF</a>
<br><a href="assistancePayForXML.action?method=assistancePayFor">XML</a>
<br><a href="assistancePayForCSV.action?method=assistancePayFor">CSV</a>
<br><a href="assistancePayForXLS.action?method=assistancePayFor">XLS</a>
<input type="button" value="HTML" onclick="test();"/>
</body>
</html>
