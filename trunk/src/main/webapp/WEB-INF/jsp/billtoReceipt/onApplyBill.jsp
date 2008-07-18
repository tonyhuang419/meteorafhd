	<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>已签开票申请计划</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	
	// 点击月份触发onchange事件
	function monthChange(){
		var yearSel = document.getElementById("yearSel").value;
		var monthSel = document.getElementById("monthSel").value;
	 	location.href="../billtoReceipt/applyBillQuery.action?method=selectMonth&monthSel="+monthSel+"&yearSel="+yearSel;
	}
		
	function splitAmount(realIdVal)
	{ 
		window.open('splitBillAmountQuery.action?realPlanId='+realIdVal,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=300,width=400');
	}	
		
	function monthApply(){
	var flag=0;
	   var chck=document.getElementsByName("monthlyBillproSids");
	   for(i=0;i<chck.length;i++){
	       if(chck[i].checked==true){
	           flag++;
	       }
	   }
	   if(flag>0){
			window.open("applyBillQuery.action?method=showApply&"+$(applyBillQuery).toQueryString(),'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=900');
	
           }else{
           alert("您没有选择任何一项!");
       }
	}	
	function refreshClient(){
		location.reload();
	}
	function torealhistory(){
	   var flag=0;
	   var chck=document.getElementsByName("monthlyBillproSids");
	   for(i=0;i<chck.length;i++){
	       if(chck[i].checked==true){
	           flag++;
	       }
	   }
	   if(flag>0){
               window.open("/yx/contract/realContractBillandRecePlan.action?"+$(applyBillQuery).toQueryString(),'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=900');
           }else{
           alert("您还没有选择,请选择要查看的开票计划变更");
       }
	
	}
	
</script>
<body >
<s:form action="applyBillQuery" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<div align="left">
	<p>当前页面：开票管理 -> 已签开票申请</p>
	
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
						<input type="button" name="button" value="确认开票" class="button02" onclick="monthApply()" />&nbsp;
						<input type="button" name="button" value="开票计划变更" class="button02" onclick="torealhistory();" />
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

					<td  class="bg_table01">选择</td>
					<td class="bg_table01">合同号</td>
					<td class="bg_table01">合同名称</td>
					<td  class="bg_table01">客户名称</td>
					<td  class="bg_table01">负责部门</td>
					<td  class="bg_table01">阶段</td>
					<td  class="bg_table01">计划开票日期</td>
					<td  class="bg_table01">开票性质</td>
					<td  class="bg_table01">发票类型</td>
					<td  class="bg_table01">开票金额</td>
					<td class="bg_table01">&nbsp;</td>
				</tr>
				<s:iterator value="info.result" id="month" status="status">
					<tr align="center">
				<s:if test="#month[4] > 0 ">
						
							<td class="bg_table02">
							 <s:property value="#bill"/>
								<input type="checkbox" disabled="disabled" name="monthlyBillproSids" 
									value="<s:property value="#month[0].realConBillproSid"/>" />
							</td>
						<td class="bg_table02"><s:property value="#month[2].conId" /></td>
						<td class="bg_table02"><s:property value="#month[2].conName" /></td>
						<td class="bg_table02"><s:property value="#month[1].name" /></td>
						<td class="bg_table02">
						<s:property
						value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName" /></td>
						<td class="bg_table02">
							<s:property value="typeManageService.getYXTypeManage(1023,#month[3]).typeName" />
					    </td>
						<td class="bg_table02"><s:property
							value="#month[0].realPredBillDate" /></td>
						<td class="bg_table02"><s:property
							value="typeManageService.getYXTypeManage(1022,#month[0].billNature).typeName" /></td>
						<td class="bg_table02"><s:property
							value="typeManageService.getYXTypeManage(1004,#month[0].billType).typeName" /></td>
						<td class="bg_table02"><s:property
							value="#month[0].realBillAmount" /></td>
							
							<td class="bg_table02" >
								&nbsp;
							</td>	
						</s:if>	
						<s:else>
							<td class="bg_table02">
							<s:property value="#bill"/>
								<input type="checkbox" name="monthlyBillproSids" 
									value="<s:property value="#month[0].realConBillproSid"/>" />
							</td>
						
						<td class="bg_table02"><s:property value="#month[2].conId" /></td>
						<td class="bg_table02"><s:property value="#month[2].conName" /></td>
						<td class="bg_table02"><s:property value="#month[1].name" /></td>
						<td class="bg_table02">
						<s:property
						value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName" /></td>
						<td class="bg_table02">
							<s:property value="typeManageService.getYXTypeManage(1023,#month[3]).typeName" />
					    </td>
						<td class="bg_table02"><s:property
							value="#month[0].realPredBillDate" /></td>
						<td class="bg_table02"><s:property
							value="typeManageService.getYXTypeManage(1022,#month[0].billNature).typeName" /></td>
						<td class="bg_table02"><s:property
							value="typeManageService.getYXTypeManage(1004,#month[0].billType).typeName" /></td>
						<td class="bg_table02"><s:property
							value="#month[0].realBillAmount" /></td>
							
							<td class="bg_table02" >
								<a href="#" onclick="splitAmount(<s:property value="#month[0].realConBillproSid"/>)">拆分</a>
							</td>							
						</s:else>
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
