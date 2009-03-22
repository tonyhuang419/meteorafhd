<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>

<html>
<head>
<title>正式合同管理</title>

<style type="text/css">
<!--

body {
	background-color: #FFFFFF;
}


-->
</style>

<script language="javascript">
window.onload=function(){
	var x = document.getElementById("receInfoTable");
	if( x!=null ){
		showtable2("receInfoTable");
		showtable2("assistanceInfo");
	}
}
</script>

</head>
<s:if test="conStat== 1">
	<script language="javascript"> 
		alert("申购保存成功");
	</script>
</s:if>
<s:elseif test="conStat== 2">
	<script language="javascript"> 
		alert("申购申请提交成功");
	</script>
</s:elseif>
<s:elseif test="conStat== 3">
	<script language="javascript"> 
		alert("外协保存成功");
	</script>
</s:elseif>
<s:elseif test="conStat== 4">
	<script language="javascript"> 
		alert("外协申请提交成功");
	</script>
</s:elseif>
<s:elseif test="conStat== 5">
	<script language="javascript"> 
		alert("合同已关闭");
	</script>
</s:elseif>
<s:elseif test="conStat== 6">
	<script language="javascript"> 
		alert("合同关闭失败,合同已被转为正式合同");
	</script>
</s:elseif>

<body>
<div  align="left" style="color:#000000">

<s:if test="whereCome == 4">
	当前页面：合同明细
</s:if>
<s:else>
	<s:if test="cmi.conState == 1 ">
		当前页面：合同管理->草稿合同明细
	</s:if>
	<s:elseif test="cmi.conState == 3 ">
		当前页面：合同管理->预合同明细
	</s:elseif>
	<s:else>
		当前页面：合同管理->正式合同管理 
	</s:else>
</s:else>

</div>

  <s:if test=" whereCome == 1"> 
  <s:form id="preparation" theme="simple">
 	<input type="hidden" name="groupId" value="<s:property value="groupId"/>"/>
  	<input type="hidden" name="expId" value="<s:property value="expId"/>"/>
	<input type="hidden" name="customerId" value="<s:property value="customerId"/>"/>
	<input type="hidden" name="conType" value="<s:property value="conType"/>"/>
	<input type="hidden" name="minMoney" value="<s:property value="minMoney"/>"/>	
	<input type="hidden" name="maxMoney" value="<s:property value="maxMoney"/>"/>
	<input type="hidden" name="minConSignDate" value="<s:property value="minConSignDate"/>"/>
	<input type="hidden" name="maxConSignDate" value="<s:property value="maxConSignDate"/>"/>
	<input type="hidden" name="conState" value="<s:property value="conState"/>"/>
	</s:form>
  </s:if>
  
  
<table width="100%"  border="0" align="center" cellpadding="1" cellspacing="1" class="bg_table02">
    		<tr>
              <td align="center" class="bg_table01"  ><table align="center" border=0 cellpadding="1" cellspacing="1" width="100%">
                </table></td>
            </tr>
  <tr class="bg_table02">
    <td colspan="4" align="center" class="bg_table02"><div id="container" class="bg_table02">
        
        <div align="center">         
          <table width="100%"  border="0" align="center" cellpadding="1" cellspacing="1" class="bg_table02">  
            <tr>
              <td align="center">
              <table align="center" border=0 cellpadding="1" cellspacing="1" width="100%"> </table>
              </td>
            </tr>

            <tr class="bg_table02">
              <td colspan="4" align="center" class="bg_table02">
                  <div id="title" class="bg_table02">
                    <div align="center" class="bg_table02">
                      <ul class="bg_table02">         
                        
                        <s:if test="cmi.conState < 4 ">
                          	<li id="tag1"><a href="#" onClick="switchTag2('tag1','content1');this.blur();"><span>主体信息</span></a></li>
                          	
                          	 <li id="tag2"><a href="#" onClick="switchTag2('tag2','content2');this.blur();"><span>合同阶段</span></a></li>
                        	<li id="tag3"><a href="#" onClick="switchTag2('tag3','content3');this.blur();"><span>项目信息</span></a></li>
                       		
                        	 <li id="tag4"><a href="#" onClick="switchTag2('tag4','content4');this.blur();"><span>原始开票收款计划</span></a></li>                                   	
                        	 <li id="tag5"><a href="#" onClick="switchTag2('tag5','content5');this.blur();"><span>自有产品</span></a></li>
                        	 <li id="tag6"><a href="#" onClick="switchTag2('tag6','content6');this.blur();"><span>资料要求</span></a></li>
                        </s:if>
                        
                        <s:else>
                        	<li id="tag1"><a href="#" onClick="switchTag('tag1','content1');this.blur();"><span>主体信息</span></a></li>
                        	
                        	<li id="tag2"><a href="#" onClick="switchTag('tag2','content2');this.blur();"><span>合同阶段</span></a></li>
                       		<li id="tag3"><a href="#" onClick="switchTag('tag3','content3');this.blur();"><span>项目信息</span></a></li>
                        	
                        	
                     	   <li id="tag4"><a href="#" onClick="switchTag('tag4','content4');this.blur();"><span>开票收款计划</span></a></li>
                    	    <li id="tag5"><a href="#" onClick="switchTag('tag5','content5');this.blur();"><span>开票信息</span></a></li>
                     	   <li id="tag6"><a href="#" onClick="switchTag('tag6','content6');this.blur();"><span>收款信息</span></a></li>
                     	   <li id="tag7"><a href="#" onClick="switchTag('tag7','content7');this.blur();"><span>申购信息</span></a></li>
                     	   <li id="tag8"><a href="#" onClick="switchTag('tag8','content8');this.blur();"><span>外协信息</span></a></li>
                     	   <li id="tag9"><a href="#" onClick="switchTag('tag9','content9');this.blur();"><span>自有产品</span></a></li>
                     	   <li id="tag10"><a href="#" onClick="switchTag('tag10','content10');this.blur();"><span>其它</span></a></li>
                        </s:else>
                        
                      </ul>
                    </div>
                  </div>
                </div>
                <!--contract main info start-->
                  <div id="content1" class="content1" >
      <table width="100%">
      <tr class="bg_table02">  
      <s:hidden id="contractmainsid" name="contractmainsid" value="${cmi.conMainInfoSid}" /><%--合同主体系统号--%>   
        <td align="right" width="20%">客户名称：</td>
        <td align="left" width="30%">
        <s:property value="foramlContractService.getCustomerFullName(cmi.conCustomer)" /></td>
        <td align="right">开票客户：</td>
        <td align="left">
         <s:property value="foramlContractService.getCustomerFullName(cmi.billCustomer)" /> <a href="javascript:showClientInfo();">查看详情</a>
        </td>
      </tr>
      <tr>
        <td align="right" class="bg_table02"> 项目单位： </td>
        <td align="left" class="bg_table02"> 
         <s:property value="foramlContractService.getCustomerFullName(cmi.itemCustomer)" /></td>
        <td width="20%" align="right" class="bg_table02">&nbsp;</td>
        <td width="30%" align="left" class="bg_table02">&nbsp;</td>
      </tr>
      <tr>
        <td align="right" class="bg_table02">客户联系人：</td>
        <td align="left" class="bg_table02">
        <s:property value="foramlContractService.getClientLinkManName(cmi.linkManId)" /></td>
        <td align="right" class="bg_table02">甲方合同号</td>
        <td align="left" class="bg_table02"><s:property value="cmi.partyAConId" /></td>
      </tr>
      <tr>
        <td align="right" class="bg_table02">客户项目类型：</td>
        <td align="left" class="bg_table02">
 		<s:property value="typeManageService.getYXTypeManage(1007,cmi.customereventtype).typeName"/></td>
        <td class="bg_table02" align="right">甲方的项目工程编号：</td>
        <td class="bg_table02" align="left"><div  style="word-break:break-all;">
            <s:property value="cmi.partyAProId" />
          </div></td>
      </tr>
      <tr>
        <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
      </tr>
      <tr>
        <td class="bg_table02" align="right">正式合同号：</td>
        <td align="left" class="bg_table02"><s:property value="cmi.conId" /></td>
        <td class="bg_table02" align="right">售前合同号：</td>
        <td align="left" class="bg_table02"><s:property value="cmi.preConSysid" /></td>
      </tr>
      <tr>
        <td class="bg_table02" align="right">合同名称：</td>
        <td align="left" class="bg_table02"><s:property value="cmi.conName" /></td>
        <td align="right" class="bg_table02">主项目部门：</td>
        <td align="left" class="bg_table02">
            <s:property value="typeManageService.getYXTypeManage(1018,cmi.mainItemDept).typeName" />
        </td>
      </tr>
      <tr>
        <td class="bg_table02" align="right">预决算信息：</td>
        <td class="bg_table02" align="left">
                              <s:if test="cmi.FinalAccount==0 ">非预决算</s:if>
                              <s:elseif test="cmi.FinalAccount ==1">预决算</s:elseif>
                               <s:else>状态出错</s:else>
