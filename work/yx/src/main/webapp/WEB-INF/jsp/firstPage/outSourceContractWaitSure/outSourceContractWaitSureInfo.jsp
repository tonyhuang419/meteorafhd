<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>外协合同待确认</title>
</head>
<body>
<s:form action="noContractSignInvoice" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<div align="left">
   <div  style="color:#000000"><p>当前页面：首页提醒->外协合同待确认</p></div>
</div>
<table width="100%" border="1"  bordercolor="#808080" style=" border-collapse: collapse;">
        <tr class="bg_table01" align="center">
          <td nowrap>外协合同名称</td>
          <td nowrap>合同号</td> 
          <td nowrap>项目号</td>
          <td nowrap>外协供应商</td>
          <td nowrap>签订日期</td>
          <td nowrap>预计结束日期</td>
          <td nowrap>合同金额</td>
        </tr>
        <s:iterator id="result" value="info.result">
	    <tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
			<td align="left"> <s:property value="#result[0].assistanceName" /></td>
			<td nowrap align="left"><s:property value="#result[2]"/></td>	
			<td align="left"> <s:property value="#result[0].mainProjectId"/></td>	
			<td nowrap align="left"><s:property value="#result[1]" /></td>
			<td nowrap><s:property value="#result[0].contractDate" /></td>
			<td nowrap><s:property value="#result[0].endDate" /></td>
			<td nowrap align="right"><s:property value="#result[0].contractMoney" /></td>
		</tr>
        </s:iterator>
      </table>
  <table cellSpacing=1 cellPadding=2 width="100%" border=0>
	<tr>
		<td class="bg_table04"><baozi:pages value="info"  beanName="info" formName="forms(0)" /></td>
	</tr>
</table>

</s:form>

</body>
</html>
