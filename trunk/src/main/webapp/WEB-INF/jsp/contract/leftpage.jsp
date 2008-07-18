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
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.contractLeftPage.expId,"id","name",{value:"",text:"    "});
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
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.contractLeftPage.id,"id","name",{value:"",text:"    "});
	}
	function clean()
	{
		document.getElementById("clientName").value="";
	}
</script>
</head>
<body leftmargin="0" style="background-color: #FFFFFF;">
<s:form action="contractLeftPage" theme="simple" target="content">
	<s:hidden name="resetCondition" value="true"></s:hidden>
    <table width="100%"  class="bg_table02">
      <tr>
    <td colspan="2" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
      <tr>
        <td width="30%"><div align="right">小组：</div></td>
        <td><div align="left">
            <s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value);clean()" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
        </div></td>
      </tr>
      <tr>
        <td><div align="right">销售员：</div></td>
        <td><div align="left">
           <s:select name="expId" onchange="getClientOfEmployee(this.value);clean()" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select>
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
      <tr>
        <td><div align="right">合同号：</div></td>
        <td width="70%">        	
        	<s:textfield name="contractId" maxlength="10" />
        </td>
      </tr>
      <tr>
        <td><div align="right">合同性质：</div></td>
        <td class="bg_table02">
        	<s:select name="contractType" emptyOption="true" list="contractTypeList"  listKey="typeSmall" listValue="typeName"></s:select>
        </td>
      </tr>
      <tr>
        <td><div align="right">开工报告</div></td>
        <td>
          <div align="left">
            <select name="start">
              <option value="1">已确认</option>
              <option value="2">未确认</option>
              <option value="3" selected>全部</option>
                        </select>
          </div>        </td>
      </tr>
      <tr>
        <td><div align="right">实物交接</div></td>
        <td><div align="left">
          <select name="physical">
            <option value="1">已确认</option>
            <option value="2">未确认</option>
            <option value="3" selected>全部</option>
          </select>
        </div></td>
      </tr>
      <tr>
        <td><div align="right">竣工验收证书</div></td>
        <td><div align="left">
          <select name="completion">
            <option value="1">已确认</option>
            <option value="2">未确认</option>
            <option value="3" selected>全部</option>
          </select>
        </div></td>
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
getEmployeeOfDepartment(document.forms(0).groupId.value);
</script>
</body>
</html>
