<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>上传附件</title>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 16px
}

body {
	background-color: #FFFFFF;
}

.AutoNewline {
	word-break: break-all; /*必须*/
}
-->
</style>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
</head>
<body leftmargin="0">
<iframe id="applyBillDownFrame" style="display: none;"></iframe>
<p>&nbsp;</p>
<s:form action="applyBillAttachment" method="POST" theme="simple"
	enctype="multipart/form-data">
	<div style="color: red"><s:fielderror /></div>
	<s:hidden name="method" value="execute"></s:hidden>
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1 class="bg_white" 
				width="100%">
				<tr class="bg_table02">
					<td width="17%" align="right" class="bg_table02">
					<div align="right">请选择文件：</div>
					</td>
					<td align="right" class="bg_table02">
					<div align="left"><s:file name="myFile" /></div>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="bg_table03"><s:submit value="上传" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<s:if test="#session.applyBillFilePath!=null && #session.applyBillFileName!=null">
			<tr>
			<tD>
				<a href="javascript:showUploadFile();"><s:property value="#session.applyBillFileName"/></a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="javascript:deleteUploadFile();">删除</a>
			</tD>
			</tr>
		</s:if>
		<s:else>
		<tr>
		<td><s:property value="#session.applyBillFileName"/></td>
		</tr>
		</s:else>
	</table>
</s:form>
</body>
<script type="text/javascript">
function showUploadFile()
{
 var url="/yx/billtoReceipt/applyBillShowUpload.action?method=downLoadFile";
	applyBillDownFrame.location=url;
}
function deleteUploadFile()
{
	var url="/yx/billtoReceipt/applyBillAttachmentMain.action?method=deleteUploadFile"
	window.location=url;
}
</script>
</html>

