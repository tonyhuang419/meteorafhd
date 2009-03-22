<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>客户管理</title>

<link href="./css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">

function aaa() 
{ 
location.href="../system/client.action"; 
} 

function bbb(){
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
         location.href="../system/client.action?method=enterUpdate&idsss="+checkStr.substring(1);
       
      }
   if(j==0){
        alert("您还没有选择修改的对象！");
   }
   if(j>1){
    
     alert("不能选择多个修改对象！");
   }
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
     if(j==0){
        alert("您还没有选择删除的对象！");
        }
      if(j>0){
        if(window.confirm("确定要删除吗?")){
  location.href="../system/client.action?method=del&ids="+checkStr.substring(1);
  }
  } 
   }  
  function ccc(){
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
         location.href="../system/clientLinkMan.action?idsss="+checkStr.substring(1)+"&from=1";
      
      }
   if(j==0){
        location.href="../system/clientLinkMan.action?from=1";
   }
   if(j>1){
    
     alert("不能选择多个对象！");
   }
      } 
</script>

</head>
<body leftmargin="0">
<s:form action="clientQuery">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" 
		class="bg_white" align="center">
		<tr><td height="3" align="left" >当前页面:基础管理->客户管理</td></tr>
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
            	<td  align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="3"></td>
       	  </tr>
			<tr class="bg_table03">
         
					<td  class="bg_table03">
					<div align="center"><input
						type="button" name="SearchBtn2" value="　新 增　" class="button01"
						onClick="javascript:aaa()"> <input type="button" name="SearchBtn2"
						value=" 修 改" class="button01" onClick="javascript:bbb()" > <input
						type="button" name="SearchBtn2" value="　删 除　" class="button01"
						 onClick="delChose()"
						> <input
						type="button" name="SearchBtn2" value="新增联系人" class="button01"
						onClick="javascript:ccc()"></div>
					</td>
				</tr>
			</table>
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%"  bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td width="6%" class="bg_table01" align="center" nowrap="true">选择</td>
					<td width="18%" class="bg_table01" align="center" nowrap="true">客户全称</td>
					<td width="13%" class="bg_table01" align="center" nowrap="true">客户简称</td>
					<td width="13%" class="bg_table01" align="center" nowrap="true">ERP编号</td>
					<td width="11%" class="bg_table01" align="center" nowrap="true">客户性质</td>
					<td width="11%" class="bg_table01" align="center" nowrap="true">行业类型</td>
					<td width="13%" class="bg_table01" align="center" nowrap="true">客户开户银行</td>
					<td width="13%" class="bg_table01" align="center" nowrap="true">开户帐号</td>
				  	<td width="11%" class="bg_table01" align="center" nowrap="true">税号</td>

				  
				</tr>

				<s:iterator value="info.result">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
						onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids"
							value="<s:property value="id"/>" /></td>
						<td align="left"><s:property value="fullName" /></td>
						<td align="left"><s:property value="name" /></td>
						<td align="left"><s:property value="userCode" /></td> 				
						<td align="left"><s:property value="typeManageService.getYXTypeManage(1001L,clientNID).typeName"/></td>
						<td align="left"><s:property value="typeManageService.getYXTypeManage(1002L,businessID).typeName"/></td>
						<td align="left"><s:property value="billBank" /></td>
						<td align="left"><s:property value="account" /></td>
						<td align="left"><s:property value="taxNumber" /></td>					
							
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
