<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>外协付款确认</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
function edit(){
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(j==1)
      {  
         var url="../assistance/assistanceTicket.action?method=enterUpdate&ids="+checkStr.substring(1);
         window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');
      }
   if(j==0){
        alert("您还没有选择修改的对象！");
   }
   if(j>1){
    
     alert("不能选择多个修改对象！");
   }
      }
function backEdit(){
	location.href("../assistance/ticketLeft.action?method=query");
}
function delChose(){
	var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
   if(j==0)
   {
        alert("您还没有选择删除的对象！");
   }else{
   		location.href="../assistance/assistanceTicket.action?method=del&ids="+checkStr.substring(1);
   }
  
}  
function relationContract(){
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(j==1)
      {  
         location.href="../assistance/assistanceTicket.action?method=enterRelationContract&ids="+checkStr.substring(1);
      }
   if(j==0){
        alert("您还没有选择关联的对象！");
   }
   if(j>1){
    
     alert("不能选择多个关联对象！");
   }
} 
function relationContractBack(){
	alert("已关联外协合同");
}
</script>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
</head>
<body leftmargin="0">
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<tr>
	<td align="center"> 
<s:form theme="simple" action="ticketLeft">
<s:hidden name="method" value="query"></s:hidden>
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="3" align="left" >当前页面:外协管理->外协发票管理</td>
			</tr>
			<tr>
            	<td colspan="3" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
			    <tr class="bg_table02">
	
			      <td align="left">
			        <div align="center">
			        	<input type="button" name="SearchBtn" value="新   增" class="button01" onClick="javascript:location.href('../assistance/assistanceTicket.action');" >
			          <input type="button" name="SearchBtn" value="修    改" class="button01" onClick="javascript:edit();" >
			           <input type="button" value="删    除" class="button02" onclick="javascript:delChose()">	
                             <input type="button" name="SearchBtn" value="关联外协合同" class="button01" onClick="javascript:relationContract();" >		     
	              </div></td>
        </tr>
	  </table>
	  <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="checkInfo">
<tr align="center">
	      <td width="7%" class="bg_table01">选择</td>
          <td width="16%" class="bg_table01">供应商名称</td>
          <td width="11%" class="bg_table01">发票号</td>
          <td width="11%" class="bg_table01">发票类型</td>
          <td width="14%" class="bg_table01">发票金额</td>
          <td width="11%" class="bg_table01">开票时间</td>
          <td width="30%" class="bg_table01">备注</td>
        </tr>
      <s:iterator value="info.result" id="ticket">
	    <tr align="center"  onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';" >
          <td><input type="checkbox" name="ids"  value="<s:property value="#ticket[0].id"/>"></td>
	      <td><s:property value="#ticket[1]"/></td>
	      <td><s:property value="#ticket[0].num"/></td>
	      <td><s:property value="#ticket[0].type"/></td>
	      <td><s:property value="#ticket[0].money"/></td>
	      <td><s:property value="#ticket[0].ticket_Time"/></td>
	      <td><s:property value="#ticket[0].remark"/></td>
        </tr>
      </s:iterator>  
	  </table>
<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>    </td>
  </tr>
</table>
</s:form>
<p>&nbsp;</p>
</body>
</html>