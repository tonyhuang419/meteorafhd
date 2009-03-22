<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>申购采购确认</title>
<script language="javascript">
	function checkConfirm(j)
	{  	
		if(j==1 && checkCount() && window.confirm("确定要通过这些申购？")){
			document.forms(0).action="../purchase/purchase.action?method=checkThrough";
	    	document.forms(0).submit();
		}
		else if( j==0 && checkCount()  ){
			var checkedBoxes = $$("input[name=amids][checked]");
			if(checkedBoxes.length > 1){
				alert("请选择一个申购退回");
				return;
			}
			else{
				openWin2("../purchase/purchase.action?method=returnReason&amids="+checkedBoxes[0].value,400,300,"purchaseReturn")
			}
		}
	}
	
	function checkCount(){
    	var amids=document.getElementsByName("amids");
    	var num=0;
    	for(var i=0;i<amids.length;i++){
    		if(amids[i].checked)
				num++;
    	}	
    	if(num<1){
    		alert("请先选择申购！")
    		return false;
    	}
    	return true;
    }
	
	function viewPurchase(id){
		window.open("../purchase/purchase.action?method=viewPurchase&action=view&amids="+id,"newwindow","menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=500,width=860");
	}
    function reflushPage()
	{
		location.href="/yx/purchase/purchaseConfirmSearch.action";
	}
	
</script>
</head>
<body leftmargin="0">
<s:form action="purchaseConfirmSearch" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" align="center">
		<tr>
			<td height="3" align="left">当前页面:申购采购->申购采购确认</td>
		</tr>
		<tr>
			<td class="bg_table01" height="1"><img
				src="../../images/temp.gif" alt="temp" width="1" height="3"></td>
		</tr>

		<tr>
			<td width="100%" align="center" class="bg_table03">
				<input type="button" value="通  过" class="button02" onclick=checkConfirm(1);> 
				<input type="button" value="退  回" class="button02" onclick=checkConfirm(0);>
			</td>
		</tr>
    </table>

		<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
			<tr align="center">
				<td width="4%" class="bg_table01">选择</td>
				<td width="10%" class="bg_table01">申购单号</td>
				<td width="7%" class="bg_table01">申购人</td>
				<td width="17%" class="bg_table01">主体合同号</td>
				<td width="7%" class="bg_table01">项目号</td>
				<td width="6%" class="bg_table01">任务号</td>
				<td width="14%" class="bg_table01">客户名称</td>
				<td width="13%" class="bg_table01">申购内容</td>
				<td width="10%" class="bg_table01">申购金额</td>
				<td width="8%" class="bg_table01">申购日期</td>
			</tr>

			<s:iterator value="info.result">
				<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
					<td><input name="amids" value="<s:property value="id" />" type="checkbox"></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:property value="applyId" /></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:property value="pruService.getEmployee(sellmanId).name" /></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:if test="mainId==null">无关联合同</s:if><s:else><s:property value="pruService.getContractMainInfo(mainId).conId"/></s:else></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:if test="eventId==null">无</s:if><s:else><s:property value="eventId" /></s:else></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:if test="eventId==null">无</s:if><s:else><s:property value="assignmentId"/></s:else></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:property value="pruService.getYXClientCode(customerId).name"/></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:property value="applyContent" /></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:property value="applymoney" /></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:date name="applyDate" format="yyyy-MM-dd"/></td>
				</tr>
			</s:iterator>
		</table>


		<table cellSpacing=1 cellPadding=2 width="100%" border=0 algin="center">
			<tr valign="top">
				<td class="bg_table04"><baozi:pages value="info"	beanName="info" formName="forms(0)" /></td>
			</tr>
		</table>

</s:form>
</body>
<s:if test="opState == 4">
	<script language="javascript"> 
		alert("确认成功");
	</script>
</s:if>
<s:elseif test="opState == 5">
	<script language="javascript"> 
		alert("退回成功");
	</script>
</s:elseif>
</html>

