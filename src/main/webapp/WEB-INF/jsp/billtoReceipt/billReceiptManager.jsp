<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>开票/收据管理</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
		function openUrl(url){
			window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
		}
		function addAmount(billId){
			var billList = document.getElementById("billList");
			var rows = billList.rows;
			var totalAmount = 0;    
			var sumAmount = 0;
			for(var i=0;i<rows.length;i++){
				var row = rows[i];
				if(row.id !=null && row.id.indexOf(""+billId) != -1){
					//总金额
					if(row.id.indexOf("bill") == 0){
						var totalstr = row.cells(5).innerText;
						totalAmount = parseFloatNumber(totalstr);
						//加输入框里的
						var inputAmount = parseFloatNumber(row.cells(8).firstChild.value);
						if(isNaN(inputAmount)||row.cells(8).firstChild.value.length == 0){
							alert("请输入正确开票金额");
							return;
						}
						else if(row.cells(9).firstChild.value.length==0)
						{
							alert("请输入发票号!")
							return;
						}
						else if(row.cells(10).firstChild.value.length==0)
						{
							alert("请选择日期")
							return;
						}
						else{
							sumAmount += inputAmount;
						}
					}
					//子金额
					if(row.id.indexOf("subBillAmount") == 0){
						var sumstr = row.cells(1).innerText;
						sumAmount+=parseFloatNumber(sumstr);
					}
				}
			}
			if(sumAmount <= totalAmount){
				eval("addAmount"+billId+".submit()");
			}else{
				alert("开票金额总金额("+sumAmount+")不能大于申请金额("+totalAmount+")");
			}
		}		
	// 根据开票状态查询
	function billStateChange(selObj){
		
	    location.href="../billtoReceipt/billReceiptQuery.action?confirm="+document.getElementById("billState").value+"&selectState="+document.getElementById("selectState").value;
	}
	function selectStateChange(selState)
	{
		
	 	location.href="../billtoReceipt/billReceiptQuery.action?confirm="+document.getElementById("billState").value+"&selectState="+document.getElementById("selectState").value;
	}
	
	function refreshClient(){
		location.reload();
		alert("修改成功!");
	}

</script>
<style type="text/css">
		<!--
		body {
			background-color: #FFFFFF;
		}
		.STYLE2 {
			font-size: 16px
		}
		-->
</style>

