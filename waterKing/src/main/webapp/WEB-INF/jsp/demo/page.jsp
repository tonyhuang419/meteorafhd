<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html> 
<body>

	<s:iterator id="result" value="info.data">
		<s:property value="#result.title"/><hr/>
	</s:iterator>

     <%-- 
        共 <s:property value="total"/> 页 第 <s:property value="pageNo"/> 页<br><br>
        --%>
    <pages:pages beanName="info"  styleClass="xx" baseAction="demo/page.action?method=demoPage"/> 
    
</body> 
</html> 