<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>角色列表</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<style type="text/css">
table {
	border: 1px solid black;
	border-collapse: collapse;
}
table thead tr th {
	border: 1px solid black;
	padding: 3px;
	background-color: #cccccc;
	background-color: expression(this.rowIndex%2==0?"#FFFFFF":"#EEEEEE");
}
table tbody tr td {
	border: 1px solid black;
	padding: 3px;
}
.trs {
	background-color: expression(this.rowIndex%2==0?"#FFFFFF":"#EEEEEE");
}
</style>
</head>
<body>
<s:form action="user" theme="simple">
	<table cellspacing="0" align="center">
		<thead>
			<tr>
				<th>选择</th>
				<th>角色</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator id="role" value="roles">
				<tr class="trs">
					<td><input type="checkbox" id="ids" name="ids" value="<s:property value="#role[0]" />" <s:if test="#role[2]!=0">checked="true"</s:if>></td>
					<td><s:property value="#role[1]" /></td>
				</tr>
			</s:iterator>
		</tbody>
		<tr>
			<td colspan="3" align="center"><input type="button" onclick="setIds();" value="选择"></td>
		</tr>
	</table>
</s:form>
</body>
</html>
<script type="text/javascript">
function setIds(){
 	var ids="";
 	$A($N("ids")).each(function(node){
 		if(node.checked){
 			ids=ids+node.value+","; 		
 		}
 	});
	window.opener.getRoleIds(ids);
	window.close();
}
</script>