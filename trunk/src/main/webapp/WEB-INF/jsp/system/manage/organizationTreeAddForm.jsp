<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
	<title>Import Management</title>
	
	<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css" />
</head>

<body>
	
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td valign="top">
		
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td height="3" align="left">&nbsp;</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img src="../../images/temp.gif" width="1" height="1"></td>
				</tr>
			</table>
			
			<s:form action="organizationTree" method="post" theme="simple">
				<s:hidden name="method" value="saveChildNode"/>
				<s:hidden name="organizationId" />
				<table width="94%" border="0" cellspacing="1" cellpadding="1">
					<tr>
						<td colspan="4" align="right" class="bg_table02">&nbsp;</td>
					</tr>
					<tr align="center">
						<td align="right" class="bg_table02">
							父结点代码
						</td>
						<td align="left" class="bg_table02">
							<s:property value="organizationTree.organizationCode" />
						</td>
						<td align="right" class="bg_table02">
							父结点名称
						</td>
						<td align="left" class="bg_table02">
							<s:property value="organizationTree.organizationName" />
						</td>
					</tr>
					<tr align="center">
						<td width="15%" align="right" class="bg_table02">组织名：</td>
						<td width="35%" align="left" class="bg_table02">
							<s:textfield name="subOrganizationName" />
						</td>
						<td align="right" class="bg_table02">&nbsp;</td>
						<td align="left" class="bg_table02">&nbsp;</td>
					</tr>
					<tr class="bg_table03" align="center" style="height: 42px">
						<td align="right" width="43" colspan="2">
							<input class="button01" type="button" value="返回" onClick="" />
						</td>
						<td align="left" width="43" colspan="2">
							<input class="button01" type="submit" value="保存" />
						</td>
					</tr>
				</table>
			</s:form>
			
		</td>
	</tr>
</table>
	
</body>
</html>

