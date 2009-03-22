<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<script
	src="<s:url value="/commons/scripts/mootools-release-1[1].11.js"/>"
	type="text/javascript"></script>
<title>新增负责人</title>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<body>
<s:form action="chargeman" theme="simple">
	<s:hidden name="method" value="" />
	<input type="hidden" name="deleteDepartmentid"/>
	<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td height="3" align="left">当前页面：部门负责人管理-&gt;新增负责人</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img
						src="../../images/temp.gif" width="1" height="1"></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td class="bg_table02" align="right"><font color="red">* </font>负责人姓名：</td>
					<td class="bg_table02" align="left"><s:textfield
						name="cm.name"></s:textfield></td>				
				</tr>
				<tr align="center">	
					<td class="bg_table02" align="right">固定电话：</td>
					<td class="bg_table02" align="left"><s:textfield
						name="cm.phone"></s:textfield></td>
				</tr>
				<tr align="center">	
					<td class="bg_table02" align="right">移动电话：</td>
					<td class="bg_table02" align="left"><s:textfield
						name="cm.mobilephone"></s:textfield></td>
				</tr>
				<tr align="center">
 			    <td colspan="6" align="left" nowrap class="bg_table02">	<hr></td>
                </tr>
                </table>
   				<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
          		<tr align="center">
					<td width="50%" class="bg_table01" align="left" nowrap>部门</td>
					<td align="left" class="bg_table01" nowrap>操作</td>
				</tr>
          		<tr align="center">
					<td class="bg_table02" align="left"><s:select name="departmentid" 
					list="departmentList" listKey="typeSmall" listValue="typeName"
					required="true" headerValue="">
				    </s:select></td>
					<td align="left" class="bg_table02" ><a href="javascript:addDepartment()">添加</a></td>
				</tr>
				<s:iterator value="relatedDeparment" id="departmentId" status="status">
					<tr align="center">
						<td align="left" class="bg_table02" nowrap><s:property value="typeManageService.getYXTypeManage(1018,#departmentId).typeName" /></td>
						<td align="left" class="bg_table02" ><a href="javascript:delDepartment('<s:property value="#departmentId" />')">删除</a></td>
					</tr>
				</s:iterator>
			</table>
			<Table style="width: 100%;">
				<tfoot class="bg_table03" style="height: 42px">
					<tr>
						<td align="center" colspan="2"><input class="button01"
							type="button" name="" value="保   存" onclick="return check()"/>   <input name="save" type="submit"
							class="button01" onclick="javascript:document.forms(0).action='/yx/system/chargemanQuery.action'" value="返   回"></td>
					</tr>
				</tfoot>
			</Table>
			</td>
		</tr>
	</table>
</s:form>

<script type="text/javascript">
function check(){	
	if(!validate()){
		document.forms(0).method.value = "savePro";
		document.forms(0).submit();
	}
	return false;
}

function validate(){
       var ev2=new Validator();
       with(chargeman){  
       ev2.test("notblank","负责人姓名不能为空",$('cm.name').value);       
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}
function addDepartment(){
	document.forms(0).method.value = "saveDepartment";
	document.forms(0).submit();
}
function delDepartment(departmentCode){
	document.forms(0).deleteDepartmentid.value = departmentCode;
    document.forms(0).method.value = "delDepartment";
	document.forms(0).submit();
}

</script>

</body>
</html>

