<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>基础数据管理左面显示列表</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
</head>
<body>
<br/>
	<center>
		<a
		href="/yx/system/basedata/baseData.action?method=opBillByPlan"
		target="rightFrame">全额开票和全额收款</a>
		<br/><br/>
		<a href="/yx/system/basedata/baseData.action?method=cancelBillAndRece"
		target="rightFrame">取消开票和收款（按计划）</a>
		<br/><br/>
		<a href="/yx/system/basedata/baseData.action?method=relationBillAndReceiptPlan"
		target="rightFrame">收据关联发票</a>
		<br/><br/>
		<a href="/yx/system/basedata/baseData.action?method=releaseRelationBillAndReceiptPlan&sign=noFind"
		target="rightFrame">取消收据关联发票</a>
			<br/><br/>
		<a href="/yx/system/basedata/baseData.action?method=modifyContractBaseInfo&sign=noFind"
		target="rightFrame">合同基础信息修改</a>
			<br/><br/>
		<a href="/yx/system/basedata/baseData.action?method=listNoBillApplyPlan"
		target="rightFrame">调整未开票（无申请单）的计划</a>	
			<br/><br/>
		<a href="/yx/system/basedata/baseData.action?method=cancelReceByReceQuery&sign=noFind"
		target="rightFrame">取消收款（按收款）</a>	
			<br/><br/>
		<a href="/yx/system/basedata/baseData.action?method=cancelMonthBillQuery&sign=noFind"
		target="rightFrame">取消月度开票计划</a>	
			<br/><br/>
		<a href="/yx/system/basedata/baseData.action?method=cancelMonthReceQuery&sign=noFind"
		target="rightFrame">取消月度收款计划</a>	
			<br/><br/>
		<a href="/yx/system/console.action"
		target="rightFrame">生成月度开票计划</a>	
			<br/><br/>
			<hr/>
			<strong>外协合同</strong>
				<br/><br/>
			<a href="/yx/system/basedata/systemOpAssistance.action?method=showPayInfoApplyPage" target="rightFrame">外协付款申请</a>
			<br/><br/>
			<a href="/yx/system/basedata/systemOpAssistance.action?method=showCancelPayInfoApply" target="rightFrame">取消外协付款申请 </a>
			<br/><br/>
			<a href="/yx/system/basedata/systemOpAssistance.action?method=showCancelPage" target="rightFrame">取消关联</a>
			<br/><br/>
			<a href="/yx/system/basedata/systemOpAssistance.action?method=showRelationPage" target="rightFrame">建立关联</a>
			<br/><br/>
	</center>
</body>
</html>