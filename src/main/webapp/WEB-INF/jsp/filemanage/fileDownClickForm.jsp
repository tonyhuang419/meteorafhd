<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>文件下载</title>
<%@ include file="/commons/jsp/meta.jsp"%>
</head>
<body>
<iframe name="downFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>

<s:form action="fileDownClick" theme="simple">
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td align="center"> 
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="3" align="left" >当前页面：文件管理->文件明细</td>
			</tr>
			<tr>
            	<td colspan="3" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
          	<tr class="bg_table02">
			      <td width="33%"  align="right" class="bg_table02">上传人：</td>
			      <td width="67%"  align="right" class="bg_table02"><div align="left"><s:property value="fileInfo[2]" /></div></td>
	      </tr>     
          	<tr class="bg_table02">
			      <td width="33%"  align="right" class="bg_table02">上传时间：</td>
			      <td width="67%"  align="right" class="bg_table02"><div align="left"><s:date name="fileInfo[0].filedate" format="yyyy-MM-dd" /></div></td>
	      </tr> 
	      <tr class="bg_table02">
			      <td width="33%"  align="right" class="bg_table02">文件类型：</td>
			      <td width="67%"  align="right" class="bg_table02"><div align="left"><s:property value="typeManageService.getYXTypeManage(1014,fileInfo[0].filetype).typeName" /></div></td>
	      </tr> 
	      <tr class="bg_table02">
			      <td width="33%"  align="right" class="bg_table02">所属客户：</td>
			      <td width="67%"  align="right" class="bg_table02"><div align="left"><s:property value="fileInfo[1]" /></div></td>
	      </tr>
	      <tr class="bg_table02">
			      <td width="33%"  align="right" class="bg_table02">文件名：</td>
			      <td width="67%"  align="right" class="bg_table02"><div align="left"><s:property value="fileInfo[0].filename" /></div></td>
	      </tr>                  			    
			    <tr class="bg_table02">
			      <td width="33%"  align="right" class="bg_table02">文件说明：</td>
			      <td width="67%"  align="right" class="bg_table02"><div align="left"><s:textarea name="fileInfo[0].remarks" cols="40" rows="5" readonly="readonly"></s:textarea> </div></td>
		          </tr>
			    <tr align="center">
			      <td  colspan="3" class="bg_table02">&nbsp;</td>
	      </tr>
			    <tr align="center">
                  <td colspan="3" class="bg_table03">
                  <input type="button" name="SaveBtn" onclick="downFrame.location='<s:url action="downloadFile"><s:param name="method">downLoad</s:param></s:url>';" value="　下 载　" class="button02"></td>
	      </tr>
		</table>
</table>
</s:form>
</body>
</html>