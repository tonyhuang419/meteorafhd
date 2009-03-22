<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>项目跟踪状态列表</title>

<link href="./css/style.css" rel="stylesheet" type="text/css">

<script language="javascript">

function aaa() 
{ 

location.href="../system/projectStateFollow.action"; 
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
         location.href="../system/projectStateFollow.action?method=enterUpdate&idsss="+checkStr.substring(1);
       
      }
   if(j==0){
        alert("您还没有选择修改的对象！");
   }
   if(j>1){
    
     alert("不能选择多个修改对象！");
   }
      }
</script>

</head>
<body>
<s:form action="projectStateFollow">
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">


				<tr class="bg_table03">

					<td colspan="6" class="bg_table03">
					<div align="center"><input type="button" name="SearchBtn2"
						value="　删 除　" class="button01" onClick="">
						<input
						type="button" name="SearchBtn2" value=" 修 改" class="button01"
						onclick="javascript:bbb()" /> <input type="button"
						name="SearchBtn2" value="　新 增　" class="button01"
						onclick="javascript:aaa()" /></div>
					</td>
				</tr>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01">项目状态</td>
					<td class="bg_table01">最后修改日期</td>
					<td class="bg_table01">最后修改人</td>
				</tr>

				<s:iterator value="info.result">
					<tr align="center">
						<td><input type="checkbox" name="ids"
							value="<s:property value="id"/>" /></td>
						<td><s:property value="type" /></td>
						<td><s:date name="modifyTime" format="yyyy-MM-dd" /></td>
						<td><s:property value="modifyPeople" /></td>
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
