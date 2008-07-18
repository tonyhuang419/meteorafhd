<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>签收管理</title>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}

.STYLE3 {
	color: #000000
}
-->
</style>
</head>
<body>
<s:form action="">
	<div align="left">
	<p class="STYLE3">当前页面：开票管理 -> 签收管理</p>

	</div>
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
					<td colspan="2" align="right" class="bg_table01" height="3"></td>
				</tr>
				<tr class="bg_table02">
					<td align="right" class="bg_table03" STYLE1>
					<div align="center"><input type="button" name="SearchBtn2"
						value="确认签收" class="button01" onClick="aaa();"> <input
						type="button" name="SearchBtn3" value="取消签收" class="button01"
						onClick="ccc();"> <input type="button"
						name="SearchBtn" value="打印签收单" class="button01"
						onClick="printList();"></div>
					</td>
				</tr>
			</table>

			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%" id="signManage">
				<tr align="center">
					<td width="6%" class="bg_table01">选择</td>
					<td width="11%" class="bg_table01">申请编号</td>
					<td width="11%" class="bg_table01">客户名称</td>
					<td width="11%" class="bg_table01">申请日期</td>
					<td width="17%" class="bg_table01">开票类型</td>
					<td width="15%" class="bg_table01">发票号</td>
					<td width="17%" class="bg_table01">开票金额</td>
					<td width="12%" class="bg_table01">开票日期</td>
				</tr>
				<s:iterator id="result" value="info.result" status="infoState">
				
						<tr align="center">
							<td><input type="checkbox" name="ids" id="ids" 
							value="<s:property value="#result[0].billApplyId"/>"/></td>
							<td><s:property value="#result[0].billApplyNum" /></td>
							<td><s:property value="#result[1]" /></td>
							<td><s:property value="#result[0].applyId" /></td>
							<td><s:property
								value="typeManageService.getYXTypeManage(1004,#result[0].billType).typeName" />
							</td>
					        <td></td>
							<td></td>
							<td></td>
							</tr>
			


					<s:iterator value="invoiceList" status="invoiceState">
						<!--           开票申请表id和开票明细表id想同的情况下进行循环输出某个申请表的明细信息-->

						   <s:if test="#result[0].billApplyId==applyInvoiceId">
							<tr align="center">
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><s:property value="invoiceNo" /></td>
								<td><s:property value="invoiceAmount" /></td>
								<td><s:date name="invoiceDate" format="yyyy-MM-dd" /></td>
							</tr>
							</s:if>
					
					</s:iterator>
					<tr>
						<td class="bg_white" colspan="8">
						<hr>
					</tr>
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
</s:form>
</body>
</html>

<script language="javascript">
//确认通过
function aaa() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var act="pass";
    var flag=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          flag++;
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(flag>0)
    {
		location.href="../billtoReceipt/signManage.action?method=verifyState&action="+act+"&bId="+checkStr.substring(1); 
	}
	if(flag==0)
	{
		alert("您还没有选择一项进行签收!")
	}
	
} 
//确认退回
function ccc() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var falg=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(falg>0){
		location.href="../billtoReceipt/signManage.action?method=verifyState&bId="+checkStr.substring(1); 
	}
	if(falg==0)
	{
		alert("您还没有选择一项进行取消签收!");
	}
} 

function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
 function printList()
 {
 	var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var flag=false;
    if(checkArr!=null&&checkArr.length>0)
	{
     for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          checkStr=checkArr[i].value;
          flag=true;
          break;
        }
     }
    }
    if(!flag)
    {
    	alert("请选择您要打印的申请单!");
    	return;

    }    
	var url="billAndInvoicePDF.action?method=BillAndInvoice&paramId="+checkStr;
	openUrl(url);
    }    
	var sum=0;
		if(checkArr!=null&&checkArr.length>0)
		{
			for(var k=0;k<checkArr.length;k++){
				if(checkArr[k].checked)
				{
					sum++;
				}
						
			}
		}
		if(sum>1)
		{
			alert("只能选择一个打印！");
			return;
		}
		openUrl(url);
>>>>>>> .r3118
 }

</script>

