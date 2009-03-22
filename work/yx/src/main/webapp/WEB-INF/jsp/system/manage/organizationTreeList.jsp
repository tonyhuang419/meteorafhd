<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>Import Management</title>

<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src='..//js/function.js'></script>
<script type="text/javascript" src='..//js/page.js'></script>
<script type="text/javascript" src='..//js/jquery.js'></script>
<script type="text/javascript" src='..//js/jquery.tablesorter.js'></script>
<script type="text/javascript" src='..//js/sorttable.js'></script>
<script type="text/javascript" src='..//js/YS_checkboxHelper.js'></script>

</head>
<body leftmargin="0">
<s:form name="organizationTree" action="organizationTreeQuery" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td align="center"> 
				<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
					<tr>
						<td width="80%" align="left" >当前页面:权限管理->用户组织树管理</td>
					</tr>
					
				</table>
				<table align="center" border=1  cellpadding="1" cellspacing=1 width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
		    		<tr align="center">
		      			<td width="30%" class="bg_table01">组织名称</td>
						<td width="30%" class="bg_table01">组织代码</td>
						<td width="40%" class="bg_table01">操作</td>
		    		</tr>
		    		<s:iterator value="info.result">
		    			<tr align="center">
							<td  align="left" ><s:property value="organizationName" /></td>
							<td align="left"><s:property value="organizationCode" /></td>
							<td align="left">
								<a href="<s:url action='organizationTree'><s:param name='organizationId'><s:property value="id" /></s:param><s:param name='method'>addChildNode</s:param></s:url>">新增下级组织结点</a>
								
								<s:if test="organizationCode!='10'">
									<a href="javascript:deleteOrganization(<s:property value="id" />);">删除</a>
								</s:if>
							</td>
						</tr>
		    		</s:iterator>
				</table>
			</td>
		</tr>
		<tr>
			<td>
			<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info" beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			</td>
		</tr>
	</table>
	</s:form>
</body>
</html>




<script language="javascript">
function back()
{
  location.href="../personnelManager/selectPerQuery.action?method=doDefault";
}
function openUrl(url)
{
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}

function deleteOrganization(id)
{
	if(confirm("确定删除该组织吗？"))
	{
		//window.location="<s:url action='organizationTree'><s:param name='organizationId'>"+id+"</s:param><s:param name='method'>deleteNode</s:param></s:url>";
		window.location="/yx/system/manage/organizationTree.action?method=deleteNode&organizationId="+id;
	}
	
}
<s:if test="#parameters.hasChild[0] == 'true'">alert("有子节点，不能删除");</s:if>
<s:if test="#parameters.hasEmployee[0] == 'true'">alert("有员工引用该节点，不能删除");</s:if>
</script>
