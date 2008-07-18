<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协合同付款申请</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');
}
	function relationTicket(id){
		location.href="../assistance/assistance.action?method=relationTicket&ids="+id;
	}
	function saveAssistance(){
		location.href="../assistance/assistance.action?method=saveAssistanceApply&assistanceContractId=<s:property value="ac.id"/>";
	}
	function passAssistance(){
		location.href="../assistance/assistance.action?method=passAssistanceApply&assistanceContractId=<s:property value="ac.id"/>";
	}
	function openRelationWindow(contractId){
		openUrl('../assistance/assistance.action?method=enterTicket&id='+contractId);
	}
	function unchainRelation(ticketId){
		location.href="../assistance/assistance.action?method=unchainRelation&ticketId="+ticketId;
	}
</script>
<style type="text/css">
<!--
.STYLE1 {font-size: 14px}
-->
</style>
</head>
<body>
<s:form theme="simple" action="">
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr align="left">
		<td> 
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="4" align="left">当前页面：外协管理->外协合同付款申请</td>
			</tr>
			<tr>
            	<td colspan="4" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
            	<td colspan="4" align="right" class="bg_table04">&nbsp;</td>
			    
			    <tr align="center">
                  <td class="bg_table02" align="right">主体合同号：</td>
			      <td align="left" class="bg_table02"><s:property value="cm.conId"/></td>
			      <td class="bg_table02" align="right">合同名称：</td>
			      <td class="bg_table02" align="left"><s:property value="cm.conName"/></td>
	      </tr>
			    <tr align="center">
			      <td class="bg_table02" align="right">项目号：</td>
			      <td class="bg_table02" align="left"><s:property value="ac.mainProjectId"/></td>
			      <td align="right" class="bg_table02">成本中心：</td>
			      <td align="left" class="bg_table02"><s:property value="c.itemResDept"/></td>
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
			      <td class="bg_table02" align="left">徐同心</td>
	      </tr>
			    <tr align="center">
			      <td align="right" class="bg_table02">申请部门：</td>
			      <td align="left" class="bg_table02">制造营销</td>
			      <td class="bg_table02" align="right">申请日期：</td>
                  <td class="bg_table02" align="left"><s:property value="c.itemResDept"/></td>
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
			  <td colspan="4" align="right" class="bg_table02"><div align="left" class="STYLE1">支付履历：</div></td>
		    </tr>
			<tr>
		  <td width="11%" class="bg_table01"><div align="center">申请序号</div></td>
          <td width="12%" class="bg_table01"><div align="center">申请日期</div></td>
          <td width="14%" class="bg_table01"><div align="center">申请金额</div></td>
          <td width="11%" nowrap class="bg_table01"><div align="center">申请单状态</div></td>
		  </tr>
		  <s:iterator id="result" value="pList">
			<tr>
          		<td class="bg_table02"><div align="center"><s:property value="id"/></div></td>
          		<td class="bg_table02"><div align="center"><s:property value="applyDate"/></div></td>
          		<td class="bg_table02"><div align="center"><s:property value="payNum"/></div></td>
         		<td class="bg_table02"><div align="center"><s:property value="payState"/></div></td>
			<tr>
		  </s:iterator>	
         </table>
         
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
			<tr>
			  <td colspan="6" class="bg_table02 STYLE1">已关联发票：</td>
	    </tr>
			<tr>
            <td width="15%"   class="bg_table01"><div align="center">发票号</div></td>
          <td width="16%"   class="bg_table01"><div align="center">发票类型</div></td>
          <td width="18%"   class="bg_table01"><div align="center">发票金额</div></td>
          <td width="18%" class="bg_table01"><div align="center">开票时间</div></td>
          <td width="18%"  class="bg_table01"><div align="center">支付金额</div></td>
          <td width="15%" class="bg_table01" ><div align="center">
          </div></td>
			</tr>
		  <s:iterator id="result" value="tList">
	   	 	<tr>
	   	 		<td class="bg_table02"><div align="center"><s:property value="ticket.num"/></div></td>
	      		<td class="bg_table02"><div align="center"><s:property value="ticket.type"/></div></td>
	      		<td class="bg_table02"><div align="center"><s:property value="ticket.money"/></div></td>
	      		<td class="bg_table02"><div align="center"><s:property value="ticket.ticket_Time"/></div></td>
          		<td class="bg_table02"><div align="center"><s:property value="conMoney"/></div></td>    
          		<td class="bg_table02"><div align="center"><input type="button"  value="解除关联" onClick="javascript:unchainRelation(<s:property value="ticket.id"/>)"></div></td>
	    	</tr>
	     </s:iterator>	   
		 <tr>
			  <td align="right"  colspan="6" class="bg_table02"><hr></td>
		 </tr>
         </table>
         
        
        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
			<tr>
			  <td class="bg_table02" align="right">已支付金额：</td>
			  <td class="bg_table02" align="left"><s:property value="ac.ticketMoney"/></td>
			  <td class="bg_table02" align="right">合同余额：</td>
			  <td class="bg_table02" align="left"><s:property value="balance"/></td>
		  </tr>
			<tr>
			  <td class="bg_table02" align="right">本次申请支付金额：</td>
			  <td class="bg_table02" align="left"><s:property value="sum"/></td>
			  <td class="bg_table02" align="right">&nbsp;</td>
			  <td class="bg_table02" align="left">&nbsp;</td>
		   </tr>
			<tr>
			  <td class="bg_table02" align="right">任务号：</td>
			  <td class="bg_table02" align="left"><s:textfield name="pi.assignmentId"/></td>
			  <td class="bg_table02" align="right"> 接收号：</td>
			  <td class="bg_table02" align="left"><s:textfield name="pi.receivNum"/></td>
		  </tr>
			<tr>
			  <td align="right" class="bg_table02">付款事项说明：</td>
			  <td align="left" class="bg_table02">
                  <label>
                  <s:textarea cols="20" rows="5" name="pi.info"></s:textarea>
                  </label>
              </td>
			  <td align="right" class="bg_table02">备注：</td>
			  <td align="left" class="bg_table02"><s:textarea cols="20" rows="5" name="pi.remark"></s:textarea></td>
		  </tr>
		</table>
		</s:form>
<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>	
			<tr valign="top">
			  <td class="bg_table04"><div align="center"><span class="bg_blue07">
              <input name="save3" type="button" class="button02" onClick="javascript:saveAssistance()" value="保   存">
              &nbsp;<input name="save3" type="button" class="button02" onClick="javascript:passAssistance()" value="确认提交"> 
              &nbsp;</span>
		        <input type="button" value="关联发票" class="button02" onClick="openRelationWindow('<s:property value="ac.id"/>');">
		        <span class="bg_blue07">&nbsp;
			      <input name="save" type="button" class="button02" onClick="javascript:window.close();" value="关   闭">
		        </span></div></td>
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
</body>
</html>
