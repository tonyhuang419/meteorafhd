
<%@ page language="java" import="java.util.*,java.io.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<jsp:useBean id = "rf" class = "upload.ReadFile" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
    <base href="<%=basePath%>">

    <title>upload</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    
	    <script language="javascript">
	    function getText(){
	    	var text = document.upload.path.value;
	    	alert(text);
	    }
	    function insert(){
	    	alert("1111");
	    	var loc = document.getElementById("xx");
	        loc.innerHTML = "xx";
		}
		</script>
	</head>
	
	<body>
	<% 
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");
	
	String fileName = request.getParameter("aaaa");
	out.println(fileName);
	out.println("xxx");
	%>
    	<div id="xx"></div>

   	 <form method="post" name="upload" id="upload">
        <p><input type="text" name="path"  size="30" /></p>
        <p>&#160;<input type="Button" value="UpLoad" name="submit" onclick="getText()" /></p>
   	 </form>
	</body>
</html>