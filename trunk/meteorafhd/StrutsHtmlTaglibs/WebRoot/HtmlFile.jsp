<%@ page language="java" %>
<%@ page import="org.apache.struts.action.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
<head>
<title>&lt;html:file&gt; sample code</title>
</head>
<body bgcolor="white">

<h1>&lt;html:file&gt; sample code</h1>
<!--
        The most important part is to declare your form's enctype
        to be "multipart/form-data", and to have an html:file
        element that maps to your ActionForm's FormFile property
-->
<html:form action="HtmlFile.do" enctype="multipart/form-data">

        Please select the file that you would like to upload:<br />
        <html:file property="file" /><br /><br />

        <html:submit />

</html:form>

<p>
<logic:notEmpty name="HtmlFileForm" property="fname" >
    The file just uploaded was:<p>
    <ul>
      <li>Name =<bean:write name="HtmlFileForm" property="fname" />
      <li>Size =<bean:write name="HtmlFileForm" property="size" />
    </ul>
</logic:notEmpty>

</body>
</html:html>
