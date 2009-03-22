<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>收款计划统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<script type="text/javascript">
function popwin()
{
	window.open('yx/statistics/receStat.action?method=popQuery','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=400,width=620');
	//window.open('yx/statistics/billStat.action?method=popQuery');
}
function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
}
</script>
</head>
<body>
<s:form action="receStat" theme="simple">
	<s:hidden name="method" value="showMainInfo"></s:hidden>
	<div align="left" style="color: #000000">
	<p>当前页面：统计查询 -> 预计收款统计</p>
	</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%"
		class="bg_table03">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr>
			<td align="center"><input type="button" onclick="popwin();"
				value="查询" class="button01" />
			<s:if test="oArray.size() > 0 ">
				<input type="button" value=" 导  出 " onclick="exportInfo();" class="button01" />
			</s:if>			
			</td>
		</tr>
	</table>
	<s:if test="oArray!=null">
		<table align="center" border=1 cellpadding="1" cellspacing=1
			width="100%" id="mouthlyBill" bordercolor="#808080"
			style="border-collapse: collapse;">

			<s:iterator value="oArray" id="info" status="stat">
				
				<s:if test="#stat.index == 0">
				<tr class="bg_table02"  align="center">
					<td></td>
					<td><s:date name="#info[1]" format="yyyy年MM月"/></td>
					<td><s:date name="#info[2]" format="yyyy年MM月"/></td>
					<td><s:date name="#info[3]" format="yyyy年MM月"/></td>
					<td><s:date name="#info[4]" format="yyyy年MM月"/></td>
					<td><s:date name="#info[5]" format="yyyy年MM月"/></td>
					<td><s:date name="#info[6]" format="yyyy年MM月"/></td>
				</tr>
				</s:if>
				<s:else>
				<tr class="bg_table02">
					<td align="center"><s:property value="#info[0]" /></td>
					<td align="right"><s:property value="#info[1]" /></td>
					<td align="right"><s:property value="#info[2]" /></td>
					<td align="right"><s:property value="#info[3]" /></td>
					<td align="right"><s:property value="#info[4]" /></td>
					<td align="right"><s:property value="#info[5]" /></td>
					<td align="right"><s:property value="#info[6]" /></td>
				</tr>
				</s:else>
			</s:iterator>
		</table>
	</s:if>
</s:form>
<s:form id="export">
	<s:hidden name="method" value="showMainInfo"></s:hidden>
	<s:hidden name="exportX" value="1" />
</s:form>
</body>
</html>