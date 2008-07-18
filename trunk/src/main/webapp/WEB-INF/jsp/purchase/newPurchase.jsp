<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>addNewDepartment</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all"
	href="./css/calendar-win2k-cold-1.css" title="win2k-cold-1" />
<script src="../commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="<s:url value="/commons/scripts/CalendarSelector.js"/>"
	type="text/javascript"></script>

<script language="javascript">

function validate(){ 	
	var ev = new Validator();
	with(purchase){
		
		ev.test("float","申购金额必须为数字！",$('am.applymoney').value);
	}
	if (ev.size() > 0) {
		ev.writeErrors(errorsFrame, "errorsFrame");
     	return true;
  }
    return false;
}	
	function checkSubmit3(){
		if(!validate()){
			document.forms(0).action="/yx/z/purchase.action?method=savePurchase&state=draft&action=contract";
			document.forms(0).submit();
		}
	}
	
	function checkSubmit4(){
		if(!validate()){
			document.forms(0).action="/yx/purchase/purchase.action?method=savePurchase&state=wait&action=contract";
			document.forms(0).submit();
		}
	}
	
</script>

</head>
<body>
<s:form action="purchase" theme="simple" >
    <s:hidden name="am.id"></s:hidden>
    <s:hidden name="am.applyType" />
    <s:hidden name="am.eventId" />
	<table width="95%" border="0" align="center" cellpadding="1"
		cellspacing="1">
		<tr>
			<td valign="top" align="center">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td height="3" align="left">当前页面：申购采购->
					<s:if test="'view'.equals(action)">申购采购查看</s:if>
					<s:elseif test="'update'.equals(action)">申购采购修改</s:elseif>
					<s:else>申购采购新建</s:else>
					</td>
				</tr>
				<tr>
					<td align="center">
					<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="30%" scrolling="no"></iframe>
					</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img
						src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
				</tr>
			</table>
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" align="right" class="bg_table02">申购人：</td>
					<td width="23%" align="left" class="bg_table02"><s:property value="applyMan" /></td>
					<td width="17%" align="right" class="bg_table02">申购部门：</td>
					<td width="35%" align="left" class="bg_table02"><s:property value="applyDepartment" /></td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">申购类型：</td>
					<td align="left" class="bg_table02" colspan=3><s:property value="applyType" /></td>
				</tr>
			</table>
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
					<td width="25%" class="bg_table02" align="right">合同号：</td>
					<td width="23%" class="bg_table02" align="left"><s:property value="contractId" /><s:hidden name="am.mainId" /></td>
				    <td width="17%" class="bg_table02" align="right">客户：</td>
					<td align="left" class="bg_table02"><s:property value="cName2" /><s:hidden
						name="am.customerId" /></td>	
				</tr>
			</table>
			<s:if test="eventId!=0">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
					<td class="bg_table02" align="right">项目号：</td>
					<td align="left" class="bg_table02" colspan="3"><s:property value="am.eventId" /></td>
				</tr>
			
			<tr align="center">
					<td width="25%" class="bg_table02" align="right">项目名称：</td>
					<td width="23%" class="bg_table02" align="left">
					<s:textfield name="am.projectName" size="15" ></s:textfield></td>
				<td width="17%" class="bg_table02" align="right">任务号：</td>
					<td align="left" class="bg_table02"><s:textfield
						name="am.assignmentId" size="15" /></td>	
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">设备预算：</td>
					<td class="bg_table02" align="left"><s:textfield
						name="am.bugget" size="15" /></td>
					<td class="bg_table02" align="right">&nbsp;</td>
					<td align="left" class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</s:if><s:else>
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
					<td width="25%" class="bg_table02" align="right"><font color=red>*</font>项目名称：</td>
					<td width="23%" class="bg_table02" align="left">
					<s:textfield name="am.projectName" size="15" ></s:textfield></td>
				<td width="17%" class="bg_table02" align="right">任务号：</td>
					<td align="left" class="bg_table02"><s:textfield
						name="am.assignmentId" size="15" /></td>	
				</tr>
				</table>
			</s:else>
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" height="24" align="right" class="bg_table02"><font color=red>*</font>申购金额：</td>
					<td width="23%" class="bg_table02" align="left"><s:textfield name="am.applymoney"
						size="15" /> </td>
					<td colspan="2" align="left" class="bg_table02">&nbsp;</td>
				</tr>
				<tr align="center">
					<td width="25%" height="24" align="right" class="bg_table02">联系人：</td>
					<td width="23%" class="bg_table02" align="left"><s:textfield name="am.linkman"
						size="15" /> </td>
					<td colspan="2" align="left" class="bg_table02">到齐通知 <input
					onclick="document.forms(0).idstate2.checked=false"	name="idstate1" value="1" type="checkbox"<s:if test="'view'.equals(action)">disabled</s:if>> 分批通知 <input
					onclick="document.forms(0).idstate1.checked=false"	type="checkbox" name="idstate2" value="2"<s:if test="'view'.equals(action)">disabled</s:if>></td>
				</tr>


				<tr align="center">
					<td height="29" align="right" class="bg_table02">申购内容：</td>
					<td class="bg_table02" align="left" colspan="3"><s:textarea
						name="am.applyContent" cols="60" rows="5"></s:textarea></td>
				</tr>

				<tr align="center">
					<td height="29" align="right" class="bg_table02">备注：</td>
					<td class="bg_table02" align="left" colspan="3"><s:textarea
						name="am.remark" cols="40" rows="5"></s:textarea></td>
				</tr>

				<tr align="center">
					<td class="bg_table02" width="25%" align="right">&nbsp;</td>
					<td class="bg_table02" width="23%" align="left">&nbsp;</td>
					<td width="8%" align="right" class="bg_table02">&nbsp;</td>
					<td width="44%" align="left" class="bg_table02">申购日期:<s:date name="am.applyDate" format="yyyy-MM-dd"/></td>
				</tr>
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="right" colspan="2"><input class="button01"
									type="button" name="gonext" value="保    存"
									onclick="checkSubmit3();" />
								</td>
								
								<td align="right" colspan="2"><input class="button01"
									type="button" name="gonext" value="确认提交"
									onclick="checkSubmit4();" />
								</td>
								
								<td align="right" colspan="2"><input class="button01"
									type="reset" name="gonext" value="重    填" /></td>
							</tr>
						</tfoot>
					</table>
					</td>
				</tr>
			</table>
			</s:form>
</body>
</html>
