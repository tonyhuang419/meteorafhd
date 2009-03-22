<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<html>
<head>
<title>打印开票申请查询</title>
<script type="text/javascript">
	function validate()
	{
		var ev = new Validator();
		  with(createInvoice){
		   	 ev.test("dateYX","申请开始日期格式不正确!",$("beginApplyDate").value);
		     ev.test("dateYX","申请结束日期格式不正确!",$("endApplyDate").value);	
		 }
		 ev.writeErrors(errorsFrame, "errorsFrame");
		 if (ev.size() > 0) {
	 		return true;	  
		}
	}
		
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.createInvoice.expId,"id","name",{value:"",text:"    "});
	}
		
	function doSubmit()
	{
		if( !validate() ){
			document.forms[0].submit();
		}
	}
	
</script>
</head>
<body leftmargin="0">
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:form action="createInvoice" theme="simple" target="rightFrame">
<s:hidden name="method" value="printBill"></s:hidden>
	<s:hidden name="resetCondition" value="true"/>
	<table width="100%" class="bg_table02">
		<tr>
			<td colspan="2" align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
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
			<td align="right" nowrap>合同号:</td>
		    <td class="bg_table02" align="left" >
		    	<s:textfield name="conNum"></s:textfield>      	
			</td>
	      </tr> 
	      <tr class="bg_table02">
			<td align="right" nowrap>项目号:</td>
		    <td class="bg_table02" align="left" >
		    	<s:textfield name="itemNum"></s:textfield>      	
			</td>
	      </tr> 
		<tr>
			<td class="bg_table02">
			<div align="right">申请编号：</div>
			</td>
			<td class="bg_table02">
			<div align="left"><s:textfield name="billApplyNum" size="10"
				maxlength="10" /></div>
			</td>
		</tr>
		<tr>
			<td class="bg_table02">
			<div align="right">申请日期：</div>
			</td>
			<td class="bg_table02">
			<div align="left">从：<input type="text" id="beginApplyDateX"
				name="beginApplyDate" 
				 size="12"> <img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('beginApplyDate')" align=absMiddle
				alt="" /></div>
			<div align="left">至：<input type="text" id="endApplyDateX"
				name="endApplyDate" 
				 size="12"> <img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('endApplyDate')" align=absMiddle
				alt="" /></div>
			</td>
		</tr>
	      <tr class="bg_table02">
			      <td align="right" nowrap>申请单状态：</td>
		          <td class="bg_table02" align="left" >
		          <select name="applyStatus" >
		          	<option value="-1">全部</option>
		          	<option value="5">已开票</option>
		          	<option value="3" selected="selected">确认通过</option>
		          	<option value="7">已处理</option>
		          	<option value="6">已签收</option>
		          </select>
			</td>
	      </tr>
		<tr class="bg_table04">
			<td colspan="2">
			<div align="center"><input type="button" name="button2"
				id="button2" value="查询" class="button01" onclick="doSubmit();" /></div>
			</td>
		</tr>
	</table>
</s:form>
</body>
<script type="text/javascript">
getEmployeeOfDepartment($('groupId').value);
</script>
</html>