</td>
        <td class="bg_table02" align="right">主项目负责人</td>
        <td class="bg_table02" align="left">
            <s:property value="cmi.mainItemPeople" />
        </td>
      </tr>
      <tr>
        <td class="bg_table02" align="right"> 合同类型：</td>
        <td class="bg_table02" align="left">
            <s:property value="typeManageService.getYXTypeManage(1020,cmi.ContractType).typeName "/>
        </td>
        
        <td align="right" class="bg_table02">合同性质：</td>
        <td align="left" class="bg_table02">
            <s:property value="typeManageService.getYXTypeManage(1019,cmi.conType).typeName "/></td>
  </tr>
  <tr>
			<td align="right" class="bg_table02"><div align="right">基准： </div></td>
               <td align="left" class="bg_table02">
               <s:if test ="cmi.standard == 1" >
               		含税
               </s:if>
               <s:elseif test ="cmi.standard == 2" >
               		不含税
               </s:elseif>              
               </td>
				<s:if test="whereCome == 1 || cmi.conState==1">		
						<td align="right" class="bg_table02">是否转为预合同：</td>
						<td align="left" class="bg_table02">
               		<s:if test ="cmi.beforeHandConState == false" >
               			否
	               </s:if>
	               <s:elseif test ="cmi.beforeHandConState == true" >
	               		是
	               </s:elseif> 
               </td>
               </s:if>
               <s:else>
	               	<td align="right" class="bg_table02"></td>
							<td align="left" class="bg_table02">
	               </td>
               </s:else>
      </tr>
      <tr>
			<td align="right" class="bg_table02">合同总金额：</td>
               <td align="left" class="bg_table02"><s:property value="cmi.conTaxTamount" /></td>
             <td align="right" class="bg_table02">合同不含税金额：</td>
               <td align="left" class="bg_table02"><s:property value="cmi.conNoTaxTamount" /></td>
      </tr>
    
	 <tr>
               <td class="bg_table02" align="right">货币单位：</td>
               <td class="bg_table02" align="left"> 
                <s:property value="typeManageService.getYXTypeManage(1015,cmi.copeck).typeName" /></td>
                <td align="right" class="bg_table02">基准汇率：</td>
                <td align="left" class="bg_table02"><s:property value="cmi.baserate" /></td>
                </tr>
      <tr>
        <td colspan="4" align="right" class="bg_table02"><hr align="right">        </td>
      </tr>
      <tr>
        <td class="bg_table02" align="right">合同签订日期：</td>
        <td class="bg_table02" align="left">
            <s:date name="cmi.conSignDate" format="yyyy-MM-dd" />
          </td>
        <td class="bg_table02" align="right">退税：</td>
        <td class="bg_table02" align="left">
            <s:if test="cmi.conDrawback ==0 ">&times;</s:if>
            <s:elseif test="cmi.conDrawback ==1"> &radic; </s:elseif>
          </td>
      </tr>
      <tr>
        <td align="right" class="bg_table02">合同起始日期：</td>
        <td align="left" class="bg_table02"><s:date name="cmi.conStartDate" format="yyyy-MM-dd" /></td>
        <td class="bg_table02" align="right">中标合同：</td>
        <td class="bg_table02" align="left">
            <s:if test="cmi.conBid == 0">&times;</s:if>
            <s:elseif test="cmi.conBid == 1"> &radic; </s:elseif>
          </td>
      </tr>
      <tr>
        <td align="right" class="bg_table02">合同结束日期： </td>
        <td align="left" class="bg_table02">
            <s:date name="cmi.conEndDate" format="yyyy-MM-dd" />
          </td>
        <td class="bg_table02" align="right">纳入年度运维合同：</td>
        <td class="bg_table02" align="left">
            <s:if test="cmi.IntoYearCon==0">&times;</s:if>
            <s:elseif test="cmi.IntoYearCon==1"> &radic; </s:elseif>
          </td>
      </tr>

      <tr>
        <td colspan="4" align="right" class="bg_table02"></td>
      </tr>
      <tr>
        <td align="right" class="bg_table02">开票状态：</td>
        <td class="bg_table02" align="left"><s:property value="contractBillState" /></td>
        <td align="right" class="bg_table02">收款状态：</td>
        <td align="left" class="bg_table02"><s:property value="contractReceState" /></td>
      </tr>
      <tr>
        <td align="right" class="bg_table02">外协状态：</td>
        <td class="bg_table02" align="left"><s:property value="outsourceState" /></td>
        <td align="right" class="bg_table02">合同状态：</td>
        <td align="left" class="bg_table02"><s:property value="foramlContractService.covConStateSnToName(cmi.conState)" /></td>
      </tr> 
      <tr>
       <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
        </tr>
        </table>
     
     <table align="center"   cellpadding="1" cellspacing=1 width="100%"   border="1"  bordercolor="#808080" style="border-collapse:collapse;">   
       <tr>
            <td align="center" class="bg_table01">编号</td>
               <td align="center" class="bg_table01">费用名称</td>
              <td align="center" class="bg_table01">金额</td>
              <td align="center" class="bg_table01">开票类型</td>
     </tr>
		<s:iterator value="mainMoneyList" status="mainMoneyList" >
 			<tr>
                  <td align="center"><s:property value="#mainMoneyList.index+1"/></td>
                  <td align="left"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/></td>
                  <td align="right"><s:property value="money"/></td>
                  <td align="left"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
 			 </tr>
		</s:iterator>
     
    </table>   
   </div>
                <!--contract main info end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract item info start-->
                <div id="content3"  class="hidecontent">
               <s:if test="cmi.ContractType == 1"   >
                <table align="center"   cellpadding="1" cellspacing=1 width="100%" id="item_info_table"   border="1"  bordercolor="#808080" style="border-collapse:collapse;">
                  <s:iterator id="infoPart" value="mainInfoPartList" status="partIndex">
					<tr>
					 <s:if test="cmi.conState > 3 ">
						<td width="5%" class="bg_table01" align="center">&nbsp;</td>
					</s:if>
						<td width="20%" class="bg_table01" align="center"><input type="hidden"
							name="partInfoList" value="<s:property value="id"/>" /> <s:property
							value="typeManageService.getYXTypeManage(1012,#infoPart.moneytype).typeName" />
						</td>
						<td width="25%" class="bg_table01" align="center">总金额：<s:property value="#infoPart.money"/></td>	
						<td width="25%" class="bg_table01" align="center">&nbsp;</td>
				   <s:if test="cmi.conState > 3 && whereCome != 4">
						<td width="25%" class="bg_table01" align="center">&nbsp;</td>
					</s:if>
					</tr>
				<tr>
				 <s:if test="cmi.conState > 3 ">
				 	<s:if test="whereCome != 4">
					<td  align="center">选择</td> 
					</s:if>
					<td  align="center">项目号</td>
				</s:if>	
					<td  align="center">工程部门</td>
					<td  align="center">负责人</td>
					<td   align="center">含税金额</td>
				</tr>
					<!--显示合同费用信息-->
					<!--显示项目费用和项目信息-->
					<s:iterator id="itemInfo" value="itemInfoList" status="itemInfoIndex">
						<tr>
						<s:hidden name="moneytype"  value="${infoPart.moneytype}"   />
							 <s:hidden name="itemsid" value="${itemMainInfo.conItemMinfoSid}" />
							 <s:hidden name="conItemId" value="${itemMainInfo.conItemId}" />
							 
					 <s:if test="cmi.conState > 3 "> 	
						 <s:if test="whereCome != 4">
							<td   align="center"> <input type="radio" name="item_info_radio"/></td>	
						</s:if>				
							<td   align="left"><s:property value="#itemInfo.itemMainInfo.conItemId" /></td>	
					</s:if>	
							<td   align="left">
							<s:property	 value="typeManageService.getYXTypeManage(1018,#itemInfo.itemMainInfo.itemResDept).typeName" />
							</td>
							<td  align="left"><s:property value="#itemInfo.itemMainInfo.itemResDeptP" /></td>
							<td  align="right"><s:property value="#itemInfo.conItemAmountWithTax" /></td>
						</tr>
					</s:iterator>
				</s:iterator>

                </table>
       			</s:if>
                </div>
                <!--contract item info end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract item stage start-->
                <div id="content2"  class="hidecontent">
                	
             <!--开票和收款阶段开始-->
            <table align="center"  cellpadding="1" cellspacing=1 width="100%"  border="1"  bordercolor="#808080" style="border-collapse:collapse;">
               <s:iterator value="mainMoneyWithPlanAmountList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01" colspan="5">
                  <div align="left"><s:property value="typeManageService.getYXTypeManage(1012,#maininfoPart[0].moneytype).typeName"/>&nbsp;&nbsp;&nbsp;&nbsp;总金额:<s:property value="#maininfoPart[0].money"/>
                	</div></td>
                </tr>

                  	<tr>
                  		<td align="center">阶段名称</td>
                  		<td  align="center">阶段金额</td>
                  		<td  align="center">收款金额</td>
                  		<td  align="center">开票类型</td>
                  		<td  align="center">预计开票日期</td>
                  	</tr>
                  	<s:iterator value="stagePlanlist">
                  		<s:if test="contractMaininfoPart.id == #maininfoPart[0].id">
	                  	<tr>
	                  		<td  align="left" ><s:property value="typeManageService.getYXTypeManage(1023,contractItemStage.itemStageName).typeName" />
	                  		<s:if test="stageRemark!=null && stageRemark!='' ">
	                  		（<s:property value="stageRemark"></s:property>）
	                  		</s:if>	
	                  		</td>	
	                  		<td  align="right" ><s:property value="stageAmout"></s:property></td>
	                  		<td align="right"><s:property value="reveAmount"></s:property></td>
	                  		<td align="left"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
	                  		<td align="center" ><s:property value="makeInvoiceDate"></s:property></td>
	                  	</tr>
	                  	</s:if>
                  	</s:iterator>

               </s:iterator>
              </table>
              </div>
              
               <!--contract rcplan start-->
              <s:if test="cmi.conState < 4 ">
 <div id="content4" class="hidecontent">
                <!--原始开票和收款计划开始-->
                 <table align="center"  cellpadding="1" cellspacing=1 width="100%"  border="1"  bordercolor="#808080" style="border-collapse:collapse;">
            <s:set name="planIndex" value="0"></s:set>
            <s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01" colspan="6"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/>——总金额：<s:property value="money"/></div></td>
                </tr>
                <tr>
                   <td  align="center" >合同阶段</td>
                   <td  align="center" >合同负责部门</td>
                   <td  align="center" >计划开票日期</td>
                   <td  align="center" >计划收款日期</td>
                   <td  align="center">开票金额</td> 
                   <td  align="center">收款金额</td>   
                </tr>
                <s:iterator value="initRcplan"  status="ilist">
                 <s:if test="moneytype == billNature">
	                <tr>
	                   <td align="left" ><s:property value="contractservice.findStageName(conItemStage)" /></td>
	                 
	                 <td align="left"  >
	                 <s:if  test="cmi.ContractType == 2"><%--集成类合同--%>
	                   		<s:property value="typeManageService.getYXTypeManage(1018,cmi.mainItemDept).typeName" />
	                   </s:if>
	                   <s:else>
	                   		<s:property value="contractservice.findDepName(conItemInfo)" />
	                   </s:else>
	                   </td>         
	                   <td align="center" ><s:property value="initBillDate"/></td>
	                    <td align="center" ><s:property value="initReceDate"/></td>
	                   <td align="right"  ><s:property value="initBillAmount"/></td>
	                    <td align="right" ><s:property value="initTaxReceAmount"/></td>
	                </tr>
	                <s:set name="planIndex" value="%{#planIndex+1}"></s:set>
                 </s:if>
                </s:iterator>
            </s:iterator>
       </table>
                </div>
			</s:if>

