<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
	<title>Import Management</title>
	
	<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css" />
	<script>
	function back()
{
  		location.href="../manage/organizationTreeQuery.action?method=doDefault";
}
</script>
</head>

<body>
		
			<s:form action="organizationTree" method="post" theme="simple">
				<s:hidden name="method" value="saveChildNode"/>
				<s:hidden name="organizationId" />
				<table width="70%" border="0" cellspacing="1" cellpadding="1" align="center">
				<tr>
					<td  colspan="4" class="bg_table01" height="1"><img src="../../images/temp.gif" width="1" height="3"></td>
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
							<s:textfield name="subOrganizationName"  maxlength="20"/>
						</td>
						<td align="right" class="bg_table02">&nbsp;</td>
						<td align="left" class="bg_table02">&nbsp;</td>
					</tr>
					<tr class="bg_table03" align="center" style="height: 42px">
						<td colspan="4">
							<div align="center">
								<input class="button01" type="button" value="返回" onClick="back();" />
								<input class="button01" type="submit" value="保存" />
							</div>
						</td>
					</tr>
				</table>
			</s:form>

	
</body>
</html>

