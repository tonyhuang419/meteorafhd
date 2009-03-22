<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>多部门合同拆分表</title>

<style type="text/css">
.tdw{
	padding-top:10px;
	padding-bottom: 10px;	
}
.info{
	font-size: 18;
}
</style>
<script language="javascript">

</script>
</head>
<BODY>
<div align="center" style="font-size: 18;font-weight: bold; margin-top: 10px;color: 000">
多部门合同拆分表
</div>
<br>
<table width="100%" border="1"   bordercolor="#808080" style=" border-collapse: collapse;">
 <tr>
 	<td width="10%" align="left" nowrap class="info">合同名称：</td>
 	<td align="left" class="info" ><s:property value="gcmi.conName"/></td>
 </tr>
  <tr>
 	<td align="left" nowrap class="info">客户名称：</td>
 	<td align="left" class="info"><s:property value="client.fullName"/></td>
  </tr>
  <tr>
 	<td align="left" nowrap class="info">销售员：</td>
 	<td align="left" class="info"><s:property value="employee.name"/></td>
 </tr>
</table>

<table width="100%" border="1"   bordercolor="#808080" style=" border-collapse: collapse;">
 <tr>
 	<td class="info">合同总价（含税）：
    <s:property value="gcmi.conTaxTamount"/></td><td class="info">合同总价（不含税）：
      <s:property value="gcmi.conNoTaxTamount"/></td>
 </tr>
 <s:iterator id="fee" value="gmainMoneyList">
 	<tr>
 		<td  width="40%" align="left" nowrap class="info"><s:property value="typeManageService.getYXTypeManage(1012,#fee.moneytype).typeName"/>：</td>
	  <td align="left" nowrap class="info"><s:property value="#fee.money"/></td>
 	</tr>
 </s:iterator>
</table>

<table width="100%" border="1"   bordercolor="#808080" style=" border-collapse: collapse;">
<tr>
			<td width="25%" align="center" nowrap class="info">
				部门			</td>
	  <td  align="center" class="info">
				拆分金额			</td>
	  <td align="center" class="info">
				领导签字			</td>
  </tr>
<s:iterator id="itemInfo" value="itemInfolist">
		<tr>
			<td nowrap align="left" width="25%" height="50" class="info">
			  <s:property  value="typeManageService.getYXTypeManage(1018,#itemInfo[0]).typeName" />
			</td>
	  <td  align="left" class="info">
	<s:property value="#itemInfo[1]" />
			</td>
		  <td width="30%"  align="left" class="info">&nbsp;</td>
	</tr>
</s:iterator>
</table>

</body>
</html>
