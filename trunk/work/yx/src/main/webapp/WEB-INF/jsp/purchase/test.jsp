<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>addNewDepartment</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<form name=form1 action="searchClientQuery">选择:<input type="text" name="test_list" id="test_list"><input type="submit" value="确认" class="button01"/>
<div id="msg" style="border: 1px solid #efe456; width: 300" />
</form>
</body>
</body>
</html>
<script type="text/javascript">
ajaxtools.url='/yx/purchase/testQuery.action?method=getTestList';
ajaxtools.parameters="";
smanPromptList("test_list");
function getSelectValue(){

}
</script>