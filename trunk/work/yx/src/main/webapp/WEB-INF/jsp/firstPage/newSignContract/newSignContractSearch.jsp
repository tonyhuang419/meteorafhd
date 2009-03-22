<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<html>
<head>
<title>新签合同搜索</title>

<script language="javascript">
function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.newSignContract.expId,"id","name",{value:"",text:"    "});
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
}
	
function validate()
{
	var ev = new Validator();
	  with(newSignContract){
	   	 ev.test("dateYX","开始日期格式不正确!",$("signStartDate").value);
	     ev.test("dateYX","结束日期格式不正确!",$("signEndDate").value);	
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

<s:form action="newSignContract" theme="simple" target="content">
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
	    <tr>
			<td class="bg_table02">
			<div align="right">合同类型：</div>
			</td>
			<td class="bg_table02" align="left">
			<s:select	name="contractType"
				list="#@java.util.TreeMap@{'0':'全部','1':'项目类','2':'集成类'}"></s:select>
			</td>
		</tr>
	     	     <tr class="bg_table02">
			<td align="right">开始日期:</td>
			<td align="left">
			<s:textfield name="signStartDate" id="sDate"  size="13"/>
            <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sDate')" align=absMiddle alt=""  />
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="right">结束日期:</td>
			<td align="left">
			<s:textfield name="signEndDate" id="eDate"  size="13"/>
            <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('eDate')" align=absMiddle alt=""  />
			</td>
		</tr>
		                    
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
