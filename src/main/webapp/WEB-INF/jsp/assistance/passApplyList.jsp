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
function aaa() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j = 0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
       	  j++
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(j == 0){
    	alert("您还没有选择确认提交的对象!");
    }else{
   	 	location.href="../assistance/passApply.action?method=pass&ids="+checkStr.substring(1); 
    }
}
function bbb() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j = 0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          j++;
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(j == 0){
    	alert("您还没有选择退回提交的对象!");
    }else{
    	location.href="../assistance/passApply.action?method=back&ids="+checkStr.substring(1);
    } 
}
</script>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
</head>
<body leftmargin="0">
<s:form theme="simple" action="">
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<tr>
	<td align="center"> 
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td align="left" >当前页面:外协管理->外协付款确认</td>
			</tr>
			<tr>
            	<td align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
			    <tr class="bg_table02">
			      <td align="right"><div align="center">
			        <input type="button" name="SearchBtn4" value="确认通过" class="button01" onClick="javascript:aaa();">
			        <input type="button" name="SearchBtn2" value="确认退回" class="button01" onClick="javascript:bbb();">
			      </div></td>
        </tr>
		</table>
	  <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="checkInfo">
		<tr align="center">
	      <td width="7%" class="bg_table01">&nbsp;</td>
          <td width="10%" class="bg_table01">申请序号</td>
          <td width="10%" class="bg_table01">外协合同号</td>
          <td class="bg_table01">外协合同名称</td>
	      <td width="23%" class="bg_table01">外协供应商</td>
          <td width="10%" class="bg_table01">销售人员</td>
          <td width="9%" class="bg_table01">申请日期</td>
          <td width="11%" class="bg_table01">申请金额</td>
        </tr>   
     <s:iterator value="info.result" id="apply">
	    <tr align="center"  onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
          <td>
            <input type="checkbox" name="ids" value="<s:property value="#apply[0].id"/>">
          </td>
	    	<td><s:property value="#apply[0].id"/></td>
	      	<td><s:property value="#apply[1]"/></td>
	      	<td><s:property value="#apply[2]"/></td>
         	<td><s:property value="#apply[3]"/></td>
          	<td><s:property value="#apply[3]"/></td>
         	<td><s:property value="apply[0].applyDate"/></td>
         	<td><s:property value="#apply[0].payNum"/></td>
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
<p>&nbsp;</p>
</body>
</html>
