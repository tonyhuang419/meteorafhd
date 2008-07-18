<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>添加售前合同</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<body>
<s:form action="contractBeforeSell" theme="simple" validate="true">
<div align="left" style="color:#000000">当前页面：售前合同->新增售前合同</div>
	<s:hidden name="method" value="saveCBS" />
	<s:hidden name="succSave"></s:hidden>
	<table width="95%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td valign="top" align="center">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td class="bg_table01" height="1"><img
						src="${IMG_PATH}/temp.gif" width="1" height="1">
						<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
					</td>
				</tr>
			</table>
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">				
					<td class="bg_table02" align="right">客户：</td>
					<td class="bg_table02"  align="left" >
					<input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select list="yxClientCodeList" emptyOption="true" cssStyle="margin-left:-150px;width:168px;" listKey="cli.id" listValue="cli.name" onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
					<s:hidden name="cbs.customerId" id="clientId"></s:hidden>
					</td>
					<td class="bg_table02" align="right">行业类别：</td>
					<td class="bg_table02" align="left">
						<s:hidden name="cbs.businessTypeId"></s:hidden>
						<label id="businessType"></label>
					</td>
				</tr>
		
				<tr align="center">
					<td class="bg_table02" align="right">客户项目类型：</td>
					<td class="bg_table02" align="left">
						<s:select  name="cbs.customerProjectTypeId" list="projectTypeList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="">
						</s:select>			
					</td>
					<td class="bg_table02" align="right">客户联系人：</td>
					<td class="bg_table02" align="left">
					<s:select name="cbs.linkManId" list="yxLinkManList" listKey="id" listValue="name" required="true" ></s:select></td>
				</tr>
				<tr>
					<td colspan="4" align="right" class="bg_table02">
					<hr>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right"><font color="red">* </font>项目名称：</td>
					<td class="bg_table02" align="left">
					<s:textfield name="cbs.projectName" /></td>
					<td class="bg_table02" align="right">项目跟踪状态：</td>
					<td class="bg_table02" align="left">			
						<s:select  name="cbs.projectStateFollowId" list="projectStateFollowList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="">
						</s:select>	
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right"><font color="red">* </font>项目主要内容：</td>
					<td class="bg_table02" align="left">
						<s:textarea name="cbs.mainProjectContent" id="mainProjectContent"></s:textarea>
			  	</td>
					<td class="bg_table02" align="right">项目跟踪情况说明：</td>
					<td class="bg_table02" align="left">
						<s:textarea name="cbs.descProjectFollow" id="descProjectFollow" ></s:textarea>	
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">项目类型：</td>
					<td class="bg_table02" align="left">
					
						<s:select  name="cbs.projectTypeId" list="contractTypeList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="">
						</s:select>
						</td>
					<td class="bg_table02" align="right">项目状况：</td>
					<td class="bg_table02" align="left">
						<s:select  name="cbs.projectStateId" list="projectStateList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="">
						</s:select>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">工程责任部门：</td>
					<td class="bg_table02" align="left">
						<s:select name="cbs.dutyDepartmentId" headerKey="" headerValue="--请选择--"
							list="dutyDepartmentIdList" listKey="typeSmall"
							onchange="getChargeManOfDepartment(this)" listValue="typeName">
						</s:select>
						
						</td>
					<td class="bg_table02" align="right">项目负责人：</td>
					<td class="bg_table02" align="left">
					
					<input type="text" name="cbs.projectManId" style="width: 90px; height: 21px; font-size: 10pt;" />
						<span style="width: 18px; border: 0px solid red;"><select style="margin-left: -90px; width:108px;" id="chargeManList"
								onchange="departPChange(this)">
							</select> 
						</span>
					
