<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<html>
<head>
<title>外协合同明细</title>
</head>
<body>
<s:form action="assistance" theme="simple">
	<s:hidden name="a.id"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="1">
    <iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
      <tr>
        <td height="3" align="left">
        <s:if test='detailComeFrom.equals("con")'>
      		 当前页面:正式合同管理->外协合同明细
        </s:if>
        <s:elseif test='detailComeFrom.equals("stat")'>
        	当前页面:外协合同统计->外协合同明细
        </s:elseif>
        <s:else>
        	当前页面:外协管理->外协合同明细
        </s:else>
        	</td>
      </tr>
      <tr>
        <td class="bg_table01" height="1"><img src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
      </tr>
    </table>
        <table width="100%" border="0" cellspacing="1" cellpadding="1">
         <s:if test="assistanceContract.assistanceContractType==3">
         <tr align="center">
            <td class="bg_table02" width="20%" align="right">退回理由：</td>
            <td class="bg_table02" align="left" colspan="3" >
            <font color="red">
            <s:property value="assistanceContract.returnReason"/>
            </font>
            </td>
          </tr>
        </s:if>
          <tr align="center">
            <td class="bg_table02" width="20%" align="right">销售人员：
            </td>
            <td class="bg_table02" align="left" ><s:property value="userName"/></td>
            <td class="bg_table02">&nbsp;</td>
            <td class="bg_table02">&nbsp;</td>
          </tr>
          <tr align="center">
          <td width="13%" align="right" class="bg_table02">合同号：</td>
            <td width="37%" align="left" class="bg_table02">
            	<s:property value="contractMainInfo.conId"/>
            </td>
            <td class="bg_table02" align="right">合同名称：</td>
            <td width="33%" align="left" class="bg_table02">
            	<s:property value="contractMainInfo.conName"/>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">项目号：</td>
            <td class="bg_table02" align="left">
            	<s:property value="itemMainInfo.conItemId"/>
            <td class="bg_table02" align="right">成本中心：</td>
            <td align="left" class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1018,itemMainInfo.itemResDept).typeName"/></td>
            </tr>
            <tr align="center">
            <td class="bg_table02" align="right">外协合同号：</td>
            <td class="bg_table02" align="left">
            	<s:property value="assistanceContract.assistanceId"/>
            </td>
            <td class="bg_table02" align="right">外协合同名称：</td>
            <td align="left" class="bg_table02">
             	 <s:property value="assistanceContract.assistanceName"/>
            </td>
          </tr>
          <tr align="center">
             <td class="bg_table02" align="right">外协供应商：</td>
            <td align="left" class="bg_table02"><s:property value="supply.supplierName"/></td>
            <td class="bg_table02" nowrap="nowrap" align="right">外协合同金额：</td>
            <td class="bg_table02" align="left">	
            <s:property value="assistanceContract.contractMoney"/>	
            </td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">合同签订日期：</td>
            <td class="bg_table02" align="left">
			  	<s:property value="assistanceContract.contractDate"/>
			 </td>
            <td class="bg_table02" align="right">预计结束日期：</td>
            <td class="bg_table02" align="left">
				<s:property value="assistanceContract.endDate"/>			  	
			</td>
          </tr>
          <tr align="center">
            <td align="right" class="bg_table02">分包合同内容描述：</td>
            <td class="bg_table02" align="left" >
            	<s:property value="assistanceContract.contractContent"/>
			</td>
			<td class="bg_table02">&nbsp;</td>
			<td class="bg_table02">&nbsp;</td>
          </tr>
         <tr>
         <td align="center" class="bg_table02" colspan="4">
				<hr/>
		 </td>
         </tr>
		<tr class="bg_table02">
		<td  colspan="4">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr class="bg_table01" align="center">
					<td>阶段名称</td>
					<td>辅助阶段</td>
					<td>阶段金额</td>
					<td>预计处理日期</td>
				</tr>
				<s:iterator value="sectionList" >
					<tr class="bg_table02" align="center">
						<td><s:property value="contractService.findStageName(assistanceStageSId)" /></td>
						<td><s:property value="sectionRemarks" /></td>
						<td><s:property value="sectionAmount" /></td>
						<td><s:property value="sectionBillTime" /></td>
					</tr>
				</s:iterator>
	        </table>
       </td>
     </tr>
      <s:if test = "pList != null&&pList.size() > 0">
          <tr>
          <tD colspan="4">
          <table>
          	<tr>
          <td align="center" class="bg_table02" colspan="7">
				<hr/>
			</td>
          </tr>
          <tr>
         <td width="11%" class="bg_table01"><div align="center">申请序号</div></td>
          <td width="12%" class="bg_table01"><div align="center">申请日期</div></td>
          <td width="14%" class="bg_table01"><div align="center">申请金额</div></td>
          <td width="14%" class="bg_table01"><div align="center">任务号</div></td>
          <td width="14%" class="bg_table01"><div align="center">接受号</div></td>
          <td width="14%" class="bg_table01"><div align="center">是否预付</div></td>
          <td width="11%" nowrap class="bg_table01"><div align="center">申请单状态</div></td>
		  </tr>
		  <s:iterator id="result" value="pList" status="resultIndex">
			<tr>
          		<td class="bg_table02"><div align="center">
          			<s:property value="#resultIndex.index+1"/>
<!--          		<s:property value="applyInfoCode"/>-->
          		</div></td>
          		<td class="bg_table02"><div align="center"><s:property value="applyDate"/>
          		</div></td>
          		<td class="bg_table02"><div align="center"><s:property value="payNum"/></div></td>
          		<td class="bg_table02"><div align="center"><s:property value="assignmentId"/></div></td>
          		<td class="bg_table02"><div align="center"><s:property value="receivNum"/></div></td>
          		<td class="bg_table02"><div align="center">
          			<s:if test="applyPay">
          				预付款
          			</s:if>
          			<s:else>
          				正常付款
          			</s:else>
          		</div></td>
         		<td class="bg_table02"><div align="center">
         				<s:if test="payState==0">
         					草稿
         				</s:if>
         				<s:elseif test="payState==1">
         					待确认
         				</s:elseif>
         				<s:elseif test="payState==2">
         					确认通过
         				</s:elseif>
         				<s:elseif test="payState==3">
         					确认退回
         				</s:elseif>
         				<s:elseif test="payState==4">
         					确认处理
         				</s:elseif>
         		</div></td>
			<tr>
		  </s:iterator>	
          </table>
          </tD>
          </tr>
          
	</s:if>
     <tr class="bg_table03" align="center" style="height:42px">
        <td colspan="4">
           <table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td align="right" colspan="5">
                    	<input type="button" value=" 关  闭 " onclick="window.close();" class="button02">
     
                    </td>
                  </tr> 
                </tfoot>
            </table>
           </td>
        </tr>
    </table>
    </td>
  </tr>
</table>
</s:form>
</body>

</html>
