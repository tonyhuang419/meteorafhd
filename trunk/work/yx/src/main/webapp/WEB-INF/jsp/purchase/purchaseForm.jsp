<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="../commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/thousands.js" type="text/javascript"></script>
<html>
<head>
<title>
	<s:if test="'update'.equals(action)">申购采购修改</s:if>
	<s:else>新建申购采购</s:else>
</title>
<script language="javascript">

function showTo(){
   var applyType="";
   var infotype2=document.getElementsByName("am.applyType");
   for(i=0;i<infotype2.length;i++){
	if(infotype2[i].checked==true){
		applyType=infotype2[i].value;
	}
	}
	window.open('../purchase/contractQuery.action?applyType='+applyType,'newwindow','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800')
}
function show(){
	var infotype=document.getElementsByName("am.applyState");
	var infotype2=document.getElementsByName("am.applyType");
	for(var i=0;i<2;i++){
	    if(infotype[i].checked){
		var a=infotype[i].value;	
		}
	}
	for(var j=0;j<2;j++){
	    if(infotype2[j].checked){
		var b=infotype2[j].value;
		}
	}
	processPage(a,b);
	if(a==0){
		<%--如果是未签申请，清除相关信息--%>
		document.getElementById("purchase_am_mainId").value="";
		document.getElementById("purchase_contractName").value="";
		document.getElementById("purchase_contractId").value="";
		document.getElementById("purchase_am_eventId").value="";
		document.getElementById("purchase_cName2").value="";
		document.getElementById("purchase_cNameId2").value="";
		document.getElementById("itemAmount").value ="";
		document.getElementById("itemSid").value ="";
		$('customerSelect').selectedIndex = -1; 
	}
	if(a==1){
		<%--如果是已签申请，清除相关信息--%>
		document.getElementById("clientId").value ="";
		document.getElementById("estimateDate").value="";
		document.getElementById("projectName").value ="";
	}
}


function processPage(a,b){
	if(a==0&&b==1){
	       //未签工程类
	       document.all["ht"].style.display="none";
		   document.all["xm"].style.display="none";
		   document.all["kh1"].style.display="none";
		   document.all["rq"].style.display="block";
		   document.all["kh2"].style.display="block";
		   document.all["xrs"].style.display="block";
		   document.all["x"].style.display="block";
	}
	if(a==0&&b==2){
	      //未签集成类
	       document.all["ht"].style.display="none";
		   document.all["xm"].style.display="none";
		   document.all["kh1"].style.display="none";
		   document.all["rq"].style.display="block";
		   document.all["kh2"].style.display="block";
		   document.all["xrs"].style.display="none";
		   document.all["x"].style.display="block";
	}
	if(a==1&&b==1){
	      //已签工程类
	       document.all["ht"].style.display="block";
		   document.all["xm"].style.display="block";
		   document.all["kh1"].style.display="block";
		   document.all["rq"].style.display="none";
		   document.all["kh2"].style.display="none";
		   document.all["xrs"].style.display="block";
		   document.all["x"].style.display="none";
	}
	if(a==1&&b==2){
	       //已签集成
	       document.all["ht"].style.display="block";
		   document.all["xm"].style.display="none";
		   document.all["kh1"].style.display="block";
		   document.all["rq"].style.display="none";
		   document.all["kh2"].style.display="none";
		   document.all["xrs"].style.display="none";
		   document.all["x"].style.display="none";
	}
}

function show1(m,n,x,y){
	var infotype=document.getElementsByName("am.applyState");
	var infotype2=document.getElementsByName("am.applyType");
	var idstate1=document.getElementsByName("idstate1");
	var idstate2=document.getElementsByName("idstate2");
	infotype[m].checked=true;
	infotype2[n-1].checked=true;
	if(x==1){
		idstate1[0].checked=true;
	}else{
		idstate1[0].checked=false;
	}
	
	if(y==2){
		idstate2[0].checked=true;
	}else{
		idstate2[0].checked=false;
	}
	for(var i=0;i<2;i++){
	    if(infotype[i].checked){
		var a=infotype[i].value;
		}
	}
	for(var j=0;j<2;j++){
	    if(infotype2[j].checked){
		var b=infotype2[j].value;
		}
	}
	processPage(a,b);
}

function clientChange(selObj){
		document.getElementById("clientId").value = selObj.value;
}

