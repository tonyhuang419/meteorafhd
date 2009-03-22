<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
</head>

<body>
<s:hidden id="mainsid"  value="%{mainid}"></s:hidden>
</body>

</html>

<script language="javascript">
	var baseUrl = "/yx/contract/formalContractManage/formalContractManage.action?fromPage=draft&cmisysid="
	var conSid = document.getElementById("mainsid").value;
	location.href = baseUrl + conSid;
</script>