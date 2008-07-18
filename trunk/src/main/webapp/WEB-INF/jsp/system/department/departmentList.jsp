<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>部门列表</title>
<%@ include file="/commons/jsp/meta.jsp"%>

<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<s:form action="departmentQuery" method="post">
	<table border="1" align="center" width="96%" id="tableX"
		cellspacing="1" cellpadding="1" class="bg_white">
		<tr>
			<td colspan="8" align="right"><a
				href='<s:url action="depart" ></s:url>'>添加部门</a></td>
		</tr>
		<tr>

			<td align="center" class="bg_table01">部门名称</td>
			<td align="center" class="bg_table01">部门负责人</td>
			<td align="center" class="bg_table01">部门人数</td>
			<td align="center" class="bg_table01">父部门</td>
			<td align="center" class="bg_table01">部门状态</td>
			<td align="center" class="bg_table01">部门说明</td>

			<td align="center" class="bg_table01">操作</td>
		</tr>
		<s:iterator id="result" value="list">
			<tr>
				<td align="left"><a
					href='<s:url action="depart" ><s:param name="depId" value="#result[0].id" />
	                <s:param name="method">viewDep</s:param></s:url>'><s:property
					value="#result[0].departmentName" /></a></td>
				<td align="center"><s:property value="#result[2]" /></td>
				<td align="center"><s:property value="#result[0].num" /></td>

				<s:if test="#result[1]==null">
					<td align="center">宝资公司</td>
				</s:if>
				<s:else>
					<td align="center"><s:property value="#result[1]" /></td>
				</s:else>


				<s:if test="#result[0].isactive==1">
					<td align="center">有效</td>
				</s:if>
				<s:else>
					<td align="center">无效</td>
				</s:else>
				<td align="left"><s:property value="#result[0].departmentDesc" /></td>
				<td align="center"><a
					href='<s:url action="depart" ><s:param name="depId" value="#result[0].id" />
	                <s:param name="method">viewDep</s:param><s:param name="action" >update</s:param></s:url>'>编辑</a>
				&nbsp; <a
					href='<s:url action="depart" ><s:param name="dep.id" value="#result[0].id" />
                    <s:param name="method">disableDep</s:param></s:url>'>状态失效</a>
			</tr>
		</s:iterator>
	</table>
	<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
		<tr valign="top">
			<td class="bg_table04"><baozi:pages value="info" beanName="info"
				formName="forms(0)" /></td>
		</tr>
	</TABLE>
</s:form>
</body>
</html>