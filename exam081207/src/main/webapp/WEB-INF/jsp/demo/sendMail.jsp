<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html> 
<body>
<br/>
<br/>
<br/>
<s:form action="sendMail" >
<s:hidden name="method" value="sendMail"></s:hidden>
	<s:textfield label="收件人" name="smm.to"></s:textfield>
	<s:textfield label="标题" name="smm.subject"></s:textfield>
	<s:textarea label="邮件内容" name="smm.text" cols="50"></s:textarea>
	<s:submit label="发送"></s:submit>
</s:form>
    <s:debug></s:debug>
</body> 
</html> 