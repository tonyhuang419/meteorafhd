<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

 <script src="prototype-1.4.0.js" type="text/javascript">
</script>
<script language="JavaScript">

function gotClick()
	{
	//请求的地址
		var url = 'json.action';
	
		var params = Form.serialize('json');
		//创建Ajax.Request对象，对应于发送请求
		var myAjax = new Ajax.Request(
		url,
		{
			//请求方式：POST
			method:'post',
			//请求参数
			parameters:params,
			//指定回调函数
			onComplete: processResponse,
			//是否异步发送请求
			asynchronous:true
		});
	}
    function processResponse(request)
	{
		$("show").innerHTML = request.responseText;
	}	

</script>

<html>
	<head>
		<title>json.jsp</title>
	</head>

	<body>
		<s:form  method="post">
			<s:textfield name="name" label="name" />&nbsp; 
			</br>
			<s:textfield name="age" label="age" />
			</br>
			<s:textfield name="sex" label="sex" />
			</br>
			<input type="button" value="submit" onClick="gotClick();"/>
		</s:form>
		<div id="show">
		</div>
	</body>
</html>