<s:else>
                <!--contract rcplan start-->
        
         		<s:form id="rcplanform" action="formalContractInvoiceApply" theme="simple" method="post">       	
       	 <!--   		<s:hidden id="billcustomersid" name="billcustomersid"  value="${cmi.billCustomer}" /><%--合同开票客户系统号--%>
        		 <s:hidden id="contractid" name="contractid" value="${cmi.conId}" /><%--合同号--%>
            	 <s:hidden id="rclist" name="rclist" value="" />   
		  -->       
                <div id="content4" class="hidecontent">

                <!--实际开票和收款计划开始-->
                 <table align="center"  cellpadding="1" cellspacing=1 width="100%"  border="1"  bordercolor="#808080" style="border-collapse:collapse;">
            <s:set name="planIndex" value="0"></s:set>
            <s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01" colspan="10"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/>——总金额：<s:property value="money"/></div></td>
                </tr>
                <tr align="center">
                <s:if test="whereCome != 4">
                   <td  nowarp>选择</td>
                 </s:if>
                   <td  nowarp>合同阶段</td>
                   <td  nowarp>合同负责部门</td>
                   <td  nowarp>计划开票日期</td>
                   <td  nowarp>计划收款日期</td>
                   <td  nowarp>开票类型</td>  
                   <td  nowarp>预计开票金额</td>   
                   <td  nowarp>预计收款金额</td>  
                   <td  nowarp>已开金额</td>
                   <td  nowarp>已到金额</td>   
                </tr>
                <s:iterator value="rcbrpList"  status="ilist">
                 <s:if test="moneytype == billNature">
	                <tr>
	             <!--         <s:hidden name="%{'planlist['+#planIndex+'].realConBillproSid'}" value="%{realConBillproSid}" />-->
	                 <s:if test="whereCome != 4">
	                   <td align="left">
	             <!--      <s:if test="foramlContractService.getRemainAmountByRealPro(realConBillproSid)  <= 0 ">
	                   		<input type="checkbox" name="rcbrps" id="checkbox"  disabled="true" value="<s:property value="realConBillproSid"/>">
	                   </s:if>
						<s:else>	                
	                 		  	<input type="checkbox" name="rcbrps" id="checkbox" value="<s:property value="realConBillproSid"/>">
	                   </s:else> -->
	                   <s:hidden name="moneytype"/>
	                   <input type="hidden" name="itemNum" value="<s:property  value="foramlContractService.getItemNo(contractItemMaininfo)"/>" />
	                   <input type="checkbox" name="rcbrps" id="checkbox" value="<s:property value="realConBillproSid"/>">
	                   </td>
	                </s:if>
	                   <td align="left"><s:property value="contractservice.findStageName(conItemStage)" /></td>       
	                  
	                  <td align="left" >
	                   <s:if  test="cmi.ContractType == 2"> <%--集成类合同--%>
	                   		<s:property value="typeManageService.getYXTypeManage(1018,cmi.mainItemDept).typeName" />
	                   </s:if>
	                   <s:else>
	                   		<s:property value="contractservice.findDepName(contractItemMaininfo)" />
	                   </s:else>
	                   </td>
	                  
	                    <td align="center"><s:property value="realPredBillDate"/></td>
	                    <td align="center"><s:property value="realPredReceDate"/></td>
	                    <td align="right"><s:property value="typeManageService.getYXTypeManage(1004,billType).typeName"/></td>
	                    <td align="right"><s:property value="realBillAmount"/></td>
	                    <td align="right"><s:property value="realTaxReceAmount"/></td>
	                    <td align="right" <s:if test="billInvoiceAmount > 0">title="开票日期：<s:property value="realNowBillDate"/>"</s:if> ><s:property value="billInvoiceAmount?billInvoiceAmount:0.0"/></td>
	                    <td align="right" <s:if test="realArriveAmount > 0">title="到款日期：<s:property value="realNowReceDate"/>"</s:if> ><s:property value="realArriveAmount?realArriveAmount:0.0"/></td>
	                </tr>
	                <s:set name="planIndex" value="%{#planIndex+1}"></s:set>
                 </s:if>
                </s:iterator>
            </s:iterator>
       </table>
                </div>
     </s:form> 
                <!--contract rcplan  end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract billinfo  start-->
                <div id="content5"   class="hidecontent">

                        <table align="center" border=1 cellpadding="1" cellspacing=1 width="100%"   bordercolor="#808080" style=" border-collapse: collapse;">
                          <tr>
                            <td width="13%" class="bg_table01"><div align="center">发票号</div></td>
                            <td width="18%" class="bg_table01"><div align="center">开票性质</div></td>
                            <td width="15%" class="bg_table01"><div align="center">开票金额</div></td>
                            <td width="16%" class="bg_table01"><div align="center">开票日期</div></td>
                            <td width="14%" class="bg_table01"><div align="center">发票类型</div></td>
                            <td width="24%" class="bg_table01"><div align="center">开票内容</div></td>
                            </tr>

						<s:set name="billSum" value="0.00" />
                         <s:iterator  id="ciil" value="conInvoiceInfoList" >
                          <tr align="center">
                            <td><div align="left">
                           	 <s:property value="#ciil.invoiceNo" /> 
                             </div></td>
                            <td ><div align="left">
                           <s:property value="foramlContractService.getInvoiceNature(#ciil.applyInvoiceId) "/> 
                           </div></td>
                            <td ><div align="right">
                            <s:property value="#ciil.invoiceAmount" />
                            <s:if test="#ciil.type!=4">
                           		<s:set name="billSum" value="#ciil.invoiceAmount+#billSum"/>
                           	</s:if>
                            </div></td>
                            <td  ><div align="center">
                            <s:date name="#ciil.invoiceDate" format="yyyy-MM-dd" />
                            </div></td>
                            <td ><div align="left">
                             <s:property value="typeManageService.getYXTypeManage(1004,#ciil.type).typeName"/>
                            </div></td>
                            <td ><div align="left"  style="word-break:break-all;">
                            <s:property value="foramlContractService.getInvoiceContent(#ciil.applyInvoiceId) "/>
                            </div></td>
                          </tr>
                         </s:iterator>
                        <s:if test="#billSum>0">
                     	<tr>
	                		<td colspan="2"  align="right" >开票总计：</td>
	                		<td align="right"><s:property value="#billSum"/></td>
	                		<td colspan="3">&nbsp;</td>
	                	</tr>
	                	</s:if>
                        </table>
              
                </div>
                <!--contract billinfo  end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract reveinfo  start-->
                <div id="content6" class="hidecontent">
                
        
              
   	<table align="center" border=1  width="100%"   bordercolor="#808080" style="border-collapse: collapse;">
		<tr align="center">
		<s:if  test="cmi.ContractType == 1"><%--工程类合同--%>
			<td width="25%" class="bg_table01">项目号</td>
		</s:if>	
			<td width="25%" class="bg_table01">到款金额</td>
			<td width="25%" class="bg_table01" >到款方式</td>  
			<td width="25%" class="bg_table01">到款时间</td>
		</tr>
	<s:set name="reveSum" value="0.00" />
	<s:iterator value="rList" id="invoice"> 
		<tr align="center" >
		<s:if  test="cmi.ContractType == 1"><%--工程类合同--%>
			<td><div align="left"><s:property value="#invoice[0]"/></div></td>
		</s:if>		
			<td><div align="right">	
				<s:property value="#invoice[1].amount"/>
				<s:set name="reveSum" value="#invoice[1].amount+#reveSum"/>
			 </div></td>
			<td><div align="left"> 
				<s:property value="typeManageService.getYXTypeManage(1017,#invoice[1].receType).typeName"/>
			 </div></td>
			 
			 <td><div align="center">
				<s:property value="#invoice[1].amountTime"/>
			 </div></td>
		</tr>
	   </s:iterator>
	  <s:if test="#reveSum>0">
	  <tr>
	  	<s:if  test="cmi.ContractType == 1">
	  		<td align="right">总计：</td>
	  		<td align="right" ><s:property value="#reveSum"/></td>
	  	</s:if>
	  	<s:else>
	  		<td align="right">总计：<s:property value="#reveSum"/></td>
	    </s:else> 
	     <td colspan="2">&nbsp;</td>
	 </tr>
	 </s:if>
    </table>
              
              
              
          
                </div>
                <!--contract reveinfo  end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract purchaseinfo  start-->
                <div id="content7"  class="hidecontent">
            
                        <table align="center" cellpadding="1" cellspacing=1 width="100%"  border="1" bordercolor="#808080" style="border-collapse:collapse;">
                          <tr align="center">
                            <td width="15%" class="bg_table01">申购单号</td>
                            <td width="15%" class="bg_table01">项目号</td>
                            <td width="15%" class="bg_table01">任务号</td>
                            <td width="15%" class="bg_table01">申购日期</td>
                            <td width="40%" class="bg_table01">申购内容</td>
                          </tr>

                         <s:iterator  id="am" value="amList"  >
                          <tr align="center">
                            <td ><s:property value="#am.applyId" /></td>
                            <td ><s:property value="#am.eventId" /></td>
                            <td ><s:property value="#am.assignmentId" /></td>
                            <td > <s:date name="#am.applyDate" format="yyyy-MM-dd" /></td>
                            <td ><div align="left" style="word-break:break-all;"><s:property value="#am.applyContent" /></div></td>
                          </tr>
                          </s:iterator>
                        </table>
                  
                </div>
                <!--contract purchaseinfo  end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract sidehelp  start-->
                <div id="content8"  class="hidecontent">
               
          <table align="center" cellpadding="1" cellspacing=1 width="100%" id=""  border="1"  bordercolor="#808080" style=" border-collapse: collapse;">
            <tr align="center" class="bg_table01">
              <td nowrap>项目号</td>
              <td nowrap>外协合同号</td>
              <td nowrap>外协合同名称</td>
              <td nowrap>工程部门</td>
              <td nowrap>外协供应商</td>
              <td nowrap>签订日期</td>
              <td nowrap>合同金额</td>
              <td nowrap>已支付金额</td>
            </tr>
		
          <s:iterator  id="ac" value="acList" >
            <tr align="center">
            <td ><div align="center">
                  <s:property value="#ac[1]" />
                </div></td>
            <td><div align="center">
                  <s:property value="#ac[0].assistanceId" />
                </div></td>
              <td><div align="center">
                <a href="javascript:showDetail('<s:property value="#ac[0].id"/>')"><s:property value="#ac[0].assistanceName" /></a>         
                </div></td>
                <td><div align="center">
                 <s:property value="typeManageService.getYXTypeManage(1018L,#ac[2]).typeName" />        
                </div></td>
              <td><div align="center">
                   <s:property value="foramlContractService.getSupplyName(#ac[0].supplyId) "/>
                </div></td>
              <td><div align="center">
                  <s:date name="#ac[0].contractDate" format="yyyy-MM-dd" />
                </div></td>
              <td><div align="right">
                  <s:property value="#ac[0].contractMoney" />
                </div></td>
              <td><div align="right">
              <s:if test="#ac[0].hasPayAmount == null">
              		0.00
              </s:if>
              <s:else>
                 <s:property value="#ac[0].hasPayAmount" />  
               </s:else>
                </div></td>
            </tr>
          </s:iterator>
        </table>
                </div>
                 <!--contract sidehelp  end-->
 </s:else>     
            
            <!--------------------我是华丽的分割线--------------------->
             <!--contract ownproduct  start-->
         <s:if test="cmi.conState < 4 ">            
                <div id="content5"  class="hidecontent">
         </s:if>     
         <s:else>   
          <div id="content9"  class="hidecontent">
        </s:else>   
                <div align="center" >
                <table align="center" cellpadding="1" cellspacing=1 width="100%"  border="1"  bordercolor="#808080" style="border-collapse:collapse;">
                  <tr align="center">
                    <td width="30%" class="bg_table01"><div align="center">名称</div></td>
                    <td width="20%" class="bg_table01"><div align="center">数量</div></td>
                    <td width="25%" class="bg_table01"><div align="center">价格</div></td>
                    <td width="25%" class="bg_table01"><div align="center">总计金额</div>
                  </tr>

                  <s:iterator  id="copl" value="contractOwnProduceList" >
                    <tr>
                      <td><div align="left">
                          <s:property value="foramlContractService.getContractOwnProduceName(#copl.ownProduceId) "/>
                        </div></td>
                      <td><div align="center">
                          <s:property value="#copl.conOwnProdAmount" />
                        </div></td>
                      <td ><div align="right">
                          <s:property value="#copl.conOwnProdPrice" />
                        </div></td>
                      <td><div align="right">
                          <s:property value="#copl.conOwnProdAmount * #copl.conOwnProdPrice"/>
                        </div></td>
                    </tr>
                  </s:iterator>
                </table>
                 </div>
           </div>
                <!--contract ownproduct  end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract other  start-->
                
         <s:if test="cmi.conState < 4 ">            
                <div id="content6"  class="hidecontent">
         </s:if>     
         <s:else>  
                <div id="content10"   class="hidecontent">  
          </s:else>        
                        <table width="100%" border="1" bordercolor="#808080" style=" border-collapse: collapse;">
                        <tr>
                        <td  align="right"><div align="right"> 完工应交材料： </div></td>  
                        <td colspan="2" align="left"><div align="left">
                     	<s:iterator value="checkedMaterialList" id="material" status="materialIndex">
                     		 <s:property value="#material.materialName"/><br/>
                     	</s:iterator>
                        </div></td>    
                        </tr>
                          <tr>
                            <td width="20%" ><div align="right">开工报告：</div></td>
                            <s:if test="coi.needPerativeReport==1">        
                              <td width="20%" ><div align="left">预期日期：<s:date name="coi.wantOpenReportDate" format="yyyy-MM-dd" />    </div></td>
                              <td width="60%" ><div align="left">
                           	 		<s:if test="coi.perativeReport != null ">录入日期：<s:date name="coi.perativeReport" format="yyyy-MM-dd" /></s:if>
          			 			 <s:elseif test="coi.perativeReport == null "> 未录入 </s:elseif>
            						</div></td>
                            </s:if>	
                            <s:else>
                            	<td width="20%" ><div align="left">不需要</div></td> <td width="60%"> &nbsp; </td>                   
                            </s:else> 
                          </tr>
                          
                         <tr>
                           <td width="20%" ><div align="right">实物交接：</div></td>
                             <s:if test="coi.needRecivedThing==1">
                              <td width="20%" ><div align="left">预期日期：<s:date name="coi.wantReciveThing" format="yyyy-MM-dd" />    </div></td>
                              <td width="60%" ><div align="left">
                             	<s:if test="coi.recivedThing != null ">录入日期：<s:date name="coi.recivedThing" format="yyyy-MM-dd" /></s:if>
          						  <s:elseif test="coi.recivedThing == null "> 未录入 </s:elseif>
          						</div></td>
                             </s:if>
                                  <s:else>
                            	<td width="20%" ><div align="left">不需要</div></td> <td width="60%"> &nbsp; </td>                     
                            </s:else> 
                          </tr>
                          
                          <tr>
                            <td width="20%"><div align="right">竣工验收证书：</div></td>
                             <s:if test="coi.needFinallyReport==1">
                              <td width="20%" ><div align="left">预期日期：<s:date name="coi.wantFinallyReptDate" format="yyyy-MM-dd" />    </div></td>
                              <td width="60%" ><div align="left">
                            	<s:if test="coi.finallyReport != null ">录入日期：<s:date name="coi.finallyReport" format="yyyy-MM-dd" /></s:if>
          			 			 <s:elseif test="coi.finallyReport == null "> 未录入 </s:elseif>
          			 			</div></td>
                            </s:if>
                                 <s:else>
                            	<td width="20%" ><div align="left">不需要</div></td> <td width="60%"> &nbsp; </td>                     
                            </s:else> 
                          </tr>
 						<tr>
                            <td align="right"><div align="right">合同交货日期：</div></td>
                          <td colspan="3" align="right"><div align="left"><s:property value="coi.conDeliveryDate" /> </div> </td>  
                          </tr>
                            <tr>
                            <td align="right"><div align="right">质保期要求：</div></td>
                          <td colspan="3" align="left">
                          <s:if test="coi == null ">
						   无
						  </s:if>
						  <s:elseif test="coi.guaranteeInfo==null">
						  	无
						  </s:elseif>
						  <s:else>
						  	<s:property value="coi.guaranteeInfo"/>
						  </s:else>
                          </td>  
                          </tr>
                          <tr>
                            <td align="right"><div align="right">备注：</div></td>
                          <td colspan="3" align="right"><div align="left"><s:property value="coi.otherRemarks" /> </div> </td>  
                          </tr>
                        </table>
                </div>
                <!--contract other  end-->
                <!--------------------我是华丽的分割线--------------------->
                <div align="center">
           
           <s:if test="whereCome != 4">
                
                <%-- 合同待确认 --%>		
         		<s:if test="cmi.conState == 1 || #parameters.fromPage[0] == 'draft'">
 						<%-- 入口： 合同待确认明细查看 --%>			
 			    	<s:if test="whereCome == 1 ">
 			    	
 			    	<s:if test="cmi.ContractType == 1">
 			    		<input  type="submit" class="button01" value="返回" onClick="goBackConSure();"/>
 			    		<input type="button" name="save2" value="确认退回" onClick="confirmCancel(<s:property value="cmi.conMainInfoSid"/>,<s:property value="cmi.ContractType"/>)" class="button01">
  						<input type="button" name="save" value="转预合同" onClick="yuhetong(<s:property value="cmi.conMainInfoSid"/>,<s:property value="cmi.ContractType"/>)" class="button01">
       				   <input type="button" name="save" value="转正式合同" onclick="checkGC();" class="button02">
       				   <input type="button" name="" value="合同概况导出" onclick="downLoad();" class="button02">
 			    	</s:if>
 			    	<s:else>
 			    		<input  type="submit" class="button01" value="返回" onClick="goBackConSure2();"/>
 			    		<input type="button" name="save2" value="确认退回" onClick="confirmCancel(<s:property value="cmi.conMainInfoSid"/>,<s:property value="cmi.ContractType"/>)" class="button01">
  						<input type="button" name="save" value="转预合同" onClick="yuhetong(<s:property value="cmi.conMainInfoSid"/>,<s:property value="cmi.ContractType"/>)" class="button01">
       				   <input type="button" name="save" value="转正式合同" onclick="checkGC2();" class="button02">
       				   <input type="button" name="" value="合同概况导出" onclick="downLoad();" class="button02">
 			    	</s:else>
 			    		
      	
                	</s:if>
                		<%-- 草稿合同明细查看 --%>	
                	 <s:else>
				<input type="button" name="" value="合同概况导出" onclick="downLoad();" class="button02">
                		<input  type="submit" class="button01" value="返回" onClick="javascript:window.history.go(-2)"/>
              		 </s:else>
                </s:if>
                
                
           			
            <%-- 确认退回 .....用不着
           	<s:elseif test="cmi.conState == 2 ">
               	 <input  type="submit" class="button01" value="返回" onClick="javascript:window.history.go(-1)"/>     	 
             </s:elseif> 	
              --%>
              
            <%-- 预合同 --%>
            <s:elseif test="cmi.conState == 3 ">
                   <%-- 工程类 --%>
               <s:if test="cmi.ContractType == 1">  
               	 		<input  type="button" class="button01" value="返回" onClick="goBackConSure();"/>
               			<input type="button" name="save" value="转正式合同" onclick="checkGC();" class="button02">
               			 <input type="button" name="" value="合同概况导出" onclick="downLoad();" class="button02">
       					&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<input type="button" name="save3" value="取消确认" onClick="chageState(this);" class="button01">
 			    </s:if>
 			      <%-- 集成类 --%>
 			   <s:else>
 			    	   <input  type="button" class="button01" value="返回" onClick="goBackConSure2();"/>
               			<input type="button" name="save" value="转正式合同" onclick="checkGC2();" class="button02">
               			 <input type="button" name="" value="合同概况导出" onclick="downLoad();" class="button02">
       					 &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<input type="button" name="save3" value="取消确认" onClick="chageState2(this);" class="button01">
 			    </s:else> 
  			 
            </s:elseif>  
             
          
             <s:elseif test="cmi.conState > 3 ">    <%--正式合同--%>
 						<%-- 入口： 合同待确认明细查看 --%>	
              	<s:if test="whereCome == 1 ">
              		<s:if test="cmi.ContractType == 1">
              			<input  type="button" class="button01" value="返回" onClick="goBackConSure();"/>
               	 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="save3" value="取消确认" onClick="chageState(this);" class="button01">
 			   		 </s:if>
 			  		 <s:else>
 			 		  <input  type="button" class="button01" value="返回" onClick="goBackConSure2();"/>
 			    	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="save3" value="取消确认" onClick="chageState2(this);" class="button01">
 			   		 </s:else>  	 		   		     		
             	</s:if>
              	
              	
              	<%--入口：合同工程经济来的--%>
              	<s:if test="whereCome == 3 ">
 			    		<input  type="button" class="button01" value="返回" onClick="javascript:window.history.go(-1)"/>   	
 			    </s:if>	
 			    
              
               <%--   ==正式合同管理明细显示==。。。。来源不是工程经济明细查看   来源不是待确认合同明细查看 。。。。。导入项目号不做明细显示。。。所以这里是正式合同管理明细显示  --%>
              <s:if test="whereCome != 3 && whereCome != 1">     
             
              <!--   
               <input  type="button" class="button02" value="开票申请"  onClick="trav_rcplanckbox();"  <s:if test="cmi.conState == 10">  disabled </s:if>     />  
               -->
             <!--
             <input  type="button" class="button02" value="收据申请"  onClick="receiptsApply('1');"  <s:if test="cmi.conState == 10">  disabled </s:if>     />  
             -->
                
               	 <%-- 预决算合同开票申请  
                  <s:if test="cmi.FinalAccount==1 ">
                  	<input  type="button" class="button02" value="预决算合同开票申请"  onClick="receiptsApply('3');"  <s:if test="cmi.conState == 10">  disabled </s:if>     />  
                  </s:if> --%>
                   <%--         
                  <input  type="button" class="button02" value="申购申请" onClick="con_purchase();" <s:if test="cmi.conState == 10">  disabled </s:if>   />
                  <input  type="button" class="button02" value="外协申请"  onClick="con_assistance();" <s:if test="cmi.conState == 10">  disabled </s:if>    />
            --%>
             <!--    
              <input  type="button" class="button02" value="开票收款计划变更"  onClick="openUrlReclBill();" <s:if test="cmi.conState == 10">  disabled </s:if>    />  
               -->
                 <!--            
                <input  type="button" class="button02" value="计划金额变更"  onClick="changeContractPlan();" <s:if test="cmi.conState == 10">  disabled </s:if>   />  
 				-->	
                    <input type="button" name="" value="合同概况导出" onclick="downLoad();" class="button02"/> 
                  	<input  type="button" class="button02" value="合同变更"  onClick="conChangeButton();" <s:if test="cmi.conState == 10">  disabled </s:if>    /> 
                  	<s:if test="canClose==false">
                  		<input  type="button" class="button01" value="关闭合同"    disabled/>
                  	</s:if>
                  	<s:else>
                  		 <input  type="button" class="button01" value="关闭合同"  onClick="closeCon();" <s:if test="cmi.conState == 10  ">  disabled </s:if>    />
                  	</s:else>
           	    </s:if>
           	      <input  type="button" class="button01" value="返回"  onClick="returnSearch();" />
 			</s:elseif>
                </div></td>
            </tr>
          </table>
        </div>
      </div></td>
  </tr>
  </s:if>
  <s:else> 
  	<table width="100%"  class="bg_table02">
		<tr align="center" class="bg_table02" >
			<td class="bg_table02" ><input type="button" class="button01" onclick="window.close()" value="关  闭" /></td>
		</tr>
    </table>
  </s:else>