function selectedClient(clientObj){
		document.getElementById("clientId").value = clientObj.clientId;
		addOptionX("customerSelect", clientObj.clientId  , clientObj.clientName , 1);
}
	
	
function validate(){ 	
	var infotype=document.getElementsByName("am.applyState");
	var infotype2=document.getElementsByName("am.applyType");
	for(var i=0;i<2;i++){
    	if(infotype[i].checked){
			var a=infotype[i].value;
		}
	}
	for(var j=0;j<2;j++){
    	if(infotype2[j].checked){
			var b=infotype2[j].value;
		}
	}
	var ev = new Validator();
	with(purchase){
		if(a==0){
			ev.test("notblank","预计签订合同的日期不能为空！",$('am.estimateDate').value);
			ev.test("notblank","客户不能为空！",$('customerSelect').value);
			ev.test("dateYX","预计合同签订日期格式不正确!",$("am.estimateDate").value);
		}else{
			ev.test("notblank","合同不能为空",$('am.mainId').value);
			var maxAmount = $("itemAmount").value;
			maxAmount = formatNumber( maxAmount ,'#,###.00');  <%--显示用--%>
			var applyAmountX = $("applyAmount").value;			<%--比较用--%>
			applyAmountX = parseFloatNumber(applyAmountX);
			ev.test("greater","申请金额不能超过 "+ maxAmount , $("itemAmount").value  , applyAmountX );			
		}
		ev.test("float","申购金额必须是数字！",$('am.applymoney').value);
		ev.test("length","申购内容请少于100字符",$('applyContent').value,100);
		ev.test("length","备注请少于100字符",$('remark').value,100); 	
   	   if($('am.bugget').value.length > 0 ){ 
 	   		ev.test("+float","设备预算大于0的数字!",$('am.bugget').value);
 	   }
	}
	if (ev.size() > 0) {
		ev.writeErrors(errorsFrame, "errorsFrame");
     	return true;
  }
    return false;
}	
	function checkSubmit1(){
		if(!validate()){
			document.forms(0).action="/yx/purchase/purchase.action?method=updatePurchase&state=draft";
			document.forms(0).submit();
	    }
	}
	
	function checkSubmit2(){
		if(!validate()){
			document.forms(0).action="/yx/purchase/purchase.action?method=updatePurchase&state=wait";
			document.forms(0).submit();
		}
	}
	
	function checkSubmit3(){
		if(!validate()){
			document.forms(0).action="/yx/purchase/purchase.action?method=savePurchase&state=draft";
			document.forms(0).submit();	
		 }
		
	}
	
	function checkSubmit4(){
		if(!validate()){
			document.forms(0).action="/yx/purchase/purchase.action?method=savePurchase&state=wait";
			document.forms(0).submit();
		}
	}

	function saveNew(){
		if(!validate()){
			document.forms(0).action="/yx/purchase/purchase.action?method=savePurchase&state=draft&opState=6";
			document.forms(0).submit();	
		}
	}
	
function cmoney(){
	var infotype=document.getElementsByName("am.applyState");
	
	for(var i=0;i<2;i++){
   	 if(infotype[i].checked){
		var a=infotype[i].value;	
		}
	}

	if(a==1){
		var ev = new Validator();
		with(purchase){
			var maxAmount = $("itemAmount").value;
			maxAmount = formatNumber( maxAmount ,'#,###.00');  <%--显示用--%>
			var applyAmountX = $("applyAmount").value;			<%--比较用--%>
			applyAmountX = parseFloatNumber(applyAmountX);
			ev.test("greater","申请金额不能超过 "+ maxAmount , $("itemAmount").value  ,applyAmountX);			
		}
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
     	 }
  		document.all("errorsFrame").style.display="none";
    	return false;
	}
}
	
</script>

</head>

<s:if test="opState == 0">
	<script language="javascript"> 
		alert("保存成功");
	</script>
</s:if>
<s:elseif test="opState == 1">
	<script language="javascript"> 
		alert("提交成功");
	</script>
</s:elseif>
<s:elseif test="opState == 2">
	<script language="javascript"> 
		alert("删除成功");
	</script>
</s:elseif>
<s:elseif test="opState == 3">
	<script language="javascript"> 
		alert("修改成功");
	</script>
</s:elseif>

