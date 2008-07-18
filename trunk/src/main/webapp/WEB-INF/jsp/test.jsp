<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
下标不连续也能保存
<s:form name="editForm" action="test">
	<s:textfield label="name" name="tests[0].name"></s:textfield>
	<s:textfield label="code" name="tests[0].code"></s:textfield>
	<s:textfield label="types" name="tests[0].types[0].typeName"></s:textfield>
	<s:textfield label="name" name="tests[2].name"></s:textfield>
	<s:textfield label="code" name="tests[2].code"></s:textfield>
	<s:textfield label="types" name="tests[2].types[1].typeName"></s:textfield>
	<s:submit value="提交"></s:submit>
</s:form>
<input type="button" value="json异步或数据" onclick="getAjaxData()"/>
<script>
	function getAjaxData(){
		var jsonRequest = new Request.JSON({url: "<s:url action="testJson"></s:url>", onComplete: function(jsonObj){
		     alert("length:"+jsonObj.testArray.length);
		     alert("name:" + jsonObj.testArray[0].name + ",code:"+jsonObj.testArray[0].code);
		}}).get({param:'dddd',randerNum:Math.random()});
	}
</script>