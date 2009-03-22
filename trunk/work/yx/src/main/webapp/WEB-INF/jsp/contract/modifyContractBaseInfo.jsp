<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css"
	rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<html>
<head>
<title>合同基础信息修改</title>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style>

<script language="javascript">

</script>

</head>
<body>
<div align="left" style="color: #FF0000"><iframe
	name="errorsFrame" frameborder="0" framespacing="0" height="0"
	scrolling="no"></iframe></div>
<s:form id="contractBaseInfoChange" theme="simple"
	action="modifyContractBaseInfo">
	<input type="hidden" id="showhidden" />
	<s:hidden name="method" value="save" />
	<table width="100%" border="0" align="center" cellpadding="1"
		cellspacing="1" class="bg_table02">
		<tr>
			<td align="center" class="bg_table01">
			<table align="center" border=0 cellpadding="1" cellspacing="1"
				width="100%">
			</table>
			</td>
		</tr>
		<tr class="bg_table02">
			<td colspan="4" align="center" class="bg_table02">
			<div id="container" class="bg_table02">

			<div align="center">
			<table width="100%" border="0" align="center" cellpadding="1"
				cellspacing="1" class="bg_table02">
				<tr>
					<td align="center">
					<table align="center" border=0 cellpadding="1" cellspacing="1"
						width="100%">
					</table>
					</td>
				</tr>

				<tr class="bg_table02">
					<td colspan="4" align="center" class="bg_table02">
					<div id="title" class="bg_table02">
					<div align="center" class="bg_table02">
					<ul class="bg_table02">
						<li id="tag1"><a href="#"
							onClick="switchTag2('tag1','content1');this.blur();"><span>主体信息</span></a></li>
					</ul>
					</div>
					</div>
					</div>
					<!--contract main info start-->
					<div id="content1" class="content1">
					<table width="100%">
						<s:hidden id="contractmainsid" name="contractmainsid"
							value="${cmi.conMainInfoSid}" />
						<%--合同主体系统号--%>

						<tr class="bg_table02">
							<td width="20%" align="right"><font color="red">* </font>组别：</td>
							<td width="30%" align="left"><s:select name="groupId"
								onchange="getEmployeeOfDepartment(this.value)" list="groupList"
								listKey="departmentCode" listValue="departmentName"></s:select>
							</td>
							<td align="right"><font color="red">* </font>销售员：</td>
							<td align="left"><s:select id="saleManSelect"
								name="cmi.saleMan" list="listExp" listKey="id" listValue="name"
								required="true" headerValue=""></s:select></td>
						</tr>

						<tr class="bg_table02">
							<td align="right">
							<div align="right"><font color="red">* </font>客户名称：</div>
							</td>
							<td align="left" width="30%">
							<div align="left"><s:select id="conCustomerSelect"
								name="cmi.conCustomer" list="clientlist" headerKey=""
								headerValue="--请选择--" cssStyle="width:168px;" listKey="id"
								listValue="name" onchange="getLinkMainOfClient(this.value);"></s:select>
							<input type="button" value="..." id="cmc"
								onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getSelectClientlist','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
							</td>
							<td>
							<div align="right"><font color="red">* </font>开票客户：</div>
							</td>
							<td nowrap>
							<div align="left"><s:select id="billCustomerSelect"
								name="cmi.billCustomer" headerKey="" headerValue="--请选择--"
								cssStyle="width:168px;" list="allclientlist" listKey="id"
								listValue="name">
							</s:select> <input type="button" id="billCus" value="..."
								onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
							<a href="javascript:showClientInfo();">查看详情</a></div>
							</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02"><font color="red">*
							</font>项目单位：</td>
							<td align="left" class="bg_table02"><s:select
								cssStyle="width:168px;" name="cmi.itemCustomer"
								id="itemCustomerSelect" headerKey="" headerValue="--请选择--"
								list="eventclientlist" listKey="id" listValue="name"></s:select>
							<input type="button" id="cmiX" value="..."
								onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
							</td>
							<td width="20%" align="right" class="bg_table02">&nbsp;</td>
							<td width="30%" align="left" class="bg_table02">&nbsp;</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">
							<div align="right">客户联系人：</div>
							</td>
							<td align="left" class="bg_table02">
							<div align="left"><s:hidden name="linkManName"
								id="linkManNameHidden"></s:hidden> 
								<input type="text" id="linkManNameInput"
								style="width: 90px; height: 21px; font-size: 10pt;"
								onchange="setValueText(this,'linkManNameHidden')" /><span
								style="width: 18px; border: 0px solid red;"><s:select
								id="linkmanlist" list="linkmanlist" name="cmi.linkManId"
								cssStyle="margin-left: -90px; width: 108px;"
								onchange="cusLinkManSelectChange();" required="true"
								listKey="id" listValue="name" headerKey="" headerValue=""></s:select></div>
							</td>
							<td align="right" class="bg_table02">
							<div align="right">甲方合同号：</div>
							</td>
							<td align="left" class="bg_table02"><s:textfield
								name="cmi.partyAConId" /></td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">
							<div align="right">客户项目类型：</div>
							</td>
							<td align="left" class="bg_table02">
							<div align="left"><s:select name="cmi.customereventtype"
								list="customeventypelist" listKey="typeSmall"
								listValue="typeName">
							</s:select></div>
							</td>
							<td class="bg_table02" align="right">
							<div align="right">甲方的项目工程编号：</div>
							</td>
							<td class="bg_table02" align="left"><s:textfield
								name="cmi.partyAProId" /></td>
						</tr>
						<tr>
							<td colspan="4" align="right" class="bg_table02">
							<hr align="right">
							</td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">
							<div align="right">正式合同号：</div>
							</td>
							<td align="left" class="bg_table02"><s:textfield
								name="cmi.conId" onchange="checkConNum(this);" /> <s:hidden
								id="orgConId"></s:hidden></td>
							<td class="bg_table02" align="right">
							<div align="right">售前合同号：</div>
							</td>
							<td align="left" class="bg_table02">
							<div align="left"><s:property value="cmi.preConSysid" /></div>
							</td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">
							<div align="right"><font color="red">* </font>合同名称：</div>
							</td>
							<td align="left" class="bg_table02"><s:textarea
								name="cmi.conName" /></td>
							<td align="right" class="bg_table02">
							<div align="right"><font color="red">* </font>主项目部门：</div>
							</td>
							<td align="left" class="bg_table02">
							<div align="left"><s:select id="mainItemDept"
								name="cmi.mainItemDept"
								onchange="getChargeManOfDepartment(this.value,true)"
								list="dutydepartmentlist" listKey="typeSmall"
								listValue="typeName"></s:select></div>
							</td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">
							<DIV align="right"><font color="red">* </font>预决算信息：</DIV>
							</td>
							<td class="bg_table02" align="left"><s:select
								list="#@java.util.TreeMap@{0:'非预决算',1:'预决算'}"
								name="cmi.FinalAccount"></s:select></td>
							<td class="bg_table02" align="right">
							<div align="right">主项目负责人：</div>
							</td>
							<td class="bg_table02" align="left">
							<div align="left">
							<s:hidden name="cmi.mainItemPeople" id="mainItemPeopleHidden"></s:hidden>
							<input  id="mainItemPeopleName" name="mainItemPeople"
								style="width:90px; height: 21px; font-size: 10pt;"
								onchange="setMainItemPeopleId(this);" /><span
								style="width: 18px; border: 0px solid red;"><select
								style="margin-left: -90px; width:108px;" id="chargeManList"
								onchange="departMChange(this)"></select> </span></div>
							</td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">
							<div align="right"><font color="red">* </font>合同类型：</div>
							</td>
							<td class="bg_table02" align="left">
							<div align="left"><s:select list="contracttype"
								name="cmi.ContractType" listKey="typeSmall" listValue="typeName"></s:select>
							</div>
							</td>

							<td align="right" class="bg_table02">
							<div align="right"><font color="red">* </font>合同性质：</div>
							</td>
							<td align="left" class="bg_table02">
							<div align="left"><s:select list="contractnature"
								name="cmi.conType" listKey="typeSmall" listValue="typeName"></s:select>
							</div>
							</td>

						</tr>
						<tr>
							<td align="right" class="bg_table02">
							<div align="right">基准：</div>
							</td>
							<td align="left" class="bg_table02"><s:if
								test="cmi.standard == 1">
               		含税
               </s:if> <s:elseif test="cmi.standard == 2">
               		不含税
               </s:elseif></td>
							<td align="right" class="bg_table02">&nbsp;</td>
							<td align="left" class="bg_table02">&nbsp;</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">
							<div align="right">合同总金额：</div>
							</td>
							<td align="left" class="bg_table02"><s:property
								value="cmi.conTaxTamount" /></td>
							<td align="right" class="bg_table02">
							<div align="right">合同不含税金额：</div>
							</td>
							<td align="left" class="bg_table02"><s:property
								value="cmi.conNoTaxTamount" /></td>
						</tr>


						<tr>
							<td class="bg_table02" align="right">货币单位：</td>
							<td class="bg_table02" align="left"><s:property
								value="typeManageService.getYXTypeManage(1015,cmi.copeck).typeName" />
							</td>
							<td align="right" class="bg_table02">基准汇率：</td>
							<td align="left" class="bg_table02"><s:property
								value="cmi.baserate" /></td>
						</tr>

						<tr>
							<td colspan="4" align="right" class="bg_table02">
							<hr align="right">
							</td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">
							<div align="right"><font color="red">* </font>合同签订日期：</div>
							</td>
							<td class="bg_table02" align="left">
							<div align="left"><s:textfield name="cmi.conSignDate"
								id="conSignDate" size="12" /> <img
								src="/yx/commons/images/calendar.gif"
								onClick="javascript:ShowCalendar('conSignDate')"
								align="absMiddle" alt="" /></div>
							</td>
							<td class="bg_table02" align="right">
							<div align="right">退税：</div>
							</td>
							<td class="bg_table02" align="left">
							<div align="left"><s:checkbox name="cmi.conDrawback"></s:checkbox>
							</div>
							</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">
							<div align="right"><font color="red">* </font>合同起始日期：</div>
							</td>
							<td align="left" class="bg_table02">
							<div align="left"><s:textfield name="cmi.conStartDate"
								id="conStartDate" size="12" /> <img
								src="/yx/commons/images/calendar.gif"
								onClick="javascript:ShowCalendar('conStartDate')"
								align=absMiddle alt="" /></div>
							</td>
							<td class="bg_table02" align="right">
							<div align="right">中标合同：</div>
							</td>
							<td class="bg_table02" align="left">
							<div align="left"><s:checkbox name="cmi.conBid"></s:checkbox>
							</div>
							</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">
							<div align="right"><font color="red">* </font>合同结束日期：</div>
							</td>
							<td align="left" class="bg_table02">
							<div align="left"><s:textfield name="cmi.conEndDate"
								id="conEndDate" size="12" /> <img
								src="/yx/commons/images/calendar.gif"
								onClick="javascript:ShowCalendar('conEndDate')" align=absMiddle
								alt="" /></div>
							</td>
							<td class="bg_table02" align="right">
							<div align="right">纳入年度运维合同：</div>
							</td>
							<td class="bg_table02" align="left">
							<div align="left"><s:checkbox name="cmi.IntoYearCon"></s:checkbox>
							</div>
							</td>
						</tr>

						<tr>
							<td colspan="4" align="right" class="bg_table02">
							<hr align="right">
							</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">
							<div align="right">开票状态：</div>
							</td>
							<td class="bg_table02" align="left">
							<div align="left"><s:property value="contractBillState" /></div>
							</td>
							<td align="right" class="bg_table02">
							<div align="right">收款状态：</div>
							</td>
							<td align="right" class="bg_table02">
							<div align="left"><s:property value="contractReceState" /></div>
							</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">
							<div align="right">合同状态：</div>
							</td>
							<td class="bg_table02" align="left">
							<div align="left"><s:property
								value="foramlContractService.covConStateSnToName(cmi.conState)" />
							</div>
							</td>
							<td align="right" class="bg_table02">
							<div align="right"></div>
							</td>
							<td align="right" class="bg_table02">
							<div align="left"></div>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="right" class="bg_table02">
							<hr align="right">
							</td>
						</tr>
					</table>

					<table align="center" cellpadding="1" cellspacing=1 width="100%"
						border="1" bordercolor="#808080"
						style="border-collapse: collapse;">
						<tr>
							<td align="center" class="bg_table01">编号</td>
							<td align="center" class="bg_table01">费用名称</td>
							<td align="center" class="bg_table01">金额</td>
							<td align="center" class="bg_table01">开票类型</td>
						</tr>
						<s:iterator value="mainMoneyList" status="mainMoneyList">
							<tr>
								<td align="center">
								<s:hidden name="%{'mainMoneyList['+#mainMoneyList.index+'].id'}" ></s:hidden>
								<s:property
									value="#mainMoneyList.index+1" /></td>
								<td align="left">
								<s:select list="itemdesigntypelist" listKey="typeSmall" listValue="typeName" name="%{'mainMoneyList['+#mainMoneyList.index+'].moneytype'}"></s:select>
								</td>
								<td align="right"><s:property value="money" /></td>
								<td align="left"><s:property
									value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
							</tr>
						</s:iterator>

					</table>
					</div>

					<div align="center"><input type="button" class="button01"
						value=" 保  存 " onClick="doSubmit()" /> <input type="button"
						class="button01" onclick="window.close()" value="关  闭" /></div>
					</s:form>
