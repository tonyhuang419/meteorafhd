<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>已签开票申请计划</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function check()
	{
		if(!validate()){
			if($(applyBill).toQueryString() != ""){
				location.href="../billtoReceipt/applyBill.action?method=together&"+$(applyBill).toQueryString();
			}
		}
	}
	
	function checkAmount()
	{
		alert(document.getElementById("realBillAmount").value);
	}
	//验证表单
	function validate(){
		var ev2=new Validator();
		
		with(applyBill){
			<s:iterator value="info.result" id="month" status="status">
		       ev2.test("notblank","<s:property value="#month[2].conId"  />开票内容为空!",$('content<s:property value="#status.index"/>').value);
		       ev2.test("notblank","<s:property value="#month[2].conId"  />开票金额为空!",$('amount<s:property value="#status.index"/>').value);
		    </s:iterator>
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	
	
</script>
<body >
<s:form action="applyBill" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<div align="left"> 
	<p>当前页面：开票管理 -> 开票申请</p>
	<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
	</div>
</td>
		</tr>
		<tr>
			<td>
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td class="bg_table01">合同号</td>
					<td class="bg_table01">合同名称</td>
					<td  class="bg_table01">客户名称</td>
					<td  class="bg_table01">负责部门</td>
					<td  class="bg_table01">阶段</td>
					<td  class="bg_table01">计划开票日期</td>
					<td  class="bg_table01">开票性质</td>
					<td  class="bg_table01">发票类型</td>
					<td  class="bg_table01">开票金额</td>
				</tr>
				<s:set name="totalApplyBillAmount" value="0.00"></s:set>
				<s:iterator value="billList" id="month" status="status">
				<input type="hidden" name="realPanid" value="<s:property value="#month[0].realConBillproSid"/>"/>
					<tr align="center" class="bg_table02">
						<td align="left"><s:property value="#month[2].conId"  /></td>
						<td align="left"><s:property value="#month[2].conName" /></td>
						<td align="left"><s:property value="#month[1].name" id="cusName"/></td>
						<td align="left">
							<s:if test="#month[4] != null">
								<s:property	value="typeManageService.getYXTypeManage(1018,#month[4]).typeName" />
							</s:if>
							<s:else>
								<s:property	value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName" />
							</s:else>
						</td>
						<td align="left"><s:property
							value="typeManageService.getYXTypeManage(1023,#month[3]).typeName"  /></td>
						<td align="center"><s:property
							value="#month[0].realPredBillDate" /></td>
						<td align="left"><s:property
							value="typeManageService.getYXTypeManage(1012,#month[0].billNature).typeName" /></td>
						<td align="left"><s:property
							value="typeManageService.getYXTypeManage(1004,#month[0].billType).typeName"  /></td>
						<td align="right">
						<s:property value="#month[0].realBillAmount"/><s:hidden name="realBillAmount" value="%{#month[0].realTaxBillAmount}" /></td>
					</tr>
					<s:set name="totalApplyBillAmount" value="#totalApplyBillAmount+#month[0].realBillAmount"></s:set>
				</s:iterator>
				<tr class="bg_table02">
					<td><font color="red">*</font>开票内容:</td>
					<td colspan="3"><s:textarea name="billContentList" value="%{#month[0].billContent}" rows="3" cols="30"/></td>
					<td>库存组织:</td>
					<td colspan="2"><s:select name="stockOrgList" list="stockOrgList" listKey="typeSmall" listValue="typeName" required="true"></s:select></td>
						<td align="right">
							总额：
							</td>
						<td align="left"><s:property value="#totalApplyBillAmount"/></td>
				</tr>
				<tr class="bg_table02">
					<td>备注:</td>
					<td colspan="3">
						<s:textarea name="remark" rows="3" value="%{#month[2].partyAConId}/%{#month[5]}" cols="30"/></td>
					<td>是否一次出库:</td>
					<td  colspan="2">
						<s:checkbox name="oneOut"></s:checkbox>
					</td>
					<td>
						取票地点:
					</td>
					<td>
						<s:textfield name="billSpot" value="宝山"></s:textfield>
					</td>
					</tr>
				<tr class="bg_table02">
					<td>甲方接收人:</td>
					<td colspan="3">
						<s:textarea name="firstReceiveMan" rows="3" cols="30"/></td>
					<td></td>
					<td  colspan="4">
					</td>
					</tr>
				<tr align="center">
						<td colspan="11" class="bg_table01" >
							<input type="button" class="button01" name="submit" value=" 确定合并 " onclick="check()"/>
							<input type="button" name="reset" class="button01" value=" 关  闭 " onclick="window.close()" />
						</td>
					</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>
