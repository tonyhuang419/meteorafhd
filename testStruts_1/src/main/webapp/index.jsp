<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<html> 
	<head>
		<title>Login</title>
	</head>
	<body>
		<html:form action="/login">
			userName : <html:text property="username"/><html:errors property="username"/><br/>
<!--			<input type="text" name="username" /><br>  it's work too-->
			passWord : <html:text property="password"/><html:errors property="password"/><br/>
			<br/>
			<html:text property="listX"/><br/>
			<html:text property="listX"/><br/>
			<br/>
			<input type="text" name="listStu[0].name" /><br>
			<input type="text" name="listStu[3].name" /><br>
			<br/>
<!--			<input type="text" name="stu.name" />-->
<!--			<input type="text" name="stu.age"" />-->
			 <html:text property="stu.name"/>
			 <html:text property="stu.age"/>
			<html:submit/><html:cancel/>
		</html:form>
	</body>
</html>

