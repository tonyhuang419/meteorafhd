<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>

<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>未签开票关联合同</title>
</head>
<script type="text/javascript">
	function searchCon(){
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
              window.open('relationContractQuery.action?method=searchContract&ids='+checkStr.substring(1),'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=900');
         }
		 if(j==0){
		        alert("您还没有选择修改的对象！");
		 }
 }
function refreshClient(){
		location.reload();
	}
</script>
<body style="margin: 0px;">
<div align="left" style="color: #000000">当前页面：开票管理 -> 未签开票关联合同</div>
<s:form action="createInvoice" theme="simple">
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr width="34%" class="bg_table02" align="center">
			<td  align="right" class="bg_table03" />
			<div align="center">
				<input type="button" class="button01" name="submit" value=" 关联合同 " onclick="searchCon()"/>
			</div>
			</td>
		</tr>
	</table>
	<table align="center" border=0 cellpadding="1" cellspacing=1
		width="100%" id="billApplyList">
		<tr align="center">
			<td class="bg_table01">
			选择
			</td>
			<td class="bg_table01">
			开票申请编号
			</td>
			<td class="bg_table01">
			申请日期
			</td>
			<td class="bg_table01">
			申请金额
			</td>
			<td class="bg_table01">
			开票性质
			</td>
			<td class="bg_table01">票据类型</td>
			<td class="bg_table01">
			开票内容
			</td>
		</tr>
		<s:iterator value="info.result">
			<tr align="center">
				<td class="bg_table02">
					<input type="radio" name="ids" value="<s:property value="billApplyId"/>" />
				</td>
				<td class="bg_table02"><s:property value="billApplyNum"/></td>
				<td class="bg_table02"><s:property value="applyId"/></td>
				<td class="bg_table02"><s:property value="billAmountTax"/></td>
				<td class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1022L ,billNature).typeName"/></td>
				<td class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1004L ,billType).typeName"/></td>
				<td class="bg_table02"><s:property value="billContent"/></td>
			</tr>
		</s:iterator>
	</table>
	<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
		<tr valign="top">
			<td height="20" class="bg_table02">
			<table width="100%" border="0">
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</table>
			</td>
		</tr>
	</TABLE>
</s:form>
</body>

</html>
