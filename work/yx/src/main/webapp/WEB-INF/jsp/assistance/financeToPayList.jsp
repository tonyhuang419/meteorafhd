<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<style>
</style>
<title>支付财务交接</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<script language="javascript">
	function openUrl(applyInfoId){
		window.open('apply.action?method=detail&applyInfoId='+applyInfoId,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	var allDocumentArr = new Array();
	<s:iterator value="info.result" id="apply">
	allDocumentArr[allDocumentArr.length] = new Array("<s:property value="#apply[0].id"/>","<s:property value="#apply[0].payState"/>");
	</s:iterator>
	function checkPayInfoState(payState){
		var checkArr=document.getElementsByName("applyId");
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	       	  for(var j = 0; j < allDocumentArr.length ; j++){
	       	  	if(checkArr[i].value == allDocumentArr[j][0]){
	       	  		if(allDocumentArr[j][1]!=payState){
	       	  			return false;
	       	  		}
	       	  	}
	       	  }
	        }
	    }
	    return true;
	}
	function confirmOperator(){
	    var checkedBoxes = $$("input[name=applyId][checked]");
	    if(checkedBoxes.length == 0){
	    	alert("您还没有选择要确认处理的申请单!");
	    }else{
	   	 	var flag = checkPayInfoState("2");
	   	 	if(flag){
	    		if(confirm("确定要处理选择的申请单吗？")){
	    			document.forms[0].method.value="confirmOperator";
	    			location.href="../assistance/financeToPay.action?"+$("financeToPay").toQueryString();
	    		}
    		}else{
    			alert("您选择的付款申请在该状态下不能确认处理!");
    		}
	    } 
	}
	
	function cancelOperator(){
	    var checkedBoxes = $$("input[name=applyId][checked]");
	    if(checkedBoxes.length == 0){
	    	alert("您还没有选择要确认处理的申请单!");
	    }else{
	   	 	var flag = checkPayInfoState("4");
	   	 	if(flag){
	    		if(confirm("确定要取消处理选择的申请单吗？")){
	    			document.forms[0].method.value="cancelOperator";
	    			location.href="../assistance/financeToPay.action?"+$("financeToPay").toQueryString();
	    		}
    		}else{
    			alert("您选择的付款申请在该状态下不能取消处理!");
    		}
	    } 
	}
	//打印
	function printList(){
	 var checkArr=document.getElementsByName("applyId");
	    var checkStr="";
	    var flag=false;
	    if(checkArr!=null&&checkArr.length>0){
	    for(var i=0;i<checkArr.length;i++){
	    	if(checkArr[i].checked)
	    	{
	    		checkStr=checkArr[i].value;
	    		flag=true;
	    		break;
	    	}
	    }
	    }
	    if(!flag)
	    {
	    	alert("请选择要打印的申请单");
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
    	window.open("assistancePayForPDF.action?method=assistancePayFor&paramId="+checkStr,"","menubar=yes,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
    
	}
</script>
<script type="text/javascript">
	<s:if test="processResult.errorCode == 1">
		alert("已处理成功!")
	</s:if>
	<s:if test="processResult.errorCode == 2">
		alert("已取消处理!")
	</s:if>
</script>
</head>
<body leftmargin="0">
<s:form theme="simple" action="financeToPay">
<s:hidden name="method" value="showQueryList"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<tr>
	<td align="center"> 
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td align="left" >当前页面:外协管理->支付财务交接</td>
				<s:iterator id="suMessage" value="processResult.successMessages">
					<font color="green"><strong><s:property value="#suMessage"/></strong></font><br/>
				</s:iterator>
			</tr>
			<tr>
            	<td align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
			    <tr class="bg_table02">
			      <td align="right"><div align="center">
			        <input type="button" name="2" value=" 处  理 " class="button01" onClick="javascript:confirmOperator();">
			        <input type="button" name="2" value="取消处理" class="button01" onClick="javascript:cancelOperator();">
			        <input type="button" name="2" value="打印申请单" class="button01" onClick="printList()">
			        <s:if test="info.result.size() > 0 ">
						<input type="button" value=" 导  出 " onclick="exportInfo();" class="button01" />
					</s:if>
			      </div></td>
        </tr>
		</table>
	  <table align="center" border="1" cellpadding="1" cellspacing=1 width="100%" id="checkInfo" bordercolor="#808080" style=" border-collapse: collapse;">
		<tr align="center">
	      <td nowrap="nowrap" class="bg_table01">&nbsp;</td>
	      <td nowrap="nowrap" class="bg_table01">外协合同号</td>
          <td nowrap="nowrap" class="bg_table01">申请序号</td>
          <td nowrap="nowrap" class="bg_table01">外协合同名称</td>
	      <td nowrap="nowrap" class="bg_table01">外协供应商</td>
          <td nowrap="nowrap" class="bg_table01">申请日期</td>
          <td nowrap="nowrap" class="bg_table01">申请金额</td>
          <td nowrap="nowrap" class="bg_table01">销售员</td>
          <td nowrap="nowrap" class="bg_table01">是否预付</td>
          <td nowrap="nowrap" class="bg_table01">状  态</td>
        </tr>   
     <s:iterator value="info.result" id="apply">
	    <tr align="center"  onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
          <td>
            <input type="checkbox" name="applyId" value="<s:property value="#apply[0].id"/>">
          </td>
          	<td align="left" onclick="openUrl('<s:property value="#apply[0].id"/>')">
          		<s:property value="#apply[1]"/>
          	</td>
	    	<td align="left" onclick="openUrl('<s:property value="#apply[0].id"/>')"><s:property value="#apply[0].applyInfoCode"/></td>
	      	<td align="left" onclick="openUrl('<s:property value="#apply[0].id"/>')"><s:property value="#apply[2]"/></td>
         	<td align="left" onclick="openUrl('<s:property value="#apply[0].id"/>')"><s:property value="#apply[3]"/></td>
         	<td onclick="openUrl('<s:property value="#apply[0].id"/>')"><s:property value="#apply[0].applyDate"/></td>
         	<td align="right" onclick="openUrl('<s:property value="#apply[0].id"/>')"><s:property value="#apply[0].payNum"/></td>
       		<td align="left" onclick="openUrl('<s:property value="#apply[0].id"/>')"><s:property value="#apply[4].name"/></td>
       		<td align="left" onclick="openUrl('<s:property value="#apply[0].id"/>')">
       			<s:if test = "#apply[0].applyPay">
       			预付款
       			</s:if>
       			<s:else	>
       			正常付款
       			</s:else>  	
       			</td>
       		<td align="left" onclick="openUrl('<s:property value="#apply[0].id"/>')">
       			<s:if test = "#apply[0].payState==2">
       				确认通过
       			</s:if>
       			<s:elseif test="#apply[0].payState==4">
       				确认处理
       			</s:elseif>
       		</td>
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
<s:form id="export">
	<s:hidden name="method" value="showQueryList"></s:hidden>
<s:hidden name="exportX" value="1" />
</s:form>
<script type="text/javascript">
function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
}
</script>
</body>
</html>