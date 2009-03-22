<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>项目号搜索</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script language="javascript">
    
	function getEmployeeOfDepartment(departmentCode){
	    cleartext();
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
	    cleartext();
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetClientOfEmployee&employeeId="+employeeId,document.searchContractOkList.clientSelectId,"id","name",{value:"",text:"    "});
	}
	function searchSubmit(){
	   document.forms(0).submit();
	}
    function cleartext(){
       document.getElementById("clientName").value="";
       document.getElementById("clientId").value ="";
    }
</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.STYLE1 {
	font-size: 16px;
	color: #000000;
}
.STYLE2 {
	font-size: 14px
}
-->
</style>
</head>
<body leftmargin="0">
<s:form name="searchContractOkList" action="contractItemUpdateManager" target="content" theme="simple">
    <table width="100% "  class="bg_table02">
      <tr>
    <td colspan="2" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
      <tr>
        <td width="25%" class="bg_table02"><div align="right">小组：</div></td>
  <td class="bg_table02"><label></label>
            <div align="left">
                 <s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
              </select>
          </div></td>
      </tr>
      <tr>
        <td class="bg_table02"><div align="right">销售员：</div></td>
        <td width="*%" class="bg_table02"> <s:select name="expId" onchange="getClientOfEmployee(this.value)" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select></td>
      </tr>
      <tr>
        <td class="bg_table02"><div align="right">客户名称：</div></td>
        <td width="62%" class="bg_table02">
          <input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="clientSelectId" emptyOption="true" list="yxClientCodeList" cssStyle="margin-left:-150px;width:168px;" listKey="id" listValue="name" onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
<%--					<s:hidden name="customerId" id="clientId"></s:hidden>--%>
					<input type="hidden" name="customerId" id="clientId">
		</td>
      </tr>
      <tr>
        <td class="bg_table02"><div align="right">合同性质：</div></td>
        <td class="bg_table02">
          <div align="left">
            <select name="conType">
          <option value="">全部</option>
          
          <s:iterator value="contractTypeList" id="typeManager">
           <option value="<s:property value="#typeManager.typeSmall"/>"> <s:property value="#typeManager.typeName"/> </option>
          </s:iterator>
        </select>
            <br>
          </div></td>
      </tr>
      <tr>
        <td class="bg_table02"><div align="right">工程部门：</div></td>
        <td class="bg_table02"><div align="left">
          <div align="left">
            <select name="projectDept">
             <option value="">所有</option>
             <s:iterator value="projectDeptTypeList" id="typeManager">
             <option value="<s:property value="#typeManager.typeSmall"/>"> <s:property value="#typeManager.typeName"/> </option>
            </s:iterator>
            </select>
            <br>
          </div>
        </div></td>
      </tr>
      <%--<tr>
        <td class="bg_table02"><div align="right">项目号录入状态：</div></td>
        <td class="bg_table02"><select name="projectNoState">
          <option value="">全部</option>
          <option value="0">未录入</option>
          <option value="1">已录入</option>
        </select></td>
      </tr>
      --%><tr class="bg_table04">
        <td colspan="2">          <div align="center">
          <input type="button" name="button2" id="button2" value="查询" class="button01"  onclick="searchSubmit();"/>        
        </div></td>
      </tr>
    </table>
  </div>
  <br>
</div>
</s:form>
<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).groupId.value);
</script>
</body>
</html>
