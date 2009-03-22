<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协合同付款申请</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
</script>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 14px
}
-->
</style>
</head>
<body>
<s:form theme="simple" action="">
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr align="left">
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
		  <tr> 
				<td colspan="4" align="left">当前页面->外协付款申请明细</td>
		  </tr>
		  
		  <tr>
            	<td colspan="4" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          </tr>
          <s:if test="pi.payState==3">
			<tr>
			  <td align="right" class="bg_table02">退回理由：</td>
			  <td align="left" class="bg_table02" colspan="3">
                 	<font color="red">
                 	<s:property value="pi.returnReason"/>
                  </font>	
              </td>
		  </tr>
		  </s:if>
         	 <tr align="center">
			    
			    <td class="bg_table02" align="right">主体合同号：</td>
			      <td align="left" class="bg_table02"><s:property value="cmi.conId"/></td>
			      <td class="bg_table02" align="right">合同名称：</td>
			      <td class="bg_table02" align="left"><s:property value="cmi.conName"/></td>
	      </tr>
		  <tr align="center">
			      <td class="bg_table02" align="right">项目号：</td>
			      <td class="bg_table02" align="left"><s:property value="ac.mainProjectId"/></td>
			      <td align="right" class="bg_table02">成本中心：</td>
			      <td align="left" class="bg_table02"><s:property value="typemanageservice.getYXTypeManage(1018,c.itemResDept).typeName"/></td>
	      </tr>
		  <tr align="center">
			      <td class="bg_table02" align="right">外协合同号：</td>
			      <td class="bg_table02" align="left"><s:property value="ac.assistanceId"/></td>
			      <td class="bg_table02" align="right">外协合同名：</td>
			      <td class="bg_table02" align="left"><s:property value="ac.assistanceName"/></td>
	      </tr>
		  <tr align="center">
			      <td class="bg_table02" align="right">合同金额：</td>
			      <td class="bg_table02" align="left"><s:property value="ac.contractMoney"/></td>
			      <td class="bg_table02" align="right">申请人：</td>
			      <td class="bg_table02" align="left"><s:property value="userName"/></td>
	      </tr>
		  <tr align="center">
			      <td align="right" class="bg_table02">申请部门：</td>
			      <td align="left" class="bg_table02"><s:property value="groupName"/></td>
			      <td class="bg_table02" align="right">申请日期：</td>
                  <td class="bg_table02" align="left">
                  <s:if test="pi == null || pi.applyDate == null">
                	  <s:property value="date"/>
                  </s:if>
                  <s:else>
                 	 <s:property value="pi.applyDate"/>
                  </s:else>
                  </td>
         </tr>
	     <tr class="bg_table02">
		          <td colspan="4" align="right"><hr></td>
          </tr>
	        <tr class="bg_table02">
		      <td align="right">供应商名称：</td>
		      <td align="left"><s:property value="s.supplierName"/>
		        </td>
		      <td align="right">代码：</td>
		      <td align="left"><s:property value="s.supplierCode"/></td>
		    </tr>
			<tr>
			  <td class="bg_table02" align="right">开户银行：</td>
			  <td class="bg_table02" align="left"><s:property value="s.billBank"/></td>
			  <td class="bg_table02" align="right">银行帐号：</td>
			  <td class="bg_table02" align="left"><s:property value="s.billAccount"/></td>
		  </tr>
			<tr>
			  <td colspan="4" align="right" class="bg_table02"><hr></td>
		  </tr>
          </table>
          	 <table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
			<tr>
			  <td colspan="7" align="right" class="bg_table02"><div align="left" class="STYLE1">支付履历：</div></td>
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
          		<td class="bg_table02"><div align="center"><s:property value="applyDate"/></div></td>
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
          
        	<table width="100%">
			<tr>
		<td colspan="5" class="bg_table02 STYLE1">已关联发票：</td>
	    </tr>
		<tr>
          <td width="15%"   class="bg_table01"><div align="center">发票号</div></td>
          <td width="16%"   class="bg_table01"><div align="center">发票类型</div></td>
          <td width="18%"   class="bg_table01"><div align="center">发票金额</div></td>
          <td width="18%" class="bg_table01"><div align="center">开票时间</div></td>
          <td width="18%"  class="bg_table01"><div align="center">支付金额</div></td>
		</tr>
	<s:iterator value="info.result">	
        <tr>
          <td class="bg_table02"><div align="center"><s:property value="ticket.num"/></div></td>
	      <td class="bg_table02"><div align="center"><s:property value="typemanageservice.getYXTypeManage(1004,ticket.type).typeName"/></div></td>
	      <td class="bg_table02"><div align="center"><s:property value="ticket.money"/></div></td>
	      <td class="bg_table02"><div align="center"><s:property value="ticket.ticket_Time"/></div></td>
          <td class="bg_table02"><div align="center"><s:property value="relationPrepPayAmount"/></div></td>    
        </tr>		
    </s:iterator>    	
			<tr>
			  <td colspan="5" align="right" class="bg_table02"><HR></td>
		  </tr>
			<tr>
		<td colspan="5" class="bg_table02 STYLE1">阶段信息：</td>
	    </tr>
		<tr>
          <td width="18%"   class="bg_table01"><div align="center"></div></td>
          <td width="15%"   class="bg_table01"><div align="center">阶段名称</div></td>
          <td width="16%"   class="bg_table01"><div align="center">阶段金额</div></td>
          <td width="18%"   class="bg_table01"><div align="center">预计收款日期</div></td>
          <td width="18%"   class="bg_table01"><div align="center"></div></td>
		</tr>
	<s:iterator value="asectionList" >	
        <tr>
          <td width="18%" class="bg_table02"><div align="center"></div></td>
        	<td width="15%" class="bg_table02"><div align="center"><s:property value="contractService.findStageName(assistanceStageSId)" /></div></td>
          <td width="16%" class="bg_table02"><div align="center"><s:property value="sectionAmount" /></div></td>
          <td width="18%" class="bg_table02"><div align="center"><s:property value="sectionBillTime" /></div></td>
          <td width="18%" class="bg_table02"><div align="center"></div></td>
        </tr>		
    </s:iterator>    	
			<tr>
			  <td colspan="5" align="right" class="bg_table02"><HR></td>
		  </tr>
          </table>  
        
          <table width="100%">
			<tr>
			  <td class="bg_table02" align="right">本次申请支付金额：</td>
			  <td class="bg_table02" align="left"><s:property value="pi.payNum"/></td>
			  <td class="bg_table02" align="right">&nbsp;</td>
			  <td class="bg_table02" align="left">&nbsp;</td>
		    </tr>
			<tr>
			  <td class="bg_table02" align="right">任务号：</td>
			  <td class="bg_table02" align="left"><s:textfield disabled="true" name="pi.assignmentId"/></td>
			  <td class="bg_table02" align="right"> 接收号：</td>
			  <td class="bg_table02" align="left"><s:textfield disabled="true" name="pi.receivNum"/></td>
		  </tr>
			<tr>
			  <td align="right" class="bg_table02">付款事项说明：</td>
			  <td align="left" class="bg_table02">
                  <label>
                  <s:textarea cols="20" rows="5" disabled="true" name="pi.info"></s:textarea>
                  </label>
              </td>
			  <td align="right" class="bg_table02">备注:</td>
			  <td align="left" class="bg_table02"><s:textarea cols="20" rows="5" disabled="true" name="pi.remark"></s:textarea></td>
		  </tr>
		  
		</table>
<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
			
			  <td class="bg_table04"><div align="center"><span class="bg_blue07">
&nbsp;              &nbsp;
              <input name="save" type="button" class="button02" onClick="javascript:window.close();" value="关    闭">&nbsp;</span></div></td>
		  </tr>
			<tr valign="top">
			  <td class="bg_table04"><div align="center"></div></td>
		  </tr>
		</TABLE>
	  </td>
  </tr>
</table>
<p>&nbsp;</p>
<p>&nbsp; </p>
</s:form>
</body>
</html>
