<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>售前合同搜索</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.selectSellBefore.expId,"id","name",{value:"",text:"    "});
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
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.selectSellBefore.clientSelectId,"id","name",{value:"",text:"    "});
	}
	function clean()
	{
		document.getElementById("clientName").value="";
	}
</script>
<body leftmargin="0">
  <iframe frameborder="0" id="main" name="main"  src="/yx/searchClient/searchClientQuery.action" style="HEIGHT: 50%; VISIBILITY: inherit; WIDTH:100%; Z-INDEX: 2" scrolling="auto"></iframe>
<s:form action="selectSellBefore" target="rightFrame" theme="simple">
<s:hidden name="resetCondition" value="true"/>
<table height="40%"  border=0  cellpadding=1 cellspacing=0>
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
		          <s:select name="expId" onchange="getClientOfEmployee(this.value);clean()" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select>
			</td>
	      </tr>
		  <tr class="bg_table02">
			      <td class="bg_table02" align="right">客户：</td>
					<td class="bg_table02"  align="left" >
					<input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="clientSelectId" list="yxClientCodeList" emptyOption="true" cssStyle="margin-left:-150px;width:168px;" listKey="id" listValue="name" onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
					<s:hidden name="customerId" id="clientId"></s:hidden>
					</td>
	      </tr>
		        <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">项目名称：</div></td>
		          <td  colspan="2"  nowrap><div align="left">
		 				 <s:textfield name="projectName"></s:textfield>
	              </div></td>
          </tr>
	            <tr class="bg_table02">
	              <td align="center" nowrap class="bg_table02"><div align="right">工程部门：</div></td>
	              <td colspan="2" nowrap><div align="left">
	                <s:select  name="dutyDepartmentId" list="dutyDepartmentIdList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="" emptyOption="true">
						</s:select>
	              </div></td>
          </tr>
          <tr class="bg_table02">
			      <td align="center" nowrap>结束标记：</td>
		          <td class="bg_table02" align="left" >
		          <s:select  name="state" list="{'on','off'}"  required="true" headerValue="" emptyOption="true">
				 </s:select>
		         </td>
	      </tr>
                
            <tr class="bg_table02">
			  <td colspan="3" nowrap class="bg_table04"><div align="center">
			    <input type="submit" value=" 查  询 "  class="button02">
		      </div></td>
  </tr>
</table>
</s:form>		
<script type="text/javascript">
getEmployeeOfDepartment($('groupId').value);
</script>
</body>
</html>