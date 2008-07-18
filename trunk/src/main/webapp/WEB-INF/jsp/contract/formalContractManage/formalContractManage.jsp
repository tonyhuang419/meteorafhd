<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
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
	showtable2("receInfoTable");
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

<body>
<div  align="left" style="color:#000000">
<s:if test="cmi.conState == 1 ">
	当前页面：合同管理->草稿合同明细
</s:if>
<s:else>
	当前页面：合同管理->正式合同管理 
</s:else>
</div>
<table width="100%" height="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="bg_table02">
  <tr class="bg_table02">
    <td colspan="4" align="center" class="bg_table02"><div id="container" class="bg_table02">
        <div align="center">
          <table width="100%" height="100%" border="0" align="center" cellpadding="1" cellspacing="1" class="bg_table02">
            <tr>
              <td align="center"><table align="center" border=0 cellpadding="1" cellspacing="1" width="100%">
                </table></td>
            </tr>
            <tr>
              <td colspan="4" align="center" height="0.5"><img src="./../images/temp.gif" width="1" height="1"> </td>
            </tr>
            <tr class="bg_table02">
              <td colspan="4" align="center" class="bg_table02"><div id="container1" class="bg_table02">
                  <div id="title" class="bg_table02">
                    <div align="center" class="bg_table02">
                      <ul class="bg_table02">
                        <li id="tag1"><a href="#" onClick="switchTag('tag1','content1');this.blur();"><span>主体信息</span></a></li>
                        <li id="tag2"><a href="#" onClick="switchTag('tag2','content2');this.blur();"><span>项目信息</span></a></li>
                        <li id="tag3"><a href="#" onClick="switchTag('tag3','content3');this.blur();"><span>开票收款阶段</span></a></li>
                        
                        <s:if test="cmi.conState == 1 ">
                        	 <li id="tag4"><a href="#" onClick="switchTag('tag4','content4');this.blur();"><span>原始开票收款计划</span></a></li>                                   	
                        	 <li id="tag5"><a href="#" onClick="switchTag('tag5','content5');this.blur();"><span>自有产品</span></a></li>
                        	 <li id="tag6"><a href="#" onClick="switchTag('tag6','content6');this.blur();"><span>资料要求</span></a></li>
                        </s:if>
                        
                        <s:else>
                        <li id="tag4"><a href="#" onClick="switchTag('tag4','content4');this.blur();"><span>实际开票收款计划</span></a></li>
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
        <td align="right" width="189"><div align="right">客户名称：</div></td>
        <td align="left" width="388"><div align="left"> 
        <s:property value="foramlContractService.getCustomerName(cmi.conCustomer)" />
        </div></td>
        <td align="right"><div align="right"> 开票客户： </div></td>
        <td align="left"><div align="left"> 
         <s:property value="foramlContractService.getCustomerName(cmi.billCustomer)" />
        </div></td>
      </tr>
      <tr>
        <td align="right" class="bg_table02"> 项目单位： </td>
        <td align="left" class="bg_table02"> 
         <s:property value="foramlContractService.getCustomerName(cmi.itemCustomer)" />        </td>
        <td width="251" align="right" class="bg_table02">&nbsp;</td>
        <td width="358" align="left" class="bg_table02">&nbsp;</td>
      </tr>
      <tr>
        <td align="right" class="bg_table02"><div align="right"> 客户联系人： </div></td>
        <td align="left" class="bg_table02"><div align="left"> 
        <s:property value="foramlContractService.getClientLinkManName(cmi.linkManId)" />
        </div></td>
        <td align="right" class="bg_table02"><div align="right"> 甲方合同号： </div></td>
        <td align="left" class="bg_table02"><div align="left">
            <s:property value="cmi.partyAConId" />
          </div></td>
      </tr>
      <tr>
        <td align="right" class="bg_table02"><div align="right"> 客户项目类型： </div></td>
        <td align="left" class="bg_table02"><div align="left"> 
 <s:property value="typeManageService.getYXTypeManage(1007,cmi.customereventtype).typeName"/>
