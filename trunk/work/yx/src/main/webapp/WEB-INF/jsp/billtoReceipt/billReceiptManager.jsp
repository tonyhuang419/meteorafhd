<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>发票/收据管理</title>
<script language="javascript">
	function reflushPage(){
		location.href="../billtoReceipt/billReceipt.action?method=showAdmit";
	}
	function delAmount(delId){
		if(confirm("确定要删除吗?")){
			document.forms(0).amountId.value=delId;
			document.forms(0).method.value = "delMount";
			document.forms(0).submit();
		}
	}
	
	function queren(){
		if(confirm("确认无误,是否要确定录入?")){
			document.forms(0).method.value = "updateInputState";
			document.forms(0).submit();
		}
	}
	function closeO(){
		opener.refreshPage();
		window.close();
	}
</script>
<style type="text/css">
		<!--
		body {
			background-color: #FFFFFF;
		}
		-->
</style>

</head>
<body>
<s:form action="billReceipt" theme="simple">
<s:hidden name="method"></s:hidden>
<s:hidden name="amountId"></s:hidden>
<s:hidden name="billId"></s:hidden>
    <div align="left" style="color: #000000">
		<p>当前页面：开票管理 -&gt; 发票/收据管理</p>
	</div>
	<iframe name="errorsFrame" frameborder="0"
							framespacing="0" height="0" width="100%" scrolling="no"></iframe>
      <table align="center" border=1  bordercolor="#808080" style="border-collapse: collapse;" cellpadding="1" cellspacing=1 width="100%" id="billList">
        <tr align="center">
           <td nowrap width="8%" class="bg_table01">开票编号</td>
           <td nowrap width="8%" class="bg_table01">合同号</td>
          <td nowrap width="8%" class="bg_table01">开票金额</td>
          <td nowrap width="7%" class="bg_table01">发票/收据号</td>
          <td nowrap width="11%" class="bg_table01">开票日期</td>
          <td nowrap width="10%" class="bg_table01">操作</td>
        </tr>
        <s:iterator value="billList" id="bill" status="mainStatus"> 
        <tr class="bg_table02" id="bill<s:property value="#bill[0].billApplyId"/>" >
        	
        	<td>
        		<s:property value="#bill[0].billApplyNum" /> 
        	</td>
        	<td>
        	<s:if test = "#bill[1]!=null">
        		<s:property value="#bill[1]" /> 
        	</s:if>
        	<s:else>
        		<s:property value="#bill[0].contactName" /> 
        	</s:else>
        	</td>
        	<s:set name="billIndex"  value="0"></s:set>
        	<s:iterator value="invoiceList" id="invoice" status="billSta">
        		<s:if test="#billIndex == 0">
					<s:if test="applyInvoiceId == #bill[0].billApplyId">
			       			<td align="right"><s:property value="invoiceAmount"/></td>
			       			<td align="center"><s:property value="invoiceNo"  /></td>
			       			<td align="center"><s:property value="invoiceDate"/></td>
			       			<td align="center">
				       			<a href="#" onclick="javascript:window.open('billReceipt.action?method=showNewUpdateMount&amountId=<s:property value="id"/>&billId=<s:property value="#bill[0].billApplyId"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600')">修改</a>
				       			<s:if test="receAmount == 0">
			 				/
			 				<a href="#" onclick="delAmount(<s:property value="id"/>)">删除</a>
			 				</s:if>
			       			</td>	
			       			<s:set name="billIndex" value="1"></s:set>	 
		      	 	</s:if>		
	      	 	</s:if>
	      	 	<s:else>
	      	 		<s:if test="applyInvoiceId == #bill[0].billApplyId">
					<tr class="bg_table02" id="subBillAmount<s:property value="#bill[0].billApplyId"/>" align="center" >
						<td colspan = "2">
						<td align="right" ><s:property value="invoiceAmount"/></td>
		       			<td  ><s:property value="invoiceNo"  /></td>
		       			<td  ><s:property value="invoiceDate"/></td>
		       			<td >
			       			<a href="#" onclick="javascript:window.open('billReceipt.action?method=showNewUpdateMount&amountId=<s:property value="id"/>&billId=<s:property value="#bill[0].billApplyId"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600')">修改</a>
			       			<s:if test="receAmount == 0">
			 				/
			 				<a href="#" onclick="delAmount(<s:property value="id"/>)">删除</a>
			 				</s:if>
		       			</td>	
					</tr>
					</s:if>
	      	 	</s:else>
       		</s:iterator>   
       		 <s:if test="#billIndex==0">
       			<td colspan="4" class="bg_table02"></td>
       		</s:if>
       	  </tr>
       	</s:iterator>
       	<tr class="bg_table03">
       		<td colspan="6" align="center">
       			<input type="button" name="1" value="确认录入" class="button01" onclick="queren()"/>      
       			<input type="button" name="1" value=" 关  闭 " class="button01" onclick="closeO()"/>      
       			 		
       		</td>
       	</tr>
      </table>
</s:form>
</body>
</html>
