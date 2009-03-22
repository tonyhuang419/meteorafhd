<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>售前合同统计</title>
</head>

<script type="text/javascript">

function popQuery(){
	window.open('../statistics/sellBeforeStat.action?method=searchCondition');
}

function exportInfo(){
	var formX = document.getElementById("export");
	formX.submit();
}
function openSellCon(id){
	openWin2("../sellbefore/showSellBefore.action?method=showInfo&comeFrom=stat&id="+id,800,600,'cbsDetail');
}
</script>
<body>
<s:form action="sellBeforeStat"  theme="simple" >
  <s:hidden name="method" value="queryResult" ></s:hidden>
	<div align="left" style="color: #000000">
	<p>当前页面：统计查询 -&gt; 售前合同统计</p>
	</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%"
		class="bg_table03">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr>
			<td align="center">
			<input type="button" onclick="popQuery()" value="查  询" class="button01" />
			<s:if test="info.result.size() > 0 ">
				<input type="button" value=" 导  出 " onclick="exportInfo();" class="button01" />
				</s:if>
			</td>
		</tr>
	</table>
		<s:if test="info!=null">
		<table align="center" border="1" cellpadding="1" cellspacing="1" width="100%"  bordercolor="#808080" style="border-collapse: collapse;">
			<tr align="center" class="bg_table01">
				<td nowrap>销售员</td>
				<td nowrap>客户简称</td>
				<td nowrap>客户性质</td>
				<td nowrap>项目名称</td>
				<td nowrap>重点项目</td>
				<td nowrap>预计金额</td>
				<td nowrap>工程编号</td>
				<td nowrap>跟踪状态</td>
				<td nowrap>最后更新日期</td>
				<td nowrap>结果</td>
			</tr>
			<s:set name="expectTotal" value="0.00" />
			<s:iterator value="info.result" id="info" status="status">
				<tr class="bg_table02">
					<td><s:property value="#info[10]" /></td>  
					<td><s:property value="#info[9]" /></td>
					<td><s:property value="#info[8]"/></td>  
					<td><a href="#" onclick="openSellCon('<s:property value="#info[11].longValue()"/>')" >
					<s:property value="#info[1]" /></a></td>
					<td align="center">
						<s:if test="#info[2] == 0 ">
							N
						</s:if>
						<s:if test="#info[2] == 1 ">
							Y
						</s:if>
					</td>  
					<td align="right"><s:property value="#info[3]" /></td>
					<td align="right"><s:property value="#info[4]" /></td>
					<td><s:property value="#info[5]"/></td>
					<td align="center"><s:property value="#info[6]" /></td>
					<td>
						<s:if test="#info[7] == 1">
							ON
						</s:if>
						<s:if test="#info[7] == 2">
							OFF
						</s:if>
					</td>

					<s:if test="#info[3]!=null">
						<s:set name="expectTotal" value="#info[3] + #expectTotal"/>
					</s:if>
				</tr>
			</s:iterator>
			<tr class="bg_table02">
				<td align="right" colspan="7"><b>本页小计：</b></td>
				<td align="right"><s:property value="#expectTotal" /></td>
				<td colspan="4"></td>
			</tr>
			<tr class="bg_table02">
				<td align="right" colspan="7"><b>累计总计：</b></td>
				<td align="right">
				<s:if test="expectedSum==null">
					0.00
				</s:if>
				<s:else>
					<s:property value="expectedSum" />
				</s:else>
				</td>
				<td colspan="4"></td>
			</tr>
			<tr class="bg_table04">		
				<td colspan="12" align="center"><table cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04">
								<baozi:pages value="info" beanName="info" formName="forms(0)" />
							</td>
						</tr>
					</table></td>				
			</tr>
		</table>
		</s:if>
</s:form>
<s:form id="export">
	<s:hidden name="method" value="queryResult"></s:hidden>
	<s:hidden name="exportX" value="1" />
</s:form>

</body>
</html>