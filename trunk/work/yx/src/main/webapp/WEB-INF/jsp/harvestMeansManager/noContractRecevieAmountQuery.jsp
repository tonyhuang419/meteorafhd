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
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.noContractHarvestManage.saleMan,"id","name",{value:"",text:"    "});
	}
	function doSubmit()
	{
		if(!validate()){
			document.noContractHarvestManage.submit();
		}
	}
	function validate(){
		var ev2=new Validator();
		ev2.test("dateYX","开始日期格式不正确",document.noContractHarvestManage.elements("startRecevieDate").value);
		ev2.test("dateYX","结束日期格式不正确",document.noContractHarvestManage.elements("endRecevieDate").value);
		ev2.writeErrors(errorsFrame, "errorsFrame");
		if(ev2.size()>0){
			return true;
		}
		return false;
	}
</script>
<body leftmargin="0">
<iframe name="errorsFrame" frameborder="0" width="100%"
				framespacing="0" height="0" scrolling="no"></iframe></div>
<s:form action="noContractHarvestManage" theme="simple" target="rightFrame">
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
		<tr align="right" class="bg_table02">
		<td rowspan="2">
		收款日期：
		</td>
		<td align="left">
			从<s:textfield name="startRecevieDate" id="startRecevieDate" size="12"/>  <img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('startRecevieDate')" align=absMiddle alt="" />
		</td>
		</tr>
		<tr align="left"  class="bg_table02">
		<td nowrap="nowrap">至<s:textfield name="endRecevieDate" id="endRecevieDate" size="12"/> <img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('endRecevieDate')" align=absMiddle alt="" />
				</td>
		</tr>
		<tr class="bg_table02">
			<td align="right">收款状态：</td>
			<td>
		   <s:select name="state" id="state" headerKey="" headerValue="" list="#@java.util.TreeMap@{'0':'未核销','1':'已核销','2':'历史核销'}" onchange="billStateChange(this)"  ></s:select>
			</td>
		</tr>
          
	       <tr class="bg_table04">
			      <td colspan="2" align="center" nowrap> 
			      	<input class="button01" type="button" value=" 查  询 " onclick="doSubmit();" /> 
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