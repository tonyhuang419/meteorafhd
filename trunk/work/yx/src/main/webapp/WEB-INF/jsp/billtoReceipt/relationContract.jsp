<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<title>未签开票关联合同</title>
</head>
<script type="text/javascript">
	function searchCon(url,method){
   		 var checkArr=document.getElementsByName("ids");
  		 var checkStr="";
    	 var j=0;
         for(var i=0;i<checkArr.length;i++){
            if(checkArr[i].checked){
                 j++;    
                 checkStr=checkStr+","+checkArr[i].value;
             }
         }
         if(j==1)
         {  
         		var myRequest = new Request({url:url,async:false,method:"get"});
				myRequest.addEvent("onSuccess",function(responseTest,responseXML){
					if(responseTest == '1'){
						alert("该票据已关联过合同!");
						return false;
					}
					if(responseTest == '0'){
			         	  var amount = 	"taxAmount" + checkStr.substring(1);
			              window.open('relationContractQuery.action?method=searchContract&billId='+checkStr.substring(1)+'&billAmount='+$(amount).value,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=650,width=800');
					}
				});
				myRequest.send("method="+method+"&billId="+checkStr.substring(1)+"&randomNum="+Math.random());
         }
		 if(j==0){
		        alert("您还没有选择对象！");
		 }
	 }
	function reflushPage(){
			location.href="../billtoReceipt/relationContractQuery.action";
	}

</script>
<body style="margin: 0px;">
<div align="left" style="color: #000000">当前页面：开票管理 -> 未签开票关联合同</div>
<s:form action="relationContractQuery" theme="simple" >
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr width="34%" class="bg_table02" align="center">
			<td  align="right" class="bg_table03" />
			<div align="center">
				<input type="button" class="button01" name="ffddfff" value=" 关联合同 "  onclick="searchCon('../billtoReceipt/relationContractQuery.action','isRelation')"/>
			</div>
			</td>
		</tr>
	</table>
	<table align="center" border=1 cellpadding="1" cellspacing=1 
		width="100%" id="billApplyList"  bordercolor="#808080" style=" border-collapse: collapse;">
		<tr align="center">
			<td class="bg_table01">
			选择
			</td>
			<td class="bg_table01">开票申请编号</td>
			<td class="bg_table01">申请日期</td>
			<td class="bg_table01">申请金额</td>
			<td class="bg_table01">开票性质</td>
			<td class="bg_table01">票据类型</td>
			<td class="bg_table01">开票内容</td>
		</tr>
		<s:iterator value="info.result">
			<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
				<td>
					<input type="radio" name="ids" value="<s:property value="billApplyId"/>" />
				</td>
				<td align="left"><s:property value="billApplyNum"/></td>
				<td align="center"><s:property value="applyId"/></td>
				<td align="right">
					<s:property value="billAmountTax"/>
					<input type="hidden" name="taxAmount<s:property value="billApplyId"/>" value="<s:property value="billAmountTax"/>"/>
				</td>
				<td align="left"><s:property value="typeManageService.getYXTypeManage(1012L ,billNature).typeName"/></td>
				<td align="left"><s:property value="typeManageService.getYXTypeManage(1004L ,billType).typeName"/></td>
				<td align="left"><s:property value="billContent"/></td>
			</tr>
		</s:iterator>
	</table>
	<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
	</TABLE>
</s:form>

</body>
</html>
