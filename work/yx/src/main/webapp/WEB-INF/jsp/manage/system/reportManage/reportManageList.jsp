<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>新增公告管理</title>
</head>
<body>
<s:form name="gonggao" action="" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td>当前页面:基础管理->公告管理</td>
		</tr>
		<tr><td>
			<s:iterator value="messages" id="mes">
  				<div align="left"><font color="red"><s:property value="#mes"/></font><br></div>
			</s:iterator></td></tr>
		<tr>
			<td>
			<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
				<tr>
					<td align="right" class="bg_table01" height="0.5"><img src="../../images/temp.gif" width="1" height="3"></td>
				</tr>
				<tr align="center">
					<td class="bg_table03" align="center"><input type="button" name="SearchBtn" value="新  增" onclick="add()" class="button01" /> 
					<input type="button" name="updateBtn" value="修  改" onclick="edit()" class="button01" /> 
					<input type="button" name="deleteBtn" value="删  除" class="button01" onclick="return deleteSelfProd()" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table align="center" border="1" cellpadding="1" cellspacing="1" width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center" class="bg_table01">
					<td nowrap>选择</td>
					<td nowrap>编号</td>
					<td nowrap>公告内容</td>
					<td nowrap>公告天数</td>
					<td nowrap>发布时间</td>
					<td nowrap>发布人</td>
				</tr>
				<s:iterator value="info.result" id="reportlist" status="status">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids" value="<s:property value="#reportlist[0].id"/>" /></td>
						<td align="left" nowrap><s:property value="#reportlist[0].id" /></td>
						<td align="left" ><s:property value="#reportlist[0].content" /></td>
						<td align="left" nowrap><s:property value="#reportlist[0].gdays" /></td>
						<td align="left" nowrap><s:date name="#reportlist[0].updateBy" format="yyyy-MM-dd" /></td>
						<td align="left" nowrap><s:property value="#reportlist[1].name" /></td>
					</tr>
				</s:iterator>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info" beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			</td>
		</tr>
	</table>
<script language="javascript"> 
	function checkedCount(){
	   var idArray=window.document.gonggao.ids;
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
	   openWin2("<s:url action="gonggao"></s:url>",600,300,"newGongGao");
	} 

	function edit(){
	   var checkedNum = checkedCount();
	   if(checkedNum == 0){
	   	alert("请选择要修改公告内容");
	   	return;
	   }
	   if(checkedNum >1){
	   	alert("只能修改一个公告内容");
	   	return;
	   }
	   
	   var idArray=window.document.gonggao.ids;
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
	   openWin2("<s:url action="gonggao"><s:param name="method">enterUpdate</s:param></s:url>&id="+checkedId , 600,300,"modGongGao");
   } 
   
	function deleteSelfProd(){
		var checkedNum = checkedCount();
	   if(checkedNum == 0){
	   	alert("请选择要删除的公告");
	   	return;
	   }
	    if(window.confirm("确定要删除吗?"))
       {
      document.forms(0).action='<s:url action="gonggao"><s:param name='method'>delete</s:param></s:url>';
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

