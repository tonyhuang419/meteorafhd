<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>红冲发票</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function search()
	{
		document.baseData.submit();
	}
	
	function cancelBill()
	{
		if(confirm("确定要红冲此票吗？")){
			document.baseData.method.value="hongChongBillService";
			document.baseData.submit();
		}
	}
	
	function childWindowCallBack(){
		search();
	}
</script>
</head>
<body>
<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:iterator id="erMessage" value="processResult.errorMessages">
	<font color="red"><strong><s:property value="#erMessage"/></strong></font><br/>
</s:iterator>
<s:form action="baseData" theme="simple">
	<s:hidden name="method" value="hongChongBill"></s:hidden>
	<table align="center" width="100%">
	<tr>
	<td>
		<table align=left width="60%">
			<tr>
				<td>发票号：<s:textfield name="invoiceId" size="11" /></td>
				<td><input type="button" onclick="search()" value="搜索"
					class="button01" /></td>
				<td><input type="button" onclick="cancelBill()" value="红冲发票"
					class="button01" /></td>
				<td></td>
			</tr>
		</table>
		</td>
		</tr>
		<tr>
			<td>
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill" bordercolor="#808080"
				style="border-collapse: collapse;">
				<s:if test="info != null">
					<s:set name="resultList" value="info.result"></s:set>
					<tr align="center">
						<td nowrap="nowrap" class="bg_table01">选择</td>
						<td nowrap="nowrap" class="bg_table01">销售员</td>
						<td nowrap="nowrap" class="bg_table01">项目号</td>
						<td nowrap="nowrap" class="bg_table01">合同号</td>
						<td nowrap="nowrap" class="bg_table01">合同名称</td>
						<td nowrap="nowrap" class="bg_table01">开票客户名称</td>
						<td nowrap="nowrap" class="bg_table01">发票类型</td>
						<td nowrap="nowrap" class="bg_table01">开票金额</td>
						<td nowrap="nowrap" class="bg_table01">开票日期</td>
					</tr>
					<s:iterator value="resultList" id="invoice" status="status">
						<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
							onMouseOut="this.bgColor='#FFFFFF';">
							<td><input type="radio" name="invoiceIds" value="<s:property value="#invoice[4].id"/>" /></td>
							<td align="left"><s:property value="#invoice[3].name" /></td>
							<td align="left">
								<s:iterator id="s" value="itemNoList.get(#invoice[0].billApplyId)"  status="stat2"  >    
            						<s:property value="s"/><s:if test="!#stat2.last"><br/></s:if>
           						</s:iterator>
							</td>
							<td align="left"><s:property value="#invoice[1].conId" /></td>
							<td align="left"><s:property value="#invoice[1].conName" /></td>
							<td align="left"><s:property value="#invoice[2].name" /></td>
							<td align="left"><s:property
								value="typeManageService.getYXTypeManage(1004,#invoice[0].billType).typeName" /></td>
							<td align="right"><s:property
								value="#invoice[4].invoiceAmount" /></td>
							<td align="center"><s:property
								value="#invoice[4].invoiceDate" /></td>
						</tr>
					</s:iterator>
					<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04"><baozi:pages value="info"
								beanName="info" formName="forms(0)" /></td>
						</tr>
					</TABLE>
				</s:if>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>