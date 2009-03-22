<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>到款信息</title>

<script language="javascript">

</script>

<style type="text/css">

</style>
</head>
<body>

<s:form action="hisReve" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<div align="left">
   <div  style="color:#000000"><p>
   当前页面：首页提醒->到款信息
</p></div>
</div>

<table width="100%" border="1"   id="recordItemNo" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr>
          <td  nowrap   class="bg_table01">
              <div align="center">合同号</div>
            </td>
          <td  nowrap   class="bg_table01">
        	  <div align="center">项目号</div>
          </td>
          <td   nowrap  class="bg_table01">
              <div align="center">合同名称</div>
           </td>
           <td   nowrap  class="bg_table01">
              <div align="center">客户名称</div>
           </td>
           <td   nowrap  class="bg_table01">
              <div align="center">销售员</div>
           </td>
          <td   nowrap   class="bg_table01">
              <div align="center">到款金额</div>
            </td>
          <td    nowrap  class="bg_table01">
              <div align="center">到款方式</div>
            </td>
         <td    nowrap  class="bg_table01">
              <div align="center">到款日期</div>
            </td>
        </tr>
        
        <s:iterator value="info.result" id="hisReve" status="status">
        <tr  onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
           <td><div align="left"><s:property value="#hisReve[0].conId"/></div></td>   
           <td><s:property value="#hisReve[1]"/></td>
           <td><div align="left"><s:property value="#hisReve[0].conName"/></div></td>
           <td><div align="left"><s:property value="#hisReve[4]"/></div></td>
           <td><div align="left"><s:property value="#hisReve[3]"/></div></td> 
           <td><div align="right"><s:property value="#hisReve[2].amount"/></div></td>
           <td><div align="left"><s:property value="typeManageService.getYXTypeManage(1017,#hisReve[2].receType).typeName"/></div></td>
           <td><div align="center"><s:property value="#hisReve[2].amountTime"/></div></td>
        </tr>
        </s:iterator>
        <tr>
        	<td colspan="5"><div align="right"><font style="font-weight:bold">总计：</font></div></td>
        	<td><div align="right">
        	 <s:if test="reveSum==null">
         	 	0.00
         	 </s:if>
         	 <s:else>
        		<s:property value="reveSum"/>
        	</s:else>
        	</div></td>
        	<td colspan="2"></td>
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
