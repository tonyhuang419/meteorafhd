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
<s:form action="programEconomyManageQuery">
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
				<tr class="bg_table03">

					<td colspan="6" class="bg_table03">
					<div align="center"><input type="button" name="SearchBtn2"
						value="　确认提交　" class="button01" onClick="javascript:aaa();"><input
						type="button" name="SearchBtn2" value=" 修 改" class="button01"
						onClick="javascript:bbb()"> <input type="button"
						name="SearchBtn2" value="　删 除　" class="button01"
						onClick="javascript:delChose();"></div>
					</td>
				</tr>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%" id="checkInfo">
				<tr align="center" >
					<td  width="6%" class="bg_table01">选择</td>
					<td width="10%" class="bg_table01">工程编号</td>
					<td width="9%" class="bg_table01">项目名称</td>
					<td nowrap width="12%" class="bg_table01">设计委托进度</td>
					<td width="17%" class="bg_table01">销售员</td>
					<td width="15%" class="bg_table01">执行阶段</td>
					<td width="10%" class="bg_table01">申请状态</td>
					<td nowrap  width="11%" class="bg_table01">BPMS报送标志</td>
					<td  nowrap width="10%" class="bg_table01">BPMS报送时间</td>
				</tr>
				<s:iterator id="result" value="info.result">
					<tr align="center"  onMouseOver="this.bgColor='#BBBBFF'; " onMouseOut="this.bgColor='#FFFFFF';">
						<td
					 ><input type="checkbox" name="ids"
							value="<s:property value="#result[0].id"/>" /></td>
						<td onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" >

						<s:property value="#result[0].projectNumber" /></td>
						<td nowrap onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					 ><s:property value="#result[0].projectName" /></td>
						<td onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					  ><s:date name="#result[1].designSpeed"
							format="yyyy-MM-dd" /></td>
						<td onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					 ><s:property value="#result[2]" /></td>
						<td nowrap onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					  >
<!--                      <s:property value="#result[1].sectionName"/>-->
					  <s:property value="typeManageService.getYXTypeManage(1011,#result[1].sectionName).typeName" />
					  </td>
						

						<s:if test="#result[0].state==3">
							<td onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					  >确认通过</td>
						</s:if>
						<s:else>
							<td onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					 >确认退回</td>
						</s:else>
						


						<s:if test="#result[1].bpmsFlag==0">
							<td onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					  >已报送</td>
						</s:if>
						<s:else>
							<td onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					 >未报送</td>
						</s:else>
						<td onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					 ><s:date name="#result[1].bpmsEnterTime"
							format="yyyy-MM-dd" /></td>
							<input type="hidden" name="state<s:property value="#result[0].id"/>" id="state<s:property value="#result[0].id"/>" value="<s:property value="#result[0].state"/>"/>
					
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

function aaa() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var act="submit";
    var k=0;
  
    var name="";
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
         k=1;
          var idv="state"+checkArr[i].value;
          var st=document.getElementById(idv).value;
          if(st==4){
              checkStr=checkStr+","+checkArr[i].value;
          }
       
        }
    }
   if(k==0)
   {alert("请选择一项！"); return false;}
   else
   {
      if(confirm("是否确定提交？"))
	   {
         location.href="../programEconomy/programEconomy.action?method=verifyState3&action="+act+"&stateId="+checkStr.substring(1); 
      }
      else{return false;}
   }  
} 
function bbb() 
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
    if(k==0)
   {alert("请选择一项！"); return false;}
   else
   {
	    if(confirm("是否确定修改？"))
	   {
			location.href="../programEconomy/programEconomy.action?method=economyEdit&editId="+checkStr.substring(1); 
		} 
		else{return false;}
	}
}
function ccc() 
{ 
  var k=0;
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
        k=1;
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(k==0)
     {alert("请选择一项！"); return false;}
   else
   {
		location.href="../purchase/purchase.action?method=verifyState&stateId="+checkStr.substring(1); 
	} 

}

 function delChose(){
   var k=0;
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
        k=1;
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(k==0)
   {alert("请选择一项！"); return false;}
   else
   {
        if(confirm("是否确定删除？"))
         {
  			location.href="../programEconomy/programEconomy.action?method=delEconomy&ids="+checkStr.substring(1);
  		 }
         else{return false;}
    }
}
  function openUrl(url){
    	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
</script>
