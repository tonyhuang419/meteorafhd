<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>

<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">
</script>

<title>申购采购确认管理</title>
<link href="./../css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
</head>

<body>
<s:form action="purchaseVerityQuery">
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
					<td align="left">当前页面:申购采购 -> 申购采购确认</td>
				</tr>
				<tr>
					<td align="right" class="bg_table01" height="0.5"><img
						src="./../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr>
					<td align="right" class="bg_table03">
					<div align="center"><input type="button" value="通  过"
						onClick="javascript:aaa()" class="button02" /> <input
						type="button" value="退  回" onClick="javascript:ccc()"
						class="button02" /></div>
					</td>
				</tr>
			</table>
			<table align="center" border="1" cellpadding="1" cellspacing="1" 
				width="100%" id="checkInfo" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td width="6%" class="bg_table01">选择</td>
					<td width="10%" class="bg_table01">申购单号</td>
					<td width="9%" class="bg_table01">申购人</td>
					<td width="12%" class="bg_table01">主体合同号</td>
					<td width="7%" class="bg_table01">项目号</td>
					<td width="15%" class="bg_table01">客户名称</td>
					<td width="20%" class="bg_table01">申购内容</td>
					<td width="11%" class="bg_table01">金额</td>
					<td width="10%" class="bg_table01">申购日期</td>
				</tr>
				<s:iterator id="result" value="list">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
						onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids"
							value="<s:property value="#result[0].id"/>" /></td>
						<td><a
							href='<s:url action="purchase" >
	                <s:param name="method">enterUpdate</s:param>
	                <s:param name="am.id" value="#result[0].id" />
	                <s:param name="am.applyState" value="#result[0].applyState" />
	                <s:param name="am.applyType" value="#result[0].applyType" />
	                </s:url>'>


						<s:property value="#result[0].applyId" /></a></td>
						<td
							onclick="openUrl('purchase.action?method=enterUpdate&am.id=<s:property value="#result[0].id"/>&am.applyType=<s:property value="#result[0].applyType"/>&am.applyState=<s:property value="#result[0].applyState"/>')"><s:property
							value="#result[2]" /></td>
						<td
							onclick="openUrl('purchase.action?method=enterUpdate&am.id=<s:property value="#result[0].id"/>&am.applyType=<s:property value="#result[0].applyType"/>&am.applyState=<s:property value="#result[0].applyState"/>')"><s:property
							value="#result[0].mainId" /></td>
						<td
							onclick="openUrl('purchase.action?method=enterUpdate&am.id=<s:property value="#result[0].id"/>&am.applyType=<s:property value="#result[0].applyType"/>&am.applyState=<s:property value="#result[0].applyState"/>')"><s:property
							value="#result[0].mainId" /></td>
						<td
							onclick="openUrl('purchase.action?method=enterUpdate&am.id=<s:property value="#result[0].id"/>&am.applyType=<s:property value="#result[0].applyType"/>&am.applyState=<s:property value="#result[0].applyState"/>')"><s:property
							value="#result[1]" /></td>
						<td
							onclick="openUrl('purchase.action?method=enterUpdate&am.id=<s:property value="#result[0].id"/>&am.applyType=<s:property value="#result[0].applyType"/>&am.applyState=<s:property value="#result[0].applyState"/>')"><s:property
							value="#result[0].applyContent" /></td>
						<td
							onclick="openUrl('purchase.action?method=enterUpdate&am.id=<s:property value="#result[0].id"/>&am.applyType=<s:property value="#result[0].applyType"/>&am.applyState=<s:property value="#result[0].applyState"/>')"><s:property
							value="#result[0].bugget" /></td>
						<td
							onclick="openUrl('purchase.action?method=enterUpdate&am.id=<s:property value="#result[0].id"/>&am.applyType=<s:property value="#result[0].applyType"/>&am.applyState=<s:property value="#result[0].applyState"/>')"><s:date
							name="#result[0].applyDate" format="yyyy-MM-dd" /></td>
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

<script language="javascript">

function aaa() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var act="pass";
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
location.href="../purchase/purchase.action?method=verifyState&action="+act+"&stateId="+checkStr.substring(1); 
} 
function ccc() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
location.href="../purchase/purchase.action?method=verifyState&stateId="+checkStr.substring(1); 
} 


</script>
