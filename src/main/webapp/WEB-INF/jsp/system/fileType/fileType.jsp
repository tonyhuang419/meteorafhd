<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link href="/src/main/webapp/commons/styles/style.css" rel="stylesheet" type="text/css"/>

<script language="javascript">
	function add() 
	{ 
		location.href="../system/fileType.action"; 
	} 
	function update(){
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
         location.href="../system/fileType.action?method=enterUpdate&idsss="+checkStr.substring(1);
       
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
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
  location.href="../system/fileType.action?method=del&ids="+checkStr.substring(1);
  }  
</script>
</head>
<body>
   <table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td align="center"> 
		<table align="center"  border=0    cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="4" align="center" class="txt_title01">文件类型表</td>
			</tr>
			
			<tr>
            	<td colspan="4" align="right" class="bg_table01"  height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
          	</tr>		
			
			<tr align="center">
			  <td colspan="4" class="bg_table03" align="center">
       				<input type="button"  name="addBtn" value=" 新   增" class="button01" onclick="javascript:add()">
					<input type="button" name="updateBtn" value=" 修   改" onclick="javascript:update()" class="button01" />
					<input type="button" name="delete"
						value="　删 除　" class="button01" onClick="delChose()">
					</td>
			</tr>
		</table>
		<table align="center" border=0  cellpadding="1" cellspacing=1 width="100%">
    		<tr align="center">
    			<td class="bg_table01">选择</td>
      			<td class="bg_table01">文件类型代码</td>
				<td class="bg_table01">文件类型名称</td>
      			<td class="bg_table01">最后修改时间</td>
				<td class="bg_table01">最后修改人</td>				
    		</tr>
    		<s:iterator value="info.result">
			<tr align="center">
				<td><input type="checkbox" name="ids" value="<s:property value="id"/>" />
				<td><s:property value="id" /></td>
				<td><s:property value="fileTypeName" /></td>
				<td><s:property value="lastupdateTime" /></td>
				<td><s:property value="lastupdateId" /></td>
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
</body>
</html>