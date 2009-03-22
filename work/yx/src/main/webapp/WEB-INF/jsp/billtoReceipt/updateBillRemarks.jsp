<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title><s:property value="applybill.billApplyNum" />&nbsp;开票申请明细</title>
<link href="/yx/commons/styles/foramlContractStyles/style_f.css"
	type="text/css" rel="stylesheet">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/casemoney.js" type="text/javascript"></script>
<script type="text/javascript">
	function asd()
	{
		document.getElementById('bigMoney').innerHTML = Chinese(parseFloatNumber(document.getElementById('taxmoney').innerHTML));
		document.getElementById('bigMoneyN').innerHTML = Chinese(parseFloatNumber(document.getElementById('notaxmoney').innerHTML));
	}
	function save(){
		document.forms(0).submit();
	}
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
<body leftmargin="0">
<div align="left" style="color: #000000">&nbsp;
<p align="center"><s:property value="applybill.billApplyNum" />&nbsp;开票申请明细</p>
</div>
<iframe id="downFrame" style="display: none"></iframe>
<s:form theme="simple" action="formalContractInvoiceApply">
<s:hidden name="method" value="updateRemark"></s:hidden>
	<s:hidden name="hiddenId" />
	<input type="hidden" name="aBillId" value="<s:property value="hiddenId"/>"/>
	<br />
	<table width="100%" border="1" class="bg_table02" align="center"
		bordercolor="#808080" style="border-collapse: collapse;">
		<tr>
			<td width="10%" align="right">
			<div align="right">申请部门：</div>
			</td>
			<td width="20%" align="left">
			<div align="left"><s:property value="applyDept" /></div>
			</td>
			<td width="10%" align="right">
			<div align="right">申请人：</div>
			</td>
			<td width="25%" align="right">
			<div align="left"><s:property value="applyName" /></div>
			</td>
			<td width="10%" align="right">
			<div align="right">申请日期：</div>
			</td>
			<td width="25%" align="right">
			<div align="left"><s:date name="applybill.applyId"
				format="yyyy-MM-dd" /></div>
			</td>
		</tr>

		<tr>
			<td align="right">
			<div align="right">单位名称：</div>
			</td>
			<td align="left">
			<div align="left"><s:property value=" billClient.billName" /></div>
			</td>
			<td align="right">
			<div align="right">地址：</div>
			</td>
			<td align="right">
			<div align="left"><s:property value=" billClient.billAdd" /></div>
			</td>
			<td align="right" nowrap>
			<div align="right">联系电话：</div>
			</td>
			<td align="right">
			<div align="left"><s:property value=" billClient.billPhone" />
			</div>
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>
			<div align="right">开户银行：</div>
			</td>
			<td align="left">
			<div align="left"><s:property value="billClient.billBank" /></div>
			</td>
			<td align="right">
			<div align="right"><span class="STYLE3">帐号：</span></div>
			</td>
			<td align="right">
			<div align="left"><s:property value=" billClient.account" /></div>
			</td>
			<td align="right">
			<div align="right">税号：</div>
			</td>
			<td align="right">
			<div align="left"><s:property value="billClient.taxNumber" /></div>
			</td>
		</tr>

		<s:if test="rcPlanList!=null">
			<tr>
				<td align="right">
				<div align="right">合同号：</div>
				</td>
				<td align="left">
				<div align="left"><s:property
					value="foramlContractService.getConSn(applybill.contractMainInfo)" />
				</div>
				</td>
				<td align="left">
				<div align="right">合同名称：</div>
				</td>
				<td colspan="3" align="left"><s:property
					value="foramlContractService.getConName(applybill.contractMainInfo)" /></td>

			</tr>

			<s:if test="applybill.initIsNoContract == 1">
				<%-- 未签合同--%>
				<tr>
					<td align="right">
					<div align="right">项目号：</div>
					</td>
					<td align="left">
					<div align="left"><s:property value="itemName" /></div>
					</td>
					<td align="right">
					<div align="right">申请金额：</div>
					</td>
					<td align="right">
					<div align="left"><s:property value="applyAmount" /></div>
					</td>
					<td colspan="3" align="right">&nbsp;</td>
				</tr>
			</s:if>

			<s:elseif test="applybill.initIsNoContract == 0">
				<%--已签合同 --%>

				<s:if test=" applybill.applyWay ==  1">
					<%--已签开票、合并开票--%>
					<s:iterator id="rc" value="rcPlanList" status="stats">
						<%--是个map--%>
						<tr>
							<td>
							<div align="right">项目号：</div>
							</td>
							<td>
							<div align="left"><s:property
								value="invoiceService.getCimiName(key)" /></div>
							</td>
							<td>
							<div align="right">开票金额：</div>
							</td>
							<td>
							<div align="left"><s:property value="value" /></div>
							</td>

							<s:if test=" applybill.applyWay !=  2 ">
								<%--收据开票--%>
								<td align="right">开票性质：</td>
								<td align="left"><s:if test="applybill.applyWay!=2">
									<s:property
										value="typeManageService.getYXTypeManage(1012,invoiceService.getBillTypeFromRCPlan(key)).typeName " />
								</s:if>
							</s:if>

						</tr>
					</s:iterator>
				</s:if>


				<s:elseif
					test=" applybill.applyWay ==  0  ||  applybill.applyWay ==  2">
					<%--收据开票, 预决算--%>


					<s:iterator id="rp" value="rcPlanList" status="s">
						<tr>
							<td align="right">
							<div align="right">项目号：</div>
							</td>
							<td align="left">
							<div align="left"><s:property
								value="invoiceService.getCimiName(key)" /></div>
							</td>
							<td align="right">开票性质：</td>
							<td align="left"><s:if test="applybill.applyWay!=2">
								<s:property
									value="typeManageService.getYXTypeManage(1012,invoiceService.getBillTypeFromRCPlan(key)).typeName " />
							</s:if></td>
							<td colspan="2" align="right">
							<div align="right">&nbsp;</div>
							</td>
						</tr>
					</s:iterator>

				</s:elseif>
			</s:elseif>

		</s:if>

		<tr>
			<td align="right">
			<div align="right">票据类型：</div>
			</td>
			<td align="left">
			<div align="left"><s:if test="applybill.applyWay!=2">
				<s:property
					value="typeManageService.getYXTypeManage(1004,applybill.billType).typeName" />
			</s:if> <s:else>
      	收据
      </s:else></div>
			</td>

			<td align="left">
			<div align="right">基准：</div>
			</td>
			<td colspan="3" align="left">
			<div align="left"><s:if test="applybill.base==0">
				<s:label id="hastax" value="不含税" />
			</s:if> <s:else>
				<s:label id="hastax" value="含税" />
			</s:else></div>
			</td>
		</tr>

		<tr>
			<td colspan="3" align="left">
			<div align="left">发票金额小写(含税)： <s:label id="taxmoney"
				name="applybill.billAmountTax" />
			</td>
			<td colspan="3" align="right">
			<div align="left">发票金额小写(不含税)： <s:label id="notaxmoney"
				name="applybill.billAmountNotax" /> <s:property value="" />
			</td>
		</tr>
		<tr>
			<td align="right" colspan="3">
			<div align="left">发票金额大写(含税)： <s:label id="bigMoney" />
			</td>
			<td align="right" colspan="3">
			<div align="left">发票金额大写(不含税)：<s:label id="bigMoneyN" />
			</td>
		</tr>
		<tr>
			<td align="right">
			<div align="right">一次出库：</div>
			</td>
			<td align="left"><s:if test="applybill.oneOut"> &radic; </s:if>
			<s:else> &times; </s:else></td>
			<td align="right">
			<div align="right">取票地点：</div>
			</td>
			<td align="left">
			<div align="left"><s:property value="applybill.billSpot" /></div>
			</td>
			<td align="right">
			<div align="right">库存组织：</div>
			</td>
			<td align="left">
			<div align="left"><s:property
				value="typeManageService.getYXTypeManage(1021,applybill.stockOrg).typeName" />
			</div>
			</td>
		</tr>
		<tr>
			<td align="right">
			<div align="right">开票内容：</div>
			</td>
			<td colspan="5" align="left">
			<div align="left"><%-- 
        <s:textarea   name="applybill.billContent" disabled="true"></s:textarea>
    --%> <s:property value="applybill.billContent" /></div>
			</td>
		</tr>
		<tr>
			<td align="right">
			<div align="right">备注：</div>
			</td>
			<td colspan="5" align="left"><label> <s:textarea
				cols="50" rows="4" name="remarks" value="%{applybill.remarks}"></s:textarea> </label></td>
		</tr>
		<s:if test="applybill.applyBillState == 4">
			<tr>
				<td align="right">
				<div align="right">退回理由：</div>
				</td>
				<td colspan="5" align="left"><label> <s:property
					value="applybill.returnReason" /> </label></td>
			</tr>
		</s:if>
		<tr class="bg_table01">
			<td colspan="7" align="center"><input type="button"
				name="button4" id="button4" value="修改" class="button01" onclick="save()"> <input
				type="button" name="button4" id="button4" value="关闭"
				class="button01" onClick="window.close();"></td>
		</tr>
	</table>
</s:form>
</body>
<script type="text/javascript">
	asd();
</script>
</html>
