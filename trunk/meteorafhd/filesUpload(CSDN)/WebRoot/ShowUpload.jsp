<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
	<title>上传成功页面</title>
</head>
<BODY>
 <div style="padding:3px;border:solid 1px #cccccc;text-align:center">
  <img src="UpLoadImages/${imageFileName}" border=1 />
  <br/>
  <s:property value ="caption"/> 
 </div>
</BODY> 
</html>

