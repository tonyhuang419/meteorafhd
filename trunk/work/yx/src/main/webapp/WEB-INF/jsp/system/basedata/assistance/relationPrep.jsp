<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>建立关联</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
	<script type="text/javascript">
		function searchApply(){
			var conNo = document.systemOpAssistance.assistanceConNo.value;
			if(conNo!=null&&conNo.length>0){
				document.systemOpAssistance.submit();
			}
		}
	</script>
</head>
<body>
<s:form action="systemOpAssistance" theme="simple">
	<s:hidden name="method" value="searchRelationPayInfo"></s:hidden>
	<s:hidden name="assistanceContract.id"></s:hidden>
	<table align="center" width="100%">
	<tr>
		<td>
			<table>
				<tr>
					<td>
					外协合同号：
					</td>
					<td>
						<s:textfield name="assistanceConNo"></s:textfield>
					</td>
					<td><input type="button" value="搜索" class="button01" onclick="searchApply();"/></td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
	</s:form>
</body>
</html>