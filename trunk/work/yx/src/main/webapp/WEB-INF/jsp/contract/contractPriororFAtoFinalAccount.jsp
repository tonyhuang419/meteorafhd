<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>

<html>
<head>
<title>
		<s:if test="opInterface==3">
	  		合同变更确认明细
	  	</s:if>
	  	<s:else>
	  		合同预结算转决算
	  	</s:else>
</title>

<style type="text/css">
<!--

body {
	background-color: #FFFFFF;
}


-->
</style>


<script language="javascript">

//返回搜索页面
function goBackPriFAtoFA(){
	//var form = document.getElementById("info");
	//if(form != null){
		//form.action = "/yx/contract/defineSerachList.action";
		//form.submit();
	//}
	window.history.back();
}

//确认通过
function succSure(){
	var mainSid = document.getElementById("contractmainsid").value;
    document.forms(0).action="/yx/contract/defineSerachList.action?method=cirformOKToContractMain&mainId="+mainSid;
    document.forms(0).submit();
}

//确认退回
function failSure(){
	var mainSid = document.getElementById("contractmainsid").value;
	 document.forms(0).action="/yx/contract/defineSerachList.action?method=removeChangeConMains&mainId="+mainSid;
     document.forms(0).submit();
}

</script>

</head>


<body>
<div  align="left" style="color:#000000">
	
	  	<s:if test="opInterface==3">
	  		当前页面：合同管理->合同变更确认明细 
	  	</s:if>
	  	<s:else>
	  		当前页面：合同管理->结算转决算明细
	  	</s:else>
	
</div>

<s:form id="info" theme="simple">
	<input type="hidden" name="contractName" value="<s:property value="contractName"/>"/>
	<input type="hidden" name="contractNo" value="<s:property value="contractNo"/>"/>
	<input type="hidden" name="ContractType" value="<s:property value="ContractType"/>"/>
