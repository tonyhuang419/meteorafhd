<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>正式合同管理</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
<script language="javascript">

</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}

.stat{
	border-top:solid 2px;
	border-top-color:#808080;
	font-weight: bold;
}
-->
</style>
</head>
<body leftmargin="0">
<div  align="left" style="color:#000000">当前页面：合同管理 -&gt; 正式合同管理 </div>
<s:form action="formalContractManageQuery" theme="simple">
    <table width="100%" border="1" align="center" id="formalCon"  bordercolor="#808080" style=" border-collapse: collapse;">
        <tr>
          <td nowrap width="7%"  class="bg_table01"><div align="center"> 合同号 </div></td>
          <td  width="10%"  class="bg_table01"><div align="center"> 项目号 </div></td>
          <td nowrap width="14%"  class="bg_table01"><div align="center"> 合同名称 </div></td>
          <td nowrap width="10%"  class="bg_table01"><div align="center"> 客户名称 </div></td>
          <td nowrap width="5%"   class="bg_table01"><div align="center"> 销售员 </div></td>
          <td nowrap width="10%"   class="bg_table01"><div align="center"> 合同性质 </div></td>
          <td nowrap width="9%"   class="bg_table01"><div align="center"> 合同金额 </div></td>
          <td nowrap width="8%"   class="bg_table01"><div align="center"> 签订日期 </div></td>
      <%--     <td width="8%"  class="bg_table01"><div align="center"> 主项目负责部门 </div></td>   --%>
        
         <td nowrap width="5%"   class="bg_table01"><div align="center"> 开票总额 </div></td> 
         <td nowrap width="5%"   class="bg_table01"><div align="center"> 开收据额 </div></td>   
         <td nowrap width="5%"   class="bg_table01"><div align="center"> 到款总额 </div></td>  
         <td nowrap width="5%"   class="bg_table01"><div align="center"> 是否存在外协 </div></td>  
         
	 <%-- 
         <td nowrap width="5%"   class="bg_table01"><div align="center"> 合同状态 </div></td>  --%> 
           
       <%--    <td width="7%"   class="bg_table01"><div align="center"> 预决算信息 </div></td>    --%>
      <%--     <td nowrap width="7%"   class="bg_table01"><div align="center"> 合同类型 </div></td>  --%>
        </tr>
        <s:iterator id="result" value="info.result" status="stat">
          <tr align="left" onClick="openCon(<s:property value="#result[0].conMainInfoSid" /> )" onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
           <%-- 
            <s:hidden name="con_sid" value="${result[0].conMainInfoSid} "/>
           --%>
            <td align="left"><s:property value="#result[0].conId" /></td>
            <td align="left">
            <s:iterator id="s" value="itemNoList.get(#result[0].conMainInfoSid)"  status="stat2"  >    
            	<s:property value="s"/><s:if test="!#stat2.last"><br/></s:if>
            </s:iterator>
            </td>
            <td align="left"><s:property value="#result[0].conName" /> </td>
            <td align="left"><s:property value="#result[1].name" /> </td>
            <td align="left"><s:property value="#result[3].name" /></td>
            <td> <s:property value="typeManageService.getYXTypeManage(1019,#result[0].conType).typeName"/></td>
            <td align="right">
            <s:if test="#result[0].standard == 1" >
              <s:property value="#result[0].conTaxTamount" />
            </s:if>
            <s:else>
              <s:property value="#result[0].conNoTaxTamount" />
            </s:else> </td>
            <td align="center"><s:date name="#result[0].conSignDate" format="yyyy-MM-dd" /></td>
       
      		 <td align="right">
      		 	<s:if test="#result[4]==null">
      		 		0.00
      		 	</s:if>
      		 	<s:else>
          	  		<s:property value="#result[4]" /> 
          	  	</s:else>
            </td>
          
          <td align="right">
            	<s:if test="#result[6]==null">
      		 		0.00
      		 	</s:if>
      		 	<s:else>
          	  		<s:property value="#result[6]" /> 
          	  	</s:else>  
            </td>
            
            <td align="right">
            	<s:if test="#result[5]==null">
      		 		0.00
      		 	</s:if>
      		 	<s:else>
          	  		<s:property value="#result[5]" /> 
          	  	</s:else>
            </td>
            <td align="center">
            	<s:if test="#result[0].ExistSidehelp == 1 ">
            		Y
            	</s:if>
            	<s:else>
            		&nbsp;
            	</s:else>
            </td>
        <%--        <td align="center"><s:property value="typeManageService.getYXTypeManage(1018,#result[0].mainItemDept).typeName" /></td> --%>
         <%--    <td align="center">
             <s:property value="foramlContractService.covConStateSnToName(#result[0].conState) "/>  
            </td>--%>
         <%--     <td align="center">
          		<s:if test="#result[0].FinalAccount==0"> 非预决算 </s:if>
             	<s:else > 预决算 </s:else>
             </td>   --%>
          <%--      <td align="left">
            	<s:property value="typeManageService.getYXTypeManage(1020,#result[0].ContractType).typeName" />
            </td>  --%>
          </tr> 
   </s:iterator>
      
    <%--    <s:if test="#stat.last && #stat.index > -1"> --%>
          <tr>
         	 <td  align="right" colspan="6"><font style="font-weight:bold">小计：</font></td>
         	 
         	 <td   align="right">
         	 <s:if test="sumContract==null">
         	 	0.00
         	 </s:if>
         	 <s:else>
        	 	<s:property value="sumContract" /> 
        	 </s:else>
        	 </td>
        	 
        	 <td  >&nbsp;</td>
        	
        	<td   align="right">
        	 <s:if test="billContract==null">
         	 	0.00
         	 </s:if>
         	 <s:else>
        	 <s:property value="billContract" /> 
        	 </s:else>
        	  </td>
        	  
        	  <td   align="right">
        	 <s:if test="receiptContract==null">
         	 	0.00
         	 </s:if>
         	 <s:else>
        	 <s:property value="receiptContract" /> 
        	  </s:else>
        	 </td>
        	 
        	 <td   align="right">
        	 <s:if test="reveContract==null">
         	 	0.00
         	 </s:if>
         	  <s:else>
        	 <s:property value="reveContract" /> 
        	 </s:else>
        	 </td>    	 
        	</tr> 
        	
        	<tr>
         	 <td  align="right" colspan="6"><font style="font-weight:bold">总计：</font></td>
         	
         	 <td   align="right">
         	 <s:if test="sumContractSum==null">
         	 	0.00
         	 </s:if>
         	  <s:else>
        	<s:property value="sumContractSum" /> 
        	 </s:else>
        	 </td>
        	 
        	 <td  >&nbsp;</td>
        	 
        	 <td   align="right">
        	 <s:if test="billContractSum==null">
         	 	0.00
         	 </s:if>
         	  <s:else>
        	 <s:property value="billContractSum" /> 
        	  </s:else>
        	  </td>
        	
        	<td   align="right">  
        	<s:if test="receiptContractSum==null">
         	 	0.00
         	 </s:if>
         	  <s:else>
        	 <s:property value="receiptContractSum" /> 
        	 </s:else>
        	 </td>
        	 
        	 <td   align="right">
        	 <s:if test="reveContractSum==null">
         	 	0.00
         	 </s:if>
         	  <s:else>
        	 <s:property value="reveContractSum" /> 
        	 </s:else>
        	 </td>
        	</tr> 
      <%--     </s:if> --%>
      
      </table>
      
    <table width="100%" border="0">
        <tr valign="top">
          <td class="bg_table04">
         	 <baozi:pages value="info" beanName="info" formName="forms(0)" />
          </td>
        </tr>
     </table>

</s:form>
</body>
</html>