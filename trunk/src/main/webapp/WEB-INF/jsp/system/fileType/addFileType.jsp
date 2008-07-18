<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加文件类型</title>
<link href="/src/main/webapp/commons/styles/style.css" rel="stylesheet" type="text/css"/>
<link href="/src/main/webapp/commons/styles/calendar-win2k-cold-1.css" rel="stylesheet" type="text/css"/>

</head>
<body>
	 <table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td align="center"> 
		<table align="center"  border=0 cellpadding=1 cellspacing=1 width="100%">
			<tr align="center"> 
				<td colspan="4" align="center" class="txt_title01">文件类型表</td>
			</tr>
			<tr>
            	<td colspan="4" align="right" class="bg_table01"  height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
          	</tr>		
			
		</table>
		<s:form action="fileType"  theme="simple">
			<s:hidden name="method" value="saveFT" />
		<table width="80%">
			<tr>
				<td>文件类型名称</td><td><td><s:textfield name="ft.fileType"  /></td>
				
			</tr>
			<tr align="center">
			  <td colspan="4" class="bg_table03" align="center">
       				<input type="submit"  name="addBtn" value="　新　  增　" class="button01" />
       				<input type="button" value="返回" onclick="javascript:history.go(-1);" class="button01"/>
			</tr>
		</table>
		</s:form>
</body>
</html>