<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>增票已开未签信息</title>

<script language="javascript">

</script>

<style type="text/css">

</style>
</head>
<body>

<s:form action="vATInvoiceNoSign" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<div align="left">
   <div  style="color:#000000"><p>当前页面：首页提醒-&gt;发票已开未签收</p></div>
</div>
<table width="100%" border="1"   id="vATInvoiceNoSignInfo" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr class="bg_table01">
          <td  nowrap>
              <div align="center">申请编号</div>
          </td>        
         <td   nowrap>
              <div align="center">销售员</div>
          </td>          
          <td   nowrap>
              <div align="center">合同名称</div>
           </td>
         <td   nowrap>
              <div align="center">合同号</div>
          </td>  
          <td   nowrap>
              <div align="center">项目号</div>
          </td>    
          <td    nowrap>
              <div align="center">客户名称</div>
           </td>
		 	<td   nowrap>
              <div align="center">发票号</div>
          </td>
           <td   nowrap>
              <div align="center">开票金额</div>
          </td>
           <td   nowrap>
              <div align="center">开票日期</div>
          </td>
        </tr>
        <s:set name="applyBillSid" value=""/>
        <s:iterator value="info.result" id="noContractSignInvoice" status="status">
        <tr  onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
        <s:if test="#applyBillSid != #noContractSignInvoice[0].billApplyId ">
        	<s:set name="applyBillSid" value="#noContractSignInvoice[0].billApplyId"/>
			<td><div align="left"><s:property value="#noContractSignInvoice[0].billApplyNum"/></div></td>
			<td><div align="left"><s:property value="#noContractSignInvoice[2]" /></div></td>
			<td><div align="left"><s:property value="#noContractSignInvoice[0].contactName"/></div></td>
			<td><div align="left"><s:property value="#noContractSignInvoice[4]"/></div></td>
			<td><div align="left">
					<s:iterator id="s" value="itemNoList.get(#noContractSignInvoice[0].billApplyId)"  status="stat2"  >    
            			<s:property value="s"/><s:if test="!#stat2.last">,</s:if>
           			</s:iterator>
				</div></td>
			<td><div align="left"><s:property value="#noContractSignInvoice[3]" /></div></td>
        </s:if>
        <s:else>
        	<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
        </s:else>
          <td><div align="center"><s:property value="#noContractSignInvoice[1].invoiceNo" /></div></td>
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
