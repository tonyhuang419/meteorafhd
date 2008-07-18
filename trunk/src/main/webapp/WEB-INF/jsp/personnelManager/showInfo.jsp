<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>显示用户信息</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="personnelManager" theme="simple">
<s:hidden name="method" value="updatePer"></s:hidden>
<input type="hidden" name="perId" value="<s:property value="per.id"/>" />
<input type="hidden" name="cliId" />
<s:hidden name="per.id"/>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center" >
	<tr> 
		<td valign="top" > 
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
			  <td height="3" align="left" >当前页面：基础管理->客户管理</td>
		  </tr>
			<tr><td class="bg_table01" height="1"><img src="../../images/temp.gif" width="1" height="1"></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
<tr align="center">
			  <td width="15%" align="right" nowrap class="bg_table02">用户名：</td>
			  <td width="35%" align="left" nowrap class="bg_table02"><s:property  value="per.username"  /></td>
			  <td align="right" nowrap class="bg_table02">&nbsp;</td>
					<td align="left" nowrap class="bg_table02">&nbsp;</td>
			 	 
		  </tr>
			<tr align="center">			 
			<td width="15%" align="right" nowrap class="bg_table02">工号：</td>
			  <td width="35%" align="left" nowrap class="bg_table02">
			  		<s:property  value="per.workId"  /></td>
			  <td align="right" nowrap class="bg_table02">姓名：</td>
			  <td align="left" nowrap class="bg_table02">
			  		<s:property  value="per.name" />             </td>
			  			
		  </tr>
			<tr align="center">
	  <td align="right" nowrap class="bg_table02">手机：</td>
			  <td align="left" nowrap class="bg_table02">
			  		<s:property  value="per.callPhone" />              </td>	
              <td align="right" nowrap class="bg_table02">email：</td>
              <td align="left" nowrap class="bg_table02">
              		<s:property  value="per.email" /></td>
             
		  </tr>
		  
			<tr align="center">
					  <td align="right" nowrap class="bg_table02">固定电话：</td>
			  <td align="left" nowrap class="bg_table02"><s:property  value="per.phone" /></td>
			  <td align="right" nowrap class="bg_table02">职责：</td>
			  <td align="left" nowrap class="bg_table02">
				<s:iterator value="dutyList"  >
				<s:if test="id == per.position">
					<s:property value="organizationName" />					
				</s:if>
				</s:iterator>
			  </td>
		  </tr>
		  
		<tr align="center">
		
			  	  <td align="right" nowrap class="bg_table02">其他联系方式：</td>
			  <td align="left" nowrap class="bg_table02"><s:property  value="per.otherPhone" /></td>
			   <td align="right" nowrap class="bg_table02">&nbsp;</td>
			   <td align="right" nowrap class="bg_table02">&nbsp;</td>
		  </tr>
		  
			<tr align="center">
 			<td colspan="6" align="left" nowrap class="bg_table02">	<hr></td>
        </tr>
</table>

	<table id="newClient" width="100%"  border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
             <tr>
			  <td colspan="2" align="right" nowrap class="bg_table02" clspan="7"><div align="center" class="STYLE1">
			    <div align="left">关联客户</div>
			  </div></td>
			  <td colspan="3" align="left" nowrap class="bg_table02"></td>
             </tr>			
					<tr align="center">
						<td width="13%" class="bg_table01">编号</td>
						<td width="28%" class="bg_table01">客户名称</td>
						<td width="17%" class="bg_table01">客户性质</td>
						<td width="18%" class="bg_table01">行业类型</td>
					</tr>
					<s:iterator value="empCliInfo.result" status="status">
					<tr align="center">						
						<td width="13%" class="bg_table02"><s:property value="#status.index + 1" /></td>
						<td width="28%" class="bg_table02"><s:property value="cli.name" /></td>
						<td width="17%" class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1001,cli.clientNID).typeName" /></td>
						<td width="18%" class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1002,cli.businessID).typeName"/></td>						
					</tr>
					</s:iterator>
				</table>
		<table width="100%">
			<tr class="bg_table03" align="center" style="height:42px">
				<td colspan="4" nowrap>
				<Table style="width:0%;100%">
					<tfoot class="bg_table03" style="height:42px">
						<tr>
							<td> 
								<input type="button" class="button01" value=" 关  闭 " onclick="window.close()" />
							</td>
						 </tr>
					</tfoot>
				</Table>
				</td>
			</tr>
		</table>
	  </td>
	</tr>
</table>
</s:form>
</body>
</html>