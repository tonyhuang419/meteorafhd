<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>新签合同信息</title>

<script language="javascript">

</script>

<style type="text/css">

</style>
</head>
<body>

<s:form action="noContractSignInvoice" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<div align="left">
   <div  style="color:#000000"><p>当前页面：首页提醒->未签合同开票</p></div>
</div>
<table width="100%" border="1"   id="noContractSignInvoiceInfo" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr>
          <td  nowrap   class="bg_table01">
              <div align="center">申请编号</div>
            </td>
          <td   nowrap  class="bg_table01">
              <div align="center">合同名称</div>
            </td>
          <td    nowrap  class="bg_table01">
              <div align="center">客户名称</div>
            </td>
          <td   nowrap   class="bg_table01">
              <div align="center">销售员</div>
            </td>
          <td    nowrap  class="bg_table01">
              <div align="center">申请日期</div>
           </td>
          <td   nowrap   class="bg_table01">
              <div align="center">申请金额</div>
          </td>
           <td   nowrap   class="bg_table01">
              <div align="center">票据类型</div>
          </td>
           <td   nowrap   class="bg_table01">
              <div align="center">开票金额</div>
          </td>
            <td   nowrap   class="bg_table01">
              <div align="center">开票日期</div>
          </td>
        </tr>
        
        <s:iterator value="info.result" id="noContractSignInvoice" status="status">
        <tr  onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
          <td><div align="left"><s:property value="#noContractSignInvoice[0].billApplyNum"/></div></td>
          <td><div align="left"><s:property value="#noContractSignInvoice[0].contactName"/></div></td>
          <td><div align="left"><s:property value="#noContractSignInvoice[3]" /></div></td>
          <td><div align="left"><s:property value="#noContractSignInvoice[2]" /></div></td>
          <td><div align="center"><s:property value="#noContractSignInvoice[0].applyId" /></div></td>
          <td><div align="right"><s:property value="#noContractSignInvoice[0].billAmountTax" /></div></td>
          <td><div align="left"><s:property value="typeManageService.getYXTypeManage(1004,#noContractSignInvoice[0].billType).typeName"/></div></td>
          <td><div align="right"><s:property value="#noContractSignInvoice[1].invoiceAmount" /></div></td>
          <td><div align="center"><s:property value="#noContractSignInvoice[1].invoiceDate" /></div></td>
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
