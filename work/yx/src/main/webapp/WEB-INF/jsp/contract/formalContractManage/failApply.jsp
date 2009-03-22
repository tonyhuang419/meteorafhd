<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<script language="javascript">
	<s:if test="succValidate == 2">
		alert("你选择的计划不符合合并要求,请重新选择!");	
	</s:if>
	<s:else>
		alert("申请失败");
	</s:else>
		window.opener=null;  
		window.open("","_self");   
		window.close(); 

	 
</script>
</head>