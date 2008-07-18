<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="utf-8" />
<meta name="robots" content="all" />
<meta name="author" content="http://www.iwcn.net,Jxdawei" />
<meta name="Copyright" content="Jxdawei CopyRight 2007" />
<meta name="keywords" content="СϷ,flash game,flashϷ" />
<meta name="description" content="flashСϷ" />
<title>显示文件下载列表</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/cookieStore.js" type="text/javascript"></script>
<style type="text/css">

</style>

</head>
<script language="javascript">
var storeItem = new TokenStore("downloadFileTree");
function showItem(num){
	var itemx = "item"+num;
	if(document.getElementById(itemx) == null){
		return ;
	}
	if(document.getElementById(itemx).style.display == "none"){
		document.getElementById(itemx).style.display="block";
		storeItem.addToken(num);
	}
	else{
		document.getElementById(itemx).style.display="none";
		storeItem.removeToken(num);
	}
}



</SCRIPT>
<body >
<s:form action="searchClientQuery" theme="simple">	
<s:iterator value="fileTypeList" id="fileType" status="status">  
<s:hidden value="%{#fileType[0]}"/>

<div id="list" style="width:100%">
<div id="item" class="sketchConManSide" align="left">
  <ul>
    <li class="xlipadding"><a href="javascript:void(0)" target="rightFrame" onClick="showItem(<s:property value="#fileType[0]"/>);"><s:property value="typeManageService.getYXTypeManage(1014,#fileType[0]).typeName"/>
    (<s:property value="#fileType[1]"/>)</a></li>  
    <div id="item<s:property value="#fileType[0]"/>" align="left" style="display:none" >
    <s:iterator value="clientList" id="clientL">
    	<s:if test="#fileType[0] == #clientL[0]">
	    <ul>
	        <li class="xlipadding"> <a href="../fileManager/fileDownSelect.action?resetCondition=true&filetype=<s:property value="#fileType[0]"/>&clientcode=<s:property value="#clientL[1]"/>" target="rightFrame"><s:property value="#clientL[2]"/>(<s:property value="#clientL[3]"/>)</a></li>
	    </ul>
	    </s:if>
    </s:iterator>
    </div>
  </ul>
</div>
</div></s:iterator>
</s:form>
<script language="javascript">
	storeItem.retrieve();
	for(var i=0;i<storeItem.tokens.length;i++){
		showItem(storeItem.tokens[i]);
	}
</script>
</body>
</html>