<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>

<html>
	<head>
		<title>正式合同管理索引</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/cookieStore.js" type="text/javascript"></script>

<script language="javascript">
var storeItem = new TokenStore("searchFormalContractClientTree");
function showlist(obj){
	//var itemx = "khsy"+obj;
	if(document.getElementById(obj) == null){
		return ;
	}
	if(document.getElementById(obj).style.display  == "none"){
		document.getElementById(obj).style.display = "block";
		storeItem.addToken(obj);
	}
	else{
		document.getElementById(obj).style.display="none";
		storeItem.removeToken(obj);
	}	
}

</script>
<style type="text/css">
<!--
li {
	list-style: none;
	vertical-align: middle;
	padding-left: 10px;
}
-->
</style>
	</head>
	<body>
	<s:hidden name="saleId"/>
	<s:hidden name="conStateSn"/>
				<ul>
				<s:iterator  id="cl" value="clientList" >
					<li class="xlipadding"><a href="javascript:void(0)" target="content" onClick="showlist('<s:property value="'khsy'+ #cl[1]" />');">
					<!-- /yx/contract/formalContractManage/formalContractManageQuery.action?customerId=<s:property value="#cl[1]" />  -->
					<s:property value="#cl[2]" />(<s:property value="#cl[0]" />)</a>
					</li>
					<div  align="left" style="display: none"  id="<s:property value="'khsy'+ #cl[1]" />">
						<ul>
						<s:iterator  id="cm" value="customerConMap" >
						<s:if test="key==#cl[1]"> 									<%--如果系统ID号相同 --%>
						<s:iterator  id="vs" value="value" >
							<li class="xlipadding">
							<s:iterator  id="v" value="vs" status="vs">		<%--遍历value， 1为合同主体系统号，2为合同名称 --%>
								<s:if test="#vs.index==0" >	
									<a href='/yx/contract/formalContractManage/formalContractManage.action?cmisysid=<s:property value="#v"/>' target='content'>
								</s:if>
								<s:else >
									<s:property value="#v"/></a>
								</s:else>
							</s:iterator>
							</li>					
						</s:iterator>
							</s:if>
						</s:iterator>
						</ul>
					</div>	
					</s:iterator>
				</ul>		
	</body>
</html>
<script language="javascript">
	storeItem.retrieve();
	for(var i=0;i<storeItem.tokens.length;i++){
		showlist(storeItem.tokens[i]);
	}
</script>