<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

 <script src="prototype-1.4.0.js" type="text/javascript">
</script>
<script language="JavaScript">

function gotClick()
	{
	//����ĵ�ַ
		var url = 'json.action';
	
		var params = Form.serialize('json');
		//����Ajax.Request���󣬶�Ӧ�ڷ�������
		var myAjax = new Ajax.Request(
		url,
		{
			//����ʽ��POST
			method:'post',
			//�������
			parameters:params,
			//ָ���ص�����
			onComplete: processResponse,
			//�Ƿ��첽��������
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
