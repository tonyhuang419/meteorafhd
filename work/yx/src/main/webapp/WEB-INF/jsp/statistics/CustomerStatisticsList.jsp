<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>客户统计</title>
</head>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
	function popQuery(){
		openWin("../statistics/customerContractStat.action?method=popQuery",600,400);
	}
	function openPage(customId){
	    window.open("../statistics/formalContractStat.action?method=queryList&customId="+customId+"&isFromCustom=1&resetCondition=true");
	}
	function exportInfo(){
		var formX = document.getElementById("export");
		formX.submit();
	}
</script>
<body>
<s:form action="customerContractStat" theme="simple">
  <s:hidden name="method" value="queryList" ></s:hidden>
	<div align="left" style="color: #000000">
	<p>当前页面：统计查询 -> 客户统计</p>
	</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%"
		class="bg_table03">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr>
			<td align="center">
			<input type="button" onclick="popQuery()" value="查  询" class="button01" />
			<s:if test="info.result.size() > 0 ">
				<input type="button" value=" 导  出 " onclick="exportInfo();" class="button01" />
			</s:if>
			</td>
		</tr>
	</table>
		<s:if test="info!=null">
		<table align="center" border=1 cellpadding="1" cellspacing=1
			width="100%" id="mouthlyBill" bordercolor="#808080"
			style="border-collapse: collapse;">
			<tr align="center" class="bg_table01">
				<td>序号</td>
				<td>客户名称</td>
				<td>含税总金额</td>
				<td>不含税总金额</td>
			</tr>
			<s:set name="subTotalTaxAmount" value="0.00"></s:set>
			<s:set name="subTotalNoTaxAmount" value="0.00"></s:set>
			<s:iterator value="info.result" id="info">
				<tr onMouseOver="this.bgColor='#BBBBFF';"  onMouseOut="this.bgColor='#FFFFFF';"  onclick="openPage(<s:property value="#info[4].longValue()" />)" >
					<td align="center"><s:property value="#info[0].longValue()" /></td>
					<td><s:property value="#info[1]" /></td>
					<td align="right" ><s:property value="#info[2]" /></td>
					<td align="right"><s:property value="#info[3]" /></td>
					<s:set name="subTotalTaxAmount" value="#subTotalTaxAmount+#info[2]"></s:set>
					<s:set name="subTotalNoTaxAmount" value="#subTotalNoTaxAmount+#info[3]"></s:set>
				</tr>
			</s:iterator>
			<tr >
					<td align="center"></td>
					<td align="right">本次小计：</td>
					<td align="right"><s:property value="#subTotalTaxAmount" /></td>
					<td align="right"><s:property value="#subTotalNoTaxAmount" /></td>
			</tr>
			<s:iterator value="totalInfo" id="totalInfo">
			<tr >
					<td align="center"></td>
					<td align="right">全部累计：</td>
					<td align="right"><s:property value="#totalInfo[0]" /></td>
					<td align="right"><s:property value="#totalInfo[1]" /></td>
			</tr>
			</s:iterator>
			<tr class="bg_table04">		
				<td colspan="9" align="center"><table cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04"><baozi:pages value="info"
							beanName="info" formName="forms(0)" /></td>
						</tr>
					</table></td>				
			</tr>
		</table>
		</s:if>
</s:form>
<s:form id="export">
	<s:hidden name="method" value="queryList"></s:hidden>
	<s:hidden name="exportX" value="1" />
</s:form>
</body>
</html>