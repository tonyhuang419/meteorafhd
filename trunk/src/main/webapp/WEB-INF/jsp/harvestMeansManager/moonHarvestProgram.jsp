<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>

<link href="./css/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
   function yearMonthChange(){
  	 document.forms(0).submit();
   }
   function generateMonthlyPlan(){
   	if(confirm("确定要生成本月月度计划吗？")){
   		document.forms(0).action="/yx/harvestMeansManager/moonHarvestProgram.action?method=generateMonthPlan";
   		document.forms(0).submit();
   	}
   }
</script>

</head>
<body>
<s:form action="moonHarvestProgram" theme="simple">
<s:hidden name="resetCondition" value="true"/>
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				
				  <tr><td height="3" align="left" >当前页面：收款管理->月度计划收款管理</td></tr>
			<tr class="bg_table03">
             <td  align="left" class="bg_table03">
				
				&nbsp;&nbsp;&nbsp;&nbsp;<s:select name="year" onchange="yearMonthChange()" list="yearMap" >
					
					
					
				</s:select>年
       	 	   <s:select name="month" list="{'01','02','03','04','05','06','07','08','09','10','11','12'}" onchange="yearMonthChange()">
				</s:select>月	
				<td align="left" class="bg_table03">&nbsp; <input type="button" onclick="generateMonthlyPlan()" name="button" id="button" value="生成本月收款计划" class="button02">
			</td>	
       	 	 </td>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">
				<tr align="center">
				
					<td class="bg_table01">合同号</td>
					<td class="bg_table01">客户名称</td>
					<td class="bg_table01">负责部门</td>
					<td class="bg_table01">计划收款日期</td>
					<td class="bg_table01">计划金额</td>
					<td class="bg_table01">计划状态</td>
				</tr>

				<s:iterator value="info.result" id="monthlyPlan">
					<tr align="center">
				
					   <td><s:property value="#monthlyPlan[0]" /></td>
						<td><s:property value="#monthlyPlan[1]"/></td> 
						<td><s:property value="typeManageService.getYXTypeManage(1018,#monthlyPlan[2]).typeName" /></td>
						<td><s:date name="#monthlyPlan[3]" format="yyyy-MM-dd" /></td>
					    <td><s:property value="#monthlyPlan[4]" /></td>
						<td><s:property value=""/></td> 
					</tr>
				</s:iterator>
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			</td>
		</tr>
	</table>
</s:form>
<script type="text/javascript">
<s:if test="#generateSuccess == true">alert("月度计划生成成功");</s:if>
<s:if test="#repeatGenerate == true">alert("不能重复生成本月月度计划")</s:if>
</script>
</body>
</html>
