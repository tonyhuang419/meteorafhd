<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
	<head>
		<title>Error Page</title>
		<script src="<s:url value="/commons/scripts/prototype.js"/>" type="text/javascript"></script>
	</head>
	<%
	Throwable ex = null;
	if(exception!=null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Exception)request.getAttribute("javax.servlet.error.exception");
	%>
	<body>
		<div id="content">
				<div>
					<h3>
						系统运行异常:<br>
						<%
							if(ex!=null)
								out.println(ex.getMessage());
						%>
					</h3>
				</div>
				<div>
					<button onclick="history.back();">Back</button>
				</div>
				<div><a href="#" onclick="$('detail_error_msg').toggle();">Administrator click here to get the detail.</a></div>
				<div id="detail_error_msg" style="display: none">
					<% if (ex != null) { %>
					<pre>
						<% ex.printStackTrace(new java.io.PrintWriter(out)); %>
					</pre>
					<% } %>
				</div>
			</div>
	</body>
</html>