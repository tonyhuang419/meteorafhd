<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<html>
<head>
<title>外协合同确认搜索</title>
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.affirmAssistanceContract.expId,"id","name",{value:"",text:"    "});
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
</script>	
</head>
<style>
</style>
<body leftmargin="0">
<s:form theme="simple" action="affirmAssistanceContract" target="rightFrame">
<s:hidden name="method" value="query"/>
<s:hidden name="resetCondition" value="true"/>
<div align="left" style="color: #FF0000">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<table  width="100%"  class="bg_table02">
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
		          <s:select name="expId" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select>
			</td>
	      </tr>
	            <tr class="bg_table02">
                  <td  nowrap class="bg_table02"><div align="right">供应商：</div></td>
	              <td><s:textfield size="15" name="supplyName" id="supplyId" readonly="true"/><input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseSup.action');">
	              	<s:hidden id="supplierid" name="supplierid"></s:hidden></td>
  </tr>
	            <tr class="bg_table02">
	              <td rowspan="2" nowrap class="bg_table02"><div align="right">合同金额：</div></td>
	              <td nowrap>最小金额
                    <s:textfield name="minConMoney" size="12"/></td>
  </tr>
	            <tr class="bg_table02">
                  <td nowrap>最大金额
                    <s:textfield name="maxConMoney" size="12"/></td>
  </tr>
                <tr class="bg_table02">
                  <td rowspan="2" nowrap class="bg_table02"><div align="right">合同签订日期：</div></td>
                  <td nowrap><div align="left">
                    从
                    <input type="text" id="sDate" name="startDate"  size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sDate')" align=absMiddle alt=""  /></div></td>
                </tr>
                <tr class="bg_table02">
                  <td nowrap>至
                  <input type="text" id="eDate" name="endDate" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('eDate')" align=absMiddle alt=""  /></td>
                </tr>
           <tr class="bg_table02">
			<td align="right">外协状态：</td>
			<td align="left">
				<select name="assistanceContractType">
					<option value="1" <s:if test="assistanceContractType == 1">selected</s:if> >待确认</option>
					<option value="2" <s:if test="assistanceContractType == 2">selected</s:if> >确认通过</option>
				</select>
			</td>
		</tr>   
            <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    <input type="button" value="查    询" class="button02" onclick="doSubmit();">
		      </div></td>
  </tr>
		</table>	
		</s:form>	
<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).groupId.value);
function validate()
{
	 var ev2=new Validator();
	 var minFee=document.affirmAssistanceContract.minConMoney.value;
	 var maxFee=document.affirmAssistanceContract.maxConMoney.value;
	 if(minFee.length>0)
	 {
	 	 ev2.test("+float","最小金额金额必须是大于0的数字",minFee);
	 }
	 if(maxFee.length>0)
	 {
	 	ev2.test("+float","最大金额金额必须是大于0的数字",maxFee);
	 }
	 ev2.test("dateYX","开始日期时间格式不正确",$('sDate').value);
	 ev2.test("dateYX","结束日期时间格式不正确",$('eDate').value);
	ev2.writeErrors(errorsFrame, "errorsFrame");
	 if(ev2.size()>0)
	 {
	 	return true;
	 }
	 return false;
}
function doSubmit(){
	if(!validate()){
		document.affirmAssistanceContract.submit();
	}
}
</script>
</body>
</html>