</body>

<script language="javascript">

function closeCon(){
<s:if test = "cmi.FinalAccount ==1">
	if(confirm("是否确认关闭合同，注意：该合同为预决算合同"))
</s:if>
<s:else>
	if(confirm("是否确认关闭合同"))
</s:else>
	{
		var baseurl = "/yx/contract/formalContractManage/formalContractManage.action?method=closeCon&cmisysid=";	
		var contractMainSid = document.getElementById("contractmainsid").value;
		window.open(baseurl + contractMainSid,"_self");
	}
}

function returnSearch(){
	 location.href ="/yx/contract/formalContractManage/formalContractManageQuery.action";
}

<s:if test="cmi.ContractType == 2">  
<%--集成类申请申购--%>
//提取合同系统号、项目系统号，提交申购申请
function con_purchase(){
	var baseurl = "/yx/purchase/purchase.action?method=newPurchase";
	//参数1：合同主体系统号
	var contractmainsid = document.getElementById("contractmainsid").value;
	//参数2：合同项目系统号
	var itemsysid = 0;
	openUrl(baseurl+'&mainId='+contractmainsid+"&eventId="+itemsysid);
}
</s:if>

<s:if test="cmi.ContractType == 1"> 

<%--
//获取表格的行的cell的innerHTML
function getCell(tableID , rowNum, cellNum){
	var table = document.getElementById(tableID);
	return table.rows[rowNum].cells[cellNum].innerHTML ;
}
--%>
 
