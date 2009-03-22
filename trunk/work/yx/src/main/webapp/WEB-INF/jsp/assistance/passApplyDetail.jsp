<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协合同付款申请</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
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
<s:form theme="simple" action="apply">
	<s:hidden name="method" value="saveAssistanceApply"></s:hidden>
	<s:hidden name="applyInfoId"/>
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr align="left">
		<td> 
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="4" align="left">当前页面：外协管理->外协付款申请明细</td>
			</tr>
			<tr>
            	<td colspan="4" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
            	<td colspan="4" align="right" class="bg_table04">&nbsp;</td>
			    
			    <tr align="center">
                  <td class="bg_table02" align="right">主体合同号：</td>
			      <td align="left" class="bg_table02"><label><s:property value="cmi.conId"/></label></td>
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
			      <td class="bg_table02" align="left"><s:property value="ac.id"/></td>
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
                  <td class="bg_table02" align="left"><s:property value="pi.applyDate"/></td>
		        <tr class="bg_table02">
		          <td colspan="4" align="right"><hr></td>
          </tr>
	        <tr class="bg_table02">
		      <td align="right">供应商名称：</td>
		      <td align="left"><s:property value="s.supplierid"/>
		        <label></label></td>
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
          <td class="bg_table02"><div align="center"><s:property value="num"/></div></td>
	      <td class="bg_table02"><div align="center"><s:property value="typemanageservice.getYXTypeManage(1004,type).typeName"/></div></td>
	      <td class="bg_table02"><div align="center"><s:property value="money"/></div></td>
	      <td class="bg_table02"><div align="center"><s:property value="ticket_Time"/></div></td>
          <td class="bg_table02"><div align="center"><s:property value="doneMoney"/></div></td>    
        </tr>		
    </s:iterator>    	
			<tr>
			  <td colspan="6" align="right" class="bg_table02"><HR></td>
		  </tr>
          </table>      
          <table width="100%">
			<tr>
			  <td class="bg_table02" align="right">本次申请支付金额：</td>
			  <td class="bg_table02" align="left"><s:property value="sum"/></td>
			  <td class="bg_table02" align="right">&nbsp;</td>
			  <td class="bg_table02" align="left">&nbsp;</td>
		    </tr>
			<tr>
			  <td class="bg_table02" align="right">任务号：</td>
			  <td class="bg_table02" align="left"><s:textfield name="pi.assignmentId" size="15"/>
			  </td>
			  <td class="bg_table02" align="right">接收号：</td>
			  <td class="bg_table02" align="left"><s:textfield name="pi.receivNum" size="15"/></td>
		  </tr>
			<tr>
			  <td align="right" class="bg_table02">付款事项说明：</td>
			  <td align="left" class="bg_table02">
                  <label>
                  <s:textarea name="pi.info" cols="20" rows="5"></s:textarea>
                  </label>
              </td>
			  <td align="right" class="bg_table02">备注：</td>
			  <td align="left" class="bg_table02"><s:textarea name="pi.remark" cols="20" rows="5" ></s:textarea></td>
		  </tr>
		</table>
<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
			
			
			
			<tr valign="top" align="center">
              <td><input name="save" type="button" class="button02" onClick="javascript:window.close();" value="关闭">&nbsp;</td>
		  </tr>
			<tr valign="top">
			  <td class="bg_table04"><div align="center"></div></td>
		  </tr>
		</TABLE>
	  </td>
  </tr>
</table>
</s:form>
<p>&nbsp;</p>
<p>&nbsp; </p>
</body>
</html>
