<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>

<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">

</script>

<title>工程经济明细</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">

<style>
table#checkInfo tr:hover {
	background: lightblue;
	color: blue;
}
</style>

<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
</head>
<body>
<s:form action="" theme="simple">
	<s:hidden name="yxosi.economy.id" id="yid" />
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
					<td colspan="4" align="left">当前页面:工程经济->工程经济明细</td>
				</tr>
				<tr>
					<td colspan="4" align="right" class="bg_table01" height="0.5"><img
						src="./../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr align="center">
					<td colspan="4" align="right" class="bg_table02">
					<div align="center">主体信息</div>
					</td>
				</tr>
				<tr align="center">
					<td width="16%" height="20" align="right" class="bg_table02">售前合同号：</td>
					<td class="bg_table02" width="34%" align="left"><s:property
						value="proe.shellContractId" /></td>
					<td width="26%" align="right" class="bg_table02">项目名称：</td>
					<td width="24%" align="left" class="bg_table02"><s:property
						value="proe.projectName" /></td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">*工程编号：</td>
					<td class="bg_table02" align="left"><s:property
						value="proe.projectNumber" /></td>
					<td class="bg_table02" align="right">项目联系人（电话）：</td>
					<td class="bg_table02" align="left"><s:property
						value="proe.projectLMP" /></td>
				</tr>
				<tr align="center">
					<td height="21" align="right" class="bg_table02">项目编号（甲方）：</td>
					<td class="bg_table02" align="left"><s:property
						value="proe.projectNumberJ" /></td>
					<td class="bg_table02" align="right">投资匡算：</td>
					<td class="bg_table02" align="left"><s:property
						value="proe.investment" /></td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">客户项目单位：</td>
					<td class="bg_table02" align="left"><s:property
						value="proe.clientFactory" /></td>
					<td align="right" nowrap class="bg_table02">客户项目负责人（电话）：</td>
					<td class="bg_table02" align="left"><s:property
						value="proe.clientOrderP" /></td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">客户管理部门：</td>
					<td class="bg_table02" align="left"><s:property
						value="proe.clientManageDep" /></td>
					<td class="bg_table02" align="right">管理部门联系人（电话）：</td>
					<td class="bg_table02" align="left"><s:property
						value="proe.manageDepLMP" /></td>
				</tr>
				<tr>
					<td colspan="4" align="right" class="bg_table02">
					<hr>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="right" class="bg_table02">
					<div align="center">阶段信息</div>
					</td>
				</tr>
				<tr>
					<td class="bg_table02" align="right">阶段名称：</td>
					<td class="bg_table02" align="left">
						<s:property value="typeManageService.getYXTypeManage(1011,yxosi.sectionName).typeName" />
						</td>
					<td class="bg_table02" align="right">设计委托进度：</td>
					<td class="bg_table02" align="left"><s:date
						name="yxosi.designSpeed" format="yyyy-MM-dd" /></td>
				</tr>
				<tr>
					<td class="bg_table02" align="right">阶段投资估概算：</td>
					<td class="bg_table02" align="left"><s:property
						value="yxosi.investmentCount" /></td>
					<td class="bg_table02" align="right">&nbsp;</td>
					<td class="bg_table02" align="left">&nbsp;</td>
				</tr>
				<tr align="center">
					<td colspan="4" class="bg_table03">
					<div align="center"><span class="bg_blue07"> <input
						name="save2" type="button" class="button02"
						onclick="javascript:aaa();" value="确认通过"> <input
						name="save" type="button" class="button02"
						onclick="javascript:ccc();" value="确认退回"> <input
						name="save" type="button" class="button02"
						onClick="window.close();" value="关   闭"> </span></div>
					</td>
				</tr>
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>



				<tr valign="top">
					<td class="bg_table04"></td>
				</tr>
			</TABLE>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>
<script language="javascript">

function aaa() 
{ 
  var checkArr=document.getElementsByName("yxosi.id");
  var yid=document.getElementById("yid").value;
   var act="pass";
   opener.location.href="../programEconomy/programEconomy.action?method=verifyState2&action="+act+"&stateId="+yid;
   this.window.close();
} 
function ccc() 
{ 
  var checkArr=document.getElementsByName("ids");
   var yid=document.getElementById("yid").value;
   opener.location.href="../programEconomy/programEconomy.action?method=verifyState2&stateId="+yid;
   this.window.close();
} 


</script>
