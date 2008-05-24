<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="logictaglibs.SomeBean" %>
<html:html>
<head>
<title>Logic Compare sample code</title>
</head>
<body bgcolor="white">
<h3>Logic Compare sample code</h3>
<p>This page provides examples of the following Struts LOGIC tags:<br>
<ul>
  <li>&lt;logic:equal&gt;</li>
  <li>&lt;logic:lessEqual&gt;</li>
  <li>&lt;logic:lessThan&gt;</li>
  <li>&lt;logic:greaterEqual&gt;</li>
  <li>&lt;logic:greaterThan&gt;</li>
  <li>&lt;logic:notEqual&gt;</li>
</ul>

 <%
         Cookie c = new Cookie("username", "Linda");
         c.setComment("A test cookie");
         c.setMaxAge(3600); //60 seconds times 60 minutes
         response.addCookie(c);
 %>

<logic:equal cookie="username" value="Linda" >
        UserName in Cookie is Linda <p>
</logic:equal>

<logic:equal header="Accept-Language" value="zh-cn" >
        Client¡¯s language is: zh-cn. <p>
</logic:equal>

<logic:greaterThan parameter="arg1" value="100" >
        The first request parameter is greater than 100 <p>
</logic:greaterThan >

<% 
        request.setAttribute("intBean",new Integer(100));
%>
<logic:equal name="intBean" value="100" >
        The value of intBean is "100".<p> 
</logic:equal >

<% 
        SomeBean bean=new SomeBean();
        bean.setName("Linda");
        request.setAttribute("someBean",bean);
%>
<logic:notEqual name="someBean" property="name" value="Tom" >
        The name of someBean is not "Tom" <p>
</logic:notEqual >

<% request.setAttribute("number","100"); %>
<logic:equal name="number" value="100.0" >
        "100" equals "100.0" <p>
</logic:equal >

<logic:lessThan name="number" value="100.0a" >
        "100" is less than "100.0a" <p>
</logic:lessThan >

</body>
</html:html>
