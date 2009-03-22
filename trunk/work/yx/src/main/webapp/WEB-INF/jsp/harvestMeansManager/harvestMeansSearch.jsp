<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">
</script>
<title>收款管理查询</title>
<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<body leftmargin="0">
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
      <s:form action="harvestMeansSearch" target="rightFrame" theme="simple">
      <s:hidden name="resetCondition" value="true"/>
<table width="100%" class="bg_table02">
  <tr>
    <td colspan="2" align="right" class="bg_table01"  height="0.5"><img src="/yx/commons/images/temp.gif" width="1" height="1"></td>
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
		          <s:select name="sid" onchange="getClientOfEmployee(this.value);clean()" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select>
			</td>
	      </tr>
		 
		<tr class="bg_table02">

		
		<td class="bg_table02" align="right">客户：</td>
					<td class="bg_table02"  align="left" >
					
					<input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="id" list="listCli" cssStyle="margin-left:-150px;width:168px;" listKey="id" listValue="name" emptyOption="true"  onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');">
					<s:hidden name="clientId" id="clientId"></s:hidden>
					</td>
		</tr>


	<tr class="bg_table02">
		<td align=""right"" nowrap class="bg_table02">
		<div align="right">发票号：</div>
		 <td nowrap> <s:textfield name="invoiceNo"></s:textfield></td>
	</tr>
	<tr class="bg_table02">
		<td align="center" nowrap class="bg_table02">
		<div align="right">项目号：</div>
		 <td nowrap> <s:textfield name="projectNo"></s:textfield></td>
	</tr>
	<tr class="bg_table02">
		<td align=""right"" nowrap class="bg_table02">
		<div align="right">开票类型：</div>
		<td nowrap>
	         <s:select name="billType" list="billTypeName" listKey="typeSmall" emptyOption="true"
				listValue="typeName" required="true" headerValue="请选择">
			</s:select></td>
		</tr>
	<tr class="bg_table02">
		<td rowspan="2" align="center" nowrap>
			<div align="right">开票日期：</div>
			<div align="right"></div>
			</td>
			<td nowrap>从 <input type="text" id="sTime" name="sTime"
				size="10" /> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('sTime')" align=absMiddle alt="" />
			</td>
		</tr>
		<tr class="bg_table02">
			<td nowrap>
			<div align="left">至 <input type="text" id="lTime" name="lTime"
				size="10" /> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('lTime')" align=absMiddle alt="" />
			</td>
	</tr>
	<tr class="bg_table02">
			<td align="center" nowrap class="bg_table02">
		<div align="right">收款状态：</div>
				<td nowrap><select name="receState">
	              <option Value=''> 全部</option> 
                      <option Value="0">全到款</option> 
                       <option Value="1">部分到款</option> 
                      <option Value="2">未到款</option>
                  </select></td>
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
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.harvestMeansSearch.sid,"id","name",{value:"",text:"    "});
	}
    
	
	function clientChange(selObj){
		document.getElementById("clientName").value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
		harvestMeansSearch.id.value="";
	}
	 
	
	function getClientOfEmployee(employeeId){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.harvestMeansSearch.id,"id","name",{value:"",text:"    "});
	}
		function clean()
	{
		document.getElementById("clientName").value="";
		document.getElementById("clientId").value="";
	}
	
function check(){
	
	if(!validate()){
	document.forms(0).submit();
	}
}
function validate()
	{
		var ev = new Validator();
        with(harvestMeansSearch){
 		// ev.test("notblank", "开票开始时间不能大于结束时间", checkTime($('sTime').value,$('lTime').value));		 
 		 ev.test("dateYX","开票起始日期格式不正确",$("sTime").value); 
 		 ev.test("dateYX","开票结束日期格式不正确",$("lTime").value); 
		   }
		   ev.writeErrors(errorsFrame, "errorsFrame");
		if (ev.size() > 0) {			
			return true;
		}
	}	
	
	
	
	
</script>
