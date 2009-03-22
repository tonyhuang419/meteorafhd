<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title></title>
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
<s:form action="contractItemManager" theme="simple" target="rightFrame">
	<s:hidden name="resetCondition" value="true"/>
	<s:hidden name="method" value="queryNoProjectCode"/>
	<table height="20%" width="100%"  border=0  cellpadding=1 cellspacing=0>
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
	form.submit();
}

</script>
<script type="text/javascript">
getEmployeeOfDepartment($('groupId').value);
</script>
</html>