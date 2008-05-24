<%@ page language="java" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>test</title>
		
	<script type="text/javascript" src='dwr/interface/Test.js'></script>
	<script type="text/javascript" src='dwr/engine.js'></script>
	<script type="text/javascript" src='dwr/util.js'></script>
	<script type="text/javascript" type="text/javascript">
		function hello(){
			var text = $('result').value
			Test.hello(text,22,callback);
		}	
		function callback(data){
			//result.value = data;
			DWRUtil.setValue('result', data);
		}
	</script>
  </head>
  
  <body><input id="result" type="text">
   <input type="button" name="btn" value="Test" onclick="hello()">
  </body>
</html>
