<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="pages" uri="/WEB-INF/tlds/page.tld"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html> 
<body>

	<s:iterator id="result" value="info.data">
		<s:property value="#result.title"/><hr/>
	</s:iterator>

     <%-- 
        共 <s:property value="total"/> 页 第 <s:property value="pageNo"/> 页<br><br>
        --%>
    <pages:pages beanName="info"  styleClass="xx" baseAction="demo/demoPage.action?method=demoPage"/> 
    
    <s:debug></s:debug>
</body> 
</html> 