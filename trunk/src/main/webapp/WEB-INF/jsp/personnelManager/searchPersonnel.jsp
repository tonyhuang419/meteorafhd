<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>员工信息查询</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="selectPerQuery" theme="simple" target="rightFrame">
	<s:hidden name="resetCondition" value="true"/>
	<table height="40%"  border=0  cellpadding=1 cellspacing=0>
          <tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5">
            	<img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
       	  <tr class="bg_table02">
			      <td align="center" nowrap>用户名:</td>
		          <td class="bg_table02" align="left" >
		          	<s:textfield name="userName" cssClass="input" size="12"></s:textfield>
		         </td>
	      </tr>
	      <tr class="bg_table02">
			      <td align="center" nowrap>员工名称:</td>
		          <td class="bg_table02" align="left" >
		          	<s:textfield name="clientName" size="12"></s:textfield>
			</td>
	      </tr>
	       <tr class="bg_table02">
			      <td colspan="2" align="center" nowrap> 
			      	<input class="button01" type="submit" value=" 查  询 " /> 
			      </td>
	      </tr>
	 </table>
	</s:form>
</body>
</html>