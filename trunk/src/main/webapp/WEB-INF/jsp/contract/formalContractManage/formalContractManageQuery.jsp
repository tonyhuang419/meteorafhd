<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>正式合同管理搜索清单</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
<script language="javascript">

</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style>
</head>
<body leftmargin="0">
<div  align="left" style="color:#000000">当前页面：合同管理 -&gt; 正式合同管理 </div>
<s:form action="formalContractManageQuery" theme="simple">
<s:hidden name="start_date" />
<s:hidden name="end_date"/>
<s:hidden name="conState" />
<s:hidden name="conStateSn"/>
<s:hidden name="saleId"/>
<s:hidden name="customerId"/>
<s:hidden name="conType" />
<s:hidden name="groupId" />
<s:hidden name="conSn" />
<s:hidden name="finalAccount" />
<s:hidden name="contractType" />
<table width="130%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center" >
  <tr>
    <td align="center"></td>
  </tr>
  <tr>
    <td align="center"><table width="100%" border="0" align="center" id="formalCon">
        <tr>
          <td width="9%"  class="bg_table01"><div align="center"> 合同号 </div></td>
          <td width="14%"  class="bg_table01"><div align="center"> 合同名称 </div></td>
          <td width="14%"  class="bg_table01"><div align="center"> 客户名称 </div></td>
          <td width="8%"   class="bg_table01"><div align="center"> 销售员 </div></td>
          <td width="10%"   class="bg_table01"><div align="center"> 合同性质 </div></td>
          <td width="9%"   class="bg_table01"><div align="center"> 合同金额 </div></td>
          <td width="8%"   class="bg_table01"><div align="center"> 签订日期 </div></td>
          <td width="8%"  class="bg_table01"><div align="center"> 主项目负责部门 </div></td>
          <td width="6%"   class="bg_table01"><div align="center"> 合同状态 </div></td>
          <td width="7%"   class="bg_table01"><div align="center"> 预决算信息 </div></td>
          <td width="7%"   class="bg_table01"><div align="center"> 合同类型 </div></td>
        </tr>
        <s:iterator id="result" value="info.result">
          <tr align="center" onClick="openCon(this)" onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
            <s:hidden name="con_sid" value="${result[0].conMainInfoSid} "/>
            <td align="center"><s:property value="#result[0].conId" />  </td>
            <td align="center"><s:property value="#result[0].conName" /> </td>
            <td align="center"><s:property value="#result[1].name" /> </td>
            <td align="center"><s:property value="#result[3].name" />   </td>
            <td> <s:property value="typeManageService.getYXTypeManage(1019,#result[0].conType).typeName"/></td>
            <td align="center"><s:property value="#result[0].conTaxTamount" /> </td>
            <td align="center"><s:date name="#result[0].conSignDate" format="yyyy-MM-dd" /></td>
            <td align="center"><s:property value="typeManageService.getYXTypeManage(1018,#result[0].mainItemDept).typeName" /></td>
            <td align="center">
              <s:property value="foramlContractService.covConStateSnToName(#result[0].conState) "/> 
            </td>
             <td align="center">
           		<s:if test="#result[0].FinalAccount==0"> 非预决算 </s:if>
             	<s:else > 预决算 </s:else>
             </td> 
            <td align="center">
            	<s:property value="typeManageService.getYXTypeManage(1020,#result[0].ContractType).typeName" />
            </td> 
          </tr> 
        </s:iterator>
      </table></td>
  </tr>
  <tr>
    <td height="20" class="bg_table02">
    <table width="100%" border="0">
        <tr valign="top">
          <td class="bg_table04">
          <baozi:pages value="info" beanName="info" formName="forms(0)" />
          </td>
        </tr>
      </table>
      </td>
  </tr>
</table>
</s:form>
</body>
</html>