<%--工程类申请申购--%>
//提取合同系统号、项目系统号，提交申购申请
function con_purchase(){
	var baseurl = "/yx/purchase/purchase.action?method=newPurchase";
	//参数1：合同主体系统号
	var contractmainsid = document.getElementById("contractmainsid").value;
	//参数2：合同项目系统号
	var itemsysid = 0;
	//获得项目数组
	iir = document.getElementsByName("item_info_radio");
	var len = iir.length;
	for(var i=0;i<len;i++){        
		if(iir[i].checked){
			itemsysid = iir[i].parentNode.parentNode.all.itemsid.value;				
			
			var conItemId = iir[i].parentNode.parentNode.all.conItemId.value;			
			if ( conItemId.length < 1){
				alert("项目号不存在，无法进行申购");
				return;	
			}		
			openUrl(baseurl+'&mainId='+contractmainsid+"&eventId="+itemsysid);
			return;
		}
	}
	alert("请先选择项目");
}
</s:if>

function changeContractPlan(){
	var baseurl = "/yx/contract/formalContractPlanModify.action?method=doDefault";
	//参数1：合同主体系统号
	var contractmainsid = document.getElementById("contractmainsid").value;
	openUrl(baseurl+'&mainid='+contractmainsid);
}

//1 收据 3预决算
function receiptsApply(obj){
	var baseurl = "/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=doApplyBillFromRcbrps&comeFrom="+obj;
	var cks = document.getElementsByName("rcbrps");
	var len = cks.length;
	var flag = 0;
	var choose = 0;
	
	for(var i=0;i<len;i++){ 
		if(cks[i].checked && obj == '1'){
		
		<s:if test="cmi.ContractType == 1  ">
			if(cks[i].parentNode.all.itemNum.value.length == 0){
				alert("选择计划存在项目号未录入，不能建立申请")
				return;
			}  
		</s:if>
		
			choose = 1;
			//break;
		}
		else if(cks[i].checked && obj == '3'){
			choose = 1;
			var strMoneyTypeOrg;
			var strMoneyTypeX;
			<s:if test="cmi.ContractType == 1  ">
			if(cks[i].parentNode.all.itemNum.value.length == 0){
				alert("选择计划存在项目号未录入，不能建立申请")
				return;
			} 
			</s:if>
		//	if( flag == 0  ){			 
		//		strMoneyTypeOrg = cks[i].parentNode.all.moneytype.value;
		//		strMoneyTypeX = cks[i].parentNode.all.moneytype.value
		//		flag = 1;	
		//	}
		//	else{
		//		strMoneyTypeX = cks[i].parentNode.all.moneytype.value
		//	}
		//	if( strMoneyTypeOrg != strMoneyTypeX){
		//		alert("请选择同一个开票性质");
		//		return;
		//	}
		//	else{
		//		continue;
		//	}	
		}
	}
	
	if(choose == 0){
		alert("请先选择计划");
	}
	else{
		var rcform = document.getElementById("rcplanform");
		rcform.target = "_blank";
        rcform.action =  baseurl;
       	rcform.submit();
		return;
	}
}

