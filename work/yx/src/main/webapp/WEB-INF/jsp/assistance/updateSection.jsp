<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>修改外协合同阶段信息</title>
</head>
<body>
<s:form action="assistance" theme="simple">
<s:hidden name="method" value="updateSee"></s:hidden>
<input type="hidden" name="sectionId" value="<s:property value="as.id"/>"/>
	<table width="100%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td height="3" align="left"><p>当前页面：外协管理->外协合同阶段修改</p></td>
      </tr>
      </table>
	<table width="90%" border="0" cellspacing="1" cellpadding="1">
		<tr class="bg_table02">
			<td>阶段名称:<s:property value="as.id"/></td>
			<td><s:textfield name="as.sectionName"/></td>
		</tr>
		<tr class="bg_table02">
			<td>阶段金额:</td>
			<td><s:textfield name="as.sectionAmount"/></td>
		</tr>
		<tr class="bg_table02">
			<td>预计收款日期:</td>
			<td>
	       		 <input type="text" id="sectionDate" name="as.sectionBillTime" value="<s:property value="as.sectionBillTime"/>"  size="12" />
		  		<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sectionDate')" align=absMiddle alt=""  />
			</td>
		</tr>
		<tr class="bg_table02">
			<td colspan="2" align="center">
				<input type="submit" name="save" class="button01" value="保存" />
				<input type="button" name="close" class="button01" value="关闭" onclick="window.close()"/>
				
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>