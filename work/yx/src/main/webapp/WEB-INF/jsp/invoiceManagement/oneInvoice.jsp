<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/casemoney.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/thousands.js" type="text/javascript"></script>

<html>
<head>
<script type="text/javascript">
	function isTaxNoOfBillClientBlank(billClientId){
			var isBlank=false;
			var jsonRequest = new Request.JSON({async:false,url:'/yx/jsonData.action?method=isTaxNoOfBillClientBlank&billClientId='+billClientId, onComplete: function(jsonObj){
				if(jsonObj!=null && jsonObj.jsonData !=null ){
					isBlank = jsonObj.jsonData.isTaxNumberBlank;
				}else{
					alert("验证税号出现失败");
				}
			}}).get({randerNum:Math.random()});	
			return isBlank;
	}
	function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById('clientId').value = selObj.value;
		addOptionX("billCustomerSelect", selObj.value , selObj.options[selObj.selectedIndex].text , 1);
		clientChangeY(selObj);
	}
	
	function selectedClient(clientObj){
	 var str=document.getElementById("showhidden").value;
	 if(str == "cc"){
	 	document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
		document.getElementById("billNameY").value = clientObj.clientName;
		document.getElementById("clientIdY").value = clientObj.clientId;
		addOptionX("conCustomerSelect", clientObj.clientId  , clientObj.clientName , 1);
		addOptionX("billCustomerSelect", clientObj.clientId  , clientObj.clientName , 1);
		liandong("/yx/jsonData.action?method=doGetLinkMainOfUnits&unitsId="+clientObj.clientId);
	 }
	 else{
	 	document.getElementById("billNameY").value = clientObj.clientName;
		document.getElementById("clientIdY").value = clientObj.clientId;
		addOptionX("billCustomerSelect", clientObj.clientId  , clientObj.clientName , 1);
		liandong("/yx/jsonData.action?method=doGetLinkMainOfUnits&unitsId="+clientObj.clientId);
	 }
	 }
	
	function clientChangeY(selObj){
		document.getElementById('billNameY').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById('clientIdY').value = selObj.value;
     	liandong("/yx/jsonData.action?method=doGetLinkMainOfUnits&unitsId="+selObj.value);
	}
	function liandong(jsonObjGetUrl)
	{
		var jsonRequest = new Request.JSON({url:jsonObjGetUrl, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
					if(jsonObj.jsonData.billAdd!=null){
					document.getElementById('address').innerHTML=jsonObj.jsonData.billAdd;
					}else{document.getElementById('address').innerHTML="";}
					
					if(jsonObj.jsonData.billPhone!=null){
					document.getElementById('phone').innerHTML=jsonObj.jsonData.billPhone;
					}else{
					document.getElementById('phone').innerHTML="";
					}
					
					if(jsonObj.jsonData.billBank!=null){
					document.getElementById('bank').innerHTML=jsonObj.jsonData.billBank;}
					else{
					document.getElementById('bank').innerHTML="";
					}
					
					if(jsonObj.jsonData.account!=null){
					document.getElementById('zhanghao').innerHTML=jsonObj.jsonData.account;}
					else{
					document.getElementById('zhanghao').innerHTML="";
					}
					
					if(jsonObj.jsonData.taxNumber!=null){
					document.getElementById('shuihao').innerHTML=jsonObj.jsonData.taxNumber;}
					else{
					document.getElementById('shuihao').innerHTML="";
					}
			}
		}}).get({randerNum:Math.random()});	 	
	}
	
	
	function lowercaseY()
	{
		var a=Chinese(document.getElementById('lowerY').value);
		document.getElementById('capitalizationY').value=a;
		document.getElementById('lowerN').value=document.getElementById('lowerY').value/1.117;
		var b=Chinese(document.getElementById('lowerN').value);
		document.getElementById('capitalizationN').value=b;
	}
	
	
	
	function asd()
	{
		var b = document.getElementById("tax").value;
		var c = document.getElementById('base').value;
		
		m = document.getElementById('money').value;
		
		if(m!=null && m !="" ){
			var moneyx = parseFloatNumber(m);	
			if(c==1)
			{
			document.getElementById('lowerY').innerHTML= Math.round( moneyx * 100)/100;
			document.getElementById('capitalizationY').innerHTML=Chinese( document.getElementById('lowerY').innerHTML );
			document.getElementById('lowerN').innerHTML=Math.round( document.getElementById('lowerY').innerHTML  /b*100 ) /100;
			document.getElementById('capitalizationN').innerHTML=Chinese( document.getElementById('lowerN').innerHTML );
			}
			else if(c==0)
			{
			document.getElementById('lowerN').innerHTML= Math.round( moneyx * 100)/100;
			document.getElementById('capitalizationN').innerHTML=Chinese(  document.getElementById('lowerN').innerHTML );
			document.getElementById('lowerY').innerHTML=Math.round( document.getElementById('lowerN').innerHTML * b*100)/100;
			document.getElementById('capitalizationY').innerHTML=Chinese( document.getElementById('lowerY').innerHTML );
			}
			document.getElementById('lowercaseMoney').value = document.getElementById('lowerY').innerHTML
			document.getElementById('lowercaseMoneyN').value = document.getElementById('lowerN').innerHTML;
			
			document.getElementById('lowerY').innerHTML = formatNumber(document.getElementById('lowerY').innerHTML,'#,###.00');
			document.getElementById('lowerN').innerHTML = formatNumber(document.getElementById('lowerN').innerHTML,'#,###.00');
		}
		else{
			document.getElementById('lowerN').innerHTML= 0;
			document.getElementById('capitalizationN').innerHTML="零";
			document.getElementById('lowerY').innerHTML= 0;
			document.getElementById('capitalizationY').innerHTML="零";
		}
	}
	
	function openUrl()
	{
		window.open('createInvoice.action?method=openAssociationUpdate','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');
	}
	
	function refreshSuper()	
	{
		document.forms(0).action="createInvoice.action?method=refreshupdata";
		document.forms(0).submit();
	}
	function refreshfu()
	{
		document.forms(0).action="createInvoice.action?method=findInvoiceApplications";
		document.forms(0).submit();
	}
	function deleter(a)
	{
		document.forms(0).action="createInvoice.action?method=applicationsDelete&modify=true&deleteId="+a;
		document.forms(0).submit();
	}
	function uuuuuu(a)
	{
		document.forms(0).action="createInvoice.action?method=updateOneInvoice&idd="+a;
		document.forms(0).submit();
	}
	function saveuu()
	{	
			document.forms(0).action="createInvoice.action?method=updateOneInvoice";
			document.forms(0).submit();	
	}
	
	function saveaAndApply()
	{	
		//验证客户有没有税号
		if($('piaotype').value == "2" && isTaxNoOfBillClientBlank($('clientIdY').value)){
			alert("开票客户没有税号");
			return;
		}
		var f=window.confirm("您确认要提交您的申请吗？");
		if(f){
			document.forms(0).action="createInvoice.action?method=updateOneInvoice&saveUpdate=2";   <%--saveUpdate表明申请并提交--%>
			document.forms(0).submit();
		}
	}
	
	function changeBillType(obj){
		billType = obj.value;
		changeMoney("/yx/jsonData.action?method=doGetTax&billType=" + billType);
	}
	

	function changeMoney(jsonObjGetUrl){
		var jsonRequest = new Request.JSON({url:jsonObjGetUrl, onComplete: function(jsonObj){
			if(jsonObj!=null && jsonObj.jsonData !=null ){
					if(jsonObj.jsonData!=null){
						//alert(jsonObj.jsonData);
						document.getElementById("tax").value = jsonObj.jsonData;
						asd();
					}
			}
		}}).get({randerNum:Math.random()});	 	
	}
	
	
	function saveuuX(obj)
	{	
		if(obj == 1){
			document.forms(0).action="createInvoice.action?method=updateOneInvoice&state=1";
			document.forms(0).submit();	
		}
		else if(obj==2){
			document.forms(0).action="createInvoice.action?method=updateOneInvoice&state=2";
			document.forms(0).submit();	
		}
		else if(obj==3){
			//验证客户有没有税号
			if($('piaotype').value == "2" && isTaxNoOfBillClientBlank($('clientIdY').value)){
				alert("开票客户没有税号");
				return;
			}
			var f=window.confirm("您确认要提交您的申请吗？");
				if(f)
			{
				document.forms(0).action="createInvoice.action?method=updateOneInvoice&state=3&saveUpdate=2";
				document.forms(0).submit();	
			}
		}
	}
	
	
		
	function setShowId(buttonid){	
      var hidden=document.getElementById("showhidden");
      //合同客户
      if(buttonid=="cc"){
      	hidden.setAttribute("value","cc");
      }  
      //开票客户
      if(buttonid=="bc"){
        hidden.setAttribute("value","bc")
      }
	}
	
