<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>

<link href="./css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">

function aaa() 
{ 

location.href="../system/clientLinkMan.action"; 
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
         location.href="../system/clientLinkMan.action?method=enterUpdate&idsss="+checkStr.substring(1);
       
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
  location.href="../system/clientLinkMan.action?method=del&ids="+checkStr.substring(1);
  }   
  } 
   }  
   function queryByClient(){
   	 document.forms(0).action ="/yx/system/clientLinkManQuery.action";
  	 document.forms(0).submit();
   }
</script>

</head>
<body>
<s:form action="clientLinkManQuery" theme="simple">
<s:hidden name="resetCondition" value="true"/>
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				
				  <tr><td height="3" align="left" >当前页面:基础管理->联系人管理</td></tr>
			<tr class="bg_table03">
             <td align="left" nowrap class="bg_table03"><align="right">所属客户： <s:textfield name="clientName" size="15"></s:textfield> <input class="button01" onclick="queryByClient()" type="button" name="Input" value="查  询"  /></td>
   
					<td colspan="6" class="bg_table03">
					
					<div align="left">
					 
					<input
					
						type="button" name="SearchBtn2" value="　新 增　" class="button01"
						onClick="javascript:aaa()"><input type="button" name="SearchBtn2"
						value=" 修 改" class="button01" onClick="javascript:bbb()" ><input
						type="button" name="SearchBtn2" value="　删 除　" class="button01"
						 onClick="delChose()"
						> </div>
					</td>
				</tr>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01">联系人名称</td>
					<td class="bg_table01">所属客户</td>
					
					<td class="bg_table01">手机</td>
					<td class="bg_table01">电话</td>
					<td class="bg_table01">联系人性质</td>
					<td class="bg_table01">联系地址</td>
				    <td class="bg_table01">最后修改日期</td>
					<td class="bg_table01">最后修改人</td>
				</tr>

				<s:iterator value="info.result" id="linkman">
					<tr align="center">
						<td><input type="checkbox" name="ids"
						value="<s:property value="#linkman[0].id"/>" />
						<td><s:property value="#linkman[0].name" /></td>
						<td><s:property value="#linkman[1].name"/></td> 
						<td><s:property value="#linkman[0].callPhone" /></td>
						<td><s:property value="#linkman[0].phone" /></td>
						<td><s:property value="typeManageService.getYXTypeManage(1003L,natureId).typeName"/></td> 
					    <td><s:property value="#linkman[0].address" /></td>
						<td><s:property value="#linkman[0].updateBy" /></td>
						<td><s:property value="#linkman[2].name" /></td>
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
