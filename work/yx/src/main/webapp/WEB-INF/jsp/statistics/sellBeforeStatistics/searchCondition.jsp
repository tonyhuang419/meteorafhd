<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<head>
<title>售前合同统计查询条件</title>
<script type="text/javascript">

function onSubmit(){
	document.forms(0).submit();
	this.close();
}

function selectedClient(clientObj){
	document.sellBeforeStat.customerName.value = clientObj.clientFullName;
	document.sellBeforeStat.customer.value = clientObj.clientId;
}
	function updateTraceList(obj){
		var valueX = obj.options[obj.selectedIndex].text;
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetTrackList&trace="+valueX,document.getElementById("itemTraceResultSelect"),"typeSmall","typeName",{defaultAsync:false,value:"",text:"    "});
	}
function saveSaleMan(sale)
	{
		var departmentCode=document.sellBeforeStat.groupId.value;
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.sellBeforeStat.expId,"id","name",{defaultAsync:false,value:"",text:"    "});
		var selectNode=document.sellBeforeStat.expId.options;
		for(var k=0;k<selectNode.length;k++){
			if(selectNode[k].value==sale){
				selectNode[k].selected=true;
				break;
			}
		}
	}
function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.sellBeforeStat.expId,"id","name",{value:"",text:"    ",defaultAsync:false});
	}
</script>
<body>
<s:form action="sellBeforeStat" theme="simple" target="main_view" >
<s:hidden name="method" value="queryResult"/>
<s:hidden name="resetCondition" value="true"/>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%" class="bg_table02">
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
	</table>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%" class="bg_table02">
	<tr>
		<td align="right">组别：</td>
		<td align="left"><s:select name="groupId" list="groupList" onchange="getEmployeeOfDepartment(this.value)"
			listKey="departmentCode" listValue="departmentName"></s:select></td>
		<td align="right">销售员：</td>
		<td align="left"><s:select list="listExp" name="expId" listKey="id" listValue="name" headerValue="" headerKey="" ></s:select>
		</td>
	</tr>
		<tr class="bg_table02">
			<td align="right">客户：</td> 
			<td align="left">
				<s:textfield name="customerName" size="17" onchange="cleanCusId();"/> 
				<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');">
				<s:hidden name="customer" id="customerIdField"></s:hidden>
			</td>
			<td align="right">客户性质：</td> 
			<td align="left">
				<s:select name="clientNId" headerKey="" headerValue="--请选择--"
					list="clientNList" listKey="typeSmall" listValue="typeName" >
				</s:select>	
			</td>
	   </tr>
	   	<tr class="bg_table02">
		    <td align="right" nowrap>工程责任部门：</td>
		   <td class="bg_table02" align="left">
				<s:select name="dutyDepartmentIdSS" headerKey="" headerValue="--请选择--"
					list="dutyDepartmentIdList" listKey="typeSmall" listValue="typeName" onchange="changeSelectValue(this,'dutyDepartmentId');">
				</s:select>	
				<s:hidden name="dutyDepartmentId"/>			
			</td>
		    <td align="right" nowrap></td>
		   <td class="bg_table02" align="left">
			</td>
	   </tr>
	   <tr class="bg_table02">
		    <td align="right" nowrap>甲方工程编号：</td>
		   <td class="bg_table02" align="left">
				<s:textfield name="partyAProId"></s:textfield>		
			</td>
		    <td align="right" nowrap>重点项目：</td>
		   <td class="bg_table02" align="left">
		   		<s:select name="importantItem" list="#@java.util.TreeMap@{0:'全部',1:'Y',2:'N'}"></s:select>
			</td>
	   </tr>
	   <tr class="bg_table02">
	   		<td align="right">跟踪状态：</td>
	   		<td class="bg_table02" align="left">
	   		<s:select  name="projectStateFollowIdSS" headerKey="" headerValue="--请选择--" list="projectStateFollowList" 
	   		listKey="typeSmall" listValue="typeName"  onchange="changeSelectValue(this,'projectStateFollowId');"></s:select>
	   		<s:hidden name="projectStateFollowId"/>			
	   		</td>	
	   		<td align="right" nowrap>跟踪结果：</td>
			<td class="bg_table02" align="left">
					<s:select name="projectStateSelect" headerKey="" headerValue=" " id="projectStateSelect" list="#@java.util.TreeMap@{1:'on',2:'off'}"
					 onchange="updateTraceList(this)"></s:select>
					<s:select  name="itemTraceResultSS" headerKey="" headerValue=" " list="itemTrackList" listKey="typeSmall" 
					listValue="typeName" required="true"  id="itemTraceResultSelect"></s:select>
			</td>
	   </tr>
	   <tr>
	   		<td align="right" nowrap>预计金额：</td>
		     <td align="left" nowrap colspan="3" >
		     <s:textfield name="projectSumGt"  size="15" onblur="formatInputNumber(this)" />
		     --&nbsp;<s:textfield name="projectSumLt" size="15" onblur="formatInputNumber(this)"/>
			  </td>	 
	   </tr>
	   <tr>
	   		<td align="right" nowrap>预计合同签订日期：</td>
		     <td colspan="3" align="left">
				<s:textfield size="11" name="estimateSignDateStart" id="estimateSignDateStart"></s:textfield>
				<img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('estimateSignDateStart')" align="absMiddle"/> --
				<s:textfield size="11" name="estimateSignDateEnd" id="estimateSignDateEnd"></s:textfield>
				<img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('estimateSignDateEnd')" align="absMiddle"/>
			</td>
	   </tr>
	   
	   <tr>
	   		<td align="right" nowrap>最后更新时间：</td>
		    <td align="left">
		     <s:textfield name="dayAfter"  size="2"/>天内
			  </td>	 
	   </tr>

	</table>
	<table  border=0 cellpadding=1 cellspacing=1 width="100%" class="bg_table04" height="37">
		<tr><td align="center">
		<input type="button" value="查    询" class="button01" onclick="onSubmit();"/>
		</td></tr>
	</table>
</s:form>
</body>
<script type="text/javascript">
function changeSelectValue(obj,field){
	$(field).value = obj[obj.selectedIndex].value;
}
function cleanCusId(){
	$('customerIdField').value="";
}
<s:if test="groupId!=10">
		saveSaleMan(<s:property value="expId"/>);
	</s:if>
updateTraceList($('projectStateSelect'));
</script>

</html>