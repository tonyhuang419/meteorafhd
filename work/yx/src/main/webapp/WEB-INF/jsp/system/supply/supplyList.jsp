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

location.href="../system/supply.action"; 
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
         location.href="../system/supply.action?method=enterUpdate&idsss="+checkStr.substring(1);
       
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
   location.href="../system/supply.action?method=del&ids="+checkStr.substring(1);
  }    
  } 
   }  
 
</script>

</head>
<body leftmargin="0">
<s:form action="supplyQuery">
		<div align="left" style="color: #000000">
			当前页面:基础管理->供应商管理
		</div>

			<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">	
			<tr>
            	<td  align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="3"></td>
       	  </tr>		
			<tr class="bg_table03">
					<td colspan="6" class="bg_table03">
					<div align="center"><input
						type="button" name="SearchBtn2" value="新 增" class="button01"
						onClick="javascript:aaa()"> <input type="button" name="SearchBtn2"
						value="修 改" class="button01" onClick="javascript:bbb()" > <input
						type="button" name="SearchBtn2" value="删 除" class="button01"
						 onClick="delChose()"
						> </div>
					</td>
				</tr>
			</table>
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
				<td width="5%" class="bg_table01" align="center" nowrap="true">选择</td>
				<td width="15%" class="bg_table01" align="center" nowrap="true">供应商名称</td>
				<td width="15%" class="bg_table01" align="center" nowrap="true">供应商类别</td>
				<td width="15%" class="bg_table01" align="center" nowrap="true">开票名称</td>
				<td width="15%" class="bg_table01" align="center" nowrap="true">开户银行</td>
				<td width="15%" class="bg_table01" align="center" nowrap="true">开户账号</td>
				<td width="15%" class="bg_table01" align="center" nowrap="true">税号</td>
			
				</tr>

				<s:iterator value="info.result">
				    	<tr align="center"  onMouseOver="this.bgColor='#BBBBFF'; " onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids"
							value="<s:property value="supplierid"/>" /></td>
						<td align="left"><s:property value="supplierName" /></td>
						<td align="left"><s:property value="typeManageService.getYXTypeManage(1013L,supplierType).typeName" /></td>
						<td align="left"><s:property value="nameOfInovice" /></td>
						<td align="left"><s:property value="billBank" /></td>
						<td align="left"><s:property value="billAccount" /></td>
						<td align="left"><s:property value="dutyParagraph" /></td>
						
						
					</tr>
				</s:iterator>
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			
</s:form>
</body>
</html>
