<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<html:html>

<body>
	<html:form action="/action2">
	<html:hidden property="page" value="2"/>
	<table>
		<tr>
			<th align="right" > Text2:</th>
			<TD>
				<html:text property="text2"></html:text>
			</TD>
		</tr>
	</table>
	<html:submit value="Submit"></html:submit>
	
	</html:form>
</body>


</html:html>