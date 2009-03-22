<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>部门列表</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<s:form action="department" method="post">
	<table width="96%" border="1">
		<tr>
			<td colspan="2" align="center" class="bg_table02">公司组织结构</td>
		</tr>
		<tr>
			<td class="bg_table02"><s:hidden name="dep.id"></s:hidden></td>
		</tr>
		<tr>
			<td class="bg_table02" width="302">部门名称</td>
			<td class="bg_table02"><s:property value="dep.departmentName" /></td>
		</tr>
		<tr>
			<td class="bg_table02">部门负责人</td>
			<td class="bg_table02"><s:property value="us.name" /></td>
		</tr>
		<tr>
			<td class="bg_table02">部门人数</td>
			<td class="bg_table02"><s:property value="dep.num" /></td>
		</tr>
		<tr>
			<td class="bg_table02">父部门</td>
			<td class="bg_table02"><s:property value="ddd.departmentName" /></td>
		</tr>
		<tr style="height: 15px;">
			<td class="bg_table02" width="295">部门说明</td>
			<td class="bg_table02"><s:property value="dep.departmentDesc" /></td>
		</tr>
		<tr>
			<td class="bg_table02">部门状态</td>
			<s:if test="dep.isactive==1">
				<td class="bg_table02">有效</td>
			</s:if>
			<s:else>
				<td class="bg_table02" width="117">无效</td>
			</s:else>

		</tr>
		<tr>
			<td class="bg_table02" align="center" colspan="2"><a
				href="javascript:history.go(-1)">返回</a></td>
		</tr>
	</table>

</s:form>
</body>
</html>