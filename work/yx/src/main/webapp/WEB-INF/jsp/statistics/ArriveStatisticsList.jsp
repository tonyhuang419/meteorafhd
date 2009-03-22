<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>实际到款统计</title>
</head>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
function popQuery(){
	window.open('../statistics/arriveStat.action?method=popQuery');
}
function openPage(customId){
	 window.open("../statistics/arriveStat.action?method=queryList&customId="+customId+" ");
}
function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
}
</script>
<body>
<s:form action="arriveStat"  theme="simple">
  <s:hidden name="method" value="queryList" ></s:hidden>
	<div align="left" style="color: #000000">
	<p>当前页面：统计查询 -> 实际到款统计</p>
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
				<td nowrap="nowrap">销售员</td>
				<td nowrap="nowrap">合同号</td>
				<td nowrap="nowrap">项目号</td>
				<td nowrap="nowrap">合同名称</td>
				<td nowrap="nowrap">客户简称</td>
				<td nowrap="nowrap">到款日期</td>
				<td nowrap="nowrap">到款金额</td>
			</tr>
			<s:set name="calTotalAmount" value="0.00"></s:set>
			<s:iterator value="info.result" id="reve">
				<tr class="bg_table02">
					<td nowrap="nowrap"><s:property value="#reve[0]" /></td>
					<td><s:property value="#reve[1]" /></td>
					<td><s:property value="#reve[2]" /></td>
					<td>
						<s:if test="#reve[3] != null">
							<a href="#" onclick="openCon('<s:property value="#reve[7].longValue()"/>')" ><s:property value="#reve[3]" /></a>
						</s:if>
						<s:else>
							待确定
						</s:else>
					</td>
					<td><s:property value="#reve[4]" /></td>
					<td nowrap="nowrap"><s:property value="#reve[5]" /></td>
					<td align="right" nowrap="nowrap"><s:property value="#reve[6]" /></td>
				</tr>
				<s:set name="calTotalAmount" value="#calTotalAmount + #reve[6]"></s:set>
			</s:iterator>
			<tr class="bg_table02">
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="right">本页小计：</td>
					<td align="right"><s:property value="#calTotalAmount" /></td>
			</tr>
			<tr class="bg_table02">
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="right">全部累计：</td>
					<td align="right"><s:property value="totalAmount" /></td>
			</tr>
			<tr class="bg_table04">		
				<td colspan="12" align="center"><table cellSpacing=1 cellPadding=2 width="100%" border=0>
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