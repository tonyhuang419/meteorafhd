<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>月度开票计划</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	
	// 点击月份触发onchange事件
	function monthChange(){
		var yearSel = document.getElementById("yearSel").value;
		var monthSel = document.getElementById("monthSel").value;
	 	location.href="../billtoReceipt/showMoonBillQuery.action?method=selMonth&monthSel="+monthSel+"&yearSel="+yearSel;
	}
		
	function monthApply(){

		document.showMoonBillQuery.action="/yx/contract/formalContractManage/formalContractInvoiceApply.action";	
		document.showMoonBillQuery.submit();
	}	
</script>
<body >
<s:form action="showMoonBillQuery" theme="simple">
	
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<div align="left">
	<p>当前页面：开票管理 -> 月度开票计划</p>
	
	</div>
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%" class="bg_table03">
				<tr>
					<td colspan="2" align="right" class="bg_table01" height="3"><img
						src="./../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr class="bg_table03">
					<td width="27%" align="right">
					<div align="center">
					<s:select name="yearSel" onchange="monthChange()" id="yearSel" list="yearMap"></s:select>
					 年 
					<s:select name="monthSel" list="{'01','02','03','04','05','06','07','08','09','10','11','12'}" id="monthSel" onchange="monthChange()" ></s:select> 月</div>
					</td>
					<td width="73%" align="left">
						&nbsp;
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill">
				<tr align="center">
					<td width="11%" class="bg_table01">合同号</td>
					<td width="18%" class="bg_table01">客户名称</td>
					<td width="12%" class="bg_table01">负责部门</td>
					<td width="10%" class="bg_table01">计划开票日期</td>
					<td width="9%" class="bg_table01">开票性质</td>
					<td width="13%" class="bg_table01">发票类型</td>
					<td width="9%" class="bg_table01">开票金额</td>
					<td width="14%" class="bg_table01">计划状态</td>
				</tr>
				<s:iterator value="info.result" id="month">
				<input type="hidden" name="rclist" value="<s:property value="#month[1].realConBillproSid"/>" />
				<input type="hidden" name="contractmainsid" value="<s:property value="#month[3].conMainInfoSid"/>" />
					<tr align="center">
						<td class="bg_table02"><s:property value="#month[3].conId" /></td>
						<td class="bg_table02"><s:property value="#month[2].name" /></td>
						<td class="bg_table02"><s:property
							value="typeManageService.getYXTypeManage(1018,#month[3].mainItemDept).typeName" /></td>
						<td class="bg_table02"><s:property
							value="#month[1].realPredBillDate" /></td>
						<td class="bg_table02"><s:property
							value="typeManageService.getYXTypeManage(1004,#month[1].billNature).typeName" /></td>
						<td class="bg_table02"><s:property
							value="typeManageService.getYXTypeManage(1004,#month[1].billType).typeName" /></td>
						<td class="bg_table02"><s:property
							value="#month[1].realBillAmount" /></td>
						<td class="bg_table02">计划状态</td>
					</tr>
				</s:iterator>
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>

			</td>
		</tr>
	</table>
</s:form>
</body>
</html>
