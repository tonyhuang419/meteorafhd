<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>新增外协付款申请</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
	<script type="text/javascript">
		function searchCon(){
			var conNo = document.systemOpAssistance.assistanceConNo.value;
			if(conNo!=null&&conNo.length>0){
				document.systemOpAssistance.submit();
			}
		}
		function doSave()
		{
			if(!validate()){
				var j = checkCheckBoxChekced();
				if(j==0){
					alert("请选择阶段！");
				}else{
					var prepPay = document.systemOpAssistance.isPrepPay.checked; 
					if(prepPay){
						document.systemOpAssistance.method.value="saveApplyPayInfo";
						document.systemOpAssistance.submit();
					}else{
						var a = document.systemOpAssistance.elements('assistanceContract.id').value;
						var flag = checkExistsPrepPay(a);
						if(flag){
	      					var hasPrep = window.confirm("该外协供应商下面有预付款信息，是否关联？");
	      					if(hasPrep){
	      						document.systemOpAssistance.method.value="relationPrepList";
								document.systemOpAssistance.submit();
							}else{
								document.systemOpAssistance.method.value="saveApplyPayInfo";
								document.systemOpAssistance.submit();
							}
						}else{
							document.systemOpAssistance.method.value="saveApplyPayInfo";
							document.systemOpAssistance.submit();
						}
					}
				}
			}
		}
		function validate(){
			var ev2=new Validator();
			ev2.test("notblank","请输入日期",$('applyPayDate').value);
			ev2.test("dateYX","付款申请开始日期格式不正确！",$('applyPayDate').value);
			ev2.writeErrors(errorsFrame,"errorsFrame");
			if(ev2.size()>0){
				return true;
			}
			return false;
			
		}
		function checkCheckBoxChekced(){
			var checkNode = document.getElementsByName("sectionIds");
			var j=0;
			if(checkNode != null && checkNode.length > 0){
				for(var k = 0 ; k <checkNode.length; k ++ ){
					if(checkNode[k].checked){
					 j++;
					}
				}
			}
			return j;
		}
		
		function checkExistsPrepPay(assistanceId){
		var url = "/yx/assistance/assistanceApplyPay.action";
		var method = "checkExistsPrepPay";
		var flag = false;
		 var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		      if(responseText == "true"){
			 	flag = true;
			 }else{
			 	flag = false;
			 }
		    });
		   myRequest.send("method="+method+"&assistanceId="+assistanceId+"&randomNum="+Math.random());
		   return flag;
}
	</script>
