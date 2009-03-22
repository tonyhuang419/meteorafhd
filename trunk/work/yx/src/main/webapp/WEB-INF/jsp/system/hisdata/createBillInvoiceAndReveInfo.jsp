<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<head>
<title>合同历史数据导入</title>
</head>
<body>
<s:form name="fileupload" action ="createBillInvoiceAndReveInfo" method ="POST" theme="simple" enctype ="multipart/form-data" > 
<div style="color:red">
<s:fielderror/>
</div>
<table width="98%" border="0" cellspacing="1" cellpadding="1"
	class="bg_white" align="center">
	<tr>
		<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
					<td colspan="2" align="left">当前页面：基础管理->关闭以后的合同生成开票和收款</td>
				</tr>
				<tr class="bg_table02">
					<td width="17%" align="right" class="bg_table02">
						<div align="right">请选择文件：</div>
					</td>
					<td align="right" class="bg_table02">
						<div align="left"><s:file name="excelFile"/></div>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="bg_table03"><s:submit value="上传" /></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</s:form>
<p>&nbsp;</p>
<p>&nbsp;</p>
</body>
</html>