<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Statistics.Dept</title>
</head>

<body>
<table id="DeptStatistics" border="1" align="CENTER"
	bordercolor="#000000">
	<tr>
		<td>单位（万元）
		<td></td>
	<tr align="center">
		<th width="100">部门</th>
		<th width="100">指标</th>
		<th width="100">完成总额</th>
		<th width="100">完成比例</th>
		<th width="100">差额</th>
	</tr>
	<s:bean id="deptOrder" name="statistics.dept.DeptCompator" />
	<s:sort source="#session.statistics_dept_list" comparator="#deptOrder">
		<s:iterator id="list">
			<tr align="center">
				<td><s:property value="#list.deptName" /></td>
				<td><s:property value="#list.target" /></td>
				<td><s:property value="#list.accomplish" /></td>
				<td><s:property value="#list.precentage" /></td>
				<td><s:property value="#list.balance" /></td>
			</tr>
		</s:iterator>
	</s:sort>
</table>
</body>
</html>