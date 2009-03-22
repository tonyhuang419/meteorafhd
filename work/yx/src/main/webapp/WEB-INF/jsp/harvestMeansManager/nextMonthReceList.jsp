<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>

<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="corentMouthHarvestProgram" theme="simple">
  <s:hidden name="method" value="nextMonthReceList"/>
  <div align="left" style="color:#000000">当前页面：首页提醒->收款计划</div>

			<table align="center" border=1 cellpadding="1" cellspacing=1 bordercolor="#808080" style=" border-collapse: collapse;"
				width="100%">	
			<tr align="center">	
					<td class="bg_table01" nowrap="nowrap">合同号</td>
					<td class="bg_table01" nowrap="nowrap">项目号</td>
					<td class="bg_table01" nowrap="nowrap">合同名称</td>
					<td class="bg_table01" nowrap="nowrap">客户名称</td>
					<td class="bg_table01" nowrap="nowrap">负责部门</td>
					<td class="bg_table01" nowrap="nowrap">开票性质</td>
					<td class="bg_table01" nowrap="nowrap">阶段</td>
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
				</tr>
				<s:iterator value="info.result" id="month">
					<tr  align="center" onMouseOver="this.bgColor='#BBBBFF';" onMouseOut="this.bgColor='#FFFFFF';">
						<td align="left"><s:property value="#month[2].conId" /></td>
						<td align="left"><s:property value="#month[3]"/></td>
						<td align="left" ><s:property value="#month[2].conName" /></td>
						<td align="left" ><s:property value="#month[1].name" /></td>
						<td align="left" >
						<s:if test="#month[6]!=null">
							<s:property value="typeManageService.getYXTypeManage(1018,#month[6]).typeName" />
						</s:if>
						<s:else>
							<s:property value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName" />
						</s:else>
						</td>
						<td align="left" >
							<s:property value="typeManageService.getYXTypeManage(1012,#month[0].billNature).typeName" />
						</td>
						<td align="center" ><s:property
							value="contractService.findStageName(#month[0].conItemStage)" /></td>
						<td align="center" ><s:property value="#month[0].realPredBillDate" /></td>
						<td align="center" ><s:property value="#month[0].realNowBillDate" /></td>
						<td align="right" ><s:property value="#month[0].realTaxBillAmount" /></td>
						<td align="right" ><s:property value="#month[0].billInvoiceAmount" /></td>						
						<td align="center" ><s:property value="#month[0].realPredReceDate" /></td>							
						<td align="right" ><s:property value="#month[0].realTaxReceAmount-(#month[5]==null? 0.0 :#month[5])" /></td>
						<td align="right" >
							0.0
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
					</tr>
				</s:iterator>
				<tr align="center"  onMouseOver="this.bgColor='#BBBBFF';" onMouseOut="this.bgColor='#FFFFFF';">
						<td align="right" colspan="11">总计：</td>
						<td align="right" ><s:property value="sumArray[0]" /></td>
						<td align="right" ><s:property value="sumArray[1]" /></td>
						<td >&nbsp;</td>
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
<script type="text/javascript">

</script>
</body>
</html>