</script>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 16px;
	font-weight: bold;
}
-->
</style>
</head>
<s:if test="succSave == 2">
	<script language="javascript"> 
		alert("修改成功");
	</script>
</s:if>
<body  style="margin: 0px;">  <%-- onload="show();" --%>
	<br>
	<div  style="color:#000000" align="center">
		
	<s:if test="state==1">
	<div  align="left">	<font color="red">开票申请已保存</font></div>
		<div  class="STYLE1" >
			未签合同开票申请</div>
	</s:if>
			<s:else>
			<div  class="STYLE1" >
				开票申请修改
				</div>
			</s:else>
	</div>
	<iframe name="errorsFrame" frameborder="0"	framespacing="0" height="0" scrolling="no" >	</iframe>
	<s:form action="createInvoice" theme="simple" validate="true"  id="noConApplyBill">
	<s:hidden id="hiddenids" name="hiddenid"/>
	 <input type="hidden" id="tax" value="" />
	 	<input type="hidden" id="showhidden" />
	<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style="border-collapse: collapse;">
						<tr >
							<td width="10%" align="right">申请部门：</td>
							<td width="25%" align="left"><s:property value="position"/></td>
							<td width="10%" align="right">申请人：</td>
							<td width="25%" align="left"><s:property value="user.name"/></td>
							<td width="35%" colspan="2" align="right">&nbsp;</td>
						</tr>

						<tr>
							<td align="right" nowrap >
								<font color="red">*</font>合同客户：
							</td>
							<td  align="left">
								<input type="text" id="clientName" name="hetongkehu" value="<s:property value="hetongkehu"/>" style="width:150px;height:21px;font-size:10pt;" readonly="true" /><span style="width:18px;border:0px solid red;">
								<s:select id="conCustomerSelect" list="yxClientCodeList"
										cssStyle="margin-left:-150px;width:168px;" listKey="id"
										listValue="name" onchange="clientChange(this)" emptyOption="true"></s:select></span>
								<input type="button" value="…" id="cc"
									onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getSelectClientlist','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
								<s:hidden name="clientNameY" id="clientId"></s:hidden> 
							</td>
							<td align="right" >
								<font color="red">*</font>合同名称：
							</td>
							<td  align="left">
								<s:textfield name="contactName" id="contactName"></s:textfield>
							</td>
							<td colspan="2" align="right" >
								&nbsp;
							</td>
						</tr>
						<tr>
							<td nowrap align="right" >开票单位名称：</td>
							<td  align="left">
								<input type="text" id="billNameY" name="kaipiaodanwei"  value="<s:property value="kaipiaodanwei"/>" style="width:150px;height:21px;font-size:10pt;" readonly="true" /><span style="width:18px;border:0px solid red;">
								<s:select id="billCustomerSelect" list="yxClientCodeList"
										cssStyle="margin-left:-150px;width:168px;" listKey="id" 
										listValue="name" onchange="clientChangeY(this)"  emptyOption="true"></s:select> </span>
								<input type="button" value="…" id="bc"
									onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
								<s:hidden name="billCustomer" id="clientIdY"></s:hidden>
							</td>
							<td  align="right">地址：</td>
							<td align="left"   id="address">
								<s:property value="yxClientCode.billAdd"/>
							</td>
							<td align="right" nowrap>联系电话：</td>
							<td width="25%" align="left"  id="phone">
								<s:property value="yxClientCode.billPhone"/>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap>开户银行：</td>
							<td align="left"  id="bank">
								<s:property value="yxClientCode.billBank"/>
							</td>
							<td  align="right">帐号：</td>
							<td  align="left" id="zhanghao">
								<s:property value="yxClientCode.account"/>
							</td>
							<td align="right" >税号：</td>
							<td align="left"  id="shuihao">
								<s:property value="yxClientCode.taxNumber"/>	
							</td>
						</tr>
						<tr>
							<td  align="right"><font color="red">*</font>开票性质：</td>
							<td align="left" >
								<s:select name="invoiceNature" list="invoiceNatureList" listKey="typeSmall"  
									listValue="typeName"></s:select>
							</td>
							<td  align="right"><font color="red">*</font>票据类型:</td>
							<td align="left" >
								<s:select name="invoiceType" list="invoiceList" listKey="typeSmall" id="piaotype"
									listValue="typeName"  onchange="changeBillType(this);"></s:select>
							</td>
							<td colspan="2" align="right" >
								&nbsp;
							</td>
						</tr>
						
						<tr>
							<td  align="right">
								<font color="red">*</font>申请金额：
							</td>
							<td align="left" >
								<s:textfield name="money" id="money" onchange="javascript:asd();" onkeypress="JHshNumberText();"></s:textfield>
							</td>
							<td align="right" nowrap >
								<font color="red">*</font>基准：
							</td>
							<td  align="left">
							<s:select list="#@java.util.HashMap@{0:'不含税',1:'含税'}" name="base" id="base" onchange="javascript:asd();">
							</s:select>
							</td>
							<td colspan="2" align="right" >
								&nbsp;
							</td>
						</tr>
						
						
						<tr>	
							<td colspan="3" align="left" >发票金额小写(含税)：
								<s:label id="lowerY" ></s:label>
								<input type="hidden" name="lowercaseMoney" id="lowercaseMoney">
							</td>
							<td colspan="3"  align="left" >发票金额小写(不含税)：
								<s:label  id="lowerN" ></s:label>
								<input type="hidden" name="lowercaseMoneyN" id="lowercaseMoneyN">
							</td>
						</tr>
						<tr>	
							<td  colspan="3" align="left">发票金额大写(含税)：
								<s:label id="capitalizationY"></s:label>	
							</td>
							<td colspan="3"  align="left" >发票金额大写(不含税)：
								<s:label id="capitalizationN" ></s:label>	
							</td>
						</tr>
						<tr>	
							<td align="right" >
								一次出库:
							</td>
							<td  align="left" >
								<s:checkbox name="ischu"></s:checkbox>
							</td>
							<td align="right" >
								<div align="right">
									取票地点：
								</div>
							</td>
							<td   align="right" >
								<div align="left">
								<s:textfield  name="billSpot" id="billSpot"> </s:textfield>
								</div>
							</td>
							<td width="16%" align="right" >
								库存组织：
							</td>
							<td   align="right" >
								<div align="left">
								<s:select name="stockOrg" list="stockOrgList" listKey="typeSmall"
									listValue="typeName" id="stockOrgId" ></s:select>
								</div>
							</td>
						</tr>

						<tr>
							<td align="right" >
								<font color="red">*</font>开票内容：
							</td>
							<td colspan="5"  align="left" >
								<s:textarea  name="invoiceContent" id="invoiceContent" cols="50"   ></s:textarea>
							</td>							
						</tr>
						
						<tr>
							<td align="right" >
								备注：
							</td>
							<td colspan="3" align="left" >
								<s:textarea name="remarks" cols="50"></s:textarea>
							</td>
							<td  align="right">甲方接收人：</td>
							      <td>
								      <s:textarea cols="20" rows="3" id="firstReceiveMan" name="firstReceiveMan" ></s:textarea>
							      </td>
							</tr>
							<s:if test="applyBillState == 4">
								<tr>
									<td align="right" >退回理由：</td>
									<td colspan="5" align="left" >
										<s:property value="returnReason"/>
									</td>
								</tr>
							</s:if>
					</table>
					<br/>
				<table width="100%" border="1" class="bg_table02" align="center" bordercolor="#808080" style=" border-collapse: collapse;">
						<tr class="bg_table01">
							<td width="11%" align="right" >
								<div align="center">
									申购关联：
								</div>
							</td>
							<td width="15%" align="left" >
								<div align="center">
									申购单号
								</div>
							</td>
							<td width="58%" align="right" >
								<div align="center">
									申购内容
								</div>
							</td>
							<td width="16%" align="left" >
								&nbsp;
							</td>

						</tr>
						<s:iterator value="messageList">
						<tr class="bg_table02">
							<td align="left" >&nbsp;</td>
							<td align="left" >
								<div align="center"><s:property value="applyId"/></div>
							</td>
							<td align="left" >
								<div align="center"><s:property value="applyContent"/></div>
							</td>
							<td align="left" >
								<div align="center"><a href="#" onclick="javascript:deleter('<s:property value="id"/>')">删除</a></div>
							</td>
						</tr>
						</s:iterator>
						<tr>
							<td align="center"  colspan="4">
								<div align="center">
									<a href="#" onclick="javascript:openUrl()">添加</a>
								</div>
							</td>
							
						</tr>
					</table>
					<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr height="42px">
							<td class="bg_table04">
								<div align="center">
								<s:if test="state==1">
									<input type="button" name="button4" id="button4" value=" 返  回" class="button01"  onClick="goBack();">			
									<input type="button" value="保  存" class="button01"
										onclick="javascript:if(!validate1()){saveuuX(1);}">
									<input type="button" value="保存关闭" class="button01"
										onclick="javascript:if(!validate1()){saveuuX(2);}">
									<input type="button" value="提交申请" class="button01"
										onclick="javascript:if(!validate1()){saveuuX(3);}">	
								</s:if>
								<s:else>
									<input type="button" name="button4" id="button4" value=" 返   回" class="button01"  onClick="goBack2();">
									<input type="button" value="保  存" class="button01"
										onclick="javascript:if(!validate1()){saveuu();}">
									<input type="button" value="提交申请" class="button01"
										onclick="javascript:if(!validate1()){saveaAndApply();}">
								</s:else>
								</div>
							</td>
						</tr>
					</TABLE>
	</s:form>
	

</body>
<script type="text/javascript">
	changeBillType(document.getElementById("invoiceType"));
	//clientChangeY(document.getElementById("clientChangeYs"));
	
	function validate1(){
     var ev2=new Validator();
     with(noConApplyBill){  
       ev2.test("notblank","合同客户不能为空",$('clientId').value);   
       ev2.test("notblank","开票单位不能为空",$('clientIdY').value); 
       ev2.test("notblank","合同名称不能为空",$('contactName').value); 
       ev2.test("notblank","合同金额不能为空",$('money').value); 
       ev2.test("notblank","取票地点不能为空",$('billSpot').value); 
       ev2.test("notblank","开票内容不能为空",$('invoiceContent').value); 
       ev2.test("float","申请金额必须为数字",$('money').value); 
       ev2.test("length","开票内容请少于300字符",$('invoiceContent').value,400); 
       ev2.test("length","备注请少于300字符",$('remarks').value,500);  
 	}
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}

	
function goBack()
{
	location.href="/yx/invoice/createInvoice.action";
}

function goBack2()
{
	//var form = document.getElementById("findConition");
	location.href = "/yx/invoice/createInvoice.action?method=findInvoiceApplications";
	//form.submit();
}

	
</script>

</html>
