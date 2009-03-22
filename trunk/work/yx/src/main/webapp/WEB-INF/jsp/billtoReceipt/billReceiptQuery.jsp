<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>开票/收据管理查询</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.billReceiptQuery.saleMan,"id","name",{value:"",text:"    "});
	}
</script>
<body leftmargin="0">
<iframe name="errorsFrame" frameborder="0" width="100%"
				framespacing="0" height="0" scrolling="no"></iframe></div>
<s:form action="billReceiptQuery" theme="simple" target="rightFrame">
	<s:hidden name="method" value="newDefault"/>
	<s:hidden name="resetCondition" value="true"/>
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
		          <select name="saleMan" id="saleMan" ></select>
			</td>
	      </tr>
		<tr class="bg_table02">
			<td align="right">合同号：</td>
			<td>
			<s:textfield name="contractNo"/>
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="right">项目号：</td>
			<td>
			<s:textfield name="itemNo"/>
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="right">发票号：</td>
			<td>
			<s:textfield name="invoiceNo"/>
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="right">开票金额：</td>
			<td>
			<s:textfield name="billAmountTax" onblur="formatInputNumber(this)" onkeydown="valNum(event);"/>
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="right">开票状态：</td>
			<td>
		   <s:select name="confirm" id="billState" headerKey="-1" headerValue=" " list="#@java.util.TreeMap@{'2':'未完全录入','1':'已确认录入','0':'未确认录入'}" ></s:select>
			</td>
		</tr>
      <tr>
      		<td><div align="right">已签收：</div></td>
      		<td><div align="left"><s:checkbox name="hasSigned" />
        	</div></td>
    	</tr> 
	       <tr class="bg_table04">
			      <td colspan="2" align="center" nowrap> 
			      	<input class="button01" type="submit" value=" 查  询 " /> 
			      </td>
	      </tr>
	      
	 </table>
	</s:form>
</body>
<script type="text/javascript">
</script>
<script type="text/javascript">
getEmployeeOfDepartment($('groupId').value);
</script>
</html>