<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>提示信息</title>
<style>
.ft {
	font-family: '宋体';
}
.bg{
	background-image:url(/yx/commons/images/bj.gif);
	background-repeat:repeat-x;
}
</style>
</head>
<body class="bg">
<table width="100%" height="100%" border=0 cellpadding=0 cellspacing=0>
<tr >
    <td height="50" align="left" nowrap ><img src="/yx/commons/images/tsxx.gif"></td>
    <td width="43%" colspan="2" align="center" nowrap ><img src="/yx/commons/images/gg.gif"></td>
  </tr>
	<tr>
		<td>
			<table width="100%" height="100%" border=0 cellpadding=0 cellspacing=0>
				<tr>
					<td height="0.5" align="center" class="bg_table01"><img
						src="./../images/temp.gif" width="1" height="1"></td>
				</tr>
				<!-- 售前合同 -->
				<s:if test="lastSellBeforeUpdateDate!=null">
				<tr>
					<td width="57%" height="35">
					<img src="/yx/commons/images/fk.gif">  <s:date name="lastSellBeforeUpdateDate" format="yyyy-MM-dd" />更新过售前合同，距离今日<s:property value="subSellBeforeDate" />天
					</td>
				</tr>
				</s:if>
				
				<!-- 新签合同 -->
				<s:if test="newContractCount > 0">
				<tr>
					<td width="57%" height="35">
					<img src="/yx/commons/images/fk.gif">  本月有<s:property value="newContractCount" />笔新签合同：
					<a href="javascript:parent.redirectForm.action='/yx/firstPage/newSignContract.action?method=frame&contractType=2';parent.redirectForm.submit();">
					<s:property value="newContractCountIntegrated" />笔集成类</a>
					<a href="javascript:parent.redirectForm.action='/yx/firstPage/newSignContract.action?method=frame&contractType=1';parent.redirectForm.submit();">
					<s:property value="newContractCountItem" />笔项目类
					</a>
					</td>
				</tr>
				</s:if>
				
				<!-- 本月计划开票 -->
				<s:if test="countBillandRecePlan1 > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif"> 本月有<s:property value="countBillandRecePlan1" />笔计划开票： 
				 	<a href="javascript:parent.redirectForm.action='/yx/billtoReceipt/showMoonBillQuery.action?method=enterBillQueryPage&inPlan=1&billStartDate=<s:property value="curMonthFirstDay"/>&billEndDate=<s:property value="curMonthLastDay"/>&hasApply=1';parent.redirectForm.submit();"><s:property value="countBillandRecePlan5" />笔已申请</a>
				 	（<a href="javascript:parent.redirectForm.action='/yx/billtoReceipt/showMoonBillQuery.action?method=enterBillQueryPage&billState=1&inPlan=1&hasApply=1&billStartDate=<s:property value="curMonthFirstDay"/>&billEndDate=<s:property value="curMonthLastDay"/>';parent.redirectForm.submit();"><s:property value="countBillandRecePlan2" />笔已开票</a>），
				<%--	<a href="javascript:parent.redirectForm.action='/yx/billtoReceipt/showMoonBillQuery.action?method=enterBillQueryPage&billState=0&inPlan=1&billStartDate=<s:property value="curMonthFirstDay"/>&billEndDate=<s:property value="curMonthLastDay"/>';parent.redirectForm.submit();"><s:property value="countBillandRecePlan4" />笔未开票</a></td>
					--%>
					
					<a href="javascript:parent.redirectForm.action='/yx/billtoReceipt/showMoonBillQuery.action?method=enterBillQueryPage&inPlan=1&billStartDate=<s:property value="curMonthFirstDay"/>&billEndDate=<s:property value="curMonthLastDay"/>&hasApply=0';parent.redirectForm.submit();"><s:property value="countBillandRecePlan6" />笔未申请</a>
					（<a href="javascript:parent.redirectForm.action='/yx/billtoReceipt/showMoonBillQuery.action?method=enterBillQueryPage&inPlan=1&billStartDate=<s:property value="curMonthFirstDay"/>&billEndDate=<s:property value="curMonthLastDay"/>&hasApply=0&hasModify=1';parent.redirectForm.submit();"><s:property value="countBillandRecePlan7" />笔已修改</a>）
					</td>
				</tr>
				</s:if>
				<!-- 本月计划收款 -->
				<s:if test="countRecePlan1 > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif"> 本月有<s:property value="countRecePlan1" />笔计划收款： 
					<a href="javascript:parent.redirectForm.action='/yx/harvestMeansManager/corentMouthHarvestProgram.action?method=enterReceQueryPage&billState=1&inPlan=1&billStartDate=<s:property value="curMonthFirstDay"/>&billEndDate=<s:property value="curMonthLastDay"/>';parent.redirectForm.submit();" > <s:property value="countRecePlan2" />笔已收款</a>，
					<a href="javascript:parent.redirectForm.action='/yx/harvestMeansManager/corentMouthHarvestProgram.action?method=enterReceQueryPage&billState=0&inPlan=1&billStartDate=<s:property value="curMonthFirstDay"/>&billEndDate=<s:property value="curMonthLastDay"/>';parent.redirectForm.submit();"><s:property value="countRecePlan4" />笔未收款</a>
					（<a href="javascript:parent.redirectForm.action='/yx/harvestMeansManager/corentMouthHarvestProgram.action?method=enterReceQueryPage&billState=0&inPlan=1&billStartDate=<s:property value="curMonthFirstDay"/>&billEndDate=<s:property value="curMonthLastDay"/>&hasModify=1';parent.redirectForm.submit();"><s:property value="countRecePlan5" />笔已修改</a>）
					</td>
				</tr>
				</s:if>
				<!-- 本月计划外开票 -->
				<s:if test="countOutplanBill > 0">
				<tr>
					<td width="57%" height="35">
						<img src="/yx/commons/images/fk.gif">
						<a href="javascript:parent.redirectForm.action='/yx/billtoReceipt/showMoonBillQuery.action?method=enterBillQueryPage&inPlan=0&billState=1&billStartDate=<s:property value="curMonthFirstDay"/>&billEndDate=<s:property value="curMonthLastDay"/>';parent.redirectForm.submit();" >本月有<s:property value="countOutplanBill" />笔计划外开票 </a>
					</td>
				</tr>
				</s:if>	
				<!-- 本月计划外收款 -->
				<s:if test="countOutplanRece > 0">
				<tr>
					<td width="57%" height="35">
						<img src="/yx/commons/images/fk.gif">
						<a href="javascript:parent.redirectForm.action='/yx/harvestMeansManager/corentMouthHarvestProgram.action?method=enterReceQueryPage&inPlan=0&billStartDate=<s:property value="curMonthFirstDay"/>&billEndDate=<s:property value="curMonthLastDay"/>';parent.redirectForm.submit();" >本月有<s:property value="countOutplanRece" />笔计划外收款 </a>
					</td>
				</tr>
				</s:if>		
				<!-- 下月预计开票 -->
				<s:if test="nextMouthBill > 0">
				<tr>
					<td width="57%" height="35">
						<img src="/yx/commons/images/fk.gif">
						<a href="javascript:parent.redirectForm.action='/yx/billtoReceipt/showMoonBillQuery.action?method=enterNexMonthBillQueryPage&billStartDate=<s:property value="nextMonthFirstDay"/>&billEndDate=<s:property value="nextMonthLastDay"/>';parent.redirectForm.submit();" >下月有<s:property value="nextMouthBill" />笔预计开票 </a>
					</td>
				</tr>
				</s:if>
				<!-- 下月预计收款 -->
				<s:if test="nextMouthReve > 0">
				<tr>
					<td width="57%" height="35">
						<img src="/yx/commons/images/fk.gif"> 
						<a href="javascript:parent.redirectForm.action='/yx/harvestMeansManager/corentMouthHarvestProgram.action?method=nextMonthReceQueryPage&billStartDate=<s:property value="nextMonthFirstDay"/>&billEndDate=<s:property value="nextMonthLastDay"/>';parent.redirectForm.submit();">下月有<s:property value="nextMouthReve" />笔预计收款</a>
					</td>
				</tr>
				</s:if>
                <!-- 无项目号的正式合同 -->
                <s:if test="countContractItem > 0">
				<tr>
					<td width="57%" height="35">
						<img src="/yx/commons/images/fk.gif"> <a href="javascript:parent.redirectForm.action='/yx/contract/contractItemManager.action?method=enterNoProjectTipFrame';parent.redirectForm.submit();"> 有<s:property value="countContractItem" />笔无项目号的正式合同</a>
					</td>
				</tr>
				</s:if>
				
				
			<!-- 未签合同开票 -->
				<s:if test="countApplyBill > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='/yx/firstPage/noContractSignInvoice.action?method=frame';parent.redirectForm.submit();"> 有<s:property value="countApplyBill" />笔未签合同开票 </a>
					</td>
				</tr>
				</s:if>
				
				
			<!-- 开票未签收 -->
			<s:if test="vATInovoiceNoSign > 0 ||  businessInovoiceNoSign > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">  				
					<a href="javascript:parent.redirectForm.action='/yx/firstPage/vATInvoiceNoSign.action?method=frame&billType=2';parent.redirectForm.submit();"> 有<s:property value="vATInovoiceNoSign" />张增票未签收 </a>
					，<a href="javascript:parent.redirectForm.action='/yx/firstPage/vATInvoiceNoSign.action?method=frame&billType=3';parent.redirectForm.submit();"> 有<s:property value="businessInovoiceNoSign" />张商票未签收 </a>			
					</td>		
				</tr>
			</s:if>
				
					
