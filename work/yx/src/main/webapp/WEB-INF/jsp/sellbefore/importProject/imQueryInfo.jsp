<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<head>
<title>重点工程项目管理主页面</title>
<script language="javascript">

</script>
</head>
<html>
<body style="margin: 0px;">
<div align="left" style="color:#000000">当前页面：售前合同—>重点工程项目 </div>
<s:form action="importantProject">
<s:hidden name="method"  value="goQueryInfo" />
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
		<tr>
			<td colspan="6" align="right" class="bg_table01" height="3"></td>	
		</tr>
		<tr align="center">
			<td  class="bg_table03" align="center">
				 <input type="button" name="update" value="新  增"  class="button01" onclick="addIMP()"/> 
				 <input type="button" name="delete" value="修   改" class="button01" onclick="modIMP()"/> 
			</td>
		</tr>
	</table>
	<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
		<tr align="center" >
			<td class="bg_table01">选择</td>
			<td class="bg_table01">客户名称</td>
			<td class="bg_table01">工程编号</td>
			<td class="bg_table01">工程名称</td>
			<td class="bg_table01">修改人</td>
			<td class="bg_table01">修改时间</td>
		</tr>
		<s:iterator value="info.result" id="imp">
		<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
			<td><input type="checkbox" name="impId" value="<s:property value="#imp[0].id"/>"/></td>
			<td align="left"><s:property value="#imp[1]" /></td>
			<td align="left"><s:property value="#imp[0].projectNum" /></td>
			<td align="left"><s:property value="#imp[0].projectName" /></td>
			<td align="left"><s:property value="#imp[2]" /></td>
			<td><s:property value="#imp[0].updateBy" /></td>
		</tr>
		</s:iterator>
	</table>
	<table cellSpacing=1 cellPadding=2 width="100%" border=0>
		<tr valign="top">
				<td class="bg_table04"><baozi:pages value="info" beanName="info" formName="forms(0)" /></td>
		</tr>
	</table>
</s:form>
</body>
<script language="javascript">
function addIMP(){
	location.href="../sellbefore/importantProject.action?method=addIMP";
}
function modIMP(){
	var checkedBoxes = $$("input[name=impId][checked]");
	if(  1 == checkedBoxes.length){
		location.href="../sellbefore/importantProject.action?method=openModIMP&impId="+ checkedBoxes[0].value;
	}
	else if( 0 == checkedBoxes.length){
		alert("请选择要修改的重点工程项目 ");
		return;
	}
	else if( 1  <  checkedBoxes.length){
		alert("请不要多选");
		return;
	}
}

<s:if test="alertInfo!=null">
	alert("<s:property value="alertInfo" />");
</s:if>
</script>
</html>