</div></td>
        <td class="bg_table02" align="right"><div align="right"> 甲方的项目工程编号： </div></td>
        <td class="bg_table02" align="left"><div align="left">
            <s:property value="cmi.partyAProId" />
          </div></td>
      </tr>
      <tr>
        <td colspan="4" align="right" class="bg_table02"><hr align="right">        </td>
      </tr>
      <tr>
        <td class="bg_table02" align="right"><div align="right"> 正式合同号： </div></td>
        <td align="left" class="bg_table02"><div align="left">
            <s:property value="cmi.conId" />
          </div></td>
        <td class="bg_table02" align="right"><div align="right"> 售前合同号： </div></td>
        <td align="left" class="bg_table02"><div align="left">
            <s:property value="cmi.preConSysid" />
          </div></td>
      </tr>
      <tr>
        <td class="bg_table02" align="right"><div align="right"> 合同名称： </div></td>
        <td align="left" class="bg_table02"><div align="left">
            <s:property value="cmi.conName" />
          </div></td>
        <td align="right" class="bg_table02"><div align="right"> 主项目部门： </div></td>
        <td align="left" class="bg_table02"><div align="left">
            <s:property value="typeManageService.getYXTypeManage(1018,cmi.mainItemDept).typeName" />
        </div></td>
      </tr>
      <tr>
        <td class="bg_table02" align="right"><DIV align="right">预决算信息：</DIV></td>
        <td class="bg_table02" align="left">
                              <s:if test="cmi.FinalAccount==0 ">非预决算</s:if>
                              <s:elseif test="cmi.FinalAccount ==1">预决算</s:elseif>
                               <s:else>状态出错</s:else>
