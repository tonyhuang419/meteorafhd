<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>外协付款确认</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
function edit(){
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
         var url="../assistance/apply.action?method=enterUpdate&ids="+checkStr.substring(1);
         window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');
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
       	alert("您还没有选择修改的对象！");
        
   }else{
 	  	var name = confirm("确定删除?");
   		if(name==true){
   			location.href="../assistance/apply.action?method=delete&ids="+checkStr.substring(1);
   		}else{
        	 return;
       	}
   }
}
function aaa() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
    location.href="../assistance/apply.action?method=pass&ids="+checkStr.substring(1); 
}
function printList()
{
	 var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var flag=false;
    if(checkArr!=null&&checkArr.length>0){
    for(var i=0;i<checkArr.length;i++){
    	if(checkArr[i].checked)
    	{
    		checkStr=checkArr[i].value;
    		flag=true;
    		break;
    	}
    }
    }
    if(!flag)
    {
    	alert("请选择要打印的申请单");
    	return;
    }
    
    	var sum=0;
				if(checkArr!=null&&checkArr.length>0)
				{
					for(var k=0;k<checkArr.length;k++){
						if(checkArr[k].checked)
						{
							sum++;
						}
						
					}
				}
				if(sum>1)
				{
					alert("只能选择一个打印！");
					return;
				}
    	window.open("assistancePayForPDF.action?method=assistancePayFor&paramId="+checkStr,"","menubar=yes,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
    
}
</script>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
</head>
<body leftmargin="0">
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<tr>
	<td align="center"> 
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td align="left" >当前页面：外协管理->外协付款申请管理</td>
</tr>
			<tr>
            	<td align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
			    <tr class="bg_table02">
			      <td align="right"><div align="center">
			        <input type="button" name="SearchBtn3" value="修  改" class="button01" onClick="javascript:edit();" >
		            <input type="button" name="SearchBtn2" value="提交确认" class="button01" onClick="javascript:aaa();">
		            <input type="button" name="SearchBtn2" value="申请单打印" class="button01" onClick="printList();">
		          <input type="button" value="删    除" class="button02" onclick="javascript:delChose()">			     
		          </div></td>
        </tr>
		</table>
	  <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="checkInfo">
<tr align="center">
	      <td width="8%" class="bg_table01">选择</td>
          <td width="11%" class="bg_table01">申请序号</td>
          <td width="13%" class="bg_table01">外协合同号</td>
          <td width="13%" nowrap class="bg_table01">外协合同名称</td>
          <td width="18%" class="bg_table01">外协供应商</td>
          <td width="12%" class="bg_table01">申请日期</td>
          <td width="14%" class="bg_table01">申请金额</td>
          <td width="11%" nowrap class="bg_table01">申请单状态</td>
</tr>
	<s:iterator value="info.result" id="apply">
	    <tr align="center">
          <td><input type="checkbox" name="ids"  value="<s:property value="#apply[0].id"/>"></td>
          <td><s:property value="#apply[0].id"/></td>
          <td><s:property value="#apply[1]"/></td>
          <td><s:property value="#apply[2]"/></td>
          <td><s:property value="#apply[3]"/></td>
          <td><s:property value="#apply[0].applyDate"/></td>
          <td><s:property value="#apply[0].payNum"/></td>
          <td><s:property value="#apply[0].payState"/></td>
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
<p>&nbsp;</p>
</body>
</html>