</body>

<script language="javascript">
	var partCount  = <s:property value = "mainMoneyList.size()"/>;
	function checkPartRepate(){
		var partArr = new Array();
		for(var i = 0; i < partCount; i ++ ){
			var selectNode = document.contractBaseInfoChange.elements("mainMoneyList["+i+"].moneytype");
			partArr.push(selectNode.options[selectNode.selectedIndex].value);
		}
		
		return checkValueisRepate(partArr);
	}

function getLinkMainOfClient(clientIdStr){
    ajaxSetSelectOptions("/yx/jsonData.action?method=doGetLinkMainOfClient&clientId="+clientIdStr,document.getElementById("linkmanlist"),"id","name",{defaultAsync:false,value:"",text:""});
    $('linkManNameInput').value="";
	$('linkManNameHidden').value="";
}

function selectedClient(client){
	var i= client.clientId;
	var j= client.clientName;
	var option=document.createElement("option");
	option.text=""+j+"";
	option.value=""+i+"";
	
	 var s = document.getElementById("showhidden").value;
	 if(s == "cmc" && existSelect($("conCustomerSelect") ,i )){
	 	$("conCustomerSelect").add(option);
	 	$("conCustomerSelect").selectedIndex = $("conCustomerSelect").length-1;
	 	getLinkMainOfClient(i);
	 }
	 else if(s == "billCus" && existSelect($("billCustomerSelect") ,i ) ){
		$("billCustomerSelect").add(option);
		$("billCustomerSelect").selectedIndex = $("billCustomerSelect").length-1;
	 }
	 else if(s == "cmiX" && existSelect($("itemCustomerSelect") ,i ) ){
		$("itemCustomerSelect").add(option);
		$("itemCustomerSelect").selectedIndex = $("itemCustomerSelect").length-1;
	}
}

