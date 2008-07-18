<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title><s:property value="applybill.billApplyNum"/>&nbsp;开票申请明细</title>
<link href="/yx/commons/styles/foramlContractStyles/style_f.css" type="text/css" rel="stylesheet">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/casemoney.js" type="text/javascript"></script>
<script type="text/javascript">


</script>
<style type="text/css">
<!--
.STYLE3 {
	color: #000000
}
body {
	background-color: #FFFFFF;
}
.STYLE5 {
	font-size: 16px;
	font-weight: bold;
}
.STYLE6 {
	font-size: 16px
}
-->
</style>
</head>
<body  leftmargin="0">
<div align="left" style="color:#000000">&nbsp;
  <p align="center">
  <s:property value="applybill.billApplyNum"/>&nbsp;开票申请明细</p>
</div>

<s:form theme="simple" >
<s:hidden name="hiddenId"/>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td colspan="10" align="right" class="bg_table01" height="3"></td>
  </tr>
  <tr class="bg_table02">
    <td width="16%" class="bg_table02" align="right"><div align="right">申请部门： </div></td>
    <td colspan="2" align="left" class="bg_table02"><div align="left">
       <s:property value="applyDept"/>
    </div></td>
    <td width="8%" align="right" class="bg_table02"><div align="right">申请人： </div></td>
    <td colspan="2" align="right" class="bg_table02"><div align="left">
        <s:property value="applyName"/>
    </div></td>
    <td width="9%" align="right" class="bg_table02"><div align="right">申请日期： </div></td>
    <td width="34%" colspan="3" align="right" class="bg_table02"><div align="left">
        <s:date name="applybill.applyId" format="yyyy-MM-dd" />
      </div></td>
  </tr>
  <tr class="bg_table02">
    <td colspan="10" align="right" class="bg_table02"><hr align="right"></td>
  </tr>
  <tr>
    <td align="right" class="bg_table02"><div align="right">单位名称： </div></td>
    <td colspan="2" align="left" class="bg_table02"><div align="left">
    <s:property value=" billClient.billName"/>
    </div></td>
    <td align="right" class="bg_table02"><div align="right">地址： </div></td>
    <td colspan="2" align="right" class="bg_table02"><div align="left">
	<s:property value=" billClient.billAdd"/>
	</div></td>
    <td align="right" nowrap class="bg_table02"><div align="right">联系电话： </div></td>
    <td colspan="3" align="right" class="bg_table02"><div align="left">
<s:property value=" billClient.billPhone"/>
 </div></td>
  </tr>
  <tr>
    <td   align="right" nowrap class="bg_table02"><div align="right">开户银行： </div></td>
    <td colspan="2"  align="left" class="bg_table02"><div align="left">
<s:property value="billClient.billBank"/>
</div></td>
    <td align="right"  class="bg_table02"><div align="right"><span class="STYLE3">帐号：</span> </div></td>
    <td colspan="2" align="right"  class="bg_table02"><div align="left">
<s:property value=" billClient.account"/>
</div></td>
    <td align="right"  class="bg_table02"><div align="right">税号：</div></td>
    <td colspan="3" align="right"  class="bg_table02"><div align="left">
<s:property value="billClient.taxNumber"/>
</div></td>
  </tr>
  <tr>
    <td colspan="10" align="right" class="bg_table02"><hr align="right"></td>
  </tr>
</table>
<s:if test="rcPlanList!=null">
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td width="12%" align="right" class="bg_table02"><div align="right">合同号：</div></td>
    <td align="left" class="bg_table02"><div align="left">
    <s:property value="foramlContractService.getConSn(applybill.contractMainInfo)"/>
    </div></td>
    <td width="8%" align="left" class="bg_table02"><div align="right">合同名称：</div></td>
    <td colspan="3" align="left" class="bg_table02">
	<s:property value="foramlContractService.getConName(applybill.contractMainInfo)"/></td>
    <td width="18%" colspan="5" rowspan="3" align="left" class="bg_table02"><div align="center"></div>   <div align="center"></div></td>
  </tr>
<s:iterator id="rc" value="rcPlanList">    <%--是个map--%>
  <tr>
	<td align="right" class="bg_table02"><div align="right">项目号： </div></td>
	<td width="10%" align="left" class="bg_table02"><div align="left">
	<s:property value="foramlContractService.getItemNo(key)"/>
</div></td>
	<td align="right" class="bg_table02"><div align="right">申请金额：</div></td>
	<td width="14%" align="right" class="bg_table02"><div align="left">
     <s:property value="value"/>
    </div></td>
    <td colspan="2" align="right" class="bg_table02">&nbsp;</td>
  </tr>
</s:iterator>
  <tr>
    <td colspan="11" align="right" class="bg_table02"><hr align="right"></td>
  </tr>
  </table>
