<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<title>应收款统计</title>
<script type="text/javascript">
	function showQueryList(){
		openWin("../statistics/receivableStatisticsQuery.action?method=showQueryList",700,500);
	}
	function showInfo(type,min,max){
		if(type == 1){
			location.href="../statistics/receivableStatisticsQuery.action?method=queryResult&resetCondition=true&minRealMonthAccountAge="+min+"&maxRealMonthAccountAge="+max;
		}
		else{
			location.href="../statistics/receivableStatisticsQuery.action?method=queryResult&resetCondition=true&minLogicMonthAccountAge="+min+"&maxLogicMonthAccountAge="+max;
		}
	}
</script>
<body>
<s:form action="receivableStatisticsQuery" theme="simple">
<div align="left" style="color:#000000">当前页面:统计查询—>按帐龄范围汇总统计</div>
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
			<tr>
				<td colspan="8" align="right" class="bg_table01" height="3">
					
				</td>	
			</tr>
		<tr>
			<td align="center" class="bg_table03">
				<input type="button" value=" 查  询 " name="1" onclick="showQueryList()" class="button01" />
			</td>
		</tr>
	</table>
		<table align="center" id="infoX" border=1 cellpadding=1 cellspacing=1 width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
			<tr align="center" class="bg_table01">
				<td class="bg_table01" nowrap="nowrap">类型</td>
				<td class="bg_table01" nowrap="nowrap">日期</td>
				<td class="bg_table01" nowrap="nowrap">总计</td>
				<td class="bg_table01" nowrap="nowrap"> &lt;0个月</td>
				<td class="bg_table01" nowrap="nowrap"> 0-3个月</td>
				<td class="bg_table01" nowrap="nowrap">4-6个月</td>
				<td class="bg_table01" nowrap="nowrap">7-12个月</td>
				<td class="bg_table01" nowrap="nowrap">12个月以上</td>
			</tr>
			<tr class="bg_table02">
				<td>实际帐龄</td>
				<td align="center"><s:property value="todayTime"/></td>
				<td align="right">
					<a href="#" onclick="showInfo(1,null,null)"><s:property value="billService.calRealTotal(null,null)"/></a>
				</td>
				<td align="right">0.00</td>
				<td align="right">
					<a href="#" onclick="showInfo(1,0,3)"><s:property value="billService.calRealTotal(0,3L)"/></a>
				</td>
				<td align="right">
					<a href="#" onclick="showInfo(1,4,6)"><s:property value="billService.calRealTotal(4L,6L)"/></a>
				</td>
				<td align="right">
					<a href="#" onclick="showInfo(1,7,12)"><s:property value="billService.calRealTotal(7L,12L)"/></a>
				</td>
				<td align="right">
					<a href="#" onclick="showInfo(1,13,null)"><s:property value="billService.calRealTotal(13L,null)"/></a>
				</td>
			</tr>
			<tr class="bg_table02">
				<td>逻辑帐龄</td>
				<td align="center"><s:property value="todayTime"/></td>
				<td align="right"><a href="#" onclick="showInfo(2,null,null)"><s:property value="billService.calLogicTotal(null,null)"/></a></td>
				<td align="right"><a href="#" onclick="showInfo(2,null,-1)"><s:property value="billService.calLogicTotal(null,-1)"/></a></td>
				<td align="right"><a href="#" onclick="showInfo(2,0,3)"><s:property value="billService.calLogicTotal(0,3)"/></a></td>
				<td align="right"><a href="#" onclick="showInfo(2,4,6)"><s:property value="billService.calLogicTotal(4,6)"/></a></td>
				<td align="right"><a href="#" onclick="showInfo(2,7,12)"><s:property value="billService.calLogicTotal(7,12)"/></a></td>
				<td align="right"><a href="#" onclick="showInfo(2,13,null)"><s:property value="billService.calLogicTotal(13,null)"/></a></td>
			</tr>
		</table>
</s:form>
</body>
</html>