function downLoad(){
	var baseurl = "/yx/contract/downLoadContractInfo.action?method=downLoad";
	//参数1：合同主体系统号
	var contractmainsid = document.getElementById("contractmainsid").value;
    url=baseurl+"&mainid="+contractmainsid+"&randomNum="+Math.random();
    window.open(url,'','menubar=yes,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=1200,top=50,left=50');
}
	
//确认退回理由
function confirmCancel(conId,contractType){
	var chegth=window.confirm("您确定退回吗？");
	if(chegth==true){
		if(contractType == '1'){
			openWin("../contract/searchContractOkList.action?method=showConfirmCancel&XMJC=1&conMainId="+conId,400,300);
		}
		else{
			openWin("../contract/searchContractOkJCList.action?method=showConfirmCancelJC&XMJC=2&conMainId="+conId,400,300);
		 }
	}
}

//转预合同
function yuhetong(conId,contractType)
{ 
    if(!isExist("../searchContractOkList.action","selectExist",conId)){
	 var chegth=window.confirm("您确定转预合同吗？");
		if(chegth==true){
		   if(contractType == '1'){
		    location.href="../contract/searchContractOkList.action?method=toPrepare&XMJC=1&conMainId="+conId;
		   }
		   else{
		   	location.href="../contract/searchContractOkList.action?method=toPrepare&XMJC=2&conMainId="+conId;
		    }
		}
	}
	else{
		alert("该合同已经是预合同!")
	}
} 
	
