<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script language="javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.affirmAssistanceContract.expId,"id","name",{value:"",text:"    "});
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
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.affirmAssistanceContract.clientSelectId,"id","name",{value:"",text:"    "});
	}
</script>	
</head>
<style>
html { overflow-x:hidden;overflow-y:hidden;  }
</style>
<body leftmargin="0">
<s:form theme="simple" action="affirmAssistanceContract" target="rightFrame">
	<s:hidden name="method" value="query"/>
<table height="40%" width="100%"  border=0  cellpadding=1 cellspacing=0>

<tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
       	            <tr class="bg_table02">
			      <td align="center" nowrap>组别：</td>
		          <td class="bg_table02" align="left" >
		        	  <s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
		         </td>
	      </tr>
	      <tr class="bg_table02">
			      <td align="center" nowrap>销售员：</td>
		          <td class="bg_table02" align="left" >
		          <s:select name="expId" onchange="getClientOfEmployee(this.value)" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select>
			</td>
	      </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">供应商：</div></td>
	              <td><s:textfield name="supplyName" size="20"/></td>
  </tr>
	            <tr class="bg_table02">
	              <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">合同金额：</div></td>
	              <td nowrap>最小金额
                    <s:textfield name="min" size="12"/></td>
  </tr>
	            <tr class="bg_table02">
                  <td nowrap>最大金额
                    <s:textfield name="max" size="12"/></td>
  </tr>
                <tr class="bg_table02">
                  <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">合同签订日期：</div></td>
                  <td nowrap><div align="left">
                    从
                    <input type="text" id="sDate" name="sDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sDate')" align=absMiddle alt=""  /></div></td>
                </tr>
                <tr class="bg_table02">
                  <td nowrap>至
                  <input type="text" id="eDate" name="eDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('eDate')" align=absMiddle alt=""  /></td>
                </tr>
            <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    <input type="submit" value="查    询" class="button02">
		      </div></td>
  </tr>
		</table>	
		</s:form>	
<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).groupId.value);
</script>
</body>
</html>
