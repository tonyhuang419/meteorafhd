<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改项目信息</title>
<link href="/src/main/webapp/commons/styles/style.css" rel="stylesheet" type="text/css"/>
<link href="/src/main/webapp/commons/styles/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
function submitBtn()
{
	document.forms(0).submit();	
}
</script>
</head>
<body>
	 <table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td align="center"> 
		<table align="center"  border=0 cellpadding=1 cellspacing=1 width="100%">
			<tr align="center"> 
				<td colspan="4" align="center" class="txt_title01">项目组成内容信息表</td>
			</tr>
			<tr>
            	<td colspan="4" align="right" class="bg_table01"  height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
          	</tr>	
			
		</table>
		
	<s:form action="projectInfo" theme="simple">
		<s:hidden name="method" value="updatePI" />
		<table width="80%">
			<tr>
				<td>项目组成信息系统号</td><td><s:textfield name="pi.id"  /></td>
			</tr>
			<tr>
				<td>项目内容</td><td><s:textfield name="pi.projectInfo" /></td>
			</tr>
			<tr>
				<td>最后修改日期</td><s:textfield name="pi.lastupdateTime" /></td>
            </tr>
			<tr>
				<td>最后修改人</td><td><s:textfield name="pi.lastupdateId" /></td>
			</tr>
			<tr align="center">
			  <td colspan="4" class="bg_table03" align="center">
       				<input type="submit"  name="addBtn" value="　修   改　" onclick="submitBtn()" class="button01" />
       				<input type="button" value="返回" onclick="javascript:history.go(-1);" class="button01"/>
			</tr>
		</table>
	</s:form>
</body>
</html>