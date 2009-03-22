<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<script language="javascript" src="<s:url value="/commons/scripts/checkLength.js"/>">
</script>
<title>收款管理查询</title>

<script language="javascript" type=text/javascript>
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,
		document.w_HarvestMeansSearch.expId,"id","name",{value:"",text:"    "});
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
		hisReve.clientSelectId.value="";
	}
</script>

<style type="text/css">
<!--

-->
</style>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<body leftmargin="0">
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
      <s:form action="w_HarvestMeansSearch" target="rightFrame" theme="simple">
      <s:hidden name="resetCondition" value="true"/>
<table width="100%" class="bg_table02">
  <tr>
    <td colspan="2" align="right" class="bg_table01"  height="0.5"><img src="/yx/commons/images/temp.gif" width="1" height="1"></td>
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
		    <s:select name="expId" list="listExp" listKey="id" listValue="name" required="true" headerValue="" ></s:select>
		</td>
	</tr>
	      
	<tr class="bg_table02">
		<td align="center">
		<div align="right">合同号：</div>
		 <td nowrap align="left"> <s:textfield name="contractId" size="15"></s:textfield></td>
	</tr>
	
	<tr class="bg_table02">
		<td nowrap>
		<div align="right">项目号：</div></td>
		 <td nowrap align="left"> <s:textfield name="projectNo" size="10"></s:textfield></td>
	</tr>
	<tr class="bg_table02">
	<td nowrap ><div align="right">收款状态：</div></td>
	<td nowrap  align="left">
	<s:select name="noRemain" list="#@java.util.TreeMap@{'0':'存在余额','1':'余额为0','2':'全部'}">
	</s:select>
	</td>
	</tr>
	
	 <tr class="bg_table02">
		<td align="right">开始日期：</td>
		<td align="left">
		<s:textfield name="reveStartDate" id="sDate"  size="13"/>
        <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sDate')" align=absMiddle alt=""  />
		</td>
	</tr>
	<tr class="bg_table02">
		<td align="right">结束日期：</td>
		<td align="left">
		<s:textfield name="reveEndDate" id="eDate"  size="13"/>
        <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('eDate')" align=absMiddle alt=""  />
		</td>
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

function check(){
	
	if(!validate()){
		document.forms(0).submit();
	}
}
function validate()
	{
		var ev = new Validator();
        with(w_HarvestMeansSearch){
 		// ev.test("notblank", "开票开始时间不能大于结束时间", checkTime($('sTime').value,$('lTime').value));		 
 			ev.test("dateYX","起始日期格式不正确",$("reveStartDate").value); 
 			ev.test("dateYX","结束日期格式不正确",$("reveEndDate").value); 
		   }
		   ev.writeErrors(errorsFrame, "errorsFrame");
		if (ev.size() > 0) {			
			return true;
		}
	}	
	
getEmployeeOfDepartment(document.forms(0).groupId.value);

</script>
