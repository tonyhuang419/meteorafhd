<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>关联合同</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
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
        location.href("../assistance/assistanceTicket.action?method=relationContract&ids="+checkStr.substring(1)+"&ticketId=<s:property value="#parameters.ids"/>");
      }
   if(j==0){
        alert("您还没有选择关联的对象！");
   }
   if(j>1){
    
     alert("不能选择多个关联对象！");
   }
      }
</script>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<body>
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<s:form theme="simple" action="assistanceTicket">
	<s:hidden name="method" value="enterRelationContract"></s:hidden>
<tr>
	<td align="center"> 
	  <table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="4" align="left" >当前页面：外协管理->关联外协合同</td>
		</tr>

			    <tr >
                  <td width="17%" align="right" class="bg_table04">外协合同名称：</td>
                  <td width="24%" align="left" class="bg_table04"><s:textfield name="name"/></td>
                  <td width="21%" align="right" class="bg_table04">外协合同号：</td>
                  <td width="38%" align="left" class="bg_table04"><s:textfield name="id"/></td>
	    </tr>
		</table>
	  <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="checkInfo">	  
		<tr align="center">
   			    <td width="9%" class="bg_table01">&nbsp;</td>
		        <td width="13%" class="bg_table01">外协合同号</td>
          <td width="22%" class="bg_table01">外协合同名称</td>
                <td width="16%" class="bg_table01">外协供应商</td>
          <td width="16%" class="bg_table01">签订日期</td>
          <td width="15%" class="bg_table01">预计结束日期</td>
          <td width="14%" class="bg_table01">合同金额<s:property value="ticketId"/></td>
       	 </tr>
       	 <s:iterator value="info.result" id="ac">
			<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
			  <td>
			    <input type="checkbox" name="ids"  value="<s:property value="#ac[0].id"/>">
			  </td>
			  <td nowrap><DIV align="center"><s:property value="#ac[0].id"/></DIV></td>
			  <td><s:property value="#ac[0].assistanceName"/></td>
			  <td><s:property value="#ac[1]"/></td>
			  <td><s:property value="#ac[0].contractDate"/></td>
			  <td><div align="center"><s:property value="#ac[0].endDate"/></div></td>
			  <td><s:property value="#ac[0].contractMoney"/></td>
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
<table width="98%" border="0" align='center' class="bg_table04">
<tr> 
  <td><div align="center">
    <input type="submit" value="查    询" class="button02" >
    <input type="button" class="button02" value="确    认" onClick="javascript:relationContract()">
  </div></td>
</tr>
</s:form>
</table>
<p>&nbsp;</p>
</body>
</html>
