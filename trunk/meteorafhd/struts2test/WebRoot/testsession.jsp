<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
  <head>
    <title>test session</title>


  </head>
  
  <body>
  Name: <s:textfield  name="name" /><br><br>
  Age:&nbsp;      <s:textfield  name="age"/>
  <br><br><a href = "#" onclick="javascript:openWin()">click me</a>
  </body>
  
  <script language="javascript">
	function openWin(){
		window.open("testSessionOpen.jsp",null,"height=150,width=200");
	}
	function fresh(){		
       window.location.reload();
	}
	</script>
</script>
</html>
