<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>售前合同修改历史</title>
</head>
<body>
<div align="left" style="color:#000000"><br/>当前页面：售前合同->售前合同修改历史</div>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
				<td colspan="5" align="right" class="bg_table01" height="3"></td>	
		</tr>
		<tr align="center" class="bg_table03">
			<td  nowrap align="center">修改项</td>
			<td  nowrap align="center" >修改前</td>
			<td  nowrap align="center">修改后</td>
			<td  nowrap align="center" >修改时间</td>
			<td  nowrap align="center" >修改人</td>
		</tr>
		<s:set name="groupId" value="0"/>
	<s:iterator value="ccList" id="info" status="stat">
		 <s:if test="#info[0].groupId!=#groupId && #stat.index!=0 ">			   
			 <tr>
				<td colspan="5" class="bg_white">
					<hr align="center"/>
				</td>
			</tr>
		</s:if>
		<s:set name="groupId" value="#info[0].groupId"/>
		<tr align="center" class="bg_table02">
			<td  align="left"><s:property value="#info[0].type" /></td>
			<td  align="left" ><s:property value="#info[0].original" /></td>
			<td  align="left"><s:property value="#info[0].present" /></td>
			<td  align="center" ><s:date name="#info[0].updateBy" format="yyyy-M-d HH:mm:ss"/></td>
			<td  align="left" ><s:property value="#info[1]" /></td>
		</tr>
	</s:iterator>
	<tr>
		<td colspan="5" align="center" >
			<input type="button" class="button01" onclick="window.close()"  value="关  闭" />
		</td>
	</tr>
	</table>

</body>

</html>