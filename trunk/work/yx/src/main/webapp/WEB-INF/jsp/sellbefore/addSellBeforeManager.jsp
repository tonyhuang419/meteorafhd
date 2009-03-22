<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>添加售前合同</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/checkLength.js" type="text/javascript"></script>

</head>
<body>
<div align="left" style="color:#000000">
<s:if test ="comeFrom.equals('mod')">
当前页面：售前合同->修改售前合同
</s:if>
<s:else>
当前页面：售前合同->新增售前合同
</s:else>
</div>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<s:form action="contractBeforeSell" theme="simple" validate="true">
<s:if test="message!=null">
<div  style="color:#FF0000" align="left">
	<br/><s:property value="message"/> 
</div>
</s:if>
	<s:hidden name="method" value="saveCBS" />
	<s:hidden name="succSave"></s:hidden>
	<s:hidden name="selectExit" value="true"/>
	<s:hidden name="cbs.ID"/>
	<s:hidden name="cbs.sellBeforeNum"/>
	<s:hidden name="cbs.timeOfVary"/>
	<s:hidden name="comeFrom"/>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
				<td align="right" class="bg_table01" height="3"></td>	
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">				
					<td class="bg_table02" width="15%"  align="right"  ><font color="red">* </font>客户名称：</td>
					<td class="bg_table02"  align="left" width="30%"  >
					<input type="text" id="clientName"
							style="width: 140px; height: 21px; font-size: 10pt;"
							readonly="true" /><span style="width: 18px; border: 0px solid red;"> 
					<s:select id="cbs_customerId" name="cbs.customerId" list="yxClientCodeList"  
					listKey="cli.id" listValue="cli.name" headerKey="" headerValue="--请选择--" 
					cssStyle="margin-left:-140px;width:158px;" 
					 onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=600');">
					<s:hidden  id="clientId"></s:hidden>
					</td>
					<td class="bg_table02" align="right" >行业/性质：</td>
					<td class="bg_table02" align="left"  ><label id="businessType"></label><label id="customerNature"></label></td>
										<%-- 
					<td class="bg_table02" align="right">销售员：</td>
					<td class="bg_table02" align="left"><s:property value="user.name"/></td>--%>
					<s:hidden name="cbs.employeeId" value="%{user.id}"/>
				</tr>
				<tr>
					<td class="bg_table02" align="right">客户项目类型：</td>
					<td class="bg_table02" align="left">
						<s:select  name="cbs.customerProjectTypeId" list="projectTypeList" headerKey="" headerValue="--请选择--"  
						listKey="typeSmall" listValue="typeName" required="true"  cssStyle="width:158px" >
						</s:select>			
					</td>
					<td class="bg_table02" align="right">客户联系人及部门：</td>
					<td class="bg_table02" align="left">
						<s:hidden name="customerLinkMan" id="customerLinkMan"></s:hidden>
						<input type="text" onchange="setValueText(this,'customerLinkMan');" size="20" id="customerLinkManInput"/>
						电话：<s:textfield name="cbs.customerLinkManPhone" size="12" />
				</tr>
				
				<tr align="center">
					<td class="bg_table02" align="right"><font color="red">* </font>项目名称：</td>
					<td class="bg_table02" align="left">
					<s:textfield name="cbs.projectName" size="30" /></td>
					<td class="bg_table02" align="right">工程名称：</td>
					<td class="bg_table02" align="left">
					<s:textfield size="45" name="cbs.projectNameX"/></td>
			    </tr>
			    <tr>
					<td class="bg_table02" align="right" nowrap >甲方工程编号：</td>
					<td class="bg_table02" align="left"><s:textfield size="15" name="cbs.partyAProId"/></td>						
					<td class="bg_table02" align="right" nowrap >甲方项目进度：</td>
					<td class="bg_table02" align="left">
 						<s:select  name="cbs.projectStateId" list="projectStateList" listKey="typeSmall" listValue="typeName" required="true" headerValue=""></s:select>
                    </td>						
	
				</tr>
				
				<tr align="center">
					<td class="bg_table02" align="right">项目概况：</td>
					<td class="bg_table02" align="left">
						<s:textarea name="cbs.mainProjectContent" id="mainProjectContent" cols="24"></s:textarea>
			  		</td>
			  		<td class="bg_table02" align="right">工程概况：</td>
					<td class="bg_table02" align="left">
					<s:textarea name="cbs.projectSummary" cols="30"></s:textarea></td>
				</tr>
				
				<tr>
					<td class="bg_table02" align="right">预计金额：</td>
					<td class="bg_table02" align="left">						
						<s:textfield name="cbs.projectSum" maxlength="15" onblur="formatInputNumber(this)" id="cbsProjectSum" ></s:textfield>
					</td>
					<td class="bg_table02" align="right">计划投运日期：</td>
					<td class="bg_table02" align="left">
			  		<s:textfield id="estimateProjectDate" name="cbs.estimateProjectDate"  size="12" />
			  		<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('estimateProjectDate')" align=absMiddle alt=""  />

			  		</td>	
				</tr>
				
 				<tr align="center">
					<td colspan="4" align="right" class="bg_table02">
					<hr>
					</td>
				</tr>	
				<tr class="bg_table02">
					<td align="right">纳入重点工程：</td>
					<td align="left">
					<s:checkbox id="inImportantItem" name="inImportantItem"  onclick="changeIMP(this);"/>
					<span id="inIMP">
					<s:textfield size="20" name="impName" id="impName" readonly="true"/><input type="button" value="…"	
					onclick="openWin2('../sellbefore/importantProject.action?method=goQueryInfoForCBS',650,400,'impSearch');">
					<s:hidden name="importantProjectId"/></span>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right" class="bg_table02" >重点项目：</td>
					<td align="left" class="bg_table02">
					<s:if test="cbs.importantItem == true">
						<s:checkbox name="cbs.importantItem" disabled="true"/><s:hidden name="cbs.importantItem"/>
					</s:if>
					<s:else>
						<s:checkbox name="cbs.importantItem" />
					</s:else>
					</td>
					<td class="bg_table02" width="15%" align="right">项目跟踪起始日期：</td>
					<td class="bg_table02" align="left">
			  		<s:textfield id="projectDate" name="cbs.projectDate" size="12" />
			  		<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('projectDate')" align=absMiddle alt=""  />
			  		</td>
				</tr>
				<tr>
					<td class="bg_table02" align="right">工程责任部门：</td>
					<td class="bg_table02" align="left">
						<s:select name="cbs.dutyDepartmentId" headerKey="" headerValue="--请选择--"
							list="dutyDepartmentIdList" listKey="typeSmall"
							onchange="getChargeManOfDepartment(this)" listValue="typeName" >
						</s:select>					
					</td>
					<td class="bg_table02" align="right">项目负责人：</td>
					<td class="bg_table02" align="left" >
					<s:hidden name="cbs.projectManId" id="projectManId"></s:hidden>
					<input type="text" name="projectManIdInput" onchange="setValueText(this,'projectManId');"
					style ="width: 90px; height: 21px; font-size: 10pt;" /><span style="width: 18px; border: 0px solid red;">
						<s:select  cssStyle="margin-left: -90px; width:108px;" listKey="id" listValue="name" 
						list="chargeManList" id="chargeManList"
						 onchange="setValueSelect(this,'projectManId','projectManIdInput');displayPhone(this);"></s:select>
						</span>
						&nbsp;&nbsp;电话：<s:textfield name="cbs.itemRespeoplePhone" id="itemRespeoplePhone" size="19" />
					</td>
				</tr>
				
				<tr>
					<td class="bg_table02" align="right">报价（投标）金额：</td>
					<td class="bg_table02" align="left">
						<s:textfield name="cbs.bidSum" maxlength="15" onblur="formatInputNumber(this)"/>
				    </td>
				    <td class="bg_table02" align="right">中标（合同）金额：</td>
					<td class="bg_table02" align="left">
						<s:textfield name="cbs.ownSum" maxlength="15" onblur="formatInputNumber(this)" />
				</tr>
				
				<tr>
					<td class="bg_table02" align="right" nowrap>预定报价（投标）日期：</td>
					<td class="bg_table02" align="left">
			  		<s:textfield  id="bidDate" name="cbs.bidDate"  size="12" />
			  		<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('bidDate')" align=absMiddle alt=""  />
			  		</td>	
			  		<td class="bg_table02" width="15%" align="right" nowrap>预计合同签订日期：</td>
					<td class="bg_table02" align="left">
			  		<s:textfield id="estimateSignDate" name="cbs.estimateSignDate"  size="12" />
			  		<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('estimateSignDate')" align=absMiddle alt=""  />
				  	</td>	
				</tr>
				
				<tr>
			  		<td class="bg_table02" align="right">项目跟踪状态：</td>
					<td class="bg_table02" align="left">			
						<s:select  name="cbs.projectStateFollowId"  headerKey="" headerValue="--请选择--" list="projectStateFollowList" listKey="typeSmall" listValue="typeName" required="true">
						</s:select>	
					</td>
					<td class="bg_table02" align="right">项目跟踪结果：</td>
					<td class="bg_table02" nowrap="nowrap" align="left" >
					<s:select name="cbs.projectStateSelect" id="projectStateSelect" list="#@java.util.TreeMap@{1:'on',2:'off'}"
					 onchange="updateTraceList(this)"></s:select>
					<s:select  name="cbs.itemTraceResult" list="itemTrackList" listKey="typeSmall" 
					listValue="typeName" required="true"  id="itemTraceResultSelect"></s:select><span id="r1">说明：<s:textfield name="cbs.itemTraceResultReason" /></span>
					</td>
				</tr>

				<tr align="center">
					<td colspan="4" align="right" class="bg_table02">
					<hr>
					</td>
				</tr>	

				 <tr>
				 	<td class="bg_table02" align="right">项目跟踪情况说明：</td>
					<td class="bg_table02" align="left" colspan=3">
						<s:textarea name="cbs.descProjectFollow" id="descProjectFollow" cols="70" rows="3"/>
			  		</td>
				 </tr>
				 <%-- 
				<tr align="center">
					<td class="bg_table02" align="right">中标厂家：</td>
					<td class="bg_table02" align="left" colspan="3">
						<s:textfield name="cbs.ownFactory" maxlength="25" />
					</td>
				</tr>
				--%>
				<tr align="center">
					<td class="bg_table02" align="right">竞争厂家概述：</td>
					<td class="bg_table02" align="left" colspan="3">					
						<s:textarea name="cbs.competeInfo" cols="70" rows="3" id="competeInfo"/>
					 </td>
				</tr>
				<tr>
					<td class="bg_table02" align="right">备注：</td>
					<td class="bg_table02" align="left" colspan="3"><s:textarea name="cbs.remark" id="remark" cols="70" rows="3" /></td>
				</tr>

				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<Table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
							<s:if test=" comeFrom.equals('mod')">
								<td align="right" colspan="2">
									<input type="button" onclick="doSave(1)" class="button01" value="保存修改" />
								</td>
								<td align="right" colspan="2">
									<input type="button" onclick="goBack();" class="button01" value="返    回" />
								</td>
							</s:if>
							<s:else>
								<td align="right" colspan="2">
									<input type="button" onclick="doSave(0)" class="button01" value="保存" />
								</td>
								<td align="right" colspan="2">
									<input type="button" onclick="doSave(1)" class="button01" value="保存并关闭" />
								</td>
							</s:else>
							</tr>
						</tfoot>
					</Table>
					</td>
				</tr>
			</table>
