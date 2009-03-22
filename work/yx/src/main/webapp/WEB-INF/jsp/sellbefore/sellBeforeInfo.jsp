<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>查看售前合同明细</title>
</head>
<body>
<s:form action="showSellBefore" theme="simple">
<div align="left" style="color:#000000"><br/>
<s:if test="comeFrom.equals('stat')">
当前页面：统计查询->售前合同统计->售前合同明细
</s:if>
<s:else>
当前页面：售前合同->售前合同明细
</s:else>
</div>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td class="bg_table01" height="1"><img width="1" height="3"></td>
				</tr>
			</table>
			<table width="100%" border="1" cellspacing="1" cellpadding="1" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center" class="bg_table02">				
					<td align="right" width="15%" >客户名称：</td>
					<td align="left" width="30%" >
						<s:property value="obj[2]"/>
					</td>
					<td align="right" width="10%" >行业/性质：</td>
					<td align="left" width="*%">
							<s:property value="typeManageService.getYXTypeManage(1002,obj[1].businessID).typeName"/>/
							<s:property value="typeManageService.getYXTypeManage(1001,obj[1].clientNID).typeName"/>
					</td>
				</tr>
				<tr  class="bg_table02">
					<td align="right">客户项目类型：</td>
					<td align="left">
						<s:property value="typeManageService.getYXTypeManage(1007,obj[0].customerProjectTypeId).typeName"/>		
					</td>
					<td align="right">客户联系人：</td>
					<td align="left">
						<s:property value="obj[3]" />
						&nbsp;&nbsp;电话：<s:property value="obj[0].customerLinkManPhone"/>
					</td>
				</tr>
				
				<tr align="center" class="bg_table02">
					<td align="right">项目名称：</td>
					<td align="left">
					<s:property value="obj[0].projectName"/></td>
					<td align="right">工程名称：</td>
					<td align="left">
					<s:property value="obj[0].projectNameX" />
					</td>
				</tr>
				<tr align="center" class="bg_table02">
					<td align="right">甲方项目工程编号：</td>
					<td align="left">
						<s:property value="obj[0].partyAProId" />
			  		</td>
			  		<td align="right">甲方项目进度：</td>
					<td align="left">
					<s:property value="typeManageService.getYXTypeManage(1006,obj[0].projectStateId).typeName"/></td>
				</tr>
				<tr align="center" class="bg_table02">
					<td align="right">项目概况：</td>
					<td align="left">
						<s:property value="obj[0].mainProjectContent"/>
			  		</td>
			  		<td align="right">工程概况：</td>
					<td align="left">
					<s:property value="obj[0].projectSummary"/></td>
				</tr>
				
				<tr class="bg_table02">
					<td align="right">预计金额：</td>
					<td align="left">						
						<s:property value="obj[0].projectSum"/>
					</td>
					<td align="right">计划投运日期：</td>
					<td align="left">
			  		<s:property value="obj[0].estimateProjectDate" />
			  		</td>	
				</tr>
				
	<%--
				<tr align="center">
					<td align="right">联系人部门：</td>
					<td align="left">
						<s:textfield name="cbs.customerDepartment"/>
					</td>
					<td></td>
				</tr>
 --%>
 				<tr align="center" class="bg_table02">
					<td colspan="4" align="right">
					&nbsp;
					</td>
				</tr>	
				
				<tr class="bg_table02">
					<td align="right">纳入重点工程：</td>
					<td align="left">
					<s:if test="obj[5].id!=null">
						<s:property value="obj[5].projectNum"/>/<s:property value="obj[5].projectName"/></td>
					</s:if>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				
				<tr class="bg_table02">
					<td align="right" >重点项目：</td>
					<td align="left">
						<s:if test="obj[0].importantItem ==0 ">&times;</s:if>
          				<s:elseif test="obj[0].importantItem ==1"> &radic; </s:elseif>
					</td>
					<td width="15%" align="right">项目跟踪起始日期：</td>
					<td align="left">
			  		<s:property value="obj[0].projectDate"  /></td>
				</tr>
				<tr class="bg_table02">
					<td align="right">工程责任部门：</td>
					<td align="left">
						<s:property value="typeManageService.getYXTypeManage(1018,obj[0].dutyDepartmentId).typeName"/>					
					</td>
					<td align="right">项目负责人：</td>
					<td align="left" >
						<s:property value="obj[0].projectManId" />
						&nbsp;&nbsp;电话：<s:property value="obj[0].itemRespeoplePhone" />
					</td>
				</tr>
				
				<tr class="bg_table02">
					<td align="right">报价（投标）金额：</td>
					<td align="left">
						<s:property value="obj[0].bidSum"/>
				    </td>
				    <td align="right">中标（合同）金额：</td>
					<td align="left">
						<s:property value="obj[0].ownSum"  />
					</td>
				</tr>
				
				<tr class="bg_table02">
					<td align="right" nowrap>预定报价（投标）日期：</td>
					<td align="left">
			  			<s:property value="obj[0].bidDate" />
			  		</td>	
			  		<td width="15%" align="right" nowrap>预计合同签订日期：</td>
					<td align="left">
			  		<s:property value="obj[0].estimateSignDate"  /></td>	
				</tr>
				
				<tr class="bg_table02">
			  		<td align="right">项目跟踪状态：</td>
					<td align="left">			
						<s:property value="typeManageService.getYXTypeManage(1009,obj[0].projectStateFollowId).typeName"/>
					</td>
					<td align="right">项目跟踪结果：</td>
					<td align="left">
					<s:if test="obj[0].projectStateSelect==1">
						on
					</s:if>
					<s:else>
						off
					</s:else>
					<s:property value="typeManageService.getYXTypeManage(1026,obj[0].itemTraceResult).typeName"/>
					<s:if test="obj[0].projectStateSelect==1">
						&nbsp;
					</s:if>
					<s:else>
						原因：<s:property value="obj[0].itemTraceResultReason" />
					</s:else>
					</td>
				</tr>

				<tr class="bg_table02" align="center">
					<td colspan="6" align="right">
					&nbsp;
					</td>
				</tr>	

				 <tr class="bg_table02">
				 	<td align="right">项目跟踪情况说明：</td>
					<td align="left" colspan="5">
						<s:property value="obj[0].descProjectFollow"/>
			  		</td>
				 </tr>
				 <%-- 
				<tr align="center">
					<td align="right">中标厂家：</td>
					<td align="left" colspan="3">
						<s:textfield name="cbs.ownFactory" maxlength="25" />
					</td>
				</tr>
				--%>
				<tr class="bg_table02" align="center">
					<td align="right">竞争厂家概述：</td>
					<td align="left" colspan="5">					
						<s:property value="obj[0].competeInfo"/>
					 </td>
				</tr>
				<tr class="bg_table02">
					<td align="right">备注：</td>
					<td align="left" colspan="5">
						<s:property value="obj[0].remark"/>
					</td>
				</tr>
			
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="6">
					<Table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
							<td align="right" colspan="2">
							<s:if test="comeFrom.equals('stat')">
								<input type="button" onclick="viewModHis();" class="button01" value="修改记录" />
							</s:if>
							<s:if test="comeFrom.equals('mod')">
								<input type="button" onclick="goBack();" class="button01" value="返  回" />
							</s:if>
							<s:else>
								<input type="button" class="button01" onclick="window.close()"  value="关  闭" />
							</s:else>
							</td>
							</tr>
						</tfoot>
					</Table>
					</td>
				</tr>
			</table>
</s:form>
</body>
<script type="text/javascript">
<s:if test="comeFrom.equals('stat')">
	function viewModHis(){
		openWin2("../sellbefore/showSellBefore.action?method=showHistory&id="+<s:property value="obj[0].ID"/>,800,600,"cbsHistory");
	}
</s:if>
	function goBack(){
		window.location.href("/yx/sellbefore/selectSellBefore.action");
	}
</script>
</html>