function isExist(url, method, name){
	var flag;
    var myRequest = new Request({url:url,async:false,method:'get'});
	   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		  if(responseText =='1'){
			   flag = true;
			 } 
			 else{
			  flag = false;
		 }
	   });
	 myRequest.send("method="+method+"&conMainid="+name+"&randomNum="+Math.random());
	 return flag;	   
}

function showClientInfo(){
	var url="/yx/contract/showOneClientInfo.action?clientId="+<s:property value="cmi.billCustomer" />;
	openWin2(url,400,300,"cInfo");
}


function reflushPage(){
		alert("变更已提交申请，请尽快联系商务确认!");
}

//合同变更按钮
function conChangeButton(){
	var url = "/yx/contract/formalContractManage/formalContractManage.action";
	//参数1：合同主体系统号
	var contractmainsid = document.getElementById("contractmainsid").value;
	var flag = checkShowChangeButton(url,"checkIsShowConChangeButton",contractmainsid);
	if(flag){
		var baseurl = "/yx/contract/formalContractModify.action?method=doDefault";
		window.open(baseurl+'&mainid='+contractmainsid+"&randomNum="+Math.random());
	}else{
		alert("该合同已经提交变更申请，请等待商务确认后再进行新的变更！");
	}
}

//检查合同是否能点击变更按钮	
function checkShowChangeButton(url, method, mid){
		var flag = false;
	    var myRequest = new Request({url:url,async:false,method:'get'});
		myRequest.addEvent("onSuccess",function(responseText, responseXML){
		 if(responseText =='true'){
			 flag = true;
		 } 
		 else{
			flag = false;
		}
	 });
	 myRequest.send("method="+method+"&cmisysid="+mid+"&randomNum="+Math.random());
	 return flag;
}
 function showDetail(realIdVal)
	{ 
		openWin2('/yx/assistance/assistanceMLeftQuery.action?method=showDetail&detailComeFrom=con&assistanceId='+realIdVal,700,500,"outsourceDetail");
	}

</script>
</html>
