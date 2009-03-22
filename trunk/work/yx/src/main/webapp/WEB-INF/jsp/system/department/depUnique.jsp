<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>部门列表</title>
<%@ include file="/commons/jsp/meta.jsp"%>

<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<s:form action="depQueryTrue" method="post">
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
			<td align="center" class="bg_table01">部门状态</td>
			<td align="center" class="bg_table01">部门说明</td>

			<td align="center" class="bg_table01">操作</td>
		</tr>

		<tr>
			<td align="left"><s:property value="dep.departmentName" /></td>
			<td align="center"><s:property value="u.name" /></td>
			<td align="center"><s:property value="dep.num" /></td>

			<s:if test="dep.isactive==1">
				<td align="center">有效</td>
			</s:if>
			<s:else>
				<td align="center"><font color="red">无效</font></td>
			</s:else>
			<td align="left"><s:property value="dep.departmentDesc" /></td>
			<td align="center"><a
				href='<s:url action="depart" ><s:param name="depId" value="dep.id" />
	                <s:param name="method">viewDep</s:param><s:param name="action" >update</s:param></s:url>'>编辑</a>
			&nbsp; 
			<s:if test="dep.isactive==1">
			<a
				href='<s:url action="depart" ><s:param name="dep.id" value="dep.id" />
                    <s:param name="method">disableDep</s:param><s:param name="dep.isactive" value="dep.isactive"/></s:url>'  onclick="javascript:if(confirm('要让它失效吗？')){return true} else{return false}">
                    状态失效 </a>
            </s:if>
            <s:else>
                <a
				href='<s:url action="depart" ><s:param name="dep.id" value="dep.id" />
                    <s:param name="method">disableDep</s:param><s:param name="dep.isactive" value="dep.isactive"/></s:url>'  onclick="javascript:if(confirm('要激活它吗？')){return true} else{return false}">
                    状态激活 </a>
            </s:else>
                    </td>
                    
                   
		</tr>

	</table>

</s:form>
</body>
</html>
