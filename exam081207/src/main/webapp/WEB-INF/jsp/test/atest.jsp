<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="pages" uri="/WEB-INF/tlds/page.tld"%>

<html> 
<body>
    <br> <%-- 
        共 <s:property value="total"/> 页 第 <s:property value="pageNo"/> 页<br><br>
        --%>
    <pages:pages beanName="info"  styleClass="xx" baseAction="aTest.action?method=ff"/> 
</body> 
</html> 