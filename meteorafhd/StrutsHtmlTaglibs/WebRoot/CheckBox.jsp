<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<html:html>
<head>
<title>Checkboxes and Radio Buttons</title>
</head>
<body bgcolor="white">

<h3>Checkboxes and Radio Buttons</h3>

<p>This page provides examples of the following Struts HTML tags:<br>
<ul>
<li>&lt;html:checkbox&gt;</li>
<li>&lt;html:multibox&gt;</li>
<li>&lt;html:radio&gt;</li>
</ul>

<html:form action="CheckBox.do">

<table border="1" width="100%">

  <tr>
    <th align="left" width="20%">
    &lt;html:checkbox&gt;
    </th>
    <th align="left" width="80%">
    Struts code for example
    </th>
  </tr>

  <tr>
    <td align="left">
      Checkbox 1:
      <html:checkbox property="checkbox1"/>
    </td>
    <td align="left">
        &lt;html:checkbox property="checkbox1"&gt;
        - Normal checkbox
    </td>
  </tr>

  <tr>
    <td align="left">
      Checkbox 2:
      <html:checkbox property="checkbox2"/>
    </td>
    <td align="left">
        &lt;html:checkbox property="checkbox2" /&gt;
        - Strange behavior - form bean doesn't reset
    </td>
  </tr>

</table>

<table border="1" width="100%">

  <tr>
    <th align="left" width="20%">
      &lt;html:multibox&gt;
    </th>
    <th align="left" width="80%">
      Struts code for example
    </th>
  </tr>

  <tr>
    <td align="left" width="20%">
      Multibox 1:
      <html:multibox property="strArray" value="Value1"/>
    </td>
    <td align="left" width="80%">
      Multibox 1:
      &lt;html:multibox property="strArray" value="Value1"/&gt;
    </td>
  </tr>
  <tr>
    <td align="left" width="20%">
      Multibox 2:
      <html:multibox property="strArray">Value2</html:multibox>
    </td>
    <td align="left" width="80%">
      Multibox 2:
      &lt;html:multibox property="strArray"&gt;Value2&lt;/html:multibox&gt;
    </td>
  </tr>

</table>

<table border="1" width="100%">

  <tr>
    <th align="left" width="20%">
    &lt;html:radio&gt;
    </th>
    <th align="left" width="80%">
    Struts code for example
    </th>
  </tr>

  <tr>
    <td align="left" width="20%">
      <html:radio property="radioVal" value="Value1"/>
      Radio Button 1
    </td>
    <td align="left" width="80%">
      &lt;html:radio property="radioVal" value="Value1"/&gt;
      Radio Button 1
    </td>
  </tr>

  <tr>
    <td align="left" width="20%">
      <html:radio property="radioVal" value="Value2"/>
      Radio Button 2
    </td>
    <td align="left" width="80%">
      &lt;html:radio property="radioVal" value="Value2"/&gt;
      Radio Button 2
    </td>
  </tr>

</table>

<table border="0" width="100%">

  <tr>
    <td align="left" width="20%">&nbsp;</td>
    <td align="left">
      <html:submit>Submit</html:submit>
      <html:reset>Reset</html:reset>
      <html:cancel>Cancel</html:cancel>
    </td>
  </tr>

</table>

<logic:present name="CheckBoxForm" scope="session">
         <ul>
          <li>checkbox1: <bean:write name="CheckBoxForm" property="checkbox1" />
          <li>checkbox2: <bean:write name="CheckBoxForm" property="checkbox2" />
         
</logic:present >

</html:form>

</body>
</html:html>