<!--				<tr>-->
<!--					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">-->
<!--					<a href="#"> 有3笔异常应收款 </a>-->
<!--					</td>-->
<!--				</tr>-->

				
				<tr>				
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='/yx/firstPage/hisReve.action?method=frame&signHisReve=4';parent.redirectForm.submit();"> 前日到款：<s:property value="beforeYesterdatReve" /></a>
					，
					<a href="javascript:parent.redirectForm.action='/yx/firstPage/hisReve.action?method=frame&signHisReve=1';parent.redirectForm.submit();"> 昨日到款：<s:property value="yesterdayReve" /></a>
					，
					<a href="javascript:parent.redirectForm.action='/yx/firstPage/hisReve.action?method=frame&signHisReve=3';parent.redirectForm.submit();"> 本月到款：<s:property value="thisMonthReve" /></a>
					</td>
				</tr>	
				
				<s:if test="noContractReve > 0" /> 
				<tr>				
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='/yx/firstPage/noContractReve.action?method=frame';parent.redirectForm.submit();"> 有<s:property value="noContractReve" />笔未核销的未签到款，共计：<s:property value="noContractReveSum" /></a>
					</td>
				</tr>
				
				
				<!-- 未签合同申购 -->
				<s:if test="countApplyMessage > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='/yx/purchase/purManageQuery.action?method=manage&select6=0';parent.redirectForm.submit();"> 有<s:property value="countApplyMessage" />笔未签合同申购</a>
					</td>
				</tr>
				</s:if>
				
				<!--开票申请待确认 -->
			 <s:if test="applyWaitSureCount > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='billtoReceipt/billApplyVerifyQuery.action?resetCondition=true';parent.redirectForm.submit();"> 有<s:property value="applyWaitSureCount" />笔开票申请待确认</a>
					</td>
				</tr>
			</s:if> 
				
				<!-- 开票申请已处理未开票 --> 
			<s:if test="applyPassNoInvoiceCount > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='billtoReceipt/billReceiptQuery.action?method=newDefault&resetCondition=true&resetPage=true';parent.redirectForm.submit();"> 有<s:property value="applyPassNoInvoiceCount" />笔开票申请已处理未开票</a>
					</td>
				</tr>
			</s:if>
			
			<!-- 外协合同待确认 --> 
			<s:if test="outSourceContractWaitSure > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='assistance/affirmMain.action';parent.redirectForm.submit();"> 有<s:property value="outSourceContractWaitSure" />笔外协合同待确认</a>
					</td>
				</tr>
			</s:if>
			
			<!-- 外协付款申请合同待确认 --> 
		 	<s:if test="outSourcePayWaitSure > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='assistance/passApplyMain.action';parent.redirectForm.submit();"> 有<s:property value="outSourcePayWaitSure" />笔外协付款申请待确认</a>
					</td>
				</tr>
			</s:if> 
				
				
			<!--  预决算转结算待确认数 --> 
			<s:if test="conPreToFinalCount > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='contract/defineHome.action';parent.redirectForm.submit();"> 有<s:property value="conPreToFinalCount" />笔预决算转结算待确认</a>
					</td>
				</tr>
			</s:if> 				
			
				<!-- 项目建议关闭 -->
				<s:if test="itemSuggestClose > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='/yx/firstPage/itemSuggestClose.action?method=frame';parent.redirectForm.submit();"> 有<s:property value="itemSuggestClose" />个项目建议关闭 </a>
					</td>
				</tr>
			    </s:if>
			  
			    
				<!-- 合同建议关闭 -->
				<s:if test="countContractMain > 0">
				<tr>
					<td width="57%" height="35"><img src="/yx/commons/images/fk.gif">
					<a href="javascript:parent.redirectForm.action='/yx/firstPage/contractSuggestClose.action?method=frame';parent.redirectForm.submit();"> 有<s:property value="countContractMain" />份合同建议关闭 </a>
					</td>
				</tr>
			    </s:if>
				<tr height="100%">
					<td>
					&nbsp;
					</td>
				</tr>
			</table>
		</td>
		<td>
		<form>
		<table width="100%" height="100%" CELLSPACING="0" CELLPADDING="0">
			<tr align="center"  valign="top">
				<td class=ft colspan="2">
<!--				<table></table>-->
				<textarea cols="40" rows="29"readonly="readonly"><s:iterator value="reportList" id="report" status="status">发布日期：<s:property value="#report[1]" />
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
