<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>修改售前合同</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<body>
<s:form action="showSellBefore"  theme="simple">
<s:hidden name="method" value="updateSellBefore"></s:hidden>
<div align="left" style="color:#000000">首页：售前合同>修改售前合同</div>
	<s:iterator value="info.result" id="sellbefore">
	<input type="hidden" name="sellBeforeID" value="<s:property value="cbs.ID"/>" />
	<table width="99%" border="0" cellspacing="1" cellpadding="1"
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
					<input type="text" value="<s:property value="#sellbefore[1].name"/>" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select  name="clientSelectId" emptyOption="true" list="yxClientCodeList" cssStyle="margin-left:-150px;width:168px;" listKey="cli.id" listValue="cli.name" onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
					
					<input type="hidden" name="customerId" id="clientId" value="<s:property value="cbs.customerId"/>" />
					
					</td>
					<td class="bg_table02" align="right">行业类别：</td>
					<td class="bg_table02" align="left">
						<s:hidden name="cbs.businessTypeId"></s:hidden>
<!--						<s:property id="businessType" value="typeManageService.getYXTypeManage(1002,cbs.businessTypeId).typeName"/>-->
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
					<s:select name="cbs.linkManId" list="yxLinkManList" listKey="id" listValue="name" required="true" ></s:select>
					</td>
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
						</s:select>	</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right"><font color="red">* </font>项目主要内容：</td>
					<td class="bg_table02" align="left">
						<s:textarea name="cbs.mainProjectContent" ></s:textarea>
			  	</td>
					<td class="bg_table02" align="right">项目跟踪情况说明</td>
					<td class="bg_table02" align="left">
						<s:textarea name="cbs.descProjectFollow"></s:textarea>	
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">项目类型：</td>
					<td class="bg_table02" align="left">
					<s:select  name="cbs.projectTypeId" list="contractTypeList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="">
						</s:select></td>
					<td class="bg_table02" align="right">项目状况：</td>
					<td class="bg_table02" align="left">
						<s:select  name="cbs.projectStateId" list="projectStateList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="">
						</s:select></td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">工程责任部门：</td>
					<td class="bg_table02" align="left">
					<s:select name="cbs.dutyDepartmentId" headerKey="" headerValue="--请选择--"
							list="dutyDepartmentIdList" listKey="typeSmall"
							onchange="getChargeManOfDepartment(this)" listValue="typeName">
						</s:select></td>
					<td class="bg_table02" align="right">项目负责人：</td>
					<td class="bg_table02" align="left">
					<input type="text" name="cbs.projectManId" value="<s:property value="cbs.projectManId"/>" style="width: 90px; height: 21px; font-size: 10pt;" />
						<span style="width: 18px; border: 0px solid red;">
						<select style="margin-left: -90px; width:108px;" id="chargeManList"
								onchange="departPChange(this)">
							</select> 
						</span>
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
					<td class="bg_table02" align="right">投标（报价）金额</td>
					<td class="bg_table02" align="left">
						<s:textfield name="cbs.bidSum" ></s:textfield>
				    </td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">中标（合同）金额</td>
					<td class="bg_table02" align="left">
						<s:textfield name="cbs.ownSum" /></td>
					<td class="bg_table02" align="right">投标（报价）日期</td>						
					<td class="bg_table02" align="left">
			  	<div align="left">
			  	<input type="text" id="bidDate" name="cbs.bidDate" value="<s:property value="cbs.bidDate" />" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('bidDate')" align=absMiddle alt=""  />
			  	</div></td>	
				</tr>
				<tr align="center">
					<td class="bg_table02" width="15%" align="right">项目跟踪起始日期</td>
					<td class="bg_table02" align="left">
			  	<div align="left">
			  	<input type="text" id="projectDate" name="cbs.projectDate" value="<s:property value="cbs.projectDate" />" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('projectDate')" align=absMiddle alt=""  />
			  	</div></td>	
					<td class="bg_table02" align="right">项目计划投运日期</td>
					<td class="bg_table02" align="left">
			  	<div align="left">
			  	<input type="text" id="estimateProjectDate" value="<s:property value="cbs.estimateProjectDate" />" name="cbs.estimateProjectDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('estimateProjectDate')" align=absMiddle alt=""  />
			  	</div></td>	
				</tr>
				<tr align="center">
					<td class="bg_table02" width="15%" align="right">预计合同签订日期</td>
					<td class="bg_table02" align="left">
			  	<div align="left">
			  	<input type="text" id="estimateSignDate" value="<s:property value="cbs.estimateSignDate" />" name="cbs.estimateSignDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('estimateSignDate')" align=absMiddle alt=""  />
			  	</div></td>	
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
					<s:if test="cbs.projectStateSelect == on">
						<select size="1" name="cbs.projectStateSelect">
							<option id="off">off</option>
							<option selected="selected" id="on">on</option>
						</select>
					</s:if>
					<s:else>
						<select size="1" name="cbs.projectStateSelect">
							<option selected="selected" id="off">off</option>
							<option  id="on">on</option>
						</select>
					</s:else>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">竞争厂家概述：</td>
					<td class="bg_table02" align="left">					
						<s:textarea name="cbs.competeInfo" cols="20" rows="5" />
					 </td>
					<td class="bg_table02" align="right">备注：</td>
					<td class="bg_table02" align="left"><s:textarea name="cbs.remark" cols="20" rows="5" /></td>
				</tr>
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<Table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="right" colspan="2"><input type="button" value=" 修  改 " onclick="doSave()" class="button01" />
								</td>
								<td colspan="2">
									<input  type="button" value=" 返  回 " class="button01" name="button" onclick="fanhui()" />
								</td>
							</tr>
						</tfoot>
					</Table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</s:iterator>
