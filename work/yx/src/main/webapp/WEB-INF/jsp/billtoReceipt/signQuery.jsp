<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<html>
<head>
<title>签收管理查询</title>
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
<s:form action="signManageQuery" target="rightFrame" theme="simple">
   <s:hidden name="method" value="queryList" ></s:hidden>
   <s:hidden name="resetCondition" value="true"></s:hidden>
<div id="find" align="left">
<div id="cname">
<table width="100%" class="bg_table02">
	<tr>
		<td colspan="2" align="right" class="bg_table01" height="1"><img
			src="./../images/temp.gif" width="1" height="1"></td>
	</tr>
    <tr class="bg_table02">
			      <td align="right" nowrap>组别：</td>
		          <td class="bg_table02" align="left">
		        	  <s:select  name="groupId" id="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName"></s:select>
		         </td>
          </tr>
		  <tr class="bg_table02">
		         <td align="right" nowrap>销售员：</td>
		          <td class="bg_table02" align="left" >
		          <select name="saleMan"  ></select>
			</td>
	      </tr>
	      <tr class="bg_table02">
			    <td align="right" nowrap>合同号：</td>
		          <td class="bg_table02" align="left" >
		         	<s:textfield name="contractNo" />
				  </td>
		  </tr>
		  <tr class="bg_table02">
				  <td align="right" nowrap>项目号：</td>
		          <td class="bg_table02" align="left" >
		         	<s:textfield name="itemNo" />
				  </td>
	      </tr>
	   <tr>
		<td width="25%">
		<div align="right">客户名称：</div>
		</td>
					<td class="bg_table02"  align="left" >
					<input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="clientSelectId" list="yxClientCodeList" emptyOption="true" cssStyle="margin-left:-150px;width:168px;" listKey="cli.id" listValue="cli.name" onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');">
					<s:hidden name="clientId" id="clientId"></s:hidden>
			</td>
	</tr>
	<tr class="bg_table02">
		<td>
		<div align="right">发票号：</div>
		</td>
		<td>
		<div align="left"><input name="billNumber" type="text"
			id="textfield2" size="10" maxlength="10"> <br>
		</div>
		</td>
	</tr>
	<tr>
		<td>
		<div align="right">签收状态：</div>
		</td>
		<td>
		<div align="left"><select name="signState">
		    <option value="">全部</option>			
			<option value="1">已签收</option>
			<option value="0">未签收</option>
		</select></div>
		</td>
	</tr>
	<tr class="bg_table04">
		<td colspan="2">
		<div align="center"><input type="submit" name="button"
			id="button2" value="查询" class="button01" 
			 /></div>
		</td>
	</tr>
</table>
</div>
<br>
</div>
</s:form>
</body>
</html>
<script language="javascript">
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
	
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.forms(0).saleMan,"id","name",{value:"",text:"    "});
	}
	getEmployeeOfDepartment($('groupId').value);
</script>