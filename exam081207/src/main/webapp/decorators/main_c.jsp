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
	margin:5 0 20px 195px;
}
#sideContent{
	width:200px;
	float:left;
	margin:5 0 20px -800px;
}
li{
	float:left;
	padding:5 5px 5px 5px;
	list-style: none;
}
  -->   
</style>
	<title><decorator:title/></title>
	<decorator:head/>
</head>
<body id="twoColLayout">
<s:if test="#session.CUSTOMER_SESSION.username!=null">
<div id="title">
<ul>
<li><a href="#">one</a></li>
<li><a href="#">two</a></li>
<li><a href="#">three</a></li>
<li><a href="#">four</a></li>
</ul>
</div>
</s:if>

<div id="primaryContent">
<hr/>
	<decorator:body/>
<hr/>
</div>

<s:if test="#session.CUSTOMER_SESSION.username!=null">
<div id="sideContent">
<a href="#" onclick='modCus(<s:property value="#session.CUSTOMER_SESSION.id"/>);'>修改个人信息</a><br/>
</div>

</s:if>

</body>
<script type="text/javascript">

function modCus(cid){
	location.href="../client/customerInfo.action?method=modClientInfo&cid="+cid;
}

</script>

</html>