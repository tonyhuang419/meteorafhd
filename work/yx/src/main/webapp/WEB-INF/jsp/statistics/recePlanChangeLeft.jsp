<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>收款计划变更查询条件</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script type="text/javascript" src="/yx/commons/scripts/public.js"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.receProjectChangeStat.saleMan,"id","name",{value:"",text:"    "});
	}
	function selectedClient(clientObj){
		document.receProjectChangeStat.customerName.value = clientObj.clientFullName;
		document.receProjectChangeStat.customer.value = clientObj.clientId;
	}
	function cleanValue(obj){
		if(obj==null||obj.length==0){
			document.receProjectChangeStat.customer.value="";
		}
		
	}
	function validate()
	{
	 	var ev2=new Validator();
	 	ev2.test("dateYX","开始日期输入格式不正确！",$('changeDateStart').value);
		ev2.test("dateYX","结束日期输入格式不正确！",$('changeDateEnd').value);
		ev2.writeErrors(errorsFrame, "errorsFrame");
		if(ev2.size()>0){
			return true;
		}
		return false;
	}
	function onSubmit(){
		if(!validate()){
			document.forms(0).submit();
			this.close();
		}
	}
	function onloadMethod(sale)
	{
		
		var departmentCode=document.receProjectChangeStat.groupId.value;
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.receProjectChangeStat.saleMan,"id","name",{defaultAsync:false,value:"",text:"    "});
		var selectNode=document.receProjectChangeStat.saleMan.options;
		for(var k=0;k<selectNode.length;k++){
			if(selectNode[k].value==sale){
				selectNode[k].selected=true;
				break;
			}
		}
	}
</script>
</head>
<body>
<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:form action="receProjectChangeStat" theme="simple" target="main_view">
<s:hidden name="method" value="showMainInfo"></s:hidden>
<s:hidden name="resetCondition" value="true"/>
<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%" class="bg_table02">
	<tr>
		<td align="center" colspan="4">
			<b>查询条件</b>
		</td>
	</tr>
	<tr>
		<td align="right">组别：</td>
		<td align="left"><s:select name="groupId" list="groupList" onchange="getEmployeeOfDepartment(this.value)"
			listKey="departmentCode" listValue="departmentName"></s:select></td>
		<td align="right">销售员：</td>
		<td align="left"><s:select list="empList" name="saleMan" listKey="id" listValue="name" headerValue="" headerKey="" ></s:select>
		</td>
	</tr>
	<tr>
		<td align="right">变更操作日期：</td>
		<td colspan="3" align="left">
		<s:textfield name="changeDateStart" id="changeDateStart"></s:textfield><img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('changeDateStart')" align="absMiddle"/>--
		<s:textfield name="changeDateEnd" id="changeDateEnd"></s:textfield><img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('changeDateEnd')" align="absMiddle"/>
		</td>
	</tr>
	<tr>
		<td align="right">变更次数：</td>
		<td align="left">
			<s:textfield size="3" name="changeCountStart" maxlength="3"/>--<s:textfield size="3" maxlength="3" name="changeCountEnd"/>
		</td>
		<td align="right">变更类型：</td>
		<td align="left"><s:select list="changeTypeList" listKey="typeSmall" listValue="typeName" name="changeType" headerKey="" headerValue="---"></s:select></td>
	</tr>
	<tr>
		<td align="right">客户：</td>
		<td align="left">
		<s:textfield name="customerName" onblur="cleanValue(this.value);"/>
		<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
		<s:hidden name="customer"></s:hidden>
		</td>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td align="right">合同号：</td>
		<td align="left"><s:textfield name="contractNo"></s:textfield></td>
		<td align="right">项目号：</td>
		<td align="left"><s:textfield name="itemNo"></s:textfield></td>
	</tr>
	<tr>
		<td colspan="4" align="center">
		<input type="button" value="查询" class="button01" onclick="onSubmit();"/>
		</td>
	</tr>
</table>
</s:form>
<script type="text/javascript">
	<s:if test="groupId!=10">
		onloadMethod(<s:property value="saleMan"/>);
	</s:if>
</script>
</body>
</html>