<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>自有产品管理</title>
</head>
<body>
<script language="javascript"> 

	function checkedCount(){
	   var idArray=window.document.selfproductManage.ids;
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

	function add(){
	   openUrl("<s:url includeParams="none" action="selfProduction"></s:url>");
	} 

	function edit(){
	   var checkedNum = checkedCount();
	   if(checkedNum == 0){
	   	alert("请选择要修改的自有产品");
	   	return;
	   }
	   if(checkedNum >1){
	   	alert("只能修改一个自有产品");
	   	return;
	   }
	   
	   var idArray=window.document.selfproductManage.ids;
	   var checkedId = "";
	   if(idArray !=null && idArray.length == null){
	   		if(idArray.checked){
				checkedId = idArray.value;
		   	}
	   }else if(idArray !=null){
		   for(var i=0;i<idArray.length;i++){
		   	if(idArray[i].checked){
		   		checkedId = idArray[i].value;
		   		break;
		   	}
		   }
	   }   
	   openUrl("<s:url includeParams="none" action="selfProduction"><s:param name="method">enterUpdate</s:param></s:url>&id="+checkedId);
   } 
   
	function deleteSelfProd(){
		var checkedNum = checkedCount();
	   if(checkedNum == 0){
	   	alert("请选择要删除的自有产品");
	   	return;
	   }
	   if(window.confirm("确定要删除吗?"))
       {
       document.forms(0).action='<s:url includeParams="none" action="selfProduction"><s:param name='method'>delete</s:param></s:url>';
	   document.forms(0).submit();
       return true;
                              
       }
       else
       {
       return false;
       }
	}
	    
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=280,width=600');
	}
</script>
<s:form name="selfproductManage" action="selectSelfProduct" theme="simple">
  <div align="left" style="color:#000000">当前页面:基础管理->自有产品管理</div>
	
			<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
				<tr>
					<td align="right" class="bg_table01" height="0.5"><img src="../../images/temp.gif" width="1" height="3"></td>
				</tr>
				<tr align="center">
					<td class="bg_table03" align="center">
					<input type="button" name="SearchBtn" value="　新　  增　" onclick="add()" class="button01" />
				    <input type="button" name="updateBtn" value="　修    改　" onclick="edit()" class="button01" /> 
					<input type="button" name="deleteBtn" value="　删    除　" onclick="deleteSelfProd()" class="button01"/>
					</td>
				</tr>
			</table>
			
			<table align="center" border="1" cellpadding="1" cellspacing="1" width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td width="9%" class="bg_table01" align="center" nowrap>选择</td>
					<td width="18%" class="bg_table01" align="center" nowrap>产品登记名称</td>
					<td width="14%" class="bg_table01" align="center" nowrap>证书标号</td>
					<td width="14%" class="bg_table01" align="center" nowrap>登记日期</td>
					<td width="14%" class="bg_table01" align="center" nowrap>有效期</td>
				</tr>
				<s:iterator value="info.result" status="status">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids" value="<s:property value='id'/>"/></td>
						<td align="left" nowrap><s:property value="registerName" /></td>
						<td align="left" nowrap><s:property value="certificateNum" /></td>
						<td align="center" nowrap><s:date name="registerDate" format="yyyy-MM-dd" /></td>
						<td align="center" nowrap><s:date name="validateTime" format="yyyy-MM-dd" /></td>
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

