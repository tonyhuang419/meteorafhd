<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>取消收据关联发票</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script type="text/javascript">
	function search(){
		document.forms(0).submit();
	}

	function splitAmount()
	{ 
		var checkedNode=$$("input[name=planId][checked]");
		if(checkedNode.length <= 0){
			alert("请选择要拆分的计划");
			return;
		}else if(checkedNode.length > 1){
			alert("只能选择一个计划拆分");
			return;
		}else{
			window.open("/yx/billtoReceipt/splitBillAmountQuery.action?realPlanId="+checkedNode[0].value,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=300,width=400');
		}
	}
	
    function mergePlan(){
		var checkedNode=$$("input[name=planId][checked]");
		if(checkedNode.length <= 0){
			alert("请选择要合并的计划");
			return;
		}else if(checkedNode.length < 2){
			alert("至少要选择两个计划合并");
			return;
		}else if(confirm("确定要合并选中的计划吗？")){
			window.open("/yx/billtoReceipt/applyBillQuery.action?method=mergePlan&"+formsToQueryString(checkedNode,'monthlyBillproSids'),'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=900');
		}
	}		
	
	function modifyPlan(){
		var checkedNode=$$("input[name=planId][checked]");
		if(checkedNode.length <= 0){
			alert("请选择要拆分的计划");
			return;
		}else if(checkedNode.length > 1){
			alert("只能选择一个计划拆分");
			return;
		}else{
			window.open("/yx/system/basedata/baseData.action?method=editNoApplyBillPlan&planId="+checkedNode[0].value,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=300,width=400');
		}	
	}	
	
	function formsToQueryString(formArray,paramName){
		var queryString = [];
		formArray.each(function(el){
			if (!el.name || el.disabled) return;
			var value = (el.tagName.toLowerCase() == 'select') ? Element.getSelected(el).map(function(opt){
				return opt.value;
			}) : ((el.type == 'radio' || el.type == 'checkbox') && !el.checked) ? null : el.value;
			$splat(value).each(function(val){
				if (val) queryString.push(paramName + '=' + encodeURIComponent(val));
			});
		});
		return queryString.join('&');
	}
	
	function childWindowCallBack(){
		search();
	}
	
	function reflushPage(){
		search();
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
	<s:hidden name="method" value="listNoBillApplyPlan"></s:hidden>
	<table align="center" width="100%">
	<tr>
	<td>
		<table align=left>
			<tr>
				<td>合同号：<s:textfield name="conId" size="11" />
				&nbsp;&nbsp;<input type="button" onclick="search()" value="搜索" class="button01" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" onclick="modifyPlan()" value="调整计划" class="button01" />
				<input type="button" onclick="splitAmount()" value="拆分计划" class="button01" />
				<input type="button" onclick="mergePlan()" title="将拆分的计划并成一个计划" value="合并计划" class="button01" />
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
						<td class="bg_table01">选择</td>
						<td class="bg_table01">合同号</td>
						<td class="bg_table01">项目号</td>
						<td class="bg_table01">合同金额</td>
						<td class="bg_table01">合同名称</td>
						<td class="bg_table01">客户名称</td>
						<td class="bg_table01">负责部门</td>
						<td class="bg_table01">销售员</td>
						<td class="bg_table01">阶段</td>
						<td class="bg_table01">计划开票日期</td>
						<td class="bg_table01">开票性质</td>
						<td class="bg_table01">发票类型</td>
						<td class="bg_table01">开票金额</td>
						<td class="bg_table01">收款日期</td>
						<td class="bg_table01">收款金额</td>
						<td class="bg_table01">已开票金额</td>
						<td class="bg_table01">已收款金额</td>
					</tr>
					<s:iterator value="resultList" id="month" status="status">
						<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
							onMouseOut="this.bgColor='#FFFFFF';">
							<td><input type="checkbox" name="planId" value="<s:property value="#month[0].realConBillproSid"/>" /></td>
							<td align="left"><s:property value="#month[2].conId" /></td>
							<td align="left"><s:property value="#month[3].conItemId" /></td>
							<td align="left"><s:property value="#month[2].conTaxTamount" /></td>
							<td align="left"><s:property value="#month[2].conName" /></td>
							<td align="left"><s:property value="#month[1].name" /></td>
							<td align="left"><s:if test="#month[3] != null">
								<s:property
									value="typeManageService.getYXTypeManage(1018,#month[3].itemResDept).typeName" />
							</s:if> <s:else>
								<s:property
									value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName" />
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