</td>
        <td class="bg_table02" align="right"><div align="right"> 主项目负责人： </div></td>
        <td class="bg_table02" align="left"><div align="left">
            <s:property value="cmi.mainItemPeople" />
        </div></td>
      </tr>
      <tr>
        <td class="bg_table02" align="right"><div align="right"> 合同类型： </div></td>
        <td class="bg_table02" align="left"><div align="left">
            <s:property value="typeManageService.getYXTypeManage(1020,cmi.ContractType).typeName "/>
        </div></td>
        <td class="bg_table02" align="right"><div align="right"> 主项目负责人电话： </div></td>
        <td class="bg_table02" align="left"><div align="left">
            <s:property value="cmi.mainItemPhone" />
        </div></td>
  </tr>
      <tr>
        <td align="right" class="bg_table02"><div align="right"> 合同性质： </div></td>
        <td align="left" class="bg_table02"><div align="left">
            <s:property value="typeManageService.getYXTypeManage(1019,cmi.conType).typeName "/>
        </div></td>
                            <td align="right" class="bg_table02"><div align="right">合同含税总金额： </div></td>
                            <td align="left" class="bg_table02"><s:property value="cmi.conTaxTamount" /></td>
      </tr>
      
    
	 <tr>
                <td class="bg_table02" align="right">货币单位：</td>
                <td class="bg_table02" align="left"> 
                 <s:property value="typeManageService.getYXTypeManage(1015,cmi.copeck).typeName" />
              </td>
                <td align="right" class="bg_table02">基准汇率：</td>
                <td align="left" class="bg_table02"><s:property value="cmi.baserate" />   </td>
                </tr>
                
      <tr>
        <td colspan="4" align="right" class="bg_table02"><hr align="right">        </td>
      </tr>
      <tr>
        <td class="bg_table02" align="right"><div align="right"> 合同签订日期： </div></td>
        <td class="bg_table02" align="left"><div align="left">
            <s:date name="cmi.conSignDate" format="yyyy-MM-dd" />
          </div></td>
        <td class="bg_table02" align="right"><div align="right"> 退税： </div></td>
        <td class="bg_table02" align="left"><div align="left">
            <s:if test="cmi.conDrawback ==0 ">&times;</s:if>
            <s:elseif test="cmi.conDrawback ==1"> &radic; </s:elseif>
          </div></td>
      </tr>
      <tr>
        <td align="right" class="bg_table02"><div align="right"> 合同起始日期： </div></td>
        <td align="left" class="bg_table02"><div align="left">
            <s:date name="cmi.conStartDate" format="yyyy-MM-dd" />
          </div></td>
        <td class="bg_table02" align="right"><div align="right"> 中标合同： </div></td>
        <td class="bg_table02" align="left"><div align="left">
            <s:if test="cmi.conBid == 0">&times;</s:if>
            <s:elseif test="cmi.conBid == 1"> &radic; </s:elseif>
          </div></td>
      </tr>
      <tr>
        <td align="right" class="bg_table02"><div align="right"> 合同结束日期： </div></td>
        <td align="left" class="bg_table02"><div align="left">
            <s:date name="cmi.conEndDate" format="yyyy-MM-dd" />
          </div></td>
        <td class="bg_table02" align="right"><div align="right"> 纳入年度运维合同： </div></td>
        <td class="bg_table02" align="left"><div align="left">
            <s:if test="cmi.IntoYearCon==0">&times;</s:if>
            <s:elseif test="cmi.IntoYearCon==1"> &radic; </s:elseif>
          </div></td>
      </tr>
      <!--
       <tr>
        <td class="bg_table02" align="right"><div align="right"> 完工应交材料： </div></td>  
        <td class="bg_table02" align="left"><div align="left"><s:property value="cmi.finProStuff" /></div></td>      
        <td class="bg_table02" align="right"><div align="right"> 负责部门数： </div></td>
        <td class="bg_table02" align="left"><div align="left">
          <s:property value="foramlContractService.getItemNum(cmi.conMainInfoSid)" />
        </div></td>
      </tr>
      -->
      <!--
      <tr>
        <td class="bg_table02" align="right">&nbsp;</td>
        <td class="bg_table02" align="left">&nbsp;</td>
        <td align="right" class="bg_table02"><div align="right"> 计划阶段数： </div></td>
        <td align="left" class="bg_table02"><div align="left"> 
         <s:property value="foramlContractService.getStageNum(cmi.conMainInfoSid)" />
        </div></td>
      </tr>
       -->
      <tr>
        <td colspan="4" align="right" class="bg_table02"><hr align="right">        </td>
      </tr>
      <tr>
        <td align="right" class="bg_table02"><div align="right"> 开票状态： </div></td>
        <td class="bg_table02" align="left"><div align="left"> <s:property value="contractBillState" /></div></td>
        <td align="right" class="bg_table02"><div align="right"> 收款状态： </div></td>
        <td align="right" class="bg_table02"><div align="left"> <s:property value="contractReceState" /></div></td>
      </tr>
      <tr>
        <td align="right" class="bg_table02"><div align="right"> 合同状态： </div></td>
        <td class="bg_table02" align="left"><div align="left">
         <s:property value="foramlContractService.covConStateSnToName(cmi.conState)" />
        </div></td>
        <td align="right" class="bg_table02"><div align="right"></div></td>
        <td align="right" class="bg_table02"><div align="left"></div></td>
      </tr> 
      <tr>
       <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
        </tr>
       <tr>
            <td align="center" class="bg_table01">编号</td>
               <td align="center" class="bg_table01">费用名称</td>
              <td align="center" class="bg_table01">金额</td>
              <td align="center" class="bg_table01">开票类型</td>
     </tr>
		<s:iterator value="mainMoneyList" status="mainMoneyList" >
 			<tr>
                  <td align="center"><s:property value="#mainMoneyList.index+1"/></td>
                  <td align="center"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/></td>
                  <td align="center"><s:property value="money"/></td>
                  <td align="center"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
 			 </tr>
		</s:iterator>
     
    </table>   
   </div>
                <!--contract main info end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract item info start-->
                <div id="content2"  class="hidecontent">
                  
                <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="item_info_table" >
                  <s:iterator id="infoPart" value="mainInfoPartList"
					status="partIndex">
					<tr>
						<td width="25%" class="bg_table01" align="center"><input type="hidden"
							name="partInfoList" value="<s:property value="id"/>" /> <s:property
							value="typeManageService.getYXTypeManage(1012,#infoPart.moneytype).typeName" />
						</td>
						<td width="25%" class="bg_table01" align="center">总金额：<s:property value="#infoPart.money"/></td>
						<td width="20%" class="bg_table01" align="center">&nbsp;</td>
						<td width="25%" class="bg_table01" align="center">&nbsp;</td>
					</tr>
				<tr>
					<td  class="bg_table02" align="center">&nbsp;</td>
					<td class="bg_table02" align="center">工程部门</td>
					<td class="bg_table02" align="center">负责人</td>
					<td class="bg_table02" align="center">含税金额</td>
				</tr>
					<!--显示合同费用信息-->
					<!--显示项目费用和项目信息-->
					<s:iterator id="itemInfo" value="itemInfoList" status="itemInfoIndex">
						<tr>
							 <s:hidden name="itemsid" value="${itemMainInfo.conItemMinfoSid}" />
							 	
							<td class="bg_table02" align="center"> <input type="radio" name="item_info_radio"/></td>
							<td class="bg_table02" align="center"><s:property
								value="typeManageService.getYXTypeManage(1018,#itemInfo.itemMainInfo.itemResDept).typeName" />
							</td>
							<td class="bg_table02" align="center"><s:property
								value="#itemInfo.itemMainInfo.itemResDeptP" /></td>
							<td class="bg_table02" align="center"><s:property
								value="#itemInfo.conItemAmountWithTax" /></td>
						</tr>
					</s:iterator>

					<tr>
						<td colspan="5"><hr /></td>
					</tr>
				</s:iterator>
                </table>
       
                </div>
                <!--contract item info end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract item stage start-->
                <div id="content3"  class="hidecontent">
                	
                        <!--开票和收款阶段开始-->
                      <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
               <s:iterator value="mainMoneyWithPlanAmountList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,#maininfoPart[0].moneytype).typeName"/>&nbsp;&nbsp;&nbsp;&nbsp;总金额:<s:property value="#maininfoPart[0].money"/>
                 <!-- &nbsp;&nbsp;&nbsp;&nbsp;未分配金额:<s:property value="#maininfoPart[0].money - #maininfoPart[1]" /> -->    </div></td>
                </tr>
                <tr>
                  <td>
                  <table align="center" border=0 cellpadding="0" cellspacing="0" width="100%">
                  	<tr>
                  		<td>阶段名称</td>
                  		<td>阶段金额</td>
                  		<td>预计开票日期</td>

                  	</tr>
                  	<s:iterator value="stagePlanlist">
                  		<s:if test="contractMaininfoPart.id == #maininfoPart[0].id">
	                  	<tr>
	                  		<td><s:property value="typeManageService.getYXTypeManage(1023,contractItemStage.itemStageName).typeName" /></td>
	                  		<td><s:property value="stageAmout"></s:property></td>
	                  		<td><s:property value="makeInvoiceDate"></s:property></td>

	                  	</tr>
	                  	</s:if>
                  	</s:iterator>
                  </table>
                  </td>
                </tr>
               </s:iterator>
              </table>
              </div>
              
               <!--contract rcplan start-->
              <s:if test="cmi.conState == 1 ">
 <div id="content4" class="hidecontent">
                <!--开票和收款计划开始-->
                 <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
            <s:set name="planIndex" value="0"></s:set>
            <s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01" colspan="4"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/>——总金额：<s:property value="money"/></div></td>
                </tr>
                <tr>
                   <td align="center" >合同阶段</td>
                   <td align="center" >合同负责部门</td>
                   <td align="center">开票金额</td>
                </tr>
                <s:iterator value="initRcplan"  status="ilist">
                 <s:if test="moneytype == billNature">
	                <tr>
	              <!--    <s:hidden name="%{'planlist['+#planIndex+'].realConBillproSid'}" value="%{realConBillproSid}" />   -->  
	                   <td align="center" width="33%" ><s:property value="contractservice.findStageName(conItemStage)" /></td>
	                   <td align="center" width="33%" ><s:property value="contractservice.findDepName(conItemInfo)" /></td>
	                   <td align="center" width="33%" ><s:property value="initBillAmount"/></td>
	                </tr>
	                <s:set name="planIndex" value="%{#planIndex+1}"></s:set>
                 </s:if>
                </s:iterator>
            </s:iterator>
       </table>
                </div>
			</s:if>