</s:form>
</body>
<script type="text/javascript">
	function controlReasonInput(select){
		if(select=="on"){
			$("r1").style.display="none";
		}
		else{
			$("r1").style.display="block";
		}
	}

	function changeIMP(obj){
		if(obj.checked){
			$("inIMP").style.display="inline-block";
		}
		else{
			$("inIMP").style.display="none";
			$("impName").value="";
			$("importantProjectId").value="inline-block";
		}
	}
	
	function updateTraceList(obj){
		var valueX = obj.options[obj.selectedIndex].text;
		controlReasonInput(valueX);
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetTrackList&trace="+valueX,document.getElementById("itemTraceResultSelect"),"typeSmall","typeName");
	}

	function goBack(){
		window.location.href("/yx/sellbefore/selectSellBefore.action");
	}

	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		var selected =  selObj.options[selObj.selectedIndex].value;
		//getLinkMainOfClient(selected);
		getClientOfOther(selected);
		getClientOfNature(selected);
	}
	function selectedClient(clientObj){
		var selectValue = clientObj.clientId;
		var selectTest = clientObj.clientName;
		document.getElementById("clientName").value = selectTest;
		document.getElementById("clientId").value = selectValue;
		addOptionX("cbs_customerId", selectValue , selectTest , 1);
		//getLinkMainOfClient(clientObj.clientId);
		getClientOfOther(clientObj.clientId);
		getClientOfNature(clientObj.clientId);
	}
	// 客户与行业类别联动
	function getClientOfOther(clientIdStr){
		var jsonRequest = new Request.JSON({url:"/yx/jsonData.action?method=doGetClientOfOther&clientId="+clientIdStr, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
				$("businessType").innerHTML=jsonObj.jsonData.typeName+"/";
			 }else{
				$("businessType").innerHTML="";
			 }
		}}).get({randerNum:Math.random()});	 
	}
	// 客户与客户性质联动
	function getClientOfNature(clientIdStr){
		var jsonRequest = new Request.JSON({url:"/yx/sellbefore/jsonData.action?method=doGetClientOfNature&clientId="+clientIdStr, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
				$("customerNature").innerHTML=jsonObj.jsonData.typeName;
			 }else{
				$("customerNature").innerHTML="";
			 }
		}}).get({randerNum:Math.random()});	 
	}

	//显示联系人电话
	function displayPhone(obj){
		var jsonRequest = new Request.JSON({url:"/yx/jsonData.action?method=displayPhone&clientId="+obj.value, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
				$("itemRespeoplePhone").value=jsonObj.jsonData;
			 }else{
				$("itemRespeoplePhone").value="";
			 }
		}}).get({randerNum:Math.random()});	 
	}

	// 客户与联系人联动
	function getLinkMainOfClient(clientIdStr){
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetLinkMainOfClient&clientId="+clientIdStr,document.getElementById("linkManList"),"id","name",{defaultAsync:false,value:"",text:""});
		$("customerLinkManInput").value=""; 
	}
	
    //工程责任部门与项目负责人联动
	function getChargeManOfDepartment(obj){
		ajaxSetSelectOptions("/yx/sellbefore/jsonData.action?method=doGetChargeManOfDepartment&departmentCode="+obj.value,document.getElementById("chargeManList"),"id","name",{defaultAsync:false,value:"",text:""});
		$("projectManIdInput").value="";
		$('projectManId').value = "";
		$('itemRespeoplePhone').value = "";
	}

	function setValueText(obj,hiddenObj){
		var hid = document.getElementById(hiddenObj);
		var str = obj.value;
		hid.value = str;
	}
	
	function setValueSelect(obj,hiddenObj,textField){
		var hid = document.getElementById(hiddenObj);
		var str = obj.options[obj.selectedIndex].text;
		hid.value = str;
		var textFieldX = document.getElementById(textField);
		textFieldX.value= str;
	}
	

	//验证通过表单提交
	function doSave(type){
		if(!validate()){
			if(type == 0)
			{
				document.forms(0).submit();
			}
			if(type == 1){
				document.forms(0).method.value = "saveSBSSelect" ;
				document.forms(0).submit();
			}
		}
	}
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(contractBeforeSell){
		       ev2.test("notblank","请选择客户!",$('cbs.customerId').value);
		       ev2.test("notblank","项目名称不能为空!",$('cbs.projectName').value);    
		   //    ev2.test("length","项目名称请少于80字符",$('cbs.projectName').value ,80); 
		  //     ev2.test("length","甲方的项目工程编号请少于30字符",$('cbs.partyAProId').value ,30); 
		  //     ev2.test("length","项目概况请少于150字符",$('cbs.mainProjectContent').value,150); 		       
		  //     ev2.test("length","工程名称请少于20字符",$('cbs.projectNameX').value,20); 
		  //     ev2.test("length","工程概况请少于150字符",$('cbs.projectSummary').value,150); 
		  //     ev2.test("length","项目跟踪情况说明请少于100字符",$('cbs.descProjectFollow').value,100);       
		  //     ev2.test("length","客户联系人电话请少于50字符",$('cbs.customerLinkManPhone').value,50); 
		  //     ev2.test("length","项目跟踪结果请少于100字符",$('cbs.itemTraceResultReason').value,100); 
		  //     ev2.test("length","项目负责人请少于20字符",$('cbs.projectManId').value,20); 
		  //     ev2.test("length","竞争厂家概述请少于50字符",$('cbs.competeInfo').value,50); 
		 //      ev2.test("length","备注请少于500字符",$('cbs.remark').value,500); 	       
	      	   ev2.test("dateYX","投标（报价）日期格式不正确",$("cbs.bidDate").value);
	      	   ev2.test("dateYX","项目跟踪起始日期格式不正确",$("cbs.projectDate").value);
	      	   ev2.test("dateYX","项目计划投运日期格式不正确",$("cbs.estimateProjectDate").value);
	      	   ev2.test("dateYX","预计合同签订日期格式不正确",$("cbs.estimateSignDate").value);
	      	   if($('cbs.bidSum').value.length > 0 ){ 
	      	   		ev2.test("+float","报价（投标）金额大于0的数字!",$('cbs.bidSum').value);
	      	   }
	      	   if($('cbs.ownSum').value.length > 0 ){
	      	   		ev2.test("+float","中标（合同）金额大于0的数字!",$('cbs.ownSum').value);
	      	   }
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
		alert("保存成功!");
	}else{
		alert("失败!");
	}
}
function fullImpName(impId,impName){
	$("importantProjectId").value = impId;
	$("impName").value = impName;
}

var selected =  $('cbs_customerId').options[$('cbs_customerId').selectedIndex].value;
getClientOfOther(selected);
getClientOfNature(selected);

//初始化客户联系人
$('customerLinkManInput').value  = $('customerLinkMan').value; 
$('projectManIdInput').value  = $('projectManId').value; 
$('clientName').value = $('cbs_customerId').options[$('cbs_customerId').selectedIndex].text;
//设置预计金额0.00
if($('cbsProjectSum').value.length<1){
	$('cbsProjectSum').value="0.00";
}

controlReasonInput( $('projectStateSelect').options[$('projectStateSelect').selectedIndex].text  );

//设置重点工程checkbox
if($("importantProjectId").value.length>0){
	$("inImportantItem").checked = true;
	$("inIMP").style.display="inline-block";
}
else{
	$("inIMP").style.display="none";
}

</script> 
</html>