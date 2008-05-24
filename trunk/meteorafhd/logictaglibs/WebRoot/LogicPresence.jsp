<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="org.apache.struts.Globals" %>
<%@ page import="org.apache.struts.action.ActionMessage" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<html:html>
<head>
<title>Logic Presence sample code</title>
</head>
<body bgcolor="white">

<h3>Logic Presence sample code</h3>

<p>This page provides examples of the following Struts LOGIC tags:<br>
<ul>
<li>&lt;logic:empty&gt;</li>
<li>&lt;logic:messagesPresent&gt;</li>
<li>&lt;logic:messagesNotPresent&gt;</li>
<li>&lt;logic:notEmpty&gt;</li>
<li>&lt;logic:notPresent&gt;</li>
<li>&lt;logic:present&gt;</li>
</ul>

<%--
Variables used on this page
--%>
<%
ActionErrors errors = new ActionErrors();
errors.add("totallylost", new ActionMessage("application.totally.lost"));
request.setAttribute(Globals.ERROR_KEY, errors);
request.setAttribute("myerrors", errors);
request.setAttribute("emptyString", "");
%>
<%--
The following section shows empty and notEmpty.
--%>

<logic:empty name="emptyString">
   The variable named emptyString is empty!<P>
</logic:empty>
<logic:notEmpty name="emptyString">
    The variable named emptyString is not empty!<P>
</logic:notEmpty>
<P>
<%--
The following section shows present and notPresent.
--%>
<logic:present name="noSuchBean" property="noSuchProperty">
   Both noSuchBean and noSuchProperty exist!
</logic:present>
<logic:notPresent name="noSuchBean" property="noSuchProperty">
   Either noSuchBean or noSuchProperty does not exist!
</logic:notPresent>
<P>

<logic:present name="emptyString" >
   There is a JavaBean named "emptyString". <p>
</logic:present>
<logic:notPresent name="emptyString" property="noSuchProperty">
   EmptyString doesn't have such a property named "noSuchProperty".
</logic:notPresent>
<P>
<logic:present header="user-agent">
   Yep, we got a user-agent header.
</logic:present>
<logic:notPresent header="user-agent">
   No, user-agent header does not exist.
</logic:notPresent>
<P>
<%--
The following section shows messagesPresent and messagesNotPresent.
--%>
<logic:messagesPresent>
   Yes, there are errors.
</logic:messagesPresent><P>
<logic:messagesPresent name="myerrors">
   Yes, there are errors in myerrors collection.
</logic:messagesPresent><P>
<logic:messagesNotPresent message="true" >
  There are no normal messages.
</logic:messagesNotPresent><P>

<logic:messagesNotPresent property="noSuchError">
   There is no error named "SuchError".
</logic:messagesNotPresent><P>
<logic:messagesPresent property="totallylost">
   There is an error named "totallylost".
</logic:messagesPresent>

</body>
</html:html>
