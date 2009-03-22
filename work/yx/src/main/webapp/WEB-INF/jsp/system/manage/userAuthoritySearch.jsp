<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>

<head>
<title>用户权限查询</title>
</head>
<body leftmargin="0">
<s:form action="userAuthorityQuery" theme="simple" target="content">
	<s:hidden name="resetCondition" value="true"/>
	<s:hidden name="method" value="quryList"/>
	<table width="100%" class="bg_table02">
          <tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5">
            	<img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
       	  <tr>
			      <td align="center" nowrap>用户名：</td>
		          <td class="bg_table02" align="left" >
		          	<s:textfield name="userName"  size="12"></s:textfield>
		         </td>
	      </tr>
	      <tr>
			      <td align="center" nowrap>员工名称：</td>
		          <td class="bg_table02" align="left" >
		          	<s:textfield name="clientName" size="12"></s:textfield>
			</td>
	      </tr>
	       <tr class="bg_table04">
			      <td colspan="2" align="center" nowrap> 
			      	<input class="button01" type="submit" value="查  询" />
			      </td>
	      </tr>
	 </table>
	</s:form>
</body>
</html>