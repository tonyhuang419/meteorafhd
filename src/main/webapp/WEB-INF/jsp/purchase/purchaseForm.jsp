<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>addNewDepartment</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all"
	href="./css/calendar-win2k-cold-1.css" title="win2k-cold-1" />
<script src="../commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="<s:url value="/commons/scripts/CalendarSelector.js"/>"
	type="text/javascript"></script>

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
if(a==0&&b==1){
       //未签工程类
       document.all["ht"].style.display="none";
	   document.all["xm"].style.display="none";
	   document.all["kh1"].style.display="none";
	   document.all["rq"].style.display="block";
	   document.all["kh2"].style.display="block";
	   document.all["xrs"].style.display="block";
	   document.all["x"].style.display="none";
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
	   document.all["x"].style.display="block";
}
}
function show1(m,n,x,y){
var infotype=document.getElementsByName("am.applyState");
var infotype2=document.getElementsByName("am.applyType");
var idstate1=document.getElementsByName("idstate1");
var idstate2=document.getElementsByName("idstate2");
infotype[m].checked=true;
infotype2[n].checked=true;
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
if(a==0&&b==1){
       //未签工程类
       document.all["ht"].style.display="none";
	   document.all["xm"].style.display="none";
	   document.all["kh1"].style.display="none";
	   document.all["rq"].style.display="block";
	   document.all["kh2"].style.display="block";
	   document.all["xrs"].style.display="block";
	   document.all["x"].style.display="none";
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
	   document.all["x"].style.display="block";
}
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
			ev.test("notblank","客户不能为空！",$('cNameId1').value);
			if(b==1){
				ev.test("notblank","项目名称不能为空！",$('projectName1').value);
			}else{
				ev.test("notblank","项目名称不能为空！",$('projectName2').value);
			}
		}else{
			ev.test("notblank","合同不能为空",$('am.mainId').value);
			if(b==1){
				ev.test("notblank","项目名称不能为空！",$('projectName1').value);
			}else{
				ev.test("notblank","项目名称不能为空！",$('projectName2').value);
			}
		}
		ev.test("float","申购金额必须是数字！",$('am.applymoney').value);
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
	
	
</script>

