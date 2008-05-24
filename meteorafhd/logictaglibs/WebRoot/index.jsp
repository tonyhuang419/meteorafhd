<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:html>
<head>
<title>Struts Logic Tags</title>
</head>
<body bgcolor="white">

<h1><b>Struts Logic tags</b></h1>

<table border="0" width="100%">
   
  <tr>
    <td align="left">
      <html:link forward="LogicCompare">
        LogicCompare
      </html:link>
    </td>
    <td align="left">
      Comparing values
    </td>
  </tr>

  <tr>
    <td align="left">
     </td>
    <td align="left">
       &lt;logic:equal&gt,&lt;logic:lessEqual&gt,&lt;logic:lessThan&gt,&lt;logic:greaterEqual&gt,,&lt;logic:greaterThan&gt,,&lt;logic:notEqual&gt
    </td>
  </tr>
   
  <tr>
    <td align="left">
      <html:link forward="LogicMatch">
        LogicMatch
      </html:link>
    </td>
    <td align="left">
      Matching substrings
    </td>
  </tr>

  <tr>
    <td align="left">
     </td>
    <td align="left">
   &lt;logic:match&gt,&lt;logic:notMatch&gt
    </td>
  </tr>

  <tr>
    <td align="left">
      <html:link forward="LogicPresence">
        LogicPresence
      </html:link>
    </td>
    <td align="left">
      Checking for the presence or absence of values
    </td>
  </tr>

 <tr>
    <td align="left">
     </td>
    <td align="left">
        &lt;logic:empty&gt,&lt;logic:notEmpty&gt,&lt;logic:messagesPresent&gt,&lt;logic:messagesNotPresent&gt,&lt;logic:Present&gt,&lt;logic:notPresent&gt
    </td>
  </tr>

  <tr>
    <td align="left">
      <html:link forward="LogicIterate">
        LogicIterate
      </html:link>
    </td>
    <td align="left">
      Iteration
    </td>
  </tr>

 <tr>
    <td align="left">
     </td>
    <td align="left">
        &lt;logic:iterate&gt
    </td>
  </tr>

  <tr>
    <td align="left">
      <html:link forward="LogicForward">
        LogicForward
      </html:link>
    </td>
    <td align="left">
      Forward request
    </td>
  </tr>

  <tr>
    <td align="left">
     </td>
    <td align="left">
   &lt;logic:forward&gt
    </td>
  </tr>

<tr>
    <td align="left">
      <html:link forward="LogicRedirect">
        LogicRedirect
      </html:link>
    </td>
    <td align="left">
      Redirect request
    </td>
  </tr>

  <tr>
    <td align="left">
     </td>
    <td align="left">
   &lt;logic:redirect&gt
    </td>
  </tr>


</table>
</html:html>