<!--					<s:textfield name="cbs.projectManId"/>-->
					</td>
				</tr>
				<tr align="center">
					<td colspan="4" align="right" class="bg_table02">
					<hr>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" width="15%" align="right"><font color="red">* </font>预计项目金额：</td>
					<td class="bg_table02" width="35%" align="left">						
						<s:textfield name="cbs.projectSum"></s:textfield>
					</td>
					<td class="bg_table02" align="right">投标（报价）金额：</td>
					<td class="bg_table02" align="left">
						<s:textfield name="cbs.bidSum" ></s:textfield>
				    </td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">中标（合同）金额：</td>
					<td class="bg_table02" align="left">
						<s:textfield name="cbs.ownSum" /></td>
					<td class="bg_table02" align="right">投标（报价）日期：</td>
					<td class="bg_table02" align="left">
			  	<div align="left">
			  	<input type="text" id="bidDate" name="cbs.bidDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('bidDate')" align=absMiddle alt=""  />
			  	</div></td>	
				</tr>
				<tr align="center">
					<td class="bg_table02" width="15%" align="right">项目跟踪起始日期：</td>
					<td class="bg_table02" align="left">
			  	<div align="left">
			  	<input type="text" id="projectDate" name="cbs.projectDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('projectDate')" align=absMiddle alt=""  />
			  	</div></td>	
					<td class="bg_table02" align="right">项目计划投运日期：</td>
					<td class="bg_table02" align="left">
			  	<div align="left">
			  	<input type="text" id="estimateProjectDate" name="cbs.estimateProjectDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('estimateProjectDate')" align=absMiddle alt=""  />
			  	</div></td>	
				</tr>
				<tr align="center">
					<td class="bg_table02" width="15%" align="right">预计合同签订日期：</td>
					<td class="bg_table02" align="left">
			  	<div>
			  	<input type="text" id="estimateSignDate" name="cbs.estimateSignDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('estimateSignDate')" align=absMiddle alt=""  />
		
			  	</td>	
					<td class="bg_table02" align="right">中标概率：</td>
					<td class="bg_table02" align="left">
						<s:textfield name="cbs.ownProbability"  />					
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" width="15%" align="right">中标厂家：</td>
					<td class="bg_table02" width="35%" align="left">
						<s:textfield name="cbs.ownFactory" />
					</td>
					<td class="bg_table02" align="right">项目跟踪结束：</td>
					<td class="bg_table02" align="left">
					<s:select name="cbs.projectStateSelect" list="#@java.util.TreeMap@{'off':'off','on':'on'}"></s:select>
					</td>
				</div>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">竞争厂家概述：</td>
					<td class="bg_table02" align="left">					
						<s:textarea name="cbs.competeInfo" cols="20" rows="5" id="competeInfo"/>
					 </td>
					<td class="bg_table02" align="right">备注：</td>
					<td class="bg_table02" align="left"><s:textarea name="cbs.remark" id="remark" cols="20" rows="5" /></td>
				</tr>
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<Table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="right" colspan="2"><input type="button" onclick="doSave(0)" class="button01" value="保存" />
								</td>
								<td colspan="2"><s:reset cssClass="button01" value="重置" /></td>
							</tr>
						</tfoot>
					</Table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
<script type="text/javascript">
	// 客户与联系人联动
	function getLinkMainOfClient(clientIdStr){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetLinkMainOfClient&clientId="+clientIdStr,document.getElementById("cbs.linkManId"),"id","name");
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
		getLinkMainOfClient(selObj.value);
		getClientOfOther(selObj.value);
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
		getLinkMainOfClient(clientObj.clientId);
		getClientOfOther(clientObj.clientId);
	}
	// 客户与行业类别联动
	function getClientOfOther(clientIdStr){
		var jsonRequest = new Request.JSON({url:"<s:url action="jsonData"></s:url>?method=doGetClientOfOther&clientId="+clientIdStr, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
				$("cbs.businessTypeId").value=jsonObj.jsonData.typeSmall;
				$("businessType").innerHTML=jsonObj.jsonData.typeName;
			 }else{
				$("cbs.businessTypeId").value="";
				$("businessType").innerHTML="";
			 }
		}}).get({randerNum:Math.random()});	 
	}
    //工程责任部门与项目负责人联动
	function getChargeManOfDepartment(obj){

		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetChargeManOfDepartment&departmentCode="+obj.value,document.getElementById("chargeManList"),"id","name",{value:"",text:"      "});
	}
	function departPChange(obj)
	{
		var tdNode=obj.parentNode.parentNode;
		var txtNode=tdNode.firstChild;
		txtNode.value=obj.options[obj.selectedIndex].text;
		
	}

	//验证通过表单提交
	function doSave(type){
		if(!validate()){
			if(type == 0)
			{
				document.forms(0).submit();
			}
		}
	}
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(contractBeforeSell){
		       ev2.test("notblank","项目名称为空,请输入项目名称!",$('cbs.projectName').value);
		       ev2.test("notblank","项目内容为空,请输入项目内容!",$('cbs.mainProjectContent').value);
		       ev2.test("notblank","项目预计金额为空,请输入金额数字!!",$('cbs.projectSum').value);
		       ev2.test("+float+0","项目预计金额不是数字,请输入0~9数字!",$('cbs.projectSum').value);
		       ev.test("notblank", "项目内容长度不正确", checkLength(0,200,$('cbs.mainProjectContent').value));
		       ev.test("notblank", "项目跟踪情况说明长度不正确", checkLength(0,200,$('cbs.descProjectFollow').value));
		       ev.test("notblank", "竞争厂家概述说明长度不正确", checkLength(0,200,$('cbs.competeInfo').value));
		       ev.test("notblank", "备注说明说明长度不正确", checkLength(0,500,$('cbs.remark').value));
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	function refreshClient(){
		location.reload();
	}	
var testValue=document.contractBeforeSell.succSave.value;
if(testValue!=null && testValue!="")
{
	if(testValue=="0")
	{
		alert("成功!");
	}else{
		alert("失败!");
	}
}
</script> 
</html>