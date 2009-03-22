<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>收款管理/确认收款</title>
<script type="text/javascript">
function queren()
{
	var flag=window.confirm("您确认收款吗？");
	if(flag){
		document.w_ReveInfoMakeSure.method.value="confirmReveInfo";
		document.w_ReveInfoMakeSure.submit();
	}
}
<s:if test="flag==false">
	alert("以下收款是确认不通过的");
</s:if>
</script>
</head>
<body>
<s:form action="w_ReveInfoMakeSure" theme="simple">
<s:hidden name="method"></s:hidden>
 <div align="left" style="color: #000000">
		<p>当前页面：收款管理 -&gt; 确认收款</p>
</div>
			<table align="center" border=1 cellpadding="1" cellspacing=1 
				width="100%" id="billApplyList"  bordercolor="#808080" style="border-collapse: collapse;">
	 		<tr class="bg_table01">
	 			<td align="center">销售员</td>
	 			<td align="center">合同号</td>
	 			<td align="center">项目号</td>
	 			<td align="center">到款金额</td>
	 			<td align="center">到款方式</td>
	 			<td align="center">到款日期</td>
	 			<td align="center">操作</td>
	 		</tr>
	 	<s:iterator value="reveMakeSureList" status="status" id="rl">
	 		<tr class="bg_table02">
	 			<td align="left"><s:property value="#rl[3]"/></td>
	 			<td align="left"><s:property value="#rl[0].conId"/></td>
	 			<td align="left"><s:property value="#rl[1].conItemId"/></td>
	 			<td align="right"><s:property value="#rl[2].amount"/></td>
	 			<td align="left"><s:property value="typeManageService.getYXTypeManage(1017L,#rl[2].receType).typeName"/></td>
	 			<td align="center"><s:property value="#rl[2].amountTime"/></td>
	 			<td align="center"><a href="#" onclick="inputReve(
	 			'<s:property value="#rl[1].conItemMinfoSid"/>','<s:property value="#rl[0].conMainInfoSid"/>'
	 		)">明细&修改</a></td>
	 		</tr>
	 	</s:iterator>
	 	<tr class="bg_table03">
	 		<td colspan="7" align="center">
	 			<input type="button" class="button01" onclick="queren();" value="确认全部收款"/>
	 			<input type="button" class="button01" onclick="window.close();" value="关  闭"/>
	 		</td>
	 	</tr>
	</table>
</s:form>
</body>
<script type="text/javascript">


function inputReve(itemId,conId){
	openWin2('w_HarvestMeansSearch.action?method=showReveInfo&itemId='+itemId+'&conId='+conId,650,400, "md");
	//window.open('w_HarvestMeansSearch.action?method=showReveInfo&itemId='+itemId+'&conId='+conId);
}

function refresh()
{
	document.w_ReveInfoMakeSure.method.value="";
	document.forms(0).submit();
}

</script>
</html>