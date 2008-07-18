<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>

<title>关联申请单</title>

<script language="javascript">
function association(){
	//通过合同号有无，判断是修改已签、未签申请
	var consysid = document.getElementById("consid").value;
	if(consysid.length < 1 ){
		document.forms(0).action="createInvoice.action?method=applicationsAssociation";	
	}
	else{
		document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=applicationsAssociation";	
	}
	document.forms(0).submit();
}
function selectt(){
	//通过合同号有无，判断是修改已签、未签申请
	var consysid = document.getElementById("consid").value;
	if(consysid.length < 1){
		document.forms(0).action="createInvoice.action?method=findAssociation&iid="+document.getElementById("danhao").value;	
	}
	else{
		document.forms(0).action="createInvoice.action?method=openAssociationByConsid"+"&contractmainsid="+consysid+"&iid="+document.getElementById("danhao").value;
	}
	document.forms(0).submit();
}
</script>
</head>
<body><br>
<s:form action="" theme="simple">
	<s:hidden id="consid"  value="${contractmainsid}"/>
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">
				<tr align="center">
					<td colspan=2 class="bg_table01">请输入申请单号</td>
					<td colspan=2 class="bg_table01"><input type="text" id="danhao"></td>
					<td colspan=2 class="bg_table01"><input class="button01" type="button" value="查 找" onclick="javascript:selectt();" /></td>
				</tr>
				<tr align="center">
				<tr align="center">
					<td width="9%" class="bg_table01"  nowrap>选择</td>
					<td width="13%" class="bg_table01"  nowrap>申购单号</td>
					<td width="18%" class="bg_table01"  nowrap>客户名称</td>
					<td width="14%" class="bg_table01"  nowrap>申购日期</td>
					<td width="14%" class="bg_table01"  nowrap>申购内容</td>
					<td width="14%" class="bg_table01"  nowrap>申购金额</td>
				</tr>

				<s:iterator id="result" value="info.result">
					<tr align="center">
						<td><input type="checkbox" name="associationIds"
							value="<s:property value="#result[0].id"/>" ></td>
						<td><s:property value="#result[0].applyId"/></td>		
						<td><s:property value="#result[1].name"/></td>
						<td><s:date name="#result[0].applyDate" format="yyyy-MM-dd" /></td>
						<td><s:property value="#result[0].applyContent"/></td>
						<td><s:property value="#result[0].applymoney"/></td>
					</tr>
				</s:iterator>
			</table>
			</td>
		</tr>
	</table>
	<table cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
						<tr class="bg_table01">
			<td align="center" colspan="8"><input class="button01"
				type="button" value="关  闭" onclick="window.close()" /> 
				<input class="button01" type="button" value="关 联" onclick="javascript:association();" /></td>
		</tr>
	</table>
</s:form>
</body>

</html>
