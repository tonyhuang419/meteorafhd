<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<html>
	<head>
		<title>打印开票申请</title>
	</head>
	<script type="text/javascript">

			function downLoadApplyBill(){
				var checkedBoxes = $$("input[name=ids][checked]");
				if(checkedBoxes.length == 0)
				{
					alert("请选择要打印的开票申请！");
					return;
				}
				if(checkedBoxes.length > 1)
				{
					alert("只能选择一个打印！");
					return;
				}
				var url="applyBillPDF.action?method=downLoadApplyBill&paramId="+checkedBoxes[0].value;
				downFrame.location=url;
			}
			function printList()
			{
				var checkedBoxes = $$("input[name=ids][checked]");
				if(checkedBoxes.length == 0)
				{
					alert("请选择要打印的开票申请！");
					return;
				}
				if(checkedBoxes.length > 1)
				{
					alert("只能选择一个打印！");
					return;
				}
				window.open("applyBillPDF.action?method=applyBillJaspser&paramId="+checkedBoxes[0].value,"","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
			}
			function confirmProcessed(){
				var checkedBoxes = $$("input[name=ids][checked]");
				if(checkedBoxes.length == 0){
					alert("请选择已经处理的申请单");
					return;
				}
				if(confirm("确定已经处理吗？")){
					$('method').value="processApplyBill";
					document.forms(0).submit();
				}
			}
			//取消处理
			function returnProcessed(){
				var checkedBoxes = $$("input[name=ids][checked]");
				if(checkedBoxes.length == 0){
					alert("请选择一个已经处理的申请单");
					return;
				}
				else if(checkedBoxes.length == 1){
					if(confirm("确定取消处理吗？")){
						$('method').value="returnProcessed";
						document.forms(0).submit();
					}
				}
				else{
					alert("只能选择一个已经处理的申请单");
					return;
				}
			}
			//取消确认
			function returnConfirm(){
				var checkedBoxes = $$("input[name=ids][checked]");
				if(checkedBoxes.length == 0){
					alert("请选择一个确认通过的申请单");
					return;
				}
				else if(checkedBoxes.length == 1){
					if(confirm("确定取消确认吗？")){
						$('method').value="returnConfirm";
						document.forms(0).submit();
					}
				}
				else{
					alert("只能选择一个确认通过的申请单");
					return;
				}
			}
  </script>
 <body style="margin: 0px;">
		<div align="left" style="color: #000000">
			当前页面：开票管理 -> 打印开票申请
		</div>
		<s:iterator id="suMessage" value="processResult.successMessages">
			<font color="green"><strong><s:property value="#suMessage"/></strong></font><br/>
		</s:iterator>
		<s:iterator id="erMessage" value="processResult.errorMessages">
			<font color="red"><strong><s:property value="#erMessage"/></strong></font><br/>
		</s:iterator>
		<iframe style="display: none;" id="downFrame"></iframe>
		<s:form action="createInvoice.action" theme="simple">
			<s:hidden name="method" value="printBill"></s:hidden>
			<s:hidden name="customerId"></s:hidden>
			<s:hidden name="billApplyNum"></s:hidden>
			<s:hidden name="beginApplyDate"></s:hidden>
			<s:hidden name="endApplyDate"></s:hidden>
			<s:hidden name="monthPlanYear"></s:hidden>
			<s:hidden name="monthPlanMonth"></s:hidden>
			<s:hidden name="applyStatus"></s:hidden>
			<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
			 <tr>
 			   <td align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
 			 </tr>
				<tr class="bg_table02">
					<td align="center" class="bg_table03">
							<input type="button" value="打印申请单" onClick="printList();"
								class="button01">
								<input type="button" value="下载申请单" onClick="downLoadApplyBill();"
								class="button01">
							<input type="button" value="确认处理" onClick="confirmProcessed();" class="button01">
							<input type="button" value="取消处理" onClick="returnProcessed()" class="button01">
							<input type="button" value="取消确认" onClick="returnConfirm()" class="button01">
					</td>
				</tr>
			</table>
			<table align="center" border=1 cellpadding="1" cellspacing=1 
				width="100%" id="billApplyList"  bordercolor="#808080" style="border-collapse: collapse;">
				<tr align="center">
					<td nowrap class="bg_table01">
						</div>
					</td>
					<td nowrap class="bg_table01">
							开票申请编号
					</td>
					<td nowrap class="bg_table01">					
							合同号
					</td>
					<td nowrap class="bg_table01">					
							项目号
					</td>
					<td nowrap class="bg_table01">					
							销售员
					</td>
					<td nowrap class="bg_table01">
							申请金额
					</td>
					<td nowrap class="bg_table01">
							开票性质
					</td>
					<td nowrap class="bg_table01">
						票据类型
					</td>
					<td nowrap class="bg_table01">
							甲方接收人
					</td>
					<td nowrap class="bg_table01" width="20%">
							开票内容
					</td>
					<td nowrap class="bg_table01">
						申请类型
					</td>
					<td nowrap class="bg_table01">
						申请日期
					</td>
					<td nowrap class="bg_table01">
						申请状态
					</td>
				</tr>
			<s:iterator value="info.result" id="bill">
				<tr onMouseOver="this.bgColor='#BBBBFF'; " onMouseOut="this.bgColor='#FFFFFF';">
					<td>	
						<input type="checkbox" name="ids" id="ids"
									value="<s:property value="#bill[0].billApplyId"/>">
					</td>
					<td><s:property value="#bill[0].billApplyNum" /></td>
					<td><s:property value="#bill[1]" /></td>
					<td>
						<s:iterator id="s" value="itemNoList.get(#bill[0].billApplyId)"  status="stat2"  >    
            					<s:property value="s"/><s:if test="!#stat2.last"><br/></s:if>
           					 </s:iterator>
					</td>
					<td align="left"><s:property value="#bill[2]" /></td>
					<td align="right"><s:property value="#bill[0].billAmountTax" /></td>
					<td><s:property	value="typeManageService.getYXTypeManage(1012,#bill[0].billNature).typeName" /></td>
					<td><s:property value="typeManageService.getYXTypeManage(1004,#bill[0].billType).typeName" /></td>
					
					<td><s:property value="#bill[0].firstReceiveMan" /></td>
					<td><s:property value="#bill[0].billContent" /></td>
					<td>
						<s:if test="#bill[0].isNoContract==0">
							已签申请
						</s:if>
						<s:elseif test="#bill[0].isNoContract==1">
							未签申请
						</s:elseif>
					</td>
					<td><s:property value="#bill[0].applyId" /></td>
					<td>
						<s:if test="#bill[0].applyBillState==1">保存</s:if>
						<s:if test="#bill[0].applyBillState==2">待确认</s:if>
						<s:if test="#bill[0].applyBillState==3">确认通过</s:if>
						<s:if test="#bill[0].applyBillState==4">确认退回</s:if>
						<s:if test="#bill[0].applyBillState==5">已开票</s:if>
						<s:if test="#bill[0].applyBillState==7">已处理</s:if>
						<s:if test="#bill[0].applyBillState==6">已签收</s:if>
					</td>
				</tr>
			</s:iterator>
			</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04">
						<baozi:pages value="info" beanName="info" formName="forms(0)" />
					</td>
				</tr>
			</TABLE>
		</s:form>
	</body>
</html>
