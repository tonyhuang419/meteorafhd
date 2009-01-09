<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Welcome</title>
  </head>
  
  <body>
    welcome »¶Ó­<br>
    <hr/>
    <bean:write name="str"/>_
    <c:out value="${str}"/>
    <hr/>
     <bean:write name="s1" property="name"/>_
        <c:out value="${s1.name}"/>
     <hr/>
     <logic:iterate id="listx" name="list" >
     	<c:out value="${listx.name}"/>_
     	<c:if test="${listx.age>13}">
     		<c:out value="${listx.age}"/>
     	</c:if><br/>
     </logic:iterate>
     <hr/>
     <c:forEach var="listx" items="${list}" >
     	<c:out value="${listx.name}"/>_
     	<c:if test="${listx.age>13}">
     		<c:out value="${listx.age}"/>
     	</c:if><br/>
     </c:forEach>
  </body>
</html>
