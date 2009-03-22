<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>错误提示</title>
<script type="text/javascript">
<s:if test = "#notSure == true">
		var alertMsg = "<s:property value="#payInfoCode"/>:";
		<s:if test = "#returnCode == 1">
		alertMsg += "接收号为空！";
		</s:if>
		<s:if test = "#returnCode == 2">
		alertMsg += "任务号为空！";
		</s:if>
		<s:if test = "#returnCode == 3">
		alertMsg += "阶段总金额不等于付款申请金额！";
		</s:if>
		<s:if test = "#returnCode == 4">
		alertMsg += "最后一笔付款不能是预付款！";
		</s:if>
		<s:if test = "#returnCode == 5">
		alertMsg += "最后一笔付款申请必须关联预付款！";
		</s:if>
	alert(alertMsg);
	</s:if>	
window.location = "yx/assistance/apply.action?method=enterUpdate&ids=<s:property value="payInfoId"/>";
</script>
</head>

<body>

</body>
</html>

