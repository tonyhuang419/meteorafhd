<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>提示信息</title>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<%@ include file="/commons/jsp/meta.jsp" %>
<style>
.ft {
	font-family: '宋体';
}
</style>
</head>
<body background="/yx/commons/images/bj.gif">


<table width="100%" height="100%" border=0 cellpadding=0 cellspacing=0>
<tr >
    <td height="49" align="left" nowrap ><img src="/yx/commons/images/tsxx.gif"></td>
    <td width="43%" colspan="2" align="center" nowrap ><img src="/yx/commons/images/gg.gif"></td>
  </tr>
	<tr>
		<td>
			<table width="100%" height="100%" border=0 cellpadding=0 cellspacing=0>
				<tr>
					<td height="0.5" align="center" class="bg_table01"><img
						src="./../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr>
					<td width="57%" height="35">
					<s:if test="lastSellBeforeUpdateDate!=null">
					<img src="/yx/commons/images/fk.gif">  您在<s:date name="lastSellBeforeUpdateDate" format="yyyy-MM-dd" />更新过售前合同，距离今<s:property value="subSellBeforeDate" />天
					</s:if>
					</td>
				</tr>
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif"> 您本月有<s:property value="countBillandRecePlan1" />笔计划开票： <a href="javascript:parent.location='/yx/billtoReceipt/billReceiptQuery.action'"> <s:property value="countBillandRecePlan2" />笔已经开票</a>，<a href="javascript:parent.location='/yx/billtoReceipt/billReceiptQuery.action'"><s:property value="countBillandRecePlan3" />笔部分开票</a>，<a
						href="javascript:parent.location='/yx/billtoReceipt/billReceiptQuery.action'"><s:property value="countBillandRecePlan4" />笔未开票</a></td>
				</tr>
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif"> 您本月有<s:property value="countRecePlan1" />笔计划收款： <a href="javascript:parent.location='/yx/harvestMeansManager/harvestMeansMain.action?resetCondition=true'" > <s:property value="countRecePlan2" />笔已经收款</a>，<a href="javascript:parent.location='/yx/harvestMeansManager/harvestMeansMain.action?resetCondition=true'"><s:property value="countRecePlan3" />笔部分收款</a>，<a
						href="javascript:parent.location='/yx/harvestMeansManager/harvestMeansMain.action?resetCondition=true'"><s:property value="countRecePlan4" />笔未收款</a></td>
				</tr>
				<tr>
					<td width="57%" height="35">
					<img src="/yx/commons/images/fk.gif"> <a href="javascript:parent.location='/yx/contract/contractItemWriteHome.action'"> 您有<s:property value="countContractItem" />笔无项目号的正式合同</a>
					</td>
				</tr>
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.location='/yx/harvestMeansManager/harvestMeansMain.action'"> 您目前有<s:property value="countBillandRecePlan" />笔已开票未到款 </a>
					</td>
				</tr>
<!--				<tr>-->
<!--					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">-->
<!--					<a href="#"> 您有3笔异常应收款 </a>-->
<!--					</td>-->
<!--				</tr>-->
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.location='/yx/purchase/purManageQuery.action?method=manage'"> 您有<s:property value="countApplyMessage" />笔未签合同申购</a>
					</td>
				</tr>
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.location='/yx/billtoReceipt/receiptToBiellMainQuery.action'"> 您有<s:property value="countReceipt" />张收据未对应发票  </a>
					</td>
				</tr>
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.location='/yx/billtoReceipt/relationContractQuery.action'"> 您有<s:property value="countApplyBill" />张未签合同开票 </a>
					</td>
				</tr>
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.location='/yx/contract/formalContractManage/formalContractManageFrame.action'"> 您有<s:property value="countContractMain" />份合同建议关闭 </a>
					</td>
				</tr>
			
				<tr height="100%">
					<td>
					&nbsp;
					</td>
				</tr>
			</table>
		</td>
		<td>
		<form>
		<table width="100%" height="100%" CELLSPACING="0" CELLPADDING="0" >
			<tr>
				<td width="100%" colspan="3">
				<div align="center"></div>
				</td>
			</tr>
			<tr align="center">
				<td class=ft colspan="2"><textarea cols="40" rows="29"readonly="readonly"><s:iterator value="reportList" id="report" status="status">发布日期：<s:property value="#report[1]" />
<s:property value="#report[0]" />

</s:iterator></textarea>
  				</td>
			</tr>
		</table>
		</form>
		</td>
	</tr>
</table>
</body>
</html>