</s:form>
</body>
<script type="text/javascript">
	function getLinkMainOfClient(clientIdStr,defaultValue){
		if(defaultValue == null){
			ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetLinkMainOfClient&clientId="+clientIdStr,document.getElementById("cbs.linkManId"),"id","name");
		}else{
			ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetLinkMainOfClient&clientId="+clientIdStr,document.getElementById("cbs.linkManId"),"id","name",{'defaultValue':defaultValue});
		}
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
	function getClientOfOther(clientIdStr){
		var jsonRequest = new Request.JSON({url:"<s:url includeParams="none" action="jsonData"></s:url>?method=doGetClientOfOther&clientId="+clientIdStr, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
				$("businessType").value=jsonObj.jsonData.typeSmall;
				$("businessType").innerHTML=jsonObj.jsonData.typeName;
			 }else{
				$("businessType").value="";
				$("businessType").innerHTML="";
			 }
		}}).get({randerNum:Math.random()});	 
	}
	  //工程责任部门与项目负责人联动
	function getChargeManOfDepartment(obj){

		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetChargeManOfDepartment&departmentCode="+obj.value,document.getElementById("chargeManList"),"id","name",{value:"",text:"      "});
	}
	function departPChange(obj)
	{
		var tdNode=obj.parentNode.parentNode;
		var txtNode=tdNode.firstChild;
		txtNode.value=obj.options[obj.selectedIndex].text;
		
	}
	//验证通过表单提交
	function doSave(){
		if(!validate()){
			document.forms(0).submit();
		}
	}
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(showSellBefore){
		       ev2.test("notblank","项目名称为空,请输入项目名称!",$('cbs.projectName').value);
		       ev2.test("notblank","项目内容为空,请输入项目内容!",$('cbs.mainProjectContent').value);
		       ev2.test("notblank","项目预计金额为空,请输入金额数字!!",$('cbs.projectSum').value);
		       ev2.test("+float+0","项目预计金额不是数字,请输入0~9数字!",$('cbs.projectSum').value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	function fanhui()
	{
		location.href="sellbefore/selectSellBefore.action";
	}
	getLinkMainOfClient('<s:property value="cbs.customerId"/>','<s:property value="cbs.linkManId"/>');
	getClientOfOther('<s:property value="cbs.customerId"/>');
	getChargeManOfDepartment('<s:property value="cbs.dutyDepartmentId"/>');
</script>
</html>