<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>月度计划收款管理搜索</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.forms(0).expId,"id","name",{value:"",text:"    "});
	}
</script>
<body leftmargin="0">
<iframe name="errorsFrame" frameborder="0" width="100%"
				framespacing="0" height="0" scrolling="no"></iframe></div>
<s:form action="moonHarvestProgram" theme="simple" target="rightFrame">
 <s:hidden name="method" value="queryList" ></s:hidden>
	<s:hidden name="resetCondition" value="true"/>
	<table width="100%" class="bg_table02">
          <tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5">
            	<img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
       	  <tr class="bg_table02">
			      <td align="right" nowrap>组别：</td>
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
			<td align="right">收款状态:</td>
			<td align="left">
				<select name="billState">
					<option value="">全部</option>
					<option value="1" <s:if test="#parameters.billState[0] == 1">selected</s:if> >部分收款</option>
					<option value="0" <s:if test="#parameters.billState[0] == 0">selected</s:if> >未收款</option>
					<option value="2" <s:if test="#parameters.billState[0] == 2">selected</s:if> >全部收款</option>
				</select>
			</td>
		</tr>
			<tr class="bg_table02">
			<td align="right">计划内（外）:</td>
			<td align="left">
				<select name="inPlan">
					<option value="">全部</option>
					<option value="0" <s:if test="#parameters.inPlan[0] == 0">selected</s:if> >计划内</option>
					<option value="1" <s:if test="#parameters.inPlan[0] == 1">selected</s:if> >计划外</option>
				</select>
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="right"><s:select name="year"  list="yearMap" >
				</s:select>年
				</td>
				<td align="left">
       	 	   <s:select name="month" list="{'01','02','03','04','05','06','07','08','09','10','11','12'}" >
				</s:select>月	</td>
		</tr>
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