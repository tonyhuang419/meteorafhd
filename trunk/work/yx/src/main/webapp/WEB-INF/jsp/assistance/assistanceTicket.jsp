<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>外协付款确认</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<script type="text/javascript">
function aaa(){
		var inputCount = <s:property value="info.result.size()" />;
		var checkArr=document.getElementsByName("ids");
		var j = 0;
		for(var n=0; n<checkArr.length; n++){
			if(checkArr[n].checked){
				j++;
			}
		}	    
		if(j == 0){
			alert("请选择要关联的发票！");
		}else{
			document.forms(0).method.value = "doTicket";
			document.forms(0).action="../assistance/assistance.action?id=<s:property value="#parameters.id"/>";
			document.forms(0).submit();
		}
}
</script>
</head>
<body>
<s:form action="assistance" theme="simple">
	<s:hidden name="method" value="doTicket"></s:hidden>
	<s:hidden name="id" value="#parameters.id"></s:hidden>
	<s:hidden name="thisApplyPayAmount"></s:hidden>
	<s:hidden name="thisRelationPrepAmount"></s:hidden>
	<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="30%" scrolling="no"></iframe>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<tr>
	<td align="center"> 
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="4" align="left" >当前页面：外协管理->关联发票</td>
			</tr>
						<tr>
            	<td colspan="5" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
	  </table>
	  <table align="center" border=1 cellpadding="0" cellspacing=0 width="100%" id="checkInfo" bordercolor="#808080" style=" border-collapse: collapse;">
<tr align="center">
	      <td nowrap="nowrap" class="bg_table01">选择</td>
          <td nowrap="nowrap" class="bg_table01">供应商名称</td>
          <td nowrap="nowrap" class="bg_table01">发票号</td>
          <td nowrap="nowrap" class="bg_table01">发票类型</td>
          <td nowrap="nowrap" class="bg_table01">发票金额</td>
          <td nowrap="nowrap" class="bg_table01">开票时间</td>
          <td nowrap="nowrap" class="bg_table01">发票余额</td>
          
</tr>
<s:iterator id="result" value="info.result" status="status">
	    <tr align="center">
          <td>
        
          <input type="checkbox" name="tc[<s:property value="#status.index" />].id"
							value="<s:property value="#result[0].id"/>" id="ids" 
							<s:if test="#result[0].doneMoney != null && #result[0].doneMoney <= 0">
								disabled="true"
							</s:if>
							<s:elseif test = "assistanceTicketMap.containsKey(#result[0].id)" >
								disabled="true"
							</s:elseif>
							  <s:elseif test="isPrePay==1">  
									<s:if test ="#result[0].type!=4">
										disabled="true"
									</s:if>
							</s:elseif>
							<s:elseif test = "isPrePay==0">
									<s:if test ="#result[0].type==4">
									disabled="true"
									</s:if>
						</s:elseif>
					
						
							/> </td>
						<td align="left"><s:property value="#result[1]" /></td>
						<td align="left"><s:property value="#result[0].num" /></td>
						<td align="left"><s:property value="typemanageservice.getYXTypeManage(1004,#result[0].type).typeName"/></td>
						<td align="right"><s:property value="#result[0].money" /></td>
						<td align="center"><s:property value="#result[0].ticket_Time" /></td>
						<td align="right"><s:if test = "#result[0].doneMoney == null"><s:property value="#result[0].money" /></s:if>
						<s:else><s:property value="#result[0].doneMoney" /></s:else>
						</td>
						
						<td style="display: none;"><s:hidden name="tc[%{#status.index}].ticketId" value="%{#result[0].id}"></s:hidden></td>
	    </tr>
</s:iterator>	 
<s:hidden name="ticketId" value="#result[0].id"></s:hidden>   
	  </table>
<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>    </td>
  </tr>
  <tr>
  <td class="bg_table02"> <div align="center">
    <input type="button" name="SearchBtn" value="确认关联" class="button01" onClick="javascript:aaa();">　
    <input type="button" name="SearchBtn" value="关    闭" class="button01" onClick="javascript:window.close();">　
  </div></td>
  </tr>
</table>
</s:form>
<p>&nbsp;</p>
</body>
</html>
