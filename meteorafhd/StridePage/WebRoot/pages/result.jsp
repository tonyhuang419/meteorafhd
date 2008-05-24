<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<html:html>

<body>
	Text1:<bean:write name="stride-form" property="text1"/><BR/>
	Text2:<bean:write name="stride-form" property="text2"/><BR/>

</body>

</html:html>