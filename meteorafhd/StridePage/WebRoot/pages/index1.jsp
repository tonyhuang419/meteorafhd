<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<html:html>

<body>
	<html:form action="/action1">
	<html:hidden property="page" value="1"/>
	<table>
		<tr>
			<th align="right" > Text1:</th>
			<TD>
				<html:text property="text1"></html:text>
			</TD>
		</tr>
	</table>
	<html:submit value="Submit"></html:submit>
	
	</html:form>
</body>


</html:html>