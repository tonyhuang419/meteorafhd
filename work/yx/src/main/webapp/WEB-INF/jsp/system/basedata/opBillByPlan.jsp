<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>全额开票全额收款</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
<script type="text/javascript">
	opPlanArr=new Array();
<s:if test="info != null">
	<s:iterator value="info.result" id="month" status="status">
	opPlanArr[opPlanArr.length]=new Array("<s:property value="#month[0].realConBillproSid"/>",
	<s:property value="(#month[0].billInvoiceAmount==null||#month[0].billInvoiceAmount==0.00)"/>,
	<s:property value="(#month[0].realArriveAmount==null||#month[0].realArriveAmount==0.00)"/>);
	</s:iterator>
</s:if>
	var opPlanId;
function search()
{
	document.baseData.submit();
}
function evaluate(obj,billDate,billAmount,receDate,receAmount)
{
	opPlanId=obj.value;
	if(obj.checked){
		document.getElementById("showOp").style.display="";
		document.baseData.preBillDate.value=billDate;
		document.baseData.preReceDate.value=receDate;
		document.getElementById("billAmount").innerHTML=billAmount;
		document.getElementById("receAmount").innerHTML=receAmount;
		var checkNode=document.forms(0).xuanze;
		var fullBill=getIsBill(obj.value);
		var fullRece=getIsRece(obj.value);
		checkNode[1].checked=false;
		checkNode[0].checked=true;
		if(fullRece){
			checkNode[1].checked=true;
		}
		
	}else{
	document.getElementById("showOp").style.display="none";
	}
}
function getIsBill(planId){
	if(opPlanArr!=null&&opPlanArr.length>0){
		for(var k=0;k<opPlanArr.length;k++){
			if(opPlanArr[k][0]==planId){
			return opPlanArr[k][1];
			}
		}
	}
}
function getIsRece(planId){
	if(opPlanArr!=null&&opPlanArr.length>0){
		for(var k=0;k<opPlanArr.length;k++){
			if(opPlanArr[k][0]==planId){
			return opPlanArr[k][2];
			}
		}
	}
}
function xuanzhong(obj){
	//如果没有开票也没有收款，必须选择开票，收款可选可不选
	//如果开票没有收款，开票不选，收款可选可不选，即使开票选了也没有用
	//不会存在有收款没有开票的。
		var fullBill=getIsBill(opPlanId);//true表示可以选择，false表示不能选择
		var fullRece=getIsRece(opPlanId);
		if(!fullBill){
			if(obj.value=="0")
			{
				obj.checked=true;
				alert("该计划已开票不能再开票");
			}
		}else{
			if(obj.value=="0")
			{
				obj.checked=true;
				alert("该计划未开票必须开票，否则不能收款");
			}
		}
}
function validate()
{
	var ev2=new Validator();
	var checkedNode=document.forms(0).xuanze;
	if(checkedNode[0].checked){
		ev2.test("notblank","实际开票日期不能为空",$('preBillDate').value);
		ev2.test("dateYX","实际开票日期输入格式不正确！",$('preBillDate').value);
	}
	if(checkedNode[1].checked){
		ev2.test("notblank","实际收款日期不能为空！",$('preReceDate').value);
		ev2.test("dateYX","实际收款日期输入格式不正确！",$('preReceDate').value);
		var loinStart=$('preReceDate').value.split("-");
		if(loinStart.length>1){
			var preRece=new Date(loinStart[0],loinStart[1]-1,loinStart[2],0,0,0,0);;
			var opNowDate=new Date(2008,9,1,0,0,0);
			if(preRece>opNowDate){
				ev2.addError("到款日期不能大于2008-10-1");
			}
			
		}
	}

	
	ev2.writeErrors(errorsFrame, "errorsFrame");
	if(ev2.size()>0)
	{
		return true;
	}
	return false;
}
function queding()
{
	var checkedNode=$$("input[name=xuanze][checked]");
	if(checkedNode.length==null||checkedNode.length<=0){
		alert("请选择开票或收款");
		return;
	}
	if(!validate()){
		if(!checkPlan()){
			alert("要先关联收据，再开发票");
			var  url="/yx/billtoReceipt/applyBillQuery.action?method=showRecitpQuery&monthlyBillproSids="+opPlanId;
			window.open(url);
		}else{
			var flag=window.confirm("您确定全额开票或收款吗？");
			if(flag){
				document.baseData.method.value="confirm";
				document.baseData.submit();
			}
		}
	}
}
function checkPlan()
{
	var flag=true;
	var url="yx/system/basedata/baseData.action";
  	var myRequest = new Request({url:url,async:false,method:'get'});
  	myRequest.addEvent("onSuccess",function(responseText, responseXML){
		if(responseText =='false'){
			flag = false;
		}else{
			flag = true;
		} 
   	});
	myRequest.send("method=checkIsReceAndRelationAmount&planId="+opPlanId+"&randomNum="+Math.random());
	return flag;
}
</script>
</head>
<body>
<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:iterator id="erMessage" value="processResult.errorMessages">
	<font color="red"><strong><s:property value="#erMessage"/></strong></font><br/>
