<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Welcome</title>
  </head>
  
  <body>
    welcome 欢迎<br>
    <hr/>
    <bean:write name="str"/>_
    <c:out value="${str}"/>
    <hr/>
     <bean:write name="s1" property="name"/>_
        <c:out value="${s1.name}"/>
     <hr/>
     <logic:iterate id="listx" name="list" indexId="listIndex" >
     	<c:out value="${listx.name}"/>_
     	<c:if test="${listx.age>13}">
     		“我是 c:if” <c:out value="${listx.age}"/>
     	</c:if>/
     	<logic:greaterEqual name="listx" property="age" value="10">
     		“我是 logic:greaterEqual” <c:out value="${listx.age}"/>
     	</logic:greaterEqual>
    	index：<c:out value="${listIndex}"/>
     	<br/>
     </logic:iterate>
     <hr/>
     <c:forEach var="listx" items="${list}" varStatus="listState" >
     	<c:out value="${listx.name}"/>_
     	<c:if test="${listx.age>13}">
     		<c:out value="${listx.age}"/>
     	</c:if><br/>
     	index:<c:out value="${listState.index}"/>/
     	count:<c:out value="${listState.count}"/>
     	size:<c:out value="${fn:length(list)}"/>
     </c:forEach>
     <hr/>
     <c:forEach var="obj" items="${s2.obj}" >
     	<c:out value="${obj.name}"/>
     </c:forEach>
     <hr/>
     <logic:iterate id="listobj" name="s2" property="obj" >
     	<c:out value="${listobj.name}"/>
     </logic:iterate>
     <hr>
     <logic:iterate id="listobj" name="s2" property="obj"  >
   	 	 <c:if test="${listobj.name eq 'bname' }">
     		<c:out value="${listobj.name}"/>|
     		<l
     	</c:if>
     </logic:iterate>
     <hr>
     username:<c:out value="${loginForm.username}"/><br/>
     password:<c:out value="${loginForm.password}"/><br/>
     
     <hr>
     <c:forEach var="result" items="${loginForm.listX}" >
    	<c:out value="${result}"/><br/>
	 </c:forEach> 
	   
	  <hr>
     <c:forEach var="result" items="${loginForm.listStu}" >
    	<c:out value="${result.name}"/><br/>
	 </c:forEach> 
  
  </body>
</html>
