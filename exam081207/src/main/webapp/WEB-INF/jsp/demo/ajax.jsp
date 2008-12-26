<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html> 
<body>
<br/>
<br/>
<br/>
<s:textfield  name="info" value="输入一些值" onchange="doGetFeedBack(this);"></s:textfield>
<div id=feddBack>

</div>

<script type="text/javascript">

function doGetFeedBack(obj){
	var jsonRequest = new Request.JSON({async:false,url:'/exam081207/demo/ajax.action?method=doAjax&info='+obj.value,
		 onComplete: finishAjax(info)	
	}).get({randerNum:Math.random()});	
}

function finishAjax(obj){
	$("feddBack").innerHTML="你输入的是："+obj.value;
}

</script>


<s:debug></s:debug>
</body> 

</html> 