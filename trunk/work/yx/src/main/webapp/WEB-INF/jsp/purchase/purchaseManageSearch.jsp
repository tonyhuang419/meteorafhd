<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<html>
<head>

<title>申购采购查询页面</title>
</head>
<body leftmargin="0">
      <s:form action="purchaseManagerSearch" target="rightFrame" theme="simple">
      <s:hidden name="resetCondition" value="true"/>
            <table width="100%" class="bg_table02">
	<tr>
		<td colspan="2" align="center" class="bg_table01" height="0.5"><img
			src="./../images/temp.gif" width="1" height="1"></td>
	</tr>

   <tr class="bg_table02">
			      <td align="right" nowrap>组别：</td>
		          <td class="bg_table02" align="left" >
            <s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value);clean()" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
   </td>
      </tr>
      <tr class="bg_table02">
			      <td align="right" nowrap>销售员：</td>
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
	              <td align="right" nowrap class="bg_table02"><div align="right">申购状态：</div></td>
	              <td nowrap><select name="select6">
	                <option value="">--全部--</option>
                    <option value="0">未签</option>
                    <option value="1">已签</option>
                  </select></td>
  </tr>

      
	<tr class="bg_table02">
	              <td align="right" nowrap class="bg_table02"><div align="right">申购类型：</div></td>
	              <td nowrap><s:select name="select5"  list="typeManageService.getYXTypeManage(1020)"  
	               listKey="typeSmall" listValue="typeName" headerKey="" headerValue="--全部--"  ></s:select> </td>
  </tr>

	            <tr class="bg_table02">
	              <td align="center" nowrap class="bg_table02">申购单状态：</td>
	              <td nowrap><select name="select2">
	              <option value="">--全部--</option>
                    <option value="0">草稿</option>
                    <option value="1">待确认</option>
                    <option value="2">确认通过</option>
                    <option value="3">确认退回</option>
                  </select></td>
  </tr>
		<tr class="bg_table02">
			<td rowspan="2" align="right" nowrap>
			<div align="right">申购日期：</div>
			<div align="right"></div>
			</td>
			<td nowrap>从 <input type="text" id="str8" name="str8"
				size="12" /> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('str8')" align=absMiddle alt="" />
			</td>
		</tr>
		<tr class="bg_table02">
			<td nowrap>
			<div align="left">至 <input type="text" id="str9" name="str9"
				size="12" /> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('str9')" align=absMiddle alt="" />
			</td>
		</tr>
	<tr class="bg_table02">
                  <td align="right" nowrap class="bg_table02">出库状态：</td>
                  <td nowrap><select name="statc">
                     <option value="">--全部--</option>
                  	<option value="0">否</option>
                  	<option value="1">是</option></select></td>
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
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.purchaseManagerSearch.expId,"id","name",{value:"",text:"    "});
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
		purchaseManagerSearch.id.value="";
	}
	function getClientOfEmployee(employeeId){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.purchaseManagerSearch.id,"id","name",{value:"",text:"    "});
	}
	function clean()
	{
		document.getElementById("clientName").value="";
		document.getElementById("clientId").value="";
	}
	
function check(){

	document.forms(0).submit();

}
function validate()
	{
		var ev = new Validator();
        with(purchaseManagerSearch){
 			 ev.test("notblank", "申购开始时间不能大于结束时间", checkTime($('str8').value,$('str9').value));
 			 ev.test("dateYX","申购开始日期格式不正确!",$("am.estimateDate").value);
 			 ev.test("dateYX","申购结束日期格式不正确!",$("am.estimateDate").value);
		   }
		   ev.writeErrors(errorsFrame, "errorsFrame");
		if (ev.size() > 0) {
			
			return true;
		}
	}	
	
</script>
