<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>收款管理明细</title>
<script type="text/javascript">
	function insRow()
	{
	  var txt1=document.getElementsByName('txt1').value;
	  var x=document.getElementById('tab1').insertRow(0);
	  var y=x.insertCell(1);
	  y.innerHTML="<td><s:property value='"+txt1+"' /></td>"
	}
		 
	 function deleteCurrentRow()         //刪除當前行
	 {
	     var currRowIndex=event.srcElement.parentNode.parentNode.rowIndex;
	 	 document.all.family.deleteRow(currRowIndex);//table10--表格id
	 }	
	 function updateAmount()
	 {
		window.open('../harvestMeansManager/harvestMeansManager.action?method=enterUpdate','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');					
	        
	 }
	 function addevent(button){
		   var td=button.parentNode;
		   var tr=td.parentNode;
		   var tbody=tr.parentNode;
		   var a=tbody.getAttribute("id");
		   var info=document.getElementById("amount");
		   var money=document.getElementById("amountDate");
		   var tr=td.parentNode;
		   var parenttr=tr.parentNode;
		   
		   var newtr=document.createElement("tr");
		   var newtdn=document.createElement("td");
		   var newtdn1=document.createElement("td");
		   var newtdn2=document.createElement("td");
		   var newtdn3=document.createElement("td");
		   var newtdn4=document.createElement("td");
		   var newtd1=document.createElement("td");
		   var newtd2=document.createElement("td");
		   var newtd3=document.createElement("td");
		   
		   newtd1.innerHTML='<center>'+info.value+'</center>';
		   newtd2.innerHTML='<center>'+money.value+'</center>';
		   
		   var hidden=document.getElementById("hidden1");
		   var list=new Array;
		   var account=new Array;
		   
		   account[0]=info.value;
		   account[1]=money.value;
		   list[0]=account;
		   alert(list);
		   hidden.setAttribute("accountinfo",list);
		   var hi=hidden.getAttribute("accountinfo");
		   newtd3.innerHTML='<a href="">修改</a>/<a href="">删除</a>';
		   
		   newtr.appendChild(newtdn);
		   newtr.appendChild(newtdn1);
		   newtr.appendChild(newtdn2);
		   newtr.appendChild(newtdn3);
		   newtr.appendChild(newtdn4);
		   newtr.appendChild(newtd1);
		   newtr.appendChild(newtd2);
		   newtr.appendChild(newtd3);
		   tbody.insertBefore(newtr,tr);
		   alert(parenttr);
}
function time(img){
  var par=img.parentNode;
  var text=par.firstChild;
  ShowCalendar(text.id);
}
function setAddId(invoiceId,button)
{
	var hidden=document.getElementById("addid");
	hidden.setAttribute("value",invoiceId);
	var row = button.parentElement.parentElement;
	var arriveAmount = parseFloatNumber(row.cells(5).firstChild.value);

	var arriveType = row.cells(6).firstChild.value;
	var arriveDate = row.cells(7).firstChild.firstChild.value;
	
	if(isNaN(arriveAmount)){
		alert("请输入到款金额");

		return false;
	}
	
	if (arriveAmount<=0.0){
		alert("请输入不为零的到款金额");
		return false;
		}
	var arriveTotalAmount =  parseFloatNumber(row.cells(4).innerHTML);
	if(isNaN(arriveTotalAmount)){
		arriveTotalAmount = 0;
	}
	var arriveInvoiceAmount =  parseFloatNumber(row.cells(3).innerHTML);
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

<body>
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				
				  <tr><td height="3" align="left" >当前页面:收款管理->收款管理</td></tr>
		</table>
		
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%"  id="family" >
			
				<tr align="center">
					<td width="8%" class="bg_table01">发票/收据号</td>
					<td width="10%" class="bg_table01">客户名称</td>
					<td width="10%" class="bg_table01">合同号</td>
					<td width="11%" class="bg_table01">开票金额</td>
					<td width="10%" class="bg_table01">到款总金额</td>
					<td width="11%" class="bg_table01">到款金额</td>
					<td width="11%" class="bg_table01">到款方式</td>
					<td width="10%" class="bg_table01">到款日期</td>
					<td width="12%" class="bg_table01">操作</td>
				</tr>
		<s:iterator value="info.result" id="invoice" status="status"> 
		<s:form action="harvestMeansManager" theme="simple">	
			<s:hidden name="method" value="saveAmount" />
			<input type="hidden" name="ri.billSid" id="addid" value="<s:property value="#invoice[1].id"/>"/>
				<tr>					
					<td class="bg_table02"><s:property value="#invoice[1].invoiceNo" /></td>
					<td class="bg_table02"><s:property value="#invoice[2].name" /></td>
					<td class="bg_table02"><s:property value="#invoice[3]" /></td>
					<td class="bg_table02"><s:property value="#invoice[1].invoiceAmount" /></td>
					<td class="bg_table02"><s:property value="#invoice[1].receAmount" /></td>
					<td class="bg_table02"><input type="text" name="ri.amount" /></td>
  				   	<td class="bg_table02"><s:select name="ri.receType" list="receTypetrans" listKey="typeSmall"
				       listValue="typeName" required="true" emptyOption="true" headerValue=""> </s:select></td>
					<td class="bg_table02" td width="10%" ><div align="left">
					<input type="text" name="ri.amountTime" id="amountTime<s:property value="%{#status.index}"/>"  readonly="readonly"  onclick="ShowCalendar(this.id);"><img src="/yx/commons/images/calendar.gif" onClick="time(this);"  alt=""/>
			  	</div></td>
				 <td width="12%" class="bg_table02"><input class="button01" type="submit" value="添加" onclick="return setAddId(<s:property value="#invoice[1].id"/>,this);"/></td>	
				</tr>
						</s:form>
				<s:iterator id="reve" value="reveList.get(#status.index)">
					<tr>
				
	                    <td class="bg_table02"><s:property value="" /></td>
						<td class="bg_table02"><s:property value="" /></td>
						<td class="bg_table02"><s:property value="" /></td>
						<td class="bg_table02"><s:property value="" /></td>
						<td class="bg_table02"><s:property value="" /></td>
						<td width="11%" class="bg_table02"><s:property value="amount" /></td>
						<td width="10%" class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1017L,receType).typeName" /></td>
						<td width="10%" class="bg_table02"><s:date name="amountTime" format="yyyy-MM-dd" /></td>
					    <td class="bg_table02"><input type="submit" name="button" value=" 修 改" class="button01" onClick="bbb(<s:property value="#reve.id"/>);" > 
					        <input type="submit" name="SearchBtn2" value="　删 除　" class="button01" onClick="delect(<s:property value="#reve.id"/>);"></td>	
					</tr>
				</s:iterator>

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