<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<head>
<title>文件上传</title>
</head>
<body>
<s:fielderror/>
<s:actionerror/>
<s:form action ="testfileupload" method ="POST" theme="simple" enctype ="multipart/form-data" >   
<s:hidden name ="method" value ="doUpload" />        
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td align="center"> 
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="3" align="left" >当前页面：文件管理->文件上传</td>
			</tr>
			<tr>
            	<td colspan="3" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
            	<td colspan="3" align="right" class="bg_table03">&nbsp;</td>
			    <tr class="bg_table02">
			      <td width="17%" align="right" class="bg_table02"><div align="right">请选择文件：</div></td>
	              <td align="right" class="bg_table02"><div align="left">
			        <s:file name ="myFile" label ="Image File" /> 
			        <s:textfield name ="caption" label ="Caption" />        
			        <s:submit/> 
                  </div></td>
          </tr>
          </table>
          </td>
          </tr>
	</table>
</s:form> 
<p>&nbsp;</p>
<p>&nbsp; </p>
</body>
</html>

