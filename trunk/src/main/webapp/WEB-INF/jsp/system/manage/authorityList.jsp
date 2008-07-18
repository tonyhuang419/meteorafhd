<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>

<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src='..//js/function.js'></script>
<script type="text/javascript" src='..//js/page.js'></script>
<script type="text/javascript" src='..//js/jquery.js'></script>
<script type="text/javascript" src='..//js/jquery.tablesorter.js'></script>
<script type="text/javascript" src='..//js/sorttable.js'></script>
<script type="text/javascript" src='..//js/YS_checkboxHelper.js'></script>

<title>权限信息管理</title>

</head>

<body>

<table width="96%" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td width="30%">当前页面：基础管理->客户管理</td>
		<td width="4%">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>

<table align="center" width="96%" class="bg_table">
	<tr>
		<td width="13" height="0.5" align="right" class="bg_table01">
			<img src="../blue/images/temp.gif" width="1" height="1">
		</td>
	</tr>
	<tr class="bg_table03" align="center">
		<td>
			</td>
	</tr>
</table>

<s:form action="authorityQuery" theme="simple">
<table align="center" width="96%" class="bg_table">
	<tr align="center" class="bg_table01">
		<td width="25%" align="center">权限名称</td>
		<td width="43%">权限描述</td>
		<td width="18%">权限代码</td>
		<td width="11%">类型</td>
	</tr>
	<s:iterator value="info.result">
		<tr class='bg_table02'>
			<td align="left">
				<s:property value="authorityName" />
			</td>
			<td align="left">
				<s:property value="getCodeDestPath(code)" />
			</td>
			<td align="center">
				<s:property value="code" />
			</td>
			<td align="center">
				<s:if test="type == 1">
					模块
				</s:if>
				<s:elseif test="type == 2">
					页面
				</s:elseif>
				<s:else>
					按钮
				</s:else>
			</td>
		</tr>
	</s:iterator>
</table>
<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
	<tr valign="top">
		<td class="bg_table04">
			<s:if test="info.result!=null">
				<baozi:pages value="info" beanName="info" formName="forms(0)" />
			</s:if>
		</td>
	</tr>
</TABLE>
</s:form>

</body>
</html>
