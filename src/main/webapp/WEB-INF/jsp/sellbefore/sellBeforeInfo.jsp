<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>查看售前合同明细</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="showSellBefore" theme="simple">
<div align="left" style="color:#000000">首页：售前合同>售前合同明细</div>
<s:iterator value="info.result" id="sellBefore">
	<table width="95%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td valign="top" align="center">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td class="bg_table01" height="1"><img
						src="${IMG_PATH}/temp.gif" width="1" height="1"></td>
				</tr>
			</table>
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td class="bg_table02" align="right">客户：</td>
					<td class="bg_table02"  align="left" >
						<s:property value="#sellBefore[1].name"/>
					</td>
					<td class="bg_table02" align="right">行业类别：</td>
					<td class="bg_table02" align="left">
						<s:property value="typeManageService.getYXTypeManage(1002,#sellBefore[0].businessTypeId).typeName"/>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">客户项目类型:</td>
					<td class="bg_table02" align="left">
						<s:property value="typeManageService.getYXTypeManage(1007,#sellBefore[0].customerProjectTypeId).typeName"/>
					</td>
					<td class="bg_table02" align="right">客户联系人:</td>
					<td class="bg_table02" align="left">
						<s:property value="#sellBefore[2]" />
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right" class="bg_table02">
					<hr>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">项目名称：</td>
					<td class="bg_table02" align="left">
						<s:property value="#sellBefore[0].projectName"/>
					<td class="bg_table02" align="right">项目跟踪状态：</td>
					<td class="bg_table02" align="left">
						<s:property value="typeManageService.getYXTypeManage(1009,#sellBefore[0].projectStateFollowId).typeName"/>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">项目主要内容:</td>
					<td class="bg_table02" align="left">
						<s:property value="#sellBefore[0].mainProjectContent"/>
			  	</td>
					<td class="bg_table02" align="right">项目跟踪情况说明:</td>
					<td class="bg_table02" align="left">
						<s:property value="#sellBefore[0].descProjectFollow"/>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">项目类型:</td>
					<td class="bg_table02" align="left">
						<s:property value="typeManageService.getYXTypeManage(1008,#sellBefore[0].projectTypeId).typeName"/>
					</td>
					<td class="bg_table02" align="right">项目状况:</td>
					<td class="bg_table02" align="left">
						<s:property value="typeManageService.getYXTypeManage(1006,#sellBefore[0].projectStateId).typeName"/>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">工程责任部门:</td>
					<td class="bg_table02" align="left">
						<s:property value="typeManageService.getYXTypeManage(1018,#sellBefore[0].dutyDepartmentId).typeName"/>
					</td>
					<td class="bg_table02" align="right">项目负责人:</td>
					<td class="bg_table02" align="left">
						<s:property value="#sellBefore[0].projectManId"/>
					</td>
				</tr>
				<tr align="center">
					<td colspan="4" align="right" class="bg_table02">
					<hr>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" width="15%" align="right">预计项目金额：</td>
					<td class="bg_table02" width="35%" align="left">						
						<s:property value="#sellBefore[0].projectSum"/>
					</td>
					<td class="bg_table02" align="right">投标（报价）金额:</td>
					<td class="bg_table02" align="left">
						<s:property value="#sellBefore[0].bidSum"/>
				    </td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">中标（合同）金额:</td>
					<td class="bg_table02" align="left">
						<s:property value="#sellBefore[0].ownSum"/>
					</td>
					<td class="bg_table02" align="right">投标（报价）日期:</td>
					<td class="bg_table02" align="left">
			  			<s:property value="#sellBefore[0].bidDate"/>
			  		</td>	
				</tr>
				<tr align="center">
					<td class="bg_table02" width="15%" align="right">项目跟踪起始日期:</td>
					<td class="bg_table02" align="left">
			  			<s:property value="#sellBefore[0].projectDate"/>
			  		</td>	
					<td class="bg_table02" align="right">项目计划投运日期:</td>
					<td class="bg_table02" align="left">
			  			<s:property value="#sellBefore[0].estimateProjectDate"/>
			  		</td>	
				</tr>
				<tr align="center">
					<td class="bg_table02" width="15%" align="right">预计合同签订日期:</td>
					<td class="bg_table02" align="left">
					  	<s:property value="#sellBefore[0].estimateSignDate"/>
					 </td>	
					<td class="bg_table02" align="right">中标概率：</td>
					<td class="bg_table02" align="left">
						<s:property value="#sellBefore[0].ownProbability"  />
					
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" width="15%" align="right">中标厂家：</td>
					<td class="bg_table02" width="35%" align="left">
						<s:property value="#sellBefore[0].ownFactory"/>
					</td>
					<td class="bg_table02" align="right">项目跟踪结束：</td>
					<td class="bg_table02" align="left">
						<s:if test="#sellBefore[0].projectStateFollowId = 'on'">
							<s:property value="" default="on"/>
						</s:if>
						<s:else>
							<s:property value="" default="off"/>
						</s:else>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">竞争厂家概述：</td>
					<td class="bg_table02" align="left">					
						<s:property value="#sellBefore[0].competeInfo"/>
					 </td>
					<td class="bg_table02" align="right">备注：</td>
					<td class="bg_table02" align="left"><s:property value="#sellBefore[0].remark"/></td>
				</tr>
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<Table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="right" colspan="2"><input type="button" class="button01" onclick="window.close()"  value="  确 定  " />
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
	</s:iterator>
</s:form>
</body>
</html>