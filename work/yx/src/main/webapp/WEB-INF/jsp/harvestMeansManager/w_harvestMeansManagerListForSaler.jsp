<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>

<html>
<head>
<title>收款管理列表</title>
<script type="text/javascript">

</script>


</head>
<body leftmargin="0">
  <div align="left" style="color:#000000">当前页面:收款管理->收款管理</div>
<s:form action="w_HarvestMeansSearch" theme="simple"> 
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	</table>	
	<table align="center" border=1  width="100%"   bordercolor="#808080" style=" border-collapse: collapse;">
		<tr align="center">
			<td nowrap width="15%" class="bg_table01">合同名称</td>
			<td nowrap width="10%" class="bg_table01">合同号</td>
			<td nowrap width="10%" class="bg_table01">项目号</td>
			<td nowrap width="8%" class="bg_table01">开票总金额</td>
			<td nowrap width="8%" class="bg_table01">收据总金额</td>
			<td nowrap width="8%" class="bg_table01">应收总金额</td>
			<td nowrap width="8%" class="bg_table01">票据余额</td>
		</tr>
	<s:iterator value="info.result" id="invoice"> 
		<tr align="center"  onMouseOver="this.bgColor='#BBBBFF';" onMouseOut="this.bgColor='#FFFFFF';">
			<td><div align="left"><s:property value="#invoice[0]"/></div></td>
			<td><div align="left"><s:property value="#invoice[1]"/></div></td>
			<td><div align="left"><s:property value="#invoice[2]"/></div></td>
			<td><div align="right">
			<s:set name="billAmount" value="#invoice[3]"></s:set>
			<s:if test="#invoice[3]==null">
				0.00
				<s:set name="billAmount" value="0.0"></s:set>
			</s:if>
			<s:else>
				<s:property value="#invoice[3]"/>
			</s:else>
			 </div></td>
			 
		 <td><div align="right">
		 	<s:set name="receiptAmount" value="#invoice[7]"></s:set>
			<s:if test="#invoice[7]==null">
				0.00
				<s:set name="receiptAmount" value="0.0"></s:set>
			</s:if>
			<s:else>
				<s:property value="#invoice[7]"/>
			</s:else>
			</div></td>
			
			 <td><div align="right">
			 <s:set name="shouldReveAmount" value="#invoice[8]"></s:set>
			 <s:if test="#invoice[8]==null">
				0.00
				<s:set name="shouldReveAmount" value="0.0"></s:set>
			</s:if>
			<s:else>
				<s:property value="#invoice[8]"/>
			</s:else>
			 </div></td>
			
			<td><div align="right">
			<s:set name="reveAmount" value="#invoice[4]"></s:set>
			 <s:if test="#invoice[4]==null">
				<s:set name="reveAmount" value="0.0"></s:set>
			 </s:if>	
			<s:property value="#shouldReveAmount -#reveAmount"/>
			</div></td>
		</tr>
	   </s:iterator>
    </table>
    <table width="100%" border="0">
        <tr valign="top">
          <td class="bg_table04">
         	 <baozi:pages value="info" beanName="info" formName="forms(0)" />
          </td>
        </tr>
     </table>
</s:form>

<script type="text/javascript">


</script>
</body>
</html>