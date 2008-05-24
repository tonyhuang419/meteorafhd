<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:html>
<head>
<title>Struts HTML Tags</title>
</head>
<body bgcolor="white">

<h1><b>Struts HTML tags</b></h1>

<table border="0" width="100%">
   
  <tr>
    <td align="left">
      <html:link forward="HtmlBasic">
        HtmlBasic
      </html:link>
    </td>
    <td align="left">
      Basic,non-form Struts Html Tags 
    </td>
  </tr>

  <tr>
    <td align="left">
     </td>
    <td align="left">
       &lt;html:html&gt,&lt;html:base&gt,&lt;html:link&gt,&lt;html:rewrite&gt,&lt;html:img&gt
    </td>
  </tr>
   
  <tr>
    <td align="left">
      <html:link forward="FormBasic">
        FormBasic
      </html:link>
    </td>
    <td align="left">
      Basic Form Processing 
    </td>
  </tr>

  <tr>
    <td align="left">
     </td>
    <td align="left">
   &lt;html:form&gt,&lt;html:text&gt,&lt;html:hidden&gt,&lt;html:submit&gt,&lt;html:cancel&gt,&lt;html:reset&gt
    </td>
  </tr>

  <tr>
    <td align="left">
      <html:link forward="CheckBox">
        CheckBox
      </html:link>
    </td>
    <td align="left">
      CheckBox and Radio Button
    </td>
  </tr>

 <tr>
    <td align="left">
     </td>
    <td align="left">
       &lt;html:checkbox&gt,&lt;html:multibox&gt,&lt;html:radio&gt
    </td>
  </tr>

 <tr>
    <td align="left">
      <html:link forward="HtmlSelect">
        HtmlSelect
      </html:link>
    </td>
    <td align="left">
      Drop downs and Select/Option lists
    </td>
  </tr>

 <tr>
    <td align="left">
     </td>
    <td align="left">
       &lt;html:select&gt,&lt;html:option&gt,&lt;html:options&gt,&lt;html:optionsCollection&gt
    </td>
  </tr>

  <tr>
    <td align="left">
      <html:link forward="HtmlError">
        HtmlError
      </html:link>
    </td>
    <td align="left">
      Input validation and &lt;html:errors&gt
    </td>
  </tr>

 <tr>
    <td align="left">
     </td>
    <td align="left">
       &lt;html:errors&gt
    </td>
  </tr>


<tr>
    <td align="left">
      <html:link forward="HtmlFile">
        HtmlFile
      </html:link>
    </td>
    <td align="left">
      uploading a file using &lt;html:file&gt
    </td>
  </tr>

 <tr>
    <td align="left">
     </td>
    <td align="left">
       &lt;html:file&gt
    </td>
  </tr>
</table>
</html:html>
