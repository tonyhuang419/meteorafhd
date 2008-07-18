<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>外协合同确认</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
function aaa() 
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
        alert("您还没有选择确认通过的对象！");
   }else{
   		location.href="../assistance/assistance.action?method=verifyState2&stateId="+checkStr.substring(1); 
   }
}

function bbb() 
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
        alert("您还没有选择确认退回的对象！");
   }else{
   		location.href="../assistance/assistance.action?method=back&stateId="+checkStr.substring(1); 
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
<s:form theme="simple" action="">
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<tr>
	<td align="center"> 
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td align="left" >当前页面:外协管理->外协合同确认</td>
			</tr>
			<tr>
            	<td align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
			    <tr class="bg_table02">
			      <td align="right"><div align="center">
			        <input type="button" class="button02" onClick="javaScript:aaa()" value="确认通过">
			        <input type="button" class="button02" onClick="javaScript:bbb()" value="确认退回">
		          </div></td>
        </tr>
		</table>
	  <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="checkInfo">
<tr align="center">
	      <td width="5%" class="bg_table01">&nbsp;</td>
		        <td width="8%" nowrap class="bg_table01">外协合同号</td>
          <td width="10%" class="bg_table01">合同号</td> 
          <td width="8%" class="bg_table01">项目号</td>
	      <td width="9%" class="bg_table01">项目名</td>
          <td width="18%" class="bg_table01">外协供应商</td>
          <td width="6%" class="bg_table01">签订日期</td>
          <td width="8%" nowrap class="bg_table01">预计结束日期</td>
          <td width="8%" class="bg_table01">合同金额</td>
        </tr>
        <s:iterator id="result" value="list">
	    <tr align="center">
						<td><input type="checkbox" name="ids"
							value="<s:property value="#result[0].id"/>" /></td>
						<td><a href="javascript:openUrl('assistanceQuery.action?method=info&id=<s:property value="#result[0].id"/>')"><s:property
							value="#result[0].id" /></a></td>
						<td><s:property value="#result[2].conId"/></td>	
						<td><s:property value="#result[2].partyAProId"/></td>	
						<td><s:property value="#result[0].mainProjectName" /></td>
						<td><s:property value="#result[1]" /></td>
						<td><s:property value="#result[0].contractDate" /></td>
						<td><s:property value="#result[0].endDate" /></td>
						<td><s:property value="#result[0].contractMoney" /></td>
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
<p>&nbsp;</p>
</s:form>
</body>
</html>
