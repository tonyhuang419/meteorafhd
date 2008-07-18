<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>售前合同管理</title>
<script language="javascript">
	function add() 
	{ 
		location.href="../sellbefore/contractBeforeSellQuery.action"; 
	} 
	function addProgram(){
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
              location.href="../sellbefore/addProgramEconomyQuery.action?&ids="+checkStr.substring(1);
         }
		   if(j==0){
		        alert("您还没有选择修改的对象！");
		   }
		   if(j>1){
    
    		 alert("不能选择多个修改对象！");
   		}
 }
 
 function updateSellBefore(){
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
              location.href="../sellbefore/showSellBefore.action?method=enterUpdate&ids="+checkStr.substring(1);
         }
		   if(j==0){
		        alert("您还没有选择修改的对象！");
		   }
		   if(j>1){
    
    		 alert("不能选择多个修改对象！");
   		}
 }
  function contractInfo(){
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
              location.href="../sellbefore/showSellBefore.action?method=changeContract&id="+checkStr.substring(1);
              alert("已转为正式合同!")
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
         if(j==1)
         {  
         	if(confirm("确定要删除吗?")){
              	location.href="../sellbefore/showSellBefore.action?method=del&ids="+checkStr.substring(1);
              }
         }
		   if(j==0){
		        alert("您还没有选择删除的对象！");
		   }
		   if(j>1){
    
    		 alert("不能选择多个删除对象！");
   		}
 }
  
  
	function aaa() 
	{ 
		location.href="../sellbefore/contractBeforeSell.action"; 
	} 
	
	
	function onM()
	{
		location.href="../sellbefore/showSellBefore.action?method=showInfo";
	}
    function refresh()
    {
    	if(window.parent.window.leftFrame !=null && window.parent.window.leftFrame.main != null){
    		window.parent.window.leftFrame.main.location.reload();   
    	}
    }
</script>
</head>
<body onload="refresh()" >
<div align="left" style="color:#000000">当前页面：售前合同—>售前合同管理 </div>
<s:form action="selectSellBefore">
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
			<tr align="center">
				<td colspan="4" class="bg_table03" align="center">
				   <input type="button" name="update" value=" 修   改" onclick="updateSellBefore()" class="button01" /> 
				   	<input type="button" name="delete" value="　删 除　" class="button01" onClick="delChose()">
				   <input type="button"	name="SearchBtn" value=" 生成合同 " class="button01" onclick="contractInfo()">
				   <input type="button" name="SearchBtn" value=" 工程经济 " class="button01" onclick="addProgram()"> 
				</td>
				</tr>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">
				<tr align="center" >
					<td class="bg_table01">选择</td>
					<td class="bg_table01">编号</td>
					<td class="bg_table01">项目名称</td>
					<td class="bg_table01">客户</td>
					<td class="bg_table01">合同金额</td>
					<td class="bg_table01">起始日期</td>
					<td class="bg_table01">最后更新日期</td>
					<td class="bg_table01">跟踪状态</td>
					<td class="bg_table01">工程责任部门</td>

				</tr>
				<s:iterator value="info.result" id="sellBefore">			
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids"
							value="<s:property value="#sellBefore[0].ID"/>" />
						<td onclick="javascript:window.open('showSellBefore.action?method=showInfo&id=<s:property value="#sellBefore[0].ID"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800')"><s:property value="#sellBefore[0].ID" /></td>
						<td onclick="javascript:window.open('showSellBefore.action?method=showInfo&id=<s:property value="#sellBefore[0].ID"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800')"><s:property value="#sellBefore[0].projectName" /></td>
						<td onclick="javascript:window.open('showSellBefore.action?method=showInfo&id=<s:property value="#sellBefore[0].ID"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800')"><s:property value="#sellBefore[1].name" /></td>
						<td onclick="javascript:window.open('showSellBefore.action?method=showInfo&id=<s:property value="#sellBefore[0].ID"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800')"><s:property value="#sellBefore[0].projectSum" /></td>
						<td onclick="javascript:window.open('showSellBefore.action?method=showInfo&id=<s:property value="#sellBefore[0].ID"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800')"><s:property value="#sellBefore[0].bidDate" /></td>
						<td onclick="javascript:window.open('showSellBefore.action?method=showInfo&id=<s:property value="#sellBefore[0].ID"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800')"><s:property value="#sellBefore[0].estimateSignDate" /></td>
						<td onclick="javascript:window.open('showSellBefore.action?method=showInfo&id=<s:property value="#sellBefore[0].ID"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800')">
							<s:property value="typeManageService.getYXTypeManage(1009,#sellBefore[0].projectStateFollowId).typeName"/>
						</td>
						<td onclick="javascript:window.open('showSellBefore.action?method=showInfo&id=<s:property value="#sellBefore[0].ID"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800')">
							<s:property value="typeManageService.getYXTypeManage(1018,#sellBefore[0].dutyDepartmentId).typeName"/>
						</td>
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