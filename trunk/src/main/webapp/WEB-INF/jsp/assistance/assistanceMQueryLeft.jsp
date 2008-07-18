<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script language="javascript">
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
	function getClientOfEmployee(employeeId){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetClientOfEmployee&employeeId="+employeeId,document.assistanceMLeftQuery.clientSelectId,"id","name",{value:"",text:"    "});
	}
	function check(){
		rg = /\d+/;
		min = document.forms(0).min.value;
		max = document.forms(0).max.value;
		if(min==""){
			if(max==""){
				document.forms(0).method.value = "query";
				document.forms(0).submit();
			}else{
				if(rg.exec(max)){
					document.forms(0).method.value = "query";
					document.forms(0).submit();
				}else{
					alert("请输入数字!")
				}
			}
		}else{
			if(rg.exec(min)){
				if(max == ""){
					document.forms(0).method.value = "query";
					document.forms(0).submit();
				}else{
					if(rg.exec(min)){
						document.forms(0).method.value = "query";
						document.forms(0).submit();
					}else{
						alert("请输入数字!")
					}
				}
			}else{
				alert("请输入数字!");
			}
		}
	}
  </SCRIPT>
</head>
<style>
html { overflow-x:hidden;overflow-y:hidden;  }
</style>
<body>
<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<s:form action="assistanceMLeftQuery" target="rightFrame" theme="simple">
	<s:hidden name="method" value="query"></s:hidden>
<table height="40%"  border=0  cellpadding=1 cellspacing=0>

<tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
       	          <tr class="bg_table02">
			      <td align="center" nowrap>组别：</td>
		          <td class="bg_table02" align="left" >
		        	  <s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName" ></s:select>
		         </td>
	      </tr>
	      <tr class="bg_table02">
			      <td align="center" nowrap>销售员：</td>
		          <td class="bg_table02" align="left" >
		          <s:select name="expId" onchange="getClientOfEmployee(this.value)" list="listExp" listKey="id" listValue="name" required="true" headerValue="" emptyOption="true"></s:select>
			</td>
	      </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">供应商：</div></td>
	              <td nowrap><s:textfield size="15" name="supplier"/></td>
  </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">外协合同状态：</div></td>
	              <td nowrap><select name="cState">
	           		  <option value="">所有状态</option>
                      <option value=0>草稿</option>
                      <option value=1>提交确认</option>
                      <option value=2>确认通过</option>
                  </select></td>
  </tr>
	            <tr class="bg_table02">
	              <td align="center" nowrap class="bg_table02"><div align="right">付款状态：</div></td>
	              <td nowrap><select name="mState">
	                <option></option>
                    <option value=0>未付款</option>
                    <option value=1>部分付款</option>
                    <option value=2>全额付款</option>
                  </select></td>
  </tr>
	            <tr class="bg_table02">
	              <td align="center" nowrap class="bg_table02"><div align="right">合同名称：</div>	                <div align="right"></div></td>
	              <td nowrap><s:textfield name="contractName" size="20"/></td>
  </tr>
	            <tr class="bg_table02">
	              <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">合同金额：</div></td>
	              <td nowrap>最小金额
                    <s:textfield name="min" size="12"/></td>
  </tr>
	            <tr class="bg_table02">
                  <td nowrap>最大金额
                    <s:textfield name="max" size="12"/></td>
  </tr>
                <tr class="bg_table02">
                  <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">合同签订日期：</div></td>
                  <td nowrap><div align="left">
                    从
                    <input type="text" id="sDate" name="sDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sDate')" align=absMiddle alt=""  />
			  	</div></td>
                </tr>
                <tr class="bg_table02">
                  <td nowrap>至
                  <input type="text" id="eDate" name="eDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('eDate')" align=absMiddle alt=""  />
			  	</td>
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
