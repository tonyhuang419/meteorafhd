<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">

<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
	    clearCus();
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.searchContractOkList.expId,"id","name",{value:"",text:"    "});
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
	    clearCus();
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetClientOfEmployee&employeeId="+employeeId,document.searchContractOkList.clientSelectId,"id","name",{value:"",text:"    "});
	}
	function searchSubmit(){
	   document.forms(0).submit();
	}
	function clearCus(){
	  document.forms(0).clientName.value="";
	  document.forms(0).clientId.value="";
	}
</script>
</head>
<body leftmargin="0">
<s:form name="searchContractOkList" action="searchContractOkList" target="content" theme="simple">

<table width="100%" class="bg_table02">
  <tr>
    <td colspan="2" align="right" class="bg_table01"  height="0.5"><img src="/yx/commons/images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr class="bg_table02">
    <td width="30%"><div align="right">小组：</div></td>
    <td width="70%"><div align="left">
        <s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
      </div></td>
  </tr>
  <tr class="bg_table02">
    <td><div align="right">销售员：</div></td>
    <td width="70%"><div align="left">
        <s:select name="expId" onchange="getClientOfEmployee(this.value)" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select>
      </div></td>
  </tr>
  <tr class="bg_table02">
    <td><div align="right">客户名称：</div></td>
    <td class="bg_table02"  align="left" >
					<input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="clientSelectId" list="yxClientCodeList" cssStyle="margin-left:-150px;width:168px;" emptyOption="true" listKey="id" listValue="name" onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
<%--					<s:hidden name="customerId" id="clientId"></s:hidden>--%>
					<input type="hidden" name="customerId" id="clientId">
	</td>
  </tr>
  <tr class="bg_table02">
    <td><div align="right">合同性质：</div></td>
    <td><div align="left">
        <select name="conType">
          <option value="">全部</option>
          
          <s:iterator value="contractTypeList" id="typeManager">
           <option value="<s:property value="#typeManager.typeSmall"/>"> <s:property value="#typeManager.typeName"/> </option>
          </s:iterator>
        </select>
        <br>
      </div></td>
  </tr>
  <td><div align="right">合同状态：</div></td>
    <td><div align="left">
        <select name="conState">
          <option value="">选择 </option>
          <option value="1">待确认 </option>
          <option value="3">预合同 </option>
          <option value="4">正式合同 </option>
          
        </select>
        <br>
      </div></td>
  </tr>
  <tr class="bg_table02">
    <td><div align="right">合同金额：</div></td>
    <td><div align="left">大于：
        <input name="minMoney" type="text" id="textfield" size="10" maxlength="20">
        <br>
        小于：
        <input name="maxMoney" type="text" id="textfield" size="10" maxlength="20">
      </div></td>
  </tr>
  <tr class="bg_table02">
    <td><div align="right">签订日期：</div></td>
    <td><div align="left">从：
        <input id="minConSignDate" style="WIDTH: 90px" maxlength=10 name="minConSignDate" readonly="readonly" value="" onclick="javascript:ShowCalendar(this.id)">
        <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('minConSignDate')" align="absMiddle" alt=""  /> <br>
        至：
        <input id=maxConSignDate style="WIDTH: 90px" maxlength=10 name="maxConSignDate" readonly="readonly" value="" onclick="javascript:ShowCalendar(this.id)">
         <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('maxConSignDate')" align="absMiddle" alt=""  /></div></td>
  </tr>
  <%--<tr class="bg_table02">
    <td><div align="right">合同分类：</div></td>
    <td><div align="left">
      <select name="select4" id="typex">
        <option>工程类</option>
        <option>集成类</option>
            </select>
    </div></td>
  </tr>
  <tr class="bg_table02">
    <td><div align="right">合同状态：</div></td>
    <td><select name="select2">
      <option>全部</option>
      <option>待确认</option>
      <option>预合同</option>
      <option>正式合同</option>
    </select></td>
  </tr>
  --%><tr class="bg_table04">
    <td colspan="2">   
      <div align="center">
        <input type="button" name="button2" id="button2" value="查询" class="button01"  onClick="searchSubmit();"/>
        </div></td>
  </tr>
</table>
</s:form>
<script type="text/javascript">
  getEmployeeOfDepartment(document.forms(0).groupId.value);
</script>
</body>

</html>
