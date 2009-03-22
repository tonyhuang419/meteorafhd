<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>外协合同付款待确认</title>
</head>
<body>
<s:form action="noContractSignInvoice" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<div align="left">
   <div  style="color:#000000"><p>当前页面：首页提醒->外协合同付款待确认</p></div>
</div>
<table width="100%" border="1"   bordercolor="#808080" style=" border-collapse: collapse;">
      <tr class="bg_table01" align="center">
          <td nowrap>外协合同号</td>
          <td nowrap>申请序号</td>
          <td nowrap>外协合同名称</td>
	      <td nowrap>外协供应商</td>
          <td nowrap>申请日期</td>
          <td nowrap>申请金额</td>
          <td nowrap>销售员</td>
        </tr>
        <s:iterator id="apply" value="info.result">
        <tr  onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
          <td align="left" ><s:property value="#apply[1]"/></td>
	    	<td align="left" ><s:property value="#apply[0].applyInfoCode"/></td>
	      	<td align="left" ><s:property value="#apply[2]"/></td>
         	<td align="left" ><s:property value="#apply[3]"/></td>
         	<td ><s:property value="#apply[0].applyDate"/></td>
         	<td align="right" ><s:property value="#apply[0].payNum"/></td>
       		<td align="left" ><s:property value="#apply[4]"/></td>
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
