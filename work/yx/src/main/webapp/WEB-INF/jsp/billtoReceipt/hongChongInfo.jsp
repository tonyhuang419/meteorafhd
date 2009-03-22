<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>退票申请确认</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function saveSubmit(planLegth,url,method){
	    var ids = $('invoiceIds').value;
		if(isExist(url, method, ids )){
			var flag ;
			if(planLegth < 1){
				flag = true;
			}else{
				flag = checkSumInput(planLegth);
			}
			if(flag){
				document.hongChongQuery.submit();
				window.close();
			}
			else{
				alert("退票金额与票据金额不等,不能提交申请")
			}	
		}
		else{
			alert("计划收据被多张发票关联")
		}	
	}
	//判断收据是否被关联过来两次
	function isExist(url, method, name){
		   var flag;
	       var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
			  if(responseText == '1'){
			     flag = false;
			  }
			  else if(responseText == '2'){
			  	 flag = true;
			  }
			  else{
			  	 flag = true;
			  }
		    });
		   myRequest.send("method="+method+"&invoiceIds="+name+"&randomNum="+Math.random());
		   return flag;
	}
	
	function checkInput(inputValue,planLegth,billOld){
		var billAmount = parseFloatNumber(billOld);   //以开票金额
		var hongChongAmount = parseFloatNumber(inputValue.value);   //文本框金额
		if(hongChongAmount > billAmount){
			inputValue.value = billOld;
		}
		checkSumInput(planLegth);
	}
	function checkSumInput(planLegth){
		var sumHongChongAmount = 0;   //总金额
		//计算文本款值
		for(var i=0;i<planLegth;i++){
			sumHongChongAmount = parseFloatNumber(sumHongChongAmount) + parseFloatNumber($('hongChongRelation'+i).value);
		}
		if(parseFloatNumber(sumHongChongAmount) == parseFloatNumber($('invoiceAmount').value)){
			return true;
		}
		else{
			return false;
		}
	}
</script>
</head>
<body>
<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:iterator id="erMessage" value="processResult.errorMessages">
	<font color="red"><strong><s:property value="#erMessage"/></strong></font><br/>
