<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.Enumeration" %>
<html:html>
<head>
<title>Logic Match sample code</title>
</head>
<body bgcolor="white">

<h3>Logic Match sample code</h3>

<p>This page provides examples of the following Struts LOGIC tags:<br>
<ul>
<li>&lt;logic:match&gt;</li>
<li>&lt;logic:notMatch&gt;</li>
</ul>

<%--
Variables used on this page
--%>
<%
  request.setAttribute("authorName", "LindaSun");
%>
<%--
The following section shows match and notMatch.
--%>

<logic:match name="authorName" scope="request" value="Linda">
   <bean:write name="authorName"/> has the string 'Sun' in it.
</logic:match>
<logic:notMatch name="authorName" scope="request" value="Linda">
   <bean:write name="authorName"/> doesn't have the string 'Linda' in it.
</logic:notMatch>
<BR>
<logic:match name="authorName" scope="request" value="Linda" location="start">
   <bean:write name="authorName"/> starts with the string 'Linda'.
</logic:match>
<logic:notMatch name="authorName" scope="request" value="Linda" location="start">
   <bean:write name="authorName"/> doesn't start with the string 'Linda'.
</logic:notMatch>
<BR>
<logic:match header="user-agent" value="Windows">
   You're running Windows
</logic:match>
<logic:notMatch header="user-agent" value="Windows">
   You're not running Windows
</logic:notMatch>
<BR>
</body>
</html:html>
