<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:html>
<head>
<title>Form Basics sample code</title>
</head>
<body bgcolor="white">

<h3>Form Basics sample code</h3>

<p>This page provides examples of the following Struts HTML tags:<br>
<ul>
<li>&lt;html:form&gt;</li>
<li>&lt;html:text&gt;</li>
<li>&lt;html:hidden&gt;</li>
<li>&lt;html:submit&gt;</li>
<li>&lt;html:cancel&gt;</li>
<li>&lt;html:reset&gt;</li>
</ul>

<html:form action="FormBasic.do">

<table border="1" width="100%">

  <tr>
    <th colspan="3" align="left">
      Sample &lt;html:hidden&gt; tags
    </th>
  </tr>

  <tr>
    <td align="left" >
      Enter value to become hidden: <html:text property="hiddenValue"/>
    </td>
    <td align="left" >
      &lt;html:hidden&gt; tags are rendered below
    </td>
  </tr>

  <tr>
    <td align="left" >
      &nbsp; &lt;html:hidden property="hiddenValue" /&gt;
    </td>
    <td align="left" >
      &nbsp; <html:hidden property="hiddenValue" />
    </td>
  </tr>

  <tr>
    <td align="left" >
      &nbsp; &lt;html:hidden property="hiddenValue" write="true" /&gt;
    </td>
    <td align="left" >
      &nbsp; <html:hidden property="hiddenValue" write="true" />
    </td>
  </tr>
</table>
<br>

<table border="1" width="100%">

  <tr>
    <th colspan="3" align="left">
      Page Cancel status
    </th>
  </tr>

  <tr>
    <td align="right">
      Which was pressed?:
    </td>
    <td align="left">
      <html:text property="status" disabled="true" />
    </td>
  </tr>
</table>

<table border="0" width="100%">

  <tr>
    <td align="right">
      Press Submit, Cancel or Reset:
    </td>
    <td align="left">
      <html:submit>Submit</html:submit>
      <html:cancel>Cancel</html:cancel>
      <html:reset>Reset</html:reset>
    </td>
  </tr>

</table>

</html:form>

</body>
</html:html>
