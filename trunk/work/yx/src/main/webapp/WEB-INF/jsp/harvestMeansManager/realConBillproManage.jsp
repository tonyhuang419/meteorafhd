<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>收款计划管理</title>
<script type="text/javascript">
	function updateEnter(oid){
		var url="realConBillpro.action?method=updateEnter&realConBillproSid="+oid;
		openWin(url,700,600);
		//window.open(url);
	}
	function refresh()
	{
		document.realConBillpro.submit();
	}
</script>
</head>
<body leftmargin="0">
<s:form action="realConBillpro" theme="simple">
  <div align="left" style="color:#000000">当前页面:收款管理->收款计划管理</div>

		<table align="center" border=1 cellpadding="1" cellspacing=1
			width="100%" id="family" bordercolor="#808080"
			style="border-collapse: collapse;">
			<tr align="center">
				<td class="bg_table01">合同号</td>
				<td class="bg_table01">合同名称</td>
				<td class="bg_table01">项目号</td>
				<td class="bg_table01">项目负责部门</td>
				<td class="bg_table01">计划收款日期</td>
				<td class="bg_table01" >计划收款金额</td>
				<td class="bg_table01">状态</td>
				<td class="bg_table01" nowrap="nowrap">操作</td>
			</tr>
			<s:iterator value="info.result" id="results" status="resuIndex">
			<tr onMouseOver="this.bgColor='#BBBBFF'; "
						onMouseOut="this.bgColor='#FFFFFF';">
				<td><s:property value="#results[0]"/></td>
				<td><a href="#" onclick="openCon(<s:property value="#results[4].conMainInfoSid"/>)" ><s:property value="#results[1]"/></a></td>
				<td><s:property value="#results[2]"/></td>
				<td><s:property value="#results[3]"/></td>
				<td align="center"><s:property value="#results[4].realPredReceDate"/></td>
				<td align="right"><s:property value="#results[4].realReceAmount"/></td>
				<td>
				<s:if test="#results[4].realArriveAmount==null">
					未收款
				</s:if>
				<s:elseif test="#results[4].realTaxReceAmount!=#results[4].realArriveAmount">
				部分收款
				</s:elseif>
				<s:elseif test="#results[4].realTaxReceAmount==#results[4].realArriveAmount">
				全额收款
				</s:elseif>
				</td>
				<td align="center" >
				<s:if test="#results[4].realTaxReceAmount!=null&&#results[4].realTaxReceAmount==#results[4].realArriveAmount">
				&nbsp;
				</s:if>
				<s:else>
				<a href="javascript:updateEnter('<s:property value="#results[4].realConBillproSid"/>')">修改</a>
				</s:else>
				</td>
			</tr>
			</s:iterator>
		</table>

		<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="realConBillpro" /></td>				
				</tr>
		</TABLE>

</s:form>
</body>
</html>