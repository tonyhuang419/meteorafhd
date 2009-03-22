<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/jsp/meta.jsp"%>
		<link href="./css/style.css" rel="stylesheet" type="text/css">
		<title>签收管理</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
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
	<body leftmargin="0">
<s:form action="signManageQuery" target="rightFrame" theme="simple">
   <s:hidden name="method" value="queryList" ></s:hidden>

			<div align="left">
				<p class="STYLE3">
					当前页面：开票管理 -> 签收管理
				</p>
			</div>

			<table align="center" border=0 cellpadding=1 cellspacing=1	width="100%">
				<tr>
					<td colspan="2" align="right" class="bg_table01" height="3"></td>
				</tr>
				<tr class="bg_table02">
					<td align="right" class="bg_table03" STYLE1>
						<div align="center">
							<input type="button" name="SearchBtn2" value="确认签收"
								class="button01" onClick="aaa();">
							<input type="button" name="SearchBtn3" value="取消签收"
								class="button01" onClick="ccc();">
							<input type="button" name="SearchBtn" value="打印签收单"
								class="button01" onClick="printList();">
						</div>
					</td>
				</tr>
			</table>

			<table align="center" border=1  width="100%"   bordercolor="#808080" style=" border-collapse: collapse;" id="signManage">
				<tr align="center"  class="bg_table01">
					<td>
						选择
					</td>
					<td>
						申请编号
					</td>
					<td>
						销售员
					</td>
					<td>
						合同名称
					</td>					
					<td>
						合同号
					</td>

				    <td>
						项目号
					</td>
					<td>
						客户名称
					</td>
					<td>
						开票类型
					</td>
					<td>
						签收状态
					</td>
					<td>
						发票号
					</td>
					<td>
						开票金额
					</td>
					<td>
						开票日期
					</td>
				</tr>
				<s:set name="applyBillId" value=""/>
				<s:iterator id="result" value="info.result" status="infoState">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF';" onMouseOut="this.bgColor='#FFFFFF';">
						<td align="center">
						<s:if test="#applyBillId!=#result[0]">
							<input type="checkbox" name="billApplyId"
								value="<s:property value="#result[0].longValue()"/>/<s:property value="#result[5].longValue()"/>" />
						</s:if>
						</td>
						<td align="left" >
						<s:if test="#applyBillId!=#result[0]">
							<s:property value="#result[1]" />
                        </s:if> 
						</td>
						<td align="left" >
						<s:if test="#applyBillId!=#result[0]">
						   <s:property value="#result[11]"/>
						</s:if> 
					    </td>
						<td align="left" >
						<s:if test="#applyBillId!=#result[0]">
							<s:property value="#result[12]"/>
						</s:if>
						</td>					    
					    
						<td align="left" >
						<s:if test="#applyBillId!=#result[0]">
							<s:property value="#result[9]"/>
						</s:if>
						</td>

						<td align="center" >
						<s:if test="#applyBillId!=#result[0]">
							<s:property value="getItemNoByApplyBillId(#result[0].longValue())" escape="false"/>
					    </s:if>
						</td>
						<td align="left" >
						<s:if test="#applyBillId!=#result[0]">
							<s:property value="#result[2]" />
					    </s:if>
						</td>
						<td align="left" >
						<s:if test="#applyBillId!=#result[0]">
							<s:property value="#result[4]" />
						</s:if>
						</td>
						<td  align="center" >
						<s:if test="#applyBillId!=#result[0]">
							<s:if test="#result[5] == 1" >
							  已签收
							</s:if>
							<s:else>
							  未签收
							</s:else>
						</s:if>
						</td>
						<s:set name="applyBillId" value="#result[0]" />
						<!--   利用set值循环取invoiceList的第一条数据放在第一个tr里面,其他换行循环在下面    	-->
	                   <td  align="left" ><s:property value="#result[6]" /> </td>
	                   <td  align="right" ><s:property value="#result[7]" /> </td>
	                   <td  align="center" ><s:property value="#result[8]" /> </td>
				</s:iterator>
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04">
						<baozi:pages value="info" beanName="info" formName="forms(0)" />
					</td>
				</tr>
			</TABLE>

		</s:form>
	</body>
</html>

<script language="javascript">
//确认通过
function aaa() 
{ 
  var checkArr=document.getElementsByName("billApplyId");
    var checkStr="";
    var act="pass";
    var flag=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
           flag++;
          var tempVar=checkArr[i].value.split("/");
          checkStr=checkStr+","+tempVar[0];
          if(tempVar[1]=="1")
          {
          	alert("您选择的申请单有已经确认签收的!");
            return;
          }
         
        }
    }
    if(flag>0)
    {
    	if(confirm("确认要签收吗？")){
    		document.forms(0).method.value = "verifyState";
			location.href="../billtoReceipt/signManage.action?Cancel=true&"+$(signManageQuery).toQueryString();
		}
	}else
	{
		alert("您还没有选择一项进行签收!")
	}
	
} 
//取消签收
function ccc() 
{ 
  var checkArr=document.getElementsByName("billApplyId");
    var checkStr="";
    var falg=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
       	  falg++;
          var tempVar=checkArr[i].value.split("/");
          checkStr=checkStr+","+tempVar[0];
           if(tempVar[1]=="0")
          {
          	alert("您选择的申请单有未确认签收的，不用取消!");
          	  return;
          }
          
        }
    }
    if(falg>0){
    	if(confirm("确认要取消签收吗？")){
    		document.forms(0).method.value = "verifyState";
    		location.href="../billtoReceipt/signManage.action?Cancel=false&"+$(signManageQuery).toQueryString();
		}
	}
	if(falg==0)
	{
		alert("您还没有选择一项进行取消签收");
	}
} 

function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
 function printList()
 {
 	var checkArr=document.getElementsByName("billApplyId");
    var checkStr="";
    var flag=false;
    if(checkArr!=null&&checkArr.length>0)
	{
     for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
         var tempVar=checkArr[i].value.split("/");
          checkStr=tempVar[0];
          flag=true;
          break;
        }
     }
    }
    if(!flag)
    {
    	alert("请选择您要打印的申请单！");
    	return;
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
		
		if(checkPrint("signManage.action","checkPrint",checkStr)){
			var url="billAndInvoicePDF.action?method=BillAndInvoice&paramId="+checkStr;
			openUrl(url);
		}
 }

function checkPrint(url,method,eid){
	var flag=false;
	  	var myRequest = new Request({url:url,async:false,method:'get'});
  		myRequest.addEvent("onSuccess",function(responseText, responseXML){
			if(responseText =='false'){
				alert("该订单不能打印！");
				flag = false;
			}else{
				flag = true;
			}
   	});
	myRequest.send("method="+method+"&expId="+eid+"&randomNum="+Math.random());
	return flag;
}
<s:if  test="returnSign!=null&&returnSign==1">
	alert("操作成功");
</s:if>
</script>