</s:if>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td width="17%" align="right" class="bg_table02" >开票性质：</td>
    <td width="18%"  align="left" class="bg_table02" ><s:property value="typeManageService.getYXTypeManage(1012,applybill.billNature).typeName "/>
    </td>
    <td width="16%"  align="right" class="bg_table02" >&nbsp;</td>
    <td width="13%" align="left" class="bg_table02">&nbsp;</td>
    <td width="16%"  align="right" class="bg_table02">&nbsp;</td>
    <td width="20%"  align="left" class="bg_table02">&nbsp;</td>
  </tr>
  <tr>
    <td align="right" class="bg_table02"><div align="right">票据类型：</div></td>
    <td  align="left" class="bg_table02"><div align="left">
        <s:property value="typeManageService.getYXTypeManage(1004,applybill.billType).typeName"/>
      </div></td>
    <td  align="right" class="bg_table02"><div align="right">发票金额小写(含税)： </div></td>
    <td align="left" class="bg_table02" id="taxmoney">
    <s:property value="applybill.billAmountTax"/>
    </td>
    <td  align="right" class="bg_table02"><div align="right">发票金额小写(不含税)： </div></td>
     <td align="left" class="bg_table02" id="notaxmoney">
     <s:property value="applybill.billAmountNotax"/>
     </td>
  </tr>
  <tr>
    <td align="right" class="bg_table02"><div align="right">基准：</div></td>
    <td  align="left" class="bg_table02"><div align="left">
       <s:if test="applybill.base==0">
							<s:label id="hastax" value="不含税"/>
		  </s:if>
						<s:else>
							<s:label id="hastax" value="含税"/>
		</s:else>
      </div></td>
    <td  align="right" class="bg_table02"><div align="right">发票金额大写(含税)： </div></td>
    <td class="bg_table02" align="left" id="bigMoney"></td>
    <td  align="right" class="bg_table02"><div align="right">发票金额大写(不含税)：</div></td>
    <td  align="left" class="bg_table02" id="bigMoneyN"></td>
  </tr>
  <tr>
    <td rowspan="2" align="right" class="bg_table02"><div align="right">开票内容：</div></td>
    <td  rowspan="2" align="left" class="bg_table02"><div align="left">
        <s:textarea   name="applybill.billContent" disabled="true"></s:textarea>
      </div></td>
    <td  align="right" class="bg_table02">&nbsp;</td>
    <td class="bg_table02" align="left"></td>
    <td  align="right" class="bg_table02"><div align="right">库存组织： </div></td>
    <td  align="left" class="bg_table02"><div align="left">
     <s:property value="typeManageService.getYXTypeManage(1021,applybill.stockOrg).typeName"/>      
      </div></td>
  </tr>
  <tr>
    <td  align="right" class="bg_table02"><div align="right">一次出库：</div></td>
    <td  align="left" class="bg_table02"><s:if	test="applybill.oneOut"> &radic; </s:if>
      <s:else> &times; </s:else>
     </td>
    <td  align="right" class="bg_table02"><div align="right">取票地点： </div></td>
    <td  align="left" class="bg_table02"><div align="left">
        <s:property value="applybill.billSpot"/>
      </div></td>
  </tr>
  <tr>
    <td class="bg_table02" align="right"><div align="right">备注：</div></td>
    <td colspan="10" align="left" class="bg_table02"><label>
      <s:textarea cols="100"  name="applybill.remarks" disabled="true"></s:textarea>
      </label></td>
  </tr>
  <tr>
    <td colspan="6" align="right" class="bg_table02"><hr></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td width="14%" align="right" class="bg_table02"><div align="center">申购关联：</div></td>
    <td width="10%" align="left" class="bg_table02"><div align="center">项目号</div></td>
    <td width="11%" align="left" class="bg_table02"><div align="center">申购单号</div></td>
    <td colspan="2" align="left" class="bg_table02"><div align="center">申购内容</div></td>
    <td align="right" class="bg_table02"><div align="center"></div></td>
  </tr>
  <s:iterator id="pl" value="messageList">
    <tr>
      <td align="right" class="bg_table02"><div align="center"></div></td>
      <td align="left" class="bg_table02"><div align="center">
          <s:property value="#pl.eventId"/>
        </div></td>
      <td align="left" class="bg_table02"><div align="center">
          <s:property value="#pl.applyId"/>
        </div></td>
      <td colspan="2" align="left" class="bg_table02"><div align="center">
          <s:property value="#pl.applyContent"/>
        </div></td>
      <td width="13%" align="right" class="bg_table02"><div align="center"> </div></td>
    </tr>
  </s:iterator>
  <tr>
    <td  colspan="7" ><div align="center">
    	<input type="button" value="打印申请单"
				onclick="printList();" class="button01">
        <input type="button" name="button4" id="button4" value="关闭" class="button01"  onClick="window.close();">
      </div></td>
  </tr>
</table>

</s:form>


</body>
<script type="text/javascript">
function printList()
{
	var aid=document.forms(0).hiddenId.value;
	window.open("applyBillPDF.action?method=applyBillJaspser&paramId="+aid,"","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
}
function change()
	{
		var a = document.getElementById('taxmoney').innerHTML ;
		var b = document.getElementById('notaxmoney').innerHTML ;

		document.getElementById('bigMoney').innerHTML= Chinese(eval(a));
		document.getElementById('bigMoneyN').innerHTML=Chinese(eval(b));
	}
change();
</script>
</html>
