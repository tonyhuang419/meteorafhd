
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
 
  <body>
		<FORM name="form" method="POST" action="servlet/Auto_Servlet">
			<p><br></p><p>&nbsp; 
				<input type="text" name="name" value="UserName"> 
			</p><P>
				 &nbsp;
				<INPUT type="Submit" name="submit1" value="Submit" />
			</P>

		</FORM>
		<br>
		<%
			int forwardsNum = request.getIntHeader("Max-Forwards");
			out.println("___________"+forwardsNum+"___________");

			Date date = new Date();
			out.write(date.toString());
			out.println("<br>");
			for(int i=1;i<=9;i++){
				for(int j=1;j<=i;j++){
					out.println(i+"*"+j+"="+i*j+"	");
				}
				out.println("<br>");
			}
			//out.flush();
			
		%>
	
	 </body>
</html>
	 <%
  response.sendRedirect("login.html");
   %>
