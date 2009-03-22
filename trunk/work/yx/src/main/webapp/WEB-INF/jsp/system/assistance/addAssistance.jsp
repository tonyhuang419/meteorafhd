<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>

<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>外协信息新增</title>
</head>
<body>

<table width="780" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td valign="top" align="center">

			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td class="txt_title01" height="3" align="center">外协信息新增</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img
						src="../../images/temp.gif" width="1" height="1"></td>
				</tr>
			</table>
	
			<s:form  action="assistance" theme="simple">
					<s:hidden name="method" value="saveAssistance" />
				<table width="94%" border="0" cellspacing="1" cellpadding="1">
					<tr align="center">
						<td class="bg_table02" width="40%" align="right">供应商代码：</td>
						<td class="bg_table02" align="left">
							<s:textfield name="a.suplierId" />
						</td>
					</tr>
					<tr align="center">
						<td class="bg_table02" width="40%" align="right">开户银行：</td>
						<td class="bg_table02" align="left">
							<s:textfield name="a.bank" />
						</td>
					</tr>
					<tr align="center">
						<td class="bg_table02" width="40%" align="right">银行帐号：</td>
						<td class="bg_table02" align="left">
							<s:textfield name="a.acount" />
						</td>
					</tr>
					<tr align="center">
						<td class="bg_table02" width="40%" align="right">税号：</td>
						<td class="bg_table02" align="left">
							<s:textfield name="a.taxNum" />
						</td>
					</tr>
					<tr align="center">
						<td class="bg_table02" width="40%" align="right">联系人：</td>
						<td class="bg_table02" align="left">
							<s:textfield name="a.linkMan" />
						</td>
					</tr>
					<tr align="center">
						<td class="bg_table02" width="40%" align="right">电话：</td>
						<td class="bg_table02" align="left">
							<s:textfield name="zc.name" />
						</td>
					</tr>
					<tr align="center">
						<td class="bg_table02" width="40%" align="right">手机：</td>
						<td class="bg_table02" align="left">
							<s:textfield name="a.phone" />
						</td>
					</tr>
					<tr align="center">
						<td class="bg_table02" width="40%" align="right">供应商类型：</td>
						<td class="bg_table02" align="left">
							<s:textfield name="a.cellPhone" />
						</td>
					</tr>
										
					<tr align="center">
						<td align="right">
							<input type="submit" class="button01" value="保存"  />
						</td>
						<td align="left">
							<a href="javascript:history.go(-1)">返回</a>
						</td>
					</tr>
				</table>
			</s:form>
			<iframe name="errorsFrame" frameborder="0" height="0" width="100%" scrolling="no"></iframe>
		</td>
	</tr>
</table>
</body>
</html>

