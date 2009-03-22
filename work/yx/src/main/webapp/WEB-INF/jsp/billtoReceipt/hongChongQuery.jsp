<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>退票管理查询</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<body leftmargin="0">
<iframe name="errorsFrame" frameborder="0" width="100%"
				framespacing="0" height="0" scrolling="no"></iframe></div>
<s:form action="hongChongQuery" theme="simple" target="rightFrame">
	<s:hidden name="method" value="showHongChongBill"/>
	<s:hidden name="resetCondition" value="true"/>
	<table width="100%"  class="bg_table02" >
          <tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5">
            	<img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
		<tr class="bg_table02">
			<td align="right">合同号：</td>
			<td>
			<s:textfield name="contractNo"/>
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="right">项目号：</td>
			<td>
			<s:textfield name="itemNo"/>
			</td>
		</tr>
		<tr class="bg_table02">
			<td align="right">发票号：</td>
			<td>
			<s:textfield name="invoiceNo"/>
			</td>
		</tr>
	       <tr class="bg_table04">
			      <td colspan="2" align="center" nowrap> 
			      	<input class="button01" type="submit" value=" 查  询 " /> 
			      </td>
	      </tr>
	      
	 </table>
	</s:form>
</body>
</html>