<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>部门负责人管理</title>
</head>
<body>
<script language="javascript"> 

	function checkedCount(){
	   var idArray=window.document.chargemanList.ids;
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
	   javascript:document.forms(0).action='<s:url includeParams="none" action="chargeman"></s:url>';
	} 

	function edit(){
	   var checkedNum = checkedCount();
	   if(checkedNum == 0){
	   	alert("请选择要修改的部门负责人");
	   	return;
	   }
	   if(checkedNum >1){
	   	alert("只能修改一个部门负责人");
	   	return;
	   }
	   
	   var idArray=window.document.chargemanList.ids;
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
	   javascript:document.forms(0).action='/yx/system/chargeman.action?method=enterUpdate&id='+checkedId;
   } 
   
	function deleteSelfProd(){
		var checkedNum = checkedCount();
	    if(checkedNum == 0){
	   	alert("请选择要删除的部门负责人");
	   	return;
	   }
	   if(window.confirm("确定要删除吗?"))
       {
       document.forms(0).action='<s:url includeParams="none" action="chargeman"><s:param name='method'>delete</s:param></s:url>';
	   document.forms(0).submit();
       return true;
                              
       }
       else
       {
       return false;
       }
	}
	function queryBy(){
   	 document.forms(0).action ="/yx/system/chargemanQuery.action";
   	 document.forms(0).resetCondition.value="true";
  	 document.forms(0).submit();
   }
</script>
<s:form name="chargemanList" action="chargemanQuery.action" theme="simple">
<s:hidden name="resetCondition" value="false"></s:hidden>
	<div align="left" style="color:#000000">当前页面:基础管理->部门负责人管理</div>
	


<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
	<tr>
			<td colspan="3" align="right" class="bg_table01" height="3"></td>	
		</tr>
				<tr align="center" class="bg_table03">
				<td width="35%" align="left" nowrap >负责人姓名： <s:textfield name="searchName" size="10"></s:textfield> 
				部门： <s:textfield name="searchDepartment" size="15"></s:textfield>
				<input class="button01" onclick="queryBy()" type="button" name="Input" value="查  询"  />&nbsp;&nbsp;&nbsp;</td>					
					<td  align="left">&nbsp;&nbsp;&nbsp;
					<input type="submit" name="SearchBtn" value="　新　  增　" onclick="add()" class="button01" />
				    <input type="submit" name="updateBtn" value="　修    改　" onclick="edit()" class="button01" /> 
					<input type="button" name="deleteBtn" value="　删    除　" onclick="deleteSelfProd()" class="button01"/>
					</td>
				</tr>
			</table>

			<table align="center" border="1" cellpadding="1" cellspacing="1" width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td width="8%" class="bg_table01" align="center" nowrap>选择</td>
					<td width="15%" class="bg_table01" align="center" nowrap>负责人姓名</td>
					<td width="15%" class="bg_table01" align="center" nowrap>固定电话</td>
					<td width="15%" class="bg_table01" align="center" nowrap>移动电话</td>
					<td width="15%" class="bg_table01" align="center" nowrap>部门</td>
				</tr>
				<s:iterator value="info.result" id="chargeman" status="status">
				 <s:if test="#status.index!=0">			   
					   </s:if>	
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids" value="<s:property value='id'/>"/></td>
						<td align="left" nowrap><s:property value="#chargeman.name" /></td>
						<td align="left" nowrap><s:property value="#chargeman.phone" /></td>
						<td align="left" nowrap><s:property value="#chargeman.mobilephone" /></td>
						<td align="left" nowrap>
						<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
							<s:iterator value="departmentList" id="department">
						<s:if test="#chargeman.id == #department.chargemanid">
						<tr align="center"><s:property value="typeManageService.getYXTypeManage(1018,#department.departmentid).typeName" /></tr></s:if></s:iterator></table></td>
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

