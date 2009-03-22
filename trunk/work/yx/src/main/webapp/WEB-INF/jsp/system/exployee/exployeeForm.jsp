<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>

<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">

</script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:form action="exployee">
	<s:hidden name="method" value="updateExp" />
	<s:hidden name="exp.is_active" value="1"></s:hidden>
	<table width="96%" border="1" class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=1 width="100%">
				<tr>
					<td colspan="4" align="center" class="txt_title01">员工信息新增</td>
				</tr>
				<tr>
					<td colspan="4" align="right" class="bg_table01" height="0.5"><img
						src="./images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr>
					<td class="bg_table02"><s:hidden name="exp.id"></s:hidden></td>
				</tr>
				<tr class="bg_table02">
					<td width="20%" class="bg_table02" align="right">员工名:</td>
					<td width="380" class="bg_table02" align="left"><s:textfield
						name="exp.name" /></td>
					<td width="20%" class="bg_table02" align="right">员工工号：</td>
					<td width="30%" class="bg_table02" align="left"><s:textfield
						name="exp.workId" /></td>
				</tr>
				<tr>
					<td class="bg_table02"><s:select label="职责选择"
						name="exp.zzdmId" list="dutyList" listKey="id" listValue="name"
						required="true" headerValue="exp.name">
					</s:select></td>

					<td class="bg_table02"><s:select label="组别选择"
						name="exp.groupId" list="groupList" listKey="id" listValue="name"
						required="true" headerValue="exp.name">
					</s:select></td>
				</tr>
				<tr class="bg_table02">
					<td class="bg_table02" align="right">手机：</td>
					<td class="bg_table02" align="left"><s:textfield
						name="exp.callPhone" /></td>

				</tr>
				<tr>
					<td class="bg_table02" width="20%" align="right">电话：</td>
					<td class="bg_table02" align="left" colspan="3"><s:textfield
						name="exp.phone" /></td>

				</tr>
				<tr align="center">
					<td width="20%" class="bg_table02" align="right">其他联系方式：</td>
					<td class="bg_table02" align="left" colspan="3"><s:textfield
						name="exp.otherPhone" /></td>

					<td width="20%" class="bg_table02" align="right">邮件地址:</td>
					<td class="bg_table02" align="left" colspan="3"><s:textfield
						name="exp.email" /></td>
				</tr>


				<tr align="center">
					<td colspan="4" class="bg_table03"><input type="button"
						name="SearchBtn" value="　保   存　" class="button01"
						onClick="check()"> <input type="reset" name="SearchBtn"
						value="　重   写  " class="button01"> <input type="button"
						name="return" value="　返   回  " class="button01"
						onclick="javascript:history.go(-1)"></td>
				</tr>
			</table>
			</td>
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
        with(exployee){
		    ev.test("notblank", "职责不能为空", $('exployee_exp_zzdmId').value);
			ev.test("notblank", "组别不能为空", $('exployee_exp_groupId').value);
<!--			ev.test("notblank", "项目开始时间不能为空", $('hstime').value);-->
<!--			ev.test("notblank", "项目结束时间不能为空", $('tetime').value);-->
<!--			ev.test("notblank", "项目名称不能为空", $('projectName').value);-->
<!--		    ev.test("notblank", "项目编号不能为空", $('ID').value);-->
<!--		    ev.test("notblank", "开始时间不能大于结束时间", checkTime($('hstime').value,$('tetime').value));-->
		   }
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
		
		
		
          
	}
	
</script>
