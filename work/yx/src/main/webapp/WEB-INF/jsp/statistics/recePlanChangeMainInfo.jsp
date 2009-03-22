<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>收款计划变更统计主页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<script type="text/javascript">
function popwin()
{
	window.open('yx/statistics/receProjectChangeStat.action?method=popQuery','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');
	//window.open('yx/statistics/billStat.action?method=popQuery');
}
function showChangeDetials(opValue){
	window.open('yx/statistics/receProjectChangeStat.action?method=popShowDetails&planId='+opValue,'newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=1000');
}
function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
}
</script>
</head>
<body>
<s:form action="receProjectChangeStat" theme="simple">
	<s:hidden name="method" value="showMainInfo"></s:hidden>
	<div align="left" style="color: #000000">
	<p>当前页面：统计查询 -> 收款计划变更统计</p>
	</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%"
		class="bg_table03">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr>
			<td align="center">
			<input type="button" onclick="popwin();" value="查询" class="button01" />
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
			<tr class="bg_table01">
				<td align="center">销售员</td>
				<td align="center">合同号</td>
				<td align="center">项目号</td>
				<td align="center">合同名称</td>
				<td align="center">客户名称</td>
				<td align="center">新收款日期</td>
				<td align="center">原始收款日期</td>		
				<td align="center">预计收款金额</td>
				<td align="center">变更数</td>
				<td align="center">合同阶段</td>
				<td align="center">最后变更日期</td>
			</tr>
			<s:set name="subTotalPreRece" value="0.00"></s:set>
			<s:iterator value="info.result" id="bill">
				<tr class="bg_table02">
					<td><s:property value="#bill[0]" /></td>
					<td><s:property value="#bill[1]" /></td>
					<td><s:property value="#bill[2]" /></td>
					<td><a href="#" onclick="openCon('<s:property value="#bill[13].longValue()"/>')" ><s:property value="#bill[3]" /></a></td>
					<td><s:property value="#bill[4]" /></td>
					<td align="center"><s:property value="#bill[5]" /></td>
					<td align="center"><s:property value="#bill[12]" /></td>
					<td align="right"><s:property value="#bill[7]" /></td>
					<td align="right">
					<s:if test="#bill[8]==null">
					0.00
					</s:if>
					<s:else>
						<a href="javascript:showChangeDetials('<s:property value="#bill[11].longValue()" />')"><s:property value="#bill[8].longValue()" /></a>
					</s:else>
					</td>
					<td><s:property value="#bill[9]" />
					<s:if test="#bill[10]!=null">
					(<s:property value="#bill[10]"/>)
					</s:if>
					</td>
					<td align="center"><s:property value="#bill[6]" /></td>
				</tr>
				<s:set name="subTotalPreRece" value="#subTotalPreRece+#bill[7]"></s:set>
			</s:iterator>
			<tr class="bg_table02">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td align="right">本次小计：</td>
				<td align="right"><s:property value="#subTotalPreRece" /></td>
				<td ></td>
				<td></td>
				<td></td>
			</tr>
			<tr class="bg_table02">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td align="right">全部累计：</td>
				<td align="right"><s:property value="totalPreRece" /></td>
				<td ></td>
				<td></td>
				<td></td>
			</tr>
			<tr class="bg_table04">
				<td colspan="11" align="center"><baozi:pages value="info"
					beanName="info" formName="receProjectChangeStat" /></td>
			</tr>
		</table>
	</s:if>
</s:form>
<s:form id="export">
	<s:hidden name="method" value="showMainInfo"></s:hidden>
	<s:hidden name="exportX" value="1" />
</s:form>
</body>
</html>