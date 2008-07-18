<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form name="clientQuery" action="user.action" theme="simple">
	<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
				<tr class="bg_table03">
					<td colspan="3" class="bg_table03">客户名称：<s:textfield name="clientName"></s:textfield></td>
					<td colspan="3" class="bg_table03">客户性质：<s:textfield name="typeName"></s:textfield></td>
				</tr>
				<tr class="bg_table03">
					<td colspan="6" class="bg_table03">
					<div align="center"><input type="submit" name="SearchBtn2" value="　查 询　" class="button01" onClick="javascript:document.forms(0).action='<s:url action="user.action"><s:param name="method">doSelect</s:param></s:url>';"></div>
					</td>
				</tr>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01">客户</td>
					<td class="bg_table01">客户性质</td>
					<td class="bg_table01">行业类型</td>
					<td class="bg_table01">客户开票名称</td>
					<td class="bg_table01">客户开户银行</td>
					<td class="bg_table01">开户帐号</td>
					<td class="bg_table01">税号</td>
				</tr>

				<s:iterator value="info.result" id="result">
					<tr align="center">
						<td><input type="checkbox" name="ids" value="<s:property value="#result[0].id"/>" onclick="doSelect();"/></td>
						<td><input type="hidden" name="name" value="<s:property value='#result[0].name' />"/><s:property value="#result[0].name" /></td>
						<td><input type="hidden" name="typeBigName" value="<s:property value='#result[1].typeName' />"/><s:property value="#result[1].typeName" /></td>
						<td><input type="hidden" name="typeSmallName" value="<s:property value='#result[2].typeName' />"/><s:property value="#result[2].typeName" /></td>
						<td><s:property value="#result[0].billName" /></td>
						<td><s:property value="#result[0].billBank" /></td>
						<td><s:property value="#result[0].account" /></td>
						<td><s:property value="#result[0].taxNumber" /></td>
					</tr>
				</s:iterator>
				<tr class="bg_table03">
					<td colspan="8" align="center" class="bg_table03"><input type="button" name="SearchBtn" value="　确认关联　" class="button01" onClick="onSubmit();" disabled="true">
					<input type="button" name="close" value="　关 闭　" class="button01" onClick="javascript:window.close();"></td>
				</tr>
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info" beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			</td>
		</tr>
	</table>
<script language="javascript">
	function doSelect(){
	  var id=window.document.clientQuery.ids;
	if(id.length){
   for(i=0;i<id.length;i++){     
      if(id[i].checked==true){
          window.document.clientQuery.SearchBtn.disabled=false;    
          break;
      }
   }
   if(i==id.length){
      window.document.clientQuery.SearchBtn.disabled=true;    
   }
   }else{
   if(id.checked==true){
          window.document.clientQuery.SearchBtn.disabled=false;    
      }  
   }
}

function onSubmit(){
  var id=window.document.clientQuery.ids;
  var name=window.document.clientQuery.name;
  var typeBigName=window.document.clientQuery.typeBigName;
  var typeSmallName=window.document.clientQuery.typeSmallName;
  var ids=new Array();
  var names=new Array();
  var typeBigNames=new Array();
  var typeSmallNames=new Array();
  if(id.length){
    for(j=0;j<id.length;j++){
      if(id[j].checked==true){
         ids.push(id[j]);
         names.push(name[j]);
         typeBigNames.push(typeBigName[j]);
         typeSmallNames.push(typeSmallName[j]);
      }
    }
  }else{
    if(id.checked==true){
        ids.push(id);
        names.push(name);
        typeBigNames.push(typeBigName);
        typeSmallNames.push(typeSmallName);
     }
  }  
  window.opener.getClientCode(ids,names,typeBigNames,typeSmallNames);
  window.close();
}
</script>
</s:form>
</body>
</html>
