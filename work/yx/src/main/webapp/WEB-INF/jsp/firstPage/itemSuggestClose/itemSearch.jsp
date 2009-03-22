<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<html>
<head>
<title>项目建议关闭搜索</title>

<script language="javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.itemSuggestClose.expId,"id","name",{value:"",text:"    "});
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
		itemSuggestClose.clientSelectId.value="";
	}
	function getClientOfEmployee(employeeId){
		//ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.itemSuggestClose.clientSelectId,"id","name",{value:"",text:"    "});
	}
	function clean()
	{
		//document.getElementById("clientName").value="";
	}
</script>

<style type="text/css">

</style>
</head>
<body>

<s:form action="itemSuggestClose" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<s:hidden name="resetCondition" value="true"/>
<table width="100%"  class="bg_table02" >
          <tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
       	  <tr class="bg_table02">
			      <td align="right" nowrap>组别：</td>
		          <td class="bg_table02" align="left" >
		        	  <s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
		         </td>
	      </tr>
	      <tr class="bg_table02">
			      <td align="right" nowrap>销售员：</td>
		          <td class="bg_table02" align="left" >
		          <s:select name="expId" onchange="getClientOfEmployee(this.value);clean()" list="listExp" listKey="id" listValue="name" required="true" headerValue="" ></s:select>
			</td>
	      </tr>
	      <%-- 
		  <tr class="bg_table02">
			      <td class="bg_table02" align="right">客户：</td>
					<td class="bg_table02"  align="left" >
					<input type="text" id="clientName" style="width:130px;height:16px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="clientSelectId" list="yxClientCodeList" emptyOption="true" cssStyle="margin-left:-142px;width:138px;" listKey="cli.id" listValue="cli.name" onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=650');">
					<s:hidden name="customerId" id="clientId"></s:hidden>
					</td>
	      </tr>
	      --%>
		       
                
          <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    <input type="submit" value=" 查  询 "  class="button02">
		 </div></td>
  </tr>
</table>
</s:form>


<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).groupId.value);
</script>

</body>
</html>
