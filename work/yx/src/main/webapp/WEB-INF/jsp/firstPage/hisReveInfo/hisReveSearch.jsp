<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<html>
<head>
<title>到款信息搜索</title>

<script language="javascript">
function getEmployeeOfDepartment(departmentCode){
	ajaxSetSelectOptions("/yx/jsonData.action?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.hisReve.expId,"id","name",{value:"",text:"    "});
}
function clientChange(selObj){
	document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
	document.getElementById("clientId").value = selObj.value;
}

	

function validate()
{
	var ev = new Validator();
	  with(hisReve){
	   	 ev.test("dateYX","开始日期格式不正确!",$("reveStartDate").value);
	     ev.test("dateYX","结束日期格式不正确!",$("reveEndDate").value);	
	 }
	 ev.writeErrors(errorsFrame, "errorsFrame");
	 if (ev.size() > 0) {
 		return true;	  
	}
}

function doSubmit(){
	if( !validate() ){
		document.forms[0].submit();
	}
}

</script>

<style type="text/css">

</style>
</head>
<body>

<s:form action="hisReve" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<s:hidden name="resetCondition" value="true"/>
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
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
		          <s:select name="expId" list="listExp" listKey="id" listValue="name" required="true" headerValue="" ></s:select>
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
			    <input type="button" value=" 查  询 "  class="button02" onclick="doSubmit();">
		 </div></td>
  </tr>
</table>
</s:form>


<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).groupId.value);
</script>

</body>
</html>
