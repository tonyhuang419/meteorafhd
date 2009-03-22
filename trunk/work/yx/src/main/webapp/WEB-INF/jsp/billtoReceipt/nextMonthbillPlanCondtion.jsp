<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title></title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.showMoonBillQuery.expId,"id","name",{value:"",text:"    "});
	}
</script>
<body leftmargin="0">
<iframe name="errorsFrame" frameborder="0" width="100%"
				framespacing="0" height="0" scrolling="no"></iframe></div>
<s:form action="showMoonBillQuery" theme="simple" target="rightFrame">
	<s:hidden name="method" value="showNextMonthBill"></s:hidden>
	<s:hidden name="resetCondition" value="true"/>
	<s:hidden name="billStartDate" />
	<s:hidden name="billEndDate" />
	<table width="100%"  class="bg_table02" >
          <tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5">
            	<img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
       	  <tr class="bg_table02">
			      <td align="right" nowrap> 组  别：</td>
		          <td class="bg_table02" align="left" >
		        	  <s:select name="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
		         </td>
	      </tr>
	      <tr class="bg_table02">
			      <td align="right" nowrap>销售员：</td>
		          <td class="bg_table02" align="left" >
		          <select name="expId" id="expId" ></select>
			</td>
	      </tr>
		<tr class="bg_table02">
			<td align="right">开票状态：</td>
			<td align="left">
				<select name="billState">
					<option value="">全部</option>
					<option value="1" <s:if test="#parameters.billState[0] == 1">selected</s:if> >已开票</option>
					<option value="0" <s:if test="#parameters.billState[0] == 0">selected</s:if> >未开票</option>
				</select>
			</td>
		</tr>
		
		<%-- 
		<tr class="bg_table02">
			<td align="right">开始日期:</td>
			<td align="left">
			<s:textfield name="billStartDate" id="sDate" value="%{#parameters.billStartDate}" size="13"/>
            <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sDate')" align=absMiddle alt=""  />
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="right">结束日期:</td>
			<td align="left">
			<s:textfield name="billEndDate" id="eDate" value="%{#parameters.billEndDate}" size="13"/>
            <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('eDate')" align=absMiddle alt=""  />
			</td>
		</tr>
		--%>
	       <tr class="bg_table04">
			      <td colspan="2" align="center" nowrap> 
			      	<input class="button01" type="button" onclick="doSubmit(this)" value=" 查  询 " /> 
			      </td>
	      </tr>
	      
	 </table>
	</s:form>
</body>
<script type="text/javascript">
function doSubmit(objBtn){
	var ev2=new Validator();
	var form = getOwnerForm(objBtn);
	//ev2.test("dateYX","开始日期格式不正确",form.elements("billStartDate").value);
	//ev2.test("dateYX","结束日期格式不正确",form.elements("billEndDate").value);
	if(ev2.size()==0){
		form.submit();
	}
	ev2.writeErrors(errorsFrame, "errorsFrame");
}

</script>
<script type="text/javascript">
getEmployeeOfDepartment($('groupId').value);
</script>
</html>