<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
	<head>
		<title>开票申请管理</title>
	</head>
	<script type="text/javascript">
		function isTaxNoOfApplyBillNeeded(applyBillId){
				var isNeed=false;
				var jsonRequest = new Request.JSON({async:false,url:'/yx/jsonData.action?method=isTaxNoOfApplyBillNeeded&applyBillId='+applyBillId, onComplete: function(jsonObj){
					if(jsonObj!=null && jsonObj.jsonData !=null ){
						isNeed = jsonObj.jsonData.isTaxNumberBlank;
					}else{
						alert("验证税号出现失败");
					}
				}}).get({randerNum:Math.random()});	
				return isNeed;
		}
  		function updateInvoice()
		{		
			var checkNode=document.getElementsByName("ids");
			if(checkNode!=null&&checkNode.length>0)
			{
				var checkedCount = 0;
				for(var i=0;i<checkNode.length;i++)
				{
					if(checkNode[i].checked==true)
					{
						checkedCount++;
					}
				}			
				if(checkedCount==0)
				{
					alert("请选择一项进行修改");
					return;
				}else if(checkedCount > 1){
					alert("只能选择一项进行修改");
					return;
				}
				for(var i=0;i<checkNode.length;i++)
				{
					if(checkNode[i].checked==true)
					{
						//ture未签，长度5 false已签，长度6
						//var signValue = checkNode[i].parentNode.parentNode.all.sign.value;

						//if(signValue.length == 5){
							document.forms(0).action="createInvoice.action?sid="+checkNode[i].value;
							bb="1";
							document.forms(0).method.value="updateApply";
							document.forms(0).submit();
							break;
						//}
						//else{
						//	document.forms(0).action="/yx/contract/formalContractManage/formalContractInvoiceApply.action?applyBillSid="+checkNode[i].value;
						//	document.forms(0).method.value="updateApplyBill";
						//	bb="1";
						//	document.forms(0).submit();
						//	break;
						//}		
					}
				}
			}
		}
