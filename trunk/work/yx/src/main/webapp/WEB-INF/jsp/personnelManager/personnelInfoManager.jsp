<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>员工信息管理</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
	function addPer() 
	{ 
		location.href="../personnelManager/personnelManager.action"; 
	} 
 	function updatePer(){
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
             location.href="../personnelManager/personnelManager.action?method=enterUpdate&ids="+checkStr.substring(1);
         }
		   if(j==0){
		        alert("您还没有选择修改的对象！");
		   }
		   if(j>1){
    
    		 alert("不能选择多个修改对象！");
   		}
 	}
 
 
 	function changePWD(){
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
             location.href="../personnelManager/personnelManager.action?method=showChangePwd&ids="+checkStr.substring(1);
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
	    if(j>0)
	    {
		    if(confirm("确定要删除此员工信息吗?")){
			 	location.href="../personnelManager/personnelManager.action?method=del&ids="+checkStr.substring(1);
			}
			else
			{
			  return false;
			}
		}
		else
		{
		    alert("您还没有选择修改的对象！");
		}
  	}    
</script>
</head>
<style>
	html { overflow-x:hidden;  }
</style>
<body style="margin: 0px;">
<div align="left" style="color:#000000">当前页面：权限管理—>员工管理</div>
<s:form action="selectPerQuery" theme="simple">

			<table align="center" border=0 cellpadding=1 cellspacing=1	width="100%">
			<tr>
 			   <td align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
 			 </tr>
			<tr align="center">
				<td colspan="4" class="bg_table03" align="center">
				   <input type="button" name="add" value=" 新   增 " onclick="addPer()" class="button01" /> 
				   <input type="button" name="update" value=" 修   改 " onclick="updatePer()" class="button01" /> 
				   <input type="button" name="delete" value=" 删   除 " class="button01" onClick="delChose()"/> 
		           <input type="button" name="SearchBtn" value=" 修改密码 " class="button01" onclick="changePWD()">
				</td>
				</tr>
			</table>
			<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td style="display: none;" class="bg_table01">编号</td>
					<td class="bg_table01">用户名</td>
					<td class="bg_table01">员工名称</td>
					<td class="bg_table01">工号</td>
					<td class="bg_table01">职责</td>
					<td class="bg_table01">联系方式</td>
				</tr>
				<s:iterator value="info.result" id="user">
					<tr align="center"  onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';" >
						<td><input type="radio" name="ids"
							value="<s:property value="#user[0].id"/>" />
						<td align="left" style="display: none;"><s:property value="#user[0].id" /></td>
						<td align="left" onclick="javascript:window.open('personnelManager.action?method=showInfo&clientId=<s:property value="#user[0].id"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=400,width=600')"><s:property value="#user[0].username" /></td>
						<td align="left" onclick="javascript:window.open('personnelManager.action?method=showInfo&clientId=<s:property value="#user[0].id"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=400,width=600')"><s:property value="#user[0].name" /></td>
						<td align="left" onclick="javascript:window.open('personnelManager.action?method=showInfo&clientId=<s:property value="#user[0].id"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=400,width=600')"><s:property value="#user[0].workId" /></td>
						<td align="left" onclick="javascript:window.open('personnelManager.action?method=showInfo&clientId=<s:property value="#user[0].id"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=400,width=600')"><s:property value="#user[1].organizationName" /></td>
						<td align="left" onclick="javascript:window.open('personnelManager.action?method=showInfo&clientId=<s:property value="#user[0].id"/>','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=400,width=600')"><s:property value="#user[0].phone" /></td>
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