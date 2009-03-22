<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>收款管理明细</title>
<script type="text/javascript">
	function time(img){
	  var par=img.parentNode;
	  var text=par.firstChild;
	  ShowCalendar(text.id);
	}
	function setAddId(invoiceId,button)
	{
		var row = button.parentElement.parentElement;
	
		var recAmount = row.cells(1).firstChild.value;
		var arriveType = row.cells(2).firstChild.value;
		var arriveDate = row.cells(3).firstChild.firstChild.value;
		recAmount=recAmount.replace(/,/g,"");
		
		var numberReg=/^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
		if(!numberReg.test(recAmount))
		{
			alert("您输入的到款金额必须是不为零的数字");
			return false;
		}
		var arriveAmount = parseFloatNumber(recAmount);
		if (arriveAmount<=0.0){
			alert("请输入不为零的到款金额");
				return false;
			}
		var arriveTotalAmount =  parseFloatNumber(document.getElementById('receAmount'+invoiceId).innerHTML);
			if(isNaN(arriveTotalAmount)){
				arriveTotalAmount = 0;
			}
		var arriveInvoiceAmount =  parseFloatNumber(document.getElementById('invoiceAmount'+invoiceId).innerHTML);
		if(isNaN(arriveInvoiceAmount)){
			arriveInvoiceAmount = 0;
		}
		if(arriveInvoiceAmount < arriveAmount + arriveTotalAmount){
			alert("到款总金额不能大于发票金额");
			return false;
		}
		if(arriveType == null || ""==arriveType){
			alert("请输入到款方式");
			return false;
		}
		if(arriveDate == null || ""==arriveDate){
			alert("请输入到款日期");
			return false;
		}
		if(arriveDate != null){
			var strValue = ""+ arriveDate;
			var noSpaceStr = strValue.replace(/(^\s+)|\s+$/g,"");
			if(noSpaceStr.length > 0){
				var reg = /^(\d{4})-(([1-9])|(1[0-2]))-(([1-9])|([1-2][0-9])|([3][0-1]))$/;
				if(reg.test(noSpaceStr) == false){
					alert("你输入的日期格式不正确!");
				}
				return reg.test(noSpaceStr);
			}
			return true;
		}
		return true;
		
	
	}

	function delect(a)
	{
		 if(window.confirm("确定要删除吗?"))
		 {
			location.href="/yx/harvestMeansManager/harvestMeansManager.action?method=del&delid="+a+"";
		 }
	}
	
	function bbb(b)
	{
		location.href="/yx/harvestMeansManager/harvestMeansManager.action?method=enterUpdate&delid="+b+"";
	}

</script>
</head>

<body leftmargin="0">
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				
				  <tr><td height="3" align="left" >当前页面:收款管理->收款管理</td></tr>
		</table>
		
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%"  id="family" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td width="10%" class="bg_table01">发票/收据号</td>
					<td width="10%" class="bg_table01">客户名称</td>
					<td width="10%" class="bg_table01">合同号</td>
					<td width="10%" class="bg_table01">项目号</td>
					<td width="11%" class="bg_table01">开票金额</td>
					<td width="10%" class="bg_table01">余额</td>
					<td width="10%" class="bg_table01">到款金额</td>
					<td width="10%" class="bg_table01">到款方式</td>
					<td width="10%" class="bg_table01">到款日期</td>
					<td width="10%" class="bg_table01">操作</td>
				</tr>
		<s:iterator value="info.result" id="invoice" status="status"> 
		<s:form action="harvestMeansManager" theme="simple">	
			<s:hidden name="method" value="saveAmount" />
			<input type="hidden" name="ri.billSid" id="addid" value="<s:property value="#invoice[1].id"/>"/>
				<tr>					
					<td class="bg_table02" align="center"><s:property value="#invoice[1].invoiceNo" /></td>
					<td class="bg_table02" align="center"><s:property value="#invoice[2].name" /></td>
					<td class="bg_table02" align="center"><s:if test="#invoice[3]==null">无</s:if><s:else><s:property value="#invoice[3]" /></s:else></td>
					<td class="bg_table02" align="center"><s:if test="#invoice[4]==null">无</s:if><s:else><s:property value="#invoice[4]" /></s:else></td>
					<td id="invoiceAmount<s:property value="#invoice[1].id"/>" class="bg_table02" align="right"><s:property value="#invoice[1].invoiceAmount" /></td>
					<td id="receAmount<s:property value="#invoice[1].id"/>" class="bg_table02" align="right"><s:property value="#invoice[1].invoiceAmount - #invoice[1].receAmount" /></td>
			<s:set name="invliceIndex"  value="0"></s:set>
        	<s:iterator value="reveList.get(#status.index)" id="reve">
        		<s:if test="#invliceIndex == 0" >
			       			<td class="bg_table02" align="right"><s:property value="amount" /></td>
			       			<td class="bg_table02" align="center"><s:property value="typeManageService.getYXTypeManage(1017L,receType).typeName"  /></td>
			       			<td class="bg_table02" align="center"><s:date name="amountTime" format="yyyy-MM-dd" /></td>
			       			<td class="bg_table02">
			       				<a href="#"   onClick="bbb(<s:property value="#reve.id"/>);" >修 改</a>/
					     	   <a href="#" onClick="delect(<s:property value="#reve.id"/>);">删 除</a>
			       			</td>	
			       		<s:set name="invliceIndex" value="1"></s:set>	
		      	 </s:if>		
	      	 	<s:else>
					<tr class="bg_table02">
						<td  class="bg_table02" colspan = "6"> </td>
						<td class="bg_table02" align="right"><s:property value="amount" /></td>
			       			<td class="bg_table02" align="center"><s:property value="typeManageService.getYXTypeManage(1017L,receType).typeName"  /></td>
			       			<td class="bg_table02" align="center"><s:date name="amountTime" format="yyyy-MM-dd" /></td>
		       			<td  class="bg_table02" >
		       			<a href="#" onClick="bbb(<s:property value="#reve.id"/>);" >修 改</a>/
					     	   <a href="#" onClick="delect(<s:property value="#reve.id"/>);">删 除</a>		
		       			</td>		       			
		       		</tr>
	      	 	</s:else>
       		</s:iterator>   
       		<s:if test="#invliceIndex==0">
       			<td colspan="4" class="bg_table02"></td>
       		</s:if>
       		<tr>
       		 		<td colspan="6" class="bg_table02"></td>
    	   			<td class="bg_table02"><input type="text" name="ri.amount" size="10" onblur="formatInputNumber(this)"/></td>
  				   	<td class="bg_table02"><s:select name="ri.receType" list="receTypetrans" listKey="typeSmall"
				       listValue="typeName" required="true" emptyOption="true" headerValue=""> </s:select>
				    </td>
					<td class="bg_table02" width="10%" >
						<div align="left">
							<input  size="10" type="text" name="ri.amountTime" id="amountTime<s:property value="%{#status.index}"/>" ><img src="/yx/commons/images/calendar.gif" onClick="time(this);"  alt=""/>
				  		</div>
			  		</td>
				 <td width="12%" class="bg_table02"><input size="10" class="button01" type="submit" value="添 加" onclick="return setAddId(<s:property value="#invoice[1].id"/>,this);"/></td>	
    	   	 </tr>
    	 
    	<tr>
       	<td  colspan="10" class="bg_table02"><hr></td>
       	</tr>
       	
		</s:form>
	   </s:iterator>
	       
	       <s:form action="harvestMeansSearch" theme="simple"> 
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="harvestMeansSearch" /></td>				
				</tr>
			</TABLE>
		  </s:form>
	</table>
</body>
</html>