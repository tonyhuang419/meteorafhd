<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<html>
<head>
<head>
<style type="text/css">
<!--  
#title{
	width:800px;
	margin:20 10px 30px 10px;
}
#twoColLayout{
	width:800px;
	margin:0 atuo;
	padding:0 10px 10px 10px;
	background: #D7D493;
}
#twoColLayout #primaryContent{
	width:605px;
	float:left;
	margin:0 0 20px 195px;
}
#sideContent{
	width:200px;
	float:left;
	margin:0 0 20px -800px;
}
li{
	float:left;
	padding:0 5px 0px 5px;
}
  -->   
</style>
	<title><decorator:title/></title>
	<decorator:head/>
</head>
<body id="twoColLayout">
<div id="title">
<ul>
<li><a href="#">one</a></li>
<li><a href="#">two</a></li>
<li><a href="#">three</a></li>
<li><a href="#">four</a></li>
</ul>
</div>
<div id="primaryContent">
<hr/>
	<decorator:body/>
<hr/>
</div>
<s:if test="#session.CUSTOMER_SESSION.username!=null">
<div id="sideContent">
<a href="">修改个人信息</a><br/>
</div>
</s:if>
</body>
</html>