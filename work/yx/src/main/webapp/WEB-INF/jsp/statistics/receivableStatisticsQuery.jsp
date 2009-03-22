<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<title>应收款统计</title>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.receivableStatisticsQuery.saleMan,"id","name",{value:"",text:"    "});
	}
	function querySubmit(){
		var checkedBoxes = $$("input[name=accountAgeTotal][checked]");
		if(checkedBoxes.length == 1){
			document.forms(0).method.value="calAccountAgeTotal";
			document.forms(0).submit();
			window.close();
		}
		else{
			document.forms(0).submit();
			window.close();
		}
	}
	function saveSaleMan(sale)
	{
		var departmentCode=document.receivableStatisticsQuery.groupId.value;
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.receivableStatisticsQuery.saleMan,"id","name",{defaultAsync:false,value:"",text:"    "});
		var selectNode=document.receivableStatisticsQuery.saleMan.options;
		for(var k=0;k<selectNode.length;k++){
			if(selectNode[k].value==sale){
				selectNode[k].selected=true;
				break;
			}
		}
	}
	function cleanValue(obj){
		if(obj==null||obj.length==0){
			document.receivableStatisticsQuery.customer.value="";
		}
	}
	function selectedClient(clientObj){
		document.receivableStatisticsQuery.customerName.value = clientObj.clientFullName;
		document.receivableStatisticsQuery.customer.value = clientObj.clientId;
	}
	
	
</script>
<body>
<s:form action="receivableStatisticsQuery" theme="simple" target="main_view">
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
	       <tr class="bg_table02">
			      <td align="right">客户：</td>
					<td align="left">
						<s:textfield name="customerName" onblur="cleanValue(this.value);" size="17"/>
						<input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');">
						<s:hidden name="customer"></s:hidden>
					</td>
			      <td align="right" nowrap></td>
		          <td class="bg_table02" align="left" >
				  </td>
	      </tr>
	       <tr class="bg_table02">
			      <td align="right" nowrap>合同号：</td>
		          <td class="bg_table02" align="left" >
		         	<s:textfield name="contractNum" />
				  </td>
			      <td align="right" nowrap>项目号：</td>
		          <td class="bg_table02" align="left" >
		         	<s:textfield name="itemId" />
				  </td>
	      </tr>
	      <tr class="bg_table02">
			      <td align="right" nowrap>合同阶段：</td>
		          <td class="bg_table02" align="left" >
		         	<s:select name="conStage" list="stageList" listKey="typeSmall" listValue="typeName" required="true" emptyOption="true"></s:select>
				  </td>
			      <td align="right" nowrap>辅助阶段：</td>
		          <td class="bg_table02" align="left" >
		         	<s:textfield name="auxiliaryStage" />
				  </td>
	      </tr><!--
	       <tr class="bg_table02">
			      <td align="right" nowrap>变更类型：</td>
		          <td class="bg_table02" align="left" >
		         	<s:select name="historyType" list="#@java.util.TreeMap@{0:'开票计划变更',1:'收款计划变更'}" emptyOption="true"></s:select>
				  </td>
			      <td align="right" nowrap></td>
		          <td class="bg_table02" align="left" >
				  </td>
	      </tr>
	       --><tr class="bg_table02">
			      <td align="right" nowrap>逻辑帐龄：</td>
		          <td class="bg_table02" align="left" >
		         	天:<s:textfield name="minLogicDayAccountAge" size="4" onkeydown="valNum(event);" />-<s:textfield name="maxLogicDayAccountAge" size="4" onkeydown="valNum(event);" /><br>
		         	月:<s:textfield name="minLogicMonthAccountAge" size="4" onkeydown="valNum(event);" />-<s:textfield name="maxLogicMonthAccountAge" size="4" onkeydown="valNum(event);"/>
				  </td>
				  <td align="right" nowrap>实际帐龄：</td>
		          <td class="bg_table02" align="left" >
		         	天:<s:textfield name="minRealDayAccountAge" size="4" onkeydown="valNum(event);" />-<s:textfield name="maxRealDayAccountAge" size="4" onkeydown="valNum(event);" /><br>
		         	月:<s:textfield name="minRealMonthAccountAge" size="4" onkeydown="valNum(event);" />-<s:textfield name="maxRealMonthAccountAge" size="4" onkeydown="valNum(event);" />
				  </td>
	      </tr>
	     <tr class="bg_table02">
			      <td align="right" nowrap>选择排序：</td>
		          <td class="bg_table02" align="left" >
		          		<input type="checkbox" value="1" name="conIdBy" 
		          			<s:if test="conIdBy == 1">
		          				checked="checked"
		          			</s:if>
		          		 />合同号排序
		          		<input type="checkbox" value="0" name="accountAgeBy"
		          			<s:if test="accountAgeBy == 0">
		          				checked="checked"
		          			</s:if>
		          		/>帐龄排序
				  </td>
			      <td align="center" nowrap colspan="2"> 
			      	<input type="checkbox" value="2" name="accountAgeTotal"
		          			<s:if test="accountAgeTotal == 2">
		          				checked="checked"
		          			</s:if>
		          		/>&nbsp;&nbsp;&nbsp;按帐龄范围汇总统计
			      </td>
	      </tr>
	     <tr class="bg_table02">
			      <td align="right" nowrap>开票类型：</td>
		          <td colspan="3" class="bg_table02" align="left" >
		          		<!--<input type="checkbox" value="1" name="billType" 
		          			<s:if test="conIdBy == 1">
		          				checked="checked"
		          			</s:if>
		          		 />增值税发票
		          		<input type="checkbox" value="0" name="accountAgeBy"
		          			<s:if test="accountAgeBy == 0">
		          				checked="checked"
		          			</s:if>
		          		/>服务业普通发票
		          		-->
		          	<s:checkboxlist value="billType" theme="simple"  name="billType" list="billTypeList" listKey="typeSmall" listValue="typeName" required="true"></s:checkboxlist>
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
<script type="text/javascript">
	<s:if test="groupId!=10">
		saveSaleMan(<s:property value="saleMan"/>);
	</s:if>
</script>
</html>