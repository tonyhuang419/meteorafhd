<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html> 
<head>
<title>Ajax</title>
</head>
<body>
<br/>
<br/>
<br/>
<s:textfield  name="info" value="输入一些值" onchange="doGetFeedBack(this);doGetFeedBackArray(this)"></s:textfield>
<div id=feedBack></div>

<br/>
下面是数组DEMO
<div id="infoArray"></div>

<script type="text/javascript">
<!--
function doGetFeedBack(obj){
	var jsonRequest = new Request.JSON({async:false,url:'/exam081207/demo/ajax.action?method=doAjax&info='+obj.value,
		onComplete: function(jsonObj){
		 if(jsonObj!=null && jsonObj.jsonData !=null ){
			$("feedBack").innerHTML=jsonObj.jsonData;
		 }
	}}).get({randerNum:Math.random()});	
}

function doGetFeedBackArray(obj){
	var jsonRequest = new Request.JSON({async:false,url:'/exam081207/demo/ajax.action?method=doAjaxArray&info='+obj.value,
		onComplete: function(jsonObj){
		 if(jsonObj!=null && jsonObj.jsonData !=null ){
			var infoArray=""; 
			for(var i=0;i<jsonObj.jsonData.length;i++){
				infoArray = infoArray +"   "+ i+":" +jsonObj.jsonData[i];
			}
		 }
		 $("infoArray").innerHTML=infoArray;
	}}).get({randerNum:Math.random()});	
}

-->
</script>


</body> 

</html> 