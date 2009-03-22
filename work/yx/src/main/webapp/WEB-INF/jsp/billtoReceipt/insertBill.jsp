<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>录入修改</title>
</head>
<script type="text/javascript">
</script>
<body style="margin: 0px;" >

<s:form action="billReceipt" theme="simple">
<s:hidden name="method" value="newSaveMount"></s:hidden>
<input type="hidden" name="billId" value="<s:property value="${billId}"/>"/>
<s:hidden name="amountId"></s:hidden>
 <div align="left" style="color: #000000">
		<p>当前页面：开票管理 -&gt; 录入&修改</p>
	</div>
	<s:iterator id="erMessage" value="processResult.errorMessages">
	<font color="red"><strong><s:property value="#erMessage"/></strong></font><br/>
	</s:iterator>
	<div>
	<font color="red">
	<s:if test="errorInfo != 1">
		<s:property value="errorInfo"/>
	</s:if>
	</font>
	</div>
	<iframe name="errorsFrame" frameborder="0"
							framespacing="0" height="0" width="100%" scrolling="no"></iframe>
	 <table align="center" border=1 cellpadding="1" cellspacing=1 
				width="100%" id="billApplyList"  bordercolor="#808080" style="border-collapse: collapse;">
	 		<tr class="bg_table01">
	 			<td align="center">编号</td>
	 			<td align="center">发票/收据号</td>
	 			<td align="center">发票金额</td>
	 			<td align="center">开票日期</td>
	 			<td align="center">操作
	 			</td>
	 		</tr>
	 	<s:iterator value="invoiceList" status="status" id="invoice">
	 		<tr class="bg_table02">
	 			<td align="center"><s:property value="#status.index + 1"/></td>
	 			<td>
	 				<s:property value="invoiceNo"/><s:if test="inputState == 1"> 已确认</s:if>
	 			</td>
	 			<td align="right"><s:property value="invoiceAmount"/></td>
	 			<td align="center"><s:property value="invoiceDate"/></td>
	 			<td>
	 				<s:if test="inputState == 1">
	 					<a href="#" onclick="updateInvoice(<s:property value="id" />)">修改</a>/
	 					<a href="#" onclick="delInvoice(<s:property value="id" />)" >删除</a>
	 				</s:if>
	 				<s:else>
	 				<a href="#" onclick="updateSubmit(<s:property value="id" />)">修改</a>/
	 				<a href="#" onclick="delSubmit(<s:property value="id" />)" >删除</a>
	 				</s:else>
	 			</td>
	 		</tr>
	 	</s:iterator>
	 	<s:if test="totalAmount - balance > 0 ">
			<tr class="bg_table02" >
				<td>
				</td>
				<td>
	        		<FONT color="red"> *</FONT><s:textfield name="ii.invoiceNo" size="10" maxlength="10" onkeydown="valNum(event);" />
	        	</td>
	        	<td >
	        		<FONT color="red"> *</FONT><input type="text" value="<s:property value="totalAmount - balance"/>" name="ii.invoiceAmount" onblur="formatInputNumber(this)" size="10" onKeyUp="quanjiao(this)"  />
	        	</td>        	
	        	<td>			  
				  	<FONT color="red"> *</FONT><input type="text" id="billDate" value="<s:property value="defaultDate"/>" name="ii.invoiceDate" size="8">
				  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('billDate<s:property value="#bill[0].billApplyId"/>')" align=absMiddle alt=""  />		  	
	        	</td>
	        	<td>
	        		<a href="#" onclick="saveSubmit()" >添加</a>/
	        		<a href="#" onclick="addRelation()">添加联票</a>
	        	</td>       
	       	</tr>
	     </s:if>
	       	<tr class="bg_table03">
	       		<td colspan="5" align="center">
	       			<input type="button" name="1" class="button01" value=" 关  闭 " onclick="closeO()"/>
	       		</td>
	       	</tr>
	</table>
</s:form>
<script type="text/javascript">
	function saveSubmit(){
		if(!validate()){
			if(!checkAmount()){
				document.forms(0).submit();
			//	opener.refreshPage();
			}
		}
	}
	function delSubmit(iiIdVal){
		if(confirm("确定要删除吗?")){
			document.forms(0).amountId.value = iiIdVal;
			document.forms(0).method.value = "delNewAmount";
			document.forms(0).submit();
			//opener.refreshPage();
		}
	}
	function updateSubmit(iiIdVal){
		openWin2("../billtoReceipt/billReceipt.action?method=showNewUpdateMount&billId=<s:property value="#parameters.billId" />&amountId="+iiIdVal,400,400,"updateInovice");
	}
	 function reflushPage(){
		 location.href="../billtoReceipt/billReceipt.action?method=showInsertOrUpdate&billId=<s:property value="#parameters.billId" />";
    }
    function validate(){
		var ev2=new Validator();
		with(billReceipt){
	      	   ev2.test("notblank","发票号为空!",$("ii.invoiceNo").value);
	      	   ev2.test("notblank","开票金额为空!",$("ii.invoiceAmount").value);
	      	   ev2.test("notblank","开票日期为空!",$("ii.invoiceDate").value);
	      	   ev2.test("dateYX","开票日期格式不正确!",$("ii.invoiceDate").value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		  }
		  return false;
	}
	//验证金额是否超过可以输入金额
	function checkAmount(){
		var balance = "<s:property value="${balance}"/>";
		if(balance == ""){
			balance = 0;
		}
		var iiAmount = $("ii.invoiceAmount").value;
		if(iiAmount != 0.00){
			var total = parseFloatNumber(balance) + parseFloatNumber(iiAmount) ;
			var totalAm = "<s:property value="${totalAmount}"/>";
			var totalAmount = parseFloatNumber(totalAm);
			var remaindAmount = totalAmount - parseFloatNumber(balance);
			if(total > totalAmount){
				alert("你输入的金额已经超过可输入金额,你还可以输入"+remaindAmount+" !");
				return true;
			}
			else{
				return false;
			}
		}
		else{
			alert("你输入的金额为0!");
			return true;
		}
	}
	function closeO(){
		opener.refreshPage();
		window.close();
	}
	function closeO1(){
		opener.refreshPage();
	}
	// 添加联票
	function addRelation(){
		if(!validate()){
			if(!checkAmount()){
				var shengyu = "<s:property value="totalAmount - balance"/>" ;
				var iiAmount = $("ii.invoiceAmount").value;
				var shengyuNum = parseFloatNumber(shengyu) / parseFloatNumber(iiAmount) ;
				if(shengyuNum>20)
				{
					if(confirm("你输入的联票金额开出的联票可能大于20张!")){
						document.forms(0).method.value = "addRelationBill";
						document.forms(0).submit();
					}
				}
				else{
					document.forms(0).method.value = "addRelationBill";
					document.forms(0).submit();
				}
			}
		}
	}
	
	function updateInvoice(invoiceId){
		openWin2("billReceipt.action?method=showUpdateInvoiceNo&billId=<s:property value="#parameters.billId" />&amountId="+invoiceId,400,300,"updateInovice2");
	}
	function delInvoice(invoiceId){
		if(confirm("确定删除发票？")){
			location.href("billReceipt.action?method=delInvoice&billId=<s:property value="#parameters.billId" />&amountId="+invoiceId);
		}
	}
</script>
<s:if test="errorInfo == 1">
	<script type="text/javascript">
		closeO1();
	</script>
</s:if>
</body>
<!--<script type="text/javascript">
	opener.refreshPage();
</script>
--></html>