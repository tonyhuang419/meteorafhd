<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>

<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">

</script>
<script src="<s:url value="/commons/scripts/mootools-release-1[1].11.js"/>" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:form action="client">
	<s:hidden name="method" value="updateExp" />
    <s:hidden name="cc.is_active" value="1"></s:hidden>
	<table width="96%" border="1" class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=1 width="100%">
				<tr>
					<td colspan="4" align="center" class="txt_title01">客户联系人操作</td>
				</tr>
				<tr>
					<td colspan="4" align="right" class="bg_table01" height="0.5"><img
						src="./images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr>
					<td class="bg_table02"><s:hidden name="cc.id"></s:hidden></td>
				</tr>
				<tr class="bg_table02">
					<td width="20%" class="bg_table02" align="right">联系人:</td>
					<td width="380" class="bg_table02" align="left"><s:textfield
						name="cc.name" /></td>
					
				</tr>
				<tr>
					<td class="bg_table02"><s:select label="客户性质"
						name="cc.clientNID" list="clientNature" listKey="id" listValue="name"
						required="true" headerValue="cc.name">
					</s:select></td>

					<td class="bg_table02"><s:select label="行业类型"
						name="cc.businessID" list="businessType" listKey="id" listValue="name"
						required="true" headerValue="cc.name">
					</s:select></td>
				</tr>
				<tr class="bg_table02">
					<td class="bg_table02" align="right">客户联系地址</td>
					<td class="bg_table02" align="left"><s:textfield
						name="cc.address" /></td>

				</tr>
				<tr>
					<td class="bg_table02" width="20%" align="right">客户开票名称</td>
					<td class="bg_table02" align="left" colspan="3"><s:textfield
						name="cc.billName" /></td>

				</tr>
				<tr align="center">
					<td width="20%" class="bg_table02" align="right">客户开户银行</td>
					<td class="bg_table02" align="left" colspan="3"><s:textfield
						name="cc.billBank" /></td>

					<td width="20%" class="bg_table02" align="right">开户帐号</td>
					<td class="bg_table02" align="left" colspan="3"><s:textfield
						name="cc.account" /></td>
				</tr>

                
                <tr align="center">
					<td width="20%" class="bg_table02" align="right">税号</td>
					<td class="bg_table02" align="left" colspan="3"><s:textfield
						name="cc.taxNumber" /></td>

					<td width="20%" class="bg_table02" align="right">客户开票地址</td>
					<td class="bg_table02" align="left" colspan="3"><s:textfield
						name="cc.billAdd" /></td>
				</tr>  
				
				 <tr align="center">
					<td width="20%" class="bg_table02" align="right">客户开票电话</td>
					<td class="bg_table02" align="left" colspan="3"><s:textfield
						name="cc.billPhone" /></td>

				
						
					<td class="bg_table02"><s:select label="客户地域"
						name="cc.areaID" list="clientArea" listKey="id" listValue="name"
						required="true" headerValue="cc.name">
					</s:select></td>
				</tr>  
				
				<tr align="center">
					<td colspan="4" class="bg_table03"><input type="submit"
						name="SearchBtn" value="　保   存　" class="button01"
						onClick=""> <input type="reset" name="SearchBtn"
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

<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">
function check(){
	
	if(!validate()){
	document.forms(0).submit();
	}
}
function validate()
	{
		var ev = new Validator();
        with(client){
		    ev.test("notblank", "客户性质不能为空", $('client_cc_clientNID').value);
			ev.test("notblank", "客户地域不能为空", $('client_cc_areaID').value);
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
