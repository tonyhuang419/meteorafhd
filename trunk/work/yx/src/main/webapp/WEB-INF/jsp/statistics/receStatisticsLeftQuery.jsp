<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>收款计划查询条件</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script type="text/javascript" src="/yx/commons/scripts/public.js"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.receStat.saleMan,"id","name",{value:"",text:"    "});
	}
	function selectedClient(clientObj){
		document.receStat.customerName.value = clientObj.clientFullName;
		document.receStat.customer.value = clientObj.clientId;
	}
	function cleanValue(obj){
		if(obj==null||obj.length==0){
			document.receStat.customer.value="";
		}
		
	}
	function validate()
	{
	 	var ev2=new Validator();
	 	ev2.test("dateYX","开始日期输入格式不正确！",$('preReceStartDate').value);
		ev2.test("dateYX","结束日期输入格式不正确！",$('preReceEndDate').value);
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
		var departmentCode=document.receStat.groupId.value;
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.receStat.saleMan,"id","name",{defaultAsync:false,value:"",text:"    "});
		var selectNode=document.receStat.saleMan.options;
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
<s:form action="receStat" theme="simple" target="main_view">
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
		<td align="right" nowrap>计划收款日期：</td>
		<td colspan="3" align="left">
		<s:textfield size="11" name="preReceStartDate" id="preReceStartDate"></s:textfield><img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('preReceStartDate')" align="absMiddle"/> --
		<s:textfield size="11" name="preReceEndDate" id="preReceEndDate"></s:textfield><img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('preReceEndDate')" align="absMiddle"/>
		</td>
	</tr>
	<tr>
		<td align="right">客户：</td>
		<td align="left" nowrap>
		<s:textfield name="customerName" onblur="cleanValue(this.value);"/>
		<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
		<s:hidden name="customer"></s:hidden>
		</td>
		<td align="right" nowrap>收款情况：</td>
		<td align="left" nowrap="nowrap">
			<!--<s:checkboxlist list="#@java.util.TreeMap@{'0':'部分收款','1':'未收款','2':'全部收款'}" name="receSituation"></s:checkboxlist>-->
			<input type="checkbox" name="receSituation0" value="0" <s:if test="receSituation0 != null">checked="checked"</s:if> /> 部分收款
			<input type="checkbox" name="receSituation1" value="1" <s:if test="receSituation1 != null">checked="checked"</s:if> > 未收款
			<input type="checkbox" name="receSituation2" value="2" <s:if test="receSituation2 != null">checked="checked"</s:if> > 全部收款
		</td>
	</tr>
	<tr>
		<td align="right">合同号：</td>
		<td align="left"><s:textfield name="contractNo"></s:textfield></td>
		<td align="right">项目号：</td>
		<td align="left"><s:textfield name="itemNo"></s:textfield></td>
	</tr>
	<tr>
		<td align="right" colspan="4"></td>
	</tr>
	<tr>
		<td align="right" <td align="left" nowrap>查询下6个月：</td>
		<td align="left"><div align="left"><s:checkbox name="nextSix" /></div></td>
		<td align="right"></td>
		<td align="left"></td>
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