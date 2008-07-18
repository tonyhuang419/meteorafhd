<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>显示登陆用户对应的客户</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/cookieStore.js" type="text/javascript"></script>
</head>
<script language="javascript">
var storeItem = new TokenStore("searchClientTree");
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

<body leftmargin="0"  >
<s:form action="searchClientQuery"  >
   
<div id="list">
  <ul>
  <s:iterator value="clientConditionList" id="clientList" status="status">
    <li  class="xlipadding"><a href="javascript:void(0)" target="rightFrame" onClick="showItem(<s:property value="#clientList[0]"/>);"><s:property value="#clientList[1]"/>(<s:property value="#clientList[2]"/>)</a></li>
          <div id="item<s:property value="#clientList[0]"/>" align="left" style="display:none" >
     <ul>
     <s:iterator value="projectNameList" id="projectsList"> 
      <s:if test="#projectsList[2] == #clientList[0]">
	        <li  class="xlipadding"> <a href="/yx/sellbefore/showSellBefore.action?method=selectUpdate&id=<s:property value="#projectsList[0]"/>" target="rightFrame"><s:property value="#projectsList[1]"/></a></li>   
	    </s:if>
	    </s:iterator>
	    </ul>
    </div>
     </s:iterator>
  </ul>
</div>

</s:form>
<script language="javascript">
	storeItem.retrieve();
	for(var i=0;i<storeItem.tokens.length;i++){
		showItem(storeItem.tokens[i]);
	}
</script>
</body>
</html>