<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<script
	src="<s:url value="/commons/scripts/mootools-release-1[1].11.js"/>"
	type="text/javascript"></script>
<title><s:if test="typeManageList[0].id!=null">修改自有产品管理</s:if><s:else>新增自有产品管理</s:else></title>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<body>
<s:form action="selfProduction" theme="simple">
	<s:hidden name="method" value="savePro" />
	<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td valign="top">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td height="3" align="left">当前页面：自有产品管理-&gt;新增自有产品信息</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img
						src="../../images/temp.gif" width="1" height="1"></td>
				</tr>


			</table>
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td class="bg_table02" align="right"><font color="red">* </font>产品名称：</td>
					<td class="bg_table02" align="left"><s:textfield
						name="sp.registerName"></s:textfield></td>
					<td class="bg_table02" align="right"><font color="red">* </font>证书标号：</td>
					<td class="bg_table02" align="left"><s:textfield
						name="sp.certificateNum"></s:textfield></td>
				</tr>
				<tr align="center">	
					<td class="bg_table02" align="right"><font color="red">* </font>登记日期：</td>
					<td class="bg_table02" align="left">
					<div align="left"><input type="text" id="bidDate1"
						name="sp.registerDate" readonly="readonly"
						onClick="javascript:ShowCalendar(this.id)" size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate1')" align=absMiddle
						alt="" /></div>
					</td>
					<td class="bg_table02" align="right"><font color="red">* </font>有效期：</td>
					<td class="bg_table02" align="left">
					<div align="left"><input type="text" id="bidDate2"
						name="sp.validateTime" readonly="readonly"
						onClick="javascript:ShowCalendar(this.id)" size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate2')" align=absMiddle
						alt="" /></div>
					</td>
					<td class="bg_table02" align="right"></td>
					<td class="bg_table02" align="left"></td>
				</tr>
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<Table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="right" colspan="2"><input class="button01"
									type="button" name="" value="保  存" onclick="return check()"/></td>
								<td colspan="2"><input name="save" type="button"
									class="button02" onClick="window.close();" value="关   闭">
							</tr>
						</tfoot>
					</Table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>

<script type="text/javascript">
function check(){	
	if(!validate()){
		document.forms(0).submit();
	}
	return false;
}

function validate(){
       var ev2=new Validator();
       with(selfProduction){  
       ev2.test("notblank","产品名称不能为空",$('sp.registerName').value);       
       ev2.test("notblank","证书标号不能为空",$('sp.certificateNum').value);
       ev2.test("notblank","登记日期不能为空",$('sp.registerDate').value);
       ev2.test("notblank","有效期不能为空",$('sp.validateTime').value);
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}
</script>

</body>
</html>

