<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<script language="javascript">

function add() 
{ 
location.href="../system/suplierType.action"; 
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
         location.href="../system/ticketType.action?method=enterUpdate&idsss="+checkStr.substring(1);
       
      }
   if(j==0){
        alert("您还没有选择修改的对象！");
   }
   if(j>1){
    
     alert("不能选择多个修改对象！");
   }
      }
</script>

<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="suplierType">
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
					<td class="bg_table02"><input type="button" name="SearchBtn1"
						value=" 新 增 " class="button01" onClick="javascript:add()"></td>
					<td class="bg_table02"><input type="button" name="SearchBtn2"
						value=" 删 除 " class="button02"></td>
					<td class="bg_table02"><input type="button" name="SearchBtn3"
						value=" 修 改 " class="button03" onClick="javascript:edit()"></td>
					<td class="bg_table02"><input type="button" name="SearchBtn4"
						value=" 查 询 " class="button04"></td>
				</tr>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01">供应商类别名称</td>
				</tr>

				<s:iterator value="info.result">
					<tr align="center">
						<td><input type="checkbox" name="ids"
							value="<s:property value="id"/>" /></td>
						<td><s:property value="suplierType" /></td>
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
