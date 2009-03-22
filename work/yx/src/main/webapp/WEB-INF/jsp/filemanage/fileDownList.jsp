<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>文件下载管理</title>
</head>
<body onload="refresh()"  style="margin: 0px;">
<script language="javascript"> 

	function checkedCount(){
	   var idArray=window.document.filedownlist.ids;
	   var count = 0;
	   if(idArray !=null && idArray.length == null){
	   		if(idArray.checked){
				count++;
		   	}
	   }else if(idArray !=null){
		   for(var i=0;i<idArray.length;i++){
		   	if(idArray[i].checked){
				count++;
		   	}
		   }
	   }
	   return count;    
	}

	function filedown(id){
	   openUrl("<s:url includeParams="none" action="fileDownClick"><s:param name="method">enterUpdate</s:param></s:url>&id="+id);
	} 
   
	function deleteSelfProd(){
		var checkedNum = checkedCount();
	   if(checkedNum == 0){
	   	alert("请选择要删除的文件");
	   	return;
	   }
	   if(window.confirm("确定要删除吗?"))
       {
       document.forms(0).action='<s:url includeParams="none" action="fileDownListQuery"><s:param name='method'>delete</s:param></s:url>';
	   document.forms(0).submit();
       return true;
                              
       }
       else
       {
       return false;
       }
	}
	    
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=320,width=700');
	}
	
	function refresh()
    {
    	if(window.parent.window.leftFrame !=null && window.parent.window.leftFrame.main != null){
    		window.parent.window.leftFrame.main.location.reload();   
    	}
    }
</script>
<s:form name="filedownlist" action="fileDownSelect" theme="simple">
		<div align="left" style="color: #000000">
			当前页面:文件管理->文件下载
		</div>
			<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
				<tr>
					<td align="right" class="bg_table01" height="0.5"><img src="../../images/temp.gif" width="1" height="3"></td>
				</tr>
				<tr align="center">
					<td class="bg_table03" align="center">
					 <s:if test="userService.hasAuthority('fileDownMain.action','delete','3')">
					<input type="button" name="deleteBtn" value="　删    除　" onclick="deleteSelfProd()" class="button01"/>
					</s:if>
					</td>
				</tr>
			</table>

			<table align="center" border="1" cellpadding="1" cellspacing="1" width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td width="8%" class="bg_table01" align="center" nowrap>选择</td>
					<td width="12%" class="bg_table01" align="center" nowrap>上传人</td>
					<td width="14%" class="bg_table01" align="center" nowrap>上传时间</td>
					<td width="14%" class="bg_table01" align="center" nowrap>文件名</td>
					<td width="14%" class="bg_table01" align="center" nowrap>类型</td>
					<td width="14%" class="bg_table01" align="center" nowrap>客户</td>
					<td width="20%" class="bg_table01" align="center" nowrap>文件说明</td>
				</tr>
				<s:iterator value="info.result" id="filedown" status="status">
					<tr align="center">
						<td><input type="checkbox" name="ids" value="<s:property value="#filedown[0].id"/>"/></td>
						<td align="left" nowrap><s:property value="#filedown[2]" /></td>
						<td align="center" nowrap><s:date name="#filedown[0].filedate" format="yyyy-MM-dd" /></td>
						<td onclick="filedown(<s:property value="#filedown[0].id"/>)" align="left" nowrap><a href="#"><s:property value="#filedown[0].filename" /></a></td>
						<td align="left" nowrap><s:property value="typeManageService.getYXTypeManage(1014,#filedown[0].filetype).typeName" /></td>
						<td align="left" nowrap><s:property value="#filedown[1]" /></td>
						<td align="left" nowrap><s:property value="#filedown[0].remarks" /></td>
					</tr>
				</s:iterator>
			</table>

			<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info" beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>

</s:form>
</body>
</html>

