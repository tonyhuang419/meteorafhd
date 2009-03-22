<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<head>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<title>外协合同统计</title>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.assistanceStat.saleMan,"id","name",{value:"",text:"    "});
	}
	function querySubmit(){
		document.forms(0).submit();
		window.close();
	}

	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.assistanceStat.saleMan,"id","name",{value:"",text:"    "});
	}

	
	function cleanId(supplierVal){
		if(supplierVal == ""){
			document.getElementById("supplierid").value = "";
		}
	}
	
</script>
<body>
<s:form action="assistanceStat" theme="simple" target="main_view">
<s:hidden name="method" value="queryResult"/>
<s:hidden name="resetCondition" value="true"/>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%" class="bg_table02">
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
	</table>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%" class="bg_table02">
		<tr class="bg_table02">
			      <td align="right" nowrap>组别：</td>
		          <td class="bg_table02" align="left">
		        	  <s:select  name="groupId" onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName"></s:select>
		         </td>
			      <td align="right" nowrap>销售员：</td>
		          <td class="bg_table02" align="left" >
		          <s:select name="saleMan" list="empList" listKey="id" listValue="name" required="true" emptyOption="true"></s:select>
			</td>
	      </tr>
	    <tr>
			<td align="right">外协供应商：</td>
			<td>
				<s:textfield id="supplyId" name="supplierName" size="15" onblur="cleanId(this.value)"></s:textfield>
           		<s:hidden id="supplierid" name="supplierid"/>
                <input type="button" value="…" onclick="javascript:openWin('../assistance/chooseSup.action');">
            </td>
			<td align="right">项目号：</td>
			<td>
				<s:textfield name="itemId"></s:textfield>
            </td>
		</tr>
	    <tr>
			<td align="right">外协合同号：</td>
			<td>
				<s:textfield name="assistanceNo"></s:textfield>
            </td>
			<td align="right">外协合同名称：</td>
			<td>
				<s:textfield name="assistanceName"></s:textfield>
            </td>
		</tr>
	    <tr>
			<td align="right">合同号</td>
			<td>
				<s:textfield name="conNum"></s:textfield>
            </td>
			<td align="right">外协合同金额：</td>
			<td>
				<s:textfield name="assistanceAmountStart" size="15"  onblur="formatInputNumber(this)"></s:textfield>--
				<s:textfield name="assistanceAmountEnd" size="15"   onblur="formatInputNumber(this)"></s:textfield>
            </td>
		</tr>
	       <tr class="bg_table01">
			      <td align="center" colspan="4">
			      	<input type="button" name="2" value=" 查  询 " class="button01" onclick="querySubmit()" />
			      </td>
	      </tr>
	</table>
</s:form>
</body>
</html>