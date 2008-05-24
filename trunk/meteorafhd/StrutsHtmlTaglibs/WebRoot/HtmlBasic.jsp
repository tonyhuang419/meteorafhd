<%@ page language="java"%>
<%@ page import="htmltaglibs.beans.CustomerBean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:html lang="true">
<head>
<title>Base HTML Tags</title>
<html:base/>
</head>
<body bgcolor="white">

<h3>Sample code for basic Struts html tags</h3>

<p>This page provides examples of the following Struts HTML tags:<br>
<ul>
<li>&lt;html:html&gt;</li>
<li>&lt;html:base&gt;</li>
<li>&lt;html:link&gt;</li>
<li>&lt;html:rewrite&gt;</li>
<li>&lt;html:img&gt;</li>
</ul>
<table border="1" width="100%">
  <tr>
    <th colspan="3" align="left">
      Sample &lt;html:link&gt; tags
    </th>
  </tr>

  <%--
  The following section contains three <html:link> tags. Each
  demonstrates a different way of creating an anchor tag (<a href=...>).
  --%>
  <tr>
    <td align="left">
      <%-- Create link from a Global Forward in the struts-config.xml --%>      
      <html:link forward="index">
        Link to Global ActionForward
      </html:link>
    </td>
    <td align="left">
      <%-- Create link by specifying a full URL --%>
      <html:link href="http://jakarta.apache.org/struts/index.html">
        Generate an "href" directly
      </html:link>
    </td>
    <td align="left">
      <%-- Create the link as a relative link from this page --%>
      <html:link page="/HtmlBasic.do">
        A relative link from this page
      </html:link>
    </td>
  </tr>

  <%--
  The <html:link> and <html:rewrite> tags a very similar. The only difference
  is that <html:rewrite> creates the URI without prepending the
  "http://hostname:port/" part.
  --%>
  <tr>
    <%-- Create link and hard-code request parameters --%>
    <td colspan="1" align="left">
      <html:link page="/HtmlBasic.do?prop1=abc&amp;prop2=123">
        Hard-code the url parameters
      </html:link>
    </td>
    <%-- Create the same rewrite string for the above link. --%>
    <td colspan="2" align="left">
      <b>rewrite: </b>
      <html:rewrite page="/HtmlBasic.do?prop1=abc&amp;prop2=123" />
    </td>
  </tr>

<%
  /*

   * Create a String object to store as a bean in
   * the page context and embed in this link
   */
  String stringBean = "Value to Pass on URL";
  pageContext.setAttribute("stringBean", stringBean);
%>
<jsp:useBean id="customerBean" scope="page" class="htmltaglibs.beans.CustomerBean" />
<jsp:setProperty name="customerBean" property="name" value="weiqin" />

  <tr>
    <%-- Create link with request parameters from a bean --%>
    <td colspan="1" align="left">
      <%-- For this version of the <html:link> tag:                    --%>
      <%--   paramID = the name of the url parameter                   --%>
      <%--   paraName = the "attribute" for the bean holding the value --%>
      <html:link page="/HtmlBasic.do"
              paramId="urlParamName" paramName="stringBean">
      URL encode a parameter based on a string bean value
      </html:link>
      <br>
      <html:link page="/HtmlBasic.do"
              paramId="urlParamName" paramName="customerBean" paramProperty="name">
      URL encode a parameter based on a customer bean value
      </html:link>

    </td>
    <%-- Create the same rewrite string for the above link. --%>
    <td colspan="2" align="left">
      <b>rewrite: </b>
      <html:rewrite page="/HtmlBasic.do"
              paramId="urlParamName" paramName="stringBean" />
      <br>
      <b>rewrite: </b>
       <html:rewrite page="/HtmlBasic.do"
              paramId="urlParamName" paramName="customerBean" paramProperty="name"/>

    </td>
  </tr>

<%
   /*
    * Store values in a Map (HashMap in this case)
    * and construct the URL based on the Map
    */
  java.util.HashMap myMap = new java.util.HashMap();
  myMap.put("myString", new String("myStringValue") );
  myMap.put("myArray", new String[] { "str1", "str2", "str3" });
  pageContext.setAttribute("map", myMap);
%>
 <tr>
    <%-- Create a link with request parameters from a Map --%>
    <td colspan="1" align="left">
      <%-- For this version of the <html:link> tag: --%>
      <%--   map = a map with name/value pairs to pass on the url --%>
      <html:link page="/HtmlBasic.do" name="map">
      URL encode a parameter based on values in a Map
      </html:link>
    </td>
    <%-- Create the same rewrite string for the above link. --%>
    <td colspan="2" align="left">
      <b>rewrite: </b>
      <html:rewrite page="/HtmlBasic.do" name="map"/>
    </td>
  </tr>

</table>

<table border="1" width="100%">
  <tr>
    <th colspan="3" align="left">
      Sample &lt;html:img&gt; tags
    </th>
  </tr>

  <tr>
    <%-- Create a default <img> tag --%>
    <td align="center">
      <html:img page="/struts-power.gif" />
    </td>

    <%-- Create an <img> tag with request parameters from a bean --%>
    <td align="center">
      <%-- Note "src" requires using full relative path --%>
      <html:img src="/StrutsHtmlTaglibs/struts-power.gif"
                     paramId="urlParamName" paramName="stringBean" />
    </td>

    <%-- Create an <img> tag with request parameters from a map --%>
    <td align="center">
      <html:img page="/struts-power.gif" name="map" />
    </td>
  </tr>
</table>

</html:html>
