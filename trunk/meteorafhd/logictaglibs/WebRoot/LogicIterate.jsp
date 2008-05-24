<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Vector" %>
<html:html>
<head>
<title>Logic Iterate sample code</title>
</head>
<body bgcolor="white">

<h3>Logic Iterate sample code</h3>

<p>This page provides examples of the following Struts LOGIC tags:<br>

<ul>
<li>&lt;logic:iterate&gt;</li>
</ul>

<table border="1">
<tr>
<td>
<%--
Variables used on this page
--%>

<%
HashMap months = new HashMap();
months.put("Jan.", "January");
months.put("Feb.", "February");
months.put("Mar.", "March");
request.setAttribute("months", months);
%>
<%--
The following section shows iterate.
--%>
<logic:iterate id="element" indexId="ind" name="months">
  <bean:write name="ind"/>. 
  <bean:write name="element" property="key"/>:
  <bean:write name="element" property="value"/><BR>
</logic:iterate><P>

</td>

<td>
<%
HashMap h = new HashMap();
String vegetables[] = {"pepper", "cucumber"};
String fruits[] = {"apple","orange","banana","cherry","watermelon"};
String flowers[] = {"chrysanthemum","rose"};
String trees[]={"willow"};
h.put("Vegetables", vegetables);
h.put("Fruits", fruits);
h.put("Flowers", flowers);
h.put("Trees",trees);
request.setAttribute("catalog", h);
%>
<%--
The following section shows iterate.
--%>

<logic:iterate id="element" indexId="ind" name="catalog">
  <bean:write name="ind"/>. <bean:write name="element" property="key"/><BR>
  <logic:iterate id="elementValue" name="element" property="value" length="3" offset="1">
      -----<bean:write name="elementValue"/><BR>
  </logic:iterate>
</logic:iterate><P>

</td>
<td>
<%
 Vector animals=new Vector();
 animals.addElement("Dog");
 animals.addElement("Cat");
 animals.addElement("Bird");
 animals.addElement("Chick");
 request.setAttribute("Animals", animals);
%>
<logic:iterate id="element" name="Animals">
   <bean:write name="element"/><BR>
</logic:iterate><p>

</td>
<td>
<logic:iterate id="element" indexId="index" name="Animals" offset="1" length="2">
   <bean:write name="index"/>.<bean:write name="element"/><BR>
</logic:iterate><p>
</td>
<td>
<logic:iterate id="header" collection="<%= request.getHeaderNames() %>">
   <bean:write name="header"/><BR>
</logic:iterate>
</td>
</tr>
<table>
</body>
</html:html>
