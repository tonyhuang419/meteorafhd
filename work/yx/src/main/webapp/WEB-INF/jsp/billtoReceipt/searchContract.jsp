<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>选择关联合同</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script language="javascript">
	
	function onSubmit(){
	var checkArr=document.getElementsByName("monthlyBillproSids");
  		 var checkStr="";
    	 var j=0;
         for(var i=0;i<checkArr.length;i++){
            if(checkArr[i].checked){
                 j++;    
                 checkStr=checkStr+","+checkArr[i].value;
             }
         }
         if(j>0){  
         	document.relationContractQuery.method.value = "checkRealIsBill";
         	if(checkRealIsBill("/yx/billtoReceipt/relationContractQuery.action?"+$(relationContractQuery).toQueryString()) == 1){
         		//可以关联
         		if(confirm("确定要关联吗?")){
		         	var billId =document.getElementById("ids").value;
					location.href="../billtoReceipt/relationContractQuery.action?method=relationCon&billId="+billId+"&realPlanId="+checkStr.substring(1);
		 		}
         	}else if(checkRealIsBill("/yx/billtoReceipt/relationContractQuery.action?"+$(relationContractQuery).toQueryString()) == 2){
         		alert("金额不同,不可以关联");
         	}else{
         		alert("选择的计划不可以同时关联到一张申请单上")
         	}
		 }else{
		 	alert("请选择一个进行关联!")
		 }
		 document.relationContractQuery.method.value = "showRealRelation";
	}	
	//判断选择的计划能否被关联
	function checkRealIsBill(url){
		var flag = 0;
        var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
			  if(responseText =='1'){
			  	 flag = 1;
			  }else if(responseText == "2"){
			  	 flag = 2;
			  }else{
			  	 flag = 3;
			  }
		    });
		   myRequest.send("&randomNum="+Math.random());
	   return flag;
	}
	
	//拆分金额
	function splitAmount(realIdVal)
	{ 
		window.open("splitBillAmountQuery.action?realPlanId="+realIdVal,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=300,width=400');
	}	
	function reflushPage(){
		document.forms(0).submit();
    }
</script>
<body>
<s:form action="relationContractQuery" theme="simple">
	<s:hidden name="method" value="showRealRelation"></s:hidden>
	<s:hidden name="ids" id="ids"  value="%{#parameters.billId}"></s:hidden>
	<s:hidden name="billId" id="billId"  value="%{#parameters.billId}"></s:hidden>
	<s:hidden name="billAmount" id="billAmount"  value="%{#parameters.billAmount}"></s:hidden>
	<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
				<tr class="bg_table03">
					<td colspan="3" class="bg_table03">合同名称：<s:textfield name="cName"></s:textfield></td>
					<td colspan="3" class="bg_table03">合同号：<s:textfield name="cId"></s:textfield></td>
					<td colspan="3" class="bg_table03">项目号：<s:textfield name="itemId"></s:textfield></td>
					
				</tr>
				<tr class="bg_table03">
					<td colspan="9" class="bg_table03">
					<div align="center">
				<input value="查询" type="submit" class="button01"/>	
				</tr>
			</table>
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill"  bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td  class="bg_table01">选择</td>
					<td class="bg_table01">合同号</td>
					<td class="bg_table01">项目号</td>
					<td class="bg_table01">合同名称</td>
					<td  class="bg_table01">客户名称</td>
					<td  class="bg_table01">负责部门</td>
					<td  class="bg_table01">阶段</td>
					<td  class="bg_table01">计划开票日期</td>
					<td  class="bg_table01">开票性质</td>
					<td  class="bg_table01">发票类型</td>
					<td  class="bg_table01">开票金额</td>
					<td width="6%" class="bg_table01">操作</td>
					
				</tr>
				<s:iterator value="realRelationList" id="month" status="status">
					<tr align="center" class="bg_table02" onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
						<td>
							 <s:property value="#bill"/>
								<input type="checkbox" name="monthlyBillproSids" <s:if test="#month[5] > 0 ||(#month[0].billType == 4 && #month[6] > 0)"> disabled </s:if>
									value="<s:property value="#month[0].realConBillproSid"/>" />
							</td>
						<td align="left"><s:property value="#month[2].conId" /></td>
						<td align="left"><s:property value="#month[3].conItemId" /></td>
						<td align="left"><s:property value="#month[2].conName" /></td>
						<td align="left"><s:property value="#month[1].name" /></td>
						<td align="left">
							<s:if test="#month[3] != null">
								<s:property	value="typeManageService.getYXTypeManage(1018,#month[3].itemResDept).typeName" />
							</s:if>
							<s:else>
								<s:property value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName"/>
							</s:else>
						</td>
						<td align="left">
							<s:property value="typeManageService.getYXTypeManage(1023,#month[4]).typeName" />
					    </td>
						<td align="center"><s:property
							value="#month[0].realPredBillDate" /></td>
						<td align="left"><s:property
							value="typeManageService.getYXTypeManage(1012,#month[0].billNature).typeName" /></td>
						<td align="left"><s:property
							value="typeManageService.getYXTypeManage(1004,#month[0].billType).typeName" /></td>
						<td align="right"><s:property
							value="#month[0].realBillAmount" />
							<input type="hidden" name="realAmount<s:property value="#month[0].realConBillproSid"/>" value="<s:property value="#month[0].realBillAmount" />"/>
						</td>
						<s:if test="#month[5] > 0 || (#month[0].billType == 4 && #month[6] > 0 ) ">
							<td align="center" >
									&nbsp;
								</td>	
						</s:if>
						<s:else>
							<td align="center" >
									<a href="#" onclick="splitAmount(<s:property value="#month[0].realConBillproSid"/>)" >拆分</a>
								</td>
						</s:else>
					</tr>
				</s:iterator>
				<tr class="bg_table03">
					<td colspan="12" align="center" class="bg_table03"><input type="button" name="SearchBtn" value="　确认关联　" class="button01" onClick="onSubmit()">
					<input type="button" name="close" value="　关 闭　" class="button01" onClick="javascript:window.close();"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>

</html>