</s:iterator>
<s:form action="hongChongQuery" theme="simple" target="rightFrame">
	<s:hidden name="method" value="hongChongApply"></s:hidden>
	<table align="center" width="100%">
		<tr>
			<td>
				<div align="left" style="color:#000000">首页：退票>退票申请确认</div>
			</td>
		</tr>	
		<tr>
			<td>
		<s:iterator value="invoiceBillList" id="bill">
			<s:hidden name="invoiceIds" value="%{#bill[1].id}"></s:hidden>
			<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr class="bg_table01">
					<td  width="10%" colspan="6" align="left">申请单信息</td>
				</tr>
				<tr class="">
					<td  width="10%" align="right">申请部门：</td>
					<td  width="20%" align="left"><s:property value="foramlContractService.getDept(#bill[0].employeeId)" /></td>
					<td  width="10%" align="right">申请人：</td>
					<td  width="25%" align="left"><s:property value="#bill[3].name" /></td>
					<td  width="10%" align="right">申请日期：</td>
					<td  width="25%" align="left" id="datew"><s:property value="#bill[0].applyId"/></td>
				</tr>

				<tr>
					<td align="right" nowrap class="">合同客户：</td>
					<td align="left"><s:property value="#bill[4].name" /></td>
					<td align="right" class="">合同名称：</td>
					<td align="left" colspan="2" ><s:property value="#bill[2].conName" /></td>
					<td colspan="2" align="right">&nbsp;</td>
				</tr>
				<tr>
					<td align="right" nowrap>开票单位名称：</td>
					<td align="left"><s:property value="#bill[4].billName" /></td>
					<td align="right">地址：</td>
					<td align="left" id="address"><s:property value="#bill[4].billAdd" /></td>
					<td align="right" nowrap >联系电话：</td>
					<td align="left"  id="tax"><s:property value="#bill[4].billPhone" /></td>
				</tr>
				<tr>
					<td align="right" >开户银行：</td>
					<td align="left"  id="bank"><s:property value="#bill[4].billBank" /></td>
					<td  align="right">帐号：</td>
					<td  align="left" id="zhanghao"><s:property value="#bill[4].account" /></td>
					<td align="right" >税号：</td>
					<td align="left" id="shuihao">
					<s:property value="#bill[4].taxNumber" /></td>
				</tr>

				<tr>
					<td  align="right">开票性质：</td>
					<td align="left" >
					<s:property value="typeManageService.getYXTypeManage(1012,#bill[0].billNature).typeName "/>
					</td>
					<td  align="right">票据类型:</td>
					<td align="left"  id="piaotype">
					<s:property value="typeManageService.getYXTypeManage(1004,#bill[0].billType).typeName"/>
					</td>
					<td colspan="2" align="right">&nbsp;</td>
				</tr>
				<tr>
					<td  align="right">申请金额：</td>
					<td align="left"><s:property value="#bill[0].billAmountTax"/><s:hidden name="money" id="money"/></td>
					<td align="right" nowrap >基准：</td>
					<td  align="left"  id="base">
						<s:if test="#bill[0].base==0">
							<s:label id="hastax" value="不含税"/>
						</s:if>
						<s:else>
							<s:label id="hastax" value="含税"/>
					</s:else></td>
					<td colspan="2" align="right">&nbsp;</td>
				</tr>
				<tr>	
					<td align="right" >一次出库:</td>
					<td  align="left" ><s:if
						test="#bill[0].oneOut">
						&radic;		
					</s:if> <s:else>
						&times;
					</s:else></td>
					<td align="right" >
					<div align="right">取票地点：</div>
					</td>
					<td  align="left" >
					<s:property value="#bill[0].billSpot"/>
					</td>
					<td align="right" >库存组织：</td>
					<td   align="left" >
					 <s:property value="typeManageService.getYXTypeManage(1021,#bill[0].stockOrg).typeName "/>
					</td>
				</tr>

				<tr>
					<td align="right" >开票内容：</td>
					<td colspan="5" align="left" >
					<s:property value="#bill[0].billContent"/>
					</td>					
				</tr>

				<tr>
					<td align="right" >备注：</td>
					<td colspan="5" align="left" >
					<s:property value="#bill[0].remarks"/>
					</td>
				</tr>
				<tr class="bg_table01">
					<td colspan="6" align="left" >发票信息
					</td>
				</tr>
				<tr class="">
					<td  width="10%" align="right">发票号：</td>
					<td  width="20%" align="left"><s:property value="#bill[1].invoiceNo" /></td>
					<td  width="10%" align="right">开票金额：</td>
					<td  width="25%" align="left">
						<s:hidden name="invoiceAmount" value="%{#bill[1].invoiceAmount}"></s:hidden>
						<s:property value="#bill[1].invoiceAmount" />
					</td>
					<td  width="10%" align="right">开票日期：</td>
					<td  width="25%" align="left"><s:property value="#bill[1].invoiceDate"/></td>
				</tr>
		<s:if test="hongChongPlanList.size() != 0 " >
				<tr>
					<td colspan="6">
			<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td class="bg_table01">合同号<s:property value="hongChongPlanList.size()"/></td>
					<td class="bg_table01">项目号</td>
					<td class="bg_table01">合同名称</td>
					<td  class="bg_table01">客户名称</td>
					<td  class="bg_table01">负责部门</td>
					<td  class="bg_table01">阶段</td>
					<td  class="bg_table01">计划开票日期</td>
					<td  class="bg_table01">开票性质</td>
					<td  class="bg_table01">发票类型</td>
					<td  class="bg_table01">开票金额</td>
					<td  class="bg_table01">已开票金额</td>
					<td  class="bg_table01">退票金额</td>
				</tr>
				<s:iterator value="hongChongPlanList" id="month" status="status">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
						<td align="left"><s:property value="#month[2].conId" /></td>
						<td align="left"><s:property value="#month[3].conItemId" /></td>
						<td align="left"><s:property value="#month[2].conName" /></td>
						<td align="left"><s:property value="#month[1].name" /></td>
						<td align="left">
							<s:if test="#month[3] != null">
								<s:property	value="typeManageService.getYXTypeManage(1018,#month[3].itemResDept).typeName" />
							</s:if>
							<s:else>
								<s:property value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName"/>
							</s:else>
						</td>
						<td align="left">
							<s:property value="typeManageService.getYXTypeManage(1023,#month[4]).typeName" />
					    </td>
						<td align="center"><s:property
							value="#month[0].realPredBillDate" /></td>
						<td align="left"><s:property
							value="typeManageService.getYXTypeManage(1012,#month[0].billNature).typeName" /></td>
						<td align="left"><s:property
							value="typeManageService.getYXTypeManage(1004,#month[0].billType).typeName" /></td>
						<td align="right"><s:property
							value="#month[0].realBillAmount" /></td>
						<td align="right"><s:property
							value="#month[0].billInvoiceAmount" />
							</td>
						<td align="right">
							<s:hidden name="hongChongRelation[%{#status.index}].realContractBillandRecePlan" value="#month[0].realConBillproSid"></s:hidden>
							<s:textfield id="hongChongRelation%{#status.index}" name="hongChongRelation[%{#status.index}].relateAmount" size="10" 
							onblur="formatInputNumber(this);checkInput(this,'%{hongChongPlanList.size}','%{#month[0].billInvoiceAmount}')"></s:textfield>
						</td>
						
					</tr>
				
				</s:iterator>
				</table>
					</td>
				</tr>
			</s:if>
				<tr class="bg_table01">
					<td colspan="6" align="center" >
						<input type="button" name="1" value="确认申请" class="button01" onclick="saveSubmit('<s:property value="hongChongPlanList.size()"/>','/yx/billtoReceipt/hongChongQuery.action','checkReceipt')">
						<input type="button" name="2" value=" 取  消 " class="button01" onclick="window.close()">
					</td>
				</tr>
			</table>
		</s:iterator>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>