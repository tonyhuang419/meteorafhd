<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>新签合同信息</title>

<script language="javascript">

</script>

<style type="text/css">

</style>
</head>
<body>

<s:form action="newSignContract" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<div align="left">
   <div  style="color:#000000"><p>当前页面：首页提醒->新签合同信息</p></div>
</div>
<table width="100%" border="1"   id="recordItemNo" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr>
          <td  nowrap   class="bg_table01">
              <div align="center">合同号</div>
            </td>
          <td  width="25%" nowrap  class="bg_table01">
              <div align="center">合同名称</div>
            </td>
          <td   nowrap  class="bg_table01">
              <div align="center">客户名称</div>
            </td>
          <td   nowrap   class="bg_table01">
              <div align="center">销售员</div>
            </td>
          <td    nowrap  class="bg_table01">
              <div align="center">合同性质</div>
           </td>
         <td    nowrap  class="bg_table01">
              <div align="center">合同金额</div>
          </td> 
          <td   nowrap   class="bg_table01">
              <div align="center">签订日期</div>
          </td>
          <td   nowrap   class="bg_table01">
              <div align="center">转正日期</div>
          </td>
           <td   nowrap   class="bg_table01">
              <div align="center">结束日期</div>
          </td>
        </tr>
        
        <s:iterator value="info.result" id="newSignContract" status="status">
        <tr  onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
          <td><div align="left"><s:property value="#newSignContract[0].conId"/></div></td>

          <td><div align="left"><s:property value="#newSignContract[0].conName"/></div></td>
          <td><div align="left"><s:property value="#newSignContract[2]"/></div></td>
          <td><div align="left"><s:property value="#newSignContract[1]"/></div></td>
        <td><div align="left">
           <s:property value="typeManageService.getYXTypeManage(1019,#newSignContract[0].conType).typeName"/>      
          </div></td>
       <td><div align="right">
           <s:property value="#newSignContract[0].conTaxTamount"/>      
          </div></td> 
        <td><div align="center">
           <s:property value="#newSignContract[0].conSignDate"/>      
          </div></td> 
        <td><div align="center">
           <s:property value="#newSignContract[0].activeDate"/>      
          </div></td> 
        <td><div align="center">
           <s:property value="#newSignContract[0].conEndDate"/>      
          </div></td> 
        </tr>
        </s:iterator>
       <tr>
         	 <td class="stat" align="right" colspan="5"><font style="font-weight:bold">总计：</font></td>       	
         	 <td class="stat"  align="right">
         	 <s:if test="sumNewSignContract==null">
         	 	0.00
         	 </s:if>
         	  <s:else>
        		<s:property value="sumNewSignContract" /> 
        	 </s:else>
        	</td>
        	 <td class="stat" align="right" colspan="3">&nbsp;</td>    
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
