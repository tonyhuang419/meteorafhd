<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<title>开票精度统计</title>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.billAccuracyStatisticsQuery.saleMan,"id","name",{value:"",text:"    "});
	}
	function querySubmit(){
		document.forms(0).submit();
		window.close();
	}
	function saveSaleMan(sale)
	{
		var departmentCode=document.billAccuracyStatisticsQuery.groupId.value;
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.billAccuracyStatisticsQuery.saleMan,"id","name",{defaultAsync:false,value:"",text:"    "});
		var selectNode=document.billAccuracyStatisticsQuery.saleMan.options;
		for(var k=0;k<selectNode.length;k++){
			if(selectNode[k].value==sale){
				selectNode[k].selected=true;
				break;
			}
		}
	}
</script>
<body>
<s:form action="billAccuracyStatisticsQuery" theme="simple" target="main_view">
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
		<td align="right">计划开票日期：</td>
			<td>
				<s:select name="yearSel" id="yearSel" list="yearMap"></s:select>
				 年 
				<s:select name="monthSel" list="{'01','02','03','04','05','06','07','08','09','10','11','12'}" id="monthSel" ></s:select> 月
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