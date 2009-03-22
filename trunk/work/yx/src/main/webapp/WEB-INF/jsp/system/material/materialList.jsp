<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>应交材料管理</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>

<style type="text/css">
<!--
-->
</style>

<script type="text/javascript">
	//新增
	function addMaterial(){
		openWin("material.action", 500, 300);
	}
	// 修改
	function editMaterial(){
	   var checkArr=document.getElementsByName("mid");
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
         	openWin("/yx/system/material/material.action?method=updateMeterial&mids="+checkStr.substring(1), 500, 300);
         }
	   if(j==0){
	        alert("您还没有选择修改的对象！");
	   }
	   if(j>1){
   		 alert("不能选择多个修改对象！");
  			} 
   }
   // 删除
     function delMaterial(){
	   var flag=0;
	   var chck=document.getElementsByName("mid");
	   for(i=0;i<chck.length;i++){
	       if(chck[i].checked==true){
	           flag++;
	       }
	   }
	   if(flag>0){
	   	  if(confirm("确定要删除这些信息吗?")){
	   		location.href="/yx/system/material/material.action?method=deleteMater&="+$(materialQuery).toQueryString();
	   	  }
       }else{
           alert("您没有选择任何一项!");
       }
	}
</script>
<s:if test="stage == 1">
	<script language="javascript"> 
		alert("新增成功!");
	</script>
</s:if>
<s:elseif test="stage == 2">
	<script language="javascript"> 
		alert("修改成功!");
	</script>
</s:elseif>
<s:elseif test="stage == 3">
	<script language="javascript"> 
		alert("删除成功!");
	</script>
</s:elseif>
<body>
<div align="left" style="color:#000000">当前页面：基础管理—>应交材料管理 </div>

<s:form action="materialQuery" theme="simple"> 
		<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
		<tr>
				<td colspan="3" align="right" class="bg_table01" height="3"></td>	
			</tr>
			<tr align="center">
				<td  class="bg_table03" align="center">
				   <input type="button" name="add" value="新增" onclick="addMaterial()" class="button01" /> 
				   	<input type="button" name="update" value="修改" class="button01" onClick="editMaterial()">
				   <input type="button"	name="delete" value="删除" class="button01" onclick="delMaterial()" >
				</td>
			</tr>
		</table>
		<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center" >
					<td class="bg_table01" width="10%">选择</td>
					<td class="bg_table01">材料代码</td>
					<td class="bg_table01">材料名称</td>
					<td class="bg_table01">材料排序</td>
				</tr>
			<s:iterator value="info.result">
				<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
					<td><input type="checkbox" name="mid" value="<s:property value="id"/>" /></td>
					<td align="left"><s:property value="materialCode"/></td>
					<td align="left"><s:property value="materialName"/></td>
					<td align="left"><s:property value="sortOrder"/></td>
				</tr>
			</s:iterator>
		</table>
		<table cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
		</table>
</s:form>
</body>
</html>