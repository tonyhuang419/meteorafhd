<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>

<title>外协合同管理搜索</title>
<script language="javascript" src="<s:url value="/commons/scripts/checkLength.js"/>"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script language="javascript">
function openUrl(url){
	window.open(url,null,'menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.assistanceMLeftQuery.expId,"id","name",{value:"",text:"    "});
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
	function check(){
		if(!validate())
		{
			document.forms(0).method.value = "query";
			document.forms(0).submit();
		}
	}
	function validate()
{
	 var ev2=new Validator();
	 var minFee=document.forms(0).minConMoney.value;
	 var maxFee=document.forms(0).maxConMoney.value;
	 if(minFee.length>0)
	 {
	 	 ev2.test("+float","最小金额金额必须是大于0的数字",minFee);
	 }
	 if(maxFee.length>0)
	 {
	 	ev2.test("+float","最大金额金额必须是大于0的数字",maxFee);
	 }
	 ev2.test("dateYX","开始日期输入格式不正确！",$('startDate').value);
	 ev2.test("dateYX","结束日期输入格式不正确！",$('endDate').value);
	 ev2.writeErrors(errorsFrame, "errorsFrame");
	 if(ev2.size()>0)
	 {
	 	return true;
	 }
	 return false;
}
  </SCRIPT>
</head>
<style>
html { overflow-x:hidden;overflow-y:hidden;  }
</style>
<body style="margin: 0px;">
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:form action="assistanceMLeftQuery" target="rightFrame" theme="simple">
	<s:hidden name="method" value="query"></s:hidden>
	<s:hidden name="resetCondition" value="true"/>
<table  width="100%" class="bg_table02" >
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
                  <td align="right" nowrap class="bg_table02"><div align="right">供应商：</div></td>
	              <td nowrap><s:textfield size="15" name="supplier" id="supplyId" readonly="true"/><input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseSup.action');">
	              	<s:hidden id="supplierid" name="supplierid"></s:hidden>
	              </td>
  </tr>
	            <tr class="bg_table02">
                  <td align="right" nowrap class="bg_table02"><div align="right">外协合同状态：</div></td>
	              <td nowrap><select name="conState">
	           		  <option value="">所有状态</option>
                      <option value=0>草稿</option>
                      <option value=1>提交待确认</option>
                      <option value=2>确认通过</option>
                      <option value="3">确认退回</option>
                  </select></td>
  </tr>
	            <tr class="bg_table02">
	              <td align="right" nowrap class="bg_table02"><div align="right">付款状态：</div></td>
	              <td nowrap><select name="payState">
	                <option></option>
                    <option value=0>未付款</option>
                    <option value=1>部分付款</option>
<!--                    <option value=2>全额付款</option>-->
                  </select></td>
  </tr>
	            <tr class="bg_table02">
	              <td align="right" nowrap class="bg_table02"><div align="right">外协合同名称：</div>	                <div align="right"></div></td>
	              <td nowrap><s:textfield name="contractName" size="20"/></td>
  </tr>
   			 <tr class="bg_table02">
	              <td align="right" nowrap class="bg_table02"><div align="right">外协合同号：</div>	                <div align="right"></div></td>
	              <td nowrap><s:textfield name="assistanceContractNo" size="20"/></td>
  </tr>
  		 <tr class="bg_table02">
	              <td align="right" nowrap class="bg_table02"><div align="right">合同号：</div>	                <div align="right"></div></td>
	              <td nowrap><s:textfield name="contractNo" size="20"/></td>
  </tr>
   		 <tr class="bg_table02">
	              <td align="right" nowrap class="bg_table02"><div align="right">项目号：</div>	                <div align="right"></div></td>
	              <td nowrap><s:textfield name="itemNo" size="20"/></td>
  </tr>
  		 <tr class="bg_table02">
	              <td align="right" nowrap class="bg_table02"><div align="right">部门：</div>	                <div align="right"></div></td>
	              <td nowrap>
	              	 <s:select name="departmentName" list="dutydepartmentlist" listKey="typeSmall" listValue="typeName" headerKey="" headerValue="全部">
	              	 
	              	 </s:select>
	              </td>
  </tr>
	            <tr class="bg_table02">
	              <td rowspan="2" align="right" nowrap class="bg_table02"><div align="right">合同金额：</div></td>
	              <td nowrap>最小金额
                    <s:textfield name="minConMoney" size="12"/></td>
  </tr>
	            <tr class="bg_table02">
                  <td nowrap>最大金额
                    <s:textfield name="maxConMoney" size="12"/></td>
  </tr>
                <tr class="bg_table02">
                  <td rowspan="2" align="right" nowrap class="bg_table02"><div align="right">合同签订日期：</div></td>
                  <td nowrap><div align="left">
                    从
                    <input type="text" id="startDate" name="startDate"  size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('startDate')" align=absMiddle alt=""  />
			  	</div></td>
                </tr>
                <tr class="bg_table02">
                  <td nowrap>至
                  <input type="text" id="endDate" name="endDate"  size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('endDate')" align=absMiddle alt=""  />
			  	</td>
                </tr>
                <tr class="bg_table02">
                  <td nowrap align="right" class="bg_table02">已关闭:	</td>
                  <td> <s:checkbox name="hasClosed" fieldValue="true"></s:checkbox> </td>
                </tr>
            <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    <input type="button" value="查    询" class="button02" onclick="javascript:check()">
		      </div></td>
  </tr>
		</table>		
		</s:form>
<script type="text/javascript">
getEmployeeOfDepartment(document.forms(0).groupId.value);
</script>
</body>
</html>