</head>
<body>
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
  <tr>
    <td align="center">
    <div align="left">
			  <p>当前页面：开票管理 -> 开票/收据管理</p>
			</div>
	<s:form action="billReceipt" theme="simple">
    <table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%" class="bg_table03">
        <tr>
          <td colspan="2" align="right"  class="bg_table01" height="3">
          <img src="./../images/temp.gif" width="1" height="1"></td>
        </tr>
        <tr class="bg_table03">
          <td width="34%" align="right" class="bg_table03"><p align="center">开票状态：
              <s:select name="confirm" id="billState" list="#@java.util.TreeMap@{1:'已录入开票/收据',2:'未录入开票/收据'}" onchange="billStateChange(this)" emptyOption="true" ></s:select>
              &nbsp;签收状态：
              <s:select name="selectState" id="selectState" list="#@java.util.TreeMap@{1:'未签收',2:'已签收'}" onchange="selectStateChange(this)" emptyOption="true" ></s:select>
              &nbsp;&nbsp;</td>
          <td width="66%" align="right" class="bg_table03">&nbsp;</td>
        </tr>
      </table>
      </s:form>
      <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="billList">
        <tr align="center">
          <td width="6%" class="bg_table01">开票申请编号</td>
          <td width="6%" class="bg_table01">申请人</td>
          <td width="7%" class="bg_table01">合同名称</td>
          <td width="9%" class="bg_table01">客户名称</td>
          <td width="7%" class="bg_table01">申请日期</td>
          <td width="7%" class="bg_table01">申请金额</td>
          <td width="10%" class="bg_table01">票据类型</td>
          <td width="13%" class="bg_table01">开票内容</td>
          <td width="8%" class="bg_table01">开票金额</td>
          <td width="7%" class="bg_table01">发票/收据号</td>
          <td width="10%" class="bg_table01">开票日期</td>
          <td width="8%" class="bg_table01">操作</td>
        </tr>
        <s:iterator value="info.result" id="bill">
      <s:form name="addAmount%{#bill[0].billApplyId}" action="billReceipt" theme="simple">
      <s:hidden name="method" value="saveMount"></s:hidden>
      <input type="hidden" name="mainConId" value="<s:property value="#bill[0].contractMainInfo"/>" />
      <input type="hidden" name="billType" value="<s:property value="#bill[0].billType"/>" />
        <tr id="bill<s:property value="#bill[0].billApplyId"/>" align="center">
        	<td class="bg_table02"> <s:property value="#bill[0].billApplyNum" /></td>
        	<td class="bg_table02"> <s:property value="#bill[2].name" /> </td>
        	<td class="bg_table02"> <s:property value="#bill[0].contactName" /> </td>
        	<td class="bg_table02"> <s:property value="#bill[1].name" /></td>
        	<td class="bg_table02"> <s:property value="#bill[0].applyId" /> </td>
        	<td class="bg_table02"> <s:property value="#bill[0].billAmountTax" /></td>
        	<td class="bg_table02">
        		 <s:property value="typeManageService.getYXTypeManage(1004L ,#bill[0].billType ).typeName" id="billType" />
        		 
        	 </td>
        	<td class="bg_table02"> <s:property value="#bill[0].billContent" /> </td>
        	
        	<td class="bg_table02">
        		<input type="text" name="ii.invoiceAmount" size="10" onKeyUp="quanjiao(this)"/>
        	</td>
        	<td class="bg_table02">
        		<s:textfield name="ii.invoiceNo" size="10"/>
        	</td>        	
        	<td class="bg_table02">			  
			  	<input type="text" id="billDate<s:property value="#bill[0].billApplyId"/>" name="ii.invoiceDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="7">
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('billDate<s:property value="#bill[0].billApplyId"/>')" align=absMiddle alt=""  />
			  	
        	</td>
        	<td class="bg_table02">
        	<s:hidden name="billId" value="%{#bill[0].billApplyId}"></s:hidden>
        
        	<a href="javascript:addAmount(<s:property value="#bill[0].billApplyId"/>);" />添加</a> </td>       
       	</tr>
	</s:form>
				<s:iterator value="invoiceList" id="invoice">
				<s:if test="applyInvoiceId == #bill[0].billApplyId">
				<tr id="subBillAmount<s:property value="#bill[0].billApplyId"/>" align="center"  class="bg_table02">
		       		<td colspan="8"></td>	       		       		  	       		  
		       		<td  class="bg_table02"><s:property value="invoiceAmount"/></td>
		       		<td  class="bg_table02"><s:property value="invoiceNo"  /></td>
		       		<td  class="bg_table02"><s:property value="invoiceDate"/></td>
		       		<td  class="bg_table02">
			       		<a href="#" onclick="javascript:window.open('billReceipt.action?method=showUpdateMount&iiId=<s:property value="id"/>&billId=<s:property value="#bill[0].billApplyId"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600')">修改</a>
			       		/
			       		<s:form name="delAmount%{id}" action="billReceipt" theme="simple">
				       		<s:hidden name="method" value="delMount"></s:hidden>
				       		<s:hidden name="amountId" value="%{id}"></s:hidden>
				       			<a href="javascript:if(confirm('确定要删除此关联吗?')) delAmount<s:property value="id"/>.submit()">删除</a>
			       		</s:form>
		       		</td>		       	  
	      	 	</tr>	    
	      	 	</s:if>
       		</s:iterator>
       
       		<tr><td class="bg_table02" colspan="12"><hr></td></tr>
       	</s:iterator>
       
      </table>
 	  <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
	  </TABLE>
      </td>
  </tr>
</table>
</body>
</html>
