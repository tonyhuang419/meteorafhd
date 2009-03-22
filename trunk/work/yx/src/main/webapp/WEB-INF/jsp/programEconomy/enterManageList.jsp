<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>

<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">

</script>
<title>申购采购管理</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
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

<s:form action="enterManageQuery">

			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
					<td align="left">当前页面：工程经济->录入管理</td>
				</tr>
				<tr>
					<td align="right" class="bg_table01" height="0.5"><img
						src="./../images/temp.gif" width="1" height="3"></td>
				</tr>
				<tr class="bg_table03">
					<td align="right" class="bg_table03">
					<div align="center"><input type="button" class="button02"
						value="招标文件录入" onClick="javascript:ddd();"> <input type="button"
						class="button02" value="设备总表录入确认" onClick="javascript:aaa();"> <input
						type="button" class="button02" value="设备清单录入" onClick="javascript:ccc();">
					<input type="button" class="button02" value="BPMS录入" onClick="javascript:bbb();"></div>
					</td>
				</tr>
			</table>
			<table align="center" border="1" cellpadding="1" cellspacing=1
				width="100%" id="checkInfo" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td width="7%" class="bg_table01">选择</td>
					<td width="11%" class="bg_table01">工程编号</td>
					<td width="17%" class="bg_table01">项目名称</td>
					<td width="12%" class="bg_table01">设计委托进度</td>
					<td width="17%" class="bg_table01">跟踪销售人员</td>
					<td width="10%" class="bg_table01">招标文件</td>
					<td width="11%" class="bg_table01">设备总表录</td>
					<td width="15%" class="bg_table01">执行阶段</td>
				</tr>
				<s:iterator id="result" value="info.result">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
						onMouseOut="this.bgColor='#FFFFFF';" >
				       
						<td align="center"><input type="checkbox" name="ids"
							value="<s:property value="#result[0].id"/>" /></td>
						<td align="left" onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					  ><s:property value="#result[0].projectNumber" /></td>
						<td align="left" onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					   > <s:property value="#result[0].projectName" /></td>
						<td align="center" onclick=""><s:date name="#result[1].designSpeed"
							format="yyyy-MM-dd" /></td>
						<td align="left" onclick=""><s:property value="#result[2]" /></td>
						<s:if test="!#result[0].bidVerify || #result[0].bidVerify==0">
							<td align="left" onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					    >未录入</td>
						</s:if>
						<s:elseif test="#result[0].bidVerify==1">
							<td align="left" onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					    >已录入</td>
						</s:elseif>
						<s:else>
							<td align="left" onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					    ></td>
						</s:else>
						<s:if test="#result[0].enterFlag==0">
							<td align="left" onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					   >未录入</td>
						</s:if>
						<s:elseif test="#result[0].enterFlag==1">
							<td align="left" onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					   >已录入</td>
						</s:elseif>
						<s:else>
							<td align="left" onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					    ></td>
						</s:else>

						<td align="left" onclick="openUrl('programEconomy.action?method=economyView&seid=<s:property value="#result[0].id"/>')" 
					   >
<!--					   <s:property value="#result[1].sectionName"  />-->
					   	<s:property value="typeManageService.getYXTypeManage(1011,#result[1].sectionName).typeName" />
					   </td>
					   <input type="hidden" name="s<s:property value="#result[0].id"/>" id="s<s:property value="#result[0].id"/>" value="<s:property value="#result[1].id"/>"/>
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
<script language="javascript">
function aaa(){
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
         location.href="../programEconomy/programEconomy.action?method=enterManageOperate&tag=1&sectionId="+checkStr.substring(1);
       
      }
   if(j==0){
        alert("您还没有选择需要操作的对象！");
   }
   if(j>1){
    
     alert("不能选择多个操作对象！");
   }
      } 
function bbb(){
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    var svalue;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
             var idv="s"+checkArr[i].value;
             svalue=document.getElementById(idv).value; 
 
            
        }
    }
    if(j==1)
      {  if(svalue!=""){
              location.href="../programEconomy/programEconomy.action?method=enterBpms&tag=2&lastId="+svalue+"&sectionId="+checkStr.substring(1);
          }
          else{
            alert("没有阶段，不能进行bpms操作！");
          }
        
       
      }
   if(j==0){
        alert("您还没有选择需要操作的对象！");
   }
   if(j>1){
    
     alert("不能选择多个操作对象！");
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
         location.href="../programEconomy/programEconomy.action?method=enterManageOperate&tag=3&sectionId="+checkStr.substring(1);
       
      }
   if(j==0){
        alert("您还没有选择需要操作的对象！");
   }
   if(j>1){
    
     alert("不能选择多个操作对象！");
   }
      }
  function ddd(){
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
         location.href="../programEconomy/programEconomy.action?method=enterManageOperate&tag=4&sectionId="+checkStr.substring(1);
       
      }
   if(j==0){
        alert("您还没有选择需要操作的对象！");
   }
   if(j>1){
    
     alert("不能选择多个操作对象！");
   }
      }

	function openUrl(url){
    	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	function loader(){
		location.href="../programEconomy/enterManageQuery.action";
	}
  
</script>

