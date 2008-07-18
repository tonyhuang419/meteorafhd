
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
var storeItem = new TokenStore("searchContractClientTree");
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
<style>
html { overflow-x:hidden;overflow-y:hidden;  }
</style>
<body leftmargin="0"  >

 <ul>
<s:iterator value="clients" id="client" status="status">   
<div id="list" >
    <li class="xlipadding">
    <a href="javascript:void(0)" onclick="showItem(<s:property value="#client.id"/>);"><s:property value="#client.name"/></a></li>
          <div id="item<s:property value="#client.id"/>" align="left" style="display:none" >
     <ul>
     <s:iterator value="contractNames" id="cname">
      <s:if test="#cname.conCustomer == #client.id">
	        <li class="xlipadding"> <a href="/yx/contract/contract.action?method=Modify&mainid=<s:property value="#cname.conMainInfoSid"/>&isModify=1" target="content"><s:property value="#cname.conName"/></a></li>	    
	    </s:if>
	    </s:iterator>
	    </ul>
    </div>
</div>
</s:iterator>
</ul>
 
<script language="javascript">
	storeItem.retrieve();
	for(var i=0;i<storeItem.tokens.length;i++){
		showItem(storeItem.tokens[i]);
	}
	

//查找所有div，判断其id有没有包含item字符串		
function getAllDiv(){
	var   divs = document.getElementsByTagName("div");  
	var temp;
  	for(var   i=0;   i<divs.length;   i++) {
  	 	temp = divs[i].id;
  	 	if( temp.indexOf("item")!= -1 ){
  	 		calcSum(temp,"li");
  	 	}
    }
}		
		
function calcSum(tagId,tag){
        var levelF =  document.getElementById(tagId);
		var tags = levelF.getElementsByTagName(tag);
      	superAddition(tagId,tags.length);  
}


function superAddition(id,content){
        document.getElementById(id).parentNode.firstChild.innerHTML
        = document.getElementById(id).parentNode.firstChild.innerHTML + "(" + content + ")";  
}

	getAllDiv();
	//calcSum("item","li");
</script>
</body>
</html>
