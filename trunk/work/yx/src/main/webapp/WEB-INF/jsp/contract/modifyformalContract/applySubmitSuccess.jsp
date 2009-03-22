<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<script language="javascript">
<s:if test="#isSure == 1" >
alert("合同费用组成不等于合同金额！");
</s:if>
<s:if test="#isSure == 2" >
alert("合同阶段总金额不等于合同金额！");
</s:if>
<s:if test="#isSure == 3" >
alert("合同项目总金额不等于合同金额！");
</s:if>
<s:if test="#isSure == 4" >
alert("主负责部门在项目负责部门中未出现！");
</s:if>
<s:if test="#isSure == 5" >
alert("计划开票日期或者计划收款日期为空！");
</s:if>
<s:if test="#isSure == 6" >
alert("开票收款计划中收款总金额不等于合同含税总金额！");
</s:if>
<s:if test="#isSure == 7" >
alert("开票收款计划中开票总金额和合同金额不等！");
</s:if>
<s:if test="#isSure == 8" >
alert("开票收款计划未生成,请填写全合同信息！");
</s:if>
<s:if test="#isSure == 9" >
alert("开票收款计划中收款金额和费用含税金额不匹配！");
</s:if>



	if(opener.reflushPage!=null){
		opener.reflushPage();
	}
	window.close();
</script>
</head>