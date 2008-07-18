<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
<html>
<head>
<title>正式合同管理搜索栏</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
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
<script language="javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.formalContractManageQuery.saleId,"id","name",{value:"",text:"    "});
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
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.formalContractManageQuery.clientSelectId,"id","name",{value:"",text:"    "});
	}
		function clean()
	{
		document.getElementById("clientName").value="";
	}
</script>
<body>
<s:form	name="formalContractManageQuery"	action="yx/contract/formalContractManage/formalContractManageQuery.action"	target="content" theme="simple" >
  <table width="100%" class="bg_table02">
    <tr>
      <td colspan="2" align="right" height="2"></td>
    </tr>
    <tr class="bg_table02">
      <td width="29%" align="center" nowrap><div align="right">组别：</div></td>
      <td width="71%" align="left" class="bg_table02" ><s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>      </td>
    </tr>
    <tr class="bg_table02">
      <td align="center" nowrap><div align="right">销售员：</div></td>
      <td class="bg_table02" align="left" >
      <s:select name="saleId" onchange="getClientOfEmployee(this.value);clean();" list="listSale" listKey="id" listValue="name" required="true" headerValue="--全部--" headerKey=""></s:select>      </td>
    </tr>
    <tr class="bg_table02">
      <td class="bg_table02" align="right">客户：</td>
      <td class="bg_table02"  align="left" ><input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/>
        <span style="width:18px;border:0px solid red;">
        <s:select name="clientSelectId" list="yxClientCodeList" emptyOption="true" cssStyle="margin-left:-150px;width:168px;" listKey="id" listValue="name" onchange="clientChange(this)"   > </s:select>
        </span>
        <input type="button" value="…"	onclick="javascript:window.open('../../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
      <s:hidden name="customerId" id="clientId"></s:hidden>      </td></tr>
    
    
    <tr>
      <td><div align="right"> 合同号： </div></td>
      <td><div align="left"><s:textfield name="conSn" maxlength="15" ></s:textfield></div></td> 
    </tr>
      
       <tr>
         <td class="bg_table02"><div align="right">预决算信息：</div></td>
          <td><div align="left">
         <s:select list="#@java.util.TreeMap@{0:'非预决算',1:'预决算'}"   name="finalAccount"  headerKey=" " headerValue="--全部--"  ></s:select>
        </div></td>
	 </tr>
    
    
     <tr>
        <td class="bg_table02" ><div align="right">合同类型：</div></td>
         <td><div align="left">
			<s:select name="contractType"  list="typeManageService.getYXTypeManage(1020)"   listKey="typeSmall" listValue="typeName"  
            headerKey=" " headerValue="--全部--"  ></s:select> 
          </div></td>  
       </tr>
     
    <tr>
      <td><div align="right"> 合同性质： </div></td>
      <td><div align="left">
         <s:select name="conType" list="typeManageService.getYXTypeManage(1019)" listKey="typeSmall" listValue="typeName" required="true" 
            headerKey=" " headerValue="--全部--"  ></s:select>
        </div></td>
    </tr>
                   
    <tr>
      <td rowspan="2"><div align="right"> 签订日期： </div></td>
      <td nowrap>从 <input type="text" id="start_date" name="start_date"
				readonly="readonly" onClick="javascript:ShowCalendar(this.id)"
				size="12" value=" " /> <img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('start_date')">
        <div align="left"></div></td>
    </tr>
    <tr>
      <td nowrap>至 <input type="text" id="end_date" name="end_date"
				readonly="readonly" onClick="javascript:ShowCalendar(this.id)"
				size="12" value=" "/> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('end_date')" style=""/> </td>
    </tr>
    <tr>
      <td><div align="right"> 合同状态： </div></td>
      <td><select name="conState">
          <option> --全部-- </option>
          <option> 正式合同 </option>
         <!--  <option> 变更保存 </option>   -->
          <option> 变更待确认 </option>
          <option> 变更退回 </option>
           <option> 建议关闭 </option>
          <option> 已关闭 </option>
        </select>      </td>
    </tr>
    <tr class="bg_table04">
      <td colspan="2"><div align="center">
          <s:submit id="formalContractQuerty" value="查询"	cssClass="button01" />
        </div></td>
    </tr>
  </table>
</s:form>
<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).groupId.value);
</script>
</body>
</html>
