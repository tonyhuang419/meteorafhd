<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>选择应交材料</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
function queren()
{
	var checkNode=$$("input[name='customerMaterial'][checked]");
	if(checkNode.length<=0){
	alert("请选择应交材料！");
	return;
	}
	var str="";
	for(var k=0;k<checkNode.length;k++){
		str+=checkNode[k].value+",";
	}
	window.opener.baocunMaterial(str);
	window.close();
}
</script>
</head>
<body>
<s:form action="contract.action" theme="simple">
<table align="center" width="100%" border="1" bordercolor="#808080" style=" border-collapse: collapse;">
  <tr class="bg_table01">
    <td colspan="2" align="center">应交材料选择</td>
    
  </tr>
  <s:iterator value="materialManagerList" id="material">
  <tr class="bg_table02">
  <td align="right"><input type="checkbox" name="customerMaterial" <s:if test="isMaterialChecked(#material.materialCode)">checked</s:if> value="<s:property value="#material.materialCode"/>"/></td>
  <td align="left">
  <s:property value="#material.materialName"/>
  </td>
  </tr>
  </s:iterator>
  <tr class="bg_table03">
  <td colspan="2" align="center">  
  <input type="button" value="确定" onclick="queren();" class="button01"/>&nbsp;&nbsp;
  <input type="button" value="关闭"  onclick="window.close();" class="button01"/>
  </td>
  </tr>
</table>
</s:form>
</body>
</html>