function setShowId(buttonid){	
    var hidden=document.getElementById("showhidden");
    hidden.value = buttonid;
}

function existSelect(selectObj,str){
	var len = selectObj.length;
	for(var i=0; i<len; i++){
	      if( selectObj[i].value == str ) {
	    	  selectObj.selectedIndex = i;
	      	return false;
	      } 
	}
	return true;
}


function clientChange(selObj){
	document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
	document.getElementById("clientId").value = selObj.value;
}

function showClientInfo(){
    var customerId=document.forms(0).elements("cmi.billCustomer").value;
    var url="yx/contract/showOneClientInfo.action?clientId="+customerId;
    if(customerId!=null&&customerId.length>0){
    	openWin(url,400,300);
    }else{
    	alert("请选择客户");
    }	
}
	
function doSubmit(){
	if(!validate()){
		if(checkPartRepate()){
		document.forms(0).submit();
		}else{
			alert("费用名称重复！");
		}
	}
}
	
function validate(){
	 var ev = new Validator();
     with(contractBaseInfoChange){  
     	ev.test("notblank","销售员不能为空",$('saleManSelect').value); 
    	ev.test("notblank","客户不能为空",$('conCustomerSelect').value);   
     	ev.test("notblank","开票客户不能为空",$('billCustomerSelect').value); 
        ev.test("notblank","项目单位不能为空",$('itemCustomerSelect').value); 
        ev.test("notblank","合同名称不能为空",$('cmi.conName').value); 
        ev.test("notblank","合同签订日期不能为空",$('cmi.conSignDate').value);
        ev.test("notblank","合同起始日期不能为空",$('cmi.conStartDate').value);
        ev.test("notblank","合同结束日期不能为空",$('cmi.conEndDate').value);
        ev.test("dateYX","合同签订日期不正确",$('cmi.conSignDate').value);
        ev.test("dateYX","合同起始日期不正确",$('cmi.conStartDate').value);
        ev.test("dateYX","合同结束日期不正确",$('cmi.conEndDate').value);
     }
     ev.writeErrors(errorsFrame, "errorsFrame");
	 if (ev.size() > 0) {
		return true;  
	}
}

