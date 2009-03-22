<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>收款精度统计销售员明细</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="recePrecisionStat" theme="simple">
<s:hidden name="method" value="showReceDetial" />
<s:hidden name="saleMan" value="%{#parameters.saleMan}"></s:hidden>
<s:hidden name="yearSel" value="%{#parameters.yearSel}"></s:hidden>
<s:hidden name="monthSel" value="%{#parameters.monthSel}"></s:hidden>
  <div align="left" style="color:#000000">当前页面：收款精度统计->销售员明细</div>

			<table align="center" border=1 cellpadding="1" cellspacing=1 bordercolor="#808080" style=" border-collapse: collapse;"
				width="100%">	
			<tr align="center">	
					<td class="bg_table01" nowrap="nowrap">合同号</td>
					<td class="bg_table01" nowrap="nowrap">项目号</td>
					<td class="bg_table01" nowrap="nowrap">合同名称</td>
					<td class="bg_table01" nowrap="nowrap">客户名称</td>
					<td class="bg_table01" nowrap="nowrap">负责部门</td>
					<td class="bg_table01" nowrap="nowrap">开票性质</td>
					<td class="bg_table01" nowrap="nowrap">计划开票日期</td>
					<td class="bg_table01" nowrap="nowrap">实际开票日期</td>
					<td class="bg_table01" nowrap="nowrap">计划开票金额</td>
					<td class="bg_table01" nowrap="nowrap">已开票金额</td>
					<td class="bg_table01" nowrap="nowrap">计划收款日期</td>
					<td class="bg_table01" nowrap="nowrap">计划收款金额</td>
					<td class="bg_table01" nowrap="nowrap">已到款金额</td>
					<td class="bg_table01" nowrap="nowrap">逻辑帐龄</td>
					<td class="bg_table01" nowrap="nowrap">实际帐龄</td>
					<td class="bg_table01" nowrap="nowrap">销售员</td>
					<td class="bg_table01" nowrap="nowrap">计划</td>
					
				</tr>
				<s:iterator value="info.result" id="month">
					<tr  align="center" onMouseOver="this.bgColor='#BBBBFF';" onMouseOut="this.bgColor='#FFFFFF';">
						<td align="left"><s:property value="#month[2].conId" /></td>
						<td align="left"><s:property value="#month[3]"/></td>
						<td align="left" ><s:property value="#month[2].conName" /></td>
						<td align="left" ><s:property value="#month[1].name" /></td>
						<td align="left" ><s:property
							value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName" /></td>
						<td align="left" >
							<s:property value="typeManageService.getYXTypeManage(1012,#month[0].billNature).typeName" />
						</td>
						<td align="center" ><s:property value="#month[0].realPredBillDate" /></td>
						<td align="center" ><s:property value="#month[0].realNowBillDate" /></td>
						<td align="right" ><s:property value="#month[0].realTaxBillAmount" /></td>
						<td align="right" ><s:property value="#month[0].billInvoiceAmount" /></td>
						<td align="center" ><s:property value="#month[0].realPredReceDate" /></td>							
						<td align="right" >
							<s:if test="#month[5].isInsidePlan == 0">
								<!-- 计划内 -->
								<s:property value="#month[5].receTaxAmount" />
							</s:if>
							<s:else>
								<!-- 计划外 -->
								<s:property value="#month[0].realTaxReceAmount" />
							</s:else>						
						</td>
						<td align="right" >
							<s:if test="#month[5].isInsidePlan == 0">
								<!-- 计划内 -->
								<s:property value="#month[5].alreadyArrivedAmount?#month[5].alreadyArrivedAmount:0.0" />
							</s:if>
							<s:else>
								<!-- 计划外 -->
								<s:property value="#month[0].realArriveAmount?#month[0].realArriveAmount:0.0" />
							</s:else>
						</td>
						<td align="left" >
							<s:if test="#month[0].logicDayAccountAge != null">
								<s:property value="#month[0].logicDayAccountAge" />天(<s:property value="#month[0].logicMonthAccountAge" />月)
							</s:if>
						</td>
						<td align="left" >
							<s:if test="#month[0].realDayAccountAge != null">
								<s:property value="#month[0].realDayAccountAge" />天(<s:property value="#month[0].realMonthAccountAge" />月)
							</s:if>
						</td>
						<td align="left"><s:property value="#month[4]" /></td>
						<td align="center">
							<s:if test="#month[5].isInsidePlan == 1">
								N
							</s:if>
							<s:elseif test="#month[5].isInsidePlan == 0">
								Y
							</s:elseif>
						</td>
					</tr>
				</s:iterator>
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
