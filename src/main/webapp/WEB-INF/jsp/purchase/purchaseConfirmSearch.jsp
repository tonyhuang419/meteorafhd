<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">
</script>
<title>申购采购查询页面</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<style>
table#checkInfo tr:hover {
	background: lightblue;
	color: blue;
}
</style>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
      <s:form action="purchaseConfirmSearch" target="rightFrame" theme="simple">
      <s:hidden name="resetCondition" value="true"/>
           <table height="40%" border=0 cellpadding=1 cellspacing=0>
	<tr>
		<td colspan="2" align="center" class="bg_table01" height="0.5"><img
			src="./../images/temp.gif" width="1" height="1"></td>
	</tr>



   <tr class="bg_table02">
			      <td align="center" nowrap>组别：</td>
		          <td class="bg_table02" align="left" >
            <s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value);clean()" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
   </td>
      </tr>
      <tr class="bg_table02">
			      <td align="center" nowrap>销售员：</td>
		          <td class="bg_table02" align="left" >
           <s:select name="expId" onchange="getClientOfEmployee(this.value);clean()" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select>
 </td>
      </tr>
      <tr class="bg_table02">

		
		<td class="bg_table02" align="right">客户：</td>
					<td class="bg_table02"  align="left" >			
		<input type="text" id="clientName" name="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
		<s:select name="id" list="yxClientCodeList" cssStyle="margin-left:-150px;width:168px;" listKey="id" listValue="name" emptyOption="true" onchange="clientChange(this)"></s:select>
		</span>
		<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');">
		<s:hidden name="clientId" id="clientId"></s:hidden>
		</td>
      </tr>
     


      
	<tr class="bg_table02">
	              <td align="center" nowrap class="bg_table02"><div align="right">申购类型：</div></td>
	              <td nowrap><select name="select5">
	              <option value=""></option>
                    <option value="0">工程类</option>
                    <option value="1">集成类</option>
                  </select></td>
  </tr>

		<tr class="bg_table02">
			<td rowspan="2" align="center" nowrap>
			<div align="right">申购日期：</div>
			<div align="right"></div>
			</td>
			<td nowrap>从 <input type="text" id="str8" name="str8"
				readonly="readonly" onClick="javascript:ShowCalendar(this.id)"
				size="12" /> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('str8')" align=absMiddle alt="" />
			</td>
		</tr>
		<tr class="bg_table02">
			<td nowrap>
			<div align="left">至 <input type="text" id="str9" name="str9"
				readonly="readonly" onClick="javascript:ShowCalendar(this.id)"
				size="12" /> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('str9')" align=absMiddle alt="" />
			</td>
		</tr>
	
	<tr class="bg_table02">
		<td colspan="2" nowrap class="bg_table04">
			<div align="center"><input type="button" value="查    询"
			 onclick="check();"	class="button02"></div>
			</td>
	</tr>
</table>    
      </s:form>
</body>
</html>
<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).groupId.value);


  function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.purchaseConfirmSearch.expId,"id","name",{value:"",text:"    "});
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
	function getClientOfEmployee(employeeId){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.purchaseConfirmSearch.id,"id","name",{value:"",text:"    "});
	}
	function clean()
	{
		document.getElementById("clientName").value="";
	}
function check(){
	
	if(!validate()){
	document.forms(0).submit();
	}
}
function validate()
	{
		var ev = new Validator();
        with(purchaseConfirmSearch){
 		 ev.test("notblank", "申购开始时间不能大于结束时间", checkTime($('str8').value,$('str9').value));
		   }
		   ev.writeErrors(errorsFrame, "errorsFrame");
		if (ev.size() > 0) {
			
			return true;
		}
	}	
	
	
	
	
</script>
