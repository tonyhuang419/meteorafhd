<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>申购采购过渡页面</title>
<SCRIPT language=JavaScript src="./js/public.js"></SCRIPT>

<script language="javascript">
	function refresh(){
	   // window.opener.location.reload();
	    window.opener.location.href("../purchase/purchaseManagerSearch.action");
	    window.close();
	}
	
</script>
</head>
<body onLoad="javascript:refresh();">

<s:if test="opState == 0">
	<script language="javascript"> 
		alert("修改成功");
	</script>
</s:if>
<s:elseif test="opState == 1">
	<script language="javascript"> 
		alert("提交成功");
	</script>
</s:elseif>


<s:form action="" theme="simple">
	
</s:form>
</body>
</html>