</s:form>  
  
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
                          	<li id="tag1"><a href="#" onClick="switchTagV('tag1','content1',4);this.blur();"><span>主体信息</span></a></li>
                       		<li id="tag3"><a href="#" onClick="switchTagV('tag3','content3',4);this.blur();"><span>开票收款阶段</span></a></li>
                       		 <li id="tag2"><a href="#" onClick="switchTagV('tag2','content2',4);this.blur();"><span>项目信息</span></a></li>
                        	 <li id="tag4"><a href="#" onClick="switchTagV('tag4','content4',4);this.blur();"><span>开票收款计划</span></a></li>                                  	           
                      </ul>
                    </div>
                  </div>
                </div>
                <!--contract main info start-->
                  <div id="content1" class="content1" >
      <table width="100%">
      <tr class="bg_table02"> 
       <s:hidden id="contractmainsid" name="contractmainsid" value="${cmi.conMainInfoSid}" /><%--合同主体系统号--%>   
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
                               <s:if test="changingContractMainInfo!=null">
                                     <font color="red">(<s:if test="changingContractMainInfo.FinalAccount==0 ">非预决算</s:if>
                             		 <s:elseif test="changingContractMainInfo.FinalAccount ==1">预决算</s:elseif>
                              		 <s:else>状态出错</s:else>)
                              		 </font>
                               </s:if>
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
        
        <td align="right" class="bg_table02"><div align="right"> 合同性质： </div></td>
        <td align="left" class="bg_table02"><div align="left">
            <s:property value="typeManageService.getYXTypeManage(1019,cmi.conType).typeName "/>
        </div></td>
        
        <%--
        <td class="bg_table02" align="right"><div align="right"> 主项目负责人电话： </div></td>
        <td class="bg_table02" align="left"><div align="left">
            <s:property value="cmi.mainItemPhone" />
        </div></td>--%>
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
             <td align="right" class="bg_table02">&nbsp;</td>
               <td align="left" class="bg_table02">&nbsp;</td>
      </tr>
      <tr>
			<td align="right" class="bg_table02"><div align="right">合同总金额： </div></td>
               <td align="left" class="bg_table02">
                <s:if test ="cmi.standard == 1" >
              	 	<s:property value="cmi.conTaxTamount" />
              	 	<s:if test="changingContractMainInfo!=null">
               		<font color="red">(<s:property value="changingContractMainInfo.conTaxTamount"/>)</font>
               		</s:if>
               </s:if>
               <s:elseif test ="cmi.standard == 2" >
               		<s:property value="cmi.conNoTaxTamount" />
               		<s:if test="changingContractMainInfo!=null">
              			<font color="red">(<s:property value="changingContractMainInfo.conNoTaxTamount" />)</font>
               		</s:if>
               </s:elseif> 
               </td>
             <td align="right" class="bg_table02"><div align="right">合同不含税金额： </div></td>
               <td align="left" class="bg_table02">
               		<s:property value="cmi.conNoTaxTamount" />
               		<s:if test="changingContractMainInfo!=null">
              		<font color="red">(<s:property value="changingContractMainInfo.conNoTaxTamount" />)</font>
               		</s:if>
               </td>
               
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
       
   </table>
     
     <table align="center"   cellpadding="1" cellspacing=1 width="100%"   border="1"  bordercolor="#808080" style="border-collapse:collapse;">   
       <tr>
            <td align="center" class="bg_table01">编号</td>
               <td align="center" class="bg_table01">费用名称</td>
              <td align="center" class="bg_table01">金额</td>
              <td align="center" class="bg_table01">开票类型</td>
     </tr>
     <s:set name="showIndex" value="0"></s:set>
		<s:iterator value="mainMoneyList" status="mainMoneyList" >
			<s:set name="showIndex" value="#showIndex+1"></s:set>
 			<tr>
                  <td align="center"><s:property value="#showIndex"/></td>
                  <td align="center"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/></td>
                  <td align="center"><s:property value="money"/>
                  <s:if test="mainInfoPartMap.get(id)!=null">
                  	<font color="red">(<s:property value="mainInfoPartMap.get(id).money"/>)</font>
                  </s:if>
                  </td>
                  <td align="center"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
 			 </tr>
		</s:iterator>
		<s:iterator value="changeMainMoneyList" status="mainMoneyList" >
			<s:set name="showIndex" value="#showIndex+1"></s:set>
 			<tr>
                  <td align="center"><font color="red"><s:property value="#showIndex"/></font></td>
                  <td align="center"><font color="red"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/></font></td>
                  <td align="center"><font color="red"><s:property value="money"/></font>
                  </td>
                  <td align="center"><font color="red"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></font></td>
 			 </tr>
		</s:iterator>
		<tr>
       <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
        </tr>
      <tr>
        <td class="bg_table02" align="right">变更说明：</td>
        <td class="bg_table02" colspan="3">
        	<textarea rows="5" cols="40" readonly="readonly"><s:property value="changeExplain"/></textarea>
        </td>
        </tr>
    </table>   
   </div>
   
 
                <!--contract main info end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract item info start-->
                <div id="content2"  class="hidecontent">
                  
                <table align="center" cellpadding="1" cellspacing=1 width="100%"  border="1"  bordercolor="#808080" style="border-collapse:collapse;">
                 <s:if test="cmi.ContractType == 1">
                   <s:iterator id="infoPart" value="mainInfoPartList" status="partIndex">
					<tr class="bg_table01" >
						<td width="20%" align="center"><input type="hidden"
							name="partInfoList" value="<s:property value="id"/>" /> <s:property
							value="typeManageService.getYXTypeManage(1012,#infoPart.moneytype).typeName" />
						</td>
						<td width="25%" align="center">总金额：<s:property value="#infoPart.money"/></td>	
						<td width="25%" align="center">
						<s:if test="mainInfoPartMap.get(#infoPart.id)!=null">
             			 <font color="red">(<s:property value="mainInfoPartMap.get(#infoPart.id).money"/>)</font>
             			 </s:if>
             			 <s:else>
             			 &nbsp;
             			 </s:else>
						</td>
						<td width="25%" align="center">&nbsp;</td>
					</tr>
				<tr class="bg_table02">
					<td align="center">项目号</td>
					<td align="center">工程部门</td>
					<td align="center">负责人</td>
					<td align="center">含税金额</td>
				</tr>
					<!--显示合同费用信息-->
					<!--显示项目费用和项目信息-->
					<s:iterator id="itemInfo" value="itemInfoList" status="itemInfoIndex">
						<tr class="bg_table02">			
							<td align="center"><s:property value="#itemInfo.itemMainInfo.conItemId" /></td>	
							<td align="center">
							<s:property	 value="typeManageService.getYXTypeManage(1018,#itemInfo.itemMainInfo.itemResDept).typeName" />
							</td>
							<td align="center"><s:property value="#itemInfo.itemMainInfo.itemResDeptP" /></td>
							<td align="center"><s:property value="#itemInfo.conItemAmountWithTax" />
							<s:if test="itemInfoMap.containsKey(#itemInfo.conItemInfoSid)">
	                   		<font color="red">(<s:property value="itemInfoMap.get(conItemInfoSid).conItemAmountWithTax"/>)</font>
	                   		</s:if>
							</td>
						</tr>
					</s:iterator>
					<s:iterator id="itemInfo" value="changeItemInfoList.{?#this.mainInfoPartId == #infoPart.id }" status="itemInfoIndex">
					<tr class="bg_table02">			
						<td align="center"><FONT color="red"><s:property value="#itemInfo.itemMainInfo.conItemId" /></FONT></td>	
						<td align="center"><FONT color="red">
						<s:property	 value="typeManageService.getYXTypeManage(1018,#itemInfo.itemMainInfo.itemResDept).typeName" />
						</FONT>
						</td>
						<td align="center"><FONT color="red"><s:property value="#itemInfo.itemMainInfo.itemResDeptP" /></FONT></td>
						<td align="center"><FONT color="red"><s:property value="#itemInfo.conItemAmountWithTax" /></FONT>
						<s:if test="itemInfoMap.containsKey(#itemInfo.conItemInfoSid)">
                   		<font color="red">(<s:property value="itemInfoMap.get(conItemInfoSid).conItemAmountWithTax"/>)</font>
                   		</s:if>
						</td>
					</tr>
				</s:iterator>
				</s:iterator>
				
				<s:iterator id="infoPart" value="changeMainInfoPartList" status="partIndex">
					<tr class="bg_table01" >
						<td width="20%" align="center"><input type="hidden"
							name="partInfoList" value="<s:property value="id"/>" /> <s:property
							value="typeManageService.getYXTypeManage(1012,#infoPart.moneytype).typeName" />
						</td>
						<td width="25%" align="center">总金额：<s:property value="#infoPart.money"/></td>	
						<td width="25%" align="center">
						<s:if test="mainInfoPartMap.get(#infoPart.id)!=null">
             			 <font color="red">(<s:property value="mainInfoPartMap.get(#infoPart.id).money"/>)</font>
             			 </s:if>
             			 <s:else>
             			 &nbsp;
             			 </s:else>
						</td>
						<td width="25%" align="center">&nbsp;</td>
					</tr>
				<tr class="bg_table02">
					<td align="center">项目号</td>
					<td align="center">工程部门</td>
					<td align="center">负责人</td>
					<td align="center">含税金额</td>
				</tr>
					<!--显示合同费用信息-->
					<!--显示项目费用和项目信息-->
					<s:iterator id="itemInfo" value="itemInfoList" status="itemInfoIndex">
						<tr class="bg_table02">			
							<td align="center"><FONT color="red"><s:property value="#itemInfo.itemMainInfo.conItemId" /></FONT></td>	
							<td align="center"><FONT color="red">
							<s:property	 value="typeManageService.getYXTypeManage(1018,#itemInfo.itemMainInfo.itemResDept).typeName" />
							</FONT>
							</td>
							<td align="center"><FONT color="red"><s:property value="#itemInfo.itemMainInfo.itemResDeptP" /></FONT></td>
							<td align="center"><FONT color="red"><s:property value="#itemInfo.conItemAmountWithTax" />
							</FONT>
							</td>
						</tr>
					</s:iterator>
				</s:iterator>
				</s:if>
			 </table>
       
                </div>
                <!--contract item info end-->
                <!--------------------我是华丽的分割线--------------------->
                <!--contract item stage start-->
         <div id="content3"  class="hidecontent">
                	
                        <!--开票和收款阶段开始-->
            <table align="center" cellpadding="1" cellspacing=1 width="100%"  border="1"  bordercolor="#808080" style="border-collapse:collapse;">
             
              <s:iterator value="mainMoneyWithPlanAmountList" id="maininfoPart" status="maininfoPartStatus">
              <tr>
                <td class="bg_table01"><s:property value="typeManageService.getYXTypeManage(1012,#maininfoPart[0].moneytype).typeName"/></td>
                <td class="bg_table01" colspan="4">总金额:<s:property value="#maininfoPart[0].money"/>
              <s:if test="mainInfoPartMap.get(#maininfoPart[0].id)!=null">
              <font color="red">(<s:property value="mainInfoPartMap.get(#maininfoPart[0].id).money"/>)</font>
              </s:if>
               </td>
              </tr>
                <tr>
              		<td>阶段名称</td>
              		<td>预计开票金额</td>
              		<td>预计收款金额</td>
              		<td>票据类型</td>
              		<td>预计开票日期</td>
                 </tr>
               	<s:iterator value="stagePlanlist">
               		<s:if test="contractMaininfoPart.id == #maininfoPart[0].id">
                	<tr>
                		<td><s:property value="typeManageService.getYXTypeManage(1023,contractItemStage.itemStageName).typeName" /></td>
                		<td><s:property value="stageAmout"/>
                		<s:if test="itemStagePlanMap.containsKey(id)">
                		 <font color="red">(<s:property value="itemStagePlanMap.get(id).stageAmout"/>)</font>
                 		</s:if>
                 		<s:else>
                 			&nbsp;
                 		</s:else>
                		</td>
                		<td><s:property value="reveAmount"/>
                		<s:if test="itemStagePlanMap.containsKey(id)">
                 		<font color="red">(<s:property value="itemStagePlanMap.get(id).reveAmount"/>)</font>
                 		</s:if>
                 		<s:else>
                 			&nbsp;
                 		</s:else>
                		</td>
                		<td align="left" nowrap="nowrap"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
                		<td><s:property value="makeInvoiceDate"></s:property></td>
                	</tr>
                	</s:if>
               	</s:iterator>
               	<s:iterator value="changeStagePlanList.{?#this.ccontractMaininfoPart.id == #maininfoPart[0].id}">
                	<tr>
                		<td><FONT color="red"><s:property value="typeManageService.getYXTypeManage(1023,ccontractItemStage.itemStageName).typeName" /></FONT></td>
                		<td><FONT color="red"><s:property value="stageAmout"/>
                		<s:if test="itemStagePlanMap.containsKey(id)">
                		 <font color="red">(<s:property value="itemStagePlanMap.get(id).stageAmout"/>)</font>
                 		</s:if>
                 		<s:else>
                 			&nbsp;
                 		</s:else>
                 		</FONT>
                		</td>
                		<td><FONT color="red"><s:property value="reveAmount"/>
                		<s:if test="itemStagePlanMap.containsKey(id)">
                 		<font color="red">(<s:property value="itemStagePlanMap.get(id).reveAmount"/>)</font>
                 		</s:if>
                 		<s:else>
                 			&nbsp;
                 		</s:else>
                 		</FONT>
                		</td>
                		<td align="left" nowrap="nowrap"><FONT color="red"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></FONT></td>
                		<td><FONT color="red"><s:property value="makeInvoiceDate"></s:property></FONT></td>
                	</tr>
               	</s:iterator>
               </s:iterator>
               <s:iterator value="changeMainMoneyList" id="maininfoPart" status="maininfoPartStatus">
              <tr>
                <td class="bg_table01"><s:property value="typeManageService.getYXTypeManage(1012,#maininfoPart.moneytype).typeName"/></td>
                <td class="bg_table01" colspan="4">总金额:<s:property value="#maininfoPart.money"/>
              <s:if test="mainInfoPartMap.get(#maininfoPart[0].id)!=null">
              <font color="red">(<s:property value="mainInfoPartMap.get(#maininfoPart.id).money"/>)</font>
              </s:if>
               </td>
              </tr>
                <tr>
              		<td>阶段名称</td>
              		<td>预计开票金额</td>
              		<td>预计收款金额</td>
              		<td>票据类型</td>
              		<td>预计开票日期</td>
                 </tr>
               	<s:iterator value="stagePlanlist">
               		<s:if test="contractMaininfoPart.id == #maininfoPart.id">
                	<tr>
                		<td><s:property value="typeManageService.getYXTypeManage(1023,contractItemStage.itemStageName).typeName" /></td>
                		<td><s:property value="stageAmout"/>
                		</td>
                		<td><s:property value="reveAmount"/>
                		</td>
                		<td align="left" nowrap="nowrap"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
                		<td><s:property value="makeInvoiceDate"></s:property></td>
                	</tr>
                	</s:if>
               	</s:iterator>
               	<s:iterator value="changeStagePlanList.{?#this.ccontractMaininfoPart.id == #maininfoPart.id}">
                	<tr>
                		<td><FONT color="red"><s:property value="typeManageService.getYXTypeManage(1023,ccontractItemStage.itemStageName).typeName" /></FONT></td>
                		<td><FONT color="red"><s:property value="stageAmout"/>
                 		</FONT>
                		</td>
                		<td><FONT color="red"><s:property value="reveAmount"/>
                 		</FONT>
                		</td>
                		<td align="left" nowrap="nowrap"><FONT color="red"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></FONT></td>
                		<td><FONT color="red"><s:property value="makeInvoiceDate"></s:property></FONT></td>
                	</tr>
               	</s:iterator>
               </s:iterator>
              </table>
              </div>
         <div id="content4" class="hidecontent">
                <!--实际开票和收款计划开始-->
                 <table align="center" cellpadding="1" cellspacing=1 width="100%"  border="1"  bordercolor="#808080" style="border-collapse:collapse;">
            <s:set name="planIndex" value="0"></s:set>
            <s:iterator value="mainMoneyList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01" colspan="6"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/>——总金额：<s:property value="money"/>
                  <s:if test="mainInfoPartMap.get(id)!=null">
                  <font color="red">(<s:property value="mainInfoPartMap.get(id).money"/>)</font>
                  </s:if>
                  </div></td>
                </tr>
                <tr>
                   <td align="center" >合同阶段</td>
                   <td align="center" >合同负责部门</td>
                   <td align="center" >计划开票日期</td>
                   <td align="center" >计划收款日期</td>
                   <td align="center">开票金额</td>  
                   <td align="center">收款金额</td>  
               
                </tr>
                <s:iterator value="rcbrpList.{?#this.billNature == #maininfoPart.moneytype }"  status="ilist">
	                <tr>
	                   <td align="center"><s:property value="contractservice.findStageName(conItemStage)" /></td>       
	                   <s:if  test="cmi.ContractType == 2">
	                   		<td align="center"><s:property value="typeManageService.getYXTypeManage(1018,cmi.mainItemDept).typeName" />
	                   		</td>
	                   </s:if>
	                   <s:else>
	                   	<td align="center"><s:property value="contractservice.findDepName(contractItemMaininfo)" /></td>
	                   </s:else>
	                    <td align="center" ><s:property value="realPredBillDate"/></td>
	                    <td align="center" ><s:property value="realPredReceDate"/></td>
	                   <td align="center" ><s:property value="realBillAmount"/>
	                   <s:if test="planMap.containsKey(realConBillproSid)">
	                   <font color="red">(<s:property value="planMap.get(realConBillproSid).realBillAmount"/>)</font>
	                   </s:if>
	                   </td>
	                   <td align="center" ><s:property value="realReceAmount"/>
	                   <s:if test="planMap.containsKey(realConBillproSid)">
	                   <font color="red">(<s:property value="planMap.get(realConBillproSid).realReceAmount"/>)</font>
	                   </s:if>
	                   </td>
	                </tr>
	                <s:set name="planIndex" value="%{#planIndex+1}"></s:set>
                </s:iterator>
                 <s:iterator value="crcbpList.{?#this.billNature == #maininfoPart.moneytype }"  status="ilist">
	                <tr>
	                   <td align="center"><FONT color="red"><s:property value="finalToCloseService.findChangeStageName(conItemStage)" /></FONT></td>       
	                   <s:if  test="cmi.ContractType == 2">
	                   		<td align="center"><FONT color="red"><s:property value="typeManageService.getYXTypeManage(1018,cmi.mainItemDept).typeName" /></FONT>
	                   		</td>
	                   </s:if>
	                   <s:else>
	                   	<td align="center"><FONT color="red"><s:property value="finalToCloseService.findChangeDepName(contractItemMaininfo)" /></FONT></td>
	                   </s:else>
	                    <td align="center" ><FONT color="red"><s:property value="realPredBillDate"/></FONT> </td>
	                    <td align="center" ><FONT color="red"><s:property value="realPredReceDate"/></FONT> </td>
	                   <td align="center" ><FONT color="red"><s:property value="realBillAmount"/></FONT>
	                   </td>
	                   <td align="center" ><FONT color="red"><s:property value="realReceAmount"/>
	                   </FONT>
	                   </td>
	                </tr>
	                <s:set name="planIndex" value="%{#planIndex+1}"></s:set>
                </s:iterator>
            </s:iterator>
            <s:iterator value="changeMainMoneyList" id="maininfoPart" status="maininfoPartStatus">
                <tr>
                  <td class="bg_table01" colspan="6"><div align="left"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/>——总金额：<s:property value="money"/>
                  <s:if test="mainInfoPartMap.get(id)!=null">
                  <font color="red">(<s:property value="mainInfoPartMap.get(id).money"/>)</font>
                  </s:if>
                  </div></td>
                </tr>
                <tr>
                   <td align="center" >合同阶段</td>
                   <td align="center" >合同负责部门</td>
                   <td align="center" >计划开票日期</td>
                   <td align="center" >计划收款日期</td>
                   <td align="center">开票金额</td>  
                   <td align="center">收款金额</td>  
               
                </tr>
                <s:iterator value="rcbrpList.{?#this.billNature == #maininfoPart.moneytype }"  status="ilist">
	                <tr>
	                   <td align="center"><s:property value="finalToCloseService.findChangeStageName(conItemStage)" /></td>       
	                   <s:if  test="cmi.ContractType == 2">
	                   		<td align="center"><s:property value="typeManageService.getYXTypeManage(1018,cmi.mainItemDept).typeName" />
	                   		</td>
	                   </s:if>
	                   <s:else>
	                   	<td align="center"><s:property value="finalToCloseService.findChangeDepName(contractItemMaininfo)" /></td>
	                   </s:else>
	                    <td align="center" ><s:property value="realPredBillDate"/></td>
	                    <td align="center" ><s:property value="realPredReceDate"/></td>
	                   <td align="center" ><s:property value="realBillAmount"/>
	                   </td>
	                   <td align="center" ><s:property value="realReceAmount"/>
	                   </td>
	                </tr>
	                <s:set name="planIndex" value="%{#planIndex+1}"></s:set>
                </s:iterator>
                 <s:iterator value="crcbpList.{?#this.billNature == #maininfoPart.moneytype }"  status="ilist">
	                <tr>
	                   <td align="center"><FONT color="red"><s:property value="finalToCloseService.findChangeStageName(conItemStage)" /></FONT></td>       
	                   <s:if  test="cmi.ContractType == 2">
	                   		<td align="center"><FONT color="red"><s:property value="typeManageService.getYXTypeManage(1018,cmi.mainItemDept).typeName" /></FONT>
	                   		</td>
	                   </s:if>
	                   <s:else>
	                   	<td align="center"><FONT color="red"><s:property value="finalToCloseService.findChangeDepName(contractItemMaininfo)" /></FONT></td>
	                   </s:else>
	                    <td align="center" ><FONT color="red"><s:property value="realPredBillDate"/></FONT> </td>
	                    <td align="center" ><FONT color="red"><s:property value="realPredReceDate"/></FONT> </td>
	                   <td align="center" ><FONT color="red"><s:property value="realBillAmount"/></FONT>
	                   </td>
	                   <td align="center" ><FONT color="red"><s:property value="realReceAmount"/>
	                   </FONT>
	                   </td>
	                </tr>
	                <s:set name="planIndex" value="%{#planIndex+1}"></s:set>
                </s:iterator>
            </s:iterator>
       </table>
                </div>
                <div align="center">
               	 <input  type="submit" class="button01" value="返回" onClick="goBackPriFAtoFA();"/>   
               	<s:if test="opInterface == 2">
               		<input type="button" name="succ" value="确认通过" onclick="succSure();" class="button02">
               		<input type="button" name="fail" value="确认退回" onClick="failSure();" class="button02">
               	</s:if>
                </div></td>
            </tr>
          </table>
        </div>
      </div></td>
  </tr>

</body>
</html>