<s:if test="cmi.conState > 3 ">
                <!--contract rcplan start-->
               <s:form id="rcplanform" action="formalContractInvoiceApply" theme="simple" method="post">                 
      
      		<s:hidden id="contractmainsid" name="contractmainsid" value="${cmi.conMainInfoSid}" /><%--合同主体系统号--%>
       <!--  		<s:hidden id="billcustomersid" name="billcustomersid"  value="${cmi.billCustomer}" /><%--合同开票客户系统号--%>
        	 <s:hidden id="contractid" name="contractid" value="${cmi.conId}" /><%--合同号--%>
             <s:hidden id="rclist" name="rclist" value="" />   
	  -->       
                <div id="content4" class="hidecontent">
                <!--开票和收款计划开始-->
                 <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
            <s:set name="planIndex" value="0"></s:set>
            <s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01" colspan="4"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/>——总金额：<s:property value="money"/></div></td>
                </tr>
                <tr>
                   <td align="center" >合同阶段</td>
                   <td align="center" >合同负责部门</td>
                   <td align="center">开票金额</td>
                </tr>
                <s:iterator value="rcbrpList"  status="ilist">
                 <s:if test="moneytype == billNature">
	                <tr>
	             <!--         <s:hidden name="%{'planlist['+#planIndex+'].realConBillproSid'}" value="%{realConBillproSid}" />-->
	                   <td align="center" width="33%" ><s:property value="contractservice.findStageName(conItemStage)" /></td>
	                   <td align="center" width="33%" ><s:property value="contractservice.findDepName(contractItemMaininfo)" /></td>
	                   <td align="center" width="33%" ><s:property value="realBillAmount"/></td>
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

                        <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
                          <tr>
                            <td width="13%" class="bg_table01"><div align="center">发票号</div></td>
                            <td width="18%" class="bg_table01"><div align="center">开票性质</div></td>
                            <td width="15%" class="bg_table01"><div align="center">开票金额</div></td>
                            <td width="16%" class="bg_table01"><div align="center">开票日期</div></td>
                            <td width="14%" class="bg_table01"><div align="center">发票类型</div></td>
                            <td width="24%" class="bg_table01"><div align="center">开票内容</div></td>
                            </tr>
                        </table> 

                        <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="related_bill">
                         <s:iterator  id="ciil" value="conInvoiceInfoList" >
                          <tr align="center">
                            <td width="13%"><div align="center">
                           	 <s:property value="#ciil.invoiceNo" /> 
                             </div></td>
                            <td  width="18%" ><div align="center">
                           <s:property value="foramlContractService.getInvoiceNature(#ciil.applyInvoiceId) "/> 
                           </div></td>
                            <td  width="15%" ><div align="center">
                            <s:property value="#ciil.invoiceAmount" />
                            </div></td>
                            <td width="16%" ><div align="center">
                            <s:date name="#ciil.invoiceDate" format="yyyy-MM-dd" />
                            </div></td>
                            <td width="14%" ><div align="center">
                             <s:property value="typeManageService.getYXTypeManage(1004,#ciil.type).typeName "/>
                            </div></td>
                            <td width="24%" ><div align="center">
                            <s:property value="foramlContractService.getInvoiceContent(#ciil.applyInvoiceId) "/>
                            </div></td>
                          </tr>
                         </s:iterator>
                        </table>
              
                </div>
                <!--contract billinfo  end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract reveinfo  start-->
                <div id="content6" class="hidecontent">
              
              <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
                <td width="11%" class="bg_table01"><div align="center">发票号</div></td>
                  <td width="17%" class="bg_table01"><div align="center">开票日期</div></td>
                  <td width="18%" class="bg_table01"><div align="center">开票金额</div></td>
                  <td width="19%" class="bg_table01"><div align="center">到款总金额</div></td>
                  <td width="18%" class="bg_table01"><div align="center">发票类型</div></td>
                  <td width="17%" class="bg_table01"><div align="center">到款状态</div></td>
              </table>
          
          
              <table width="100%" id="receInfoTable">
                <s:iterator  id="ciil" value="conInvoiceInfoList" >
                  <tr>
                    <td colspan="6"><table  align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
                        <tr align="center" onClick="showlist('<s:property value="'dqmx'+ #ciil.invoiceNo" />');">
                          <td width="13%"><div align="center">
                                <s:property value="#ciil.invoiceNo" />
                            </div></td>
                          <td width="17%"><div align="center">
                              <s:date name="#ciil.invoiceDate" format="yyyy-MM-dd" />
                            </div></td>
                          <td width="18%"><div align="center">
                              <s:property value="#ciil.invoiceAmount" />
                            </div></td>
                          <td width="19%"><div align="center">
                              <s:property value="#ciil.receAmount" />
                            </div></td>
                          <td width="18%"><div align="center">
                              <s:property value="typeManageService.getYXTypeManage(1004,#ciil.type).typeName "/>
                            </div></td>
                          <td width="17%" ><div align="center">
                        	  <s:if test=" #ciil.receAmount == null">无到款</s:if>
                              <s:elseif test=" #ciil.receAmount == 0">无到款</s:elseif>                             
                              <s:elseif test="#ciil.invoiceAmount > #ciil.receAmount">部分到款</s:elseif>
                              <s:elseif test="#ciil.invoiceAmount == #ciil.receAmount">全部到款</s:elseif>
                            </div></td>
                        </tr>
                      </table></td>
                  </tr>
                  <tr style="display:none" id="<s:property value="'dqmx'+#ciil.invoiceNo" />">
                    <td colspan="6"><table width="40%"  class="con_stage_tableborder">
                        <tr align="center">
                          <td width="35%" align="left" class="bg_table02"><div align="center"><strong>收款金额</strong></div></td>
                          <td width="35%" align="right" class="bg_table02"><div align="center"><strong>到款方式</strong></div></td>
                          <td width="30%" align="right" class="bg_table02"><div align="center"><strong>到款时间</strong></div></td>
                        </tr>
                        <s:iterator  id="crils" value="conReveInfoList" >
                          <s:iterator  id="cril" value="crils" >
                            <s:if test="#cril.billSid == #ciil.id">
                              <tr>
                                <td align="left"  class="bg_table02"><div align="center">
                                    <s:property value="#cril.amount" />
                                  </div></td>
                                <td align="right" class="bg_table02"><div align="center">
                                 <s:property value="typeManageService.getYXTypeManage(1017,#cril.receType).typeName "/>
								</div></td>
                                <td align="right" class="bg_table02"><div align="center">
                                    <s:date name="#cril.amountTime" format="yyyy-MM-dd" />
                                  </div></td>
                              </tr>
                            </s:if>
                          </s:iterator>
                        </s:iterator>
                      </table></td>
                  </tr>
                </s:iterator>
              </table>
          
                </div>
                <!--contract reveinfo  end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract purchaseinfo  start-->
                <div id="content7"  class="hidecontent">
            
                        <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
                          <tr align="center">
                            <td width="16%" class="bg_table01">申购单号</td>
                            <td width="16%" class="bg_table01">项目号</td>
                            <td width="16%" class="bg_table01">任务号</td>
                            <td width="16%" class="bg_table01">申购日期</td>
                            <td width="31%" class="bg_table01">申购内容</td>

                          </tr>
                        </table>

                        <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
                         <s:iterator  id="am" value="amList"  >
                          <tr align="center">
                            <td width="16%" class="bg_table02"><s:property value="#am.applyId" /></td>
                            <td width="16%" class="bg_table02">PBC1234</td>
                            <td width="16%" class="bg_table02"><s:property value="#am.assignmentId" /></td>
                            <td width="16%" class="bg_table02"> <s:date name="#am.applyDate" format="yyyy-MM-dd" /></td>
                            <td width="31%" class="bg_table02"><s:property value="#am.applyContent" /></td>

                          </tr>
                          </s:iterator>
                        </table>
                  
                </div>
                <!--contract purchaseinfo  end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract sidehelp  start-->
                <div id="content8"  class="hidecontent">
               
          <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
            <tr align="center">
              <td width="13%" class="bg_table01"><div align="center">外协合同号</div></td>
              <td width="17%" class="bg_table01"><div align="center">外协合同名称</div></td>
              <td width="22%" class="bg_table01"><div align="center">外协供应商</div></td>
              <td width="18%" class="bg_table01"><div align="center">签订日期</div></td>
              <td width="16%" class="bg_table01"><div align="center">合同金额</div></td>
              <td width="14%" class="bg_table01"><div align="center">已支付金额</div></td>
            </tr>
            <tr>
          </table>
      
     
        <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
          <s:iterator  id="ac" value="acList" >
            <tr align="center" onClick="showlist('<s:property value="'fksq'+ #ac.id" />');">
            <td width="13%" class="bg_table02"><div align="center">
                  <s:property value="#ac.assistanceId" />
                </div></td>
              <td width="15%" class="bg_table02"><div align="center">
                 <s:property value="#ac.assistanceName" />        
                </div></td>
              <td width="24%" class="bg_table02"><div align="center">
                   <s:property value="foramlContractService.getSupplyName(#ac.supplyId) "/>
                </div></td>
              <td width="18%" class="bg_table02"><div align="center">
                  <s:date name="#ac.contractDate" format="yyyy-MM-dd" />
                </div></td>
              <td width="16%" class="bg_table02"><div align="center">
                  <s:property value="#ac.contractMoney" />
                </div></td>
              <td width="14%" class="bg_table02"><div align="center">
                 <s:property value="#ac.ticketMoney" />   
                </div></td>
            </tr>
            <tr style="display:none" id="<s:property value="'fksq'+#ac.id" />">
            <td colspan="6"><div align="left" >
                  <table width="61%" class="con_stage_tableborder">
                    <tr align="center">
                      <td width="23%"   align="left" class="bg_table02"><div align="center"><strong>申请序号</strong></div></td>
                      <td width="31%" align="right" class="bg_table02"><div align="center"><strong>申请金额</strong></div></td>
                      <td width="25%" align="right" class="bg_table02"><div align="center"><strong>申请时间</strong></div></td>
                      <td width="21%" align="right" class="bg_table02"><div align="center"><strong>申请状态</strong></div></td>
                    </tr>
                    <s:iterator  id="apis" value="apiList" >
                      <s:iterator  id="api" value="apis" >
                        <s:if test="#ac.id == #api.assistanceId">
                          <tr>
                            <td class="bg_table02" width="23%" align="left"><div align="center">
                                <s:property value="#api.assistanceId" />
                              </div></td>
                            <td align="right" class="bg_table02"><div align="center">
                                <s:property value="#api.payNum" />
                              </div></td>
                            <td align="right" class="bg_table02"><div align="center">
                                <s:date name="#api.applyDate" format="yyyy-MM-dd" />
                              </div></td>
                            <td align="right" class="bg_table02"><div align="center">
                                <s:if test=" #api.payState == 0">新建</s:if>
                                <s:elseif test=" #api.payState == 1">待确认</s:elseif>
                                <s:elseif test=" #api.payState == 2">确认通过</s:elseif>
                                <s:elseif test=" #api.payState == 3">确认退回</s:elseif>
                                <s:elseif test=" #api.payState == 4">付款完成</s:elseif>
                                <s:else>状态错误</s:else>
                              </div></td>
                          </tr>
                        </s:if>
                      </s:iterator>
                    </s:iterator>
                  </table>
            </td>          
            </tr>        
          </s:iterator>
        </table>
                </div>
                 <!--contract sidehelp  end-->
           </s:if>     
            
            <!--------------------我是华丽的分割线--------------------->
             <!--contract ownproduct  start-->
         <s:if test="cmi.conState == 1 ">            
                <div id="content5"  class="hidecontent">
         </s:if>     
         <s:else>   
          <div id="content9"  class="hidecontent">
        </s:else>   
                <div align="center" >
                <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
                  <tr align="center">
                    <td width="30%" class="bg_table01"><div align="center">名称</div></td>
                    <td width="10%" class="bg_table01"><div align="center">数量</div></td>
                    <td width="30%" class="bg_table01"><div align="center">价格</div></td>
                    <td width="20%" class="bg_table01"><div align="center">总计金额</div>
                  </tr>
                </table>
              </div>

                <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="own_product">
                  <s:iterator  id="copl" value="contractOwnProduceList" >
                    <tr>
                      <td width="30%"><div align="center">
                          <s:property value="foramlContractService.getContractOwnProduceName(#copl.ownProduceId) "/>
                        </div></td>
                      <td width="10%"><div align="center">
                          <s:property value="#copl.conOwnProdAmount" />
                        </div></td>
                      <td width="30%"><div align="center">
                          <s:property value="#copl.conOwnProdPrice" />
                        </div></td>
                      <td width="20%"><div align="center">
                          <s:property value="#copl.conOwnProdAmount * #copl.conOwnProdPrice"/>
                        </div></td>
                    </tr>
                  </s:iterator>
                </table>
                </div>
                <!--contract ownproduct  end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract other  start-->
                
         <s:if test="cmi.conState == 1 ">            
                <div id="content6"  class="hidecontent">
         </s:if>     
         <s:else>  
                <div id="content10"   class="hidecontent">  
          </s:else>        
                        <table width="100%" >
                        <tr>
                        <td class="bg_table02" align="right"><div align="right"> 完工应交材料： </div></td>  
                        <td class="bg_table02" align="left"><div align="left"><s:property value="coi.finallyLize" /></div></td>    
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
                              <td width="20%" ><div align="left">预期日期： <s:date name="coi.wantFinallyReptDate" format="yyyy-MM-dd" />    </div></td>
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
                            <td align="right"><div align="right">备注：</div></td>
                          <td colspan="3" align="right"><div align="left"><s:property value="coi.otherRemarks" /> </div> </td>    </tr>
                        </table>
                </div>
                <!--contract other  end-->
                <!--------------------我是华丽的分割线--------------------->
                
                <div align="center">
                  <input  type="submit" class="button01" value="返回" onClick="javascript:window.history.go(-1)"/>
             
             <s:if test="cmi.conState != 1 ">
              <!--   
               <input  type="submit" class="button02" value="开票申请"  onClick="trav_rcplanckbox();"  <s:if test="cmi.conState == 10">  disabled </s:if>     />  
               -->
                  <input  type="submit" class="button02" value="申购申请" onClick="con_purchase();" <s:if test="cmi.conState == 10">  disabled </s:if>   />
                  <input  type="submit" class="button02" value="外协申请"  onClick="con_assistance();" <s:if test="cmi.conState == 10">  disabled </s:if>    />
             <!--    
              <input  type="submit" class="button02" value="开票收款计划变更"  onClick="openUrlReclBill();" <s:if test="cmi.conState == 10">  disabled </s:if>    />  
               -->
                  <input  type="submit" class="button02" value="合同变更"  onClick="con_change();" <s:if test="cmi.conState == 10">  disabled </s:if>    />
                   
                  	<s:if test="canClose==false">
                  		<input  type="button" class="button01" value="关闭合同"  onClick="alert('合同关闭');"  disabled/>
                  	</s:if>
                  	<s:else>
                  		 <input  type="button" class="button01" value="关闭合同"  onClick="closeCon();" <s:if test="cmi.conState == 10">  disabled </s:if>    />
                  	</s:else>
 			</s:if>
                </div></td>
            </tr>
          </table>
        </div>
      </div></td>
  </tr>
</table>

</body>
</html>


