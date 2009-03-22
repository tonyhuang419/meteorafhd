<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<title>新签合同统计</title>
<script type="text/javascript">
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.formalContractStat.saleMan,"id","name",{defaultAsync:false,value:"",text:"    "});
	}
	function onloadMethod(sale)
	{
	
		var departmentCode=document.formalContractStat.groupId.value;
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.formalContractStat.saleMan,"id","name",{defaultAsync:false,value:"",text:"    "});
		var selectNode=document.formalContractStat.saleMan.options;
		for(var k=0;k<selectNode.length;k++){
			if(selectNode[k].value==sale){
				selectNode[k].selected=true;
				break;
			}
		}
	}
function selectedClient(client){
	cleanNum();
	var i= client.clientId;
	var j= client.clientFullName;
	if(i!=""&&j!=""){
	  var textfild = document.getElementById("custom");
	  textfild.value=j;
	  var hidden = document.getElementById("customId"); 
	  hidden.value = i;   
	}
}
function cleanNum(){
      var hidden = document.getElementById("customId"); 
	  hidden.value = "";   
}
function onSubmit(){
     document.formalContractStat.submit();
     window.close();
}
function setSaleMan(a){
  document.formalContractStat.saleMan.value=a;
}
function validate(){
       var ev2=new Validator(); 
       with(formalContractStat){  
       ev2.test("dateYX","起始日期格式不正确",$('startDate').value);
       ev2.test("dateYX","结束日期格式不正确",$('endDate').value);
       
       }
       if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
	   }
	   return false;
}
</script>
<body>
<s:form action="formalContractStat" target="main_view" theme="simple">
<s:hidden  name="resetCondition" value="true" />
 <s:hidden name="method" value="queryList" />
    <s:hidden name="customId" ></s:hidden>
     <div  align="left" style="color: #FF0000" >
<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" scrolling="no"></iframe></div>
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
		          <s:select name="saleMan" headerKey="" headerValue="--请选择--"  list="empList" listKey="id" listValue="name" required="true"  ></s:select>
			</td>
	      </tr>
	      <tr class="bg_table02">
			    <td align="right" nowrap>合同号：</td>
		          <td class="bg_table02" align="left" >
		         	<s:textfield name="contractNo" />
				  </td>
				  <td align="right" nowrap>项目号：</td>
		          <td class="bg_table02" align="left" >
		         	<s:textfield name="itemNo" />
				  </td>
	      </tr>
	       <tr class="bg_table02">
	       <td align="right" nowrap>项目部门：</td>
		          <td class="bg_table02" align="left" >
		         	 <s:select name="department"   headerKey=""  headerValue="--请选择--" list="dutydepartmentlist" listKey="typeSmall" listValue="typeName"  ></s:select>
				  </td>
			      <td align="right" nowrap>合同类别：</td>
		          <td class="bg_table02" align="left" >
		         	<s:select name="contractType" headerKey="" headerValue="--请选择--" list="contractTypeList" listKey="typeSmall" listValue="typeName" ></s:select>
				  </td>			   
	      </tr>
	      	 <tr class="bg_table02">
	       <td align="right" nowrap>决算信息：</td>
		          <td class="bg_table02" align="left" >
		         	 <s:select name="finalAccount"   headerKey=""  headerValue="--请选择--" list="#@java.util.TreeMap@{0:'非预决算',1:'预决算'}"   ></s:select>
				  </td>
	        <td align="right" nowrap>合同性质：</td>
		          <td class="bg_table02" align="left" >
		          <s:select name="conType" headerKey="" headerValue="--请选择--"  list="contractNatureList" listKey="typeSmall" listValue="typeName" ></s:select>
				  </td>	
	      </tr>
	      
	       <tr class="bg_table02">
	      <td align="right" nowrap>销售组织：</td>
		     <td class="bg_table02" align="left" >
		          <s:select name="businessArea" headerKey="" headerValue="--请选择--"  list="businessAreaList" listKey="typeSmall" listValue="typeName"  ></s:select>
		 	</td>
			<td align="right" nowrap>预合同：</td>  
			<td class="bg_table02" align="left" >
		         	<s:checkbox name="readyCon" />
			 </td>
	      </tr>
	    <tr class="bg_table02">
	      <td align="right" nowrap>客户性质：</td>
		     <td class="bg_table02" align="left" >
		     <s:select name="clientNID" list="clientNature" listKey="typeSmall"
				listValue="typeName"  headerKey="" headerValue="--请选择--"> 
		         </s:select>
		 	</td>
			
			  <td align="right" nowrap>客户行业类别：</td>
		          <td class="bg_table02" align="left" >
		          <s:select name="businessType" headerKey="" headerValue="--请选择--"  list="businessTypeList" listKey="typeSmall" listValue="typeName" ></s:select>
				  </td>	 
	      </tr>
	     
	      <tr class="bg_table02">
			      <td align="right" nowrap>客户全称：</td>
		          <td colspan="3" class="bg_table02" align="left" >
		         	<s:textfield size="25" name="custom" onchange="cleanNum()" ></s:textfield>
		         	  <input type="button"  value="..." 	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getSelectClientlist','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
				  </td>	 
	      </tr>
	      <tr class="bg_table02">
			      <td align="right" nowrap>日期范围：</td>
			      <td align="left" nowrap colspan="3" ><s:textfield name="startDate" ></s:textfield><img src="/yx/commons/images/calendar.gif"  onClick="javascript:ShowCalendar('startDate')" align="absMiddle" alt=""  />                 
			      ——&nbsp;<s:textfield name="endDate" ></s:textfield><img src="/yx/commons/images/calendar.gif"  onClick="javascript:ShowCalendar('endDate')" align="absMiddle" alt=""  />
			      </td>
	      </tr>
	        <tr class="bg_table02">
			      <td align="right" nowrap>合同交货日期：</td>
			      <td align="left" nowrap colspan="3" ><s:textfield name="handInStartDate" ></s:textfield><img src="/yx/commons/images/calendar.gif"  onClick="javascript:ShowCalendar('handInStartDate')" align="absMiddle" alt=""  />                 
			      ——&nbsp;<s:textfield name="handInEndDate" ></s:textfield><img src="/yx/commons/images/calendar.gif"  onClick="javascript:ShowCalendar('handInEndDate')" align="absMiddle" alt=""  />
			      </td>
	      </tr>
	       <tr class="bg_table02">
			      <td align="center" colspan="4">
			      	<input type="button" name="2" value=" 查  询 " onClick="if(!validate()){onSubmit();}"  class="button01"/>
			      </td>
	      </tr>
	</table>
</s:form>
</body>
<script type="text/javascript">
	<s:if test="groupId!=10">
		onloadMethod(<s:property value="saleMan"/>);
	</s:if>
</script>
</html>