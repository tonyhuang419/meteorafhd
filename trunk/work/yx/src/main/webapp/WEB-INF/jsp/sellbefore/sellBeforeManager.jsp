<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<head>
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
              location.href="../sellbefore/showSellBefore.action?method=enterUpdate&comeFrom=mod&ids="+checkStr.substring(1);
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
         	if(confirm("确定要转为正式草稿合同吗?")){
              location.href="../sellbefore/showSellBefore.action?method=changeContract&id="+checkStr.substring(1);
         	}
         }
		   if(j==0){
		        alert("您还没有选择修改的对象！");
		   }
		   if(j>1){
    
    		 alert("不能选择多个修改对象！");
   		}
 }
  function delChose(){
	   var flag=0;
	   var chck=document.getElementsByName("ids");
	   for(i=0;i<chck.length;i++){
	       if(chck[i].checked==true){
	           flag++;
	       }
	   }
	   if(flag>0){
	   	  if(confirm("确定要删除这些信息吗?")){
	   		location.href="../sellbefore/showSellBefore.action?method=del&="+$(selectSellBefore).toQueryString();
	   	  }
       }else{
           alert("您没有选择任何一项!");
       }
	}

  	function showHistory(){

  		var checkedBoxes = $$("input[name=ids][checked]");
		if(checkedBoxes.length == 0)
		{
			alert("请选择要查看的售前合同!");
			return;
		}
		if(checkedBoxes.length > 1)
		{
			alert("请不要多选!");
			return;
		}
		if(checkedBoxes.length == 1)
		{
			id = checkedBoxes[0].value;
			openWin2("../sellbefore/showSellBefore.action?method=showHistory&id="+id,800,600,"cbsHistory");
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
    function openDetail(cbsId){
    	openWin2("showSellBefore.action?method=showInfo&id="+cbsId,800,600,'cbsDetail');
    }
    
    

</script>
</head>
<!-- 根据返回的proSave数值判断alert出信息
	1  表示 生成过工程经济
	2  表示 售前合同已转为正式草稿合同
	3  表示工程经济保存成功
	4   表示修改售前合同信息成功
	5  表示 删除售前合同
-->
<s:if test="proSave == 1">
	<script language="javascript"> 
		alert("该售前合同工程经济已经提交!");
	</script>
</s:if>
<s:elseif test="proSave == 2">
	<script language="javascript"> 
		alert("该售前合同已转为正式草稿合同!");
	</script>
</s:elseif>
<s:elseif test="proSave == 3">
	<script language="javascript"> 
		alert("工程经济保存成功!");
	</script>
</s:elseif>
<s:elseif test="proSave == 4">
	<script language="javascript"> 
		alert("保存成功!");
	</script>
</s:elseif>
<s:elseif test="proSave == 5">
	<script language="javascript"> 
		alert("删除成功!");
		window.parent.window.leftFrame.upFrame.location.reload();  
	</script>
</s:elseif>
<body style="margin: 0px;">
<div align="left" style="color:#000000">当前页面：售前合同—>售前合同管理 </div>
<s:form action="selectSellBefore">
<s:hidden name="stageId"/>
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
			<tr>
				<td colspan="7" align="right" class="bg_table01" height="3"></td>	
			</tr>
			<tr align="center">
				<td  class="bg_table03" align="center">
				   <input type="button" name="update" value="修  改" onclick="updateSellBefore()" class="button01" /> 
				   <input type="button" name="delete" value="删   除 " class="button01" onClick="delChose()">
				   <input type="button"	name="SearchBtn" value="生成合同 " class="button01" onclick="contractInfo()">
				   <input type="button" name="SearchBtn" value="工程经济 " class="button01" onclick="addProgram()"> 
				   <input type="button" name="SearchBtn" value="查看修改历史" class="button01" onclick="showHistory()"> 
				</td>
			</tr>
			</table>
			<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center" >
					<td class="bg_table01">选择</td>
					<td class="bg_table01">编号</td>
					<td class="bg_table01">项目名称</td>
					<td class="bg_table01">客户</td>
					<td class="bg_table01">预计金额</td>
					<td class="bg_table01">起始日期</td>
					<td class="bg_table01">最后更新日期</td>
					<td class="bg_table01">跟踪状态</td>
					<td class="bg_table01">工程责任部门</td>
				</tr>
				<s:iterator value="info.result" id="sellBefore">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids" value="<s:property value="#sellBefore[0].ID"/>" 
					 			 <s:if test="#sellBefore[4]!=null">disabled</s:if>/></td>
						<td align="left" onclick="openDetail('<s:property value="#sellBefore[0].ID"/>')"><s:property value="#sellBefore[0].sellBeforeNum" /></td>
						<td align="left" onclick="openDetail('<s:property value="#sellBefore[0].ID"/>')"><s:property value="#sellBefore[0].projectName" /></td>
						<td align="left" onclick="openDetail('<s:property value="#sellBefore[0].ID"/>')"><s:property value="#sellBefore[1].name" /></td>
						<td align="right" onclick="openDetail('<s:property value="#sellBefore[0].ID"/>')"><s:property value="#sellBefore[0].projectSum" /></td>
						<td onclick="openDetail('<s:property value="#sellBefore[0].ID"/>')"><s:property value="#sellBefore[0].projectDate" /></td>
						<td onclick="openDetail('<s:property value="#sellBefore[0].ID"/>')"><s:property value="#sellBefore[0].updateBy" /></td>
						<td align="left" onclick="openDetail('<s:property value="#sellBefore[0].ID"/>')">
							<s:property value="typeManageService.getYXTypeManage(1009,#sellBefore[0].projectStateFollowId).typeName"/>
						</td>
						<td align="left" onclick="openDetail('<s:property value="#sellBefore[0].ID"/>')">
							<s:property value="typeManageService.getYXTypeManage(1018,#sellBefore[0].dutyDepartmentId).typeName"/>
						</td>
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
<s:if test="message!=null">
	<script language="JavaScript" type="text/JavaScript">
		alert("<s:property value="message"/>");
	</script>
</s:if>
</html>