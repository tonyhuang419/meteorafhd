<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<title>新签合同统计</title>
</head>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
function popQuery(){
	window.open("../statistics/formalContractStat.action?method=popQuery");
}

function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
	
}
		
</script>
<body>
<s:form action="formalContractStat" theme="simple">
  <s:hidden name="method" value="queryList" ></s:hidden>
	<div align="left" style="color: #000000">
	<s:hidden name="isFromCustom"/>
	<p>当前页面：统计查询 -> 新签合同统计</p>
	</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%"
		class="bg_table03">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr>
		<s:if test="isFromCustom == null">
			<td align="center"><input type="button" onclick="popQuery()"
				value="查  询" class="button01" />
				<s:if test="info.result.size() > 0">
					<input type="button" onclick="exportInfo()" value="导  出" class="button01" />
				</s:if>
			</td>
		</s:if>		
		</tr>
	</table>
	<s:if test="info!=null">
		<table align="center" border=1 cellpadding="1" cellspacing=1
			width="100%" id="mouthlyBill" bordercolor="#808080"
			style="border-collapse: collapse;">
			<tr align="center" class="bg_table01">
				<td nowrap="nowrap">销售员</td>
				<td nowrap="nowrap">合同号</td>
				<td nowrap="nowrap">项目号</td>
				<td nowrap="nowrap">合同名称</td>
				<td nowrap="nowrap">合同性质</td>
				<td nowrap="nowrap">合同金额</td>
				<td nowrap="nowrap">合同不含税金额</td>
				<td nowrap="nowrap">客户简称</td>
				<td nowrap="nowrap">项目部门</td>
				<td nowrap="nowrap">项目含税金额</td>
				<td nowrap="nowrap">项目不含税金额</td>
				<td nowrap="nowrap">转正式合同日期</td>
			</tr>
			<s:set name="subTotalTaxAmount" value="0.00"></s:set>
			<s:set name="subTotalNoTaxAmount" value="0.00"></s:set>
			<s:set name="subTotalItemMoney" value="0.00"></s:set>
			<s:set name="subTotalNoxItemMoney" value="0.00"></s:set>
			<s:set name="conid" value = "" ></s:set>
			<s:iterator value="info.result" id="info">
				<tr class="bg_table02">
					<td nowrap="nowrap"><s:property value="#info[0]" /></td>
					<td><s:property value="#info[1]" /></td>
					<td><s:property value="#info[2]" /></td>
					<td><a href="#" onclick="openCon('<s:property value="#info[10].longValue()"/>')" ><s:property value="#info[3]" /></a></td>
					<td ><s:property value="typeManageService.getYXTypeManage(1019,#info[12]).typeName" /></td>
					<s:if test="#conid != #info[10] ">
					<td align="right" ><s:property value="#info[4]" /></td>
					<td align="right" ><s:property value="#info[5]" /></td>
					</s:if><s:else>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					</s:else>
					<td ><s:property value="#info[6]" /></td>
					<td >
					<s:property value="typeManageService.getYXTypeManage(1018,#info[7]).typeName" />
					</td>
					<td align="right" ><s:property value="#info[8]" /></td>
					<td align="right" ><s:property value="#info[11]" /></td> <!-- 不含税金额 -->
					<td align="center" ><s:property value="#info[9]" /></td> 
				</tr>
				    <s:if test="#conid != #info[10] " >
					<s:set name="subTotalTaxAmount" value="#subTotalTaxAmount+#info[4]"></s:set>
					<s:set name="subTotalNoTaxAmount" value="#subTotalNoTaxAmount+#info[5]"></s:set>
					</s:if>
					<s:set name="conid" value="#info[10]"></s:set>
					<s:if test="#info[8]!=null">
					<s:set name="subTotalItemMoney" value="#subTotalItemMoney+#info[8]"></s:set>
					</s:if>
					<s:if test="#info[11]!=null">
						<s:set name="subTotalNoxItemMoney" value="#subTotalNoxItemMoney+#info[11]"></s:set>
					</s:if>
					
			</s:iterator>
						<tr class="bg_table02">
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="right">本次小计：</td>
					<td align="right"><s:property value="#subTotalTaxAmount" /></td>
					<td align="right"><s:property value="#subTotalNoTaxAmount" /></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="right"><s:property value="#subTotalItemMoney" /></td>
					<td align="right"><s:property value="#subTotalNoxItemMoney" /></td>
					<td align="center"></td>
			</tr>
			
			<tr class="bg_table02">
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="right">全部累计：</td>
					<s:iterator value="totalContractInfo" id="totalContractInfo">
					<td align="right"><s:property value="#totalContractInfo[0]" /></td>
					<td align="right"><s:property value="#totalContractInfo[1]" /></td>
					</s:iterator>
					<td align="center"></td>
					<td align="center"></td>
					<s:iterator value="totalInfo" id="totalInfo">
					<td align="right"><s:property value="#totalInfo" /></td>
					</s:iterator>
					<td align="right">
						<s:iterator value="totalNoxInfo" id="totalNoxInfo">
								<s:property value="#totalNoxInfo" />
						</s:iterator>
					</td>
					<td align="center"></td>
			</tr>
           
			<tr class="bg_table04">
			    <s:hidden name="resetCondition" value="false"/>
				<td colspan="12" align="center"><baozi:pages value="info"
					beanName="info" formName="forms(0)" /></td>
			</tr>
		 <s:if test="isFromCustom != null">
		 		<s:hidden name="customId" ></s:hidden>
		 		<tr class="bg_table04">
				<td colspan="11" align="center"><input type="button" onclick="window.close();"
				value="关  闭" class="button01" /></td>
			</tr>
		</s:if>	
		</table>
	</s:if>
</s:form>
<s:form id="export"  >
  <s:hidden name="method" value="queryList" ></s:hidden>
	<s:hidden name="isExport" value="true" />
</s:form>

</body>

</html>