</head>
<body>
<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:form action="systemOpAssistance" theme="simple">
	<s:hidden name="method" value="searchAssiConThenPayApply"></s:hidden>
	<s:hidden name="assistanceContract.id"></s:hidden>
	<table align="center" width="100%">
	<tr>
		<tD>
			<table>
				<tr>
					<td>
					外协合同号：
					</td>
					<td>
						<s:textfield name="assistanceConNo"></s:textfield>
					</td>
					<td><input type="button" value="搜索" class="button01" onclick="searchCon();"/></td>
				</tr>
			</table>
		</tD>
	</tr>
	<s:if test="addSign!=null&& addSign==1">
		<tr>
		<td>
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr align="center" class="bg_table02">
	           <td colspan="4" class=""><strong>外协合同主体信息</strong></td>
		      </tr>
			<tr  class="bg_table02">
	            <td  align="right">主体合同号：</td>
		    	<td align="left"> <s:property value="contract.conId"/></td>
		      	<td  align="right">合同名称：</td>
		      	<td  align="left"><s:property value="contract.conName"/></td>
		      </tr>
			 <tr class="bg_table02">
			     <td  align="right">项目号：</td>
			     <td  align="left"><s:property value="contractItem.conItemId"/></td>
			     <td align="right" >成本中心：</td>
			     <td align="left" ><s:property value="typeManageService.getYXTypeManage(1018,contractItem.itemResDept).typeName"/></td>
		      </tr>
			<tr class="bg_table02">
			      <td align="right">外协合同号：</td>
			      <td align="left"><s:property value="assistanceContract.assistanceId"/></td>
			      <td align="right">外协合同名：</td>
			      <td align="left"><s:property value="assistanceContract.assistanceName"/></td>
		      </tr>
			<tr class="bg_table02">
			      <td align="right">合同金额：</td>
			      <td align="left"><s:property value="assistanceContract.contractMoney"/></td>
			      <td align="right">已申请金额：</td>
			      <td align="left">
			      	<s:if test="assistanceContract.hasPayAmount != null">
			      		<s:property value="assistanceContract.hasPayAmount"/>
			      	</s:if>
			      	<s:else>
			      	0.00
			      	</s:else>
			    </td>
		      </tr>
		      <tr class="bg_table02">
			      <td align="right">已开票金额：</td>
			      <td align="left">
			      	<s:if test="assistanceContract.ticketMoney != null">
			      		<s:property value="assistanceContract.ticketMoney"/>
			      	</s:if>
			      	<s:else>
			      	0.00
			      	</s:else>
			      <td align="right">已开收据金额：</td>
			      <td align="left">
			      	<s:if test="assistanceContract.receiptAmount != null">
			      		<s:property value="assistanceContract.receiptAmount"/>
			      	</s:if>
			      	<s:else>
			      	0.00
			      	</s:else>
			    </td>
		      </tr>
		      <tr class="bg_table02">
			        <td colspan="4" align="right"><hr></td>
	           </tr>
		          <tr class="bg_table02">
				      <td align="right">供应商名称：</td>
				      <td align="left"><s:property value="supply.supplierName"/>
				        </td>
				      <td align="right">代码：</td>
				      <td align="left"><s:property value="supply.supplierCode"/></td>
			    </tr>
				<tr class="bg_table02" >
				  <td align="right">开户银行：</td>
				  <td align="left"><s:property value="supply.billBank"/></td>
				  <td align="right">银行帐号：</td>
				  <td align="left"><s:property value="supply.billAccount"/></td>
			  </tr>
				<tr class="bg_table02">
				  <td colspan="4" align="right"><hr></td>
			  </tr>
	          </table>
		</td>
		</tr>
		<tr>
		<td>
			<table align="center" width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr class="bg_table02">
					<td align="center">
						选择
					</td>
					<td align="center">
						阶段名称
					</td>
					<td align="center">
						辅助阶段
					</td>
					<td align="center">
						 阶段金额
					</td>
					<td align="center">
						阶段日期
					</td>
				</tr>
				<s:iterator value="sectionList">
				<tr class="bg_table02">
					<td align="right">
					<s:if test="!assistanceService.checkSectionIsRelation(id)">
						<input type="checkbox" name="sectionIds" value="<s:property value="id"/>"/>
					</s:if>
					<s:else>
						<input type="checkbox" name="sectionIds" value="<s:property value="id"/>" disabled="true"/>
					</s:else>
					</td>
					<td>
						<s:property value="contractService.findStageName(assistanceStageSId)" />
					</td>
					<td >&nbsp;
						<s:property value="sectionRemarks" />
					</td>
					<td align="right">
						<s:property value="sectionAmount" />
					</td>
					<td align="center">
						<s:property value="sectionBillTime" />
					</td>
				</tr>
				</s:iterator>
			</table>
		</td>
		</tr>
		<tr class="bg_table02">
			<td>
				<table>
					<tr>
					<tD>付款申请日期：</tD>
					<tD>
						<input type="text" name="applyPayDate" size="12"/>
						<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('applyPayDate')" align=absMiddle alt=""  />
					</tD>
					<td>
						是否预付：<s:checkbox name="isPrepPay"></s:checkbox>
					</td>
					<td>
						<input type="button" value="付款申请" class="button01" onclick="doSave();"/>
					</td>
					</tr>
				</table>
			</td>
		</tr>
	</s:if>
	</table>
</s:form>
</body>
</html>