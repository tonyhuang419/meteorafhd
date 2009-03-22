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
	                <input type="file" name="afile"  />
                  </div></td>
          </tr>
			    <tr class="bg_table02">
			      <td  align="right">文件类型：
			        
			       </td>
			      <td  align="left">
                      <select name="select">
                        <option>可研文本</option>
                        <option>初步设计文本</option>
                        <option>内控流程文件</option>
                        <option>部门管理文件</option>
                        <option>公司管理文件</option>
                      </select>
                </td>
	      </tr>
			    <tr class="bg_table02">
			      <td  align="right">所属客户：</td>
			      <td  align="left">
                      <s:select  name="clientName" id="clientName" list="yxClientCodeList" listKey="ycc.id" listValue="name" required="true"
							headerValue="">
					</s:select>
                </td>
	      </tr>
			    <tr class="bg_table02">
			      <td  align="right">文件名：</td>
			       <td align="left">
			        <input type="text" size="20" />
			     </td>
	      </tr>
			    <tr class="bg_table02">
			      <td  align="right" class="bg_table02">文件说明：</td>
			      <td  align="right" class="bg_table02"><div align="left">
			        <textarea cols="40" nrows="5"></textarea>
		          </div></td>
	      </tr>
			    <tr align="center">
			      <td  colspan="3" class="bg_table02">&nbsp;</td>
	      </tr>
			    <tr align="center">
                  <td colspan="3" class="bg_table03"><input type="button" name="SearchBtn" value="上  传" CLASS="button02" ></td>
	      </tr>
		</table>

</table>
<p>&nbsp;</p>
<p>&nbsp; </p>
</body>
</html>