function getChargeManOfDepartment(departmentCode,change){
	ajaxSetSelectOptions("/yx/jsonData.action?method=doGetChargeManOfDepartment&departmentCode="+departmentCode,$('chargeManList'),"id","name",{defaultAsync:false,value:"",text:""});
	if(change){
		$('mainItemPeopleName').value="";
		$('mainItemPeopleHidden').value="";
	}
}


function checkConNum(obj){
	var srcConId = $("orgConId").value;
	if( !(obj.value == srcConId)){
		var jsonRequest = new Request.JSON({async:false,url:'/yx/jsonData.action?method=uniqueConNum&conNum='+obj.value, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
				if( !parseInt(jsonObj.jsonData)){
					alert("该合同号已存在");	
					$('cmi.conId').value = srcConId;
				}
			}
			}}).get({randerNum:Math.random()});	
	}
}

function departMChange(obj){
	$("mainItemPeopleName").value=obj.options[obj.selectedIndex].text;
	$('mainItemPeopleHidden').value=obj.options[obj.selectedIndex].text;
}

function setMainItemPeopleId(obj){
	$('mainItemPeopleHidden').value = obj.value;
}

function setValueText(obj,hiddenObj){
	var hid = document.getElementById(hiddenObj);
	var str = obj.value;
	hid.value = str;
}

function cusLinkManSelectChange(){
	$('linkManNameInput').value=$('linkmanlist')[$('linkmanlist').selectedIndex].text;
	$('linkManNameHidden').value=$('linkmanlist')[$('linkmanlist').selectedIndex].text;
}

function getEmployeeOfDepartment(departmentCode){
	ajaxSetSelectOptions("/yx/jsonData.action?method=doGetEmployeeOfDepartment&departmentCode="+departmentCode,document.contractBaseInfoChange["cmi.saleMan"],"id","name");
}

//初始化主项目负责人
getChargeManOfDepartment($("mainItemDept").value,false);
$('mainItemPeopleName').value = $('mainItemPeopleHidden').value
//初始化客户联系人
<s:if test="cmi.linkManId!=null">
cusLinkManSelectChange();
</s:if>
//初始化合同号...比较用
$("orgConId").value = $("cmi.conId").value;
</script>
</html>
