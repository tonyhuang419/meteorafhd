<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>未签合同到款</title>

<script language="javascript">

</script>

<style type="text/css">

</style>
</head>
<body>

<s:form action="noContractReve" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<div align="left">
   <div  style="color:#000000"><p>当前页面：首页提醒->未签合同到款</p></div>
</div>
<table width="100%" border="1"   id="noContractReveInfo" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr class="bg_table01">
          <td  nowrap>
              <div align="center">销售员</div>
          </td>
          <td  nowrap>
              <div align="center">客户名称</div>
          </td>
          <td  width="15%">
              <div align="center">收款金额</div>
          </td>
          <td  nowrap>
              <div align="center">收款日期</div>
          </td>
          <td  nowrap>
              <div align="center">收款状态</div>
          </td>
         <td width="10%">
              <div align="center" >是否预收款</div>
          </td>
          <td width="25%">
              <div align="center">备注</div>
          </td>
        </tr>
        
        <s:iterator value="info.result" id="noContractReve" status="status">
        <tr onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
        	<td align="left"><s:property value="#noContractReve[1]" /></td>
			<td align="left"><s:property value="#noContractReve[2]" /></td>
			<td align="right"><s:property value="#noContractReve[0].recevieAmount" /></td>
			<td align="center"><s:property value="#noContractReve[0].recevieDate" /></td>
			<td align="left">
			<s:if test="#noContractReve[0].state == 0">
				未核销
			</s:if>
			<s:elseif test="#noContractReve[0].state == 1">
				已核销
			</s:elseif>
			<s:elseif test="#noContractReve[0].state == 2">
				历史核销
			</s:elseif>
			</td>
			<td align="center">
			  <s:if test="#noContractReve[0].isPerArrive == 0">
                 	否
              </s:if>
              <s:elseif test="#noContractReve[0].isPerArrive == 1">
               	     是
             </s:elseif>
			</td>
			<td align="center"><s:property value="#noContractReve[0].remark" /></td>
       	 </tr>
        </s:iterator>
        <tr>
        	<td colspan="2" align="right">总计：</td>
        	<td align="right">
        	<s:if test="sumNoContract==null">
         	 	0.00
         	 </s:if>
         	 <s:else>
        		<s:property value="sumNoContract"/>
        	</s:else>
        	</td>
        </tr>
      </table>

  <table cellSpacing=1 cellPadding=2 width="100%" border=0>
	<tr>
		<td class="bg_table04"><baozi:pages value="info"  beanName="info" formName="forms(0)" /></td>
	</tr>
</table>

</s:form>

</body>
</html>
