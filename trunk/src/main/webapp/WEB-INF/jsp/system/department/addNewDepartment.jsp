<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>addNewDepartment</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="<s:url value="/commons/scripts/checkLength.js"/>"
	type="text/javascript"></script>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:form action="depart" name="form0">
	<s:hidden name="method" value="saveDep" />
	<table width="96%" border="1">
		<tr>
			<td colspan="2" align="center">公司组织结构</td>
		</tr>
		<tr>
			<td class="bg_table02">部门状态</td>
			<td class="bg_table02"><select name="dep.isactive"
				id="depart_dep_isactive">
				<option value="1">有效</option>
				<option value="0">无效</option>
			</select></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:select label="部门负责人"
				name="dep.leaderId" list="user" listKey="id" listValue="name"
				required="true" id="editdep">
			</s:select></td>
		</tr>
		<tr>
			<td class="bg_table02"><s:select label="父部门"
				name="dep.parentDepartment" list="deps" listKey="id"
				listValue="departmentName" required="true">
			</s:select></td>
		</tr>
		<tr>
			<td class="bg_table02">部门名称</td>
			<td class="bg_table02"><input type="text"
				name="dep.departmentName" value="" id="depn"></td>
		</tr>
		<tr>
			<td class="bg_table02">部门人数</td>
			<td class="bg_table02"><input type="text" name="dep.num"
				id="depnum"></td>
		</tr>

		<tr>
			<td class="bg_table02">部门说明</td>
			<td class="bg_table02"><textarea id="dDesc"
				name="dep.departmentDesc"></textarea></td>
		</tr>

		<tr>
			<td align="center" colspan="2" class="bg_table02"><input
				type="button" value="提 交" onClick="check()" /> &nbsp;&nbsp; <input
				type="button" value="返回" onclick="javascript:history.go(-1);" /></td>
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
			ev.test("notblank", "部门不能为空", $('depn').value);
		    ev.test("notblank", "部门人数不能为空", $('depnum').value);
		    ev.test("notblank", "部门状态不能为空", $('depart_dep_isactive').value);
		    ev.test("notblank", "部门描述不能为空", $('dDesc').value);
		    ev.test("notblank", "部门名称长度应为1－20个字符，请重新输入",checkLength(0,20,$('depn').value));
		    ev.test("notblank", "部门负责人不能为空", $('editdep').value);
		}
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
		return false;
	}

 </script>