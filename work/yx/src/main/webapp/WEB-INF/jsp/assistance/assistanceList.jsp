<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<style>
</style>
<title>外协合同确认</title>
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
function aaa() 
{ 
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    var asContractId = -1;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             asContractId=checkArr[i].value;
        }
    }
   if(j==0){
        alert("您还没有选择确认通过的外协合同！");
   }else if(j == 1){
   	var flag = window.confirm("您确定要通过选中的外协合同吗？");
	   	if(flag){
	   		location.href="../assistance/assistance.action?method=enterConfirmPass&asContractId="+asContractId; 
	   	}
   }else{
   		alert("外协合同只能单个确认！");
   }
}

function confirmCancel() 
{ 
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
   if(j==0){
        alert("您还没有选择确认退回的外协合同！");
   }else if(j > 1){
   		alert("请选择一个合同退回！");
   }else{
   		//location.href="../assistance/assistance.action?method=back&stateId="+checkStr.substring(1); 
   		openWin('assistance.action?method=showBack&stateId='+checkStr.substring(1),400,300);
   }
}

</script>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<style type="text/css">
<!--
-->
</style>
</head>
<body>
<s:form theme="simple" action="affirmAssistanceContract" id="outsourceContractSure">
<s:hidden name="method" id="method" value="query"/>
	<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
				<tr> 
					<td align="left" >当前页面:外协管理->外协合同确认</td>
				</tr>
				<tr><td>
				<s:iterator value="messages" id="mes">
	  				<div align="left"><font color="red"><s:property value="#mes"/></font><br></div>
				</s:iterator></td></tr>
				<tr>
	            	<td colspan="2" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="3"></td>
	          	</tr>
				    <tr class="bg_table03">
				      <td width="73%"  align="right"><div align="center">
				        <input type="button" class="button02" onClick="aaa();" value=" 通  过 ">
				        <input type="button" class="button02" onClick="confirmCancel()" value=" 退  回 ">
			          </div></td>
			          <td width="27%" align="left"><div align="center">
			          	<input type="button" class="button02" onClick="cancelSure();" value="取消确认">
			          </td>
	        </tr>
	</table>
	<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" id="checkInfo" bordercolor="#808080" style=" border-collapse: collapse;">
	<tr align="center" >
	      <td nowrap class="bg_table01">&nbsp;</td>
	      <td nowrap  class="bg_table01">销售员</td>
		  <td nowrap  class="bg_table01">外协合同名称</td>
          <td nowrap class="bg_table01">合同号</td> 
          <td nowrap class="bg_table01">项目号</td>
          <td nowrap class="bg_table01">外协供应商</td>
          <td nowrap class="bg_table01">签订日期</td>
          <td nowrap class="bg_table01">预计结束日期</td>
          <td nowrap class="bg_table01">合同金额</td>
          <td nowrap class="bg_table01">合同状态</td>
        </tr>
        <s:iterator id="result" value="info.result">
	    <tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
			<td><input type="checkbox" name="ids"
				value="<s:property value="#result[0].id"/>" /></td>
			<td align="left" onclick="showDetails('<s:property value="#result[0].id"/>');"><s:property value="#result[3]" /></td>
			<td align="left" onclick="showDetails('<s:property value="#result[0].id"/>');"><s:property value="#result[0].assistanceName" /></td>
			<td nowrap align="left" onclick="showDetails('<s:property value="#result[0].id"/>');"><s:property value="#result[2].conId"/></td>	
			<td align="left" onclick="showDetails('<s:property value="#result[0].id"/>');"><s:property value="#result[0].mainProjectId"/></td>	
			<td align="left" onclick="showDetails('<s:property value="#result[0].id"/>');"><s:property value="#result[1]" /></td>
			<td nowrap onclick="showDetails('<s:property value="#result[0].id"/>');"><s:property value="#result[0].contractDate" /></td>
			<td nowrap onclick="showDetails('<s:property value="#result[0].id"/>');"><s:property value="#result[0].endDate" /></td>
			<td nowrap align="right" onclick="showDetails('<s:property value="#result[0].id"/>');"><s:property value="#result[0].contractMoney" /></td>
			<td nowrap align="center" onclick="showDetails('<s:property value="#result[0].id"/>');">
				<s:if test="#result[0].assistanceContractType==1">待确认</s:if>
				<s:elseif test="#result[0].assistanceContractType==2">确认通过</s:elseif>
			</td>
		</tr>
        </s:iterator>
	  	</table>
		<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
		</TABLE>
</s:form>
</body>
<script type="text/javascript">
	<s:if test ="isSuccess =='1'">
		alert("外协合同确认通过！");
	</s:if>
	<s:if test ="isSuccess =='5'">
		alert("外协合同已退回！");
	</s:if>


function cancelSure(){
	var checkedBoxes = $$("input[name=ids][checked]");
	if(checkedBoxes.length == 0)
	{
		alert("请选择要取消确认的外协合同!");
		return;
	}
	else if(confirm("确定取消确认?")){
	var oForm  = $("outsourceContractSure");
	oForm.action = "affirmAssistanceContract.action";
	$("method").value = "cancelSure";
	oForm.submit();
	}
}
if('<s:property value="alertInfo"/>'!=""){
	   alert('<s:property value="alertInfo"/>');
}
function showDetails(assistanceId){
   openUrl('assistanceMLeftQuery.action?method=showDetail&assistanceId='+assistanceId);
}
	
</script>
</html>
