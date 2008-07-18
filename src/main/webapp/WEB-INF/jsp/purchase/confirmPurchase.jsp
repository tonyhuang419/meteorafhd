<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>申购采购管理</title>
<SCRIPT language=JavaScript src="./js/public.js"></SCRIPT>

<script language="javascript">
	
	function checkConfirm(j)
	{  	if(j==1 && checkCount() && window.confirm("确定要通过这些申购？")){
			document.forms(0).action="<s:url action="purchase"><s:param name="method">checkThrough</s:param></s:url>";
	    	document.forms(0).submit();
		}
		if(j==0 && checkCount()  && window.confirm("确定要退回这些申购？")){
			document.forms(0).action="<s:url action="purchase"><s:param name="method">checkBback</s:param></s:url>";
	    	document.forms(0).submit();
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
    		alert("您没有选择申购单，请选择后操作！")
    		return false;
    	}
    	return true;
    }
	
	function viewPurchase(id){
		window.open("../purchase/purchase.action?method=viewPurchase&action=view&amids="+id,"newwindow","menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=500,width=860");
	}
	
</script>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="pruManageQuery" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		align="center">
		<tr>
			<td height="3" align="left">当前页面:申购采购->申购采购管理</td>
		</tr>
		<tr>
			<td class="bg_table01" height="1"><img
				src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table class="bg_table03" style="width: 100%;" algin="center">
				<tr>
					<td width="99%" align="center" class="bg_table03"><input
						type="button" value="确认通过" class="button02" onclick=checkConfirm(1);> 
						&nbsp;&nbsp;&nbsp;
						<input type="button"
						value="确认退回" class="button02"
						onclick=checkConfirm(0);>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</td>
	</tr>

	<tr>
		<td align="center">
		<table align="center" border=0 cellpadding="1" cellspacing=1
			width="100%">
			<tr align="center">
				<td width="4%" class="bg_table01">选择</td>
				<td width="10%" class="bg_table01">申购单号</td>
				<td width="7%" class="bg_table01">申购人</td>
				<td width="17%" class="bg_table01">主体合同号</td>
				<td width="7%" class="bg_table01">项目号</td>
				<td width="6%" class="bg_table01">任务号</td>
				<td width="14%" class="bg_table01">客户名称</td>
				<td width="13%" class="bg_table01">申购内容简述</td>
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
		</td>
	</tr>
	<tr>
		<td algin="center">
		<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0
			algin="center">
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

