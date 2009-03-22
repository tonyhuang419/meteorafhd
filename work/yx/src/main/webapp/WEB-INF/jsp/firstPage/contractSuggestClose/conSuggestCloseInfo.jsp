<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>合同建议关闭</title>

<script language="javascript">

</script>

<style type="text/css">

</style>
</head>
<body>

<s:form action="contractSuggestClose" theme="simple" target="content">
<input type="hidden" name="method" value="right"/>
<div align="left">
   <div  style="color:#000000"><p>
   当前页面：首页提醒->合同建议关闭
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
          <td   nowrap  class="bg_table01">
              <div align="center">合同性质</div>
           </td>  
          <td   nowrap   class="bg_table01">
              <div align="center">合同金额</div>
            </td>      
           <td   nowrap  class="bg_table01">
              <div align="center">签订日期</div>
           </td>

        </tr>
        
        <s:iterator value="info.result" id="conSuggestClose" status="status">
        <tr  onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
        <td><div align="left"><s:property value="#conSuggestClose[0].conId"/></div></td>   
		<td><div align="left">
            <s:iterator id="s" value="itemNoList.get(#conSuggestClose[0].conMainInfoSid)"  status="stat2"  >    
            	<s:property value="s"/><s:if test="!#stat2.last"><br/></s:if>
            </s:iterator>
		</div></td>   
		<td><div align="left"><s:property value="#conSuggestClose[0].conName"/></div></td>   
		<td><div align="left"><s:property value="#conSuggestClose[2]"/></div></td>
		<td><div align="left"><s:property value="#conSuggestClose[1]"/></div></td>     
		<td><div align="left"><s:property value="typeManageService.getYXTypeManage(1019,#conSuggestClose[0].conType).typeName"/></div></td>
		 <td><div align="right">
		 <s:if test="#conSuggestClose[0].standard == 1" >
			 <s:property value="#conSuggestClose[0].conTaxTamount"/>   
		 </s:if>
		 <s:else>
		 	 <s:property value="#conSuggestClose[0].conNoTaxTamount" />
		</s:else>
		</div></td>
		 <td><div align="center"><s:property value="#conSuggestClose[0].conSignDate"/></div></td>   
        </tr>
        </s:iterator>
      </table>

  <table cellSpacing=1 cellPadding=2 width="100%" border=0>
	<tr>
		<td class="bg_table04"><baozi:pages value="info"  beanName="info" formName="forms(0)" /></td>
	</tr>
</table>

</s:form>

</body>
</html>
