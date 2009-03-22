<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>外协合同确认提交验证</title>
</head>
<body>
<script language="javascript"> 
<s:if test = "#notSure == true">
		var alertMsg = "<s:property value="#assistanceContractName"/>:";
		<s:if test = "#returnCode == 1">
			alertMsg += "无供应商代码！";
		</s:if>
		<s:if test = "#returnCode == 2">
			alertMsg += "还没有添加阶段！";
		</s:if>
		<s:if test = "#returnCode == 3">
			alertMsg += "阶段名称和该项目下的阶段不匹配！";
		</s:if>
		<s:if test = "#returnCode == 4">
			alertMsg += "阶段金额不等于外协合同金额！";
		</s:if>
		<s:if test = "#returnCode == 5">
			alertMsg += "外协合同的金额总额大于项目金额！";
		</s:if>
	alert(alertMsg);
</s:if>	
	location.href("../assistance/assistanceMLeftQuery.action?method=query");
</script>
</body>
</html>