<body leftmargin="0"  <s:if test="am.id!=null">onLoad="javascript:show1(<s:property value="am.applyState" />,<s:property value="am.applyType" />,<s:property value="idstate1" />,<s:property value="idstate2" />);"</s:if><s:else>onLoad="javascript:show1(0,1,0,0);"</s:else>>
<s:form action="purchase" theme="simple" >
    <s:hidden name="am.id"></s:hidden>
    <s:hidden id="itemAmount" value="${maxAmount}"></s:hidden>
	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
		<tr>
			<td valign="top" align="center">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td height="3" align="left">
					<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="30%" scrolling="no"></iframe>
					</td>
				</tr>
				<tr>
					<td align="left">当前页面：申购采购->
					<s:if test="'update'.equals(action)">申购采购修改</s:if>
					<s:else>申购采购新建</s:else>
					<s:if test="displayInfo!=null">
					<div  style="color:#FF0000" align="left">
					<s:property value="displayInfo"/> 
					</div>
					</s:if>
					</td>
				</tr>

				<tr>
					<td class="bg_table01" height="1"><img
						src="../../images/temp.gif" alt="temp" width="1" height="3"></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%" align="right" class="bg_table02">申购人：</td>
					<td width="30%" align="left" class="bg_table02"><s:property value="applyMan" /></td>
					<td width="20%" align="right" class="bg_table02">申购部门：</td>
					<td width="30%" align="left" class="bg_table02"><s:property value="applyDepartment" /></td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">是否未签合同申购：</td>
					<td align="left" class="bg_table02">
					<input name="am.applyState" type="radio" value="0" onClick="javascript:show();">未签
					<input name="am.applyState" type="radio" value="1" onClick="javascript:show();">已签</td>
					<td class="bg_table02" align="right">申购类型：</td>
					<td align="left" class="bg_table02">
					<input type="radio" name="am.applyType" id="amj" value="1" onClick="javascript:show();">项目类 
					<input type="radio" id="amj" name="am.applyType" value="2" onClick="javascript:show();"> 集成类</td>
				</tr>
			</table>
			<!--合同相关信息-->
			<div id="ht">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%" align="right" class="bg_table02"><font color=red>*</font>选择合同：</td>
					<td width="30%" align="left" class="bg_table02" style="word-break: keep-all"><label>
					<s:hidden name="am.mainId" />
					<s:textfield name="contractName" size="15"  readonly="true"/>
					<input type="button" value="…"  onclick="showTo();"></label></td>
					<td class="bg_table02" width="17%" align="right" >合同号:</td>
					<td class="bg_table02" width="35%" align="left"><s:textfield name="contractId" readonly="true" size="15"/></td>
				</tr>
			</table>
			</div>
			<!--合同中的项目-->
			<div id="xm">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%" align="right" class="bg_table02">项目号：</td>
					<td width="30%" align="left" class="bg_table02">
					 <s:textfield name="am.eventId" readonly="true" size="15"/><s:hidden id="itemSid" name="am.itemMainId" />
					</td>
					<td class="bg_table02" align="right" colspan="2">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--合同中的客户-->
			<div id="kh1">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%"  align="right" class="bg_table02">客户：</td>
					<td width="30%" align="left" class="bg_table02"><s:hidden
						name="cNameId2" /> <s:textfield name="cName2" readonly="true" size="15" ></s:textfield>
					</td>
					<td class="bg_table02" align="right" colspan="2">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--预计合同签订日期-->
			<div id="rq">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center" id="yq1">
					<td width="20%" align="right" class="bg_table02"><font color=red>*</font>预计合同签订日期：</td>
					<td width="30%" align="left" class="bg_table02">
						<input type="text"	id="estimateDate" name="am.estimateDate" 
						value="<s:date name="am.estimateDate" format="yyyy-MM-dd"/>"
								size="15" style="word-break: keep-all"/><img src="../commons/images/calendar.gif"
								onClick="javascript:ShowCalendar('estimateDate')"
								align=absMiddle </td>
					<td class="bg_table02" align="right" colspan="2">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--可选择客户-->
			<div id="kh2">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%" class="bg_table02" align="right"><font color=red>*</font>客户：</td>
					<td width="30%" align="left" class="bg_table02">
			   <!--       <input type="text" id="cName1" name="cName1" value="<s:property value="cName1" />"
			        style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">  -->
					<s:select id="customerSelect" name="am.customerId"  list="yxClientCodeList" cssStyle="width:130px;"  
					listKey="cli.id" listValue="cli.name" emptyOption="true"  onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…" onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');">
					<s:hidden name="cNameId1" id="clientId"></s:hidden>  
					</td>
					<td class="bg_table02" align="right" colspan="2">&nbsp;</td>
				</tr>
			</table>
			</div>
			
			<!--项目名称-->
			<div id="x">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
					<td width="20%" class="bg_table02" align="right"><font color=red>*</font>项目名称：</td>
					<td width="30%" class="bg_table02" align="left">
						<s:textfield name="am.projectName" size="15" id="projectName"></s:textfield>
					</td>
					<td class="bg_table02" align="right" colspan="2">&nbsp;</td>
			</tr>
			</table>
			</div>
			
			<!--任务号,设备预算-->
			<div id="xrs">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
	         <tr align="center">
	         			<td width="20%" class="bg_table02" align="right">任务号：</td>			
					<td align="left" width="30%" class="bg_table02">
						<s:textfield name="am.assignmentId" size="15" />
					</td>	
					<td class="bg_table02" align="right" colspan="2">&nbsp;</td>
				</tr>
		
				<tr align="center">
					<td class="bg_table02" align="right">设备预算：</td>
					<td class="bg_table02" align="left">
							<s:textfield name="am.bugget" onblur="formatInputNumber(this);" size="15" />
					</td>
					<td class="bg_table02" align="right" colspan="2">&nbsp;</td>
				</tr>
			</table>
			</div>

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%" align="right" class="bg_table02"><font color=red>*</font>申购金额：</td>		
					<td width="30%" class="bg_table02" align="left">
						<s:textfield name="am.applymoney" onblur="formatInputNumber(this);cmoney();" size="15" id="applyAmount"/>
					 </td>
					<td colspan="2" align="left" class="bg_table02">&nbsp;</td>
				</tr>
				<tr align="center">
					<td  class="bg_table02" align="right"  >联系人：</td>
					<td  class="bg_table02" align="left">
					<s:textfield name="am.linkman" size="15" /> </td>
					<td colspan="2" align="left" class="bg_table02">到齐通知 
					<input name="idstate1" onclick="document.forms(0).idstate2.checked=false" value="1" type="checkbox" > 分批通知 
					<input type="checkbox" onclick="document.forms(0).idstate1.checked=false" name="idstate2" value="2" >
					</td>
				</tr>

				<tr align="center">
					<td align="right" class="bg_table02">申购内容：</td>
					<td class="bg_table02" align="left" colspan="3">
						<s:textarea name="am.applyContent" id="applyContent" cols="50" rows="2" ></s:textarea>
					</td>
				</tr>

				<tr align="center">
					<td height="29" align="right" class="bg_table02">备注：</td>
					<td class="bg_table02" align="left" colspan="3">
					<s:textarea name="am.remark" id="remark" cols="50" rows="2" ></s:textarea>
					</td>
				</tr>

				<tr align="center">
					<td class="bg_table02"  align="right">&nbsp;</td>
					<td class="bg_table02" align="left">&nbsp;</td>
					<td width="8%" align="right" class="bg_table02">&nbsp;</td>
					<td width="44%" align="left" class="bg_table02">申购日期:<s:date name="am.applyDate" format="yyyy-MM-dd"/></td>
				</tr>

				<s:if test="'update'.equals(action)">
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="right" colspan="2"><input class="button01"
									type="button" name="gonext" value="保   存"
									onclick="checkSubmit1();" />
								</td>
								
								<td align="right" colspan="2"><input class="button01"
									type="button" name="gonext" value="确认提交"
									onclick="checkSubmit2();" />
								</td>
								
								<td align="center" colspan="2"><input class="button01"
									type="button" name="gonext" value="关   闭"
									onclick="javascript:window.close();" />
								</td>
							</tr>
						</tfoot>
					</table>
					</td>
				</tr>
				</s:if>
				<s:else>
				<tr class="bg_table03" align="center" style="height: 42px">
						<td align="center" colspan="4">
							<input class="button01" type="button" value="保  存" onclick="saveNew();" />
							<input class="button01" type="button" name="gonext" value="保存并关闭" onclick="checkSubmit3();" />
							<input class="button01" type="button" name="gonext" value="确认提交" onclick="checkSubmit4();" />
						</td>
				</tr>
				</s:else>
			</table>
			</s:form>
</body>
</html>
