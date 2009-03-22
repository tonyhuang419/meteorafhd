<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script language="javascript">
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.passApply.expId,"id","name",{value:"",text:"    "});
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
	function doSubmit()
	{
			if(!validate()){	
			document.passApply.submit();
			}
	}
	function validate()
	{
		var ev2=new Validator();
		ev2.test("dateYX","付款申请开始日期格式不正确！",$('startDate').value);
		ev2.test("dateYX","付款申请结束日期格式不正确！",$('endDate').value);
		ev2.writeErrors(errorsFrame,"errorsFrame");
		if(ev2.size()>0){
			return true;
		}
	  return false;
	}

</script>
</head>
<style>
html { overflow-x:hidden;overflow-y:hidden;  }
</style>
<body leftmargin="0">
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<table width="100%" class="bg_table02">
<s:form theme="simple" action="passApply" target="rightFrame">
	<s:hidden name="method" value="query"/>
	<s:hidden name="resetCondition" value="true" />
<tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
       	       <tr class="bg_table02">
                  <tr class="bg_table02">
			      <td align="right" nowrap>组别：</td>
		          <td class="bg_table02" align="left" >
		        	  <s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
		         </td>
	      </tr>
	      <tr class="bg_table02">
			      <td align="right" nowrap>销售员：</td>
		          <td class="bg_table02" align="left" >
		          <s:select name="expId" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select>
			</td>
	      </tr>
	            <tr class="bg_table02">
                  <td align="right" nowrap class="bg_table02"><div align="right">供应商：</div></td>
	              <td nowrap><s:textfield size="15" name="supply" id="supplyId" readonly="true"/><input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseSup.action');">
	              	<s:hidden id="supplierid" name="supplierid"></s:hidden>
	              </td>
	              </tr>
	              <tr class="bg_table02">
                	<td align="right">
                		外协合同号：
                	</td>
                	<td>
                		<s:textfield name = "assistanceNo"></s:textfield>
                	</td>
                </tr>
                <tr class="bg_table02">
                  <td rowspan="2" align="right" nowrap class="bg_table02"><div align="right">付款申请日期：</div></td>
                  <td nowrap><div align="left">
                    从
                    <input type="text" id="startDate" name="startDate"  size="12" />
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('startDate')" align=absMiddle alt=""  /></div></td>
                </tr>
                <tr class="bg_table02">
                  <td nowrap>至
                  <input type="text" id="endDate" name="endDate"  size="12" />
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('endDate')" align=absMiddle alt=""  /></td>
                </tr>
                <tr class="bg_table02">
                	<td align="right">
                		申请单状态：
                	</td>
                	<td>
                		<s:select list="#@java.util.TreeMap@{'1':'待确认','2':'确认通过'}" name="payInfoState"></s:select>
                	</td>
                </tr>
            <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    <input type="button" value="查    询" class="button02" onclick="doSubmit();">
		      </div></td>
  </tr>
  </s:form>
		</table>		
</body>
<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).groupId.value);
</script>
</html>
