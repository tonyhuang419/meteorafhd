<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tangs" uri="/WEB-INF/tlds/page.tld"%>

<html> 
<body> 
    <br> 
        共 <s:property value="total"/> 页 第 <s:property value="pageNo"/> 页<br><br>
    <tangs:pages pageNo="pageNo" total="total" includes="test,test1,test2,test3" styleClass="page" theme="number"/> 
    
</body> 
</html> 