</head>
<body  <s:if test="am.id!=null">onLoad="javascript:show1(<s:property value="am.applyState" />,<s:property value="am.applyType" />,<s:property value="idstate1" />,<s:property value="idstate2" />);"</s:if><s:else>onLoad="javascript:show1(0,0,0,0);"</s:else>>
<s:form action="purchase" theme="simple" >
    <s:hidden name="am.id"></s:hidden>
	<table width="95%" border="0" align="center" cellpadding="1"
		cellspacing="1">
		<tr>
			<td valign="top" align="center">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td height="3" align="left">当前页面：申购采购->
					<s:if test="'view'.equals(action)">申购采购查看</s:if>
					<s:elseif test="'update'.equals(action)">申购采购修改</s:elseif>
					<s:else>申购采购新建</s:else>
					</td>
				</tr>
				<tr>
					<td align="center">
					<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="30%" scrolling="no"></iframe>
					</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img
						src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
				</tr>
			</table>
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" align="right" class="bg_table02">申购人：</td>
					<td width="23%" align="left" class="bg_table02"><s:property value="applyMan" /></td>
					<td width="17%" align="right" class="bg_table02">申购部门：</td>
					<td width="35%" align="left" class="bg_table02"><s:property value="applyDepartment" /></td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">是否未签合同申购</td>
					<td align="left" class="bg_table02"><input name="am.applyState"
						type="radio"  value="0" onClick="javascript:show();" <s:if test="'view'.equals(action)">disabled</s:if>>未签
					<input name="am.applyState" type="radio" value="1"
						onClick="javascript:show();" <s:if test="'view'.equals(action)">disabled</s:if>>已签</td>
					<td class="bg_table02" align="right">申购类型：</td>
					<td align="left" class="bg_table02"><input type="radio"
						name="am.applyType" id="amj" value="1" onClick="javascript:show();" <s:if test="'view'.equals(action)">disabled</s:if>>
					工程类 <input type="radio" id="amj" name="am.applyType" value="2"
						onClick="javascript:show();" <s:if test="'view'.equals(action)">disabled</s:if>> 集成类</td>
				</tr>
			</table>
			<!--合同相关信息-->
			<div id="ht">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" align="right" class="bg_table02"><font color=red>*</font>选择合同：</td>
					<td width="23%" align="left" class="bg_table02" style="word-break: keep-all"><label>
					<s:hidden name="am.mainId" />
					<s:textfield name="contractName" size="15"  readonly="true"/> <input
						type="button" value="……" <s:if test="'view'.equals(action)">disabled</s:if>
						onclick="showTo();"></label></td>
					<td class="bg_table02" width="17%" align="right" >合同号:</td>
					 <s:if test="'view'.equals(action)">
					<td class="bg_table02" width="35%" align="left"><s:textfield name="contractId" readonly="true"  size="15"/></td>
					</s:if>
					<s:else>
					<td class="bg_table02" width="35%" align="left"><s:textfield name="contractId" size="15"/></td>
						</s:else>
				</tr>
			</table>
			</div>
			<!--合同中的项目-->
			<div id="xm">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" align="right" class="bg_table02">项目号：</td>
					<td width="23%" align="left" class="bg_table02">
					 <s:if test="'view'.equals(action)">
					<s:textfield name="am.eventId" readonly="true"  size="15"/>
					</s:if>
					<s:else>
					<s:textfield name="am.eventId"  size="15"/>
					</s:else>
					</td>
					<td width="17%" align="right" class="bg_table02">&nbsp;</td>
					<td width="35%" align="left" class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--合同中的客户-->
			<div id="kh1">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" height="23" align="right" class="bg_table02">客户：</td>
					 <s:if test="'view'.equals(action)">
					<td width="23%" align="left" class="bg_table02"><s:hidden
						name="cNameId2" /> <s:textfield name="cName2"  readonly="true" size="15" ></s:textfield>
					</td>
					</s:if>
					<s:else>
					<td width="23%" align="left" class="bg_table02"><s:hidden
						name="cNameId2" /> <s:textfield name="cName2" size="15" ></s:textfield>
					</td>
					</s:else>
					
					<td width="17%" class="bg_table02" align="right">&nbsp;</td>
					<td width="35%" class="bg_table02" align="left">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--预计合同签订日期-->
			<div id="rq">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center" id="yq1">
					<td width="25%" align="right" class="bg_table02"><font color=red>*</font>预计合同签订日期：</td>
					<td width="23%" align="left" class="bg_table02">
						<input type="text"
								id="estimateDate" name="am.estimateDate" value="<s:date name="am.estimateDate" format="yyyy-MM-dd"/>"
								readonly="readonly" onClick="javascript:ShowCalendar(this.id)"
								size="15" style="word-break: keep-all"/> <img src="../commons/images/calendar.gif"
								onClick="javascript:ShowCalendar('estimateDate')"
								align=absMiddle alt=""  <s:if test="'view'.equals(action)">disabled</s:if>/></td>
					<td width="17%" class="bg_table02" align="right">&nbsp;</td>
					<td width="35%" class="bg_table02" align="left">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--可选择客户-->
			<div id="kh2">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" class="bg_table02" align="right"><font color=red>*</font>客户：</td>
					<td width="23%" align="left" class="bg_table02"><s:hidden
						name="cNameId1" /> <s:textfield name="cName1" readonly="true" size="15" /> 
						<input type="button" value="……"  <s:if test="'view'.equals(action)">disabled</s:if>
						onclick="javascript:window.open('../purchase/contractQuery.action?method=showClient','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');">
					</td>
					<td width="17%" class="bg_table02" align="right">&nbsp;</td>
					<td width="35%" class="bg_table02" align="left">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--项目名称，任务号,设备预算-->
			<div id="xrs">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
	         <tr align="center">
	         			<td width="25%" class="bg_table02" align="right">任务号：</td>			
					<td align="left" width="23%" class="bg_table02">
					 <s:if test="'view'.equals(action)">
					<s:textfield name="am.assignmentId" readonly="true" size="15" /></td>	
					</s:if>
						<s:else>
						<s:textfield name="am.assignmentId" size="15" /></td>
						</s:else>
					<td width="17%" class="bg_table02" align="right">&nbsp;</td>
					<td class="bg_table02" align="left">
					<s:textfield name="projectName1" cssStyle="display:none" size="15" ></s:textfield>&nbsp;
				</tr>
				
				<tr align="center">
				
					<td class="bg_table02" align="right">设备预算：</td>
					<td class="bg_table02" align="left">
					<s:if test="'view'.equals(action)">
					<s:textfield name="am.bugget" readonly="true" size="15" /></td>
					</s:if>
						<s:else>
						<s:textfield name="am.bugget" size="15" /></td>
						</s:else>
					<td class="bg_table02" align="right">&nbsp;</td>
					<td align="left" class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--项目名称-->
			<div id="x">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
					<td width="25%" class="bg_table02" align="right"><font color=red>*</font>项目名称：</td>
					<td width="23%" class="bg_table02" align="left">
					 <s:if test="'view'.equals(action)">
					<s:textfield name="projectName2"  readonly="true" size="15" ></s:textfield></td>
					</s:if>
					<s:else>
					<s:textfield name="projectName2" size="15" ></s:textfield></td>
					</s:else>
					<td width="17%" class="bg_table02" align="right">&nbsp;</td>
					<td align="left" class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</div>

			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" height="24" align="right" class="bg_table02"><font color=red>*</font>申购金额：</td>		
					<td width="23%" class="bg_table02" align="left">
					 <s:if test="'view'.equals(action)">
					<s:textfield name="am.applymoney" readonly="true" size="15" /> </td>
					</s:if>
						<s:else>
						<s:textfield name="am.applymoney" size="15" /> </td>
						</s:else>
					<td colspan="2" align="left" class="bg_table02">&nbsp;</td>
				</tr>
				<tr align="center">
					<td width="25%" height="24" align="right" class="bg_table02">联系人：</td>
					<td width="23%" class="bg_table02" align="left">
					 <s:if test="'view'.equals(action)">
					<s:textfield name="am.linkman" readonly="true" size="15" /> </td>
					</s:if>
					<s:else>
					<s:textfield name="am.linkman" size="15" /> </td>
					</s:else>
					<td colspan="2" align="left" class="bg_table02">到齐通知 <input
						name="idstate1" onclick="document.forms(0).idstate2.checked=false" value="1" type="checkbox"<s:if test="'view'.equals(action)">disabled</s:if>> 分批通知 <input
						type="checkbox" onclick="document.forms(0).idstate1.checked=false" name="idstate2" value="2"<s:if test="'view'.equals(action)">disabled</s:if>></td>
				</tr>


				<tr align="center">
					<td height="29" align="right" class="bg_table02">申购内容：</td>
					<td class="bg_table02" align="left" colspan="3">
					 <s:if test="'view'.equals(action)">
					<s:textarea name="am.applyContent" readonly="true"  cols="60" rows="5" ></s:textarea></td>
					</s:if>
					<s:else>
					<s:textarea name="am.applyContent" cols="60" rows="5" ></s:textarea></td>
					</s:else>
				</tr>

				<tr align="center">
					<td height="29" align="right" class="bg_table02">备注：</td>
					<td class="bg_table02" align="left" colspan="3">
					 <s:if test="'view'.equals(action)">
					<s:textarea name="am.remark" readonly="true" cols="40" rows="5" ></s:textarea></td>
					</s:if>
					<s:else>
					<s:textarea name="am.remark" cols="40" rows="5" ></s:textarea></td>
					</s:else>
				</tr>

				<tr align="center">
					<td class="bg_table02" width="25%" align="right">&nbsp;</td>
					<td class="bg_table02" width="23%" align="left">&nbsp;</td>
					<td width="8%" align="right" class="bg_table02">&nbsp;</td>
					<td width="44%" align="left" class="bg_table02">申购日期:<s:date name="am.applyDate" format="yyyy-MM-dd"/></td>
				</tr>

				<s:if test="'view'.equals(action)">
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
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
				<s:elseif test="'update'.equals(action)">
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="right" colspan="2"><input class="button01"
									type="button" name="gonext" value="更    新"
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
				</s:elseif>
				<s:else>
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="right" colspan="2"><input class="button01"
									type="button" name="gonext" value="保    存"
									onclick="checkSubmit3();" />
								</td>
								
								<td align="right" colspan="2"><input class="button01"
									type="button" name="gonext" value="确认提交"
									onclick="checkSubmit4();" />
								</td>
								
								<td align="right" colspan="2"><input class="button01"
									type="reset" name="gonext" value="重    填" /></td>
							</tr>
						</tfoot>
					</table>
					</td>
				</tr>
				</s:else>
			</table>
			</s:form>
</body>
</html>