</s:iterator>
<s:form action="baseData" theme="simple">
	<s:hidden name="method" value="queryPlanByconId"></s:hidden>
	<table align="center" width="100%">
	<tr>
	<td>
		<table align=left>
			<tr>
				<td>合同号：<s:textfield name="conId" size="11" />
				&nbsp;&nbsp;<input type="button" onclick="search()" value="搜索"	class="button01" /></td>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		<tr>
			<td>
			<table id="showOp" style="display: none;" align="left" width="70%">
				<tr>
					<td><input type="checkbox" name="xuanze" value="0" onclick="xuanzhong(this);"/>开票日期：</td>
					<td><s:textfield id="preBillDate" name="preBillDate" /><img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('preBillDate')" align=absMiddle alt=""  /></td>
					<td>开票金额：</td>
					<td>
					<div id="billAmount"></div>
					</td>
					<td rowspan="2"><input type="button" value="确定"
						class="button01" onclick="queding()" /></td>
				</tr>
				<tr>
					<td><input type="checkbox" name="xuanze" value="1" onclick="xuanzhong(this);"/>收款日期：</td>
					<td><s:textfield id="preReceDate" name="preReceDate" /><img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('preReceDate')" align=absMiddle alt=""/></td>
					<td>收款金额：</td>
					<td>
					<div id="receAmount"></div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill" bordercolor="#808080"
				style="border-collapse: collapse;">
				<s:if test="info != null">
					<s:set name="resultList" value="info.result"></s:set>
					<tr align="center">
						<td nowrap class="bg_table01">选择</td>
						<td nowrap class="bg_table01">合同号</td>
						<td nowrap class="bg_table01">项目号</td>
						<td nowrap class="bg_table01">合同名称</td>
						<td nowrap class="bg_table01">客户名称</td>
						<td nowrap class="bg_table01">负责部门</td>
						<td nowrap class="bg_table01">销售员</td>
						<td nowrap class="bg_table01">阶段</td>
						<td nowrap class="bg_table01">计划开票日期</td>
						<td nowrap class="bg_table01">开票性质</td>
						<td nowrap class="bg_table01">发票类型</td>
						<td nowrap class="bg_table01">开票金额</td>
						<td nowrap class="bg_table01">收款日期</td>
						<td nowrap class="bg_table01">收款金额</td>
						<td nowrap class="bg_table01">已开票金额</td>
						<td nowrap class="bg_table01">已收款金额</td>
					</tr>
					<s:iterator value="resultList" id="month" status="status">
						<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
							onMouseOut="this.bgColor='#FFFFFF';">
							<td><input type="radio" name="planId"
								onclick="evaluate(this,'<s:property value="#month[0].realPredBillDate"/>','<s:property value="#month[0].realBillAmount"/>','<s:property value="#month[0].realPredReceDate"/>','<s:property value="#month[0].realTaxReceAmount"/>')"
								<s:if test="!(#month[0].realArriveAmount==null||#month[0].realArriveAmount==0.00||#month[0].billInvoiceAmount==null||#month[0].billInvoiceAmount==0.00)"> disabled </s:if>
								value="<s:property value="#month[0].realConBillproSid"/>" /></td>
							<td align="left"><s:property value="#month[2].conId" /></td>
							<td align="left"><s:property value="#month[3].conItemId" /></td>
							<td align="left"><s:property value="#month[2].conName" /></td>
							<td align="left"><s:property value="#month[1].name" /></td>
							<td align="left"><s:if test="#month[3] != null">
								<s:property value="typeManageService.getYXTypeManage(1018,#month[3].itemResDept).typeName" />
							</s:if> <s:else>
								<s:property value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName" />
							</s:else></td>
							<td><s:property
								value="#month[7].name" /> </td>
							<td align="left"><s:property
								value="typeManageService.getYXTypeManage(1023,#month[4]).typeName" />
							</td>
							<td align="center"><s:property
								value="#month[0].realPredBillDate" /></td>
							<td align="left"><s:property
								value="typeManageService.getYXTypeManage(1012,#month[0].billNature).typeName" /></td>
							<td align="left"><s:property
								value="typeManageService.getYXTypeManage(1004,#month[0].billType).typeName" /></td>
							<td align="right"><s:property
								value="#month[0].realBillAmount" /></td>
							<td align="center"><s:property
								value="#month[0].realPredReceDate" /></td>

							<td><s:property value="#month[0].realTaxReceAmount" /></td>

							<td><s:if test="#month[0].billInvoiceAmount!=null">
								<s:property value="#month[0].billInvoiceAmount" />
							</s:if> <s:else>
						0.00
						</s:else></td>
							<td><s:if test="#month[0].realArriveAmount!=null">
								<s:property value="#month[0].realArriveAmount" />
							</s:if> <s:else>
						0.00
						</s:else></td>
						</tr>
					</s:iterator>
					<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04"><baozi:pages value="info"
								beanName="info" formName="forms(0)" /></td>
						</tr>
					</TABLE>
				</s:if>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>