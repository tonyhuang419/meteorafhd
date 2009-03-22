<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title></title>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
	function cleanValue(obj){
		if(obj==null||obj.length==0){
			document.applyBillQuery.customer.value="";
		}
	}
	function selectedClient(clientObj){
		document.applyBillQuery.customerName.value = clientObj.clientFullName;
		document.applyBillQuery.customer.value = clientObj.clientId;
	}
function doSubmit(){
	if(!validate()){
		document.applyBillQuery.submit();
	}
}
function validate()
{
	var ev2=new Validator();
	ev2.test("dateYX","计划收款开始日期格式不正确！",$('startDate').value);
	ev2.test("dateYX","计划收款结束日期格式不正确！",$('endDate').value);
	ev2.writeErrors(errorsFrame,"errorsFrame");
	if(ev2.size()>0){
		return true;
	}
	return false;
}
</script>
</head>
<body leftmargin="0">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" scrolling="no"></iframe>
<s:form action="applyBillQuery" theme="simple" target="rightFrame">
	<s:hidden name="method" value="selectMonth"></s:hidden>
	<s:hidden name="resetCondition" value="true"/>
	<table height="20%" width="100%"  border=0  cellpadding=1 cellspacing=0>
          <tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5">
            	<img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
       	<tr class="bg_table02">
			<td align="right">合同号：</td>
			<td align="left">
				<s:textfield name="conId"></s:textfield>
			</td>
		</tr>  
       	<tr class="bg_table02">
			<td align="right">项目号：</td>
			<td align="left">
				<s:textfield name="itemId"></s:textfield>
			</td>
		</tr>  
		<tr class="bg_table02">
			<td align="right">客户：</td>
			<td align="left">
				<s:textfield name="customerName" onblur="cleanValue(this.value);" size="17"/>
				<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=550,width=700');">
				<s:hidden name="customer"></s:hidden>
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="right">含税开票金额：</td>
			<td align="left">
				大于<s:textfield name="minRealTaxBillAmount" size="15" onblur="formatInputNumber(this)"/><br>
				小于<s:textfield name="maxRealTaxBillAmount" size="15" onblur="formatInputNumber(this)" />
			</td>
		</tr> 
		<tr class="bg_table02">
			<td align="right" nowrap="nowrap">不含税开票金额：</td>
			<td align="left">
				大于<s:textfield name="minRealBillAmount" size="15" onblur="formatInputNumber(this)"></s:textfield><br>
				小于<s:textfield name="maxRealBillAmount" size="15" onblur="formatInputNumber(this)"></s:textfield>
			</td>
		</tr>  
		<tr class="bg_table02">
			<td align="right" nowrap="nowrap">合同金额：</td>
			<td align="left">
				大于<s:textfield name="minConAmount" size="15" onblur="formatInputNumber(this)"></s:textfield><br>
				小于<s:textfield name="maxConAmount" size="15" onblur="formatInputNumber(this)"></s:textfield>
			</td>
		</tr>  
		<tr class="bg_table02">
			<td align="right">开票月份：</td>
			<td align="left">
				<s:select name="yearSel" id="yearSel" list="yearMap" emptyOption="true"></s:select>
					 年 
				<s:select name="monthSel" list="{'01','02','03','04','05','06','07','08','09','10','11','12'}" id="monthSel" emptyOption="true"></s:select> 月</div>
			</td>
		</tr>
	<tr align="right" class="bg_table02">
		<td rowspan="2">
		计划开票日期：
		</td>
		<td align="left">
			从<s:textfield name="startDate" id="startDate" size="12"/>  <img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('startDate')" align=absMiddle alt="" />
		</td>
		</tr>
		<tr align="left"  class="bg_table02">
		<td nowrap="nowrap">至<s:textfield name="endDate" id="sDate" size="12"/> <img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('endDate')" align=absMiddle alt="" />
				</td>
		</tr>
	       <tr class="bg_table04">
			      <td colspan="2" align="center" nowrap> 
			      	<input class="button01" type="button" value=" 查  询 " onclick="doSubmit();"/> 
			      </td>
	      </tr>
	 </table>
	</s:form>
</body>
</html>