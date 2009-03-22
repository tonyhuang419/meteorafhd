<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
<html>
<head>
<title>正式合同管理搜索</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style>

<script type="text/javascript">

</script>

</head>
<script language="javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.formalContractManageQuery.saleId,"id","name",{value:"",text:"    "});
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
	function getClientOfEmployee(employeeId){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.formalContractManageQuery.clientSelectId,"id","name",{value:"",text:"    "});
	}
	
	function clean()
	{
		document.getElementById("clientName").value="";
	}
	
	function validate()
	{
		var ev = new Validator();
        with(formalContractManageQuery){ 
 		 ev.test("dateYX","起始签订日期格式不正确",$("start_date").value); 
 		 ev.test("dateYX","结束签订日期格式不正确",$("end_date").value); 
 		 ev.test("dateYX","起始交货日期格式不正确",$("deliveryStartDate").value); 
 		 ev.test("dateYX","结束交货日期格式不正确",$("deliveryEndDate").value); 

		 if($("maxAmount").value.length>0){
 			 ev.test("float","合同最大含税金额不为数字",$("maxAmount").value); 
 		 }
 		 if($("minAmount").value.length>0){
 		 	ev.test("float","合同最小含税金额不为数字",$("minAmount").value); 
 		 }
		 }
	    ev.writeErrors(errorsFrame, "errorsFrame");
		if (ev.size() > 0) {			
			return true;
		}
	}
	function search(){
		if(!validate()){
			document.forms(0).submit();
		}
	}
	
</script>
<body  leftmargin="0" >
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:form	name="formalContractManageQuery"	action="yx/contract/formalContractManage/formalContractManageQuery.action"	target="content" theme="simple" >
  <s:hidden name="resetCondition" value="true"></s:hidden>
  <table width="100%" class="bg_table02">
  <tr>
    <td colspan="2" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
    <tr >
      <td width="30%" align="center" nowrap><div align="right">组别：</div></td>
      <td width="*%" align="left"  ><s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>      </td>
    </tr>
    <tr >
      <td align="center" nowrap><div align="right">销售员：</div></td>
      <td  align="left" >
      <s:select name="saleId" onchange="getClientOfEmployee(this.value);clean();" list="listSale" listKey="id" listValue="name" required="true" headerValue="" headerKey=""></s:select>      </td>
    </tr>
    <tr >
      <td  align="right">客户名称：</td>
      <td   align="left" >
      <input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
        <s:select name="clientSelectId" list="yxClientCodeList" emptyOption="true" cssStyle="margin-left:-150px;width:168px;" listKey="id" listValue="name" onchange="clientChange(this)"> </s:select>
	        </span>
		<input type="button" value="…"	onclick="javascript:window.open('../../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
      <s:hidden name="customerId" id="clientId"></s:hidden></td></tr>
    <tr>
      <td><div align="right"> 合同号： </div></td>
      <td><div align="left"><s:textfield name="conSn" maxlength="15" ></s:textfield></div></td> 
    </tr>
    <tr>
      <td><div align="right"> 合同名称： </div></td>
      <td><div align="left"><s:textfield name="conName" ></s:textfield></div></td> 
    </tr>
    <tr>
      <td><div align="right">项目号： </div></td>
      <td><div align="left"><s:textfield name="itemSn" maxlength="15" ></s:textfield></div></td> 
    </tr>
    
    <tr>
      <td><div align="right">甲方合同号： </div></td>
      <td><div align="left"><s:textfield name="partyAConId" ></s:textfield></div></td> 
    </tr>
    
   <tr>
      <td><div align="right">工程编号： </div></td>
      <td><div align="left"><s:textfield name="partyAProId" ></s:textfield></div></td> 
    </tr> 
        
    <tr>
         <td ><div align="right">预决算信息：</div></td>
          <td><div align="left">
         <s:select list="#@java.util.TreeMap@{0:'非预决算',1:'预决算'}"   name="finalAccount"  headerKey=" " headerValue="--全部--"  ></s:select>
        </div></td>
	 </tr>
    
    
     <tr>
        <td  ><div align="right">合同类型：</div></td>
         <td><div align="left">
			<s:select name="contractType"  list="typeManageService.getYXTypeManage(1020)"   listKey="typeSmall" listValue="typeName"  
            headerKey=" " headerValue="--全部--"  ></s:select> 
          </div></td>  
       </tr>
     
    <tr>
      <td><div align="right"> 合同性质： </div></td>
      <td><div align="left">
         <s:select name="conType" list="typeManageService.getYXTypeManage(1019)" listKey="typeSmall" listValue="typeName" required="true" 
            headerKey=" " headerValue="--全部--"  ></s:select>
        </div></td>
    </tr>
    
    <tr>
      <td rowspan="2"><div align="right"> 含税金额： </div></td>
      <td><div align="left">大于<input type="text" id="maxAmount" name="maxAmount"  size="15"   />
        </div></td>
    </tr>
    <tr>
      <td><div align="left">小于<input type="text" id="minAmount" name="minAmount"  size="15" />
		</div></td>
    </tr>
                   
    <tr>
      <td rowspan="2"><div align="right"> 签订日期： </div></td>
      <td><div align="left">从 <input type="text" id="start_date" name="start_date"
				size="12" /> <img src="/yx/commons/images/calendar.gif" 
				onclick="javascript:ShowCalendar('start_date')" align="absMiddle">
        </div></td>
    </tr>
    <tr>
      <td><div align="left">至 <input type="text" id="end_date" name="end_date"
				size="12" /> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('end_date')"  align="absMiddle"/> 
		</div></td>
    </tr>
    <tr>
      <td rowspan="2"><div align="right"> 交货日期： </div></td>
      <td><div align="left">从 <input type="text" id="deliveryStartDate" name="deliveryStartDate"
				size="12" /> <img src="/yx/commons/images/calendar.gif"deliveryStartDate
				onclick="javascript:ShowCalendar('deliveryStartDate')" align="absMiddle">
        </div></td>
    </tr>
    <tr>
      <td><div align="left">至 <input type="text" id="deliveryEndDate" name="deliveryEndDate"
				size="12" /> <img src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('deliveryEndDate')"  align="absMiddle"/> 
		</div></td>
    </tr>
    <tr>
      <td><div align="right"> 合同状态： </div></td>
      <td><div align="left"><select name="conState">
          <option> --全部-- </option>
          <option> 正式合同 </option>
         <!--  <option> 变更保存 </option>   -->
          <option> 变更待确认 </option>
          <option> 变更退回 </option>
          <option> 建议关闭 </option>
        </select>   </div>   </td>
    </tr>
    <tr>
      <td><div align="right">已关闭：</div></td>
      <td><div align="left"><s:checkbox name="hasClosed" />
        </div></td>
    </tr>
    <tr class="bg_table04">
      <td colspan="2"><div align="center">
    	  <input type="button" value="查  询"  onclick="search();"	class="button01" id="formalContractQuerty">
       </td>
    </tr>
  </table>
</s:form>
<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).groupId.value);
</script>
</body>
</html>
