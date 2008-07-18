<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>选择申购</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
<script language="javascript">
	function addSp(){
		document.searchPurQuery.action="<s:url action="searchPurQuery"><s:param name="method">conPur</s:param></s:url>";
		document.searchPurQuery.submit();
	}
<s:if test="#isRelationSuccess == 'true' ">
window.opener.refreshPage(); 
window.close();
</s:if>
</script>

</head>
<body>
<s:form action="searchPurQuery" theme="simple">
   <s:hidden name="mainid"></s:hidden>
			<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
				<tr>
					<td align="right" colspan="8" class="bg_table01" height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr>
				    <td width="97"></td>
					<td  align="left">
					</td>
				    <td colspan="5" align="left"></td>
				</tr>
			</table>
			<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
				<tr align="center">
					<td width="9%" class="bg_table01"  nowrap>选择</td>
					<td width="13%" class="bg_table01"  nowrap>申购单号</td>
					<td width="18%" class="bg_table01"  nowrap>客户名称</td>
					<td width="14%" class="bg_table01"  nowrap>申购日期</td>
					<td width="14%" class="bg_table01"  nowrap>申购内容</td>
					<td width="14%" class="bg_table01"  nowrap>申购金额</td>
				</tr>
				<s:iterator value="info.result">
					<tr align="center">
						<td><input type="checkbox" name="selectid" value="<s:property value="id"/>" /></td>
	                    <td><s:property value="applyId"/></td>
                        <td><s:property value="customerId"/></td>
                        <td><s:date name="applyDate" format="yyyy-MM-dd"/></td>
                        <td><s:property value="applyContent"/></td>
                        <td><s:property value="applymoney"/></td>
					</tr>
			   </s:iterator>
			</table>
			<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				  <tr valign="top">
					  <td class="bg_table04"><s:if test="info.result!=null"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></s:if></td>
				  </tr>
		    </TABLE>
		    <TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				  <tr valign="top">
					  <td class="bg_table02" align="center">
					    <input type="button" value="添加" onclick="addSp()">
					    <input type="button" value="关闭" onclick="window.close()">
					  </td>
				  </tr>
		    </TABLE>
  </s:form>
</body>
</html>
