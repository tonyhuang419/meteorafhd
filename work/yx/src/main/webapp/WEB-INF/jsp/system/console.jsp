<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>

<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>控制台</title>
</head>
<body>
<form name="opForm" method="post"></form>
<input type="button" value="生成本月月度开票计划" onclick="reGenCurMonthBillPlan(this)"><br/>
<input type="button" value="更新帐龄" onclick="updateAccountAgePlan(this)"><br/>
<input type="button" value="修正合同转正日期" onclick="updateContractActiveDate(this);"/>
<script type="text/javascript">
function reGenCurMonthBillPlan(btn){
	if(confirm("是否要生成本月月度开票计划?")){
		opForm.action="/yx/system/console.action?method=reGenerateCurrentMonthBillPlan";
		opForm.submit();
		btn.disabled = true;
	}
}
function updateAccountAgePlan(btn){
	opForm.action="/yx/system/console.action?method=updateAccountAge";
	opForm.submit();
	btn.disabled = true;
}
function updateContractActiveDate(btn){
	opForm.action="/yx/system/console.action?method=updateContractActiveDate";
	opForm.submit();
	btn.disabled = true;

}
</script>
</body>
</html>