<%--		function deleteInvoice()--%>
<%--		{--%>
<%--			var checkNode=document.getElementsByName("ids");--%>
<%--			var bb="0";--%>
<%--			if(checkNode!=null&&checkNode.length>0)--%>
<%--			{--%>
<%--				for(var i=0;i<checkNode.length;i++)--%>
<%--				{--%>
<%--					if(checkNode[i].checked==true)--%>
<%--					{--%>
<%--							document.forms[0].action="createInvoice.action?method=findOneInvoice&sid="+checkNode[i].value;--%>
<%--							bb="1";--%>
<%--							document.forms(0).submit();--%>
<%--							break;--%>
<%--					}--%>
<%--				}--%>
<%--				if(bb=="0")--%>
<%--				{--%>
<%--					alert("请选择一项进行修改");--%>
<%--				}--%>
<%--			}--%>
<%--		}--%>
		
		function refreshSuper()	
		{
			document.forms(0).method.value="findInvoiceApplications";
			document.forms(0).submit();
		}
		
		function updateN()
			{
				var idNode=document.getElementsByName("ids");
				var flag=false;
				if(idNode!=null&&idNode.length>0)
				{
					for(var k=0;k<idNode.length;k++){
						if(idNode[k].checked)
						{
							flag=true;
							break;						
						}	
					}
				}
				if(!flag)
				{
					alert("请选择要删除的申请！");
					return ;
				}
				if(confirm("是否确认删除"))
			    {
				 document.forms(0).method.value="deleteApplications";
				 document.forms[0].submit();
				}
			}
			
			//已签开票申请open
			function openOneInvoiceHasContract(oid)
			{
				 window.open("/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=showInvoice&applyBillSid="+oid+"&opType=00","","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=850");
			}
			
			//未签开票申请open
			function openOneInvoice(oid)
			{
				 window.open("createInvoice.action?method=showInvoiceInfor&applyBillId="+oid+"&opType=00","","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=850");
			}
			function printList()
			{
				var idNode=document.getElementsByName("ids");
				var flag=false;
				var aid=-1;
				if(idNode!=null&&idNode.length>0)
				{
					for(var k=0;k<idNode.length;k++){
						if(idNode[k].checked)
						{
							flag=true;
							aid=idNode[k].value;
							break;
						}
						
					}
				}
				if(!flag)
				{
					alert("请选择要打印的开票申请！");
					return;
				}
				var sum=0;
				if(idNode!=null&&idNode.length>0)
				{
					for(var k=0;k<idNode.length;k++){
						if(idNode[k].checked)
						{
							sum++;
						}
						
					}
				}
				if(sum>1)
				{
					alert("只能选择一个打印！");
					return;
				}
				window.open("applyBillPDF.action?method=applyBillJaspser&paramId="+aid,"","menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
			}
			function submitApp()
			{
				var idNode=document.getElementsByName("ids");
				var flag=false;
				if(idNode!=null&&idNode.length>0)
				{
					for(var k=0;k<idNode.length;k++){
						if(idNode[k].checked)
						{
							flag=true;
							break;							
						}			
					}
					for(var k=0;k<idNode.length;k++){
						if(idNode[k].checked)
						{
							if(isTaxNoOfApplyBillNeeded(idNode[k].value)){
								alert("申请单中开票客户没有税号");
								return;
							}						
						}			
					}					
				}
				if(!flag)
				{
					alert("请选择要提交的申请！");
					return ;
				}
				
				var f=window.confirm("您确认要提交您选择的申请吗？");
				if(f)
				{
					document.forms(0).method.value="submitApplications";
					document.forms(0).submit();
				}
			}
  </script>
	<s:if test="succSave == 2">
		<script language="javascript"> 
			alert("修改成功");
		</script>
	</s:if>
	<s:elseif test="succSave == 3">
		<script language="javascript"> 
			alert("提交成功");
		</script>
	</s:elseif>    
    <s:elseif test="succSave == 4">
		<script language="javascript"> 
			alert("删除成功");
		</script>
	</s:elseif> 

	<body style="margin: 0px;">
		<div align="left" style="color: #000000">
			当前页面：开票管理 -> 开票申请管理
		</div>
		<s:form action="createInvoice.action" theme="simple">
			<s:hidden name="method"></s:hidden>
			<s:hidden name="conNumX"></s:hidden>
			<s:hidden name="itemNumX"></s:hidden>
			<s:hidden name="customerId"></s:hidden>
			<s:hidden name="billApplyNum"></s:hidden>
			<s:hidden name="beginApplyDate"></s:hidden>
			<s:hidden name="endApplyDate"></s:hidden>
			<s:hidden name="monthPlanYear"></s:hidden>
			<s:hidden name="monthPlanMonth"></s:hidden>
			<s:hidden name="applyStatus"></s:hidden>
			<s:hidden name="hasSigned"></s:hidden>
			<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
			 <tr>
 			   <td align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
 			 </tr>
				<tr class="bg_table02">
					<td align="right" class="bg_table03">
						<div align="center">
							<input type="button" name="SearchBtn3" value="提交申请"
								class="button01" onClick="submitApp();">
							<input type="button" name="SearchBtn2" value="修改"
								class="button01" onClick="updateInvoice();">
							<input type="button" name="SearchBtn" value="删除" class="button01"
								onClick="updateN();">
							<input type="button" value="打印申请单" onClick="printList();"
								class="button01">
						</div>
					</td>
				</tr>
			</table>
			<table align="center" border=1 cellpadding="1" cellspacing=1 
				width="100%" id="billApplyList"  bordercolor="#808080" style="border-collapse: collapse;">
				<tr align="center">
					<td nowrap class="bg_table01">
						<div align="center"></div>
					</td>
					<td nowrap class="bg_table01">
						<div align="center">
							开票申请编号
						</div>
					</td>
					<td nowrap class="bg_table01">
						<div align="center">
							合同号
						</div>
					</td>
					<td nowrap class="bg_table01">
						<div align="center">
							项目号
						</div>
					</td>
					<td nowrap class="bg_table01">
						<div align="center">
							申请金额
						</div>
					</td>
					<td nowrap class="bg_table01">
						<div align="center">
							开票性质
						</div>
					</td>
					<td nowrap class="bg_table01">
						票据类型
					</td>
					<td nowrap class="bg_table01" width="20%">
						<div align="center">
							开票内容
						</div>
					</td>
					<td nowrap class="bg_table01">
						申请类型
					</td>
					<td nowrap class="bg_table01">
						统一
						<br>
						开票
					</td>
					<td nowrap class="bg_table01">
						申请日期
					</td>
					<td nowrap class="bg_table01">
						计划日期
					</td>
					<td nowrap class="bg_table01">
						甲方接收人
					</td>
					<td nowrap class="bg_table01">
						申请状态
					</td>
				</tr>

				<s:iterator id="applybill" value="info.result">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
						onMouseOut="this.bgColor='#FFFFFF';">
						<s:hidden name="sign" value="${applybill[0].initIsNoContract} " />
						<td>
							<s:if test="#applybill[0].applyBillState==1">
								<input type="checkbox" name="ids" id="ids"
									value="<s:property value="#applybill[0].billApplyId"/>">
							</s:if>
							<s:if test="#applybill[0].applyBillState==4">
								<input type="checkbox" name="ids" id="ids"
									value="<s:property value="#applybill[0].billApplyId"/>">
							</s:if>
							<s:if test="#applybill[0].applyBillState==2">
								<input type="checkbox" name="ids" id="ids" disabled="disabled"
									value="<s:property value="#applybill[0].billApplyId"/>">
							</s:if>
							<s:if test="#applybill[0].applyBillState==3">
								<input type="checkbox" name="ids" id="ids" disabled="disabled"
									value="<s:property value="#applybill[0].billApplyId"/>">
							</s:if>
						</td>

						<s:if test="#applybill[0].initIsNoContract==1">
							<td onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0  ">
							<td onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						<s:property value="#applybill[0].billApplyNum" />
						</td>

<%-- 
						<s:if test="#applybill[0].initIsNoContract==1">
							<td
								onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0 ">
							<td
								onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						<s:property value="#applybill[0].applyId" />
						</td>
--%>


						<s:if test="#applybill[0].initIsNoContract==1">
							<td onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0 ">
							<td onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						<s:property value="#applybill[1]" />
						</td>
						
						<s:if test="#applybill[0].initIsNoContract==1">
							<td
								onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0 ">
							<td
								onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						     <s:iterator id="s" value="itemNoList.get(#applybill[0].billApplyId)"  status="stat2"  >    
            					<s:property value="s"/><s:if test="!#stat2.last"><br/></s:if>
           					 </s:iterator>
						</td>

						<s:if test="#applybill[0].initIsNoContract==1">
							<td
								onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
								<div align="right">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0">
							<td
								onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
								<div align="right">
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						<s:property value="#applybill[0].billAmountTax" />
						</div>
						</td>

						<s:if test="#applybill[0].initIsNoContract==1  ">
							<td	onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')"><div align="left">
								<s:property	value="typeManageService.getYXTypeManage(1012,#applybill[0].billNature).typeName" />
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0">
							<td		onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')"><div align="left">
								<s:property	value="typeManageService.getYXTypeManage(1012,#applybill[0].billNature).typeName" />
						</s:elseif>
						<s:else>
							<td><div align="left">
						</s:else>
						</div></td>

						<s:if test="#applybill[0].initIsNoContract==1">
							<td	onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')"><div align="left">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0">
							<td 	onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')"><div align="left">
						</s:elseif>
						<s:else>
							<td><div align="left">
						</s:else>
						<s:property value="typeManageService.getYXTypeManage(1004,#applybill[0].billType).typeName" />
						</div></td>

						<s:if test="#applybill[0].initIsNoContract==1">
							<td		onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
								<div align="left" style="word-break: break-all;">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0">
							<td
								onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
								<div align="left">
									<%--<div align="left" style="word-break: break-all;">--%>
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						<s:property value="#applybill[0].billContent" />
						</div>
						</td>

						<s:if test="#applybill[0].initIsNoContract==1">
							<td onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0">
							<td	onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						<s:if test="#applybill[0].isNoContract==0">
					已签申请
				</s:if>
						<s:elseif test="#applybill[0].isNoContract==1">
					未签申请
				</s:elseif>
						</td>

						<%--首先判断是否已签合同，如果是已签合同，那么再判断是否统一开票 --%>

						<s:if test="#applybill[0].initIsNoContract==1">
							<td
								onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
								&times;
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0   ">
							<td
								onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
								<s:if
									test="invoiceService.getIsUniteBill(#applybill[0].billApplyId)">
							&radic;
						</s:if>
						<s:else>&times;</s:else>
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						</td>


						<s:if test="#applybill[0].initIsNoContract==1">
							<td onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0 ">
							<td onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						<s:property value="#applybill[0].applyId" />
						</td>
						

						<s:if test="#applybill[0].initIsNoContract==1">
							<td onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0 ">
							<td onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						<s:property value="#applybill[2]" />
						</td>

						<s:if test="#applybill[0].initIsNoContract==1">
							<td onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0 ">
							<td onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')">
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						<s:property value="#applybill[0].firstReceiveMan" />
						</td>
						
						
						<s:if test="#applybill[0].initIsNoContract==1">
							<td
								onclick="openOneInvoice('<s:property value="#applybill[0].billApplyId"/>')"><div align="left">
						</s:if>
						<s:elseif test="#applybill[0].initIsNoContract==0">
							<td
								onclick="openOneInvoiceHasContract('<s:property value="#applybill[0].billApplyId"/>')"><div align="left">
						</s:elseif>
						<s:else>
							<td>
						</s:else>
						<s:if test="#applybill[0].applyBillState==1">保存</s:if>
						<s:if test="#applybill[0].applyBillState==2">待确认</s:if>
						<s:if test="#applybill[0].applyBillState==3">确认通过</s:if>
						<s:if test="#applybill[0].applyBillState==4">确认退回</s:if>
						<s:if test="#applybill[0].applyBillState==5">已开票</s:if>
						<s:if test="#applybill[0].applyBillState==7">已处理</s:if>
						<s:if test="#applybill[0].applyBillState==6">已签收</s:if>
						</div></td>
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
<script language="javascript">
	document.forms(0).method.value="findInvoiceApplications";
</script>
</html>
