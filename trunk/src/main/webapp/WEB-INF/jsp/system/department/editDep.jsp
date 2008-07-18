<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>addNewDepartment</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<h2>部门修改</h2>
<s:form name="editForm" action="depart">
	<s:hidden name="method" value="updateDep" />

	<table width="100%" border="1">

		<tr>
			<td class="bg_table02" colspan="2" align="center">公司组织结构</td>
		</tr>

		<tr>
			<td class="bg_table02"><s:hidden name="dep.id"></s:hidden></td>
		</tr>

		<tr>
			<td class="bg_table02">部门状态</td>
			<td class="bg_table02"><select name="dep.isactive">
				<s:if test="dep.isactive==1">
					<option selected="selected" value="1">有效</option>
					<option value="0">无效</option>
				</s:if>
				<s:else>
					<option selected="selected" value="0">无效</option>
					<option value="1">有效</option>
				</s:else>

			</select></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:select label="部门负责人"
				name="dep.leaderId" list="user" listKey="id" listValue="name"
				required="true" headerValue="us.name">
			</s:select></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:select label="父部门"
				name="dep.parentDepartment" list="deps" listKey="id"
				listValue="departmentName" required="true">
			</s:select></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:textfield label="部门名称"
				name="dep.departmentName"></s:textfield></td>
		</tr>

		<tr>
			<td class="bg_table02"><s:textfield label="部门人数" name="dep.num"></s:textfield></td>
		</tr>


		<tr>
			<td class="bg_table02">部门说明</td>
			<td class="bg_table02"><textarea name="dep.departmentDesc"
				id="depart_dep_departmentDesc" length="3"><s:property
				value="dep.departmentDesc" /></textarea></td>
		</tr>

		<tr>
			<td align="center" colspan="2"><input type="button" value="保存修改"
				onClick="check()" />&nbsp;&nbsp; <input type="button" value="返回"
				onclick="javascript:history.go(-1);" /></td>
		</tr>
	</table>
</s:form>


</body>
</html>
<script type="text/javascript">
function check(){
	if(!validate()){
	document.forms(0).submit();
	}
}
function validate()
	{
		var ev = new Validator();
        with(depart){
            ev.test("notblank", "部门人数不能为空", $('depart_dep_num').value);
		    ev.test("notblank", "部门负责人不能为空", $('depart_dep_leaderId').value);
		    ev.test("notblank", "部门描述不能为空", $('depart_dep_departmentDesc').value);
			
		}
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
		return false;
	}
</script>