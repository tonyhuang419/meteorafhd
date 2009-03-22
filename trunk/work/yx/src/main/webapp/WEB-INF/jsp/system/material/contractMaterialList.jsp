<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>应交材料设置列表</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
function addMaterial()
{
	document.forms(0).method.value="addEnter";
	document.forms(0).submit();
}
function updateMaterial()
{
	var opValue=checkValue();
	var valueArr=opValue.split(",");
	if(valueArr!=null&&valueArr.length>1){
		if(valueArr.length>2){
			alert("修改只能选择一个！");
		}else{
			document.forms(0).method.value="updateEnter";
			document.forms(0).elements('mid').value=valueArr[0];
			document.forms(0).submit();
		}
	}else{
		alert("请选择要修改的应交材料设置！");
	}
}
function deleteMaterial()
{
	var opValue=checkValue();
	if(opValue.length == 0 ){
		alert("请选择要删除的应交材料设置");
		return;
	}
	if(confirm("确定要删除选中的应交材料设置吗？")){
		document.forms(0).action="/yx/system/material/contractMaterialSet.action?idarr="+opValue;
		document.forms(0).method.value="deleteContractMaterialSet";
		document.forms(0).submit();
	}
}
function checkValue()
{
 	var returnValue="";
 	var checkNode=document.forms(0).elements('materialId');
 	if(checkNode!=null&&checkNode.length>0){
 		for(var k=0;k<checkNode.length;k++){
 			if(checkNode[k].checked){
 				returnValue+=checkNode[k].value+",";
 			}
 		}
 	}else{
 		if(checkNode.checked){
 			returnValue+=checkNode.value+",";
 		}
 	}
 	return returnValue;
}
function showDetails(tid){
	openWin("/yx/system/material/contractMaterialSet.action?method=showDetails&mid="+tid, 500, 300);
}
function query(){
	document.forms(0).action="/yx/system/material/contractMaterialSet.action?resetCondition=true";
	document.forms(0).method.value="doDefault";
	document.forms(0).submit();
}
</script>
</head>
<body>
<div align="left" style="color:#000000">当前页面：基础管理—>应交材料设置 </div>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:form action="yx/system/material/contractMaterialSet.action" theme="simple">
<s:hidden name="method" value="doDefault"></s:hidden>
<s:hidden name="mid"></s:hidden>
	<table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
		<tr>
			<td colspan="3" align="right" class="bg_table01" height="3"></td>	
		</tr>
		<tr class="bg_table03">
			<td align="left" width="350">
				客户名称：<s:textfield name="queryCustomName"></s:textfield><input type="button" value="查询" class="button01" onclick="query();"/>
			</td>
			<td align="left">
			<input type="button" value="新增" class="button01" onclick="addMaterial();"/> &nbsp;&nbsp;
			<input type="button" value="修改" class="button01" onclick="updateMaterial();"/> &nbsp;&nbsp; 
			<input type="button" value="删除" class="button01" onclick="deleteMaterial();"/>
			</td>
		</tr>
	</table>

			<table align="center" border="1" cellpadding="0" cellspacing="0" 
				width="100%"  bordercolor="#808080" style="border-collapse: collapse;">
				<tr class="bg_table01">
					<td align="center">选择</tD>
					<td align="center" >客户名称</tD>
					<td align="center">客户项目性质</td>
				</tr>
				<s:iterator value="info.result" id="material">
					<tr onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td align="center"><input type="checkbox" name="materialId" value="<s:property value="#material[0].id"/>"/></tD>
						<td onclick="showDetails('<s:property value="#material[0].id"/>')"><s:property value="#material[1]"/></td>
						<td onclick="showDetails('<s:property value="#material[0].id"/>')"><s:property value="typeManageService.getYXTypeManage(1007L,#material[0].customerProjectType).typeName"/></td>
					</tr>
				</s:iterator>
			</table>

		<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04">
						<baozi:pages value="info" beanName="info" formName="forms(0)" />
					</td>
				</tr>
			</TABLE>

</s:form>
</body>
</html>