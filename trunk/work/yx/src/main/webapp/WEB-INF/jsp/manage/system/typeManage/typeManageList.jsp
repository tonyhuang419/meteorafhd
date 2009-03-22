<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>新增类型管理</title>
</head>
<body>
<s:form name="typeManage" action="typeManageQuery.action" theme="simple">
<s:hidden id="listTypeBig" name="typeBig"></s:hidden>
	<div align="left" style="color:#000000">当前页面:基础管理->类型管理</div>

			<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
				<tr>
					<td align="right" class="bg_table01" height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr align="center">
					<td class="bg_table03" align="center"><input type="submit" name="SearchBtn" value="　新　  增　"
						onclick="javascript:document.forms(0).action='<s:url includeParams="none" action="typeManage"><s:param name='typeBig'><s:property value='typeBig'/></s:param></s:url>';" class="button01" /> 
						<input type="submit" name="updateBtn" value="　修   改　" onClick="javascript:document.forms(0).action='<s:url action="typeManage"></s:url>';" class="button01" disabled=true/> 
						<input type="button" name="deleteBtn" value="　删    除　" class="button01"
						onClick="deleteSelfProd()" disabled=true/>
						<input type="button" name="deleteEn" value=" 彻底删除 " class="button01"
						onClick="deleteEntire()" disabled=true/></td>
				</tr>
			</table>
			
			<table align="center" border="1" cellpadding="1" cellspacing="1" width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td width="9%" class="bg_table01" align="center" nowrap="true">选择</td>
					<td width="13%" class="bg_table01" align="left" nowrap="true">类型编号</td>
					<td width="17%" class="bg_table01" align="left" nowrap="true">类型种类</td>
					<td width="14%" class="bg_table01" align="left" nowrap="true">类型名称</td>
					<td width="14%" class="bg_table01" align="left" nowrap="true">其他信息</td>
					<td width="20%" class="bg_table01" align="left" nowrap="true">最后修改日期</td>
					<td width="13%" class="bg_table01" align="left" nowrap="true">最后修改人</td>
				</tr>
				<s:iterator value="info.result" id="typeList" status="status">
					<tr align="center">
						<td><input type="checkbox" name="ids" value="<s:property value='#typeList[0].id'/>" onclick="edit();" /></td>
						<td align="left" nowrap="true"><s:property value="#typeList[0].typeSmall" /></td>
						<td align="left" nowrap="true"><s:property value="typeBigMap.get(#typeList[0].typeBig)" /></td>
						<td align="left" nowrap="true"><a href="<s:url action='typeManage.action'><s:param name='ids'><s:property value='#typeList[0].id'/></s:param></s:url>"><s:property
							value="#typeList[0].typeName" /></a></td>
						<td align="left" nowrap="true"><s:property value="#typeList[0].info" /></td>
						<td align="center" nowrap="true"><s:date name="#typeList[0].updateBy" format="yyyy-MM-dd" /></td>
						<td align="left" nowrap="true"><s:property value="#typeList[1].name" /></td>
					</tr>
				</s:iterator>
			</table>
			
			<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info" beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			
<script language="javascript"> 
function edit(){
   var id=window.document.typeManage.ids;
   if(id.length){
   for(i=0,j=0;i<id.length;i++){     
      if(id[i].checked==true){
        window.document.typeManage.SearchBtn.disabled=true;
        window.document.typeManage.deleteBtn.disabled=false;
        window.document.typeManage.deleteEn.disabled=false;
        if(j<1){                 
          window.document.typeManage.updateBtn.disabled=false;          
          j++;
        }else{         
          window.document.typeManage.updateBtn.disabled=true;           
          break;                   
        }        
      }
   }
   if(i==id.length){
   if(j==0){
   window.document.typeManage.SearchBtn.disabled=false;
   window.document.typeManage.updateBtn.disabled=true;
   window.document.typeManage.deleteBtn.disabled=true;
   window.document.typeManage.deleteEn.disabled=true;
   }
   }
   }else{
    if(id.checked==true){
        window.document.typeManage.SearchBtn.disabled=true;
        window.document.typeManage.deleteBtn.disabled=false;                  
          window.document.typeManage.updateBtn.disabled=false;
          window.document.typeManage.deleteEn.disabled=false;          
      }
   }
 
	return;
}

function deleteSelfProd(){
	   if(window.confirm("确定要删除吗?"))
       {
       document.forms(0).action='<s:url includeParams="none" action="typeManage"><s:param name='method'>delete</s:param></s:url>';
	   document.forms(0).submit();
       return true;
                              
       }
       else
       {
       return false;
       }
	}
	
function deleteEntire(){
	if(window.confirm("确定要彻底删除吗?"))
       {
       document.forms(0).action='<s:url includeParams="none" action="typeManage"><s:param name='method'>deleteEntire</s:param></s:url>';
	   document.forms(0).submit();
       return true;
                              
       }
       else
       {
       return false;
       }
	}
</script>
</s:form>
</body>
</html>

