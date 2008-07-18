<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同确认</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.programEconomyManageQuery.sid,"id","name",{value:"",text:"    "});
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
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.programEconomyManageQuery.id,"id","name",{value:"",text:"    "});
	}
	function clean()
	{
		document.getElementById("clientName").value="";
	}
	function validate6(obj) {
    var value = obj.value;
    var input = event.keyCode;
    if(input > "57"&&input < "189" || input< "48") {
         alert("Error Format");
        document.forms(0).money1.value="";
     }
}
function validate7(obj) {
    var value = obj.value;
    var input = event.keyCode;
    if(input > "57"&&input < "189" || input< "48") {
         alert("Error Format");
        document.forms(0).money2.value="";
     }
}
</script>
</head>
<body leftmargin="0"  style="background-color: #FFFFFF;">
<s:form action="programEconomyManageQuery" theme="simple" target="content">
	<s:hidden name="resetCondition" value="true"></s:hidden>
    <table width="100%"  class="bg_table02">
      <tr>
    <td colspan="2" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
      <tr>
        <td width="30%"><div align="right">小组：</div></td>
        <td><div align="left">
            <s:select  name="str10" onchange="getEmployeeOfDepartment(this.value);clean()" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
        </div></td>
      </tr>
      <tr>
        <td><div align="right">销售员：</div></td>
        <td><div align="left">
           <s:select name="sid" onchange="getClientOfEmployee(this.value);clean()" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select>
            </div></td>
      </tr>
      <tr>
        <td><div align="right">客户名称：</div></td>
		<td class="bg_table02"  align="left" >			
		<input type="text" id="clientName" name="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
		<s:select name="id" list="yxClientCodeList" cssStyle="margin-left:-150px;width:168px;" listKey="id" listValue="name" emptyOption="true" onchange="clientChange(this)"></s:select>
		</span>
		<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');">
		<s:hidden name="clientId" id="clientId"></s:hidden>
		</td>
      </tr>

      
	<tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">执行阶段：</div></td>
	              <td nowrap>
	             <s:select name="str8" list="sectionList" listKey="typeSmall" emptyOption="true"
				listValue="typeName" required="true" headerValue="请选择">
			</s:select></td>
              <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">申请状态：</div></td>
	              <td nowrap><select name="str9">
                      <option value=""></option>
                      <option value="3">确认通过</option>
                      <option value="4">确认退回</option>
                  </select></td>
  </tr>
		
		<tr class="bg_table02">
			<td align="center" nowrap class="bg_table02">
			<div align="right">工程编号：</div>
			</td>
			<td nowrap><input type="text" name="str7" size="15"></td>
		</tr>


		<tr class="bg_table02">
			<td rowspan="2" align="center" nowrap>
			<div align="right">设计委托进度：</div>
			<div align="right"></div>
			</td>
			<td nowrap>从 <input type="text" id="str3" name="str3"
				readonly="readonly" onClick="javascript:ShowCalendar(this.id)"
				size="12" /> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('str3')" align=absMiddle alt="" />
			</td>
		</tr>
		<tr class="bg_table02">
			<td nowrap>
			<div align="left">至 <input type="text" id="str4" name="str4"
				readonly="readonly" onClick="javascript:ShowCalendar(this.id)"
				size="12" /> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('str4')" align=absMiddle alt="" />
			</td>
		</tr>
		<tr class="bg_table02">
			<td rowspan="2" align="center" nowrap class="bg_table02">
			<div align="right">投资估概算：</div>
			</td>
			<td nowrap>最小金额<input type="text" name="money1" size="12" onKeyUp="validate6(this)"></td>
		</tr>
		<tr class="bg_table02">
			<td nowrap>
			<div align="left">最大金额<input type="text" name="money2"
				size="12" onKeyUp="validate7(this)"></div>
			</td>
		</tr>
      <tr class="bg_table04">
        <td colspan="2">
          <div align="center">
            <input type="submit" name="button2" id="button2" value="查询" class="button01"/>
          </div></td>
      </tr>
</table>
</s:form>
<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).str10.value);
</script>
</body>
</html>
