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
</script>
<body >
<s:form action="showMoonBillQuery" theme="simple">
<s:hidden name="method" value="showTotalBill" ></s:hidden>

	<div align="left"  style="color:#000000" >
	<p>当前页面：首页提醒 -> 开票计划</p>
	</div>

			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center" class="bg_table01">
					<td  nowrap="nowrap">合同号</td>
					<td  nowrap="nowrap">项目号</td>
					<td  nowrap="nowrap">合同名称</td>
					<td  nowrap="nowrap">客户名称</td>
					<td  nowrap="nowrap">负责部门</td>
					<td  nowrap="nowrap">开票性质</td>
					<td  nowrap="nowrap">发票类型</td>
					<td  nowrap="nowrap">阶段</td>
					<td  nowrap="nowrap">计划开票日期</td>
					<td  nowrap="nowrap">计划开票金额</td>
					<td  nowrap="nowrap">已开票金额</td>
					<td  nowrap="nowrap">销售员</td>
					<td  nowrap="nowrap">确认状态</td>
				</tr>
				<s:iterator value="info.result" id="month">
					<tr align="center"  onMouseOver="this.bgColor='#BBBBFF';" onMouseOut="this.bgColor='#FFFFFF';">
						<td align="left"><s:property value="#month[2].conId" /></td>
						<td align="left"><s:property value="#month[3]"/></td>
						<td align="left" ><s:property value="#month[2].conName" /></td>
						<td align="left" ><s:property value="#month[1].name" /></td>
						<td align="left" >
						<s:if test="#month[5]!=null">
							<s:property value="typeManageService.getYXTypeManage(1018,#month[5]).typeName" />
						</s:if>
						<s:else>
							<s:property value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName" />
						</s:else>
						</td>
						<td align="left" >
							<s:property value="typeManageService.getYXTypeManage(1012,#month[0].billNature).typeName" />
						</td>
						<td align="left" ><s:property
							value="typeManageService.getYXTypeManage(1004,#month[0].billType).typeName" /></td>
							
						<td align="center" ><s:property
							value="contractService.findStageName(#month[0].conItemStage)" /></td>		
												
						<td align="center" ><s:property
							value="#month[0].realPredBillDate" /></td>							
						<td align="right" ><s:property
							value="#month[0].realTaxBillAmount" /></td>
						<td align="right" >
						<s:if test="#month[0].billInvoiceAmount == null">
							0.00
						</s:if>
						<s:else>
							<s:property value="#month[0].billInvoiceAmount" />
						</s:else>
						</td>
						<td align="left"><s:property value="#month[4]" /></td>
						
						<td align="left">
							<s:if test="#month[0].applyBillState == null ">未确认</s:if>
							<s:elseif test="#month[0].applyBillState==1 || #month[0].applyBillState==2">已确认</s:elseif>
							<s:elseif test="#month[0].applyBillState==3">确认通过</s:elseif>
							<s:elseif test="#month[0].applyBillState==4">确认退回</s:elseif>
							<s:elseif test="#month[0].applyBillState==5">已开票</s:elseif>
							<s:elseif test="#month[0].applyBillState==7">已处理</s:elseif>
							<s:elseif test="#month[0].applyBillState==6">已签收</s:elseif>
						</td>
											
					</tr>
				</s:iterator>
					<tr align="center"  onMouseOver="this.bgColor='#BBBBFF';" onMouseOut="this.bgColor='#FFFFFF';">
						<td align="right" colspan="9">总计：</td>
						<td align="right" ><s:property value="sumArray[0]" /></td>
						<td align="right" ><s:property value="sumArray[1]" /></td>
						<td >&nbsp;</td>
						<td >&nbsp;</td>
					</tr>				
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>

</s:form>
</body>
</html>
