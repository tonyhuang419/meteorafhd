<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>

<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="projectDangerQuery">
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				
				
			<tr class="bg_table03">

					<td colspan="6" class="bg_table03">
					<div align="center"><input
						type="button" name="SearchBtn2" value="　删 除　" class="button01"
						onClick="javascript:aaa()"><input type="submit" name="SearchBtn2"
						value=" 修 改" class="button01" > <input
						type="button" name="SearchBtn2" value="　新 增　" class="button01"
						onClick="javascript:bbb()"></div>
					</td>
				</tr>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01">组号</td>
					<td class="bg_table01">工号</td>
					<td class="bg_table01">姓名</td>
					<td class="bg_table01">手机</td>
					<td class="bg_table01">办公电话</td>
					<td class="bg_table01">邮件地址</td>
					<td class="bg_table01">职责代码</td>
					<td class="bg_table01">最后修改日期</td>
					<td class="bg_table01">最后修改人</td>
				</tr>

				<s:iterator value="info.result">
					<tr align="center">
						<td><input type="checkbox" name="ids"
							value="<s:property value="id"/>" /></td>
						<td><s:property value="groupId" /></td>
						<td><s:property value="workId" /></td>
						<td><s:property value="name" /></td>
						<td><s:property value="callPhone" /></td>
						<td><s:property value="phone" /></td>
						<td><s:property value="email" /></td>
						<td><s:property value="zzdmId" /></td>
						<td><s:date name="lastDate" format="yyyy-MM-dd" /></td>
						<td><s:property value="lastByid" /></td>
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
