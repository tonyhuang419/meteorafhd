<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>正式合同管理</title>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style>
</head>
<body>
<div align="left"> &nbsp;当前页面：合同管理 -> 正式合同管理 </div>
<table width="100%" height="100%" border="0" align="center"
			cellpadding="1" cellspacing="1" class="bg_table02">
  <tr class="bg_table02">
  
  <td colspan="4" align="center" class="bg_table02">
  
  <div id="container" class="bg_table02">
    <div align="center">
    
    <table width="100%" height="100%" border="0" align="center"
								cellpadding="1" cellspacing="1" class="bg_table02">
      <tr>
        <td align="center"><table align="center" border=0 cellpadding=1 cellspacing=1
											width="100%">
          </table></td>
      </tr>
      <tr>
        <td colspan="4" align="center" height="0.5"><img src="./../images/temp.gif" width="1" height="1"> </td>
      </tr>
      <tr class="bg_table02">
        <td colspan="4" align="center" class="bg_table02"><div id="container2" class="bg_table02">
            <div id="title" class="bg_table02">
              <div align="center" class="bg_table02">
                <ul class="bg_table02">
                  <li id="tag1"> <a href="#"
																onClick="switchTag('tag1','content1');this.blur();"><span>主体信息</span> </a> </li>
                  <li id="tag2"> <a href="#"
																onClick="switchTag('tag2','content2');this.blur();"><span>项目信息</span> </a> </li>
                  <li id="tag3"> <a href="#"
																onClick="switchTag('tag3','content3');this.blur();"><span>开票收款阶段</span> </a> </li>
                  <li id="tag4"> <a href="#"
																onClick="switchTag('tag4','content4');this.blur();"><span>实际开票收款计划</span> </a> </li>
                  <li id="tag5"> <a href="#"
																onClick="switchTag('tag5','content5');this.blur();"><span>开票信息</span> </a> </li>
                  <li id="tag6"> <a href="#"
																onClick="switchTag('tag6','content6');this.blur();"><span>收款信息</span> </a> </li>
                  <li id="tag7"> <a href="#"
																onClick="switchTag('tag7','content7');this.blur();"><span>申购信息</span> </a> </li>
                  <li id="tag8"> <a href="#"
																onClick="switchTag('tag8','content8');this.blur();"><span>外协信息</span> </a> </li>
                  <li id="tag9"> <a href="#"
																onClick="switchTag('tag9','content9');this.blur();"><span>其它</span> </a> </li>
                </ul>
              </div>
            </div>
 <div id="content1">
  <div align="center">
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
 			</tr>
                  <td align="center"><s:property value="#mainMoneyList.index+1"/></td>
                  <td align="center"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/></td>
                  <td align="center"><s:property value="money"/></td>
                  <td align="center"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
 			 <tr>
</s:iterator>
     
    </table>  
  </div>
</div>
              
  
  </td>
  
  </tr>
  
</table>
</body>
<s:debug/>
</html>
