<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>

<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">

</script>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">

<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {
	background: lightblue;
	color: blue;
}
</style>
<title>申购采购管理</title>
<link href="./../css/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
</head>

<body leftmargin="0">
<s:form action="programEconomyVerifyQuery" target="content" theme="simple">
   <s:hidden name="a" id="a"/>
   
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
					<td align="left">当前页面：基础管理->客户管理</td>
				</tr>
				<tr>
					<td align="right" class="bg_table01" height="0.5"><img
						src="./../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr>
					<td align="right" class="bg_table03">
					<div align="center"><input type="button" value="确认通过"
						onClick="javascript:aaa()" class="button02" /> <input
						type="button" value="确认退回" onClick="javascript:ccc()"
						class="button02" /></div>
					</td>
				</tr>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%" id="checkInfo">
				<tr align="center">
					<td width="6%" class="bg_table01">选择</td>
					<td width="10%" class="bg_table01">工程编号</td>
					<td width="19%" class="bg_table01">项目名称</td>
					<td width="12%" class="bg_table01">设计委托进度</td>
					<td width="7%" class="bg_table01">投资匡算</td>
					<td width="15%" class="bg_table01">项目编号（甲方）</td>
					<td width="20%" class="bg_table01">客户项目单位</td>
					<td width="11%" class="bg_table01">销售员</td>

				</tr>
				<s:iterator id="result" value="info.result">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
						onMouseOut="this.bgColor='#FFFFFF';" >
						<td><input type="checkbox" name="ids"
							value="<s:property value="#result[0].id"/>" /></td>
						<td onclick="openUrl('programEconomy.action?method=projectInfo&pid=<s:property value="#result[0].id"/>')" >

						<s:property value="#result[0].projectNumber" />
						
						</td>
	                     <td onclick="openUrl('programEconomy.action?method=projectInfo&pid=<s:property value="#result[0].id"/>')" ><s:property value="#result[0].projectName" /></td>
						<td onclick="openUrl('programEconomy.action?method=projectInfo&pid=<s:property value="#result[0].id"/>')"><s:date name="#result[1].designSpeed"
							format="yyyy-MM-dd" /></td>
						<td onclick="openUrl('programEconomy.action?method=projectInfo&pid=<s:property value="#result[0].id"/>')"><s:property
							value="#result[0].investment" /></td>
						<td onclick="openUrl('programEconomy.action?method=projectInfo&pid=<s:property value="#result[0].id"/>')"><s:property
							value="#result[0].projectNumberJ" /></td>
						<td onclick="openUrl('programEconomy.action?method=projectInfo&pid=<s:property value="#result[0].id"/>')"><s:property
							value="#result[0].clientFactory" /></td>
						<td onclick="openUrl('programEconomy.action?method=projectInfo&pid=<s:property value="#result[0].id"/>')"><s:property value="#result[2]" /></td>

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
 var a=document.getElementById("a");
 if(a==3){
    alert("确认通过!");
 }
function aaa() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var act="pass";
    var k=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
        k=1;
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(k==0){alert("请选择复选框！");return false;}
    else{
           if(confirm("是否确定通过?"))
           {
			  location.href="../programEconomy/programEconomy.action?method=verifyState&action="+act+"&stateId="+checkStr.substring(1); 
           } 
           else
           {return false;}
         }
}
function ccc() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var k=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
        k=1;
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(k==0){return false;}
    else
    {
        if(confirm("是否确认退回?"))
        {
		  location.href="../programEconomy/programEconomy.action?method=verifyState&stateId="+checkStr.substring(1); 
	    }
	    else{return false;}
	}
} 

function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
 
</script>
