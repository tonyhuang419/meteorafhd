<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html>
<head>
<style type="text/css">
<!--  

  -->   
</style>
<title>menu</title>
</head>
<body>
<a href="#">修改个人信息</a><br/>

<script type="text/javascript">
function doLogin(){
	var formX = $("loginForm");
	$("formMethod").value = "validateUser";
	formX.submit();
}

</script>

</body>
</html>
